package pandemic;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;

import org.junit.Before;
import org.junit.Test;

public class TownTest {
	private Town town1;
	private Town town2;
	private Disease d1;
	private Disease d2;
	
	@Before
	public void init() {
		this.town1 = new Town("Town1", 1);
		this.town2 = new Town("Town2", 2);
		this.d1 = new Disease("Disease1", 1);
		this.d2 = new Disease("Disease2", 2);
	}

	@Test
<<<<<<< HEAD
	public void UpdateAndSetInfectionStateTest() {

		assertEquals(-1, this.town1.getInfectionState(this.d1));
		this.town1.updateInfectionState(this.d1);
		assertEquals(1, this.town1.getInfectionState(this.d1));
		
		int newInfectionState = 3;
		this.town2.setInfectionState(newInfectionState, this.d1);
		assertEquals(3, this.town2.getInfectionState(this.d1));

		this.town2.decreaseInfectionState(this.d1);
=======
	public void setInfectionStateTest() {

		assertEquals(-1, this.town1.getInfectionState(this.d1));
		this.town1.setInfectionState(this.d1, 1);
		assertEquals(1, this.town1.getInfectionState(this.d1));
		
		this.town2.setInfectionState(this.d1, 3);
		assertEquals(3, this.town2.getInfectionState(this.d1));

		this.town2.setInfectionState(this.d1, -1);
>>>>>>> François
		assertEquals(2, this.town2.getInfectionState(this.d1));
	}
	
	@Test
	public void addNeighborTest() {
		ArrayList<Town> list = new ArrayList<Town>();
		list.add(this.town2);
		
		assertFalse(list.equals(this.town1.getNeighbors()));
		
		this.town1.addNeighbor(this.town2);
		assertTrue(list.equals(this.town1.getNeighbors()));
	}
	
	@Test
	public void buildResearchCenterTest() {
		assertFalse(this.town1.hasResearchCenter());
		this.town1.buildResearchCenter();
		assertTrue(this.town1.hasResearchCenter());
		assertFalse(this.town1.buildResearchCenter());
	}

	@Test
	public void getAllInfectionStateTest() {
<<<<<<< HEAD
		this.town1.setInfectionState(1, this.d1);
		HashMap<Disease, Integer> infectionStates = this.town1.getAllInfectionState();
		assertSame(1, infectionStates.get(this.d1));

		this.town1.setInfectionState(0, this.d2);
=======
		this.town1.setInfectionState(this.d1, 1);
		HashMap<Disease, Integer> infectionStates = this.town1.getAllInfectionState();
		assertSame(1, infectionStates.get(this.d1));

		this.town1.setInfectionState(this.d2, 0);
>>>>>>> François
		assertSame(0, infectionStates.get(this.d2));
	}

	@Test
<<<<<<< HEAD
	public void setInfectionClusterTest() {
		assertFalse(this.town1.isCluster());
		this.town1.setInfectionCluster();
		assertTrue(this.town1.isCluster());
=======
	public void clusterTest() {
		assertFalse(this.town1.isCluster(this.d1));
		this.town1.setInfectionState(this.d1, 3);
		this.town1.setInfectionState(this.d1, 1);
		assertTrue(this.town1.isCluster(this.d1));
>>>>>>> François
	}

	@Test
	public void isInfectedTest() {
<<<<<<< HEAD
		this.town1.setInfectionState(1, this.d1);
		assertTrue(this.town1.isInfected(this.d1));
		this.town1.decreaseInfectionState(this.d1);
=======
		this.town1.setInfectionState(this.d1, 1);
		assertTrue(this.town1.isInfected(this.d1));
		this.town1.setInfectionState(this.d1, -1);
>>>>>>> François
		assertFalse(this.town1.isInfected(this.d1));
	}

	@Test
	public void getClusterDiseaseTest() {
<<<<<<< HEAD
		this.town1.setInfectionState(3, this.d2);
		ArrayList<Disease> clusters = this.town1.getClusterDisease();
		assertTrue(clusters.contains(this.d2));
		this.town1.setInfectionState(3, this.d1);
		ArrayList<Disease> newClusters = this.town1.getClusterDisease();
		assertFalse(newClusters.equals(clusters));
		assertTrue(newClusters.contains(this.d1));
=======
		this.town1.setInfectionState(this.d2, 3);
		this.town1.setInfectionState(this.d2, 1);
		ArrayList<Disease> clusters = this.town1.getClusterDisease();
		assertTrue(clusters.contains(this.d2));
		this.town1.setInfectionState(this.d1, 3);
		ArrayList<Disease> newClusters = this.town1.getClusterDisease();
		assertTrue(newClusters.equals(clusters));
		assertFalse(newClusters.contains(this.d1));
>>>>>>> François
	}
 
	
}
