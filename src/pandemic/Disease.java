package pandemic;

/* The class that represents diseases in the game */
public class Disease {
    public static final int INITIAL_CUBES_NUMBER = 24;


    /* The name of the disease */
    private String name;

    /* The status of the disease, true if the disease is cured, else false */
    private boolean cured;

    /* The initial sector of the disease */
    private int sector;

    /* The number of cubes left for the disease */
    private int cubes;

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
        this.cubes = INITIAL_CUBES_NUMBER;
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
            System.out.println("\nDisease " + this.name + " cured\n");
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

    /**
     * Give the number of cubes left for the disease
     * 
     * @return The number of cubes left for the disease
     */
    public int getCube() {
        return this.cubes;
    }

    public boolean placeCube() {
        this.cubes--;
        if (this.cubes == 0) {
            return false;
        }
        return true;
    }

    public void removeCube() {
        if (this.cubes != INITIAL_CUBES_NUMBER) {
            this.cubes++;
        }
    }
}
