package org.moxqeon.bukkit.item.feature.tool

import org.bukkit.NamespacedKey
import org.bukkit.configuration.ConfigurationSection
import org.moxqeon.bukkit.api.Constructor
import org.moxqeon.bukkit.item.ItemX
import org.moxqeon.bukkit.item.api.vanilla.IToolType

class CustomToolType(private val key: NamespacedKey) : IToolType {
    override fun getKey() = key

    constructor(config: ConfigurationSection) : this(Constructor.namespacedKey(ItemX.NAMESPACE, config.name, true))
}
