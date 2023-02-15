package pandemic;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;

import org.junit.Test;

public class TownsJsonReaderTest {
	
	@Test(expected=FileNotFoundException.class)
	public void initTest() throws FileNotFoundException {
		String filename = "wrongPath.json";
		new TownsJsonReader(filename);
	}

	@Test
	public void setTownsTest() throws FileNotFoundException {
		String filename = "json/MapForTest.json";
		TownsJsonReader jsonReader = new TownsJsonReader(filename);
		HashMap<String,Integer> towns = new HashMap<String,Integer>();
		towns.put("ville-1", 1);
		towns.put("ville-2", 2);
		towns.put("ville-3", 3);
		assertFalse(towns.equals(jsonReader.getTowns()));
		
		jsonReader.setTowns();
		assertEquals(towns, jsonReader.getTowns());
	}
	
	@Test
	public void setNeighborsTest() throws FileNotFoundException {
		String filename = "json/MapForTest.json";
		TownsJsonReader jsonReader = new TownsJsonReader(filename);
		HashMap<String, ArrayList<String>> towns = new HashMap<String, ArrayList<String>>();
		
		ArrayList<String> list1 = new ArrayList<String>();
		list1.add("ville-2");
		list1.add("ville-3");
		
		ArrayList<String> list2 = new ArrayList<String>();
		list2.add("ville-1");
		list2.add("ville-3");
		
		ArrayList<String> list3 = new ArrayList<String>();
		list3.add("ville-1");
		list3.add("ville-2");
		
		towns.put("ville-1", list1);
		towns.put("ville-2", list2);
		towns.put("ville-3", list3);
		assertFalse(towns.equals(jsonReader.getNeighbors()));
		
		jsonReader.setNeighbors();
		assertEquals(towns, jsonReader.getNeighbors());
	}
}

