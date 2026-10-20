package org.moxqeon.bukkit.item;

import com.google.common.base.Preconditions;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.moxqeon.bukkit.item.core.block.BlockManager;
import org.moxqeon.bukkit.item.core.item.ItemManager;
import org.moxqeon.bukkit.item.feature.tool.DiggingManager;
import org.moxqeon.bukkit.item.listener.consume.Consumable;

public final class ItemX extends JavaPlugin {
    public static final String NAMESPACE = "itemx";
    private static ItemX instance;
    private ItemManager itemManager;
    private BlockManager blockManager;
    private DiggingManager diggingManager;
    private Consumable.ConsumableManager consumableManager;

    public static ItemX instance() {
        return Preconditions.checkNotNull(instance);
    }

    @Override
    public void onEnable() {
        Preconditions.checkState(instance == null, "Plugin shouldn't be enabled multiple times");
        instance = this;
        itemManager = new ItemManager(this);
        blockManager = new BlockManager(this);
//        diggingManager = new DiggingManager(this);
        consumableManager = new Consumable.ConsumableManager(itemManager);
        itemManager.generateDefaults();
        itemManager.importItems();
        itemManager.generateResourcePack();
//        diggingManager.start();
    }

    @Override
    public void onDisable() {
        Preconditions.checkState((instance != null), "Plugin is not enabled");
        instance = null;
    }

    @NotNull
    public ItemManager getItemManager() {
        return itemManager;
    }

    @NotNull
    public BlockManager getBlockManager() {
        return blockManager;
    }


    public DiggingManager getDiggingManager() {
        return diggingManager;
    }

    public Consumable.ConsumableManager getConsumableManager() {
        return consumableManager;
    }
}
