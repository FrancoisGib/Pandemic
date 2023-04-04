package pandemic.actions;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Scanner;

import pandemic.Disease;
import pandemic.Town;
import pandemic.player.Player;
import pandemic.player.Role;

public class TreatDisease implements Action {

    private final String description;

    public TreatDisease() {
        this.description = "Treat a disease";
    }

    public String getDescription() {
        return this.description;
    }

    /**
     * Run an action chosen by the player
     * 
     * @param player The player who executes the action
     * @return 0 if the action has been successfully made, 1 if the player exit, -1 if the action failed
     */
    public int run(Player player, Object o) {
        Disease chosenDisease = (Disease)o;
        Town playerTown = player.getTown();
        if (player.getRole() == Role.DOCTOR) {
            playerTown.setInfectionState(0, chosenDisease);
        }
        else {
            playerTown.decreaseInfectionState(chosenDisease);
        }
        return 0;
    }

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

    public int runWithChoice(Player player, Scanner sc) {
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
                return 1;
            }
            chosenDisease = diseasesByName.get(diseaseName);
        }
        if (chosenDisease != null) {
            System.out.println("\nThe current town infection state for the disease " + chosenDisease.getName() + " has been decreased by 1, it is now of " + playerTown.getInfectionState(chosenDisease));
            return this.run(player, chosenDisease); 
        }
        System.out.println("\nThis town is not infected by this disease, nothing happened");
        return 1;
    }
}
