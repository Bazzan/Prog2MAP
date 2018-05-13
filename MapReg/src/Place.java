import javax.swing.*;
import java.awt.*;

public class Place extends JComponent {
	
	private String name;
	private Category cg;
	private Position cordinates;
	
	private JTextField nameField = new JTextField(15);
	private JTextField descriptionField = new JTextField(); 
	
	public Place(String name, Position mp, Category cg) {
		this.name = name;
		this.cordinates = mp;
		this.cg = cg;
		
		
		int x1 = mp.getX();
		int y1 = mp.getY();
	}
	
//	public String getName() {
//		return name;
//	}
	
	public Category getCategory() {
		return cg;
	}
		
	public Position getPosition() {
		return cordinates;
	}
	
	public String getCordinates() {
		return cordinates.getMapPosition();
	}
	
	protected void paintComponent(Graphics g) {
		int[] xs = new int[] { 0, 15, 15 *2};
		int[] ys = new int[] { 0, 15 * 2, 0};
		
		Color c = getCategory().getColor();
		
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		g.setColor(c);
		g.fillPolygon(xs, ys, 3);
		g.drawPolygon(xs, ys, 3);
		
	
	}
	

	class NamePlace extends Place{

		public NamePlace(String name, Position mp, Category cg) {
			super(name, mp, cg);
		}
		
		public String toString() {
			return "Named place: " + getCategory() + ", " + getCordinates() +", " + getName();
					 
		}
	}
	
//	public class NamedFormular extends JPanel{
//
//		
//		public NamedFormular() {
//			setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
//			JPanel line1 = new JPanel();
//			line1.add(new JLabel("Name of place:"));
//			line1.add(nameField);
//			add(line1);
//		}
//		public String getName() {
//			return nameField.getText();
//		}
//	}
	
	class DescribedPlace extends Place{
		private String description;
		
		public DescribedPlace(String description, String name, Position mp, Category cg) {
			super(name, mp, cg);
			this.description = description;

		}
		
		public String getDescription() {
			return description;
		}
		public String toString() {
			return "Described place: " + getCategory() + ", " + getCordinates() +", " + getName() + ", " + description;
		}
		
	}
	
//	public class DescribedFormular extends JPanel{
//
//		public DescribedFormular() {
//			
//			setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
//			JPanel line1 = new JPanel();
//			line1.add(new JLabel("Name:"));
//			line1.add(nameField);
//			add(line1);
//			
//			JPanel line2 = new JPanel();
//			line2.add(new JLabel("Description:"));
//			line2.add(descriptionField);
//			add(line2);			
//			
//			
//		}
//		
//		
//	}
}

