package dev.warriorrr.quickshopremover;

import org.bukkit.plugin.java.JavaPlugin;

import dev.warriorrr.quickshopremover.command.QuickShopRemoverCommand;

public class QuickShopRemover extends JavaPlugin {
    public static String prefix = "[QuickShopRemover] ";

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(new TownyListener(this), this);
        getCommand("quickshopremover").setExecutor(new QuickShopRemoverCommand());
    }

    @Override
    public void onDisable() {
        System.out.println(prefix + "Disabling...");
    }
}
