
public class DescribedPlace extends Place {
	/**
	 * 	 * Sebastian Åkerlund - 1995-10-01
	 * seae5393
	 * sebake01@gmail.com
	 */
	private static final long serialVersionUID = 1L;
	private String description;

	public DescribedPlace(String description, String name, Position mp, Category cg) {
		super(name, mp, cg);
		this.description = description;

	}

	public String getDescription() {
		return description;
	}

	public String toString() {
		return "Described," + getCategory() + "," + getCordinates() + "," + getName() + "," + description;
	}

}