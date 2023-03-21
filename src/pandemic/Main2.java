package pandemic;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.io.FileNotFoundException;

import pandemic.cards.*;
import pandemic.player.*;

public class Main2 {
    public static void main(String[] args) {
		try {
			Map map = new Map();
			map.setMapWithJSON("json/villes48.json");
            ArrayList<Town> towns = map.getTowns();

			Disease d1 = new Disease("Ebola");
			Disease d2 = new Disease("Sida");
			Disease d3 = new Disease("Coronavirus");
			Disease d4 = new Disease("Chikungunya");
			ArrayList<Card> cards = new ArrayList<Card>();

            ArrayList<Disease> diseases = new ArrayList<Disease>();
            diseases.add(d1);
            diseases.add(d2);
            diseases.add(d3);
            diseases.add(d4);

			for (Town town : towns) {
				int sector = town.getSector();
				if (sector == 0) {
					cards.add(new Card(town, d1));
					town.setInfectionState(0, d1);
				}
				if (sector == 1) {
					cards.add(new Card(town, d2));
					town.setInfectionState(0, d2);
				}
				if (sector == 2) {
					cards.add(new Card(town, d3));
					town.setInfectionState(0, d3);
				}
				if (sector == 3) {
					cards.add(new Card(town, d4));
					town.setInfectionState(0, d4);
				}
			}

            CardsStack playerCardsStack = new CardsStack(new ArrayList<Card>());
			CardsStack infectionCardsStack = new CardsStack(cards);

            Town mainTown = map.getTown(0);
			Player g2 = new Globetrotter("Globetrotter", mainTown);
            ArrayList<Player> players = new ArrayList<Player>();
            players.add(g2);

            Game game = new Game(map, players, diseases, playerCardsStack, infectionCardsStack);

            System.out.println(game.getMap().toStringInfectionState());

			for (int i=0; i <20; i++) {
				game.startInfectionPhase();
			}
			infectionCardsStack.resetStack();

			for (int i=0; i <10; i++) {
				game.startInfectionPhase();
			}
			System.out.println(game.getMap().toStringInfectionState());
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
