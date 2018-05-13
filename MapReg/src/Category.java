import java.awt.*;



public enum Category {

	Bus, Train, Underground, None;
	
	public Color getColor() {
		switch(this) {
			case Train:
				return Color.MAGENTA;
			case Bus:
				return Color.RED;
			case Underground:
				return Color.LIGHT_GRAY;
			default:
				return Color.WHITE;
		}
		
	}

	public static Category parseCategory(String str) {
		switch(str){
		case "Train":
			return Category.Train;
		case "Bus":
			return Category.Bus;
		case "Underground":
			return Category.Train;
		default:
			return Category.None;
		}
	}
	
}
