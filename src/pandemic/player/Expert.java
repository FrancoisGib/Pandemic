package pandemic.player;

public class Expert extends Player {
    public Expert(String name) {
        super(name);
    }

    public boolean buildResearchCenter() {
		if (!this.town.hasResearchCenter()) {
			this.town.buildResearchCenter();
			return true;
		}
		return false;
	}
    
}
