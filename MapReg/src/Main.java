
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.List;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.*;

public class Main extends JFrame {
	// TODO: tar bort de gamla inladdade platserna och dialogfönster på det.

	/**
	 * 	 * Sebastian �kerlund - 1995-10-01
	 * seae5393
	 * sebake01@gmail.com
	 */
	private static final long serialVersionUID = 1L;
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

	JPanel centerP = new JPanel();

	private boolean mapDisplaying = false;
	private boolean changed = false;
	private boolean placesLoaded = false;

	public Main() {
		super("Main");

		cgPlace.put(Category.Bus, new HashSet<>());
		cgPlace.put(Category.Underground, new HashSet<>());
		cgPlace.put(Category.Train, new HashSet<>());
		cgPlace.put(Category.None, new HashSet<>());

		JMenuBar mbar = new JMenuBar();
		setJMenuBar(mbar);

		// Archive
		JMenu archiveMenu = new JMenu("Archive");
		mbar.add(archiveMenu);

		JMenuItem newMap = new JMenuItem("New Map");
		archiveMenu.add(newMap);
		newMap.addActionListener(new NewMapLiss());

		JMenuItem loadPlaces = new JMenuItem("Load Places");
		archiveMenu.add(loadPlaces);
		loadPlaces.addActionListener(new LoadPlacesLiss());

		JMenuItem save = new JMenuItem("Save");
		archiveMenu.add(save);
		save.addActionListener(new SaveLiss());
		JMenuItem exit = new JMenuItem("Exit");
		archiveMenu.add(exit);
		exit.addActionListener(new ExitLiss());
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
		searchButton.addActionListener(new SearchButtonLiss());

		upP.add(hideButton);
		hideButton.addActionListener(new HideButtonLiss());

		upP.add(removeButton);
		removeButton.addActionListener(new RemoveButtonLiss());

		upP.add(cordButton);
		cordButton.addActionListener(new CoordinateLiss());
		// end Upper buttons

		centerP.setLayout(new BorderLayout());
		centerP.setBorder(new EmptyBorder(4, 4, 4, 4));

		add(centerP, BorderLayout.CENTER);
		centerP.addMouseListener(new ClickedLiss());

		// right Panel
		JPanel rightP = new JPanel();
		rightP.setLayout(new BoxLayout(rightP, BoxLayout.Y_AXIS));
		rightP.setBorder(new EmptyBorder(4, 4, 4, 4));
		add(rightP, BorderLayout.EAST);

		JLabel cgLabel = new JLabel("Categories");
		rightP.add(cgLabel);

		// right buttons
		cgList = new JList<>(cg);

		rightP.add(cgList);
		cgList.addMouseListener(new CategoryLiss());

		JScrollPane cgScroll = new JScrollPane(cgList);
		rightP.add(cgScroll);

		rightP.add(hideCG);
		hideCG.addActionListener(new HideCGLiss());

		addWindowListener(new ExitLiss());
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		setSize(700, 600);
		setLocationRelativeTo(null);
		setVisible(true);

	}

	class ExitLiss extends WindowAdapter implements ActionListener {
		private void close() {
			if (changed) {
				int answer = JOptionPane.showConfirmDialog(Main.this,
						"You might have unsaved changes, do you want to continue?", "Warning",
						JOptionPane.OK_CANCEL_OPTION);
				if (answer == JOptionPane.OK_OPTION) {
					System.exit(0);
				}
			} else
				System.exit(0);
		}

		@Override
		public void windowClosing(WindowEvent wev) {
			close();

		}

		public void actionPerformed(ActionEvent ave) {
			close();
		}
	}

	class RemoveButtonLiss implements ActionListener {
		public void actionPerformed(ActionEvent ave) {
			List<Place> placesRemoved = ip.removeMarked();
			for (Place place : placesRemoved) {
				removePlace(place);
			}
		}
	}

	private void removePlace(Place place) {
		placeByMapPosition.remove(place.getPosition(), place);
		String name = place.getName();
		List<Place> sameName = placeByName.get(name);
		sameName.remove(place);

		if (sameName.isEmpty()) {
			placeByName.remove(name);

		}
		cgPlace.get(place.getCategory()).remove(place);
		changed = true;

	}

	class CoordinateLiss implements ActionListener {
		public void actionPerformed(ActionEvent ave) {
			CordFormular cf = new CordFormular();
			System.out.println("12");
			while (true) {
				int answer = JOptionPane.showConfirmDialog(null, cf, "Coordinates", JOptionPane.OK_CANCEL_OPTION);
				if (answer == 2 || answer == -1) {
					break;
				}
				try {
					System.out.println("1");
					if (cf.getXText() == null || cf.getXText().equals("")) {
						JOptionPane.showMessageDialog(null, "Insert X-coordinates", "ERROR", JOptionPane.ERROR_MESSAGE);
					} else if (cf.getYText() == null || cf.getYText().equals("")) {
						JOptionPane.showMessageDialog(null, "Insert Y-coordinates", "ERROR", JOptionPane.ERROR_MESSAGE);
					} else if (cf.getXCoordinate() < 0) {
						JOptionPane.showMessageDialog(null, "Coordinates can't be lower than zero", "ERROR",
								JOptionPane.ERROR_MESSAGE);
						continue;
					} else if (cf.getYCoordinate() < 0) {
						JOptionPane.showMessageDialog(null, "Coordinates can't be lower than zero", "ERROR",
								JOptionPane.ERROR_MESSAGE);
						continue;
					}
					System.out.println("13");

					int xC = cf.getXCoordinate();
					int yC = cf.getYCoordinate();

					Position testPos = new Position(xC, yC);
					Place place = checkPoss(testPos);

					if (place == null) {
						JOptionPane.showMessageDialog(null, "There is not place with those coordinates.", "ERROR",
								JOptionPane.ERROR_MESSAGE);
						break;
					} else {
						ip.unMark();
						place.setVisible(true);
						ip.markIt(place);

						break;
					}
				} catch (NumberFormatException e) {
					JOptionPane.showMessageDialog(null, "Wrong format", "ERROR", JOptionPane.ERROR_MESSAGE);
				}
			}
		}
	}

	public Place checkPoss(Position testPos) {
		System.out.println(testPos);
		return placeByMapPosition.get(testPos);
	}

	class SearchButtonLiss implements ActionListener {
		public void actionPerformed(ActionEvent ave) {
			String searchStr = searchField.getText();

			List<Place> sameName = placeByName.get(searchStr);

			if (sameName == null) {
				JOptionPane.showMessageDialog(null, "No place with that name", "ERROR", JOptionPane.ERROR_MESSAGE);
				searchField.setText("Search");
				return;
			} else {
				ip.unMark();
				for (Place place : sameName) {
					place.setVisible(true);
					ip.markIt(place);
				}
			}
		}
	}

	// TODO: kolla om de ska vara omarkerade n�r man tar fram dom igen
	class ClickedLiss extends MouseAdapter {
		public void mouseClicked(MouseEvent e) {
			System.out.println("clicked");
			ImagePanel imgP = (ImagePanel) e.getSource();
			System.out.print("cleared?");

			if (ip.equals(imgP)) {

				cgList.clearSelection();
			}
			System.out.print("cleared?");
		}
	}

	class HideButtonLiss implements ActionListener {
		public void actionPerformed(ActionEvent ave) {
			cgList.clearSelection();
			ip.triangelHide();
		}
	}

	class CategoryLiss extends MouseAdapter {
		public void mouseClicked(MouseEvent e) {
			Category cg = cgList.getSelectedValue();

			Set<Place> places = cgPlace.get(cg);

			ip.triangleShow(places);
			System.out.println("Cgliss");
		}
	}

	class HideCGLiss implements ActionListener {
		public void actionPerformed(ActionEvent ave) {
			Category cg = cgList.getSelectedValue();
			Set<Place> places = cgPlace.get(cg);

			if (cg == null) {
				places = cgPlace.get(Category.None);
			}
			System.out.println(cg);
			System.out.println(places);

			ip.cgHide(places);
			cgList.clearSelection();

			System.out.println("hidecgliss");

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

	class NewMapLiss implements ActionListener {
		public void actionPerformed(ActionEvent ave) {
			if (changed || mapDisplaying) {
				int answer = JOptionPane.showConfirmDialog(Main.this,
						"You might have unsaved changes, do you want to continue?", "Warning",
						JOptionPane.OK_CANCEL_OPTION);
				if (answer != JOptionPane.OK_OPTION) {
					return;
				}

				markAllAndRemove();
				clearLists();
				System.out.println(placeByName);
				System.out.println(placeByMapPosition);
				System.out.println(cgPlace);

			}

			FileFilter ff = new FileNameExtensionFilter("Images", "jpg", "png", "gif");
			jfc.setFileFilter(ff);

			int answer = jfc.showOpenDialog(Main.this);

			if (answer != JFileChooser.APPROVE_OPTION) {
				return;
			}

			changed = false;
			placesLoaded = false;

			File file = jfc.getSelectedFile();
			String path = file.getAbsolutePath();

			if (scroll != null) {
				centerP.remove(scroll);
			}
			ip = new ImagePanel(path);
			scroll = new JScrollPane(ip);
			centerP.add(scroll, BorderLayout.CENTER);

			mapDisplaying = true; // bool för att kolla om kartan är laddad

			validate();
			repaint();

		}

	}

	class SaveLiss implements ActionListener {
		public void actionPerformed(ActionEvent ave) {
			int answer = jfc.showSaveDialog(Main.this);
			if (answer != JFileChooser.APPROVE_OPTION) {
				return;
			}

			File file = jfc.getSelectedFile();
			String path = file.getAbsolutePath();

			try {
				FileWriter outFile = new FileWriter(path);
				PrintWriter out = new PrintWriter(outFile);

				for (Place place : placeByMapPosition.values()) {

					out.println(place);
				}
				out.close();
				outFile.close();
				changed = false;
			} catch (FileNotFoundException e) {
				JOptionPane.showMessageDialog(Main.this, "File not found");
			} catch (IOException ei) {
				JOptionPane.showMessageDialog(Main.this, "ERROR" + ei.getMessage());
			}
		}
	}

	public void markAllAndRemove() {
		for (Entry<Position, Place> me : placeByMapPosition.entrySet()) {
			Place p = me.getValue();
			ip.placesMarked.add(p);
			ip.ipRemovePlace(p);
		}
	}

	public void clearLists() {

		placeByMapPosition.clear();
		placeByName.clear();
		cgPlace.clear();

		cgPlace.put(Category.Bus, new HashSet<>());
		cgPlace.put(Category.Underground, new HashSet<>());
		cgPlace.put(Category.Train, new HashSet<>());
		cgPlace.put(Category.None, new HashSet<>());
	}

	class LoadPlacesLiss implements ActionListener {
		public void actionPerformed(ActionEvent ave) {
			Place loadedPlaces = null;

			FileFilter ff = new FileNameExtensionFilter("Text Files", "txt");
			jfc.setFileFilter(ff);

			if (placesLoaded || changed) {
				int answer = JOptionPane.showConfirmDialog(Main.this,
						"Unsaved changes, do you want to continue anyways?", "Warning", JOptionPane.OK_CANCEL_OPTION);
				if (answer != JOptionPane.OK_OPTION) {
					return;
				}

				markAllAndRemove();
				clearLists();
				System.out.println(placeByName);
				System.out.println(placeByMapPosition);
				System.out.println(cgPlace);
			}

			int answer = jfc.showOpenDialog(Main.this);
			if (answer != JFileChooser.APPROVE_OPTION) {
				return;
			}

			// TODO: check if you want to save

			File file = jfc.getSelectedFile();
			String path = file.getAbsolutePath();

			try {
				FileReader inFile = new FileReader(path);
				BufferedReader in = new BufferedReader(inFile);
				String line;

				while ((line = in.readLine()) != null) {
					String[] token = line.split(","); // splitting on ,
					String type = token[0];
					Category cg = Category.parseCategory(token[1]);
					int x = Integer.parseInt(token[2]);
					int y = Integer.parseInt(token[3]);
					Position mp = new Position(x, y);
					String name = token[4];

					if (type.equals("Described")) {
						String description = token[5];
						loadedPlaces = new DescribedPlace(description, name, mp, cg);

					} else if (type.equals("Named")) {
						loadedPlaces = new NamePlace(name, mp, cg);

					} else {
						JOptionPane.showMessageDialog(null, "Not correct file", "ERROR", JOptionPane.ERROR_MESSAGE);
						loadedPlaces = null;
					}
					if (loadedPlaces != null) {
						addPlace(loadedPlaces);
					}
					placesLoaded = true;

				}
				changed = false;

				in.close();
				inFile.close();

			} catch (FileNotFoundException e) {
				JOptionPane.showMessageDialog(Main.this, "File can't be opened");
			} catch (IOException ei) {
				JOptionPane.showMessageDialog(Main.this, "ERROR" + ei.getMessage());
			}

		}
	}

	private void addPlace(Place place) {
		placeByMapPosition.put(place.getPosition(), place);

		String name = place.getName();
		List<Place> sameName = placeByName.get(name);

		ip.addTriangle(place);

		if (sameName == null) {
			sameName = new LinkedList<Place>();
			placeByName.put(name, sameName);
		}
		sameName.add(place);
		cgPlace.get(place.getCategory()).add(place);
		changed = true;
		System.out.println(changed);
		System.out.println(place);

	}

	public static void main(String[] args) {
		new Main();
	}

}
