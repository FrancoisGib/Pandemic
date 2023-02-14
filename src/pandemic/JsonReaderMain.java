package pandemic;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class JsonReaderMain {

	public static void main(String[] args) {
		JsonReader reader = new JsonReader("villes48.json");
		reader.setTowns();
		HashMap<String,Integer> map = reader.getTowns();
		Set<String> keys = map.keySet();
		for(String key: keys) {
			System.out.println(key + " : " + map.get(key));
		}
		reader.setNeighbors();
		HashMap<String,List<String>> neighbors = reader.getNeighbors();
		for(String neighbor: neighbors.keySet()) {
			System.out.println(neighbor + " : " + neighbors.get(neighbor));
		}
	}
}
