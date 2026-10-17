package org.moxqeon.bukkit.item.api.block

import org.bukkit.Keyed
import org.bukkit.NamespacedKey
import org.moxqeon.bukkit.item.api.item.ICustomItemType
import org.moxqeon.bukkit.item.api.model.IModel
import org.moxqeon.bukkit.item.api.vanilla.IToolType

interface ICustomBlockType : Keyed {
    val itemType: ICustomItemType
    val model: IModel
    val isSolid: Boolean
    val hardness: Float
    val diggingLevel: Int
    val bestTool: IToolType
    override fun getKey() = itemType.key
}