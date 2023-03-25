package pandemic.cards;
import pandemic.Town;
import pandemic.Disease;

public class Card {
	private Town town;
	private Disease disease;

	public Card(Town town, Disease disease) {
		this.town = town;
		this.disease = disease;
	}

	public Card() {
		this.town = null;
		this.disease = null;
	}

	/**
	 * @return the town
	 */
	public String getTownName() {
		return this.town.getName();
	}

	public Town getTown() {
		return this.town;
	}

	public String getDiseaseName() {
		return this.disease.getName();
	}

	/**
	 * @return the  disease
	 */
	public Disease getDisease() {
		return this.disease;
	}
}
