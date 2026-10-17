package org.moxqeon.bukkit.item.feature.tool

import com.destroystokyo.paper.MaterialSetTag
import com.destroystokyo.paper.MaterialTags
import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.moxqeon.bukkit.item.api.vanilla.IToolType


class VanillaToolType : IToolType {
    private val key: NamespacedKey
    val materials: MutableSet<Material>

    constructor(key: String, vararg materials: Material) {
        this.key = NamespacedKey.minecraft(key)
        this.materials = materials.toMutableSet()
    }

    constructor(key: String, materialSetTag: MaterialSetTag) {
        this.key = NamespacedKey.minecraft(key)
        this.materials = materialSetTag.values
    }

    override fun getKey() = key

    fun contains(material: Material): Boolean = materials.contains(material)

    companion object {
        @JvmField
        val AXE: VanillaToolType = VanillaToolType("axe", MaterialTags.AXES)

        @JvmField
        val PICKAXE: VanillaToolType = VanillaToolType("pickaxe", MaterialTags.PICKAXES)

        @JvmField
        val HOE: VanillaToolType = VanillaToolType("hoe", MaterialTags.HOES)

        @JvmField
        val SHOVEL: VanillaToolType = VanillaToolType("shovel", MaterialTags.SHOVELS)

        @JvmField
        val SWORD: VanillaToolType = VanillaToolType("sword", MaterialTags.SWORDS)

        @JvmField
        val SHEARS: VanillaToolType = VanillaToolType("shears", Material.SHEARS)

        @JvmStatic
        fun valueOf(name: String): VanillaToolType {
            return when (name) {
                "AXE" -> AXE
                "PICKAXE" -> PICKAXE
                "HOE" -> HOE
                "SHOVEL" -> SHOVEL
                "SWORD" -> SWORD
                "SHEARS" -> SHEARS
                else -> throw IllegalArgumentException(String.format("Vanilla tool type %s not found", name))
            }
        }

        @JvmStatic
        fun values(): Array<VanillaToolType> {
            return arrayOf(AXE, PICKAXE, HOE, SHOVEL, SWORD, SHEARS)
        }
    }
}