package pandemic;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.InputMismatchException;
import java.util.Iterator;
import java.util.Random;
import java.util.Scanner;
import java.util.Stack;
import java.util.Map.Entry;

import pandemic.player.Player;
import pandemic.actions.Action;
import pandemic.cards.*;

public class Game {

    /* The number of clusters before the game is lost */
    private static final int MAX_CLUSTERS_NUMBER = 8;

    /* The initial infection state */
    private static int INITIAL_INFECTION_STATE = 2;

    /* The current global infection state */
    private int globalInfectionState;

    /* The current number of clusters */
    private int clustersNumber;

    /* The map of the game, which handles all towns changes */
    private Map map;

    /* The players of the game */
    private ArrayList<Player> players;

    /* The diseases of the game */
    private ArrayList<Disease> diseases;

    /* The player cards stack which handles the player cards */
    private CardsStack playerCardsStack;

    /* The infection cards stack which handles the infection cards */
    private CardsStack infectionCardsStack;

    private ArrayList<Action> actions;

    private Scanner sc;

    /**
     * Builds a game to be played
     * 
     * @param map      The map of the game, to handle the towns
     * @param players  The players of the game
     * @param diseases The diseases of the game
     */
    public Game(Map map, ArrayList<Player> players, ArrayList<Disease> diseases, ArrayList<Action> actions) {
        this.globalInfectionState = INITIAL_INFECTION_STATE;
        this.clustersNumber = 0;
        this.map = map;
        this.actions = actions;
        this.players = players;
        this.diseases = diseases;
        this.sc = new Scanner(System.in);
        this.initGame();
    }

    /**
     * Initialize the game by initializing cards, players' hands, initial town and
     * the initial infection of the game
     */
    public void initGame() {
        this.initCards();
        this.initPlayersHand();
        this.initInitialTown();
        this.initialInfection();
    }

    /**
     * Initialize the two cardsStacks of the game
     */
    public void initCards() {
        ArrayList<Town> towns = this.map.getTowns();
        ArrayList<Card> playerCards = new ArrayList<Card>();
        for (Town town : towns) {
            int sector = town.getSector();
            boolean found = false;
            Iterator<Disease> it = this.diseases.iterator();
            Disease disease = null;
            while (!found && it.hasNext()) {
                disease = it.next();
                if (disease.getSector() == sector) {
                    found = true;
                }
            }
            town.setInfectionState(0, disease);
            Card card = new Card(town, disease);
            playerCards.add(card);
        }
        ArrayList<Card> infectionCards = new ArrayList<>(playerCards);
        this.playerCardsStack = new CardsStack(playerCards);
        this.infectionCardsStack = new CardsStack(infectionCards);
    }

    /**
     * Initialize the players' hands
     */
    public void initPlayersHand() {
        int cpt = 4 - this.players.size() + 2; // Number of cards per player initially.
        for (Player player : this.players) {
            for (int i = 0; i < cpt; i++) {
                this.pickPlayerCard(player);
            }
        }
        ArrayList<Stack<Card>> splitCards = this.playerCardsStack.splitCards(4);
        for (Stack<Card> cStack : splitCards) {
            cStack.add(new Card(null, null));
        }
        this.playerCardsStack.mergeStacks(splitCards);
    }

    /**
     * Move all the players on a random town in the map
     */
    public void initInitialTown() {
        Random random = new Random();
        int initialTownIndex = random.nextInt(map.getTowns().size());
        Town initialTown = this.map.getTown(initialTownIndex);
        for (Player player : this.players) {
            player.setTown(initialTown);
        }
    }

    /**
     * Make the initial infection of the game
     */
    public void initialInfection() {
        for (int i = 3; i > 0; i--) {
            for (int j = 0; j < 3; j++) {
                Card card = this.infectionCardsStack.pickCard();
                card.getTown().setInfectionState(i, card.getDisease());
            }
        }
        this.computeGlobalInfectionState();
        this.computeClustersNumber();
    }

    /**
     * Starts an infection phase in the game
     * 
     * @param n The number of cards to pick in the infection cards
     */
    public void startInfectionPhase(int n) {
        int tempo = n; // Sinon problème avec le for qui va changer et donc problèmes avec les cartes
        for (int i = 0; i < tempo; i++) {
            this.pickInfectionCard();
        }
        this.computeGlobalInfectionState();
        this.computeClustersNumber();
    }

    /**
     * Update the number of clusters in the game by adding one to it
     */
    public void computeClustersNumber() {
        this.clustersNumber = this.map.getClustersNumber();
    }

    /**
     * Compute all infection states and return the new global infection state
     * 
     * @return The new global infection state of the game
     */
    public void computeGlobalInfectionState() {
        this.globalInfectionState = this.map.getGlobalInfectionState() + INITIAL_INFECTION_STATE;
    }

    /**
     * Tell if the game is lost or not
     * 
     * @return True if the game is lost, else false
     */
    public boolean loose() {
        if (this.clustersNumber > MAX_CLUSTERS_NUMBER) {
            return true;
        }
        return false;
    }

    /**
     * Tell if the game is won or not
     * 
     * @return True if the game is won, else false
     */
    public boolean win() {
        for (Disease disease : this.diseases) {
            if (!disease.isCured()) {
                return false;
            }
        }
        return true;
    }

    /**
     * Get the game's map
     * 
     * @return The map of the game
     */
    public Map getMap() {
        return this.map;
    }

    /**
     * Get the infection cards stack of the game
     * 
     * @return The infection cards stack of the game
     */
    public CardsStack getInfectionCardsStack() {
        return this.infectionCardsStack;
    }

    /**
     * Get the player cards stack of the game
     * 
     * @return The player cards stack of the game
     */
    public CardsStack getPlayerCardsStack() {
        return this.playerCardsStack;
    }

    /**
     * Get all the players in the game
     * 
     * @return All the players in the game
     */
    public ArrayList<Player> getPlayers() {
        return this.players;
    }

    /**
     * Pick an infection card in the infection cards stack and then make the actions
     * associated
     */
    public void pickInfectionCard() {
        Card card = this.infectionCardsStack.pickCard();
        Town town = card.getTown();
        Disease disease = card.getDisease();
        if (!disease.isCured()) {
            this.infectionCardsStack.discardCard(card);
            town.updateInfectionState(disease);
            if (town.isCluster(disease)) {
                this.propagation(town, disease);
            }
            this.computeGlobalInfectionState();
            this.computeClustersNumber();
        }
    }

    /**
	 * Pick a card into a cardStack, if the player has already 6 cards, the card is
	 * discarded
	 * 
	 * @param player The Player who picks a card
	 * @return True if the player picked the card successfully, else false (the
	 *         CardsStack is empty)
	 */
	public boolean pickPlayerCard(Player player) {
		Card card = this.playerCardsStack.pickCard();
        ArrayList<Card> playerCards = player.getCards();
		if (card == null) {
			return false;
		}
        if (card.getTown() == null) {
            INITIAL_INFECTION_STATE++;
            this.startInfectionPhase(1);
            this.infectionCardsStack.resetStack();
            return true;
        }

		if (playerCards.size() == 7) {
            String res = "You have 7 cards in your hand, you must discard one, choose : \n";
            int i = 1;
            for (Card c : playerCards) {
                res += i + " -> " + c.getTownName() + " " + c.getDiseaseName() + " / ";
            }
            System.out.println(res + "\n");
            int choice = -1; 
            String act = "";
            while (choice < 1 && choice > 7) {
                act = sc.next();
                try {
                    choice = Integer.parseInt(act);
                }
                catch (NumberFormatException e) {
                    System.out.println("Put an integer between 1 and 7 :\n");
                }
            }
			Card removedCard = playerCards.remove(choice-1);
            System.out.println("The card with town " + removedCard.getTownName() + " and disease " + removedCard.getDiseaseName() + " has been removed from your hand\n");
		}
        player.getCards().add(card);
		return true;
	}

    /**
     * Propagate the diseases from the clusters to the neighboring towns
     */
    public void propagation(Town town, Disease disease) {
        HashSet<Town> visited = new HashSet<Town>();
        ArrayList<Town> toVisit = new ArrayList<Town>();
        toVisit.add(town);
        while (toVisit.size() > 0) {
            Town currentTown = toVisit.remove(0);
            visited.add(currentTown);
            for (Town neighbor : town.getNeighbors()) {
                neighbor.updateInfectionState(disease);
                if (neighbor.isCluster(disease) && !visited.contains(neighbor)) {
                    toVisit.add(neighbor);
                }
            }
        }
    }

    /**
     * Give a String that describes the infections states in all the towns of the
     * map
     * 
     * @return The String describing all the states
     */
    public String toStringInfectionState(Player p) {
        String res = "";
        for (Town town : this.map.getTowns()) {
            int cpt = 0;
            String townRes = "";
            HashMap<Disease, Integer> diseases = town.getAllInfectionState();
            for (Disease disease : diseases.keySet()) {
                if (diseases.get(disease) > 0) {
                    cpt++;
                    townRes += disease.getName() + " : " + diseases.get(disease) + " / ";
                }
            }
            if (cpt > 0) {
                res += town.getName() + " infection state : " + townRes + "Shortest path"
                        + this.shortestPathToString(p.getTown(), town) + "\n";
                cpt = 0;
            }
        }
        return res;
    }

    /**
     * Print the state of the game for the player p
     * 
     * @param p The player who is playing
     */
    public void print(Player p) {
        System.out.println(this.toStringInfectionState(p));
        System.out.println("_____________________________________\n");
        System.out.println("\n Player to play : " + p.getName() + "\n");

        System.out.println("\n The number of cluster is : " + this.clustersNumber + "\n");
        System.out.println(" The global infection state is : " + this.globalInfectionState + "\n");
        Town town = p.getTown();
        System.out.println(" " + town.toString() + "\n");
        System.out.println(" " + p.cardToString() + "\n\n");
        System.out.println("_____________________________________\n");
    }

    /**
     * Run the game while the game is not won or lost
     * 
     * @param sc The scanner of the game to make all decisions
     */
    public boolean run() {
        while (!this.loose() && !this.win()) {
            for (Player player : this.players) {
                for (int i = 0; i < 4; i++) {
                    this.print(player);
                    this.chooseAction(player);
                }
                this.pickPlayerCard(player);
                boolean j = this.pickPlayerCard(player);
                if (!j) {
                    System.out.println("You have lost");
                }
            }
            INITIAL_INFECTION_STATE++;
            this.startInfectionPhase(this.globalInfectionState);
        }
        sc.close();
        if (this.loose()) {
            this.toStringInfectionState(null);
            return false;
        }
        return true;
    }

    public Stack<Town> shortestPath(Town t1, Town t2) {
        ArrayList<Node> toVisit = new ArrayList<Node>();
        HashSet<Node> visited = new HashSet<Node>();
        Node root = new Node(null, t1);
        toVisit.add(root);
        while (!toVisit.isEmpty()) {
            Node current = toVisit.remove(0);
            visited.add(current);
            if (current.getCurrent() == t2) {
                return current.getPath();
            }
            for (Town t : current.getCurrent().getNeighbors()) {
                Node next = new Node(current, t);
                if (!visited.contains(next)) {
                    toVisit.add(next);
                }
            }
        }
        return null;
    }

    public String shortestPathToString(Town t1, Town t2) {
        Stack<Town> path = shortestPath(t1, t2);
        String res = "";
        while (!path.isEmpty()) {
            Town t = path.pop();
            res += " --> " + t.getName();
        }
        return res;
    }

    /**
	 * Choose an action to do
	 * 
	 * @param sc The scanner to choose the action
	 */
	public void chooseAction(Player player) {
		String print = "Choose an action by entering a number !\n";
		ArrayList<Action> availableActions = new ArrayList<Action>();
		int i = 1;
		Iterator<Action> it = this.actions.iterator();
		while (it.hasNext()) {
			Action action = it.next();
			if (action.requirements(player)) {
				availableActions.add(action);
				print += i + " -> " + action.getDescription() + "\n";
				i++;
			}
		}
		print += i + " -> " + "Do nothing";
		System.out.println(print);
		int actionNumber = -1; 
        String act = "";
		while (actionNumber == -1) {
			act = sc.next();
			try {
				actionNumber = Integer.parseInt(act)-1;
			}
			catch (NumberFormatException e) {
				System.out.println("Not an integer, retry");
			}
		}
		int availableActionsNumber = availableActions.size();
		if (actionNumber < availableActionsNumber) {
			int res = availableActions.get(actionNumber).runWithChoice(player, this.sc);
			if (res == -1) {
				availableActions.get(actionNumber).runWithChoice(player, this.sc);
			}
			else if (res == 1) {
				this.chooseAction(player);
			}
		}
		else {
			if (actionNumber > availableActionsNumber) {
				System.out.println("Number out of range, retry \n");
				this.chooseAction(player);
			}
		}
	}

}