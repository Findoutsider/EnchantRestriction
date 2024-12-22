package com.enchantRestriction;

import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

public class PlCommand implements CommandExecutor {
    public  final String VERSION = "1.0";
    private final Plugin plugin;

    public PlCommand(Plugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, org.bukkit.command.@NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if (s.equalsIgnoreCase("enchantrestriction") || s.equalsIgnoreCase("er")) {
            if (args.length == 1 && args[0].equalsIgnoreCase("reload")) {
                if (sender.hasPermission("enchantrestriction.reload") || sender.isOp()) {
                    reloadConfig();
                    sender.sendMessage("[EnchantRestriction] 配置已重载");
                    return true;
                } else {
                    sender.sendMessage("你没有权限执行命令");
                    return false;
                }
            } else {
                if (sender.isOp()) {
                    sender.sendMessage("[EnchantRestriction] " + VERSION);
                    sender.sendMessage("输入 /enchantrestriction reload 重载配置文件");
                    return true;
                } else sender.sendMessage("你没有权限执行命令"); return false;
            }
        }
        return false;
    }

    private void reloadConfig() {
        plugin.reloadConfig();
        FileConfiguration config = plugin.getConfig();
        EnchantListener enchantListener = new EnchantListener(config);

        plugin.getServer().getPluginManager().registerEvents(enchantListener, plugin);
    }
}
