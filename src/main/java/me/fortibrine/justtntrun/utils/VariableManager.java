package me.fortibrine.justtntrun.utils;

import lombok.Getter;
import lombok.Setter;
import me.fortibrine.justtntrun.arena.Arena;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VariableManager {

    @Getter
    private List<Material> materialList = new ArrayList<>();

    @Getter
    private int time;

    @Getter
    private boolean enableEvents;

    @Getter
    private Map<Block, Material> renewBlocks = new HashMap<>();

    @Getter
    private List<Arena> arenaList = new ArrayList<>();

    @Getter
    private String arenaListTitle;

    private ItemStack template;

    public VariableManager(FileConfiguration config) {
        config.getStringList("materials").forEach(s -> materialList.add(Material.matchMaterial(s)));
        this.time = config.getInt("time");
        this.enableEvents = config.getBoolean("events");

        ConfigurationSection arenas = config.getConfigurationSection("arenas");

        for (String key : arenas.getKeys(false)) {
            String name = arenas.getString(key + ".name");

            int players = arenas.getInt(key + ".players");

            double x = arenas.getDouble(key + ".x");
            double y = arenas.getDouble(key + ".y");
            double z = arenas.getDouble(key + ".z");

            World world = Bukkit.getWorld(arenas.getString(key + ".world"));

            Location location = new Location(world, x, y, z);

            Arena arena = new Arena(name, players, location);

            this.arenaList.add(arena);
        }

        this.template = new ItemStack(Material.matchMaterial(config.getString("template.material")));

        ItemMeta meta = this.template.getItemMeta();

        meta.setDisplayName(config.getString("template.name"));
        meta.setLore(config.getStringList("template.lore"));

        this.template.setItemMeta(meta);

        this.arenaListTitle = config.getString("arena-list.title");

    }

    public ItemStack fromArena(Arena arena) {
        ItemStack item = template.clone();

        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(meta.getDisplayName().replace("%name", arena.getName()).replace("%playersWait", String.valueOf(arena.getPlayerList().size())).replace("%players", String.valueOf(arena.getPlayers())));

        List<String> lore = meta.getLore();
        lore.replaceAll(s -> s.replace("%name", arena.getName()).replace("%playersWait", String.valueOf(arena.getPlayerList().size())).replace("%players", String.valueOf(arena.getPlayers())));

        meta.setLore(lore);

        item.setItemMeta(meta);

        return item;
    }

}
