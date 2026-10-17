package org.moxqeon.bukkit.item.core.block

import de.tr7zw.nbtapi.NBTCompound
import org.bukkit.block.Block
import org.bukkit.block.BlockFace
import org.moxqeon.bukkit.api.SlotType
import org.moxqeon.bukkit.item.api.vanilla.IToolType
import org.moxqeon.bukkit.item.core.item.CustomItemType
import org.moxqeon.bukkit.item.core.model.Model
import java.util.function.Consumer

class CustomInventoryBlockType(
    itemType: CustomItemType,
    model: Model,
    hardness: Float,
    isSolid: Boolean,
    diggingLevel: Int,
    bestTool: IToolType
) : CustomBlockType(itemType, model, hardness, isSolid, diggingLevel, bestTool) {
    override fun place(
        slotType: SlotType,
        against: Block,
        direction: BlockFace,
        consumer: Consumer<NBTCompound>
    ): NBTCompound {
        val compound = super.place(slotType, against, direction, consumer)
        val inventoryData = compound.addCompound("Inventory")
        inventoryData.setItemStackArray("Items", arrayOf())
        return compound
    }
}