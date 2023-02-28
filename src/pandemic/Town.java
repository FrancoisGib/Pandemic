package pandemic;
<<<<<<< HEAD
import java.util.HashSet;
=======

import java.util.ArrayList;
>>>>>>> 352eef3fbdfecc1c15e20a751005f81ddf898923

/** The class that defines a town in the infection game */
public class Town {

	/** The Town's infection state */
	private int infectionState;

	/** The name of the Town */
	private String name;

	/** The list of neighbors the Town has */
<<<<<<< HEAD
	private HashSet<Town> neighbors;
=======
	private ArrayList<Town> neighbors;
>>>>>>> 352eef3fbdfecc1c15e20a751005f81ddf898923

	/** The sector of the Town */
	private int sector;

	/** true if the Town has a research center, else false */
	private boolean researchCenter;

	/**
	 * Builds a Town
	 * 
<<<<<<< HEAD
	 * @param name The name of the Town
	 * @param sector The sector of the Town
=======
	 * @param name, the name of the Town
	 * @param sector, the sector of the Town
>>>>>>> 352eef3fbdfecc1c15e20a751005f81ddf898923
	 */
	public Town(String name, int sector) {
		this.infectionState = 0;
		this.name = name;
<<<<<<< HEAD
		this.neighbors = new HashSet<Town>();
=======
		this.neighbors = new ArrayList<Town>();
>>>>>>> 352eef3fbdfecc1c15e20a751005f81ddf898923
		this.sector = sector;
		this.researchCenter = false;
	}

	/**
	 * Give the Town's infection state
	 * 
<<<<<<< HEAD
	 * @return The infection state of the Town
=======
	 * @return the infection state of the Town
>>>>>>> 352eef3fbdfecc1c15e20a751005f81ddf898923
	 */
	public int getInfectionState() {
		return infectionState;
	}

	/**
	 * Set the infection state to the specified parameter infectionState
	 * 
<<<<<<< HEAD
	 * @param infectionState The infection state to apply
=======
	 * @param infectionState, the infection state to apply
>>>>>>> 352eef3fbdfecc1c15e20a751005f81ddf898923
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
<<<<<<< HEAD
	 * @return The list of the Town's neighbors
	 */
	public HashSet<Town> getNeighbors() {
=======
	 * @return the list of the Town's neighbors
	 */
	public ArrayList<Town> getNeighbors() {
>>>>>>> 352eef3fbdfecc1c15e20a751005f81ddf898923
		return neighbors;
	}

	/**
	 * Add a neighbor into the Town's neighbors list
	 * 
<<<<<<< HEAD
	 * @param neighbor The neighbor to add into the list
=======
	 * @param neighbor, the neighbor to add into the list
>>>>>>> 352eef3fbdfecc1c15e20a751005f81ddf898923
	 */
	public void addNeighbor(Town neighbor) {
		this.neighbors.add(neighbor);
	}

	/**
	 * Give the name of the Town
	 * 
<<<<<<< HEAD
	 * @return The Town's name
=======
	 * @return the Town's name
>>>>>>> 352eef3fbdfecc1c15e20a751005f81ddf898923
	 */
	public String getName() {
		return name;
	}

	/**
	 * Give the Town's sector
	 * 
<<<<<<< HEAD
	 * @return The sector number
=======
	 * @return the sector number
>>>>>>> 352eef3fbdfecc1c15e20a751005f81ddf898923
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
<<<<<<< HEAD
	 * @return true if the town has a research center, else false.
=======
	 * @return boolean, true if the town has a research center, else false.
>>>>>>> 352eef3fbdfecc1c15e20a751005f81ddf898923
	 */
	public boolean hasResearchCenter() {
		return this.researchCenter;
	}

}
