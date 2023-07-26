package me.fortibrine.justtntrun;

import lombok.Getter;
import me.fortibrine.justtntrun.listeners.Listener;
import me.fortibrine.justtntrun.utils.PlaceholderManager;
import me.fortibrine.justtntrun.utils.RunnableManager;
import me.fortibrine.justtntrun.utils.SQLManager;
import me.fortibrine.justtntrun.utils.VariableManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.Map;

public final class JustTntRun extends JavaPlugin {

    @Getter
    private SQLManager sqlManager;

    @Getter
    private VariableManager variableManager;

    @Override
    public void onEnable() {
        File config = new File(this.getDataFolder() + File.separator + "config.yml");
        if (!config.exists()) {
            this.getConfig().options().copyDefaults(true);
            this.saveDefaultConfig();
        }

        sqlManager = new SQLManager();

        variableManager = new VariableManager(this.getConfig());

        if (Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")) {
            new PlaceholderManager(this).register();
        }

        new RunnableManager(this);

        Bukkit.getPluginManager().registerEvents(new Listener(this), this);

    }

    @Override
    public void onDisable() {
        this.sqlManager.close();

        Map<Block, Material> renewBlocks = this.getVariableManager().getRenewBlocks();

        for (Block block : renewBlocks.keySet()) {
            block.setType(renewBlocks.get(block));
       }
    }
}
