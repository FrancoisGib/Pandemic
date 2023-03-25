package pandemic;

import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;

import pandemic.player.*;
import pandemic.cards.*;

public class Game {
    private static final int MAX_CLUSTERS_NUMBER = 8;
    private static final int INITIAL_INFECTION_STATE = 2;

    private int globalInfectionState;

    private int clustersNumber;

    private Map map;

    private ArrayList<Player> players;

    private ArrayList<Disease> diseases;

    private CardsStack playerCardsStack;

    private CardsStack infectionCardsStack;

    public Game(Map map, ArrayList<Player> players, ArrayList<Disease> diseases) {
        this.globalInfectionState = INITIAL_INFECTION_STATE;
        this.clustersNumber = 0;
        this.map = map;
        this.players = players;
        this.diseases = diseases;
        this.playerCardsStack = null;
        this.infectionCardsStack = null;
    }

    public void initGame() {
        initPlayersHand();
        initInitialTown();
    }

    public void initCards() {
        ArrayList<Town> towns = this.map.getTowns();
        ArrayList<Card> playerCards = new ArrayList<Card>();
        for (Town town : towns) {
            
        }
    }

    public void initPlayersHand() {
        int cpt = 4 - this.players.size() + 2; // Number of cards per player initially.
        for (Player player : this.players) {
            for (int i = 0; i < cpt; i++) {
                player.pickPlayerCard(this.playerCardsStack);
            }
        }   
        ArrayList<Stack<Card>> splitCards = this.playerCardsStack.splitCards(4);
        for (Stack<Card> cStack : splitCards) {
            cStack.add(null)
        }
        this.playerCardsStack.mergeStacks(splitCards);
    }

    public void initInitialTown() {
        Random random = new Random();
        int initialTownIndex = random.nextInt(map.getTowns().size());
        Town initialTown = this.map.getTown(initialTownIndex);
        for (Player player : this.players) {
            player.setTown(initialTown);
        }
    }

    public int getGlobalInfectionState() {
        return this.globalInfectionState;
    }

    public Player getPlayer(int i) {
        return this.players.get(i);
    }

    public void startInfectionPhase() {
        int tempo = this.globalInfectionState; // Sinon problème avec le for qui va changer et donc problèmes avec les cartes
        for (int i = 0; i < tempo; i++) {
            Card card = this.infectionCardsStack.pickCard();
            Town town = this.map.getTownByName(card.getTownName());
            Disease disease = card.getDisease();
            if (town.isInfected(disease)) {
                if (town.getInfectionState(disease)==3) {
                    town.setInfectionCluster();
                }
                else {
                    this.globalInfectionState ++;
                    town.updateInfectionState(disease);
                }
            }
            else {
                this.globalInfectionState ++;
                town.setInfectionState(1, disease);
            }
        }
    }

    public void updateClustersNumbers() {
        this.clustersNumber = this.getClustersNumber();
    }

    public int getClustersNumber() {
        return this.map.getClustersNumber();
    }

    public int processGlobalInfectionState() {
        this.globalInfectionState = this.map.getGlobalInfectionState();
        return this.globalInfectionState + INITIAL_INFECTION_STATE;
    }

    public boolean gameEnded() {
        if (this.clustersNumber < MAX_CLUSTERS_NUMBER) {
            return false;
        }
        return true;
    }

    public Map getMap() {
        return this.map;
    }

    public CardsStack getInfectionCardsStack() {
        return this.infectionCardsStack;
    }
}