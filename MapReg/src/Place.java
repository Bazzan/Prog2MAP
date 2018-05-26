
import javax.swing.*;
import java.awt.*;

public class Place extends JComponent {

	/**
	 * 	  
	 *  Sebastian Åkerlund - 1995-10-01
	  * seae5393
	  * sebake01@gmail.com

	 */
	private static final long serialVersionUID = 1L;
	private String name;
	private Category cg;
	private Position cordinates;
	private boolean marked = false;

	public Place(String name, Position mp, Category cg) {
		this.name = name;
		this.cordinates = mp;
		this.cg = cg;

		int x1 = mp.getX() - 15;
		int y1 = mp.getY() - (15 * 2);

		setBounds(x1, y1, 15 * 2, 15 * 2);
	}

	public String getName() {
		return name;
	}

	public Category getCategory() {
		return cg;
	}

	public Position getPosition() {
		return cordinates;
	}

	public String getCordinates() {
		return cordinates.getMapPosition();
	}

	public boolean getIsMakred() {
		return marked;

	}

	public void setIsMarked(boolean mark) {
		marked = mark;
		repaint();
	}

	protected void paintComponent(Graphics g) {
		int[] xs = new int[] { 0, 15, 15 * 2 };
		int[] ys = new int[] { 0, 15 * 2, 0 };

		Color c = getCategory().getColor();

		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		if (marked) {
			g.setColor(c);
			g.fillPolygon(xs, ys, 3);
			g.setColor(Color.WHITE);
			g2d.setStroke(new BasicStroke(3));
			g.drawPolygon(xs, ys, 3);
		} else {
			g.setColor(c);
			g.fillPolygon(xs, ys, 3);
			g.drawPolygon(xs, ys, 3);
		}

	}

}
