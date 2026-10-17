package org.moxqeon.bukkit.item.core.block

import de.tr7zw.nbtapi.NBTBlock
import de.tr7zw.nbtapi.NBTCompound
import de.tr7zw.nbtapi.NBTItem
import org.bukkit.Bukkit
import org.bukkit.event.inventory.InventoryType
import org.bukkit.inventory.Inventory
import org.moxqeon.bukkit.item.ItemX
import org.moxqeon.bukkit.item.api.block.IInventoryBlock

open class CustomInventoryBlock(itemX: ItemX, nbtBlock: NBTBlock) : IInventoryBlock, CustomBlock(itemX, nbtBlock) {
    val inventoryData: NBTCompound = nbtBlock.data.getCompound("Inventory")!!
    private val inventory: Inventory =
        Bukkit.createInventory(this, InventoryType.valueOf(inventoryData.getString("Type")!!))

    init {
        val items = inventoryData.getCompoundList("Items")!!
        for (itemNBT in items) {
            val itemCompound = itemNBT as NBTCompound
            val slot = itemCompound.getByte("Slot").toInt()
            inventory.setItem(slot, NBTItem.convertNBTtoItem(itemCompound)!!)
        }
    }

    fun save() {
        val items = inventoryData.getCompoundList("Items")!!
        for (i in 0 until inventory.size) {
            val inventoryItem = inventory.getItem(i) ?: continue
            val nbtContainer = NBTItem.convertItemtoNBT(inventoryItem)
            nbtContainer.setByte("Slot", i.toByte())
            items.addCompound(nbtContainer)
        }

    }

    override fun getInventory(): Inventory {
        return inventory
    }

}