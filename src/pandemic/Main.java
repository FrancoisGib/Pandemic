package pandemic;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashSet;
import pandemic.cards.Card;

public class Main {
	public static void main(String[] args) throws FileNotFoundException {
		try {
			Map map = new Map();
			map.setMapWithJSON("json/villes48.json");
			System.out.println(map.toString());
			Town a = map.getTown(0);

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
				System.out.println("Ville : " + card.getTownName() + " et la maladie est : "+ card.getDiseaseName());
			}

			Globetrotter g2 = new Globetrotter("a", a);
			ArrayList<Town> hash = g2.getTowns();
			/*for (Town t: hash) {
				System.out.println(t.getName());
			}*/
			System.out.println(g2.getTownName());
			g2.move();
			System.out.println(g2.getTownName());
			g2.move();
			System.out.println(g2.getTownName());
		}
		catch(NoSuchTownException e) {
			System.out.println("Error in the json file");
		}
		catch(FileNotFoundException e) {
			System.out.println("File not found");
		}

	}
}
