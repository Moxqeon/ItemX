package org.moxqeon.bukkit.item.core.model

import org.bukkit.Material
import org.moxqeon.bukkit.item.api.model.IModel

class Model(
    override val vanilla: Material,
    override val modelData: Int,
    override val modelPath: String
) : IModel