package pandemic.actions;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Stack;

import pandemic.Town;
import pandemic.player.Player;
import pandemic.player.Role;

/** The class that reprensents the Move's Action in the Game */
public class Move implements Action {

    /** The description of the action */
    private final String description;

    /** Builds a move action for the game */
    public Move() {
        this.description = "Move to another city";
    }

    /**
     * Get the action's description
     * 
     * @return The description of the action
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * Move the player to the town (Object o)
     * 
     * @param player The player who is playing
     * @param o The object casted into a Town
     * @return 1 (The cost of the action)
     */
    public int run(Player player, Object o) {
        player.setTown((Town) o);
        return 1;
    }

    /**
     * Tell if the player can do this action or not
     * 
     * @param player The player who plays
     * @return True if the player has the requirements to make this action
     */
    public boolean requirements(Player player) {
        return true;
    }

    /**
     * Makes the player choose an action
     * 
     * @param player The player playing the action
     * @param actionsLeft The number of actions the player has left
     * @return The cost of the action, -1 if failed
     */
    public int chooseParameter(Player player, Scanner sc, int actionsLeft) {
        String res = "Choose a city to move on, here the list of cities :\n\n";
        HashSet<Town> visited = new HashSet<Town>();
        Stack<Town> toVisit = new Stack<Town>();
        Town playerTown = player.getTown();
        HashMap<Integer, HashSet<Town>> movableTownsByCost = new HashMap<>();
        toVisit.push(playerTown);

        Town currentTown = playerTown;
        HashSet<Town> neighbors = new HashSet<Town>();

        if (player.getRole() == Role.GLOBETROTTER) {
            actionsLeft = 0;
            player.movableTowns(currentTown, neighbors);
            movableTownsByCost.put(1, neighbors);
            Iterator<Town> neighborsIt = neighbors.iterator();
            while (neighborsIt.hasNext()) {
                res += neighborsIt.next().getName();
                if (neighborsIt.hasNext()) {
                    res += " / ";
                }
            }
        }
        for (int i = 1; i <= actionsLeft; i++) {
            neighbors = new HashSet<Town>();
            res += "Cost : " + i + " :\n";
            while (toVisit.size() > 0) {
                currentTown = toVisit.pop();
                if (!visited.contains(currentTown)) {
                    for (Town neighbor : currentTown.getNeighbors()) {
                        if (!visited.contains(neighbor)) {
                            neighbors.add(neighbor);
                        }
                    }
                    visited.add(currentTown);
                }
            }
            Iterator<Town> neighborsIt = neighbors.iterator();
            while (neighborsIt.hasNext()) {
                Town neighbor = neighborsIt.next();
                if (!visited.contains(neighbor)) {
                    toVisit.push(neighbor);
                    res += neighbor.getName();
                    if (neighborsIt.hasNext()) {
                        res += " / ";
                    }
                }
            }
            movableTownsByCost.put(i, neighbors);
            res += "\n";
        }
        
        System.out.println(res + "\nexit\n");
        System.out.println("Enter a town name !\n");
        String townName = sc.next();
        if (townName.equals("exit")) {
            return 0;
        }
        for (HashMap.Entry<Integer, HashSet<Town>> entry : movableTownsByCost.entrySet()) {
            for (Town newTown : entry.getValue()) {
                if (townName.equals(newTown.getName())) {
                    if (this.run(player, newTown) == 1) {
                        return entry.getKey();
                    }
                }
            }
        }
        System.out.println("This town doesn't exist, retry");
        return -1;
    }

    /**
     * Makes the player choose an action randomly
     * 
     * @param player The player playing the action
     * @param actionsLeft The number of actions the player has left
     * @return The cost of the action
     */
    public int chooseRandomParameter(Player player, int actionsLeft) {
        System.out.println("Choose a city to move on, here the list of cities :\n\n");
        String res = "Cost : 1 \n";
        Town playerTown = player.getTown();
        HashMap<Integer, HashSet<Town>> movableTownsByCost = new HashMap<>();
        HashSet<Town> movableTowns = new HashSet<Town>();
        player.movableTowns(playerTown, movableTowns);
        movableTownsByCost.put(1, movableTowns);
        for (Town town : movableTowns) {
            if (town != playerTown) {
                res += (town.getName() + " / ");
            }
        }
        res += "\n";
        if (player.getRole() == Role.GLOBETROTTER) {
            actionsLeft = 0;
        }
        for (int i = 2; i <= actionsLeft; i++) {
            res += "Cost : " + i + "\n";
            movableTowns = new HashSet<Town>();
            HashSet<Town> neighbors = movableTownsByCost.get(i - 1);
            for (Town neighbor : neighbors) {
                HashSet<Town> townsToAdd = new HashSet<Town>();
                player.movableTowns(neighbor, townsToAdd);
                for (Town townToAdd : townsToAdd) {
                    if (!neighbors.contains(townToAdd)) {
                        movableTowns.add(townToAdd);
                    }
                }
            }
            for (Town town : movableTowns) {
                if (town != playerTown) {
                    res += (town.getName() + " / ");
                }
            }
            res += "\n";
            movableTownsByCost.put(i, movableTowns);
        }
        System.out.println(res + "exit\n");
        System.out.println("Enter a town name !\n");
        int index = (int) Math.floor(Math.random() * movableTownsByCost.size());
        int townIndex = (int) Math.floor(Math.random() * movableTownsByCost.get(index + 1).size());
        Iterator<HashMap.Entry<Integer, HashSet<Town>>> it = movableTownsByCost.entrySet().iterator();
        HashMap.Entry<Integer, HashSet<Town>> entry = it.next();
        int temp = 0;
        while (temp < index) {
            entry = it.next();
            temp++;
        }
        Iterator<Town> townsIt = entry.getValue().iterator();
        Town newTown = townsIt.next();
        while (townIndex > 0) {
            newTown = townsIt.next();
            townIndex--;
        }
        System.out.println("You chose to move on the town " + newTown.getName() + "\n");
        this.run(player, newTown);
        return index + 1;
    }
}