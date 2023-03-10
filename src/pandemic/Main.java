package pandemic;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import pandemic.cards.Card;
import pandemic.player.Globetrotter;
import pandemic.player.Player;

public class Main {
	public static void main(String[] args) {
		try {
			Map map = new Map();
			map.setMapWithJSON("json/villes48.json");
			System.out.println(map.toString());
			Town mainTown = map.getTown(0);

			ArrayList<Town> towns = map.getTowns();
			Disease d1 = new Disease("Ebola");
			Disease d2 = new Disease("Sida");
			Disease d3 = new Disease("Coronavirus");
			Disease d4 = new Disease("Chikungunya");

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
			CardsStack cardsStack = new CardsStack(cards);

			while (!cardsStack.hasCardsLeft()) {
				Card card = cardsStack.pickCard();
				System.out.println(card.getTownName() + ", la maladie de ce secteur est : "+ card.getDiseaseName());
			}

			Player g2 = new Globetrotter("Globetrotter", mainTown);

			Scanner sc = new Scanner(System.in);
			System.out.println("Le joueur est sur la ville : " + g2.getTownName());
			g2.move(sc);
			System.out.println("Le joueur est sur la ville : " + g2.getTownName());
			g2.move(sc);
			System.out.println("Le joueur est sur la ville : " + g2.getTownName());
			sc.close();
		}
		catch(NoSuchTownException e) {
			System.out.println("Error");
		}
		catch(FileNotFoundException e) {
			System.out.println("File not found");
		}

	}
}
