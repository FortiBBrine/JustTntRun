package me.fortibrine.justtntrun.listeners;

import me.fortibrine.justtntrun.JustTntRun;
import me.fortibrine.justtntrun.arena.Arena;
import me.fortibrine.justtntrun.inventory.ArenaListInventory;
import me.fortibrine.justtntrun.utils.VariableManager;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;

public class Listener implements org.bukkit.event.Listener {

    private JustTntRun plugin;
    private VariableManager variableManager;
    public Listener(JustTntRun plugin) {
        this.plugin = plugin;
        this.variableManager = plugin.getVariableManager();
    }

    @EventHandler
    public void onBreak(BlockBreakEvent event) {

        Player player = event.getPlayer();

        if (variableManager.getPlayersArena().containsKey(player) && variableManager.getPlayersArena().get(player).isInGame()) {
            event.setCancelled(!this.variableManager.isEnableEvents());
        }
    }

//    @EventHandler
//    public void onSpawn(EntitySpawnEvent event) {
//        event.setCancelled(!this.variableManager.isEnableEvents());
//    }

    @EventHandler
    public void onPlace(BlockPlaceEvent event) {

        Player player = event.getPlayer();

        if (variableManager.getPlayersArena().containsKey(player) && variableManager.getPlayersArena().get(player).isInGame()) {
            event.setCancelled(!this.variableManager.isEnableEvents());
        }

    }

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent event) {

        Entity entity = event.getDamager();

        if (!(entity instanceof Player)) return;
        Player player = (Player) entity;

        if (variableManager.getPlayersArena().containsKey(player) && variableManager.getPlayersArena().get(player).isInGame()) {
            event.setCancelled(!this.variableManager.isEnableEvents());
        }

    }

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent event) {
        Player player = event.getPlayer();

        if (variableManager.getPlayersArena().containsKey(player)) {

            variableManager.getPlayersArena().get(player).getPlayerList().remove(player);

            variableManager.getPlayersArena().remove(player);

            Arena arena = variableManager.getPlayersArena().get(player);

            if (arena.getPlayerList().size() == 1) {
                Player winner = arena.getPlayerList().get(0);

                plugin.getMessageManager().sendMessage(winner, "end-game");

                arena.setInGame(false);
                arena.getPlayerList().clear();

                variableManager.getPlayersArena().remove(winner);
            }
        }
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {

        Player player = event.getEntity();

        if (variableManager.getPlayersArena().containsKey(player)) {

            Arena arena = variableManager.getPlayersArena().get(player);

            variableManager.getPlayersArena().get(player).getPlayerList().remove(player);

            variableManager.getPlayersArena().remove(player);

            plugin.getMessageManager().sendMessage(player, "death");

            if (arena.getPlayerList().size() == 1) {
                Player winner = arena.getPlayerList().get(0);

                plugin.getMessageManager().sendMessage(winner, "end-game");

                arena.setInGame(false);
                arena.getPlayerList().clear();

                variableManager.getPlayersArena().remove(winner);
            }
        }
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Inventory inventory = event.getClickedInventory();

        if (inventory == null) return;
        if (inventory.getHolder() == null) return;

        if (inventory.getHolder() instanceof ArenaListInventory) {
            ((ArenaListInventory) inventory.getHolder()).onInventoryClick(event);
        }
    }

}
