package pandemic;

public class MapVisualizer {
	public static void main(String[] args) {
		try {
			Map map = new Map("villes48.json");
			map.initMap();
			System.out.println(map.toString());
		}
		catch(NoSuchTownException e) {
			System.out.println("Error in the json file");
		}
	}
	
}
