

public class Position {

	private int x;
	private int y;
	
	public Position(int x, int y){
		this.x = x;
		this.y = y;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
		
	}
	
	public String getMapPosition() {
		return x + "," + y;
	}

	@Override
	public int hashCode() {
		return x*10000 + y;
	}

	public boolean equals(Object mp) {
		if (mp instanceof Position) {
			if(x == ((Position) mp).getX() && y == ((Position) mp).getY()) {
				return true;
			}
		}
		return false;
	}
}
