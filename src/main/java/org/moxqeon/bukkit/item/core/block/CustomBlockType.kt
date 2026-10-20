package org.moxqeon.bukkit.item.core.block

import com.google.common.base.Preconditions
import de.tr7zw.nbtapi.NBTBlock
import de.tr7zw.nbtapi.NBTCompound
import org.bukkit.Material
import org.bukkit.block.Block
import org.bukkit.block.BlockFace
import org.bukkit.configuration.ConfigurationSection
import org.bukkit.entity.ItemFrame
import org.bukkit.event.entity.CreatureSpawnEvent
import org.moxqeon.bukkit.api.SlotType
import org.moxqeon.bukkit.item.ItemX
import org.moxqeon.bukkit.item.api.block.ICustomBlockType
import org.moxqeon.bukkit.item.api.item.ICustomItemType
import org.moxqeon.bukkit.item.api.model.IModel
import org.moxqeon.bukkit.item.api.vanilla.IToolType
import org.moxqeon.bukkit.item.core.item.CustomItemType
import org.moxqeon.bukkit.item.core.model.Model
import java.util.function.Consumer

open class CustomBlockType : ICustomBlockType {
    final override val itemType: ICustomItemType
    final override val model: IModel
    final override val hardness: Float
    final override val isSolid: Boolean
    final override val diggingLevel: Int

    final override val bestTool: IToolType


    constructor(itemX: ItemX, itemType: ICustomItemType, config: ConfigurationSection) {
        this.itemType = itemType
        model = itemType.defaultModel
        hardness = config.getDouble("Hardness", 0.0).toFloat()
        isSolid = config.getBoolean("Solid", true)
        diggingLevel = config.getInt("DiggingLevel", 0)
        bestTool = IToolType.getByName(itemX.itemManager, config.getString("BestTool", "AXE")!!)!!
    }

    constructor(
        itemType: CustomItemType,
        model: Model,
        hardness: Float,
        isSolid: Boolean,
        diggingLevel: Int,
        bestTool: IToolType
    ) {
        this.itemType = itemType
        this.model = model
        this.hardness = hardness
        this.isSolid = isSolid
        this.diggingLevel = diggingLevel
        this.bestTool = bestTool
    }

    open fun place(slotType: SlotType, against: Block, direction: BlockFace, consumer: Consumer<NBTCompound>): NBTCompound {
        Preconditions.checkArgument(SlotType.HAND.contains(slotType))
        val toBeReplaced = if (against.isSolid) against.getRelative(direction) else against
        if (isSolid && !toBeReplaced.isSolid) toBeReplaced.type = Material.BARRIER
        against.world.spawn(
            toBeReplaced.location, ItemFrame::class.java, CreatureSpawnEvent.SpawnReason.CUSTOM
        ) { entity ->
            entity.setFacingDirection(direction, true)
            entity.isFixed = true
            entity.isVisible = false
            entity.setItem(itemType.defaultItem, false)
        }
        val nbtCompound = NBTBlock(toBeReplaced).data
        nbtCompound.setString("Facing", direction.toString())
        nbtCompound.setString("BlockType", key.key)
        consumer.accept(nbtCompound)
        return nbtCompound
    }


}