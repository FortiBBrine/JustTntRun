package me.fortibrine.justtntrun.inventory;

import me.fortibrine.justtntrun.arena.Arena;
import me.fortibrine.justtntrun.utils.MessageManager;
import me.fortibrine.justtntrun.utils.VariableManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

import java.util.Map;

public class ArenaListInventory implements InventoryHolder {

    private Inventory inventory;
    private Map<ItemStack, Arena> items;
    private VariableManager variableManager;
    private MessageManager messageManager;

    public ArenaListInventory(Map<ItemStack, Arena> items, VariableManager variableManager, MessageManager messageManager) {
        this.items = items;
        this.variableManager = variableManager;
        this.messageManager = messageManager;

        inventory = Bukkit.createInventory(this, 54, variableManager.getArenaListTitle());

        items.forEach((ItemStack item, Arena arena) -> inventory.addItem(item));
    }

    @Override
    public Inventory getInventory() {
        return this.inventory;
    }

    public void onInventoryClick(InventoryClickEvent event) {

        event.setCancelled(true);

        Player player = (Player) event.getWhoClicked();

        ItemStack item = event.getCurrentItem();

        Arena arena = items.get(item);

        if (arena.isInGame()) {
            return;
        }

        if (variableManager.getPlayersArena().containsKey(player)) {

            if (variableManager.getPlayersArena().get(player).isInGame()) {
                return;
            }

            variableManager.getPlayersArena().get(player).getPlayerList().remove(player);
            variableManager.getPlayersArena().remove(player);

            messageManager.sendMessage(player, "leave-arena");

        } else {
            variableManager.getPlayersArena().put(player, arena);
            arena.getPlayerList().add(player);

            messageManager.sendMessage(player, "join-arena");

            if (arena.getPlayerList().size() >= arena.getPlayers() / 2) {
                arena.setInGame(true);

                Location location = arena.getLocation();

                arena.getPlayerList().forEach(listPlayer -> {
                    listPlayer.teleport(location);
                    messageManager.sendMessage(listPlayer, "game-start");
                });

            }
        }

    }
}
