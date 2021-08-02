package initialization;

import java.awt.Color;
import java.awt.Component;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableCellRenderer;

/**
 * @author Aleksandar Tulic
 * @version 1.0
 * @since 2021-07-28
 * */

public class ColorController { 
	/**
	 * <h3>FLAGCOLOR</h3>
	 * flagColor pamtimo kojom bojom da celija ostane ofarbana(koristimo repaint za tabelu). 
	 * Ukoliko bismo ovo izbacili onda kod kretanja kompozicije bi jedna boja prepisala drugu. 
	 * VAZNO: kod kretanja mi samo bojimo BORDER. 
	 * */
	public static Map <String, Color> flagColor = new HashMap<String, Color>();
	
	/**
	 * <h3>changeBorderColor</h3>
	 * Dodajemo BORDER sa odgovarajucom bojom.
	 * @param x red polja na kojem trebamo da promijenimo boju
	 * @param y kolona polja na kojem trebamo da promijenimo boju
	 * @param color boja GRANICE koju trebamo da postavimo 
	 * */
	public synchronized static void changeBorderColor(int x, int y, Color color) {
		init.table.getColumnModel().getColumn(y).setCellRenderer(new cellColorRenderer());
		flagColor.put(String.valueOf(x) + " " + String.valueOf(y), color);
		init.table.repaint();
	}
	
	/**
	 * <h3>changeBackColor</h3>
	 * Brisemo BORDER.
	 * @param x red polja na kojem trebamo da promijenimo boju
	 * @param y kolona polja na kojem trebamo da promijenimo boju 
	 * */
	public synchronized static void changeBackColor(int x, int y) {
		init.table.getColumnModel().getColumn(y).setCellRenderer(new cellColorRenderer());
		flagColor.put(String.valueOf(x) + " " + String.valueOf(y), null);
		init.table.repaint();
	}
}

/*
 * CHANGING THE BORDER COLOR OF THE CELL TO KNOW IF THERE IS A VEHICLE OR A TRAIN
 * */

@SuppressWarnings("serial")
class cellColorRenderer extends DefaultTableCellRenderer {
	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int col) {
		JLabel l = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, col);
		  
		Color color = ColorController.flagColor.get(String.valueOf(row) + " " + String.valueOf(col));
		if ( color != null ) {
			 Border border = BorderFactory.createLineBorder(color, 3);
			 l.setBorder(border);
		}

		l.setBackground(init.col[row][col]);
		return l;
	}
}
