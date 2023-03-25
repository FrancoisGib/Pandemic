package pandemic;

public class Disease {
    private String name;

    private boolean cured;

    private int sector;

    public Disease(String name, int sector) {
        this.name = name;
        this.cured = false;
        this.sector = sector;
    }

    public String getName() {
        return this.name;
    }

    public boolean cure() {
        if (!this.cured) {
            this.cured = true;
            return true;
        }
        return false;
    }

    public int getSector() {
        return this.sector;
    }
}
