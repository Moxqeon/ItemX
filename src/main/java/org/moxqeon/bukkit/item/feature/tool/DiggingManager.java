package org.moxqeon.bukkit.item.feature.tool;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.wrappers.BlockPosition;
import com.comphenix.protocol.wrappers.EnumWrappers;
import net.kyori.adventure.text.Component;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Entity;
import org.bukkit.entity.ItemFrame;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;
import org.moxqeon.bukkit.api.Constructor;
import org.moxqeon.bukkit.item.ItemX;
import org.moxqeon.bukkit.item.core.block.CustomBlock;
import org.moxqeon.bukkit.item.protocol.DiggingProgressPacket;
import org.moxqeon.bukkit.util.PlayerUtil;

public class DiggingManager {
    private static final NamespacedKey DIGGING_KEY = Constructor.namespacedKey(ItemX.NAMESPACE, "digging");
    private final ItemX itemX;
    private final ProtocolManager pm;

    public DiggingManager(@NotNull ItemX itemX) {
        this.itemX = itemX;
        pm = ProtocolLibrary.getProtocolManager();
    }

    public void start() {
        pm.addPacketListener(new PacketAdapter(itemX, PacketType.Play.Client.BLOCK_DIG) {
            @Override
            public void onPacketReceiving(PacketEvent event) {
                Player player = event.getPlayer();
                PacketContainer packet = event.getPacket();
                EnumWrappers.Direction direction = packet.getDirections().read(0);
                EnumWrappers.PlayerDigType digType = packet.getPlayerDigTypes().read(0);
                player.sendMessage(Component.text(digType.name()));
                BlockPosition blockPosition = packet.getBlockPositionModifier().read(0);
                switch (digType) {
                    case START_DESTROY_BLOCK -> {
                        player.getPersistentDataContainer().set(DIGGING_KEY, PersistentDataType.BOOLEAN, true);
                    }
                    case ABORT_DESTROY_BLOCK -> {
                        Block targetBlock = PlayerUtil.targetBlock(player);
                        if (targetBlock == null) break;
                        if (new BlockPosition(targetBlock.getLocation().toVector()).equals(blockPosition)) {
                            player.getPersistentDataContainer().set(DIGGING_KEY, PersistentDataType.BOOLEAN, false);
                        }
                    }
                    case STOP_DESTROY_BLOCK, SWAP_HELD_ITEMS, DROP_ITEM, RELEASE_USE_ITEM -> {
                        player.getPersistentDataContainer().set(DIGGING_KEY, PersistentDataType.BOOLEAN, false);
                    }
                }

            }
        });
    }

    public void sendDiggingProgress(@NotNull Player player, @NotNull Location location, int progress) {
        if (player.getWorld() != location.getWorld()) return;
//        Block block = location.getBlock();
        Material blockType = location.getBlock().getType();
        if (blockType != Material.BARRIER) {
            DiggingProgressPacket packet = new DiggingProgressPacket(location.toVector(), progress);
            pm.sendServerPacket(player, packet);
        } else {
            CustomBlock customBlock = CustomBlock.fromBukkit(location.getBlock());
            if (customBlock == null) return;
            BlockFace facing = customBlock.getFacing();
            ItemFrame animationHolder = null;
            for (Entity entity : location.getChunk().getEntities()) {
                if (entity instanceof ItemFrame itemFrame && itemFrame.getLocation().toBlockLocation().toVector().equals(location.toBlockLocation().toVector())) {
                    if (itemFrame.getFacing() != facing) {
                        animationHolder = itemFrame;
                        break;
                    }
                }
            }
            if (animationHolder == null) {
                itemX.getLogger().info("Animation Holder not found, creating...");
                animationHolder = location.getWorld().spawn(location, ItemFrame.class, CreatureSpawnEvent.SpawnReason.CUSTOM, itemFrame -> {
                    if (facing == BlockFace.UP) itemFrame.setFacingDirection(BlockFace.DOWN, true);
                    else itemFrame.setFacingDirection(BlockFace.UP, true);
                    itemFrame.setFixed(true);
                    itemFrame.setVisible(false);
                });
            }
            if (progress > -1)
                animationHolder.setItem(itemX.getItemManager().getItem(Constructor.namespacedKey(ItemX.NAMESPACE, "block_crack_" + progress)).getDefaultItem(), false);
            else
                animationHolder.remove();
        }
    }
}
