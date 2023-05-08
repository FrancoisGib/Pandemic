package pandemic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;
import java.util.Scanner;
import java.util.Stack;

import pandemic.player.Player;
import pandemic.actions.Action;
import pandemic.cards.*;

/** The class reprensenting the Pandemic Game */
public class Game {

    /** The number of clusters before the game is lost */
    private static final int MAX_CLUSTERS_NUMBER = 8;

    /** The number of research centers players can build */
    public static final int MAX_RESEARCH_CENTERS = 8;

    /** The initial infection state */
    private static int INITIAL_INFECTION_STATE = 2;

    /** The current global infection state */
    private int globalInfectionState;

    /** The current number of clusters */
    private int clustersNumber;

    /** The map of the game, which handles all towns changes */
    private Map map;

    /** The players of the game */
    private ArrayList<Player> players;

    /** The diseases of the game */
    private ArrayList<Disease> diseases;

    /** The player cards stack which handles the player cards */
    private CardsStack playerCardsStack;

    /** The infection cards stack which handles the infection cards */
    private CardsStack infectionCardsStack;

    /** The actions available in the game */
    private ArrayList<Action> actions;

    /** The scanner to make decisions */
    private Scanner sc;

    /**
     * Builds a game to be played
     * 
     * @param map      The map of the game, to handle the towns
     * @param players  The players of the game
     * @param diseases The diseases of the game
     * @param actions  The actions of the game
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
            town.setInfectionState(disease, 0);
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
                this.pickPlayerCard(player, false);
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
                card.getTown().setInfectionState(card.getDisease(), i);
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
        int temp = n; // Sinon problème avec le for qui va changer et donc problèmes avec les cartes
        for (int i = 0; i < temp; i++) {
            this.pickInfectionCard();
        }
        this.computeClustersNumber();
        this.computeGlobalInfectionState();
    }

    /**
     * Update the number of clusters in the game by adding one to it
     */
    public void computeClustersNumber() {
        this.clustersNumber = this.map.getClustersNumber();
    }

    /** Compute all infection states */
    public void computeGlobalInfectionState() {
        this.globalInfectionState = this.map.getGlobalInfectionState() + INITIAL_INFECTION_STATE;
    }

    /**
     * Tell if the game is lost or not
     * 
     * @return True if the game is lost, else false
     */
    public boolean lose() {
        boolean res = false;
        if (this.clustersNumber > MAX_CLUSTERS_NUMBER) {
            res = true;
        }
        for (Disease d : this.diseases) {
            if (d.getCube() == 0) {
                res = true;
            }
        }
        return res;
    }

    /**
     * Tell if the game is won or not
     * 
     * @return True if the game is won, else false
     */
    public boolean win() {
        if (this.diseases.size() == 0) {
            return true;
        }
        return false;
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

    /** Pick an infection card in the infection cards stack and then make the actions associated */
    public void pickInfectionCard() {
        Card card = this.infectionCardsStack.pickCard();
        Town town = card.getTown();
        Disease disease = card.getDisease();
        HashSet<Town> visited = new HashSet<Town>();
        if (!(disease.isCured() && disease.getCube() != Disease.INITIAL_CUBES_NUMBER) && !visited.contains(town)) {
            this.infectionCardsStack.discardCard(card);
            town.setInfectionState(disease, 1);
            if (town.isCluster(disease)) {
                this.propagation(town, disease);
            }
            visited.add(town);
        }
    }

    /**
     * Propagate the diseases from the clusters to the neighboring towns
     * 
     * @param town    The cluster to propagate
     * @param disease The disease to propagate
     */
    public void propagation(Town town, Disease disease) {
        HashSet<Town> visited = new HashSet<Town>();
        ArrayList<Town> toVisit = new ArrayList<Town>();
        toVisit.add(town);
        while (toVisit.size() > 0) {
            Town currentTown = toVisit.remove(0);
            visited.add(currentTown);
            for (Town neighbor : town.getNeighbors()) {
                neighbor.setInfectionState(disease, 1);
                if (neighbor.isCluster(disease) && !visited.contains(neighbor)) {
                    toVisit.add(neighbor);
                }
            }
        }
    }

    /**
     * Pick a card into a cardStack, if the player has already 6 cards, the card is
     * discarded
     * 
     * @param player The Player who picks a card
     * @param random If true, the action is made randomly, else not
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
                choice = (int) Math.floor(Math.random() * 7);
            }
            String act = "";
            String error = "Put an integer between 1 and 7 :\n";
            while (choice == -1) {
                act = sc.next();
                try {
                    int value = Integer.parseInt(act);
                    if (value < 1 || value > 7) {
                        System.out.println(error);
                    } else {
                        choice = value - 1;
                    }
                } catch (NumberFormatException e) {
                    System.out.println(error);
                }
            }
            Card removedCard = playerCards.get(choice);
            player.discardCard(removedCard);
            System.out.println("The card with town " + removedCard.getTownName() + " and disease "
                    + removedCard.getDiseaseName() + " has been removed from your hand\n");
        }
        player.addCard(card);
        return true;
    }

    /**
     * Give a String that describes the infections states in all the towns of the
     * map
     * 
     * @param player The player who is playing
     * @param paths If true, the paths between towns will be printed, else not
     * @return The String describing all the states
     */
    public String toStringInfectionState(Player player, boolean paths) {
        String res = "";
        for (Town town : this.map.getTowns()) {
            int cpt = 0;
            String townRes = "";
            HashMap<Disease, Integer> diseases = town.getAllInfectionState();
            Iterator<HashMap.Entry<Disease, Integer>> diseasesIt = diseases.entrySet().iterator();
            while (diseasesIt.hasNext()) {
                HashMap.Entry<Disease, Integer> entry = diseasesIt.next();
                if (entry.getValue() > 0) {
                    Disease disease = entry.getKey();
                    cpt++;
                    townRes += disease.getName() + " : " + diseases.get(disease);
                    if (diseasesIt.hasNext()) {
                        townRes += " / ";
                    }
                }
            }
            if (cpt > 0) {
                res += town.getName() + " infection state : " + townRes;
                if (paths) {
                    res += " / Shortest path" + this.shortestPathToString(player.getTown(), town);
                }
                res += "\n";
                cpt = 0;
            }
        }
        return res;
    }

    /**
     * Print the state of the game for the player p
     * 
     * @param player The player who is playing
     */
    public void toString(Player player) {
        System.out.println(this.toStringInfectionState(player, true));
        System.out.println(this.map.toStringClusters());
        System.out.println("_____________________________________\n");
        System.out.println("\n Player to play : " + player.getName() + "\n");
        System.out.println("\n The number of clusters is : " + this.clustersNumber + "\n");
        System.out.println(" The global infection state is : " + this.globalInfectionState + "\n");
        Town town = player.getTown();
        System.out.println(" " + town.toString() + "\n");
        System.out.println(" " + player.cardToString() + "\n");
        String res = " Players informations :\n ";
        ArrayList<Player> playersCopy = new ArrayList<>(this.players);
        playersCopy.remove(player);
        Iterator<Player> playersIt = playersCopy.iterator();
        while (playersIt.hasNext()) {
            Player p = playersIt.next();
            res += p.getName() + " : " + p.getTownName();
            if (playersIt.hasNext())
                res += " / ";
        }
        HashSet<Town> researchCenters = this.map.getResearchCenters();
        if (researchCenters.size() > 0) {
            res += "\n\n Research centers : ";
            Iterator<Town> centersIt = researchCenters.iterator();
            while (centersIt.hasNext()) {
                res += centersIt.next().getName();
                if (centersIt.hasNext()) 
                    res += " / ";
            }
        }
        System.out.println(res);
        System.out.println("_____________________________________\n");
    }

    /**
     * Get the shortest Path between two towns
     * 
     * @param t1 The first town
     * @param t2 The second town
     * @return The stack of moves to make to move on the town t2
     */
    public Stack<Town> shortestPath(Town t1, Town t2) {
        ArrayList<Node> toVisit = new ArrayList<Node>();
        HashSet<Node> visited = new HashSet<Node>();
        Node root = new Node(null, t1);
        toVisit.add(root);
        while (!toVisit.isEmpty()) {
            Node current = toVisit.remove(0);
            visited.add(current);
            if (current.getCurrent() == t2)
                return current.getPath();
            for (Town t : current.getCurrent().getNeighbors()) {
                Node next = new Node(current, t);
                if (!visited.contains(next))
                    toVisit.add(next);
            }
        }
        return null;
    }

    /**
     * Get the shortest Path between two towns
     * 
     * @param t1 The first town
     * @param t2 The second town
     * @return The string describing the path between t1 and t2
     */
    public String shortestPathToString(Town t1, Town t2) {
        Stack<Town> path = shortestPath(t1, t2);
        String res = "";
        while (!path.isEmpty()) {
            Town t = path.pop();
            res += " -> " + t.getName();
        }
        return res;
    }

    /**
     * Choose an action to do
     * 
     * @param player The player who choose the action
     * @param actionsLeft The number of actions the player has left
     * @return The consumed actions number
     */
    public int chooseAction(Player player, int actionsLeft) {
        String print = "Choose an action by entering a number !\n";
        ArrayList<Action> availableActions = new ArrayList<>();
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
                actionNumber = Integer.parseInt(act) - 1;
            } catch (NumberFormatException e) {
                System.out.println("Not an integer, retry");
            }
        }
        int res = 0;
        int availableActionsNumber = availableActions.size();
        if (actionNumber == availableActionsNumber) {
            return 1;
        }
        else if (actionNumber < availableActionsNumber) {
            res = availableActions.get(actionNumber).chooseParameter(player, this.sc, actionsLeft);
            if (res == -1)
                availableActions.get(actionNumber).chooseParameter(player, this.sc, actionsLeft);
            else if (res == 0) {
                return this.chooseAction(player, actionsLeft);
            }
        } else {
            System.out.println("Number out of range, retry \n");
            return this.chooseAction(player, actionsLeft);
        }
        return res;
    }

    /**
     * Choose an action randomly
     * 
     * @param player The player who plays the action
     * @param actionsLeft The number of actions left
     * @return The cost of the action
     */
    public int chooseActionRandomly(Player player, int actionsLeft) {
        String print = "Actions available : \n";
        ArrayList<Action> availableActions = new ArrayList<>();
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
        int actionNumber = (int) Math.floor(Math.random() * availableActionsNumber);
        int res = 0;
        if (actionNumber < availableActionsNumber) {
            res = availableActions.get(actionNumber).chooseRandomParameter(player, actionsLeft);
            if (res == -1) {
                availableActions.get(actionNumber).chooseRandomParameter(player, actionsLeft);
            } else if (res == 0) {
                this.chooseActionRandomly(player, actionsLeft);
            }
        } else {
            if (actionNumber > availableActionsNumber) {
                System.out.println("Number out of range, retry \n");
                this.chooseActionRandomly(player, actionsLeft);
            }
        }
        return res;
    }

    /**
     * Run the game while the game is not won or lost
     * 
     * @param random if true, the game will be played randomly, else false
     * @return true if the player won, else false
     */
    public boolean run(boolean random) {
        while (true) {
            for (Player player : this.players) {
                int i = 4;
                while (i > 0) {
                    if (this.lose() || this.win()) {
                        sc.close();
                        if (this.win()) {
                            System.out.println("You have won");
                            return true;
                        }
                        else {
                            System.out.println("You have lost");
                            System.out.println("\n The number of cluster is : " + this.clustersNumber + "\n");
                            System.out.println(" The global infection state is : " + this.globalInfectionState + "\n");
                            System.out.println(this.toStringInfectionState(player, false));
                            return false;
                        }
                    }
                    this.toString(player);
                    System.out.println("Number of actions left : " + i + "\n");
                    int cost = 0;
                    if (random) {
                        cost = this.chooseActionRandomly(player, i);
                    }
                    else {
                        cost = this.chooseAction(player, i);
                    }
                    i -= cost;
                    this.removeCuredDiseases();
                    this.computeClustersNumber();
                    this.computeGlobalInfectionState();
                }
                this.pickPlayerCard(player, true);
                this.pickPlayerCard(player, true);
            }
            INITIAL_INFECTION_STATE++;
            this.startInfectionPhase(this.globalInfectionState);
        }
    }

    /** Remove the diseases who has been cured */
    public void removeCuredDiseases() {
        ArrayList<Disease> remainingDiseases = new ArrayList<Disease>();
        for (Disease disease : this.diseases) {
            if (!(disease.isCured() && disease.getCube() == Disease.INITIAL_CUBES_NUMBER)) {
                remainingDiseases.add(disease);
            }
        }
        this.diseases = remainingDiseases;
    }

    /**
     * Get the global infection state
     * 
     * @return The global infection state
     */
    public int getGlobalInfectionState() {
        return this.globalInfectionState;
    }

    /**
     * Get the number of clusters
     * 
     * @return The number of clusters
     */
    public int getClustersNumber() {
        return this.clustersNumber;
    }
}