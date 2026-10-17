package org.moxqeon.bukkit.item.api.model

import org.bukkit.Material

interface IModel {
    val vanilla: Material
    val modelData: Int
    val modelPath: String
}