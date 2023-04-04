package pandemic.actions;

import java.util.Scanner;

import pandemic.player.Player;

public interface Action {

    int run(Player player, Object o);

    boolean requirements(Player player);

    String getDescription();

    int runWithChoice(Player player, Scanner sc);
}
