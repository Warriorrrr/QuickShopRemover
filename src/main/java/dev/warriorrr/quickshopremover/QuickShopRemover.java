package dev.warriorrr.quickshopremover;

import org.bukkit.plugin.java.JavaPlugin;

import dev.warriorrr.quickshopremover.command.ClearWildernessShopsCommand;
import dev.warriorrr.quickshopremover.command.RemoveCommand;

public class QuickShopRemover extends JavaPlugin {
    public static String prefix = "[QuickShopRemover] ";

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(new TownyListener(this), this);
        getCommand("removeshop").setExecutor(new RemoveCommand());
        getCommand("clearwildernessshops").setExecutor(new ClearWildernessShopsCommand());
    }

    @Override
    public void onDisable() {
        System.out.println(prefix + "Disabling...");
    }
}
