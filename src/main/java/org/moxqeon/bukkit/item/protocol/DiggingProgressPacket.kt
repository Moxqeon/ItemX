package org.moxqeon.bukkit.item.protocol

import com.comphenix.protocol.PacketType
import com.comphenix.protocol.events.PacketContainer
import com.comphenix.protocol.wrappers.BlockPosition
import org.bukkit.util.Vector


class DiggingProgressPacket(vector: Vector, progress: Int) :
    PacketContainer(PacketType.Play.Server.BLOCK_BREAK_ANIMATION) {
    init {
        integers.write(0, vector.hashCode()).write(1, progress)
        blockPositionModifier.write(0, BlockPosition(vector))
    }
}