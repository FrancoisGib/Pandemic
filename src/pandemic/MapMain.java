package pandemic;

import java.io.FileNotFoundException;
import java.util.ArrayList;

public class MapMain {
	public static void main(String[] args) throws FileNotFoundException {
		try {
			Map map = new Map();
			map.setMapWithJSON("json/villes48.json");
			System.out.println(map.toString());
		}
		catch(NoSuchTownException e) {
			System.out.println("Error in the json file");
		}
		catch(FileNotFoundException e) {
			System.out.println("File not found");
		}
		System.out.println("\n");
		
		Town town1 = new Town("1", 0);
		Town town2 = new Town("2", 0);
		town1.addNeighbor(town2);
		town2.addNeighbor(town1);
		ArrayList<Town> towns = new ArrayList<Town>();
		towns.add(town1);
		towns.add(town2);
		
		Map map2 = new Map();
		map2.setMap(towns);
		System.out.println(map2.toString());
	}
}
