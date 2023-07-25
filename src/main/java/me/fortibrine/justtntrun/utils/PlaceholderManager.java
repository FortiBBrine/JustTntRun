package me.fortibrine.justtntrun.utils;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import me.fortibrine.justtntrun.JustTntRun;
import org.bukkit.OfflinePlayer;

import java.util.UUID;

public class PlaceholderManager extends PlaceholderExpansion {

    private JustTntRun plugin;
    private SQLManager sqlManager;
    public PlaceholderManager(JustTntRun plugin) {
        this.plugin = plugin;
        this.sqlManager = plugin.getSqlManager();
    }

    @Override
    public String getIdentifier() {
        return "justtntrun";
    }

    @Override
    public String getAuthor() {
        return plugin.getDescription().getAuthors().get(0);
    }

    @Override
    public String getVersion() {
        return plugin.getDescription().getVersion();
    }

    @Override
    public boolean persist() {
        return true;
    }

    @Override
    public String onRequest(OfflinePlayer player, String params) {
        if (params.equalsIgnoreCase("blocks")) {
            UUID uuid = player.getUniqueId();
            int blocks = sqlManager.getBlocks(uuid.toString());
            return String.valueOf(blocks);
        }

        return null;
    }
}
