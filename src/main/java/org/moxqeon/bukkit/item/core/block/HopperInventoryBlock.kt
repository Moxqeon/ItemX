package org.moxqeon.bukkit.item.core.block

import de.tr7zw.nbtapi.NBTBlock
import de.tr7zw.nbtapi.NBTCompound
import org.bukkit.Bukkit
import org.bukkit.block.BlockFace
import org.bukkit.event.inventory.InventoryType
import org.bukkit.inventory.Inventory
import org.moxqeon.bukkit.item.ItemX
import org.moxqeon.bukkit.item.api.block.ICustomBlockType
import org.moxqeon.bukkit.item.api.block.IInventoryBlock

class HopperInventoryBlock(itemX: ItemX, nbtBlock: NBTBlock) : CustomInventoryBlock(itemX, nbtBlock) {
    override val blockType: ICustomBlockType
        get() = TODO("Not yet implemented")

}