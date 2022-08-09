package forms;

import java.awt.Color;
import java.awt.Font;
import java.awt.BorderLayout;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

public class ShowMessage {
	public ShowMessage(String message) {
		JPanel myPanel = new JPanel();
		myPanel.setBackground(Color.white);
		myPanel.setBorder(new EmptyBorder(30, 30, 30, 30));
		myPanel.setBackground(new Color(255, 255, 255));
		myPanel.setLayout(new BorderLayout(0, 0));
		
		JLabel showMessage = new JLabel(message);
		showMessage.setFont(new Font("Tahoma", Font.BOLD, 12));
		myPanel.add(showMessage);
		myPanel.add(showMessage);
		
		UIManager.put("OptionPane.background", Color.white);
		UIManager.put("Panel.background", Color.white);
		
		JOptionPane.showMessageDialog(null, myPanel, "Putanja Kretanja", JOptionPane.PLAIN_MESSAGE);
	}
}
