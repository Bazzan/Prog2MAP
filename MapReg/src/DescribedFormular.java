import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class DescribedFormular extends JPanel{
	private JTextField nameField = new JTextField(15);
	private JTextField descriptionField = new JTextField(); 
		
	public DescribedFormular() {
			
			setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
			JPanel line1 = new JPanel();
			line1.add(new JLabel("Name:"));
			line1.add(nameField);
			add(line1);
			
			JPanel line2 = new JPanel();
			line2.add(new JLabel("Description:"));
			line2.add(descriptionField);
			add(line2);			
			
			
		}
		
		
	}