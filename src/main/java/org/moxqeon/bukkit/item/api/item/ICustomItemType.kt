package org.moxqeon.bukkit.item.api.item

import org.bukkit.Keyed
import org.bukkit.Material
import org.bukkit.inventory.ItemStack
import org.moxqeon.bukkit.item.api.model.IModel
import org.moxqeon.bukkit.item.api.vanilla.IToolMaterial
import org.moxqeon.bukkit.item.api.vanilla.IToolType

interface ICustomItemType : Keyed {
    val defaultModel: IModel
    val vanilla: Material
    val defaultItem: ItemStack
    val toolType: IToolType?
    val toolMaterial: IToolMaterial?
    fun matchesModel(model: IModel) = model.vanilla == vanilla

}