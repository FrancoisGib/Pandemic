package pandemic;

<<<<<<< HEAD
import java.util.*;

import pandemic.cards.Card;
=======
import java.util.List;
>>>>>>> 352eef3fbdfecc1c15e20a751005f81ddf898923

/* The class that defines a player in the game */
public abstract class Player {

	/* The Player's name */
	protected String name;
	
	/* The town where the Player is */
	protected Town town;
	
<<<<<<< HEAD
	
	protected List<Card> cards;
	
	
	public Player(String name, Town town) {
		this.name = name;
		this.town = town;
		ArrayList<Card> cards = new ArrayList<Card>();
	}
	
	public int getCurrentTownCardsNumber() {
		int cpt = 0;
		for(Card card : cards) {
			if (card.getTownName().equals(this.town.getName())) {
				cpt++;
			}
		}
		return cpt;
	}
	
	public boolean buildResearchCenter() {
		if (this.getCurrentTownCardsNumber() > 0 && !this.town.hasResearchCenter()) {
			this.town.buildResearchCenter();
			return true;
		}
		return false;
	}
	
	public boolean discoverCure() {
		if (this.town.hasResearchCenter() && this.getCurrentTownCardsNumber() > 4) {
			return true;
		}
		return false;
	}
	
	public HashSet<Town> getTowns() {
		return this.town.getNeighbors();
	}
=======
	/* The list of cards the Player has */
	protected List<PlayerCardsStack> cards;
	
	
	
>>>>>>> 352eef3fbdfecc1c15e20a751005f81ddf898923
}
