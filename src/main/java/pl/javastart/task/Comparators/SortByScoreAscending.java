package pl.javastart.task.Comparators;

import pl.javastart.task.Player;

import java.util.Comparator;

public class SortByScoreAscending implements Comparator<Player> {
    @Override
    public int compare(Player o1, Player o2) {
        return Integer.compare(o1.getScore(), o2.getScore());
    }
}
