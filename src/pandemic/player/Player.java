package pandemic.player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Map.Entry;

import pandemic.Town;
import pandemic.cards.Card;
import pandemic.cards.CardsStack;
import pandemic.Disease;

/* The class that defines a player in the game */
public abstract class Player {

	/* The Player's name */
	protected String name;

	/* The town where the Player is */
	protected Town town;

	protected ArrayList<Card> cards;

	public Player(String name) {
		this.name = name;
		this.town = null;
		this.cards = new ArrayList<Card>();
	}

	public void setTown(Town town) {
		this.town = town;
	}

	public String getName() {
		return this.name;
	}

	public void movableTowns(Town town, HashSet<Town> movableTowns) {
		for (Town neighbor : town.getNeighbors()) {
			movableTowns.add(neighbor);
		}
	}

	public int getTownCardsNumber(Town town) {
		int cpt = 0;
		for (Card card : cards) {
			if (card.getTownName().equals(town.getName())) {
				cpt++;
			}
		}
		return cpt;
	}

	public int getCardsNumberByDisease(Disease disease) {
		int cpt = 0;
		for (Card card : cards) {
			if (card.getDisease() == disease) {
				cpt++;
			}
		}
		return cpt;
	}

	public boolean buildResearchCenter() {
		if (this.getTownCardsNumber(this.town) > 0 && !this.town.hasResearchCenter()) {
			boolean res = this.town.buildResearchCenter();
			if (res) {
				ArrayList<Card> townCards = this.getCardsByTown(this.town);
				Card removedCard = townCards.get(0);
				this.discardCard(removedCard);
			}
			return res;
		}
		return false;
	}

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
			} else if (this.getCardsNumberByDisease(chosenDisease) > 4) {
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

	public ArrayList<Town> getPossibleMove() {
		return this.town.getNeighbors();
	}

	public Town getTown() {
		return this.town;
	}

	public String getTownName() {
		return this.town.getName();
	}

	public void move(Scanner sc) {
		System.out.println("Choose a city to move on, here the list of cities :\n\n");
		String res = "";
		HashSet<Town> movableTowns = new HashSet<Town>();
		this.movableTowns(this.town, movableTowns);
		for (Town town : movableTowns) {
			if (town != this.town) {
				res += (town.getName() + " / ");
			}
		}
		res += "exit";

		System.out.println(res + "\n");
		System.out.println("Enter a town name !\n");

		String townName = sc.next();
		boolean found = false;

		if (townName.equals("exit")) {
			found = true;
			this.chooseAction(sc);
		}
		Iterator<Town> it = movableTowns.iterator();
		while (it.hasNext() && !found) {
			Town newTown = it.next();
			if (townName.equals(newTown.getName())) {
				this.town = newTown;
				found = true;
			}
		}
		if (!found) {
			System.out.println("This town doesn't exist, retry");
			this.move(sc);
		}
	}

	public void treatDisease(Scanner sc) {
		HashMap<Disease, Integer> diseases = this.town.getAllInfectionState();
		HashMap<String, Disease> diseasesByName = new HashMap<String, Disease>();
		int cpt = 0;
		String res = "";
		for (Disease disease : diseases.keySet()) {
			if (diseases.get(disease) > 0) {
				cpt++;
				diseasesByName.put(disease.getName(), disease);
				res += disease.getName() + " / ";
			}
		}
		if (cpt > 0) {
			System.out.println("Choose a disease to treat :");
			System.out.println(res);
			String diseaseName = sc.next();
			Disease chosenDisease = diseasesByName.get(diseaseName);
			if (chosenDisease != null) {
				this.town.decreaseInfectionState(chosenDisease);
			}
		} else {
			System.out.println("\nThere's no disease to treat in that town");
		}
	}

	public void chooseAction(Scanner sc) {
		System.out.println("Choose an action by entering a number !\n");
		System.out.println(
				"1 -> Move to another city\n2 -> Build a research center in your current city\n3 -> Find a cure\n4 -> Treat a disease\n5 -> Do nothing \n");
		int actionNumber = sc.nextInt();
		switch (actionNumber) {
			case 1: // Move the player to another town
				this.move(sc);
				System.out.println("Le joueur " + this.getName() + " est sur la ville : " + this.getTownName() + "\n");
				System.out.println(this.town.toString());
				break;
			case 2: // Build a research center in the town the player is on
				this.town.buildResearchCenter();
				System.out.println("A research center has been built in " + this.town.getName());
				break;
			case 3: // Discover a cure
				this.discoverCure(sc);
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

	public boolean pickPlayerCard(CardsStack cards) {
		Card card = cards.pickCard();
		if (card == null) {
			return false;
		}
		if (card.getTown() != null) {
			if (this.cards.size() == 6) {
				cards.discardCard(card);
			} else {
				this.cards.add(card);
			}
		}
		return true;
	}

	public ArrayList<Card> getCardsByTown(Town town) {
		ArrayList<Card> townCards = new ArrayList<Card>();
		for (Card card : this.cards) {
			if (card.getTown() == town) {
				townCards.add(card);
			}
		}
		return townCards;
	}

	public ArrayList<Card> getCardsByDisease(Disease disease) {
		ArrayList<Card> diseaseCards = new ArrayList<Card>();
		for (Card card : this.cards) {
			if (card.getDisease() == disease) {
				diseaseCards.add(card);
			}
		}
		return diseaseCards;
	} 

	public boolean discardCard(Card card) {
		return this.cards.remove(card);
	}

	public String cardToString() {

		String res = "Cards you have by disease : ";
		HashMap<Disease, Integer> cardCount = new HashMap<Disease, Integer>();
		for (Card card : cards) {
			Disease d = card.getDisease();
			if (cardCount.containsKey(d)) {
				int i = cardCount.get(d);
				cardCount.replace(d, i + 1);
			} else {
				cardCount.put(d, 1);
			}
		}
		Iterator<Entry<Disease, Integer>> iterator = cardCount.entrySet().iterator();
		while (iterator.hasNext()) {
			Entry<Disease, Integer> mapEntry = (Entry<Disease, Integer>) iterator.next();
			res += mapEntry.getKey().getName() + " = " + mapEntry.getValue() + " / ";
		}
		res += "\n\n Cards you have by town : ";

		HashSet<Town> allTowns = new HashSet<Town>();
		for (Card card : cards) {
			Town t = card.getTown();
			if (!allTowns.contains(t)) {
				allTowns.add(t);
				res += t.getName() + " : " + this.getCardsByTown(t).size() + " / ";
			}
		}
		return res;
	}

}
