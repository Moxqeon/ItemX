package org.moxqeon.bukkit.item.api.block

import org.bukkit.block.BlockFace

interface IFacingBlock: ICustomBlock {
    val facing: BlockFace
}