package pandemic.actions;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

import pandemic.cards.Card;
import pandemic.player.Player;
import pandemic.player.Role;

/** The class that reprensents the SwapCards's Action in the Game, this is an exemple of extension of the game, the action is not implemented in the game but it can be easily */
public class SwapCards implements Action {

    /** The description of the action */
    private final String description;

    /** The players of the game */
    private ArrayList<Player> players;

    /** The player to exchange card with */
    private Player exchangePlayer;

    /** The first card to switch */
    private Card card;

    /** The cost of the action */
    private int cost;

    /** Builds an action who swap a card between players 
     * 
     * @param players The players of the game
     */
    public SwapCards(ArrayList<Player> players) {
        this.description = "Shift a card between two players";
        this.players = players;
        this.exchangePlayer = null;
        this.cost = 2;
        this.card = null;
    }

    /** Get the description of the action
     * 
     * @return The description of the action
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * Run an action chosen by the player
     * 
     * @param player The player who executes the action
     * @return The cost of the action if the action has been successfully made, 0 if the player exit, -1 if the action failed
     */
    public int run(Player player, Object o) {
        Card c = (Card)o;
        this.exchangePlayer.discardCard(c);
        this.exchangePlayer.addCard(this.card);
        player.discardCard(this.card);
        player.addCard(c);
        return this.cost;
    }

    /**
     * Tell if the player can do this action or not
     * 
     * @param player The player who plays
     * @return True if the player has the requirements to make this action
     */
    public boolean requirements(Player player) {
        boolean res = false;
        java.util.Iterator<Player> it = this.players.iterator();
        while (it.hasNext() && !res) {
            if (it.next().getCards().size() > 0) {
                res = true;
            }
        }
        return player.getRole() == Role.SHIFTER && player.getCards().size() > 0 && res;
    }

    /**
     * Makes the player choose an action
     * 
     * @param player The player playing the action
     * @param actionsLeft The number of actions the player has left
     * @return The cost of the action 
     */
    public int chooseParameter(Player player, Scanner sc, int actionsLeft) {
        Iterator<Player> it = this.players.iterator();
        String res = "Choose a player to exchange your cards with : \n";
        int i = 1;
        while (it.hasNext()) {
            Player p = it.next();
            res += i + " -> " + p.getName();
            if (it.hasNext()) {
                res += " / ";
            }
            i++;
        }
        System.out.println(res);

        int n = -1;
        String act;
        while (n == -1) {
            act = sc.next();
            try {
                n = Integer.parseInt(act);
                if (n < 1 || n > this.players.size()) {
                    n = -1;
                    System.out.println("Enter an integer between 1 and " + this.players.size());
                }
            } catch (NumberFormatException e) {
                System.out.println("Not an integer, retry");
            }
        }
        n--;
        this.exchangePlayer = this.players.get(n);
        System.out.println("You chose to exchange a card with the player " + this.exchangePlayer.getName() + "\n\n");

        res = "";
        Iterator<Card> itC = this.exchangePlayer.getCards().iterator();
        while (itC.hasNext()) {
            Card c = itC.next();
            res += i + " -> " + c.getTownName() + " : " + c.getDiseaseName();
            if (itC.hasNext()) {
                res += " / ";
            }
            i++;
        }
        System.out.println(res);

        n = -1;
        while (n == -1) {
            act = sc.next();
            try {
                n = Integer.parseInt(act);
                if (n < 1 || n > this.exchangePlayer.getCards().size()) {
                    n = -1;
                    System.out.println("Enter an integer between 1 and " + this.exchangePlayer.getCards().size());
                }
            } catch (NumberFormatException e) {
                System.out.println("Not an integer, retry");
            }
        }

        n--;
        Card exchangedCard = this.exchangePlayer.getCards().get(n);
        System.out.println("You chose to exchange the card " + exchangedCard.getDiseaseName() + " : " + exchangedCard.getTownName() + "\n\n");

        System.out.println("Now pick your card you want to exchange :\n");
        res = "";
        itC = player.getCards().iterator();
        while (itC.hasNext()) {
            Card c = itC.next();
            res += i + " -> " + c.getTownName() + " : " + c.getDiseaseName();
            if (itC.hasNext()) {
                res += " / ";
            }
            i++;
        }
        System.out.println(res);

        n = -1;
        while (n == -1) {
            act = sc.next();
            try {
                n = Integer.parseInt(act);
                if (n < 1 || n > this.exchangePlayer.getCards().size()) {
                    n = -1;
                    System.out.println("Enter an integer between 1 and " + this.exchangePlayer.getCards().size());
                }
            } catch (NumberFormatException e) {
                System.out.println("Not an integer, retry");
            }
        }
        n--;
        this.card = player.getCards().get(n);
        int k = this.run(player, exchangedCard);
        return k;
    }

    /**
     * Makes the player choose an action randomly
     * 
     * @param player The player playing the action
     * @param actionsLeft The number of actions the player has left
     * @return The cost of the action 
     */
    public int chooseRandomParameter(Player player, int actionsLeft) {
        return 0; // This action is an exemple of how we can add extensions for the game, so we're not gonna do the random method.
    }
}
