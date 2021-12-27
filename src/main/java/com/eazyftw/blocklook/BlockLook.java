package com.eazyftw.blocklook;

import com.eazyftw.blocklook.command.BlockLookCommand;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

public final class BlockLook extends JavaPlugin implements Listener {

    private BlockLookTask task;

    @Override
    public void onEnable() {
        this.saveDefaultConfig();

        getCommand("blocklook").setExecutor(new BlockLookCommand(this));
        getCommand("blocklook").setTabCompleter(new BlockLookCommand(this));

        getServer().getPluginManager().registerEvents(this, this);
        this.task = new BlockLookTask(this);
    }

    @Override
    public void onDisable() {
        this.task.stopTask();
    }

    public String getPrefix() {
        return getConfig().getString("Prefix");
    }

    public String getMessage() {
        return getConfig().getString("Message");
    }

    public long getCheckTime() {
        return getConfig().getLong("TargetBlockCheck");
    }

    public int getDistance() {
        return getConfig().getInt("TargetDistance");
    }

    public boolean isDefaultOn() {
        return getConfig().getBoolean("DefaultOn");
    }

    public void reloadPlugin() {
        reloadConfig();
        task.restartTask();
    }

    public BlockLookTask getTask() {
        return task;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        if(isDefaultOn())
            getTask().addUUID(e.getPlayer().getUniqueId());
    }
}
