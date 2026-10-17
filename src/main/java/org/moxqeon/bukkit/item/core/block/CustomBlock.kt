package org.moxqeon.bukkit.item.core.block

import de.tr7zw.nbtapi.NBTBlock
import org.bukkit.block.Block
import org.bukkit.block.BlockFace
import org.moxqeon.bukkit.api.Constructor
import org.moxqeon.bukkit.item.ItemX
import org.moxqeon.bukkit.item.api.block.ICustomBlock
import org.moxqeon.bukkit.item.api.block.ICustomBlockType

open class CustomBlock(val itemX: ItemX, nbtBlock: NBTBlock) : ICustomBlock {
    override val blockType: ICustomBlockType =
        itemX.blockManager.get(Constructor.namespacedKey(ItemX.NAMESPACE, nbtBlock.data.getString("BlockType")))
    override val facing: BlockFace = BlockFace.valueOf(nbtBlock.data.getString("Facing"))

    companion object {
        @JvmStatic
        fun fromBukkit(block: Block): CustomBlock? {
            val nbtBlock = NBTBlock(block)
            return if (nbtBlock.data.hasTag("BlockType")) CustomBlock(ItemX.instance(), nbtBlock) else null
        }
    }
}