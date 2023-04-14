package pandemic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Iterator;

/* The class that defines a town in the infection game */
public class Town {

	/* The Town's infection states */
	private HashMap<Disease, Integer> infectionState;

	/* The name of the Town */
	private String name;

	/* The list of neighbors the Town has */
	private ArrayList<Town> neighbors;

	/* The sector of the Town */
	private int sector;

	/* True if the Town has a research center, else false */
	private boolean researchCenter;

	/* The boolean telling if the town is a cluster or not */
	private HashMap<Disease, Boolean> infectionCluster;

	/**
	 * Builds a Town
	 * 
	 * @param name   The name of the Town
	 * @param sector The sector of the Town
	 */
	public Town(String name, int sector) {
		this.infectionState = new HashMap<Disease, Integer>();
		this.name = name;
		this.neighbors = new ArrayList<Town>();
		this.sector = sector;
		this.researchCenter = false;
		this.infectionCluster = new HashMap<Disease, Boolean>();
	}

	/**
	 * Give the Town's infection state for a disease, if the disease has not infected the town, it returns -1
	 * 
	 * @return The infection states of the Town
	 */
	public int getInfectionState(Disease disease) {
		if (this.infectionState.containsKey(disease)) {
			return this.infectionState.get(disease);
		}
		return -1;
	}

	/**
	 * Set the infection states for the disease in parameter
	 * 
	 * @param infectionState The infection state to apply
	 * @param disease        The disease to set the town infection state for
	 */
	public void setInfectionState(Disease disease, int n) {
		int formerInfectionState = this.infectionState.containsKey(disease) ? formerInfectionState = this.infectionState.get(disease) : 0;
		this.infectionState.put(disease, formerInfectionState);
		int newInfectionState = -1;
		if (!this.infectionCluster.containsKey(disease)) {
			this.infectionCluster.put(disease, false);
		}
		if (n == 0) {
			for (int i = 0; i < formerInfectionState; i++) {
				disease.removeCube();
			} 
			this.infectionState.put(disease, 0);
		}
		else {
			newInfectionState = this.infectionState.get(disease) + n;
			if (newInfectionState <= 3 && newInfectionState >= 0) {
				this.infectionState.put(disease, newInfectionState);
				if (n < 0) {
					for (int i = 0; i < -n; i++) {
						disease.removeCube();
					}
				}
				else {
					for (int i = 0; i < n; i++) {
						disease.placeCube();
					}
				}
			}
		}
		if (formerInfectionState < 3) {
			this.infectionCluster.put(disease, false);
		}
		else if (formerInfectionState == 3 && n > 0) {
			this.infectionCluster.put(disease, true);
		}
	}


	/**
	 * Get the Town's neighbors
	 * 
	 * @return The list of the Town's neighbors
	 */
	public ArrayList<Town> getNeighbors() {
		return neighbors;
	}

	/**
	 * Add a neighbor into the Town's neighbors list
	 * 
	 * @param neighbor The neighbor to add into the list
	 */
	public void addNeighbor(Town neighbor) {
		this.neighbors.add(neighbor);
	}

	/**
	 * Give the name of the Town
	 * 
	 * @return The Town's name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Give the Town's sector
	 * 
	 * @return The sector number
	 */
	public int getSector() {
		return sector;
	}

	/**
	 * Builds a research center in the Town.
	 */
	public boolean buildResearchCenter() {
		if (this.researchCenter) {
			return false;
		}
		this.researchCenter = true;
		return true;
	}

	/**
	 * Tell if a town has a research center.
	 * 
	 * @return true if the town has a research center, else false.
	 */
	public boolean hasResearchCenter() {
		return this.researchCenter;
	}

	/**
	 * Get all the town's infection states
	 * 
	 * @return The town's infection states
	 */
	public HashMap<Disease, Integer> getAllInfectionState() {
		return this.infectionState;
	}

	/**
	 * Remove the infection cluster
	 */
	public void removeInfectionCluster(Disease disease) {
		this.infectionCluster.replace(disease, false);
	}

	/**
	 * Tell if the town is a cluster or not
	 * 
	 * @return True if the town is a cluster, else false
	 */
	public boolean isCluster(Disease disease) {
		if (!this.infectionCluster.containsKey(disease)) {
			return false;
		}
		return this.infectionCluster.get(disease);
	}

	/**
	 * Tell if the specified disease has infected the town 
	 * 
	 * @param disease The disease to check
	 * @return True if the disease has infected the town, else false
	 */
	public boolean isInfected(Disease disease) {
		if (this.infectionState.containsKey(disease)) {
			return this.infectionState.get(disease) > 0;
		}
		return false;
	}

	/**
	 * Get all the diseases that makes the town a cluster
	 * 
	 * @return The list of diseases that makes the town a cluster
	 */
	public ArrayList<Disease> getClusterDisease() {
		ArrayList<Disease> clusterDiseases = new ArrayList<Disease>();
		for (Disease disease : this.infectionCluster.keySet()) {
			if (this.isCluster(disease)) {
				clusterDiseases.add(disease);
			}
		}
		return clusterDiseases;
	}

	/**
	 * Give a String that describes all information on town
	 * 
	 * @return The string of informations
	 */
	public String toString() {
		String res = "Informations on the town you're on : Name = " + this.name;
		String inf = "";
		Iterator<Entry<Disease, Integer>> iterator = this.infectionState.entrySet().iterator();
		int cpt = 0;
		while (iterator.hasNext()) {
			Entry<Disease, Integer> mapEntry = (Entry<Disease, Integer>) iterator.next();
			if (mapEntry.getValue() != 0) {
				cpt += 1;
				inf += mapEntry.getKey().getName() + " : " + mapEntry.getValue() + " / ";
			}
		}
		if (cpt > 0) {
			return res + ", town infection state = " + inf;
		}
		return res;
	}
}
