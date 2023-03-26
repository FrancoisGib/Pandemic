package pandemic.player;

/* The class that defines a Expert in the game, it is inherited of the Player class */
public class Expert extends Player {

	/**
	 * Builds an Expert for the game
	 *
	 * @param name The name of the expert
	 */
    public Expert(String name) {
        super(name);
    }

	/**
	 * Builds a research center in the expert's town, no matter if he has cards of the town or not (unlike the other players)
	 */
    public boolean buildResearchCenter() {
		if (!this.town.hasResearchCenter()) {
			this.town.buildResearchCenter();
			return true;
		}
		return false;
	}
    
}
