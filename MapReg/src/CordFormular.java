import javax.swing.*;

public class CordFormular extends JPanel {

	/**
	 * Sebastian Åkerlund - 1995-10-01
	 * seae5393
	 * sebake01@gmail.com
	 */
	private static final long serialVersionUID = 1L;
	private JTextField xField = new JTextField(6);
	private JTextField yField = new JTextField(6);

	public CordFormular() {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		JPanel row1 = new JPanel();
		add(row1);
		row1.add(new JLabel("X:"));
		row1.add(xField);
		row1.add(new JLabel("Y:"));
		row1.add(yField);
		System.out.println("CordForm");
	}

	public String getXText() {
		return xField.getText();
	}

	public String getYText() {
		return yField.getText();
	}

	public int getXCoordinate() {
		return Integer.parseInt(xField.getText());
	}

	public int getYCoordinate() {
		return Integer.parseInt(yField.getText());
	}

}
