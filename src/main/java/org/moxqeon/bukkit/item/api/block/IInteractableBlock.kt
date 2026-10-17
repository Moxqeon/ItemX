package org.moxqeon.bukkit.item.api.block

import org.bukkit.entity.Player
import org.bukkit.event.block.Action

interface IInteractableBlock: ICustomBlock {
    fun interact(player: Player, action: Action): Boolean
}