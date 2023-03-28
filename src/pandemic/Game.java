package pandemic;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
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
        this.initGame();
    }

    /**
     * Initialize the game by initializing cards, players' hands, initial town and
     * the initial infection of the game
     */
    public void initGame() {
        this.initCards();
        this.initActions();
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
        this.playerCardsStack = new CardsStack(playerCards, true);
        this.infectionCardsStack = new CardsStack(infectionCards, false);
    }

    public void initActions() {
        for (Player player : this.players) {
            player.setActions(this.actions);
        }
    }

    /**
     * Initialize the players' hands
     */
    public void initPlayersHand() {
        int cpt = 4 - this.players.size() + 2; // Number of cards per player initially.
        for (Player player : this.players) {
            for (int i = 0; i < cpt; i++) {
                player.pickPlayerCard(this.playerCardsStack);
            }
        }
        ArrayList<Stack<Card>> splitCards = this.playerCardsStack.splitCards(4);
        for (Stack<Card> cStack : splitCards) {
            cStack.add(new Card());
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
    }

    /**
     * Starts an infection phase in the game
     * 
     * @param n The number of cards to pick in the infection cards
     */
    public void startInfectionPhase(int n) {
        int tempo = n; // Sinon problème avec le for qui va changer et donc problèmes avec les cartes
        for (int i = 0; i < tempo; i++) {
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
        this.propagation();
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
     * Get the number of clusters in the game
     * 
     * @return The number of clusters in the game
     */
    public int getClustersNumber() {
        return this.clustersNumber;
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
        if (this.clustersNumber > MAX_CLUSTERS_NUMBER
                || this.infectionCardsStack.discardSize() + this.infectionCardsStack.stackSize() == 0) {
            System.out.println("Loose " + this.clustersNumber);
            System.out.println("Loose nb cartes infections " + this.infectionCardsStack.discardSize()
                    + this.infectionCardsStack.stackSize());
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
        if (card != null) {
            this.infectionCardsStack.pickCard().getTown().updateInfectionState(card.getDisease());
            this.computeGlobalInfectionState();
        }
    }

    /**
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
        Iterator<Entry<Town, ArrayList<Disease>>> iterator = propTowns.entrySet().iterator();
        while (iterator.hasNext()) {
            Entry<Town, ArrayList<Disease>> mapEntry = (Entry<Town, ArrayList<Disease>>) iterator.next();
            ArrayList<Disease> diseasesList = mapEntry.getValue();
            Town t = mapEntry.getKey();
            for (Disease d : diseasesList) {
                t.updateInfectionState(d);
            }
        }
        this.computeGlobalInfectionState();
        this.computeClustersNumber();
    }

    /**
     * Print the state of the game for the player p
     * 
     * @param p The player who is playing
     */
    public void print(Player p) {
        System.out.println(this.map.toStringInfectionState());
        System.out.println("_____________________________________\n");
        System.out.println("\n Player to play : " + p.getName() + "\n");

        System.out.println("\n The number of cluster is : " + this.getClustersNumber() + "\n");
        System.out.println(" The global infection state is : " + this.getGlobalInfectionState() + "\n");
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
    public void run() throws IOException {
        Scanner sc = new Scanner(System.in);
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
                } else {
                    this.startInfectionPhase(1);
                    INITIAL_INFECTION_STATE++;
                }
                if (this.loose() || this.win()) {
                    System.exit(0);
                }
            }
            this.startInfectionPhase(this.globalInfectionState);
        }
        sc.close();
    }
}