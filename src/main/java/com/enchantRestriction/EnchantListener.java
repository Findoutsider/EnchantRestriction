package com.enchantRestriction;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.enchantment.EnchantItemEvent;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import static org.bukkit.Bukkit.getLogger;

public class EnchantListener implements Listener {

    private final Set<Enchantment> bannedEnchantments;

    public EnchantListener(FileConfiguration config) {
        bannedEnchantments = new HashSet<>();
        for (String enchantmentName : config.getStringList("removeOnEnchant")) {
            Enchantment enchantment = Enchantment.getByName(enchantmentName.toUpperCase());
            if (enchantment != null) {
                bannedEnchantments.add(enchantment);
            } else {
                getLogger().warning("Unknown enchantment: " + enchantmentName);
            }
        }
    }

    @EventHandler
    public void onEnchantItem(EnchantItemEvent event) {
        // 获取附魔台提供的所有魔咒
        Map<Enchantment, Integer> enchants = event.getEnchantsToAdd();

        // 使用迭代器安全地移除元素
        Iterator<Map.Entry<Enchantment, Integer>> iterator = enchants.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<Enchantment, Integer> entry = iterator.next();
            Enchantment enchantment = entry.getKey();
            if (bannedEnchantments.contains(enchantment)) {
                iterator.remove(); // 使用迭代器的remove方法
            }
        }

        // 如果所有魔咒都被移除，则取消附魔事件
        if (enchants.isEmpty()) {
            event.setCancelled(true);
        }
    }
}
