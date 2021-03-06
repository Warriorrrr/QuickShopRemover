package dev.warriorrr.quickshopremover;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.palmergames.bukkit.towny.event.DeletePlayerEvent;
import com.palmergames.bukkit.towny.event.town.TownUnclaimEvent;

import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.maxgamer.quickshop.api.QuickShopAPI;
import org.maxgamer.quickshop.shop.Shop;

public class TownyListener implements Listener {

    @SuppressWarnings("unused")
    private final QuickShopRemover plugin;

    public TownyListener(QuickShopRemover quickShopRemover) {
        plugin = quickShopRemover;
    }

    @EventHandler
    public void onTownUnclaim(TownUnclaimEvent event) {
        Chunk unclaimedChunk = event.getWorldCoord().getBukkitWorld().getChunkAt(event.getWorldCoord().getX(), event.getWorldCoord().getZ());
        Map <Location,Shop> shopMap = QuickShopAPI.getShopAPI().getShop(unclaimedChunk);
        if (shopMap.size() > 0) {
            for (Entry<Location, Shop> shop : shopMap.entrySet())
                shop.getValue().delete();
        }
    }

    @EventHandler
    public void onResidentDelete(DeletePlayerEvent event) {
        List<Shop> ownedShops = QuickShopAPI.getShopAPI().getPlayerAllShops(event.getPlayerUUID());
        if (ownedShops.size() > 0) {
            for (Shop shop : ownedShops)
                shop.delete();
        }
    }
}
