package pandemic;

import java.util.ArrayList;
import java.util.Scanner;
import java.io.FileNotFoundException;

import pandemic.cards.*;
import pandemic.player.*;

public class Main2 {
    public static void main(String[] args) {
		try {
			Map map = new Map();
			map.setMapWithJSON("json/villes48.json");
			System.out.println(map.toString());
            ArrayList<Town> towns = map.getTowns();

			Disease d1 = new Disease("Ebola");
			Disease d2 = new Disease("Sida");
			Disease d3 = new Disease("Coronavirus");
			Disease d4 = new Disease("Chikungunya");

            ArrayList<Disease> diseases = new ArrayList<Disease>();
            diseases.add(d1);
            diseases.add(d2);
            diseases.add(d3);
            diseases.add(d4);

			ArrayList<Card> cards = new ArrayList<Card>();
			for (Town town : towns) {
				int sector = town.getSector();
				if (sector == 1) {
					cards.add(new Card(town, d1));
				}
				if (sector == 2) {
					cards.add(new Card(town, d2));
				}
				if (sector == 3) {
					cards.add(new Card(town, d3));
				}
				if (sector == 4) {
					cards.add(new Card(town, d4));
				}
			}
            CardsStack playerCardsStack = new CardsStack(new ArrayList<Card>());
			CardsStack infectionCardsStack = new CardsStack(cards);
            Town mainTown = map.getTown(0);
			Player g2 = new Globetrotter("Globetrotter", mainTown);
            ArrayList<Player> players = new ArrayList<Player>();
            players.add(g2);

            Game game = new Game(map, players, diseases, playerCardsStack, infectionCardsStack);

            game.getMap().toStringInfectionState();

            game.startInfectionPhase();
            
            game.getMap().toStringInfectionState();

			Scanner sc = new Scanner(System.in);
			System.out.println("Le joueur est sur la ville : " + g2.getTownName());
			g2.chooseAction(sc);
			System.out.println("Le joueur est sur la ville : " + g2.getTownName());
			g2.chooseAction(sc);
			sc.close();
			
		}
		catch(NoSuchTownException e) {
			System.out.println("Error with the towns");
		}
		catch(FileNotFoundException e) {
			System.out.println("File not found");
		}
		catch(NoSuchDiseaseException e) {
			System.out.println("Error with the diseases");
		}

	}
}
