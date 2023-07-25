package me.fortibrine.justtntrun.utils;

import me.fortibrine.justtntrun.JustTntRun;
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

        Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, () -> checkBlockUnderPlayer(), 0L, 0L);
    }

    private void checkBlockUnderPlayer() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            Location location = player.getLocation();

            location.setY(location.getY() - 1);

            UUID uuid = player.getUniqueId();

            Block block = location.clone().add(0.3, 0, -0.3).getBlock();
            if (variableManager.getMaterialList().contains(block.getType())) {
                Block finalBlock = block;

                Bukkit.getScheduler().runTaskLater(plugin, () -> renewBlock(finalBlock, finalBlock.getType()), variableManager.getTime() * 20L);
                Bukkit.getScheduler().runTaskLater(plugin, () -> renewBlock(finalBlock, Material.AIR), 3L);

                sqlManager.setBlocks(uuid.toString(), sqlManager.getBlocks(uuid.toString()) + 1);

                return;
            }
            block = location.clone().add(-0.3, 0, -0.3).getBlock();
            if (variableManager.getMaterialList().contains(block.getType())) {
                Block finalBlock = block;

                Bukkit.getScheduler().runTaskLater(plugin, () -> renewBlock(finalBlock, finalBlock.getType()), variableManager.getTime() * 20L);
                Bukkit.getScheduler().runTaskLater(plugin, () -> renewBlock(finalBlock, Material.AIR), 3L);

                sqlManager.setBlocks(uuid.toString(), sqlManager.getBlocks(uuid.toString()) + 1);

                return;
            }
            block = location.clone().add(0.3, 0, 0.3).getBlock();
            if (variableManager.getMaterialList().contains(block.getType())) {
                Block finalBlock = block;

                Bukkit.getScheduler().runTaskLater(plugin, () -> renewBlock(finalBlock, finalBlock.getType()), variableManager.getTime() * 20L);
                Bukkit.getScheduler().runTaskLater(plugin, () -> renewBlock(finalBlock, Material.AIR), 3L);

                sqlManager.setBlocks(uuid.toString(), sqlManager.getBlocks(uuid.toString()) + 1);

                return;
            }
            block = location.clone().add(-0.3, 0, 0.3).getBlock();
            if (variableManager.getMaterialList().contains(block.getType())) {
                Block finalBlock = block;

                Bukkit.getScheduler().runTaskLater(plugin, () -> renewBlock(finalBlock, finalBlock.getType()), variableManager.getTime() * 20L);
                Bukkit.getScheduler().runTaskLater(plugin, () -> renewBlock(finalBlock, Material.AIR), 3L);
                sqlManager.setBlocks(uuid.toString(), sqlManager.getBlocks(uuid.toString()) + 1);

                return;
            }

        }
    }

    private void renewBlock(Block block, Material material) {
//
//        System.out.println(material.toString());

        block.setType(material);
    }

}
