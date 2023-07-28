package me.fortibrine.justtntrun;

import lombok.Getter;
import me.fortibrine.justtntrun.arena.Arena;
import me.fortibrine.justtntrun.commands.CommandArena;
import me.fortibrine.justtntrun.listeners.Listener;
import me.fortibrine.justtntrun.utils.*;
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

    @Getter
    public MessageManager messageManager;

    @Override
    public void onEnable() {
        File config = new File(this.getDataFolder() + File.separator + "config.yml");
        if (!config.exists()) {
            this.getConfig().options().copyDefaults(true);
            this.saveDefaultConfig();

            saveResource("messages.yml", false);
        }

        sqlManager = new SQLManager();
        messageManager = new MessageManager(this);
        variableManager = new VariableManager(this.getConfig(), messageManager);
        new RunnableManager(this);

        if (Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")) {
            new PlaceholderManager(this).register();
        }

        Bukkit.getPluginManager().registerEvents(new Listener(this), this);

        this.getCommand("arena").setExecutor(new CommandArena(this));

    }

    @Override
    public void onDisable() {
        this.sqlManager.close();

        variableManager.getArenaList().forEach(Arena::regenerateBlocks);
    }
}
