package pandemic;

/* The class that represents diseases in the game */
public class Disease {

    /* The name of the disease */
    private String name;

    /* The status of the disease, true if the disease is cured, else false */
    private boolean cured;

    /* The initial sector of the disease */
    private int sector;

    /**
     * Builds a disease for the game
     * 
     * @param name The name of the disease
     * @param sector The initial sector of the disease
     */
    public Disease(String name, int sector) {
        this.name = name;
        this.cured = false;
        this.sector = sector;
    }

    /**
     * Get the name of the disease
     * 
     * @return The disease's name
     */
    public String getName() {
        return this.name;
    }

    /**
     * Cure the disease
     * 
     * @return True if the disease has been cured, else false
     */
    public boolean cure() {
        if (!this.cured) {
            this.cured = true;
            return true;
        }
        return false;
    }

    /**
     * Get the disease's sector
     * 
     * @return The disease's sector
     */
    public int getSector() {
        return this.sector;
    }

    /**
     * Tell if the disease has been cured or not
     * 
     * @return True if the disease has been cured, else false
     */
    public boolean isCured() {
        return this.cured;
    }
}
