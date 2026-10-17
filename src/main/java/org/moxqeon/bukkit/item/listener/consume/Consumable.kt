package org.moxqeon.bukkit.item.listener.consume

import org.bukkit.Keyed
import org.bukkit.NamespacedKey
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerItemConsumeEvent
import org.moxqeon.api.Accessor
import org.moxqeon.bukkit.api.manager.SimpleManager
import org.moxqeon.bukkit.item.api.listener.ItemListener
import org.moxqeon.bukkit.item.core.item.CustomItem
import org.moxqeon.bukkit.item.core.item.CustomItemType
import org.moxqeon.bukkit.item.core.item.ItemManager

abstract class Consumable(
    val itemType: CustomItemType
) : ItemListener<PlayerItemConsumeEvent>, Keyed {

    override fun getKey(): NamespacedKey {
        return itemType.key
    }

    class ConsumableManager(override val access: ItemManager) : SimpleManager<Consumable>(), Listener,
        Accessor<ItemManager> {

        @EventHandler
        fun onPlayerItemConsume(event: PlayerItemConsumeEvent) {
            val item = event.item
            val customItem = CustomItem.fromBukkit(item) ?: return
            val consumable = get(customItem.itemType.key) ?: return
            consumable.handle(event)
        }
    }
}