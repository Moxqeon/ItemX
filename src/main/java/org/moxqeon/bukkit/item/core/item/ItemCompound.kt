package org.moxqeon.bukkit.item.core.item

import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.inventory.ItemStack
import org.moxqeon.api.compound.ParsableCompound
import org.moxqeon.bukkit.item.ItemX
import org.moxqeon.bukkit.util.PluginUtil

class ItemCompound : ParsableCompound<MutableList<ItemStack>> {
    private val itemX: ItemX

    @JvmOverloads
    constructor(itemX: ItemX, compound: String, split: String = DEFAULT_SPLIT) : super(compound, split) {
        this.itemX = itemX
    }

    override fun parse(): MutableList<ItemStack> {
        val nk = PluginUtil.pluginOrVanillaKey(itemX, list[0])
        if (nk.namespace == NamespacedKey.MINECRAFT) {
            val item = ItemStack(Material.valueOf(nk.key.uppercase()))
            item.amount = list[1].toInt()
            return mutableListOf(item)
        } else {
            val items = mutableListOf<ItemStack>()
            for (i in 0 until list.size) items.add(itemX.itemManager.typeManager[nk].defaultItem)
            return items
        }
    }

}