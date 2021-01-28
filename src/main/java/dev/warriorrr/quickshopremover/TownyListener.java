package dev.warriorrr.quickshopremover;

import java.util.Map;
import java.util.Map.Entry;
import java.util.List;

import com.palmergames.bukkit.towny.event.town.TownUnclaimEvent;
import com.palmergames.bukkit.towny.TownyUniverse;
import com.palmergames.bukkit.towny.TownyAPI;
import com.palmergames.bukkit.towny.event.PreNewDayEvent;

import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.maxgamer.quickshop.api.QuickShopAPI;
import org.maxgamer.quickshop.shop.Shop;

public class TownyListener implements Listener {

    @SuppressWarnings("unused")
    private final QuickShopRemover plugin;

    public TownyListener(QuickShopRemover testPlugin) {
        plugin = testPlugin;
    }

    @EventHandler
    public void onTownUnclaim(TownUnclaimEvent event) {
        Chunk unclaimedChunk = event.getWorldCoord().getBukkitWorld().getChunkAt(event.getWorldCoord().getX(), event.getWorldCoord().getZ());
        Map <Location,Shop> shopMap = QuickShopAPI.getShopAPI().getShop(unclaimedChunk);
        for (Entry<Location, Shop> shop : shopMap.entrySet()) {
            shop.getValue().delete();
        }
    }

    @EventHandler
    public void onNewDay(PreNewDayEvent event) {
        int removedShops = 0;
        List<Shop> shops = QuickShopAPI.getShopAPI().getAllShops();
        TownyUniverse towny = TownyUniverse.getInstance();
        for (Shop shop : shops) {
            if (TownyAPI.getInstance().isWilderness(shop.getLocation())) {
                shop.delete();
                removedShops += 1;
            }
            else if (towny.getResident(shop.getOwner()) == null) {
                shop.delete();
                removedShops += 1;
            }
        }
        System.out.println(QuickShopRemover.prefix + "Removed " + removedShops + " shop(s) during latest Towny NewDay.");
    }
}
