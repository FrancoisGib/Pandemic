package pandemic;

/** An exception throws when a specified town doesn't exist */
public class NoSuchTownException extends Exception {
	
	/** Builds an NoSuchTownException exception
	 * 
<<<<<<< HEAD
	 * @param s The exception's message
=======
	 * @param s, the exception's message
>>>>>>> 352eef3fbdfecc1c15e20a751005f81ddf898923
	 */
    public NoSuchTownException(String s) {
        super(s);
    }
}
