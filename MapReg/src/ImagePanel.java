
 import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class ImagePanel extends JPanel {

	private ImageIcon image;
	private List<Place> placesMarked = new LinkedList<>();
	
	ImagePanel(String path){
		image = new ImageIcon(path);
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
		System.out.println("add tri REPAINT");
	}
	
	class AddTriangleLiss extends MouseAdapter{
		@Override
		public void mouseClicked(MouseEvent mev) {

			Place place = (Place)mev.getSource();
			boolean marked = place.getIsMakred();
			
			if(SwingUtilities.isRightMouseButton(mev)) {
				if(place instanceof NamePlace) {
					String message = "'" + place.getCordinates() + "' " + place.getName(); 
					JOptionPane.showMessageDialog(null,  message, "Place Information!", JOptionPane.OK_OPTION);
					
				}else if(place instanceof DescribedPlace) {
					String message =  place.getName() + ": " + ((DescribedPlace) place).getDescription() + "'" + place.getCordinates()+ "'"; 
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

	
	
	public void markIt(Place place) {
		place.setIsMarked(true);
		placesMarked.add(place);
		this.repaint();
		System.out.println("MarkIt");
		
	}
	
	public void unMark() {
		for (Place place : placesMarked) {
			place.setIsMarked(false);
		}
		placesMarked.clear();
		repaint();
	}
	
	public void triangelHide() {
		for(Place place : placesMarked) {
			place.setVisible(false);
			
		}
		System.out.println("trihide");
	}
	
	public void cgHide(Set<Place> places) {
		this.unMark();
		for(Place place: places) {
			this.markIt(place);
		}
		System.out.println("cghide");

		this.triangelHide();
		this.repaint();
	}
	
	public void triangleShow(Set<Place> places) {
		System.out.println(places);
		for (Place place : places) {
			place.setVisible(true);
		}
		this.unMark();
		this.repaint();
		
	}
	
	public List<Place> removeMarked() {
		for(Place place : placesMarked) {
			this.remove(place);
		}
		List<Place> removeMarkedPlaces = new LinkedList(placesMarked);
		placesMarked.clear();
		this.repaint();
		return removeMarkedPlaces;
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
