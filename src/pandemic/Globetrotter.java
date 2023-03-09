package pandemic;

import java.util.*;

import javax.swing.text.AttributeSet.FontAttribute;

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

	public void move() throws NoSuchTownException {
		System.out.println("Choose a city to move on, here the list of cities :\n\n");
		int i = 0;
		String res = "";
		for (Town town : this.towns) {
			res += (town.getName() + " / ");
			i++;
		}
		System.out.println(res);
		System.out.println("Enter a town name !");
		Scanner sc = new Scanner(System.in); 
		String townName = sc.nextLine();
		boolean found = false;
		i = 0;
		while (i < this.towns.size() && !found) {
			Town newTown = this.towns.get(i);
			if (townName.equals(newTown.getName())) {
				this.town = newTown;
				found = true;
			}
			i++;
		}
		sc.close();
		if (!found) {
			throw new NoSuchTownException("This town doesn't exist");
		}
	}
}
