package org.moxqeon.bukkit.item.feature.tool

import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.moxqeon.bukkit.item.api.vanilla.IToolMaterial

class VanillaToolMaterial(
    key: String,
    override val diggingLevel: Int,
    override val diggingSpeed: Double,
    override val maxDurability: Int,
    vararg materials: Material
) : IToolMaterial {
    private val key: NamespacedKey = NamespacedKey.minecraft(key)
    override fun getKey() = key
    val materials: MutableSet<Material> = materials.toMutableSet()

    fun contains(material: Material) = materials.contains(material)

    companion object {
        @JvmField
        val WOOD = VanillaToolMaterial(
            "wood",
            1,
            2.0,
            59,
            Material.WOODEN_PICKAXE,
            Material.WOODEN_SHOVEL,
            Material.WOODEN_AXE,
            Material.WOODEN_HOE
        )

        @JvmField
        val STONE = VanillaToolMaterial(
            "stone",
            2,
            4.0,
            131,
            Material.STONE_AXE,
            Material.STONE_PICKAXE,
            Material.STONE_HOE,
            Material.STONE_SHOVEL,
            Material.STONE_SWORD
        )

        @JvmField
        val IRON = VanillaToolMaterial(
            "iron",
            3,
            6.0,
            250,
            Material.IRON_AXE,
            Material.IRON_PICKAXE,
            Material.IRON_HOE,
            Material.IRON_SHOVEL,
            Material.IRON_SWORD
        )

        @JvmField
        val GOLD = VanillaToolMaterial(
            "gold",
            1,
            12.0,
            32,
            Material.GOLDEN_AXE,
            Material.GOLDEN_PICKAXE,
            Material.GOLDEN_HOE,
            Material.GOLDEN_SHOVEL,
            Material.GOLDEN_SWORD
        )

        @JvmField
        val DIAMOND = VanillaToolMaterial(
            "diamond",
            4,
            8.0,
            1561,
            Material.DIAMOND_AXE,
            Material.DIAMOND_PICKAXE,
            Material.DIAMOND_HOE,
            Material.DIAMOND_SHOVEL,
            Material.DIAMOND_SWORD
        )

        @JvmField
        val NETHERITE = VanillaToolMaterial(
            "netherite",
            4,
            9.0,
            2031,
            Material.NETHERITE_AXE,
            Material.NETHERITE_PICKAXE,
            Material.NETHERITE_HOE,
            Material.NETHERITE_SHOVEL,
            Material.NETHERITE_SWORD
        )

        @JvmStatic
        fun valueOf(name: String): VanillaToolMaterial {
            return when (name) {
                "WOOD" -> WOOD
                "STONE" -> STONE
                "IRON" -> IRON
                "GOLD" -> GOLD
                "DIAMOND" -> DIAMOND
                "NETHERITE" -> NETHERITE
                else -> throw IllegalArgumentException("Vanilla tool material $name not found")
            }
        }

        @JvmStatic
        fun values(): Array<VanillaToolMaterial> {
            return arrayOf(WOOD, STONE, IRON, GOLD, DIAMOND, NETHERITE)
        }
    }
}