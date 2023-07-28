package me.fortibrine.justtntrun.utils;

import me.fortibrine.justtntrun.JustTntRun;
import me.fortibrine.justtntrun.arena.Arena;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.util.UUID;

public class RunnableManager {

    private JustTntRun plugin;
    private VariableManager variableManager;
    private SQLManager sqlManager;
    public RunnableManager(JustTntRun plugin) {
        this.plugin = plugin;
        this.variableManager = plugin.getVariableManager();
        this.sqlManager = plugin.getSqlManager();

        Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, () -> checkBlockUnderPlayer(), 0L, 1L);
    }

    private void checkBlockUnderPlayer() {
        for (Player player : Bukkit.getOnlinePlayers()) {

            if (!variableManager.getPlayersArena().containsKey(player)) return;

            Arena arena = variableManager.getPlayersArena().get(player);

            Location location = player.getLocation();

            location.setY(location.getY() - 1);

            UUID uuid = player.getUniqueId();

            Block block = location.clone().add(0.3, 0, -0.3).getBlock();
            if (variableManager.getMaterialList().contains(block.getType())) {
                Block finalBlock = block;

                Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> renewBlock(arena, finalBlock, Material.AIR, uuid.toString()), 3L);

                return;
            }
            block = location.clone().add(-0.3, 0, -0.3).getBlock();
            if (variableManager.getMaterialList().contains(block.getType())) {
                Block finalBlock = block;

                Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> renewBlock(arena, finalBlock, Material.AIR, uuid.toString()), 3L);

                return;
            }
            block = location.clone().add(0.3, 0, 0.3).getBlock();
            if (variableManager.getMaterialList().contains(block.getType())) {
                Block finalBlock = block;

                Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> renewBlock(arena, finalBlock, Material.AIR, uuid.toString()), 3L);

                return;
            }
            block = location.clone().add(-0.3, 0, 0.3).getBlock();
            if (variableManager.getMaterialList().contains(block.getType())) {
                Block finalBlock = block;

                Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> renewBlock(arena, finalBlock, Material.AIR, uuid.toString()), 3L);

                return;
            }

        }
    }

    private void renewBlock(Arena arena, Block block, Material material, String uuid) {

        Material type = block.getType();

        if (type == Material.AIR) {
            return;
        }

        arena.getRenewBlocks().put(block, material);

        sqlManager.setBlocks(uuid.toString(), sqlManager.getBlocks(uuid.toString()) + 1);

        block.setType(material);
//
//        plugin.getVariableManager().getRenewBlocks().put(block, type);

//        Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> {
//            block.setType(type);
//            plugin.getVariableManager().getRenewBlocks().remove(block);
//        }, variableManager.getTime() * 20L);
    }

}
