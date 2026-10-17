package org.moxqeon.bukkit.item.feature.tool

import org.bukkit.NamespacedKey
import org.bukkit.configuration.ConfigurationSection
import org.moxqeon.api.Accessor
import org.moxqeon.bukkit.api.Constructor
import org.moxqeon.bukkit.item.ItemX
import org.moxqeon.bukkit.item.api.vanilla.IToolMaterial

class CustomToolMaterial(
    override val access: ItemX,
    private val key: NamespacedKey,
    override val diggingLevel: Int,
    override val diggingSpeed: Double,
    override val maxDurability: Int
) : IToolMaterial, Accessor<ItemX> {
    constructor(itemX: ItemX, config: ConfigurationSection) : this(
        itemX,
        Constructor.namespacedKey(ItemX.NAMESPACE, config.name, true),
        config.getInt("DiggingLevel", 1),
        config.getDouble("DiggingSpeed", 2.0),
        config.getInt("MaxDurability", 59)
    )

    override fun getKey() = key

}