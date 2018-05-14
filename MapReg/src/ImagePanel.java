
 import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.LinkedList;
import java.util.List;

public class ImagePanel extends JPanel {

	private ImageIcon image;
	private List<Place> placesMarked = new LinkedList<>();
	
	ImagePanel(String fileName){
		image = new ImageIcon(fileName);
		int w = image.getIconWidth();
		int h = image.getIconHeight();
		
		setLayout(null);
		
		setPreferredSize(new Dimension(w,h));
		setMaximumSize(new Dimension (w,h));
		setMinimumSize(new Dimension(w,h));

		
	}
	
	public void addTriangle(Place place) {
		Position position = place.getPosition();
		place.addMouseListener(new AddTriangleLiss());
		add(place);
		validate();
		repaint();
		System.out.println("REPAINT" + position.toString());
	}
	
	class AddTriangleLiss extends MouseAdapter{
//		@Override
		public void mouseClicked(MouseEvent mev) {

			Place place = (Place)mev.getSource();
			boolean marked = place.getIsMakred();
			
			if(SwingUtilities.isRightMouseButton(mev)) {
				if(place instanceof NamePlace) {
					String message = "'" + place.getCordinates() + "' " + place.getName(); 
					JOptionPane.showMessageDialog(null,  message, "Place Information!", JOptionPane.OK_OPTION);
					
				}else if(place instanceof DescribedPlace) {
					String message = "'" + place.getCordinates() + "' " + place.getName() + ((DescribedPlace) place).getDescription(); 
					JOptionPane.showMessageDialog(null,  message, "Place Information!", JOptionPane.OK_OPTION);
					
				}
			}else if(SwingUtilities.isLeftMouseButton(mev)) {
				if(marked == false) {
					placesMarked.add(place);
					System.out.println(marked);
					
				}else {
					placesMarked.remove(place);
					System.out.println(marked);
				}
				place.setIsMarked(!marked);
				System.out.println("triLiss");
			}
		}
	}
	
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(image.getImage(),0,0,this);
		
	}
	
//	public void paintTriangle(Location loc) {
//		MapPosition location = loc.getMapPosition();
//		loc.addMouseListener(new );
//		
//		
//	}
}
