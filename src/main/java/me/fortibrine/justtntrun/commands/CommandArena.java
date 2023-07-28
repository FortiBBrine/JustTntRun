package me.fortibrine.justtntrun.commands;

import me.fortibrine.justtntrun.JustTntRun;
import me.fortibrine.justtntrun.arena.Arena;
import me.fortibrine.justtntrun.inventory.ArenaListInventory;
import me.fortibrine.justtntrun.utils.MessageManager;
import me.fortibrine.justtntrun.utils.VariableManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CommandArena implements CommandExecutor {

    private JustTntRun plugin;
    private MessageManager messageManager;
    private VariableManager variableManager;
    public CommandArena(JustTntRun plugin) {
        this.plugin = plugin;
        this.messageManager = plugin.getMessageManager();
        this.variableManager = plugin.getVariableManager();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (!(sender instanceof Player)) {
            return true;
        }

        Player player = (Player) sender;

        if (!player.hasPermission("justtntrun.arena")) {
            messageManager.sendMessage(player, "not-permission");
            return true;
        }

        if (args.length > 0 && args[0].equals("leave")) {

            if (!variableManager.getPlayersArena().containsKey(player)){
                messageManager.sendMessage(player, "leave-arena");
                return true;
            }

            if (variableManager.getPlayersArena().get(player).isInGame()) {
                messageManager.sendMessage(player, "already-in-game");
                return true;
            }

            variableManager.getPlayersArena().get(player).getPlayerList().remove(player);
            variableManager.getPlayersArena().remove(player);

            messageManager.sendMessage(player, "leave-arena");

            return true;
        }

        List<Arena> arenas = variableManager.getArenaList();
        Map<ItemStack, Arena> items = new HashMap<>();

        arenas.forEach(arena -> items.put(variableManager.fromArena(arena), arena));

        ArenaListInventory arenaListInventory = new ArenaListInventory(items, variableManager, messageManager);

        player.openInventory(arenaListInventory.getInventory());

        return true;
    }
}
