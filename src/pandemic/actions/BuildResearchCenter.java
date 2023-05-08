package pandemic.actions;

import java.util.ArrayList;
import java.util.Scanner;

import pandemic.Game;
import pandemic.Town;
import pandemic.cards.Card;
import pandemic.player.Player;
import pandemic.player.Role;

/** The class that reprensents the BuildResearchCenter's Action in the Game */
public class BuildResearchCenter implements Action {

    /** The description of the action */
    private final String description;

    /** The number of research centers left */
    private int researchCentersRemaining;

    /** Builds an action who builds research centers */
    public BuildResearchCenter() {
        this.description = "Build a research center in your current town";
        this.researchCentersRemaining = Game.MAX_RESEARCH_CENTERS;
    }

    /** Get the description of the action
     * 
     * @return The description of the action
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * Run an action chosen by the player
     * 
     * @param player The player who executes the action
     * @return The cost of the action if the action has been successfully made, 0 if the player exit, -1 if the action failed
     */
    public int run(Player player, Object o) {
        Town playerTown = player.getTown();
        boolean res = playerTown.buildResearchCenter();
        if (res) {
            ArrayList<Card> townCards = player.getCardsByTown(playerTown);
            if (player.getRole() != Role.EXPERT) {
                Card removedCard = townCards.get(0);
                player.discardCard(removedCard);
            }
            this.researchCentersRemaining--;
            return 1;
        }
        System.out.println("Action failed, retry");
        return 0;
    }

    /**
     * Tell if the player can do this action or not
     * 
     * @param player The player who plays
     * @return True if the player has the requirements to make this action
     */
    public boolean requirements(Player player) {
        Town playerTown = player.getTown();
        if (playerTown.hasResearchCenter() || this.researchCentersRemaining == 0) {
            return false;
        }
        return player.getRole() == Role.EXPERT ? true : player.getCardsByTown(playerTown).size() > 0; 
    }

    /**
     * Makes the player choose an action
     * 
     * @param player The player playing the action
     * @param actionsLeft The number of actions the player has left
     * @return The cost of the action 
     */
    public int chooseParameter(Player player, Scanner sc, int actionsLeft) {
        System.out.println("A research center has been built in " + player.getTown().getName() + "\n");
        return this.run(player, null);
    }

    /**
     * Makes the player choose an action randomly
     * 
     * @param player The player playing the action
     * @param actionsLeft The number of actions the player has left
     * @return The cost of the action 
     */
    public int chooseRandomParameter(Player player, int actionsLeft) {
        System.out.println("A research center has been built in " + player.getTown().getName() + "\n");
        return this.run(player, null);
    }
}
