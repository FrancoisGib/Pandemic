package pandemic.actions;

import java.util.ArrayList;
import java.util.Scanner;

import pandemic.Town;
import pandemic.cards.Card;
import pandemic.player.Player;
import pandemic.player.Role;

public class BuildResearchCenter implements Action {

    private final String description;

    public BuildResearchCenter() {
        this.description = "Build a research center in your current town";
    }

    public String getDescription() {
        return this.description;
    }

    public int run(Player player, Scanner sc) {
        Town playerTown = player.getTown();
        boolean res = playerTown.buildResearchCenter();
        if (res) {
            ArrayList<Card> townCards = player.getCardsByTown(playerTown);
            if (player.getRole() != Role.EXPERT) {
                Card removedCard = townCards.get(0);
                player.discardCard(removedCard);
            }
            System.out.println("A research center has been built in " + playerTown.getName());
            return 0;
        }
        return 1;
    }

    public boolean requirements(Player player) {
        Town playerTown = player.getTown();
        if (playerTown.hasResearchCenter()) {
            return false;
        }
        return player.getRole() == Role.EXPERT ? true : player.getCardsByTown(playerTown).size() > 0; 
    }
}
