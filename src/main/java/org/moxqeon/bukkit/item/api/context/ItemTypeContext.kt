package org.moxqeon.bukkit.item.api.context

import org.bukkit.plugin.Plugin
import org.moxqeon.bukkit.api.context.LanguageContext
import org.moxqeon.bukkit.item.api.item.ICustomItemType

class ItemTypeContext(plugin: Plugin, key: String, val itemType: ICustomItemType) : LanguageContext(plugin, key) {
    override fun get(p0: String): Any? {
        if (p0.equals("itemType", true))
            return itemType
        return null
    }
}