package pandemic;

import java.util.ArrayList;
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

    public Game(Map map, ArrayList<Player> players, ArrayList<Disease> diseases, CardsStack playerCardsStack, CardsStack infectionCardsStack) {
        this.globalInfectionState = INITIAL_INFECTION_STATE;
        this.clustersNumber = 0;
        this.map = map;
        this.players = players;
        this.diseases = diseases;
        this.playerCardsStack = playerCardsStack;
        this.infectionCardsStack = infectionCardsStack;
    }

    public int getGlobalInfectionState() {
        return this.globalInfectionState;
    }

    public Player getPlayer(int i) {
        return this.players.get(i);
    }


    public void startInfectionPhase() throws NoSuchTownException, NoSuchDiseaseException {
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

    public void run() {
        while (!gameEnded()) {
            break; // TODO
        }
    }

    public Map getMap() {
        return this.map;
    }

    public CardsStack getInfectionCardsStack() {
        return this.infectionCardsStack;
    }
}