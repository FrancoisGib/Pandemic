package pandemic;

import java.util.ArrayList;
import java.util.Arrays;

import java.io.FileNotFoundException;

import pandemic.player.*;

public class Main {
    public static void main(String[] args) {
		try {
			Map map = new Map();
			map.setMapWithJSON("json/villes48.json");
			Disease d1 = new Disease("EBOLA", 0);
			Disease d2 = new Disease("SIDA", 1);
			Disease d3 = new Disease("CORONAVIRUS", 2);
			Disease d4 = new Disease("CHIKUNGUNYA", 3);
            ArrayList<Disease> diseases = new ArrayList<Disease>(Arrays.asList(d1, d2, d3, d4));

			Player p1 = new Globetrotter("Globetrotter");
			Player p2 = new Expert("Expert");
			Player p3 = new Scientist("Scientist");
			Player p4 = new Doctor("Doctor");
            ArrayList<Player> players = new ArrayList<Player>(Arrays.asList(p1, p2, p3, p4));

			Game game = new Game(map, players, diseases);

			for (int i = 0; i < 2; i++) {
				game.pickInfectionCard();
				game.propagation();
			}
			System.out.println(map.toStringInfectionState());

			System.out.println(game.getGlobalInfectionState());
			System.out.println(game.getClustersNumber());
		}
		catch(NoSuchTownException e) {
			System.out.println("Error with the towns");
		}
		catch(FileNotFoundException e) {
			System.out.println("File not found");
		}
	}
}
