package org.moxqeon.bukkit.item.api.block

import de.tr7zw.nbtapi.NBTCompound
import org.bukkit.Keyed
import org.bukkit.block.BlockFace

interface ICustomBlock : Keyed {
    val blockType: ICustomBlockType
    override fun getKey() = blockType.key
}