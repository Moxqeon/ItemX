package org.moxqeon.bukkit.item.protocol

import com.comphenix.protocol.PacketType
import com.comphenix.protocol.events.PacketContainer
import com.comphenix.protocol.wrappers.EnumWrappers
import com.comphenix.protocol.wrappers.MinecraftKey


class StopSoundPacket(soundName: String, soundCategory: EnumWrappers.SoundCategory) :
    PacketContainer(PacketType.Play.Server.STOP_SOUND) {
    init {
        minecraftKeys.write(
            0,
            MinecraftKey(if (soundName.startsWith("minecraft:")) soundName.substring(10) else soundName)
        )
        soundCategories.write(0, soundCategory)
    }
}