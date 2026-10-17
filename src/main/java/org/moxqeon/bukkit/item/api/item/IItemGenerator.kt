package org.moxqeon.bukkit.item.api.item

import org.bukkit.Keyed
import org.bukkit.NamespacedKey
import org.bukkit.inventory.ItemStack

interface IItemGenerator : Keyed {
    fun generate(item: ItemStack): ItemStack
    override fun getKey(): NamespacedKey
}