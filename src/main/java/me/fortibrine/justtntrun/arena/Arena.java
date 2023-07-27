package me.fortibrine.justtntrun.arena;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class Arena {

    @Getter
    @Setter
    private boolean inGame;

    @Getter
    private String name;

    @Getter
    private int players;

    @Getter
    private Location location;

    @Getter
    private List<Player> playerList = new ArrayList<>();

    public Arena(String name, int players, Location location) {
        this.name = name;
        this.players = players;
        this.location = location;
    }

}
