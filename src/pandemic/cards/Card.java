package pandemic.cards;
import pandemic.Town;
import pandemic.Disease;

/** The Card class in the game */
public class Card {

	/** The town of the card */
	private Town town;

	/** The disease of the card */
	private Disease disease;

	/**
	 * Builds a Card for the game
	 * 
	 * @param town The town of the card
	 * @param disease The disease of the card
	 */
	public Card(Town town, Disease disease) {
		this.town = town;
		this.disease = disease;
	}

	/**
	 * Get the Card's town name
	 * 
	 * @return The card's town name
	 */
	public String getTownName() {
		return this.town.getName();
	}

	/**
	 * Give the associated town of the card
	 * 
	 * @return The town associated to this card
	 */
	public Town getTown() {
		return this.town;
	}

	/**
	 * Give the associated disease name of the card
	 * 
	 * @return The disease name associated to this card
	 */
	public String getDiseaseName() {
		return this.disease.getName();
	}

	/**
	 * Give the associated disease of the card
	 * 
	 * @return The disease associated to this card
	 */
	public Disease getDisease() {
		return this.disease;
	}
}
