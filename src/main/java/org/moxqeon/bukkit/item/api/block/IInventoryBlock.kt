package org.moxqeon.bukkit.item.api.block

import org.bukkit.entity.Player
import org.bukkit.event.block.Action
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.InventoryHolder


interface IInventoryBlock : IInteractableBlock, InventoryHolder {

    override fun interact(player: Player, action: Action): Boolean {
        if (action != Action.RIGHT_CLICK_BLOCK) return false
        player.openInventory(inventory)
        return true
    }
}