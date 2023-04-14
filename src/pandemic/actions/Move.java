package pandemic.actions;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Scanner;

import pandemic.Town;
import pandemic.player.Player;

public class Move implements Action {

    private final String description;

    public Move() {
        this.description = "Move to another city";
    }
    
    public String getDescription() {
        return this.description;
    }

    public int run(Player player, Object o) {
        player.setTown((Town)o);
        return 0;
    }

    public boolean requirements(Player player) {
        return true;
    }

    public int chooseParameter(Player player, Scanner sc) {
        System.out.println("Choose a city to move on, here the list of cities :\n\n");
        String res = "";
        HashSet<Town> movableTowns = new HashSet<Town>();
        Town playerTown = player.getTown();
		player.movableTowns(playerTown, movableTowns);
		for (Town town : movableTowns) {
			if (town != playerTown) {
				res += (town.getName() + " / ");
			}
		}
		System.out.println(res + "exit\n");
		System.out.println("Enter a town name !\n");
        String townName = sc.next();
        if (townName.equals("exit")) {
			return 1;
		}
        for (Town newTown : movableTowns) {
            if (townName.equals(newTown.getName())) {
                return this.run(player, newTown);
			}
        }
        System.out.println("This town doesn't exist, retry");
		return -1;
    }

    public int chooseRandomParameter(Player player) {
        System.out.println("Choose a city to move on, here the list of cities :\n\n");
        String res = "";
        HashSet<Town> movableTowns = new HashSet<Town>();
        Town playerTown = player.getTown();
		player.movableTowns(playerTown, movableTowns);
		for (Town town : movableTowns) {
			if (town != playerTown) {
				res += (town.getName() + " / ");
			}
		}
		System.out.println(res + "exit\n");
		System.out.println("Enter a town name !\n");
        int index = (int)Math.floor(Math.random()*movableTowns.size());
        Iterator<Town> it = movableTowns.iterator();
        Town newTown = it.next();
        while (index > 0) {
            newTown = it.next();
            index--;
        }
        return this.run(player, newTown);
    }
}