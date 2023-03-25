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
		System.out.println("Choose a disease to treat :");
		HashMap<String, Disease> diseasesByName = new HashMap<String, Disease>();
		for (Disease disease : diseases.keySet()) {
			diseasesByName.put(disease.getName(), disease);
			System.out.println(disease.getName() + " / ");
		}
		String diseaseName = sc.next();
		Disease chosenDisease = diseasesByName.get(diseaseName);
		this.town.decreaseInfectionState(chosenDisease);
		System.out.println("The current town infection state for the disease " + diseaseName + "has been decreased by 1, it is now of " + this.town.getInfectionState(chosenDisease));
	}
}
