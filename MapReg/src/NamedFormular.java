
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class NamedFormular extends JPanel{
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
