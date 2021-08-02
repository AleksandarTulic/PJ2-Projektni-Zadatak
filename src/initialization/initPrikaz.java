package initialization;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.logging.Level;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.TableModelEvent;
import javax.swing.table.DefaultTableModel;

import controller.KompozicijaController;
import elements.zeljeznickiSaobracaj.Kompozicija;
import helpFunctions.RadioButtonEditor;
import helpFunctions.RadioButtonRenderer;

import javax.swing.JRadioButton;

/**
 * @author Aleksandar Tulic
 * @version 1.0
 * @since 2021-07-28
 * */

public class initPrikaz {
	private JTable tableP;
	private JScrollPane sc;
	private ButtonGroup bg;
	private DefaultTableModel dtm;
	private JButton button;
	ArrayList <JRadioButton> rb = new ArrayList<JRadioButton>();
	
	@SuppressWarnings("serial")
	public initPrikaz(JFrame frame) {
		frame.setTitle("ISA");
		frame.setBounds(100, 100, 770, 508);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		dtm = new DefaultTableModel();
		dtm.setColumnIdentifiers(new Object[] {"Check", "Start Position", "End position", "Brzina Kompozicije"});
		
		input();
	
		tableP = new JTable(dtm) {
	         public void tableChanged(TableModelEvent tme) {
	            super.tableChanged(tme);
	            repaint();
	         }
	      };
	      
	      bg = new ButtonGroup();
	      
	      tableP.getColumn("Check").setCellRenderer(new RadioButtonRenderer());
	      tableP.getColumn("Check").setCellEditor(new RadioButtonEditor(new JCheckBox()));
	      
	      sc = new JScrollPane(tableP);
	      sc.setBounds(0, 0, 500, 450);
	      frame.getContentPane().setLayout(null);
	      frame.getContentPane().add(sc);
	      
	      button = new JButton("More info");
	      button.addActionListener(new ActionListener() {
	      	public void actionPerformed(ActionEvent e) {
	      		if ( rb.size() != 0 ) {
	      		}
	      	}
	      });
	      button.setBackground(Color.WHITE);
	      button.setFont(new Font("Tahoma", Font.BOLD, 14));
	      button.setBounds(590, 50, 145, 44);
	      frame.getContentPane().add(button);
	      
	      tableP.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
	}
	
	public void input() {
		if ( KompozicijaController.kompozicija.size() != 0 ) {
			try {
				for (Kompozicija i : KompozicijaController.kompozicija) {
					
					Object []obj = new Object[4];
					JRadioButton a = new JRadioButton();
					rb.add(a);
					bg.add(a);
					obj[0] = rb.get(rb.size() - 1);
					obj[1] = i.startingPos();
					obj[2] = i.endPos();
					obj[3] = i.getBrzina();
					
					dtm.addRow(obj);
				}
				
				rb.get(0).setSelected(true);
			}catch (Exception e) {
				StringWriter sw = new StringWriter();
	            e.printStackTrace(new PrintWriter(sw));
	            String mess = sw.toString();
				String message = "\nMessage: " + mess + "\n";
				init.log.setValue(Level.INFO, message);
				init.log.publish();
				init.log.flush();
			}
		}
	}
	//prikazem listu kompozicija
	//	prikazem: brzina kompozicjie, koliko elemenata ukupno, koliko vagona, koliko vozova
}
