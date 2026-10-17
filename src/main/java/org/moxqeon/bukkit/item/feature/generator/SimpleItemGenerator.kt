package org.moxqeon.bukkit.item.feature.generator

import org.bukkit.NamespacedKey
import org.moxqeon.bukkit.item.api.item.IItemGenerator

abstract class SimpleItemGenerator(private val key: NamespacedKey) : IItemGenerator {
    override fun getKey() = key
}