package pl.javastart.task;

import pl.javastart.task.Comparators.SortByLastNameComparator;
import pl.javastart.task.Comparators.SortByNameComparator;
import pl.javastart.task.Comparators.SortByScoreComparator;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Comparator;
import java.util.Scanner;

public class DataReadWrite {
    private static final int FIRST_NAME = 1;
    private static final int LAST_NAME = 2;
    private static final int SCORE = 3;
    private static final String STOP =  "stop";
    PlayersList pl = new PlayersList();
    Comparator<Player> comparator = null;

    public void getPlayersData(Scanner scanner) {
        boolean stop = false;
        while (!stop) {
            System.out.println("Podaj wynik kolejnego gracza (lub stop):");
            String text = scanner.nextLine();
            if (text.equalsIgnoreCase(STOP)) {
                sortListByOption(scanner);
                stop = true;
            } else {
                addPlayerToTheList(text);
            }
        }
    }

    private void sortListByOption(Scanner scanner) {
        try {
            System.out.println("Po jakim parametrze posortować? (1 - imię, 2 - nazwisko, 3 - wynik)");
            int option = scanner.nextInt();
            switch (option) {
                case FIRST_NAME -> comparator = new SortByNameComparator();
                case LAST_NAME -> comparator =  new SortByLastNameComparator();
                case SCORE -> comparator = new SortByScoreComparator();
                default -> throw new NumberFormatException();
            }
            writeToFile();
        } catch (NumberFormatException e) {
            System.out.println("Format nieprawidłowy Koniec Programu");
        }
    }

    private void addPlayerToTheList(String text) {
        try {
            String[] split = text.split(" ");
            int score = Integer.parseInt(split[2]);
            if (split[0].length() == 0 || split[1].length() == 0) {
                throw new NumberFormatException();
            }
            pl.addPlayer(split[0], split[1], score);
        } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
            System.out.println("Format nieprawidłowy");
        }
    }

    private void writeToFile() {
        try (var fileWriter = new BufferedWriter(new FileWriter("stats.csv"))
        ) {
            pl.getPlayers().sort(comparator);
            for (Player player : pl.getPlayers()) {
                fileWriter.write(player.toString());
                fileWriter.newLine();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
