package pandemic;

public class NoSuchDiseaseException extends Exception {
    /** Builds an NoDiseaseInThisTownException exception
	 * 
	 * @param s The exception's message
	 */
    public NoSuchDiseaseException(String s) {
        super(s);
    }
}
