package com.eazyftw.blocklook;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.apache.commons.lang.WordUtils;
import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class BlockLookTask {

    private final BlockLook plugin;
    private final List<UUID> uuids;

    private BukkitTask task;

    public BlockLookTask(BlockLook plugin) {
        this.plugin = plugin;
        this.uuids = new ArrayList<>();

        startTask();
    }

    public void startTask() {
        this.task = plugin.getServer().getScheduler().runTaskTimer(plugin, () -> {
            for (UUID uuid : uuids) {
                Player p = Bukkit.getPlayer(uuid);

                if(p != null) {
                    Block block = p.getTargetBlockExact(plugin.getDistance());

                    if(block != null)
                        p.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(plugin.getMessage().replace("%block%", WordUtils.capitalize(block.getType().name().replace("_", "").toLowerCase()))));
                }
            }
        }, 0L, plugin.getCheckTime());
    }

    public void stopTask() {
        if(this.task != null && !this.task.isCancelled())
            this.task.cancel();
    }

    public void restartTask() {
        stopTask();
        startTask();
    }

    public void addUUID(UUID uuid) {
        if(!containsUUID(uuid))
            uuids.add(uuid);
    }

    public void removeUUID(UUID uuid) {
        uuids.remove(uuid);
    }

    public boolean toggleUUID(UUID uuid) {
        if(containsUUID(uuid)) {
            removeUUID(uuid);
            return false;
        } else {
            addUUID(uuid);
            return true;
        }
    }

    public boolean containsUUID(UUID uuid) {
        return uuids.contains(uuid);
    }
}
