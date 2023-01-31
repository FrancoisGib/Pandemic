package pandemic.cards;

import pandemic.Disease;

public abstract class Card {
	private String town;
	private Disease disease;
	
	public Card(String town,Disease disease) {
		this.town  =town;
		this.disease = disease;
	}

	/**
	 * @return the town
	 */
	public String getTown() {
		return town;
	}

	/**
	 * @return the disease
	 */
	public Disease getDisease() {
		return disease;
	}
	
}
