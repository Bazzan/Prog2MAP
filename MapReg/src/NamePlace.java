
public class NamePlace extends Place {

	public NamePlace(String name, Position mp, Category cg) {
		super(name, mp, cg);
	}

	public String toString() {
		return "Named place: " + getName() + ", " + getCategory() + ", " + getCordinates() + "," ;

	}
}