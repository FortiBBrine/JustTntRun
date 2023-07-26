package me.fortibrine.justtntrun.utils;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;

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

    public VariableManager(FileConfiguration config) {
        config.getStringList("materials").forEach(s -> materialList.add(Material.matchMaterial(s)));
        this.time = config.getInt("time");
        this.enableEvents = config.getBoolean("events");
    }

}
