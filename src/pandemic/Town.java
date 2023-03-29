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
	private boolean infectionCluster;

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
		this.infectionCluster = false;
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
		System.out.println("This town is not infected by " + disease.getName());
		return -1;
	}

	/**
	 * Set the infection states for the disease in parameter
	 * 
	 * @param infectionState The infection state to apply
	 * @param disease        The disease to set the town infection state for
	 */
	public boolean setInfectionState(int infectionState, Disease disease) {
		if (this.infectionState.containsKey(disease)) {
			this.infectionState.replace(disease, infectionState);
		} else {
			this.infectionState.put(disease, infectionState);
		}
		if (infectionState == 3 && !this.infectionCluster) {
			this.setInfectionCluster();
		}
		if (this.infectionCluster && this.infectionState.get(disease) < 3) {
			this.removeInfectionCluster();
		}
		return true;
	}

	/**
	 * Decrease the town infection state for the specified disease
	 * 
	 * @param disease The disease to decrease the town infection state
	 */
	public boolean decreaseInfectionState(Disease disease) {
		if (this.infectionState.containsKey(disease)) {
			if (this.infectionState.get(disease) > 0) {
				this.infectionState.replace(disease, this.getInfectionState(disease) - 1);
				return true;
			}
			if (this.infectionCluster && this.infectionState.get(disease) < 3) {
				this.removeInfectionCluster();
			}
		}
		return false;
	}

	/**
	 * Update the infection state of the town for the disease in parameters
	 * 
	 * @param disease The disease to update the town infection state
	 */
	public void updateInfectionState(Disease disease) {
		if (this.infectionState.containsKey(disease)) {
			int inf = this.infectionState.get(disease);
			if (inf == 3 && !this.infectionCluster) {
				this.setInfectionCluster();
			}
			else if (inf < 3) {
				this.infectionState.replace(disease, inf + 1);
			}
		} else {
			this.infectionState.put(disease, 1);
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
	 * Set the town has a cluster
	 */
	public void setInfectionCluster() {
		this.infectionCluster = true;
	}

	/**
	 * Remove the infection cluster
	 */
	public void removeInfectionCluster() {
		this.infectionCluster = false;
	}

	/**
	 * Tell if the town is a cluster or not
	 * 
	 * @return True if the town is a cluster, else false
	 */
	public boolean isCluster() {
		return this.infectionCluster;
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
		if (this.isCluster()) {
			Iterator<Entry<Disease, Integer>> iterator = this.infectionState.entrySet().iterator();
			while (iterator.hasNext()) {
				Entry<Disease, Integer> mapEntry = (Entry<Disease, Integer>) iterator.next();
				if (mapEntry.getValue() == 3) {
					clusterDiseases.add(mapEntry.getKey());
				}
			}
			return clusterDiseases;
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
