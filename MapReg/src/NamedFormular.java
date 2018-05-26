
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *  Sebastian Åkerlund - 1995-10-01
 * seae5393
 * sebake01@gmail.com
 */
public class NamedFormular extends JPanel {

	private static final long serialVersionUID = 1L;
	private JTextField nameField = new JTextField(15);

	public NamedFormular() {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		JPanel line1 = new JPanel();
		line1.add(new JLabel("Name of place:"));
		line1.add(nameField);
		add(line1);
	}

	public String getName() {
		return nameField.getText();
	}
}
