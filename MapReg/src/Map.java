import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.*;
import jav

public class Map extends JFrame {
	
	ImagePanel ip = null;
	JScrollPane scroll = null;
	JFileChooser jfc = new JFileChooser(".");
	
	
	public Map() {
		super("Map");
				
		
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
		JPanel up = new JPanel();
		//up.setLayout(new BoxLayout(up, BoxLayout.X_AXIS));
		add(up, BorderLayout.NORTH);
		
		JButton newButton = new JButton("New");
		up.add(newButton);
		//newButton.addActionListener(new newButton());
		
		Box rbVertical = Box.createVerticalBox();
		JRadioButton named = new JRadioButton("Named");
		JRadioButton described = new JRadioButton("Described");
		rbVertical.add(named);
		rbVertical.add(described);
		up.add(rbVertical);
		
		JTextField searchField = new JTextField("Search", 10);
		up.add(searchField);
		
		JButton searchButton = new JButton("Search");
		up.add(searchButton);
		
		JButton hideButton = new JButton("Hide");
		up.add(hideButton);
		
		JButton removeButton = new JButton("Remove");
		up.add(removeButton);
		
		JButton cordButton = new JButton("Coordinates");
		up.add(cordButton);
				// end Upper buttons
		
		FileFilter ff = new FileNameExtensionFilter("Image", "jpg", "gif", "png");		
		jfc.setFileFilter(ff);
		
		
		ip.mouseDown(arg0, arg1, arg2)
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(1200, 1000);
		setLocationRelativeTo(null);
		setVisible(true);
		
	}
	class MouseLiss extends MouseAdapter
	
	
	class NewMapLiss implements ActionListener{
		public void actionPerformed(ActionEvent ave) {
			int answer = jfc.showOpenDialog(Map.this);
			
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
			
			validate();
			repaint();
			
		}
		
	}
	
	
	class NewButtonLiss implements ActionListener{
		public void actionPerformed(ActionEvent ave) {
			
		}
		
	}
	
	
	public static void main(String [] args) {
		new Map();
	}
	
	
}
