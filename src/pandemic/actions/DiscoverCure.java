package pandemic.actions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import pandemic.Disease;
import pandemic.Town;
import pandemic.cards.Card;
import pandemic.player.Player;
import pandemic.player.Role;

public class DiscoverCure implements Action {

    private final String description;

    public DiscoverCure() {
        this.description = "Discover a cure";
    }

    public String getDescription() {
        return this.description;
    }

    public int run(Player player, Object o) {
        Disease chosenDisease = (Disease)o;
        if (chosenDisease == null) {
            System.out.println("This disease does not exist or the town is not infected by it, retry");
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
                return 0;
            }
            System.out.println("The disease " + chosenDisease.getName() + " has already been cured");
            return 1;
        }
        return 1;
    }

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

    public int chooseParameter(Player player, Scanner sc) {
        Town playerTown = player.getTown();
        HashMap<Disease, Integer> diseases = playerTown.getAllInfectionState();
        String print = "Choose a disease to find a cure of :";
        HashMap<String, Disease> diseasesByName = new HashMap<String, Disease>();
        for (Disease disease : diseases.keySet()) {
            diseasesByName.put(disease.getName(), disease);
            print += disease.getName() + " / ";
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

    public int chooseRandomParameter(Player player) {
        Town playerTown = player.getTown();
        ArrayList<Disease> diseasesList = new ArrayList<Disease>();
        HashMap<Disease, Integer> diseases = playerTown.getAllInfectionState();
        String print = "Choose a disease to find a cure of :";
        for (Disease disease : diseases.keySet()) {
            print += disease.getName() + " / ";
            diseasesList.add(disease);
        }
        System.out.println(print);

        Disease chosenDisease = diseasesList.get((int)Math.floor(Math.random()*diseasesList.size()));
        if (chosenDisease == null) {
            System.out.println("This disease does not exist or the town is not infected by it, retry");
            return -1;
        }
        System.out.println("You chose to discover a cure for the disease " + chosenDisease.getName() + "\n");
        return this.run(player, chosenDisease);
    }
}
