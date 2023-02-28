package pandemic;

import java.util.*;

/* The class that defines a player in the game */
public class Globetrotter extends Player {
	/* The list of cards the Player has */
	private List<PlayerCardsStack> cards;
	
	private HashSet<Town> towns;
	
	public Globetrotter(String name,Town town) {
		super(name,town);
		List<PlayerCardsStack> cards = new ArrayList<PlayerCardsStack>();
		
		this.towns = new HashSet<Town>();
		this.initTowns(town);
	}
	
	public void initTowns(Town town) {
		boolean res = true;
		while (res) {
			ArrayList<Town> neighbors = town.getNeighbors();
			
			for(Town t : neighbors) {
				if (towns.contains(t)) {
					res = false;
				}
				else {
					this.towns.add(t);
					this.initTowns(t);
					res = true;
				}
			}
			if (!res) {
				break;
			}
		}
	}

	public HashSet<Town> getTowns() {
		return this.towns;
	}
}
