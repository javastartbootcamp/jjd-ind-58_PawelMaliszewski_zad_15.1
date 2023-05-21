package pl.javastart.task;

import java.util.ArrayList;
import java.util.List;

public class PlayersList {
    List<Player> players =  new ArrayList<>();

    public List<Player> getPlayers() {
        return players;
    }

    public void addPlayer(Player player) {
        players.add(player);
    }
}
