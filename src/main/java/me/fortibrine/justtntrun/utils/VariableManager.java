package me.fortibrine.justtntrun.utils;

import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.ArrayList;
import java.util.List;

public class VariableManager {

    @Getter
    private List<Material> materialList = new ArrayList<>();

    @Getter
    private int time;
    public VariableManager(FileConfiguration config) {
        config.getStringList("materials").forEach(s -> materialList.add(Material.matchMaterial(s)));
        this.time = config.getInt("time");
    }

}
