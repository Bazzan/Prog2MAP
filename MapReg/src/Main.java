import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.List;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.*;



public class Main extends JFrame {
	private Category[] cg = { Category.Train, Category.Bus, Category.Underground};
	private JList <Category> cgList;
	
	private Map<Category, Set<Place>> categoryLocation = new HashMap<>();
	
	private Map<String, List<Place>> locationbByName = new HashMap<>();
	
	private Map<Position, Place> locationByMapPosition = new HashMap<>();
	
	ImagePanel ip = null;
	JScrollPane scroll = null;
	JFileChooser jfc = new JFileChooser(".");
	
	ButtonGroup bg = new ButtonGroup();
	JRadioButton named = new JRadioButton("Named");
	JRadioButton described = new JRadioButton("Described");

	// upper buttons
	JButton cordButton = new JButton("Coordinates");
	JButton removeButton = new JButton("Remove");
	JButton hideButton = new JButton("Hide");
	JButton searchButton = new JButton("Search");
	JTextField searchField = new JTextField("Search", 10);
	//right buttons and stuff
	JButton hideCG = new JButton("Hide");
	JScrollPane cgScroll = new JScrollPane(cgList);

	
	private boolean mapDisplaying = false;
	
	public Main() {
		super("Main");
				
		
		JMenuBar mbar = new JMenuBar();
		setJMenuBar(mbar);
		
				//Archive		
		JMenu archiveMenu = new JMenu ("Archive");
		mbar.add(archiveMenu);		
		
		JMenuItem newMap = new JMenuItem("New Map");
		archiveMenu.add(newMap);
		newMap.addActionListener(new NewMapLiss());		
		JMenuItem loadPlaces = new JMenu ("Load Places");
		archiveMenu.add(loadPlaces);
		//loadPlaces.addActionListener(new LoadPlacesLiss);		
		JMenuItem save = new JMenu ("Save");
		archiveMenu.add(save);
		//save.addActionListener(new SaveLiss);
		JMenuItem exit = new JMenu ("Exit");
		archiveMenu.add(exit);
		//exit.addActionListener(new ExitLiss);
				//Archive end
		
				// Upper buttons
		JPanel upP = new JPanel();
		//up.setLayout(new BoxLayout(up, BoxLayout.X_AXIS));
		add(upP, BorderLayout.NORTH);
		
		JButton newButton = new JButton("New");
		upP.add(newButton);
		//newButton.addActionListener(new newButton());
		


		
				// radio buttons
		bg.add(named);
		bg.add(described);

		Box rbVertical = Box.createVerticalBox();
		rbVertical.add(named);
		rbVertical.add(described);
		upP.add(rbVertical);
		
		
				// upper buttons

		upP.add(searchField);

		upP.add(searchButton);

		upP.add(hideButton);

		upP.add(removeButton);

		upP.add(cordButton);
				// end Upper buttons
				
				// right Panel
		JPanel rightP = new JPanel();
		rightP.setLayout(new BoxLayout(rightP, BoxLayout.Y_AXIS));
		rightP.setBorder(new EmptyBorder(4, 4, 4, 4));
		add(rightP, BorderLayout.EAST);
		
				// right buttons
		cgList = new JList<>(cg);
		rightP.add(cgList);
//		cgList.addMouseListener(new CategoryLiss());		
		rightP.add(cgScroll);
		rightP.add(hideCG);
//		hideCG.addActionListener(new HideCGLiss());
		
		
		FileFilter ff = new FileNameExtensionFilter("Image", "jpg", "gif", "png");		
		jfc.setFileFilter(ff);
		
		
//		ip.mouseDown(arg0, arg1, arg2)
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(1200, 1000);
		setLocationRelativeTo(null);
		setVisible(true);
		
	}
	
	class MapLiss extends MouseAdapter {
		@Override
		public void mouseClicked(MouseEvent e) {
			ip.setCursor(Cursor.getDefaultCursor());
			ip.removeMouseListener(this);
			
			int x = e.getX();
			int y = e.getY();
			Position pos = new Position(x, y);
			
			//TODO: check om det redan fins en place på positioen
			
			Category cg = cgList.getSelectedValue();
			if (cg == null) {
				cg = Category.None;
			}
			
			if (named.isSelected()) {
				NamedFormular nf = new NamedFormular();
				
			}
		}
		
	}
	
	class NewButtonLiss implements ActionListener{
		public void actionPerformed(ActionEvent ave) {
			
			if (mapDisplaying) {
				Cursor mouseC = new Cursor(Cursor.CROSSHAIR_CURSOR);
				ip.setCursor(mouseC);
				
				ip.addMouseListener(new MapLiss());
			}else {
				JOptionPane.showMessageDialog(null,"Error" ,"No map has been loaded", JOptionPane.ERROR_MESSAGE);
				bg.clearSelection();
				cgList.clearSelection();
				return;
			}
			
			
		}
		
	}
	
	class NewMapLiss implements ActionListener{
		public void actionPerformed(ActionEvent ave) {
			int answer = jfc.showOpenDialog(Main.this);
			
			if (answer != JFileChooser.APPROVE_OPTION){
				return;
			}
			
			File file = jfc.getSelectedFile();
			String fileName = file.getAbsolutePath();
			
			if(scroll != null) {
				remove(scroll);
			}
			ip = new ImagePanel(fileName);
			scroll = new JScrollPane(ip);
			add(scroll, BorderLayout.CENTER);
			
			mapDisplaying = true; // bool för att kolla om kartan är laddad
			
			validate();
			repaint();
			
		}
		
	}
	
	

	
	
	public static void main(String [] args) {
		new Main();
	}
	
	
}
