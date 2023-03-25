package pandemic.cards;

public class NoCardsRemainingInTheStack extends Exception {
    /** Builds an NoCardsRemainingInTheStack exception
	 * 
	 * @param s The exception's message
	 */
    public NoCardsRemainingInTheStack(String s) {
        super(s);
    }
}
