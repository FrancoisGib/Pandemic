package pandemic;

import java.util.ArrayList;

import pandemic.cards.Card;
import pandemic.cards.CardsStack;
import pandemic.player.*;

import java.io.FileNotFoundException;

public class MainTest {
    public static void main(String[] args) {
		try {
            Map map = new Map();
            map.setMapWithJSON("json/villes12.json");
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

            ArrayList<Card> playerCards = new ArrayList<Card>();
            ArrayList<Card> infectionCards = new ArrayList<Card>();

            for (Town town : towns) {
                int sector = town.getSector();
				if (sector == 0) {
					playerCards.add(new Card(town, d1));
                    infectionCards.add(new Card(town, d1));
				}
				if (sector == 1) {
					playerCards.add(new Card(town, d2));
                    infectionCards.add(new Card(town, d2));
				}
				if (sector == 2) {
					playerCards.add(new Card(town, d3));
                    infectionCards.add(new Card(town, d3));
				}
				if (sector == 3) {
					playerCards.add(new Card(town, d4));
                    infectionCards.add(new Card(town, d4));
				}
            }

            CardsStack playerCardsStack = new CardsStack(playerCards);
            CardsStack infectionCardsStack = new CardsStack(infectionCards);

            int i = (int)Math.random()*12;
			Player p1 = new Doctor("Docteur", towns.get(i));

            i = (int)Math.random()*12;
			Player p2 = new Globetrotter("Globetrotter", towns.get(i));

            i = (int)Math.random()*12;
			Player p3 = new Scientist("Scientist", towns.get(i));

            i = (int)Math.random()*12;
			Player p4 = new Expert("Expert", towns.get(i));

            ArrayList<Player> players = new ArrayList<Player>();
            players.add(p1);
            players.add(p2);
            players.add(p3);
            players.add(p4);

            Game game = new Game(map, players, diseases, playerCardsStack, infectionCardsStack);
            System.out.println(game.getMap().toStringInfectionState());


			game.startInfectionPhase();
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
