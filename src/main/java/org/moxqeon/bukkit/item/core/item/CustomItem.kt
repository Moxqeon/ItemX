package org.moxqeon.bukkit.item.core.item

import de.tr7zw.nbtapi.NBTItem
import org.bukkit.inventory.ItemStack
import org.moxqeon.bukkit.api.Constructor
import org.moxqeon.bukkit.item.ItemX
import org.moxqeon.bukkit.item.api.item.ICustomItem
import org.moxqeon.bukkit.item.api.item.ICustomItemType
import org.moxqeon.bukkit.item.api.model.IModel

open class CustomItem(val itemX: ItemX, nbtItem: NBTItem) : ICustomItem {
    final override val itemType: ICustomItemType =
        itemX.itemManager.typeManager.get(
            Constructor.namespacedKey(
                ItemX.NAMESPACE,
                nbtItem.getString("ItemID")!!
            )
        )
    var item: ItemStack = nbtItem.item
    override var model: IModel = itemX.itemManager.getModel(item.type, item.itemMeta.customModelData)!!
        set(model) {
            if (itemType.matchesModel(model)) {
                field = model
                val meta = item.itemMeta
                meta.setCustomModelData(model.modelData)
                item.setItemMeta(meta)
            }
        }

    companion object {
        @JvmStatic
        fun fromBukkit(item: ItemStack): ICustomItem? {
            val nbtItem = NBTItem(item)
            return if (nbtItem.hasTag("ItemID")) CustomItem(ItemX.instance(), nbtItem) else null
        }
    }


    fun regulateModel() {
        model = itemType.defaultModel
        val meta = item.itemMeta
        meta.setCustomModelData(model.modelData)
        item.setItemMeta(meta)
    }
}