package pandemic.actions;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Scanner;

import pandemic.Disease;
import pandemic.Town;
import pandemic.player.Player;
import pandemic.player.Role;

/** The class that reprensents the TreatDisease's Action in the Game */
public class TreatDisease implements Action {

    /** The description of the action */
    private final String description;

    /** The cost of the action */
    private int cost;

    /** Builds an action who treats disease */
    public TreatDisease() {
        this.description = "Treat a disease";
        this.cost = 1;
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
        Town playerTown = player.getTown();
        int cpt = 0;
        if (player.getRole() == Role.DOCTOR) {
            cpt = playerTown.getInfectionState(chosenDisease);
            playerTown.setInfectionState(chosenDisease, 0);
            for (int i = 0; i < cpt; i++) {
                chosenDisease.removeCube();
            }
            this.cost = 1;
        }
        else {
            playerTown.setInfectionState(chosenDisease, -this.cost);
        }
        return this.cost;
    }

    /**
     * Tell if the player can do this action or not
     * 
     * @param player The player who plays
     * @return True if the player has the requirements to make this action
     */
    public boolean requirements(Player player) {
        Town playerTown = player.getTown();
        HashMap<Disease, Integer> diseases = playerTown.getAllInfectionState();
        int cpt = 0;
        for (Disease disease : diseases.keySet()) {
            if (diseases.get(disease) > 0) {
                cpt++;
            }
        }
        return cpt > 0;
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
        HashMap<String, Disease> diseasesByName = new HashMap<String, Disease>();
        int cpt = 0;
        String print = "";
        for (Disease disease : diseases.keySet()) {
            if (diseases.get(disease) > 0) {
                cpt++;
                diseasesByName.put(disease.getName(), disease);
                print += disease.getName() + " / ";
            }
        }
        Disease chosenDisease = null;
        if (cpt == 1) {
            Iterator<Disease> it = diseases.keySet().iterator();
            chosenDisease = it.next();
            while (diseases.get(chosenDisease) == 0) {
                chosenDisease = it.next();
            }
        } else if (cpt > 1) {
            System.out.println("Choose a disease to treat :");
            System.out.println(print);
            String diseaseName = sc.next();
            if (diseaseName == "exit") {
                return -1;
            }
            chosenDisease = diseasesByName.get(diseaseName);
        }
        if (chosenDisease != null) {
            int maxCureNumber = Math.min(playerTown.getInfectionState(chosenDisease), actionsLeft);
            int n = -1;
            if (maxCureNumber == 1) {
                n = 1;
            }
            if (player.getRole() == Role.DOCTOR) {
                this.cost = 1;
                int res = this.run(player, chosenDisease);
                System.out.println("\nThe current town infection state for the disease " + chosenDisease.getName() + " has been decreased by " + this.cost + ", it is now of " + playerTown.getInfectionState(chosenDisease)+ "\n");
                return res;
            }
            if (maxCureNumber > 0 && actionsLeft > 1) {
                System.out.println("Choose the amount of cubes you want to treat : from 1 to " + maxCureNumber);
                String act = "";
                while (n == -1) {
                    act = sc.next();
                    try {
                        n = Integer.parseInt(act);
                        if (n < 1 || n > maxCureNumber) {
                            n = -1;
                            System.out.println("Enter an integer between 1 and " + maxCureNumber);
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Not an integer, retry");
                    }
                }
            }
            this.cost = n;
            int res = this.run(player, chosenDisease);
            System.out.println("\nThe current town infection state for the disease " + chosenDisease.getName() + " has been decreased by " + this.cost + ", it is now of " + playerTown.getInfectionState(chosenDisease)+ "\n");
            return res;
        }
        System.out.println("\nThis town is not infected by this disease, nothing happened");
        return -1;
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
        HashMap<Disease, Integer> diseases = playerTown.getAllInfectionState();
        HashMap<String, Disease> diseasesByName = new HashMap<String, Disease>();
        String print = "";
        for (Disease disease : diseases.keySet()) {
            if (diseases.get(disease) > 0) {
                diseasesByName.put(disease.getName(), disease);
                print += disease.getName() + " / ";
            }
        }
        System.out.println(print);
        int index = (int)Math.floor(Math.random() * diseases.size());
        Iterator<Disease> it = diseases.keySet().iterator();
        Disease chosenDisease = it.next();
        while (index > 0) {
            chosenDisease = it.next();
            index--;
        }
        System.out.println("You chose to treat the disease " + chosenDisease.getName() + "\n");
        this.cost = 1;
        int res = this.run(player, chosenDisease);
        System.out.println("\nThe current town infection state for the disease " + chosenDisease.getName() + " has been decreased by " + this.cost + ", it is now of " + playerTown.getInfectionState(chosenDisease)+ "\n");
        return res;
    }
}
