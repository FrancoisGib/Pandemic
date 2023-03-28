package pandemic.actions;

import java.util.Scanner;

import pandemic.player.Player;

public interface Action {

    int run(Player player, Scanner sc);

    boolean requirements(Player player);

    String getDescription();
}
