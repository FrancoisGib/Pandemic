package pandemic;

import java.util.ArrayList;
import java.util.HashMap;

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
	 * @param name The name of the Town
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
	public int getInfectionState(Disease disease) throws NoSuchDiseaseException {
		if (this.infectionState.containsKey(disease)) {
			return this.infectionState.get(disease);
		}
		throw new NoSuchDiseaseException("This town is not infected by " + disease.getName());
	}

	/**
	 * Set the infection state to the specified parameter infectionState
	 * 
	 * @param infectionState The infection state to apply
	 */
	public void setInfectionState(int infectionState, Disease disease) {
		if (this.infectionState.containsKey(disease)) {
			this.infectionState.replace(disease, infectionState);
		}
		else {
			this.infectionState.put(disease, infectionState);
		}
	}
	
	public void decreaseInfectionState(Disease disease) throws NoSuchDiseaseException {
		if (this.infectionState.containsKey(disease)) {
			this.infectionState.replace(disease, this.getInfectionState(disease)-1);
		}
		else {
			throw new NoSuchDiseaseException("This town is not infected by " + disease.getName());
		}
	}
	
	/** Update the infection state by adding 1 to it */
	public void updateInfectionState(Disease disease) throws NoSuchDiseaseException {
		if (this.infectionState.containsKey(disease)) {
			this.infectionState.replace(disease, this.getInfectionState(disease)+1);
		}
		else {
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
	public void buildResearchCenter() {
		this.researchCenter = true;
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
}
