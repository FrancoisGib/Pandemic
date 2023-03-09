package pandemic;

import java.util.*;

/* The class that defines a player in the game */
public class Globetrotter extends Player {
	/* The list of cards the Player has */
	private List<PlayerCardsStack> cards;
	
	private ArrayList<Town> towns;
	
	public Globetrotter(String name, Town town) {
		super(name,town);
		List<PlayerCardsStack> cards = new ArrayList<PlayerCardsStack>();
		
		this.towns = new ArrayList<Town>();
		this.initTowns(town);
	}
	
	public void initTowns(Town town) {
		boolean res = true;
		while (res) {
			ArrayList<Town> neighbors = town.getNeighbors();
			
			for(Town t : neighbors) {
				if (towns.contains(t)) {
					res = false;
				}
				else {
					this.towns.add(t);
					this.initTowns(t);
					res = true;
				}
			}
			if (!res) {
				break;
			}
		}
	}

	public ArrayList<Town> getTowns() {
		return this.towns;
	}

	public void move() {
		System.out.println("Choose a city to move on, here the list of cities :\n\n");
		int i = 0;
		for (Town town : towns) {
			System.out.println("Ville "+ i+1 + " : " + town.getName() + " / ");
			i++;
		}
		System.out.println("Enter a number !");
		Scanner sc = new Scanner(System.in); 
		int townNumber = sc.nextInt()-1;  
		this.town = towns.get(townNumber);
		sc.close();
	}
}
