package pandemic.actions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import pandemic.Disease;
import pandemic.Town;
import pandemic.cards.Card;
import pandemic.player.Player;
import pandemic.player.Role;

/** The class that reprensents the DiscoverCure's Action in the Game */
public class DiscoverCure implements Action {

    /** The description of the action */
    private final String description;

    /** Builds an action who discovers cure */
    public DiscoverCure() {
        this.description = "Discover a cure";
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
        Disease chosenDisease = (Disease)o;
        if (chosenDisease == null) {
            return -1;
        }
        int required = player.getRole() == Role.SCIENTIST ? 4 : 5;
        ArrayList<Card> cards = player.getCardsByDisease(chosenDisease);
        if (cards.size() >= required) {
            boolean cured = chosenDisease.cure();
            if (cured) {
                for (int i = 0; i < required; i++) { // Discard the cards used to cure the disease
                    player.discardCard(cards.get(i));
                }
                return 1;
            }
        }
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
        if (!playerTown.hasResearchCenter()) {
            return false;
        }
        HashMap<Disease, Integer> diseases = playerTown.getAllInfectionState();
        int required = player.getRole() == Role.SCIENTIST ? 4 : 5;
        for (Disease disease : diseases.keySet()) {
            int n = player.getCardsByDisease(disease).size();
            if (n >= required) {
                return true;
            }
        }
        return false;
    }

    /**
     * Makes the player choose an action
     * 
     * @param player The player playing the action
     * @param actionsLeft The number of actions the player has left
     * @return The cost of the action 
     */
    public int chooseParameter(Player player, Scanner sc, int actionsLeft) {
        Town playerTown = player.getTown();
        HashMap<Disease, Integer> diseases = playerTown.getAllInfectionState();
        String print = "Choose a disease to find a cure of :";
        HashMap<String, Disease> diseasesByName = new HashMap<String, Disease>();
        int required = player.getRole() == Role.SCIENTIST ? 4 : 5;
        for (Disease disease : diseases.keySet()) {
            int n = player.getCardsByDisease(disease).size();
            if (n >= required) {
                diseasesByName.put(disease.getName(), disease);
                print += disease.getName() + " / ";
            }
        }
        System.out.println(print);
        String diseaseName = sc.next();
        Disease chosenDisease = diseasesByName.get(diseaseName);
        if (chosenDisease == null) {
            System.out.println("This disease does not exist or the town is not infected by it, retry");
            return -1;
        }
        return this.run(player, chosenDisease);
    }

    /**
     * Makes the player choose an action randomly
     * 
     * @param player The player playing the action
     * @param actionsLeft The number of actions the player has left
     * @return The cost of the action 
     */
    public int chooseRandomParameter(Player player, int actionsLeft) {
        Town playerTown = player.getTown();
        ArrayList<Disease> diseasesList = new ArrayList<Disease>();
        HashMap<Disease, Integer> diseases = playerTown.getAllInfectionState();
        String print = "Choose a disease to find a cure of :";
        int required = player.getRole() == Role.SCIENTIST ? 4 : 5;
        for (Disease disease : diseases.keySet()) {
            int n = player.getCardsByDisease(disease).size();
            if (n >= required) {
                print += disease.getName() + " / ";
                diseasesList.add(disease);
            }
        }
        System.out.println(print);

        Disease chosenDisease = diseasesList.get((int)Math.floor(Math.random()*diseasesList.size()));
        if (chosenDisease == null) {
            System.out.println("This disease does not exist or the town is not infected by it, retry");
            return -1;
        }
        System.out.println("You chose to discover a cure for the disease " + chosenDisease.getName() + "\n");
        int i = this.run(player, chosenDisease); 
        if (i == 1) {
            System.out.println("\nDisease " + chosenDisease.getName() + " cured\n");
        }
        return i;
    }
}
