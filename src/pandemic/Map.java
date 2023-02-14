package pandemic;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;

/** The game's Map who control the towns of the game */
public class Map {

	/* The list of towns in the game */
	private List<Town> towns;
	private JsonReader reader;

	/**
	 * Builds the game's map
	 * 
	 * @param the name of the towns file
	 */
	public Map(String filename) {
		this.towns = new ArrayList<Town>();
		this.reader = new JsonReader(filename);
	}

	/**
	 * Initialize the map's towns by adding all maps and their neihbors in the towns
	 * array
	 * 
	 * @throws NoSuchTownException The exception if one of the is missing (if the
	 *                             JsonReader file has mistakes)
	 */
	public void initMap() throws NoSuchTownException {
		this.reader.setTowns();
		this.reader.setNeighbors();

		HashMap<String, Integer> townsArray = this.reader.getTowns();

		for (HashMap.Entry<String, Integer> entry : townsArray.entrySet()) {
			Town town = new Town(entry.getKey(), entry.getValue());
			this.towns.add(town);
		}
		HashMap<String, List<String>> neighbors = this.reader.getNeighbors();
		
		for (Town town : towns) {
			String name = town.getName();
			List<String> townNeighbors = neighbors.get(name);
			for (String neighborName : townNeighbors) {
				Town addedTown = getTownByName(neighborName);
				town.addNeighbor(addedTown);
			}
		}
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
	 * Get a town by it's index
	 * 
	 * @param i The index of the town to get
	 * @return The town at the i index in the towns array
	 */
	public Town getTown(int i) {
		return this.towns.get(i);
	}

	/**
	 * 
	 * 
	 * @param name The name of the town to get
	 * @return The town who has the string name has name in the towns array
	 * @throws NoSuchTownException The exception if there is no town with that name
	 *                             in the towns array
	 */
	public Town getTownByName(String name) throws NoSuchTownException {
		for (Town town : towns) {
			if (town.getName().equals(name)) {
				return town;
			}
		}
		throw new NoSuchTownException("No such town in the towns array");
	}

}
