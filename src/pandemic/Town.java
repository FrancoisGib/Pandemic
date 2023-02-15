package pandemic;

import java.util.ArrayList;

/** The class that defines a town in the infection game */
public class Town {

	/** The Town's infection state */
	private int infectionState;

	/** The name of the Town */
	private String name;

	/** The list of neighbors the Town has */
	private ArrayList<Town> neighbors;

	/** The sector of the Town */
	private int sector;

	/** true if the Town has a research center, else false */
	private boolean researchCenter;

	/**
	 * Builds a Town
	 * 
	 * @param name, the name of the Town
	 * @param sector, the sector of the Town
	 */
	public Town(String name, int sector) {
		this.infectionState = 0;
		this.name = name;
		this.neighbors = new ArrayList<Town>();
		this.sector = sector;
		this.researchCenter = false;
	}

	/**
	 * Give the Town's infection state
	 * 
	 * @return the infection state of the Town
	 */
	public int getInfectionState() {
		return infectionState;
	}

	/**
	 * Set the infection state to the specified parameter infectionState
	 * 
	 * @param infectionState, the infection state to apply
	 */
	public void setInfectionState(int infectionState) {
		this.infectionState = infectionState;
	}
	
	/** Update the infection state by adding 1 to it */
	public void updateInfectionState() {
		this.infectionState = this.infectionState + 1;
	}
	

	/**
	 * Get the Town's neighbors
	 * 
	 * @return the list of the Town's neighbors
	 */
	public ArrayList<Town> getNeighbors() {
		return neighbors;
	}

	/**
	 * Add a neighbor into the Town's neighbors list
	 * 
	 * @param neighbor, the neighbor to add into the list
	 */
	public void addNeighbor(Town neighbor) {
		this.neighbors.add(neighbor);
	}

	/**
	 * Give the name of the Town
	 * 
	 * @return the Town's name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Give the Town's sector
	 * 
	 * @return the sector number
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
	 * @return boolean, true if the town has a research center, else false.
	 */
	public boolean hasResearchCenter() {
		return this.researchCenter;
	}

}
