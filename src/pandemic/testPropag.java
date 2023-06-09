package pandemic;
import java.util.ArrayList;
import java.util.Arrays;

import java.io.FileNotFoundException;

import pandemic.player.*;
import pandemic.actions.*;
public class testPropag {
    public static void main(String[] args) {
		Map map = new Map();
		Game game = null;
		try {
			map.setMapWithJSON("json/villes48.json");
		}
		catch(NoSuchTownException e) {
			System.out.println("Error with the towns");
		}
		catch(FileNotFoundException e) {
			System.out.println("File not found");
		}
		Disease d1 = new Disease("EBOLA", 0);
		Disease d2 = new Disease("SIDA", 1);
		Disease d3 = new Disease("CORONAVIRUS", 2);
		Disease d4 = new Disease("CHIKUNGUNYA", 3);
		ArrayList<Disease> diseases = new ArrayList<Disease>(Arrays.asList(d1, d2, d3, d4));

		ArrayList<Action> actions = new ArrayList<Action>(Arrays.asList(new Move(), new BuildResearchCenter(), new DiscoverCure(), new TreatDisease()));

		Player p1 = new Player("Globetrotter", Role.GLOBETROTTER);
		Player p2 = new Player("Expert", Role.EXPERT);
		Player p3 = new Player("Scientist", Role.SCIENTIST);
		Player p4 = new Player("Doctor", Role.DOCTOR);
		ArrayList<Player> players = new ArrayList<Player>(Arrays.asList(p1, p2, p3, p4));

		game = new Game(map, players, diseases, actions);
		for (int i = 0; i < 100; i++) {
            game.startInfectionPhase(1000);
        }
        System.out.println(game.toStringInfectionState(p1));
		}
}


