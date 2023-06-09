package pandemic;

import java.util.ArrayList;
import java.util.Arrays;

import java.io.FileNotFoundException;

import pandemic.player.*;

public class Main {
    public static void main(String[] args) {
		Map map = new Map();
		try {
			map.setMapWithJSON("json/villes48.json");
		}
		catch(NoSuchTownException e) {
			System.out.println("Error with the towns");
		}
		catch(FileNotFoundException e) {
			System.out.println("File not found");
		}
		finally {
			Disease d1 = new Disease("EBOLA", 0);
			Disease d2 = new Disease("SIDA", 1);
			Disease d3 = new Disease("CORONAVIRUS", 2);
			Disease d4 = new Disease("CHIKUNGUNYA", 3);
            ArrayList<Disease> diseases = new ArrayList<Disease>(Arrays.asList(d1, d2, d3, d4));

			Player p1 = new Player("Globetrotter", Role.GLOBETROTTER);
			Player p2 = new Player("Expert", Role.GLOBETROTTER);
			Player p3 = new Player("Scientist", Role.GLOBETROTTER);
			Player p4 = new Player("Doctor", Role.GLOBETROTTER);
            ArrayList<Player> players = new ArrayList<Player>(Arrays.asList(p1, p2, p3, p4));

			Game game = new Game(map, players, diseases);

			for (Player p : players) {
				p.pickPlayerCard(game.getPlayerCardsStack()); // Les joueurs tirent 2 cartes
			}

			game.pickInfectionCard(); // Pick les deux cartes infection
			game.pickInfectionCard();

			System.out.println(map.toStringInfectionState()); // Les villes non-infectés ne sont pas affichés

			System.out.println("Le global infection state est : " + game.getGlobalInfectionState());
			System.out.println("Le nombre de clusters est de : " + game.getClustersNumber());
		}
	}
}
