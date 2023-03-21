package pandemic;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import pandemic.JsonReader.TownsJsonReader;

import java.util.HashMap;

/** The game's Map who control the towns of the game */
public class Map {

	/** The list of towns in the game */
	private ArrayList<Town> towns;

	/** Builds the game's map */
	public Map() {
		this.towns = new ArrayList<Town>();
	}

	/**
	 * Initialize the map's towns by adding all maps and their neighbors in the towns array
	 * 
	 * @param filename The name of the file to read
	 * @exception NoSuchTownException The exception if one of the is missing (if the JsonReader file has mistakes)
	 * @exception FileNotFoundException The exception if the specified file doesn't exist
	 */
	public void setMapWithJSON(String filename) throws NoSuchTownException, FileNotFoundException {
		TownsJsonReader reader = new TownsJsonReader(filename);
		reader.setTowns();

		HashMap<String, Integer> townsArray = reader.getTowns();

		for (HashMap.Entry<String, Integer> entry : townsArray.entrySet()) {
			Town town = new Town(entry.getKey(), entry.getValue());
			this.towns.add(town);
		}

		reader.setNeighbors();
		HashMap<String, ArrayList<String>> neighbors = reader.getNeighbors();

		for (Town town : towns) {
			String name = town.getName();
			ArrayList<String> townNeighbors = neighbors.get(name);
			if (townNeighbors == null) {
				throw new NoSuchTownException("No such town in the towns array");
			}
			for (String neighborName : townNeighbors) {
				Town addedTown = getTownByName(neighborName);
				town.addNeighbor(addedTown);
			}
		}
	}
	
	/**
	 * Initialize the map's towns by adding all maps and their neighbors in the towns array
	 * 
	 * @param towns The arrayList of towns to set
	 */
	public void setMap(ArrayList<Town> towns) {
		this.towns = towns;
	}

	/**
	 * Give the towns of the game
	 * 
	 * @return The list of towns in the game
	 */
	public ArrayList<Town> getTowns() {
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
	 * Get a town by it's name in the towns list
	 * 
	 * @param name The name of the town to get
	 * @return The town who has the string name has name in the towns list
	 * @exception NoSuchTownException The exception if there is no town with that name in the towns array                    
	 */
	public Town getTownByName(String name) throws NoSuchTownException {
		for (Town town : towns) {
			if (name.equals(town.getName())) {
				return town;
			}
		}
		throw new NoSuchTownException("No such town in the towns array");
	}
	
	/**
	 * Get the neighbors of the town passed in parameter
	 * 
	 * @param town The town to search neighbors
	 * @return All the neighbors of the town passed in parameters
	 */
	public ArrayList<Town> getTownNeighbors(Town town) {
		return town.getNeighbors();
	}
	
	/**
	 * Tell if two towns are neighbors or not
	 * 
	 * @param town1 The first town
	 * @param town2 The second town
	 * @return true if the two towns are neighbors, else false
	 */
	public boolean areNeighbors(Town town1, Town town2) {
		return town1.getNeighbors().contains(town2);
	}
	
	/**
	 * Give all towns information in a String
	 * 
	 * @return All the information in the towns list
	 */
	public String toString() {
		String res = "";
		for (Town town : this.towns) {
			List<Town> townNeighbors = town.getNeighbors();
			String stringNeighbors = "";
			for (Town neighbor : townNeighbors) {
				stringNeighbors += neighbor.getName() + " / ";
			}
			res += town.getName() + " sector : " + town.getSector() + " neighbors : " + stringNeighbors + "\n";
		}
		return res;
	}

	public String toStringInfectionState() {
		String res = "";
		for (Town town : this.towns) {
			res += town.getName() + " infection state : ";
			HashMap<Disease, Integer> diseases = town.getAllInfectionState();
			for (Disease disease : diseases.keySet()) {
				res += disease.getName() + " : " + diseases.get(disease) + " / ";
			}
			res += "\n";
				
		}
		return res;
	}

    public int getClustersNumber() {
		int cpt = 0;
        for (Town town : this.towns) {
            if (town.isCluster()) {
				cpt++;
			}
        }
		return cpt;
    }

	public int getGlobalInfectionState() {
		int sum = 0;
		for (Town town : towns) {
			ArrayList<Integer> infectionStates = new ArrayList<Integer>(town.getAllInfectionState().values());
			for (Integer state : infectionStates) {
				sum += state;
			}
		}
		return sum;
	}
}
