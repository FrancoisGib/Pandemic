package pandemic;

import java.util.ArrayList;
import java.util.Arrays;

import java.io.FileNotFoundException;

import pandemic.player.*;
import pandemic.actions.*;

public class Main {
    public static void main(String[] args) {
		if (args.length < 2) {
			System.out.println("Put two parameters to start the game (json file and number of players)");
			System.exit(-1);
		}
		Map map = new Map();
		try {
			map.setMapWithJSON("json/" + args[0]);
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
		ArrayList<Player> playersByNumber = new ArrayList<Player>();
		for (int i = 0; i < Integer.parseInt(args[1]); i++) {
			playersByNumber.add(i, players.get(i));
		}
		Game game = new Game(map, playersByNumber, diseases, actions);
		if (args.length == 2) {
			game.run(true);
		}
		else {
			game.run(false);
		}
	}
}

