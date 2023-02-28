package pandemic;

import java.util.*;

import pandemic.cards.Card;

/* The class that defines a player in the game */
public abstract class Player {

	/* The Player's name */
	protected String name;
	
	/* The town where the Player is */
	protected Town town;
	
	
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
	
	public void move() {
		
	}
}
