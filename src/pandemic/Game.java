package pandemic;

import java.util.ArrayList;
<<<<<<< HEAD
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Random;
import java.util.Stack;
import java.util.Map.Entry;
import java.util.Scanner;

import pandemic.player.*;
=======
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;
import java.util.Scanner;
import java.util.Stack;

import pandemic.player.Player;
import pandemic.actions.Action;
>>>>>>> François
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

<<<<<<< HEAD
=======
    private ArrayList<Action> actions;

    private Scanner sc;

>>>>>>> François
    /**
     * Builds a game to be played
     * 
     * @param map      The map of the game, to handle the towns
     * @param players  The players of the game
     * @param diseases The diseases of the game
     */
<<<<<<< HEAD
    public Game(Map map, ArrayList<Player> players, ArrayList<Disease> diseases) {
        this.globalInfectionState = INITIAL_INFECTION_STATE;
        this.clustersNumber = 0;
        this.map = map;
        this.players = players;
        this.diseases = diseases;
=======
    public Game(Map map, ArrayList<Player> players, ArrayList<Disease> diseases, ArrayList<Action> actions) {
        this.globalInfectionState = INITIAL_INFECTION_STATE;
        this.clustersNumber = 0;
        this.map = map;
        this.actions = actions;
        this.players = players;
        this.diseases = diseases;
        this.sc = new Scanner(System.in);
>>>>>>> François
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
<<<<<<< HEAD

        for (Town town : towns) {
            int sector = town.getSector();

            boolean found = false;
            Iterator<Disease> it = this.diseases.iterator();
            Disease disease = null;

=======
        for (Town town : towns) {
            int sector = town.getSector();
            boolean found = false;
            Iterator<Disease> it = this.diseases.iterator();
            Disease disease = null;
>>>>>>> François
            while (!found && it.hasNext()) {
                disease = it.next();
                if (disease.getSector() == sector) {
                    found = true;
                }
            }
<<<<<<< HEAD
            town.setInfectionState(0, disease);
=======
            town.setInfectionState(disease, 0);
>>>>>>> François
            Card card = new Card(town, disease);
            playerCards.add(card);
        }
        ArrayList<Card> infectionCards = new ArrayList<>(playerCards);
<<<<<<< HEAD

        this.playerCardsStack = new CardsStack(playerCards, true);
        this.infectionCardsStack = new CardsStack(infectionCards, false);
=======
        this.playerCardsStack = new CardsStack(playerCards);
        this.infectionCardsStack = new CardsStack(infectionCards);
>>>>>>> François
    }

    /**
     * Initialize the players' hands
     */
    public void initPlayersHand() {
        int cpt = 4 - this.players.size() + 2; // Number of cards per player initially.
        for (Player player : this.players) {
            for (int i = 0; i < cpt; i++) {
<<<<<<< HEAD
                player.pickPlayerCard(this.playerCardsStack);
=======
                this.pickPlayerCard(player, false);
>>>>>>> François
            }
        }
        ArrayList<Stack<Card>> splitCards = this.playerCardsStack.splitCards(4);
        for (Stack<Card> cStack : splitCards) {
<<<<<<< HEAD
            cStack.add(new Card());
=======
            cStack.add(new Card(null, null));
>>>>>>> François
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
<<<<<<< HEAD
                card.getTown().setInfectionState(i, card.getDisease());
            }
        }
        this.computeGlobalInfectionState();
    }

    /**
     * Get the global infection state of the game
     * 
     * @return The global infection state
     */
    public int getGlobalInfectionState() {
        return this.globalInfectionState;
    }

    /**
     * Get the player at the i index in the players ArrayList
     * 
     * @param i The players' index of the player to get
     * @return The player picked
     */
    public Player getPlayer(int i) {
        return this.players.get(i);
=======
                card.getTown().setInfectionState(card.getDisease(), i);
            }
        }
        this.computeGlobalInfectionState();
        this.computeClustersNumber();
>>>>>>> François
    }

    /**
     * Starts an infection phase in the game
     * 
     * @param n The number of cards to pick in the infection cards
     */
    public void startInfectionPhase(int n) {
        int tempo = n; // Sinon problème avec le for qui va changer et donc problèmes avec les cartes
        for (int i = 0; i < tempo; i++) {
<<<<<<< HEAD
            if (this.infectionCardsStack.stackSize() > 0) {
                Card card = this.infectionCardsStack.pickCard();
                Town town = this.map.getTownByName(card.getTownName());
                Disease disease = card.getDisease();
                if (town.isInfected(disease)) {
                    if (town.getInfectionState(disease) == 3 && !town.isCluster()) {
                        town.setInfectionCluster();
                    } else {
                        town.updateInfectionState(disease);
                    }
                } else {
                    town.setInfectionState(1, disease);
                }
            }
        }
        this.computeGlobalInfectionState();
=======
            this.pickInfectionCard();
        }
>>>>>>> François
    }

    /**
     * Update the number of clusters in the game by adding one to it
     */
<<<<<<< HEAD
    public void updateClustersNumbers() {
        this.clustersNumber = this.getClustersNumber() + 1;
    }

    /**
     * Get the number of clusters in the game
     * 
     * @return The number of clusters in the game
     */
    public int getClustersNumber() {
        return this.map.getClustersNumber();
=======
    public void computeClustersNumber() {
        this.clustersNumber = this.map.getClustersNumber();
>>>>>>> François
    }

    /**
     * Compute all infection states and return the new global infection state
     * 
     * @return The new global infection state of the game
     */
<<<<<<< HEAD
    public int computeGlobalInfectionState() {
        this.globalInfectionState = this.map.getGlobalInfectionState() + INITIAL_INFECTION_STATE;
        return this.globalInfectionState;
=======
    public void computeGlobalInfectionState() {
        this.globalInfectionState = this.map.getGlobalInfectionState() + INITIAL_INFECTION_STATE;
>>>>>>> François
    }

    /**
     * Tell if the game is lost or not
     * 
     * @return True if the game is lost, else false
     */
    public boolean loose() {
<<<<<<< HEAD
        if (this.clustersNumber > MAX_CLUSTERS_NUMBER
                || this.infectionCardsStack.discardSize() + this.infectionCardsStack.stackSize() == 0) {
            System.out.println("Loose " + this.clustersNumber);
            System.out.println("Loose nb cartes infections " + this.infectionCardsStack.discardSize()
                    + this.infectionCardsStack.stackSize());
            return true;
        }
        return false;
=======
        if (this.clustersNumber > MAX_CLUSTERS_NUMBER) {
            return true;
        }
        for (Disease d : this.diseases) {
            if (d.getCube() != 0) {
                return false;
            }
        }
        return true;
>>>>>>> François
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
<<<<<<< HEAD
        if (card != null) {
            this.infectionCardsStack.pickCard().getTown().updateInfectionState(card.getDisease());
            this.computeGlobalInfectionState();
=======
        Town town = card.getTown();
        Disease disease = card.getDisease();
        if (!disease.isCured()) {
            this.infectionCardsStack.discardCard(card);
            town.setInfectionState(disease, 1);
            if (town.isCluster(disease)) {
                this.propagation(town, disease);
            }
            this.computeGlobalInfectionState();
            this.computeClustersNumber();
>>>>>>> François
        }
    }

    /**
<<<<<<< HEAD
     * Propagate the diseases from the clusters to the neighboring towns
     */
    public void propagation() {
        HashMap<Town, ArrayList<Disease>> propTowns = new HashMap<Town, ArrayList<Disease>>();
        for (Town town : this.map.getTowns()) {
            if (town.isCluster()) {
                ArrayList<Disease> clusterDiseases = town.getClusterDisease();
                for (Town neighbor : town.getNeighbors()) {
                    for (Disease d : clusterDiseases) {
                        if (propTowns.containsKey(neighbor)) {
                            ArrayList<Disease> townDiseases = propTowns.get(neighbor);
                            townDiseases.add(d);
                        } else {
                            propTowns.put(neighbor, new ArrayList<Disease>(Arrays.asList(d)));
                        }
                    }
                }
            }
        }
        this.computeGlobalInfectionState();
        Iterator<Entry<Town, ArrayList<Disease>>> iterator = propTowns.entrySet().iterator();

        while (iterator.hasNext()) {
            Entry<Town, ArrayList<Disease>> mapEntry = (Entry<Town, ArrayList<Disease>>) iterator.next();
            ArrayList<Disease> diseasesList = mapEntry.getValue();
            Town t = mapEntry.getKey();
            for (Disease d : diseasesList) {
                t.updateInfectionState(d);
            }
        }
=======
	 * Pick a card into a cardStack, if the player has already 6 cards, the card is
	 * discarded
	 * 
	 * @param player The Player who picks a card
	 * @return True if the player picked the card successfully, else false (the
	 *         CardsStack is empty)
	 */
	public boolean pickPlayerCard(Player player, boolean random) {
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
                i++;
            }
            System.out.println(res + "\n");
            int choice = -1; 
            if (random) {
                choice = (int)Math.floor(Math.random()*7);
            }
            String act = "";
            String error = "Put an integer between 1 and 7 :\n";
            while (choice == -1) {
                act = sc.next();
                try {
                    int value = Integer.parseInt(act);
                    if (value < 1 || value > 7) {
                        System.out.println(error);
                    }
                    else {
                        choice = value-1;
                    }
                }
                catch (NumberFormatException e) {
                    System.out.println(error);
                }
            }
			Card removedCard = playerCards.get(choice);
            player.discardCard(removedCard);
            System.out.println("The card with town " + removedCard.getTownName() + " and disease " + removedCard.getDiseaseName() + " has been removed from your hand\n");
		}
        player.addCard(card);
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
                neighbor.setInfectionState(disease, 1);;
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
>>>>>>> François
    }

    /**
     * Print the state of the game for the player p
     * 
     * @param p The player who is playing
     */
    public void print(Player p) {
<<<<<<< HEAD
        System.out.println(this.map.toStringInfectionState());
        System.out.println("_____________________________________\n");
        System.out.println("\n Player to play : " + p.getName() + "\n");

        System.out.println("\n The number of cluster is : " + this.getClustersNumber() + "\n");
        System.out.println(" The global infection state is : " + this.getGlobalInfectionState() + "\n");
=======
        System.out.println(this.toStringInfectionState(p));
        System.out.println("_____________________________________\n");
        System.out.println("\n Player to play : " + p.getName() + "\n");

        System.out.println("\n The number of cluster is : " + this.clustersNumber + "\n");
        System.out.println(" The global infection state is : " + this.globalInfectionState + "\n");
>>>>>>> François
        Town town = p.getTown();
        System.out.println(" " + town.toString() + "\n");
        System.out.println(" " + p.cardToString() + "\n\n");
        System.out.println("_____________________________________\n");
    }

<<<<<<< HEAD
=======
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
			int res = availableActions.get(actionNumber).chooseParameter(player, this.sc);
			if (res == -1) {
				availableActions.get(actionNumber).chooseParameter(player, this.sc);
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

    public void chooseActionRandom(Player player) {
        String print = "Actions available : \n";
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
        int availableActionsNumber = availableActions.size();
		int actionNumber = (int)Math.floor(Math.random()*availableActionsNumber); 
		if (actionNumber < availableActionsNumber) {
			int res = availableActions.get(actionNumber).chooseRandomParameter(player);
			if (res == -1) {
				availableActions.get(actionNumber).chooseRandomParameter(player);
			}
			else if (res == 1) {
				this.chooseActionRandom(player);
			}
		}
		else {
			if (actionNumber > availableActionsNumber) {
				System.out.println("Number out of range, retry \n");
				this.chooseActionRandom(player);
			}
		}
    }

>>>>>>> François
    /**
     * Run the game while the game is not won or lost
     * 
     * @param sc The scanner of the game to make all decisions
     */
<<<<<<< HEAD
    public void run(Scanner sc) {
        boolean cardFinal = false;
        while (!this.loose() && !this.win() && !cardFinal) {
            for (Player player : this.players) {
                this.print(player);
                player.chooseAction(sc);
                this.print(player);
                player.chooseAction(sc);
                this.print(player);
                player.chooseAction(sc);
                this.print(player);
                player.chooseAction(sc);
                boolean i = player.pickPlayerCard(playerCardsStack);
                boolean j = player.pickPlayerCard(playerCardsStack);
                if (!i || !j) {
                    cardFinal = true;
                    System.out.println("You have lost");
                    break;
                } else {
                    this.startInfectionPhase(1);
                    INITIAL_INFECTION_STATE++;
                }
            }
            this.propagation();
        }
    }
=======
    public boolean run(boolean random) {
        while (true) {
            for (Player player : this.players) {
                this.computeClustersNumber();
                this.computeGlobalInfectionState();
                if (this.loose() || this.win()) {
                    sc.close();
                    if (this.win()) {
                        System.out.println("You have won");
                        return true;
                    }
                    else {
                        System.out.println(this.toStringInfectionState(player));
                        System.out.println("You have lost");
                        System.out.println("\n The number of cluster is : " + this.clustersNumber + "\n");
                        System.out.println(" The global infection state is : " + this.globalInfectionState + "\n");
                        return false;
                    }
                }
                for (int i = 0; i < 4; i++) {
                    this.print(player);
                    if (random) {
                        this.chooseActionRandom(player);
                    }
                    else {
                        this.chooseAction(player);
                    }
                }
                this.pickPlayerCard(player, true);
                this.pickPlayerCard(player, true);
            }
            INITIAL_INFECTION_STATE++;
            this.startInfectionPhase(this.globalInfectionState);
        }
    }

>>>>>>> François
}