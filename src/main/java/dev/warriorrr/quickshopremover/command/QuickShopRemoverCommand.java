package dev.warriorrr.quickshopremover.command;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.palmergames.bukkit.towny.TownyAPI;
import com.palmergames.bukkit.towny.object.Town;
import com.palmergames.bukkit.towny.utils.NameUtil;

import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.util.BlockIterator;
import org.maxgamer.quickshop.api.QuickShopAPI;
import org.maxgamer.quickshop.shop.Shop;
import org.maxgamer.quickshop.util.MsgUtil;

import dev.warriorrr.quickshopremover.QuickShopRemover;

public class QuickShopRemoverCommand implements CommandExecutor, TabCompleter {
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        List<String> quickShopRemoverTabCompletes = Arrays.asList("remove", "clearwildernessshops");
        if (args.length == 1) {
            return NameUtil.filterByStart(quickShopRemoverTabCompletes, args[0]);
        } else
            return Collections.emptyList();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length > 0) {
            switch(args[0].toLowerCase()) {
                case "remove": {
                    if (!(sender instanceof Player)) {
                        sender.sendMessage("§cOnly players can use this command!");
                        return false;
                    } else {
                        parseRemoveCommand((Player) sender);
                        break;
                    }
                }
                case "clearwildernessshops": {
                    if (sender instanceof Player) {
                        sender.sendMessage("§cThis command can only be used by console.");
                        return false;
                    } else {
                        parseClearWildernessCommand();
                        break;
                    }
                }
                default: {
                    sender.sendMessage("§cInvalid arguments!");
                    return false;
                }
            }
            return true;
        } else {
            sender.sendMessage("§cInvalid arguments!");
            return false;
        }
    }

    public void parseRemoveCommand(Player player) {
        BlockIterator iterator = new BlockIterator(player, 10);

        while (iterator.hasNext()) {
            Block block = iterator.next();
            Shop shop = QuickShopAPI.getShopAPI().getShop(block.getLocation());
            
            if (shop == null)
                continue;
            
            TownyAPI townyAPI = TownyAPI.getInstance();
            Town town = townyAPI.getTown(block.getLocation());
            if (townyAPI.isWilderness(block)) {
                shop.delete();
                return;
            } else if (shop.getOwner().equals(player.getUniqueId())) {
                shop.delete();
                return;
            } else if (town != null) {
                if (!town.getMayor().isNPC())
                    if (town.getMayor().getPlayer().equals(player)) {
                        shop.delete();
                        return;
                    }
            }
            player.sendMessage(MsgUtil.getMessage("no-permission", player));
            return;
        }
        player.sendMessage(MsgUtil.getMessage("not-looking-at-shop", player));
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
