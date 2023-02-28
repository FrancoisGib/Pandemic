package pandemic;

import static org.junit.Assert.*;

import java.util.ArrayList;
<<<<<<< HEAD
import java.util.HashSet;
=======
>>>>>>> 352eef3fbdfecc1c15e20a751005f81ddf898923

import org.junit.Before;
import org.junit.Test;

public class TownTest {
	private Town town1;
	private Town town2;
	
	@Before
	public void init() {
		this.town1 = new Town("Town1", 1);
		this.town2 = new Town("Town2", 2);
	}

	@Test
	public void UpdateAndSetInfectionStateTest() {
		assertEquals(0, this.town1.getInfectionState());
		this.town1.updateInfectionState();
		assertEquals(1, this.town1.getInfectionState());
		
		int newInfectionState = 10;
		this.town2.setInfectionState(newInfectionState);
		assertEquals(10, this.town2.getInfectionState());
	}
	
	@Test
	public void addNeighborTest() {
<<<<<<< HEAD
		HashSet<Town> list = new HashSet<Town>();
=======
		ArrayList<Town> list = new ArrayList<Town>();
>>>>>>> 352eef3fbdfecc1c15e20a751005f81ddf898923
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
	}
	
}
