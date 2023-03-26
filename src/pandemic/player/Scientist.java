package pandemic.player;

public class Scientist extends Player {
    public Scientist(String name) {
        super(name);
    }

    public boolean discoverCure() {
		if (this.town.hasResearchCenter() && this.getTownCardsNumber(this.town) > 3) {
			return true;
		}
		return false;
	}
}
