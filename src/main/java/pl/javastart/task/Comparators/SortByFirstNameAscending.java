package pl.javastart.task.Comparators;

import pl.javastart.task.Player;
import java.util.Comparator;

public class SortByFirstNameAscending implements Comparator<Player> {
    @Override
    public int compare(Player o1, Player o2) {
        return o1.getFirstName().compareTo(o2.getFirstName());
    }
}
