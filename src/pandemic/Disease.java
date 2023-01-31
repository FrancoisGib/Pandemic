/**
 * 
 */
package pandemic;

/**
 * a class that represents a disease that's in the game
 */
public class Disease {
	/*the name of the disease*/
	private String name;
	/*builds a disease with a given name*/
	public Disease(String name) {
		this.name = name;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/*change the infection state*/
	public void updateDisease() {
	}
}
