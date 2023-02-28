package pandemic;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashSet;

public class Main {
	public static void main(String[] args) throws FileNotFoundException {
		try {
			Map map = new Map();
			map.setMapWithJSON("json/villes48.json");
			System.out.println(map.toString());
			Town a = map.getTown(0);

			Player g2 = new Globetrotter("a", a);
			HashSet<Town> hash = g2.getTowns();
			String res = "";
			for (Town t: hash) {
				res += t.getName() + " / ";
			}
			System.out.println(res);
			System.out.println(hash.size());
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
