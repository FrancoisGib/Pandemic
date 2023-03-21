package pandemic.player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Scanner;

import pandemic.Town;
import pandemic.cards.Card;
import pandemic.Disease;
import pandemic.NoSuchDiseaseException;
import pandemic.NoSuchTownException;

/* The class that defines a player in the game */
public abstract class Player {

	/* The Player's name */
	protected String name;
	
	/* The town where the Player is */
	protected Town town;
	
	protected ArrayList<Card> cards;
	
	public Player(String name, Town town) {
		this.name = name;
		this.town = town;
		this.cards = new ArrayList<Card>();
	}
	
	public String getName() {
		return this.name;
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
		String townName = sc.next();
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

	public void treatDisease(Scanner sc) throws NoSuchDiseaseException {
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
	
	public void chooseAction(Scanner sc) throws NoSuchTownException, NoSuchDiseaseException {
		System.out.println("Choose an action by entering a number !\n");
		System.out.println("1 -> Move to another city\n2 -> Build a research center in your current city\n3 -> Find a cure\n4 -> Treat a disease\n5 -> Do nothing ");
		int actionNumber = sc.nextInt();
		switch(actionNumber) {
			case 1: // Move the player to another town
				this.move(sc);
				System.out.println("Le joueur " + this.getName() +" est sur la ville : " + this.getTownName());
				break;
			case 2: // Build a research center in the town the player is on
				this.town.buildResearchCenter();
				System.out.println("A research center has been built in " + this.town.getName());
				break;
			case 3: // Discover a cure
				System.out.println("A cure has been discovered for the ");
				break;
			case 4: // Treat a disease
				this.treatDisease(sc);
				break;
			case 5: // Do nothing
				System.out.println("You chose to do nothing during this round.");
				break;
		}
	}	

}
