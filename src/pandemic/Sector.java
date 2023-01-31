package pandemic;
/**
 *A class to represent the sectors of town of this PandemicGame
 */
public class Sector {
	/*the disease that's in the sector*/
	private int disease;
	/*builds a sector with an initial disease*/
	public Sector(int disease) {
		this.disease = disease;
	}
	/**get the disease of the sector
	 * 
	 * @return the disease
	 */
	public int getDisease() {
		return this.disease;
	}
}
