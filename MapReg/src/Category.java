
import java.awt.*;

public enum Category {

	Bus, Train, Underground, None;

	public Color getColor() {
		switch (this) {
		case Train:
			return Color.GREEN;
		case Bus:
			return Color.RED;
		case Underground:
			return Color.BLUE;
		default:
			return Color.BLACK;
		}

	}

	public static Category parseCategory(String str) {
		switch (str) {
		case "Train":
			return Category.Train;
		case "Bus":
			System.out.print("parse cg");
			return Category.Bus;
		case "Underground":
			return Category.Underground;
		default:
			return Category.None;
		}
	}

}
