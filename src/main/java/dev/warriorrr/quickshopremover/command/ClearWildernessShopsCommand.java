package dev.warriorrr.quickshopremover.command;

import java.util.List;

import com.palmergames.bukkit.towny.TownyAPI;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.maxgamer.quickshop.api.QuickShopAPI;
import org.maxgamer.quickshop.shop.Shop;

import dev.warriorrr.quickshopremover.QuickShopRemover;

public class ClearWildernessShopsCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            ((Player) sender).sendMessage("§cThis command can only be used by console.");
            return false;
        } else {
            parseClearWildernessCommand();
            return true;
        }
    }
    public void parseClearWildernessCommand() {
        System.out.println(QuickShopRemover.prefix + "Starting removal of wilderness shops...");
        int removedShops = 0;
        List<Shop> shops = QuickShopAPI.getShopAPI().getAllShops();
        TownyAPI townyAPI = TownyAPI.getInstance();
        for (Shop shop : shops) {
            if (townyAPI.isWilderness(shop.getLocation())) {
                shop.delete();
                removedShops += 1;
            }
        }
        System.out.println(QuickShopRemover.prefix + "Found and removed " + removedShops + " shop(s)");
    }
}