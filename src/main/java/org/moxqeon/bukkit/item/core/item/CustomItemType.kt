package org.moxqeon.bukkit.item.core.item

import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.configuration.ConfigurationSection
import org.bukkit.inventory.ItemFlag
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.PotionMeta
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType
import org.moxqeon.bukkit.api.Constructor
import org.moxqeon.bukkit.item.ItemX
import org.moxqeon.bukkit.item.api.item.ICustomItemType
import org.moxqeon.bukkit.item.api.model.IModel
import org.moxqeon.bukkit.item.api.vanilla.IToolMaterial
import org.moxqeon.bukkit.item.api.vanilla.IToolType
import org.moxqeon.bukkit.item.core.block.CustomBlockType
import org.moxqeon.bukkit.item.core.model.Model
import org.moxqeon.bukkit.item.feature.tool.VanillaToolMaterial
import org.moxqeon.bukkit.item.feature.tool.VanillaToolType
import org.moxqeon.bukkit.util.isPotion
import org.moxqeon.util.toFormatted

@Suppress("DEPRECATION")
class CustomItemType : ICustomItemType {
    private val itemManager: ItemManager
    override val defaultModel: IModel
    override val vanilla: Material
    private val key: NamespacedKey
    override val defaultItem: ItemStack
        get() = itemManager.generationManager[key]?.generate(field) ?: field
    override val toolType: IToolType?
    override val toolMaterial: IToolMaterial?

    constructor(
        itemX: ItemX,
        config: ConfigurationSection,
    ) {
        this.itemManager = itemX.itemManager
        key = Constructor.namespacedKey(ItemX.NAMESPACE, config.name, true)
        vanilla = Material.valueOf(config.getString("Type", "PAPER")!!)
        val modelData = itemManager.nextModelData(vanilla)
        val modelPath = config.getString("ModelPath")!!
        defaultModel = Model(vanilla, modelData, modelPath)
        itemManager.registerModel(defaultModel)
        defaultItem = ItemStack(vanilla)
        val meta = defaultItem.itemMeta.apply {
            setCustomModelData(modelData)
        }
        val displayName: String? = config.getString("DisplayName")
        displayName?.let { meta.setDisplayName(displayName.toFormatted()) }
        val lore: MutableList<String> = config.getStringList("Lore")
        if (!lore.isEmpty()) meta.lore = lore.toFormatted()
        val itemFlags = config.getStringList("ItemFlags")
        if (!itemFlags.isEmpty()) itemFlags.forEach { str -> meta.addItemFlags(ItemFlag.valueOf(str.uppercase())) }
        val options = config.getConfigurationSection("Options")
        if (options != null) {
            if (vanilla.isPotion()) {
                val potionConfig = options.getConfigurationSection("Potion")
                if (potionConfig != null) {
                    val type = PotionEffectType.getByName(potionConfig.getString("Type")!!)!!
                    val duration = potionConfig.getInt("Duration")
                    val level = potionConfig.getInt("Level", 1)
                    val ambient = potionConfig.getBoolean("Ambient", false)
                    val particles = potionConfig.getBoolean("Particles", false)
                    val icon = potionConfig.getBoolean("Icon", false)
                    val potionEffect = PotionEffect(type, duration, level, ambient, particles, icon)
                    (meta as PotionMeta).addCustomEffect(potionEffect, true)
                }
            }
            val toolTypeKey = options.getString("ToolType")
            toolType = if (toolTypeKey != null) {
                if (IToolType.isVanillaName(toolTypeKey))
                    VanillaToolType.valueOf(toolTypeKey.uppercase())
                else itemManager.getToolType(Constructor.namespacedKey(ItemX.NAMESPACE, toolTypeKey, true))
            } else {
                null
            }
            val toolMaterialKey = options.getString("ToolMaterial")
            toolMaterial = if (toolMaterialKey != null) {
                if (IToolMaterial.isVanillaName(toolMaterialKey))
                    VanillaToolMaterial.valueOf(toolMaterialKey.uppercase())
                else itemManager.getToolMaterial(Constructor.namespacedKey(ItemX.NAMESPACE, toolMaterialKey, true))
            } else {
                null
            }
            val customBlockConfig = options.getConfigurationSection("Block")
            if (customBlockConfig != null) {
                val blockType = CustomBlockType(itemX, this, customBlockConfig)
                itemX.blockManager.register(blockType)
            }
        } else {
            toolType = null
            toolMaterial = null
        }
        defaultItem.itemMeta = meta
    }

    constructor(
        itemX: ItemX,
        defaultModel: IModel,
        key: NamespacedKey,
        defaultItem: ItemStack,
        toolType: IToolType?,
        toolMaterial: IToolMaterial?,
    ) {

        this.itemManager = itemX.itemManager
        this.defaultModel = defaultModel
        this.vanilla = defaultModel.vanilla
        this.key = key
        this.defaultItem = defaultItem
        this.toolType = toolType
        this.toolMaterial = toolMaterial
    }

    override fun getKey() = key
}