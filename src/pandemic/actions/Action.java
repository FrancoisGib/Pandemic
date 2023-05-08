package pandemic.actions;

import java.util.Scanner;

import pandemic.player.Player;

/** The class that reprensents an Action in the Game */
public interface Action {

    /**
     * Run the action
     * 
     * @param player The player who plays the action
     * @param o The object to be casted into the type needed for the action
     * @return The cost of the action if the action has been successfully made, else -1
     */
    int run(Player player, Object o);

    /**
     * Tell if the player can do this action or not
     * 
     * @param player The player who plays
     * @return True if the player has the requirements to make this action
     */
    boolean requirements(Player player);

    /**
     * Get the description of the action
     * 
     * @return The description of the action
     */
    String getDescription();

    /**
     * Makes the player choose an action
     * 
     * @param player The player playing the action
     * @param sc The scanner to make choice
     * @param actionsLeft The number of actions the player has left
     * @return The cost of the action 
     */
    int chooseParameter(Player player, Scanner sc, int actionsLeft);

    /**
     * Makes the player choose an action randomly
     * 
     * @param player The player playing the action
     * @param actionsLeft The number of actions the player has left
     * @return The cost of the action 
     */
    int chooseRandomParameter(Player player, int actionsLeft);
}
