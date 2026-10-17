package org.moxqeon.bukkit.item.api.vanilla

import org.bukkit.Keyed
import org.bukkit.NamespacedKey
import org.moxqeon.bukkit.api.Constructor
import org.moxqeon.bukkit.item.ItemX
import org.moxqeon.bukkit.item.core.item.ItemManager
import org.moxqeon.bukkit.item.feature.tool.VanillaToolType
import java.util.*


interface IToolType : Keyed {
    override fun getKey(): NamespacedKey

    companion object {
        @JvmStatic
        fun isVanillaName(tagName: String): Boolean {
            return when (tagName.uppercase()) {
                "AXE", "PICKAXE", "HOE", "SHOVEL", "SWORD", "SHEARS" -> true
                else -> false
            }
        }

        @JvmStatic
        fun getByName(itemManager: ItemManager, tagName: String): IToolType? {
              return if (isVanillaName(tagName)) VanillaToolType.valueOf(tagName.uppercase())
            else itemManager.getToolType((Constructor.namespacedKey(ItemX.NAMESPACE, tagName, true)))
        }
    }
}