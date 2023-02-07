package pandemic;

import java.util.ArrayList;
import java.util.List;

public class Map {
	
	/* The list of towns in the game */
	private List<Town> towns;
	
	/* The list of diseases in the game */
	private List<Disease> diseases;
	
	/* The list of players in the game */
	private List<Player> players;
	
	/* The stack of player cards in the game */
	private PlayerCardsStack playerCards;
	
	/* The stack of infection cards in the game */
	private InfectionCardsStack infectionCards;
	
	/* The game's global infection state */
	private int globalInfection;
	
	/* The number of clusters in the game */
	private int cluster;
	
	/**
	 * Builds the game's map 
	 *  
	 * @param The array of towns to add in the game
	 * @param The array of diseases to add in the game
	 * @param The array of players to add in the game
	 * @param The stack of players cards to add in the game
	 * @param The stack of infection cards to add in the game
	 * @param The global infection state of the game
	 */
	public Map(ArrayList<Town> towns, ArrayList<Disease> diseases, ArrayList<Player> players, PlayerCardsStack playerCards, InfectionCardsStack infectionCards, int globalInfection) {
		this.towns = towns;
		this.diseases = diseases;
		this.players = players;
		this.playerCards = playerCards;
		this.infectionCards = infectionCards;
		this.globalInfection = globalInfection;
		this.cluster = 0;
	}
	
	/**
	 * Give the towns of the game 
	 *  
	 * @return The list of towns in the game
	 */
	public List<Town> getTowns() {
		return this.towns;
	}
	
	/**
	 * Give the diseases in the game 
	 *  
	 * @return The list of diseases in the game
	 */
	public List<Disease> getDiseases() {
		return this.diseases;
	}
	
	/**
	 * Give the players of the game
	 *  
	 * @return The list of players in the game
	 */
	public List<Player> getPlayers() {
		return this.players;
	}
	
	/**
	 * Give the player cards stack of the game
	 *  
	 * @return The player cards stack in the game
	 */
	public PlayerCardsStack getPlayerCards() {
		return this.playerCards;
	}
	
	/**
	 * Give the infection cards stack of the game
	 *  
	 * @return The infection cards stack in the game
	 */
	public InfectionCardsStack getInfectionCards() {
		return this.infectionCards;
	}
	
	/**
	 * Update the global infection state of the game.
	 *  
	 * @return The updated global infection state of the game
	 */
	public int updateInfectionState() {
		int sum = 0;
		for(Town town : this.towns) {
			sum += town.getInfectionState();
		}
		return sum;
	}
	
	
	/**
	 * Give the global infection state of the game
	 *  
	 * @return The global infection state of the game
	 */
	public int getGlobalInfectionState() {
		return this.globalInfection;
	}
	
	/**
	 * Give the number of clusters state of the game
	 *  
	 * @return The global infection state of the game
	 */
	public int getCluster() {
		return this.globalInfection;
	}
	
	
	
	
}
