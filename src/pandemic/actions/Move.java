package pandemic.actions;

import java.util.HashSet;
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

    public int run(Player player, Scanner sc) {
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
				player.setTown(newTown);;
				return 0;
			}
        }
        System.out.println("This town doesn't exist, retry");
		return -1;
    }

    public boolean requirements(Player player) {
        return true;
    }
}


