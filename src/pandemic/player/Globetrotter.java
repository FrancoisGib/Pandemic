package pandemic.player;

import java.util.HashSet;
import java.util.ArrayList;

import pandemic.Town;

/* The class that defines a Globetrotter in the game, it is inherited of the Player class */
public class Globetrotter extends Player {

	/**
	 * Builds a Globetrotter for the game
	 *
	 * @param name The name of the globetrotter
	 */
	public Globetrotter(String name) {
		super(name);
	}
	
	/**
	 * The Globetrotter can move accross the map, no matter of the town's neighbors
	 * 
	 * @param town The town to move on
	 * @param movableTowns The HashSet of towns the players can move on
	 */
	public void movableTowns(Town town, HashSet<Town> movableTowns) {
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
