package org.moxqeon.bukkit.item.api.vanilla

import org.bukkit.Keyed
import org.bukkit.NamespacedKey
import java.util.*


interface IToolMaterial : Keyed {
    override fun getKey(): NamespacedKey
    val diggingLevel: Int

    val diggingSpeed: Double

    val maxDurability: Int
    companion object{
        @JvmStatic
        fun isVanillaName(tagName: String): Boolean {
            return when (tagName.uppercase()) {
                "WOOD", "STONE", "IRON", "GOLD", "DIAMOND", "NETHERITE" -> true
                else -> false
            }
        }


    }
}