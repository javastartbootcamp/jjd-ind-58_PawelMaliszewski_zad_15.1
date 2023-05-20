package pl.javastart.task;

import pl.javastart.task.Comparators.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Comparator;
import java.util.Scanner;

public class DataReadWrite {
    private static final int FIRST_NAME = 1;
    private static final int LAST_NAME = 2;
    private static final int SCORE = 3;
    private static final String STOP = "stop";
    private static final int ASCENDING = 1;
    private static final int DESCENDING = 2;
    PlayersList playersList = new PlayersList();
    Comparator<Player> comparator = null;

    public void getPlayersData(Scanner scanner) {
        boolean stop = false;
        while (!stop) {
            String text = givePlayerScore(scanner);
            if (text.equalsIgnoreCase(STOP)) {
                sortListByOption(scanner);
                stop = true;
            } else {
                addPlayerToTheList(text);
            }
        }
    }

    private static String givePlayerScore(Scanner scanner) {
        System.out.printf("%s%s%s", "Podaj wynik kolejnego gracza (lub ", STOP, "):\n");
        return scanner.nextLine();
    }

    private void sortListByOption(Scanner scanner) {
        try {
            switch (getOption(scanner)) {
                case FIRST_NAME -> {
                    if (isAscending(scanner)) {
                        comparator = new SortByFirstNameAscending();
                    } else {
                        comparator = new SortByFirstNameDescending();
                    }
                }
                case LAST_NAME -> {
                    if (isAscending(scanner)) {
                        comparator = new SortByLastNameAscending();
                    } else {
                        comparator = new SortByLastNameDescending();
                    }
                }
                case SCORE -> {
                    if (isAscending(scanner)) {
                        comparator = new SortByScoreAscending();
                    } else {
                        comparator = new SortByScoreDescending();
                    }
                }
                default -> throw new NumberFormatException();
            }
            System.out.println();
            writeToFile();
        } catch (NumberFormatException e) {
            System.out.println("Format nieprawidłowy Koniec Programu");
        }
    }

    private static int getOption(Scanner scanner) {
        //System.out.printf("Po jakim parametrze posortować? (1 - imię, 2 - nazwisko, 3 - wynik)");
        System.out.printf("%s%d%s%d%s%d%s",
                "Po jakim parametrze posortować? (",
                FIRST_NAME, " - imię, ",
                LAST_NAME, " - nazwisko, ",
                SCORE, " - wynik)\n");
        return scanner.nextInt();
    }

    private static boolean isAscending(Scanner scanner) {
        boolean isAscending = true;
        System.out.printf("%s%d%s%d%s",
                "Sortować rosnąco czy malejąco? (",
                ASCENDING, " - rosnąco, ",
                DESCENDING, " - malejąco)");
        int optionTwo = scanner.nextInt();
        if (optionTwo == DESCENDING) {
            isAscending = false;
        } else if (optionTwo < ASCENDING || optionTwo > DESCENDING) {
            throw new NumberFormatException();
        }
        return isAscending;
    }

    private void addPlayerToTheList(String text) {
        try {
            String[] split = text.split(" ");
            int score = Integer.parseInt(split[2]);
            if (split[0].length() == 0 || split[1].length() == 0) {
                throw new NumberFormatException();
            }
            playersList.addPlayer(split[0], split[1], score);
        } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
            System.out.println("Format nieprawidłowy");
        }
    }

    private void writeToFile() {
        try (var fileWriter = new BufferedWriter(new FileWriter("stats.csv"))
        ) {
            playersList.getPlayers().sort(comparator);
            for (Player player : playersList.getPlayers()) {
                fileWriter.write(player.toString());
                fileWriter.newLine();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
