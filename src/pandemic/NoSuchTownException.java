package pandemic;

/** An exception throws when a specified town doesn't exist */
public class NoSuchTownException extends Exception {
	
	/** Builds an NoSuchTownException exception
	 * 
	 * @param s The exception's message
	 */
    public NoSuchTownException(String s) {
        super(s);
    }
}
