package com.enchantRestriction;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public final class EnchantRestriction extends JavaPlugin {

    @Override
    public void onEnable() {
        // 保存默认配置文件
        saveDefaultConfig();

        // 加载配置文件
        FileConfiguration config = getConfig();

        // 创建事件监听器实例并注册
        EnchantListener enchantListener = new EnchantListener(config);
        getServer().getPluginManager().registerEvents(enchantListener, this);

//        this.getCommand("enchantrestriction").setExecutor(new PlCommand(this));
//        this.getCommand("er").setExecutor(new PlCommand(this));

    }

    @Override
    public void onDisable() {

    }
}
