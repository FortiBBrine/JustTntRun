package me.fortibrine.justtntrun.arena;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Arena {

    @Getter
    @Setter
    private boolean inGame;

    @Getter
    private Map<Block, Material> renewBlocks;

    @Getter
    private String name;

    @Getter
    private int players;

    @Getter
    private Location location;

    @Getter
    private List<Player> playerList = new ArrayList<>();

    public Arena(String name, int players, Location location) {
        this.renewBlocks = new HashMap<>();
        this.name = name;
        this.players = players;
        this.location = location;
    }

    public void regenerateBlocks() {
        this.renewBlocks.forEach(Block::setType);
        this.renewBlocks.clear();
    }

}
