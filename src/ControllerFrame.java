import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;


public class ControllerFrame extends JFrame {

	double x;
	double y;
	double th;
	
	boolean left,right;
	
	public void setPose(double x, double y, double th) {
		this.x = x; this.y = y;this.th = th;
	}
	
	public void setBump(boolean right, boolean left) {
		this.left = left;
		this.right = right;
	}
	
	JTextField editX;
	JTextField editY;
	JTextField editTh;
	JLabel rightLabel, leftLabel;
	
	public ControllerFrame() {
		super("Controller");
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		editX = new JTextField("0.0");
		editY = new JTextField("0.0");
		editTh = new JTextField("0.0");
		editX.setEditable(false);
		editY.setEditable(false);
		editTh.setEditable(false);
		editX.setForeground(Color.black);
		editY.setForeground(Color.black);
		editTh.setForeground(Color.black);
		
		rightLabel = new JLabel("RIGHT");
		leftLabel  = new JLabel("LEFT");
		
		JPanel panel = (JPanel)getContentPane();
		panel.setLayout(new GridLayout(4,2));
		panel.add(new JLabel("X    :"));
		panel.add(editX);
		panel.add(new JLabel("Y    :"));
		panel.add(editY);
		panel.add(new JLabel("THETA:"));
		panel.add(editTh);
		panel.add(rightLabel);
		panel.add(leftLabel);
		
		
		setSize(400, 150);
		setLocation(740, 300);
		setVisible(true);
	}
	
	public void update() {
		editX.setText(Double.toString(x));
		editY.setText(Double.toString(y));
		editTh.setText(Double.toString(th));
		if(right) {
			rightLabel.setForeground(Color.red);
		} else {
			rightLabel.setForeground(Color.black);
		}
		if(left) {
			leftLabel.setForeground(Color.red);
		} else {
			leftLabel.setForeground(Color.black);
		}
	}
	
	public static void main(String[] s) {
		new ControllerFrame();
	}
}
