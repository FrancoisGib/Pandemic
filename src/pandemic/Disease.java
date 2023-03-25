package pandemic;

public class Disease {
    private String name;

    private boolean cured;

    public Disease(String name) {
        this.name = name;
        this.cured = false;
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
}
