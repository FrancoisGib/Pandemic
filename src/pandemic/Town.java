package pandemic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Iterator;

/** The class that defines a town in the infection game */
public class Town {

	/** The Town's infection state */
	private HashMap<Disease, Integer> infectionState;

	/** The name of the Town */
	private String name;

	/** The list of neighbors the Town has */
	private ArrayList<Town> neighbors;

	/** The sector of the Town */
	private int sector;

	/** true if the Town has a research center, else false */
	private boolean researchCenter;

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
	 * Give the Town's infection state
	 * 
	 * @return The infection state of the Town
	 */
	public int getInfectionState(Disease disease) {
		if (this.infectionState.containsKey(disease)) {
			return this.infectionState.get(disease);
		}
		System.out.println("This town is not infected by " + disease.getName());
		return -1;
	}

	/**
	 * Set the infection state to the specified parameter infectionState
	 * 
	 * @param infectionState The infection state to apply
	 */
	public void setInfectionState(int infectionState, Disease disease) {
		if (this.infectionState.containsKey(disease)) {
			this.infectionState.replace(disease, infectionState);
		} else {
			this.infectionState.put(disease, infectionState);
		}
		if (infectionState == 3 && !this.infectionCluster) {
			this.setInfectionCluster();
		}
	}

	public void decreaseInfectionState(Disease disease) {
		if (this.infectionState.containsKey(disease)) {
			if (this.infectionState.get(disease) > 0) {
				this.infectionState.replace(disease, this.getInfectionState(disease) - 1);
				System.out.println("\nThe current town infection state for the disease " + disease.getName()
				+ " has been decreased by 1, it is now of " + this.getInfectionState(disease));
			} else {
				System.out.println("\nThe global infection state for this disease is 0 in this town");

			}
		} else {
			System.out.println("\nThis town is not infected by " + disease.getName() + " , nothing happened");
		}
	}

	/** Update the infection state by adding 1 to it */
	public void updateInfectionState(Disease disease) {
		if (this.infectionState.containsKey(disease)) {
			int inf = this.infectionState.get(disease);
			if (inf < 3) {
				this.infectionState.replace(disease, inf + 1);
			}
		} else {
			this.infectionState.put(disease, 1);
		}
		if (this.infectionState.get(disease) == 3 && !this.infectionCluster) {
			this.setInfectionCluster();
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

	public HashMap<Disease, Integer> getAllInfectionState() {
		return this.infectionState;
	}

	public void setInfectionCluster() {
		this.infectionCluster = true;
	}

	public boolean isCluster() {
		return this.infectionCluster;
	}

	public boolean isInfected(Disease disease) {
		if (!this.infectionState.containsKey(disease)) {
			return false;
		}
		return true;
	}

	public ArrayList<Disease> getClusterDisease() {
		if (this.isCluster()) {
			Iterator<Entry<Disease, Integer>> iterator = this.infectionState.entrySet().iterator();
			ArrayList<Disease> clusterDiseases = new ArrayList<Disease>();
			while (iterator.hasNext()) {
				Entry<Disease, Integer> mapEntry = (Entry<Disease, Integer>) iterator.next();
				if (mapEntry.getValue() == 3) {
					clusterDiseases.add(mapEntry.getKey());
				}
			}
			return clusterDiseases;
		}
		return null;
	}

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
