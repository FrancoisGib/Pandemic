package pandemic;

import java.util.List;

/* The class that defines a player in the game */
public abstract class Player {

	/* The Player's name */
	protected String name;
	
	/* The town where the Player is */
	protected Town town;
	
	/* The list of cards the Player has */
	protected List<PlayerCardsStack> cards;
	
	
	
}
