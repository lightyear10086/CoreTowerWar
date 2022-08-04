package me.kiconsen.corecenterwar.CommandListeners;

import org.bukkit.Bukkit;
import org.bukkit.GameRule;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.entity.ThrowableProjectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.world.WorldLoadEvent;

public class InitListeners implements Listener {
    @EventHandler
    public void onWorldLoaded(WorldLoadEvent e){
        Bukkit.getLogger().info("world=>"+e.getWorld());
        Server nmsServer=Bukkit.getServer();
        World nmsWorld=Bukkit.getWorld("world");
        nmsWorld.setGameRule(GameRule.NATURAL_REGENERATION,false);
    }
    @EventHandler
    public void onPlayerDiscardItem(PlayerDropItemEvent e){
        e.setCancelled(true);
    }
}
