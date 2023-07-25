package me.fortibrine.justtntrun;

import lombok.Getter;
import me.fortibrine.justtntrun.utils.PlaceholderManager;
import me.fortibrine.justtntrun.utils.RunnableManager;
import me.fortibrine.justtntrun.utils.SQLManager;
import me.fortibrine.justtntrun.utils.VariableManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

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

        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            new PlaceholderManager(this).register();
        }

        new RunnableManager(this);

    }

    @Override
    public void onDisable() {
        this.sqlManager.close();
    }
}
