package me.fortibrine.justtntrun.listeners;

import me.fortibrine.justtntrun.JustTntRun;
import me.fortibrine.justtntrun.utils.VariableManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntitySpawnEvent;

public class Listener implements org.bukkit.event.Listener {

    private JustTntRun plugin;
    private VariableManager variableManager;
    public Listener(JustTntRun plugin) {
        this.plugin = plugin;
        this.variableManager = plugin.getVariableManager();
    }

    @EventHandler
    public void onBreak(BlockBreakEvent event) {
        event.setCancelled(!this.variableManager.isEnableEvents());
    }

    @EventHandler
    public void onSpawn(EntitySpawnEvent event) {
        event.setCancelled(!this.variableManager.isEnableEvents());
    }

    @EventHandler
    public void onPlace(BlockPlaceEvent event) {
        event.setCancelled(!this.variableManager.isEnableEvents());
    }

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent event) {
        event.setCancelled(!this.variableManager.isEnableEvents());
    }

}
