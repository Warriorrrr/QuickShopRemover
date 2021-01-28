package dev.warriorrr.quickshopremover.command;

import com.palmergames.bukkit.towny.TownyAPI;
import com.palmergames.bukkit.towny.object.Town;

import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.util.BlockIterator;
import org.maxgamer.quickshop.api.QuickShopAPI;
import org.maxgamer.quickshop.shop.Shop;
import org.maxgamer.quickshop.util.MsgUtil;

public class RemoveCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("§cOnly players can use this command.");
            return false;
        } else {
            parseRemoveCommand((Player) sender);
            return true;
        }
    }

    public void parseRemoveCommand(Player player) {
        BlockIterator iterator = new BlockIterator(player, 10);

        if (!iterator.hasNext()) {
            player.sendMessage(MsgUtil.getMessage("not-looking-at-shop", player));
            return;
        }

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
            } else if (town != null) {
                if (!town.getMayor().isNPC())
                    if (town.getMayor().getPlayer().equals(player)) {
                        shop.delete();
                        return;
                    }
                else if (shop.getOwner().equals(player.getUniqueId())) {
                    shop.delete();
                    return;
                }
            }
            player.sendMessage(MsgUtil.getMessage("no-permission", player));
        }
    }
}