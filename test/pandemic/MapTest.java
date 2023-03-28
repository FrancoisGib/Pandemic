package pandemic;

import java.io.FileNotFoundException;
import java.util.ArrayList;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class MapTest {
	private Map map1;
	private Map map2;
	private Town town1;
	private Town town2;

	@Before
	public void init() {
		this.map1 = new Map();
		this.map2 = new Map();
		this.town1 = new Town("town1", 1);
		this.town2 = new Town("town2", 2);
	}
	
	@Test(expected=NoSuchTownException.class)
	public void setMapWithJSONTest() throws NoSuchTownException, FileNotFoundException {
		ArrayList<Town> emptyList = new ArrayList<Town>();
		assertEquals(emptyList, this.map1.getTowns());
		
		String trueFile = "json/MapForTest.json";
		this.map1.setMapWithJSON(trueFile);
		assertFalse(emptyList.equals(this.map1.getTowns()));
		
		String wrongFile = "json/WrongMapForTest.json";
		this.map1.setMapWithJSON(wrongFile);
	}
	
	@Test
	public void setMapTest() {
		ArrayList<Town> emptyList = new ArrayList<Town>();
		assertEquals(emptyList, this.map2.getTowns());
		
		this.town1.addNeighbor(this.town2);
		this.town2.addNeighbor(this.town1);
		ArrayList<Town> townsList = new ArrayList<Town>();
		townsList.add(this.town1);
		townsList.add(this.town2);
		this.map2.setMap(townsList);
		assertSame(townsList, map2.getTowns());
	}
	
	@Test
	public void getTownByNameTest() {
		String townName = this.town1.getName();
		ArrayList<Town> townList = new ArrayList<Town>();
		townList.add(town1);
		this.map1.setMap(townList);
		assertSame(this.town1, this.map1.getTownByName(townName));
		
		String wrongTownName = "town2";
		assertEquals(null, this.map1.getTownByName(wrongTownName));
	}
	
	@Test
	public void areNeighborsTest() {
		Town town = new Town("town3", 0);
		this.town1.addNeighbor(this.town2);
		this.town2.addNeighbor(this.town1);
		
		assertTrue(this.map1.areNeighbors(this.town1, this.town2));
		assertFalse(this.map1.areNeighbors(this.town1, town));
	}
	

}
