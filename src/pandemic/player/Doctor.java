package pandemic.player;

import java.util.Scanner;
import java.util.HashMap;

import pandemic.Disease;

/* The class that defines a Doctor in the game, it is inherited of the Player class */
public class Doctor extends Player {

	/**
	 * Builds a Doctor for the game
	 *
	 * @param name The name of the doctor
	 */
    public Doctor(String name) {
        super(name);
    }

	/**
	 * Treat a disease by deleting all cubes of infection in the doctor's town
	 * 
	 * @param sc The scanner to choose which disease to treat
	 */
    public void treatDisease(Scanner sc) {
		HashMap<Disease, Integer> diseases = this.town.getAllInfectionState();
		HashMap<String, Disease> diseasesByName = new HashMap<String, Disease>();
		int cpt = 0;
		String res = "";
		for (Disease disease : diseases.keySet()) {
			if (diseases.get(disease) > 0) {
				cpt++;
				diseasesByName.put(disease.getName(), disease);
				res += disease.getName() + " / ";
			}
		}
		if (cpt > 0) {
			System.out.println("Choose a disease to treat :" + res + "exit");
			String diseaseName = sc.next();
			if (diseaseName.equals("exit")) {
				this.chooseAction(sc);
			}
			Disease chosenDisease = diseasesByName.get(diseaseName);
			if (chosenDisease != null) {
				this.town.setInfectionState(0, chosenDisease);
				System.out.println("\nThe infection state in this town is now 0");
			}
			else {
				System.out.println("\nThere's no disease of that name to treat");
				treatDisease(sc);
			}
		} else {
			System.out.println("\nThere's no disease to treat in that town");
		}
	}
}
