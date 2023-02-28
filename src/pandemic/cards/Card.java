package pandemic.cards;

<<<<<<< HEAD
import pandemic.Disease;

public abstract class Card {
	private String town;
	private Disease disease;
	
	public Card(String town,Disease disease) {
		this.town = town;
		this.disease = disease;
	}

	/**
	 * @return the town
	 */
	public String getTownName() {
		return town;
	}

	/**
	 * @return the  disease
	 */
	public Disease getDisease() {
		return disease;
	}
	
	public abstract void pickCard();
=======
public class Card {
    
>>>>>>> 352eef3fbdfecc1c15e20a751005f81ddf898923
}
