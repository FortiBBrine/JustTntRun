package me.fortibrine.justtntrun.inventory;

import me.fortibrine.justtntrun.arena.Arena;
import me.fortibrine.justtntrun.utils.VariableManager;
import org.bukkit.Bukkit;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

import java.util.Map;

public class ArenaListInventory implements InventoryHolder {

    private Inventory inventory;
    private Map<ItemStack, Arena> items;

    public ArenaListInventory(Map<ItemStack, Arena> items, VariableManager variableManager) {
        this.items = items;

        inventory = Bukkit.createInventory(this, 54, variableManager.getArenaListTitle());

        items.forEach((ItemStack item, Arena arena) -> inventory.addItem(item));
    }

    @Override
    public Inventory getInventory() {
        return this.inventory;
    }

    public void onInventoryClick(InventoryClickEvent event) {

    }
}
