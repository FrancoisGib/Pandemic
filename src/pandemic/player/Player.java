package pandemic.player;

import java.util.List;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Scanner;

import pandemic.Town;
import pandemic.cards.Card;
import pandemic.NoSuchTownException;

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
		this.cards = new ArrayList<Card>();
	}
	
	public void movableTowns(Town town, HashSet<Town> movableTowns) {
		for (Town neighbor : town.getNeighbors()) {
			movableTowns.add(neighbor);
		}
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
	
	public ArrayList<Town> getPossibleMove() {
		return this.town.getNeighbors();
	}

	public Town getTown() {
		return this.town;
	}
	
	public String getTownName() {
		return this.town.getName();
	}

	public void move(Scanner sc) throws NoSuchTownException {
		System.out.println("Choose a city to move on, here the list of cities :\n\n");
		String res = "";
		HashSet<Town> movableTowns = new HashSet<Town>();
		this.movableTowns(this.town, movableTowns);
		for (Town town : movableTowns) {
			if (town != this.town) {
				res += (town.getName() + " / ");
			}
		}
		System.out.println(res + "\n");
		System.out.println("Enter a town name !\n");
		String townName = sc.nextLine();
		boolean found = false;
		Iterator<Town> it = movableTowns.iterator();
		while (it.hasNext() && !found) {
			Town newTown = it.next();
			if (townName.equals(newTown.getName())) {
				this.town = newTown;
				found = true;
			}
		}
		if (!found) {
			throw new NoSuchTownException("This town doesn't exist");
		}
	}

}
