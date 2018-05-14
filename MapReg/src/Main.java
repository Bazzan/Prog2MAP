
import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;
import java.util.List;
import javax.swing.filechooser.FileFilter;

import javax.swing.filechooser.*;

public class Main extends JFrame {
	private Place place;

	private Category[] cg = { Category.Train, Category.Bus, Category.Underground };
	private JList<Category> cgList;

	private Map<Category, Set<Place>> cgPlace = new HashMap<>();

	private Map<String, List<Place>> placeByName = new HashMap<>();

	private Map<Position, Place> placeByMapPosition = new HashMap<>();

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
	// right buttons and stuff
	JButton hideCG = new JButton("Hide");
	JScrollPane cgScroll = new JScrollPane(cgList);

	private boolean mapDisplaying = false;
	private boolean changed = false;

	public Main() {
		super("Main");

		JMenuBar mbar = new JMenuBar();
		setJMenuBar(mbar);

		// Archive
		JMenu archiveMenu = new JMenu("Archive");
		mbar.add(archiveMenu);

		JMenuItem newMap = new JMenuItem("New Map");
		archiveMenu.add(newMap);
		newMap.addActionListener(new NewMapLiss());
		JMenuItem loadPlaces = new JMenu("Load Places");
		archiveMenu.add(loadPlaces);
		// loadPlaces.addActionListener(new LoadPlacesLiss);
		JMenuItem save = new JMenu("Save");
		archiveMenu.add(save);
		// save.addActionListener(new SaveLiss);
		JMenuItem exit = new JMenu("Exit");
		archiveMenu.add(exit);
		// exit.addActionListener(new ExitLiss);
		// Archive end

		// Upper buttons
		JPanel upP = new JPanel();
		// up.setLayout(new BoxLayout(up, BoxLayout.X_AXIS));
		add(upP, BorderLayout.NORTH);

		JButton newButton = new JButton("New");
		upP.add(newButton);
		newButton.addActionListener(new NewButtonLiss());

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
		cgList.addMouseListener(new CategoryLiss());
		rightP.add(cgScroll);
		rightP.add(hideCG);
		// hideCG.addActionListener(new HideCGLiss());

		FileFilter ff = new FileNameExtensionFilter("Image", "jpg", "gif", "png");
		jfc.setFileFilter(ff);

		// ip.mouseDown(arg0, arg1, arg2)

		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(1200, 1000);
		setLocationRelativeTo(null);
		setVisible(true);

	}

	class CategoryLiss extends MouseAdapter {
		public void mouseClicked(MouseEvent e) {
			Category cg = cgList.getSelectedValue();
			Set<Place> places = cgPlace.get(cg);
			// ip.showTriangle(places);
		}
	}

	class NewButtonLiss implements ActionListener {
		public void actionPerformed(ActionEvent ave) {
			System.out.println("newbuttonLiss");
			if (mapDisplaying) {
				Cursor mouseC = new Cursor(Cursor.CROSSHAIR_CURSOR);
				ip.setCursor(mouseC);

				ip.addMouseListener(new MapLiss());
			} else {
				JOptionPane.showMessageDialog(null, "Error", "No map has been loaded", JOptionPane.ERROR_MESSAGE);
				bg.clearSelection();
				cgList.clearSelection();
				return;
			}

		}

		class MapLiss extends MouseAdapter {
			@Override
			public void mouseClicked(MouseEvent e) {
				System.out.println("MapLiss");
				ip.setCursor(Cursor.getDefaultCursor());
				ip.removeMouseListener(this);

				System.out.println("cursor");

				int x = e.getX();
				int y = e.getY();
				Position mp = new Position(x, y);

				// TODO: check om det redan fins en place på positioen

				Category cg = cgList.getSelectedValue();
				if (cg == null) {
					cg = Category.None;
				}
				NamePlace namePlace = null;
				DescribedPlace describedPlace = null;

				if (named.isSelected()) {
					NamedFormular nf = new NamedFormular();
					while (true) {
						int confInt = JOptionPane.showConfirmDialog(null, nf, "New named",
								JOptionPane.OK_CANCEL_OPTION);
						if (confInt == 2 || confInt == -1) {
							System.out.println(confInt);
							break;
						}
						if (nf.getName() == null || nf.getName().equals("")) {
							JOptionPane.showMessageDialog(null, "Add a name", "Wrong", JOptionPane.ERROR_MESSAGE);
							continue;
						}

						String name = nf.getName();
						namePlace = new NamePlace(name, mp, cg);
						addPlace(namePlace);
						System.out.println(namePlace + " added");

						break;
					}

				} else if (described.isSelected()) {
					DescribedFormular df = new DescribedFormular();
					while (true) {
						int confInt = JOptionPane.showConfirmDialog(null, df, "New descibed place",
								JOptionPane.OK_CANCEL_OPTION);
						if (confInt == 2 || confInt == -1) {
							System.out.println(confInt);
							break;
						}
						if (df.getName() == null || df.getName().equals("")) {
							JOptionPane.showMessageDialog(null, "ERROR", "Add a name", JOptionPane.OK_CANCEL_OPTION);
							continue;
						} else if (df.getDescription() == null || df.getDescription().equals("")) {
							JOptionPane.showMessageDialog(null, "ERROR", "Add description", JOptionPane.ERROR_MESSAGE);
							continue;

						}

						String name = df.getName();
						String description = df.getDescription();

						describedPlace = new DescribedPlace(description, name, mp, cg);
						addPlace(describedPlace);
						System.out.println(describedPlace + " added");
						break;
					}
				} else {
					JOptionPane.showMessageDialog(null, "ERROR", "Choose the of place", JOptionPane.ERROR_MESSAGE);

				}
				bg.clearSelection();
				cgList.clearSelection();

			}

		}

	}

	// Public Place testPosition(Position tPos) {
	// System.out.println("tPos" + tPos.getMapPosition());
	// return placeByPosition.get(tPos);
	// }
	class NewMapLiss implements ActionListener {
		public void actionPerformed(ActionEvent ave) {
			int answer = jfc.showOpenDialog(Main.this);

			if (answer != JFileChooser.APPROVE_OPTION) {
				return;
			}

			File file = jfc.getSelectedFile();
			String fileName = file.getAbsolutePath();

			if (scroll != null) {
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

	private void addPlace(Place p) {
		placeByMapPosition.put(p.getPosition(), p);

		String name = p.getName();
		List<Place> sameName = placeByName.get(name);

		ip.addTriangle(p);

		if (sameName == null) {
			sameName = new LinkedList<Place>();
			placeByName.put(name, sameName);
		}
		sameName.add(p);

//		cgPlace.get(p.getCategory()).add(p);
		changed = true;

	}

	public static void main(String[] args) {
		new Main();
	}

}
