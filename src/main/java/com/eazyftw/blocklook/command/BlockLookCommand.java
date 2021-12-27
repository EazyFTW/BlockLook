package com.eazyftw.blocklook.command;

import com.eazyftw.blocklook.BlockLook;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BlockLookCommand implements CommandExecutor, TabExecutor {

    private final BlockLook plugin;

    public BlockLookCommand(BlockLook plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(args.length == 0) {
            if(sender instanceof Player) {
                boolean toggle = plugin.getTask().toggleUUID(((Player) sender).getUniqueId());

                sender.sendMessage(plugin.getPrefix() + (toggle ? "§aEnabled Block Look!" : "§cDisabled Block Look!"));
            } else {
                sender.sendMessage(plugin.getPrefix() + "§cOnly players can execute this command!");
            }
        } else if (args[0].equalsIgnoreCase("reload")) {
            if(sender.hasPermission("blocklook.reload")) {
                plugin.reloadPlugin();
                sender.sendMessage(plugin.getPrefix() + "§aSuccessfully reloaded the plugin!");
            } else {
                sender.sendMessage(plugin.getPrefix() + "§cInsufficient permissions!");
            }
        } else {
            sender.sendMessage(plugin.getPrefix() + "§cInvalid argument!");
        }

        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        return sender.hasPermission("blocklook.reload") && args.length > 0 && "reload".startsWith(args[0].toLowerCase()) ? Collections.singletonList("reload") : new ArrayList<>();
    }
}
