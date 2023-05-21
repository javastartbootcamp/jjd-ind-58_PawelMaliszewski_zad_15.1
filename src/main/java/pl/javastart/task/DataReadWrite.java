package pl.javastart.task;

import pl.javastart.task.comparators.*;
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

    public void enterPlayersScoresToCreateSortedListFile(Scanner scanner) {
        PlayersList playersList = createPlayersList(scanner);
        if (playersList.getPlayers().size() > 0) {
            Comparator<Player> comparator = selectSortingOption(scanner);
            sortList(playersList, comparator);
            writeToFile(playersList);
        }
    }

    private static void sortList(PlayersList playersList, Comparator<Player> comparator) {
        playersList.getPlayers().sort(comparator);
    }

    private PlayersList createPlayersList(Scanner scanner) {
        PlayersList playersList = new PlayersList();
        boolean stop = false;
        while (!stop) {
            String text = enterPlayerScore(scanner);
            if (text.equalsIgnoreCase(STOP)) {
                stop = true;
            } else {
                playersList.addPlayer(createPlayer(text));
            }
        }
        return playersList;
    }

    private static String enterPlayerScore(Scanner scanner) {
        System.out.printf("%s%s%s", "Podaj wynik kolejnego gracza (lub ", STOP, "):\n");
        return scanner.nextLine();
    }

    private Comparator<Player> selectSortingOption(Scanner scanner) {
        Comparator<Player> comparator = null;
        try {
            comparator = switch (getOption(scanner)) {
                case FIRST_NAME -> new PlayerFirstNameComparator();
                case LAST_NAME -> new PlayerLastNameComparator();
                case SCORE -> new PlayerScoreComparator();
                default -> throw new NumberFormatException();
            };
            if (!isAscending(scanner)) {
                comparator = comparator.reversed();
            }
            System.out.println();
        } catch (NumberFormatException e) {
            System.out.println("Format nieprawidłowy Koniec Programu");
        }
        return comparator;
    }

    private static int getOption(Scanner scanner) {
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
                DESCENDING, " - malejąco)\n");
        int optionTwo = scanner.nextInt();
        if (optionTwo == DESCENDING) {
            isAscending = false;
        } else if (optionTwo < ASCENDING || optionTwo > DESCENDING) {
            throw new NumberFormatException();
        }
        return isAscending;
    }

    private Player createPlayer(String text) {
        Player player = null;
        try {
            String[] split = text.split(" ");
            int score = Integer.parseInt(split[2]);
            if (split[0].length() == 0 || split[1].length() == 0) {
                throw new NumberFormatException();
            }
            player = new Player(split[0], split[1], score);
        } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
            System.out.println("Format nieprawidłowy");
        }
        return player;
    }

    private void writeToFile(PlayersList playersList) {
        try (var fileWriter = new BufferedWriter(new FileWriter("stats.csv"))
        ) {
            for (Player player : playersList.getPlayers()) {
                fileWriter.write(player.toString());
                fileWriter.newLine();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
