

public class DescribedPlace extends Place {
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