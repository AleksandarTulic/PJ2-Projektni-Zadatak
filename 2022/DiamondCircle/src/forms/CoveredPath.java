package forms;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import elements.figura.Figura;
import help.*;
import java.util.*;

public class CoveredPath {
	public CoveredPath(String path) {
		JPanel myPanel = new JPanel();
		myPanel.setBackground(Color.white);
		myPanel.setBorder(new EmptyBorder(30, 30, 30, 30));
		myPanel.setBackground(new Color(255, 255, 255));
		myPanel.setLayout(new GridLayout(Elements.MAX_ROW, Elements.MAX_COLUMN));
		
		String []l = path.split("-");
		myPanel.setLayout(new GridLayout(l.length + 2, 1));
		JLabel title = new JLabel("Putanja: ");
		title.setFont(new Font("Tahoma", Font.BOLD, 12));
		myPanel.add(title);
		myPanel.add(new JLabel(""));
		
		for (String i : l) {
			JLabel label = new JLabel("     -   " + i);
			myPanel.add(label);
		}
		
		UIManager.put("OptionPane.background", Color.white);
		UIManager.put("Panel.background", Color.white);
		
		JOptionPane.showMessageDialog(null, myPanel, "Putanja Kretanja", JOptionPane.PLAIN_MESSAGE);
	}
	
	public CoveredPath(Figura figura) {
		JPanel myPanel = new JPanel();
		myPanel.setBackground(Color.white);
		myPanel.setBorder(new EmptyBorder(30, 30, 30, 30));
		myPanel.setBackground(new Color(255, 255, 255));
		myPanel.setLayout(new GridLayout(Elements.currRow, Elements.currCol));
		
		JPanel [][]panelArr = new JPanel[Elements.currRow][Elements.currCol];
		
		int showValue = 1;
		for (int i=0;i<Elements.currRow;i++) {
			for (int j=0;j<Elements.currCol;j++) {
				panelArr[i][j] = new JPanel();
				myPanel.add(panelArr[i][j]);
				panelArr[i][j].setBorder(new LineBorder(new Color(128, 128, 128)));
				panelArr[i][j].setBackground(new Color(255, 255, 255));
				panelArr[i][j].setPreferredSize(new Dimension(50, 50));
				panelArr[i][j].setLayout(new BorderLayout(0, 0));
				
				JLabel label = new JLabel(showValue++ + "");
				label.setBorder(new EmptyBorder(10, 15, 10, 10));
				panelArr[i][j].add(label, BorderLayout.CENTER);
			}
		}
		
		List<Pair> arrPositions = figura.getArrPositions();
		
		for (Pair i : arrPositions)
			panelArr[i.getX()][i.getY()].setBackground(figura.getColor());
		
		//UIManager UI=new UIManager();
		UIManager.put("OptionPane.background", Color.white);
		UIManager.put("Panel.background", Color.white);
		
		JOptionPane.showMessageDialog(null, myPanel, "Putanja Kretanja", JOptionPane.PLAIN_MESSAGE);
	}
}
