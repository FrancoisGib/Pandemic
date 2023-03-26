package pandemic.player;

import pandemic.Disease;
import pandemic.cards.Card;

import java.util.HashMap;
import java.util.Scanner;

/* The class that defines a Scientist in the game, it is inherited of the Player class */
public class Scientist extends Player {

	/**
	 * Builds a Scientist for the game
	 *
	 * @param name The name of the scientist
	 */
    public Scientist(String name) {
        super(name);
    }

	/**
	 * Discover a cure for a disease
	 * 
	 * @param sc The scanner to choose which disease the scientist wants to find a cure for (a scientist needs just 4 cards to discover a cure instead of 5)
	 * @return True if the disease has been cured, else false
	 */
    public boolean discoverCure(Scanner sc) {
		if (this.town.hasResearchCenter()) {
			HashMap<Disease, Integer> diseases = this.town.getAllInfectionState();
			System.out.println("Choose a disease to find a cure :");
			HashMap<String, Disease> diseasesByName = new HashMap<String, Disease>();
			for (Disease disease : diseases.keySet()) {
				diseasesByName.put(disease.getName(), disease);
				System.out.println(disease.getName() + " / ");
			}
			String diseaseName = sc.next();
			Disease chosenDisease = diseasesByName.get(diseaseName);
			if (chosenDisease == null) {
				System.out.println("This disease does not exist or the town is not infected by it, retry");
				return this.discoverCure(sc);
			} else if (this.getCardsNumberByDisease(chosenDisease) > 3) {
				boolean cured = chosenDisease.cure();
				for (Card card : this.getCardsByDisease(chosenDisease)) { // Discard the cards used to cure the disease
					this.discardCard(card);
				}
				if (cured) {
					System.out.println("The disease " + chosenDisease.getName() + " has been cured");
					return true;
				} else {
					System.out.println("The disease " + chosenDisease.getName() + " was already cured");
					chooseAction(sc);
				}
			} else {
				System.out.println("This town is not infected by this disease, retry");
				return this.discoverCure(sc);
			}
		} else {
			System.out.println("The town you're on doesn't have a research center, build one to find a cure.");
		}
		return false;
	}
}
