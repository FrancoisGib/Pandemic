package pandemic.player;

import java.io.IOException;
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
import pandemic.actions.Action;

/* The class that defines a Player in the game */
public class Player {

	/* The Player's name */
	private final String name;

	private final Role role; 

	private ArrayList<Action> actions;

	/* The town where the Player is */
	private Town town;

	/* The cards the player has in hands */
	private ArrayList<Card> cards;

	/**
	 * Builds a player for the game (it is an abstract class, so a player can't be
	 * initialized but this constructor is used by the inherited classes)
	 * 
	 * @param name The name of the player
	 */
	public Player(String name, Role role) {
		this.name = name;
		this.role = role;
		this.actions = null;
		this.town = null;
		this.cards = new ArrayList<Card>();
	}

	/**
	 * Put the player on the town in parameter
	 * 
	 * @param town The town to move the player on
	 */
	public void setTown(Town town) {
		this.town = town;
	}

	public void setActions(ArrayList<Action> actions) {
		this.actions = actions;
	}

	/**
	 * Give the name of the player
	 * 
	 * @return The name of the player
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * Give the role of the player
	 * 
	 * @return The role of the player
	 */
	public Role getRole() {
		return this.role;
	}

	/**
	 * Add the neighbors of a town to an HashSet
	 * 
	 * @param town         The town to get neighbors
	 * @param movableTowns The HashSet of towns to add the neighbors in
	 */
	public void movableTowns(Town town, HashSet<Town> movableTowns) {
		if (this.role != Role.GLOBETROTTER) {
			for (Town neighbor : town.getNeighbors()) {
				movableTowns.add(neighbor);
			}
		}
		else {
			boolean res = true;
			while (res) {
				ArrayList<Town> neighbors = town.getNeighbors();
				for(Town t : neighbors) {
					if (movableTowns.contains(t)) {
						res = false;
					}
					else {
						movableTowns.add(t);
						this.movableTowns(t, movableTowns);
						res = true;
					}
				}
				if (!res) {
					break;
				}
			}
		}

	}

	/**
	 * Give the current player's town
	 * 
	 * @return The town the player is on
	 */
	public Town getTown() {
		return this.town;
	}

	/**
	 * Get the current player's town name
	 * 
	 * @return The name of the town
	 */
	public String getTownName() {
		return this.town.getName();
	}

	/**
	 * Choose an action to do
	 * 
	 * @param sc The scanner to choose the action
	 */
	public void chooseAction(Scanner sc) throws IOException {
		String print = "Choose an action by entering a number !\n";
		ArrayList<Action> availableActions = new ArrayList<Action>();
		int i = 1;
		Iterator<Action> it = this.actions.iterator();
		while (it.hasNext()) {
			Action action = it.next();
			if (action.requirements(this)) {
				availableActions.add(action);
				print += i + " -> " + action.getDescription() + "\n";
				i++;
			}
		}
		print += i + " -> " + "Do nothing";
		System.out.println(print);
        int actionNumber =  sc.nextInt()-1;  
		int availableActionsSize = availableActions.size();
		if (actionNumber < availableActionsSize) {
			int res = availableActions.get(actionNumber).run(this, sc);
			if (res == -1) {
				availableActions.get(actionNumber).run(this, sc);
			}
			else if (res == 1) {
				this.chooseAction(sc);
			}
		}
	}

	/**
	 * Pick a card into a cardStack, if the player has already 6 cards, the card is
	 * discarded
	 * 
	 * @param cards The CardsStack to pick a card in
	 * @return True if the player picked the card successfully, else false (the
	 *         CardsStack is empty)
	 */
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

	/**
	 * Give a town cards the player has in hand
	 * 
	 * @param town The town to get cards
	 * @return The list of cards associated to that town
	 */
	public ArrayList<Card> getCardsByTown(Town town) {
		ArrayList<Card> townCards = new ArrayList<Card>();
		for (Card card : this.cards) {
			if (card.getTown() == town) {
				townCards.add(card);
			}
		}
		return townCards;
	}

	/**
	 * Give a disease cards the player has in hand
	 * 
	 * @param disease The disease to get cards
	 * @return The list of cards associated to that disease
	 */
	public ArrayList<Card> getCardsByDisease(Disease disease) {
		ArrayList<Card> diseaseCards = new ArrayList<Card>();
		for (Card card : this.cards) {
			if (card.getDisease() == disease) {
				diseaseCards.add(card);
			}
		}
		return diseaseCards;
	}

	/**
	 * Remove a card from the player's hand (the player used the card)
	 * 
	 * @param card The card to remove from the hand
	 * @return True if the card has been successfully removed, else false (the
	 *         player doesn't have this card in hand)
	 */
	public boolean discardCard(Card card) {
		return this.cards.remove(card);
	}

	/**
	 * Give a string to print in the console, to visualize the cards the player has
	 * 
	 * @return The string to print
	 */
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
