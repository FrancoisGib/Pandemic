package pandemic.player;

import java.util.HashSet;
import java.util.ArrayList;

import pandemic.Town;

/* The class that defines a player in the game */
public class Globetrotter extends Player {

	public Globetrotter(String name) {
		super(name);
	}
	
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
