package pandemic;

import java.io.FileNotFoundException;
import java.util.ArrayList;

import pandemic.jsonreader.TownsJsonReader;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

/** The game's Map who control the towns of the game */
public class Map {

	/** The list of towns in the game */
	private ArrayList<Town> towns;

	/** Builds the game's map */
	public Map() {
		this.towns = new ArrayList<Town>();
	}

	/**
	 * Initialize the map's towns by adding all maps and their neighbors in the
	 * towns array
	 * 
	 * @param filename The name of the file to read
	 * @exception NoSuchTownException   The exception if one of the is missing (if
	 *                                  the JsonReader file has mistakes)
	 * @exception FileNotFoundException The exception if the specified file doesn't
	 *                                  exist
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
	 * Initialize the map's towns by adding all maps and their neighbors in the
	 * towns array
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
	 */
	public Town getTownByName(String name) {
		for (Town town : towns) {
			if (name.equals(town.getName())) {
				return town;
			}
		}
		return null;
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
	 * Get the number of clusters in the map
	 * 
	 * @return The number of clusters
	 */
	public int getClustersNumber() {
		int cpt = 0;
		for (Town town : this.towns) {
			cpt += town.getClusterDisease().size();
		}
		return cpt;
	}

	/**
	 * Get the clusters of the towns in the map
	 * 
	 * @return The clusters of the towns
	 */
	public HashMap<Town, ArrayList<Disease>> getClusters() {
		HashMap<Town, ArrayList<Disease>> clusters = new HashMap<Town, ArrayList<Disease>>();
		for (Town town : this.towns) {
			ArrayList<Disease> diseases = town.getClusterDisease();
			if (diseases.size() > 0) {
				clusters.put(town, diseases);
			}
		}
		return clusters;
	}

	/**
	 * Get the global infection state by summing all the infections state from the
	 * towns
	 * 
	 * @return The global infection state of the game
	 */
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

	public HashSet<Town> getResearchCenters() {
		HashSet<Town> researchCenters = new HashSet<Town>();
		for (Town town : towns) {
			if (town.hasResearchCenter()) {
				researchCenters.add(town);
			}
		}
		return researchCenters;
	}

	public String toStringClusters() {
        HashMap<Town, ArrayList<Disease>> clusters = this.getClusters();
        if (clusters.size() == 0) {
            return "";
        }
        String res = "The clusters are : \n";
        Iterator<HashMap.Entry<Town, ArrayList<Disease>>> it = clusters.entrySet().iterator();
        while (it.hasNext()) {
            HashMap.Entry<Town, ArrayList<Disease>> entry = it.next();
            res += entry.getKey().getName() + " : ";
            Iterator<Disease> dIt = entry.getValue().iterator();
            while (dIt.hasNext()) {
                res += dIt.next().getName();
                if (dIt.hasNext()) {
                    res += " / ";
                }
            }
            if (it.hasNext()) {
                res += ", ";
            }
        }
        return res;
    }
}
