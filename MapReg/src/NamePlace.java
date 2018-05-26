
public class NamePlace extends Place {

	/**
	  *  Sebastian Åkerlund - 1995-10-01
	  * seae5393
	  * sebake01@gmail.com
	 */
	private static final long serialVersionUID = 1L;

	public NamePlace(String name, Position mp, Category cg) {
		super(name, mp, cg);
	}

	public String toString() {
		return "Named," + getCategory() + "," + getCordinates() + "," + getName();

	}
}