package org.moxqeon.bukkit.item.core.item

import com.google.gson.JsonArray
import com.google.gson.JsonObject
import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.inventory.ItemStack
import org.moxqeon.bukkit.api.Constructor
import org.moxqeon.bukkit.api.manager.SimpleManager
import org.moxqeon.bukkit.item.ItemX
import org.moxqeon.bukkit.item.api.item.ICustomItemType
import org.moxqeon.bukkit.item.api.item.IItemGenerator
import org.moxqeon.bukkit.item.api.model.IModel
import org.moxqeon.bukkit.item.core.model.Model
import org.moxqeon.bukkit.item.feature.tool.CustomToolMaterial
import org.moxqeon.bukkit.item.feature.tool.CustomToolType
import org.moxqeon.bukkit.util.write
import org.moxqeon.ext.createDir
import org.moxqeon.ext.existingFiles
import org.moxqeon.ext.subFile
import org.moxqeon.module.zip.ZipContainer
import org.moxqeon.util.JsonUtil
import java.nio.charset.StandardCharsets

class ItemManager(private val itemX: ItemX) {
    val itemFolder = itemX.dataFolder.subFile("items")
    val packFolder = itemX.dataFolder.subFile("pack")
    val mcmetaFile = packFolder.subFile("pack.mcmeta")
    val assetsFolder = packFolder.subFile("assets")
    val itemXItemsFolder = assetsFolder.subFile("itemx").subFile("models").subFile("item")
    val exampleItemFile = itemFolder.subFile("example_item.yml")
    val typeManager: SimpleManager<ICustomItemType> = object : SimpleManager<ICustomItemType>() {}
    val generationManager: SimpleManager<IItemGenerator> = object : SimpleManager<IItemGenerator>() {}
    private val modelMap = mutableMapOf<Material, MutableList<IModel>>()
    private val toolMaterialManager: SimpleManager<CustomToolMaterial> = object : SimpleManager<CustomToolMaterial>() {}
    private val toolTypeManager: SimpleManager<CustomToolType> = object : SimpleManager<CustomToolType>() {}

    init {
        itemFolder.createDir()
        if (!exampleItemFile.exists()) itemX.write("items/example_item.yml", exampleItemFile)
        packFolder.createDir()
        itemX.write("pack.mcmeta", mcmetaFile, StandardCharsets.UTF_8)
        assetsFolder.createDir()
        itemXItemsFolder.createDir()
        for (i in 0 until 10) {
            val fileName = "block_crack_$i.json"
            itemX.write("models/item/$fileName", itemXItemsFolder.subFile(fileName))
        }
        itemX.write("models/item/example_item.json", itemXItemsFolder.subFile("example_item.json"))
        itemX.write("models/item/example_block.json", itemXItemsFolder.subFile("example_block.json"))
    }

    fun generateDefaults() {
        for (i in 0 until 10) {
            val model = Model(Material.PAPER, nextModelData(Material.PAPER), "item/block_crack_$i")

            val itemStack = ItemStack(model.vanilla)
            itemStack.editMeta { meta ->
                meta.setCustomModelData(model.modelData)
            }
            val customItemType = CustomItemType(
                itemX, model, Constructor.namespacedKey(ItemX.NAMESPACE, "block_crack_$i"), itemStack, null, null
            )
            typeManager.register(customItemType)
            registerModel(model)
        }
    }

    fun importItems() {
        for (file in itemFolder.existingFiles(".yml")) {
            val yamlConfig = YamlConfiguration.loadConfiguration(file)
            for (itemKey in yamlConfig.getKeys(false)) {
                val itemConfig = yamlConfig.getConfigurationSection(itemKey)!!
                val itemType = CustomItemType(itemX, itemConfig)
                typeManager.register(itemType)
            }
        }
    }

    fun nextModelData(vanilla: Material): Int {
        if (!modelMap.contains(vanilla)) modelMap[vanilla] = mutableListOf()
        return modelMap[vanilla]!!.size + 1
    }

    fun registerModel(model: IModel) {
        modelMap[model.vanilla]!!.add(model)
    }

    fun getModel(vanilla: Material, index: Int): IModel? {
        if (modelMap.contains(vanilla)) {
            val models = modelMap[vanilla]!!
            if (models.size > index) {
                return models[index]
            }
        }
        return null
    }

    fun registerToolMaterial(toolMaterial: CustomToolMaterial) {
        toolMaterialManager.register(toolMaterial)
    }

    fun getToolMaterial(key: NamespacedKey): CustomToolMaterial? = toolMaterialManager.get(key)

    fun registerToolType(toolType: CustomToolType) {
        toolTypeManager.register(toolType)
    }

    fun getToolType(key: NamespacedKey): CustomToolType? = toolTypeManager.get(key)

    fun getItem(key: NamespacedKey): ICustomItemType? = typeManager.get(key)

    fun generateResourcePack() {
        val zipContainer = ZipContainer(itemX.dataFolder.subFile("Pack.zip"))
//        val itemXDir = "/assets/itemx/"
        val minecraftDir = "assets/minecraft/models/item/"
        zipContainer.createFolder(minecraftDir)
        for (entry in modelMap) {
            val material = entry.key
            val materialKey = material.toString().lowercase()
            val path = "$minecraftDir${materialKey}.json"
            val modelJson = JsonUtil.parse(
                String(
                    itemX.getResource(path)!!.readAllBytes(), StandardCharsets.UTF_8
                )
            ).asJsonObject
            val overrides = JsonArray()
            for (model in entry.value) {
                val overrideJson = JsonObject()
                val cmdJson = JsonObject()
                cmdJson.addProperty("custom_model_data", model.modelData)
                overrideJson.add("predicate", cmdJson)
                overrideJson.addProperty("model", "itemx:${model.modelPath}")
                overrides.add(overrideJson)
            }
            modelJson.add("overrides", overrides)
            val content = JsonUtil.toPrettyString(modelJson).toByteArray(StandardCharsets.UTF_8)
            zipContainer.compressContent("$materialKey.json", content)
        }
        zipContainer.pointer = "/"
        for (file in packFolder.existingFiles()) {
            if (file.isDirectory) zipContainer.compressFolder(file)
            else zipContainer.compressFile(file)
        }
        zipContainer.close()
    }
}
