package pandemic.player;

import java.util.Scanner;
import java.util.HashMap;

import pandemic.Disease;

public class Doctor extends Player {
    public Doctor(String name) {
        super(name);
    }

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
