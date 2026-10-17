package org.moxqeon.bukkit.item.api.item

import org.bukkit.Keyed
import org.moxqeon.bukkit.item.api.model.IModel


interface ICustomItem : Keyed {
    var model: IModel
    val itemType: ICustomItemType
    override fun getKey() = itemType.key
}