package pandemic;
/**
 * a class that defines the initial numbers in the game
 *
 */
public class Game {
	/*the global infection state of game*/
	private static final int GLOBAL_INFECTION = 2;
	/* the number of clusters in the game (0 at start)*/
	private static final int CLUSTER = 2;
	/*builds a game with initial clusters / infectionstate*/
	public Game() {
	
	}
	/** Get global infection state of the game
	 * 
	 * @return the global infection state
	 */
	
	public static int getGlobalInfection() {
		return GLOBAL_INFECTION;
	}
	/** Get the total clusters of the game
	 * 
	 * @return the clusters
	 */
	public static int getCluster() {
		return CLUSTER;
	}
	
	public void run(){
		
	}
}
	
