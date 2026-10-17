package org.moxqeon.bukkit.item.api.listener

import org.bukkit.event.Event

interface ItemListener< E: Event> {
    fun handle(event:E)
}