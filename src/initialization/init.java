package initialization;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import controller.KompozicijaController;
import controller.VozilaController;
import myLogger.MyLogger;
import otherForms.Prikaz;

/**
 * @author Aleksandar Tulic
 * @version 1.0
 * @since 2021-07-28
 * */

public class init {
	public static JTable table;
	public static Color[][] col = new Color[30][30];
	public static Object[][] matrix = new Object[30][30];
	public static JButton button1 = null;
	public static JButton button2 = null;
	public static boolean flagProzor = true;
	public static Map <String, Boolean> flagKrajVozilo = new HashMap<String, Boolean>();
	public static MyLogger log = new MyLogger();
	
	/**
	 * <h3>Popunjavanje glavnog frejma</h3> 
	 * Sa metodom fill popunjavamo frejm, npr. sa tabelom , koja polja su obojena kojom bojom i tako dalje. 
	 * Ali takodje ovde inicijalizujemo neke druge promjenjljive sa odgovarajcim vrijednostima. 
	 * table - prvobitna tabela koja se koristi za reprezentaciju mape. 
	 * col - tabela s'kojom pamtimo na kojoj poziciji se nalazi koja boja(Za bolje shvatanje koristi pogledati ColorController). 
	 * matrix - posstavaljmo objekte na neku poziciju kada se krecu. 
	 * flagProzor - moze samo jedan dodatan frejm u jednom trenutku biti otvoren. 
	 * flagKrajVozilo - tacke na kojima je kraj za vozila koja se krecu na odgovarajucem putu. 
	 * log - koristimo kako bi exceptions logovali. 
	 * 
	 * */
	public static void fill() {
		
		for (int i=0;i<30;i++) {
			for (int j=0;j<30;j++) {
				ColorController.flagColor.put(String.valueOf(i) + " " + String.valueOf(j), null);
				flagKrajVozilo.put(String.valueOf(i) + " " + String.valueOf(j), false);
			}
		}
		
		//postavljam na true pocetne lokacije vozila
		//ovo koristim kako bi vozilo znalo gdje je kraj njegovog putovanja
		//tj kako bi znalo da otkrije kraj
		flagKrajVozilo.put("21 0", true);
		flagKrajVozilo.put("29 8", true);
		flagKrajVozilo.put("29 14", true);
		flagKrajVozilo.put("0 13", true);
		flagKrajVozilo.put("29 22", true);
		flagKrajVozilo.put("20 29", true);
		
		init.table = new JTable();
		table.setModel(new DefaultTableModel(	
			new Object[][] {
				{null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
			},
			new String[] {
				"New column", "New column", "New column", "New column", "New column", "New column", "New column", "New column", "New column", "New column", "New column", "New column", "New column", "New column", "New column", "New column", "New column", "New column", "New column", "New column", "New column", "New column", "New column", "New column", "New column", "New column", "New column", "New column", "New column", "New column"
			}
		));
		
		table.getColumnModel().getColumn(0).setResizable(false);
		table.getColumnModel().getColumn(0).setPreferredWidth(15);
		table.getColumnModel().getColumn(1).setResizable(false);
		table.getColumnModel().getColumn(1).setPreferredWidth(15);
		table.getColumnModel().getColumn(2).setResizable(false);
		table.getColumnModel().getColumn(2).setPreferredWidth(15);
		table.getColumnModel().getColumn(3).setResizable(false);
		table.getColumnModel().getColumn(3).setPreferredWidth(15);
		table.getColumnModel().getColumn(4).setResizable(false);
		table.getColumnModel().getColumn(4).setPreferredWidth(15);
		table.getColumnModel().getColumn(5).setResizable(false);
		table.getColumnModel().getColumn(5).setPreferredWidth(15);
		table.getColumnModel().getColumn(6).setResizable(false);
		table.getColumnModel().getColumn(6).setPreferredWidth(15);
		table.getColumnModel().getColumn(7).setResizable(false);
		table.getColumnModel().getColumn(7).setPreferredWidth(15);
		table.getColumnModel().getColumn(8).setResizable(false);
		table.getColumnModel().getColumn(8).setPreferredWidth(15);
		table.getColumnModel().getColumn(9).setResizable(false);
		table.getColumnModel().getColumn(9).setPreferredWidth(15);
		table.getColumnModel().getColumn(10).setResizable(false);
		table.getColumnModel().getColumn(10).setPreferredWidth(15);
		table.getColumnModel().getColumn(11).setResizable(false);
		table.getColumnModel().getColumn(11).setPreferredWidth(15);
		table.getColumnModel().getColumn(12).setResizable(false);
		table.getColumnModel().getColumn(12).setPreferredWidth(15);
		table.getColumnModel().getColumn(13).setResizable(false);
		table.getColumnModel().getColumn(13).setPreferredWidth(15);
		table.getColumnModel().getColumn(14).setResizable(false);
		table.getColumnModel().getColumn(14).setPreferredWidth(15);
		table.getColumnModel().getColumn(15).setResizable(false);
		table.getColumnModel().getColumn(15).setPreferredWidth(15);
		table.getColumnModel().getColumn(16).setResizable(false);
		table.getColumnModel().getColumn(16).setPreferredWidth(15);
		table.getColumnModel().getColumn(17).setResizable(false);
		table.getColumnModel().getColumn(17).setPreferredWidth(15);
		table.getColumnModel().getColumn(18).setResizable(false);
		table.getColumnModel().getColumn(18).setPreferredWidth(15);
		table.getColumnModel().getColumn(19).setResizable(false);
		table.getColumnModel().getColumn(19).setPreferredWidth(15);
		table.getColumnModel().getColumn(20).setResizable(false);
		table.getColumnModel().getColumn(20).setPreferredWidth(15);
		table.getColumnModel().getColumn(21).setResizable(false);
		table.getColumnModel().getColumn(21).setPreferredWidth(15);
		table.getColumnModel().getColumn(22).setResizable(false);
		table.getColumnModel().getColumn(22).setPreferredWidth(15);
		table.getColumnModel().getColumn(23).setResizable(false);
		table.getColumnModel().getColumn(23).setPreferredWidth(15);
		table.getColumnModel().getColumn(24).setResizable(false);
		table.getColumnModel().getColumn(24).setPreferredWidth(15);
		table.getColumnModel().getColumn(25).setResizable(false);
		table.getColumnModel().getColumn(25).setPreferredWidth(15);
		table.getColumnModel().getColumn(26).setResizable(false);
		table.getColumnModel().getColumn(26).setPreferredWidth(15);
		table.getColumnModel().getColumn(27).setResizable(false);
		table.getColumnModel().getColumn(27).setPreferredWidth(15);
		table.getColumnModel().getColumn(28).setResizable(false);
		table.getColumnModel().getColumn(28).setPreferredWidth(15);
		table.getColumnModel().getColumn(29).setResizable(false);
		table.getColumnModel().getColumn(29).setPreferredWidth(15);
		table.setBounds(10, 11, 667, 480);
		
		for (int i=0;i<30;i++) {
			for (int j=0;j<30;j++) {
				col[i][j] = Color.white;
			}
		}
		
		col[29][2] = Color.LIGHT_GRAY;
		col[28][1] = Color.LIGHT_GRAY;
		col[28][2] = Color.LIGHT_GRAY;
		col[27][1] = Color.LIGHT_GRAY;
		col[27][2] = Color.LIGHT_GRAY;
		col[26][2] = Color.LIGHT_GRAY;
		col[25][2] = Color.LIGHT_GRAY;
		col[24][2] = Color.LIGHT_GRAY;
		col[23][2] = Color.LIGHT_GRAY;
		col[22][2] = Color.LIGHT_GRAY;
		col[19][2] = Color.LIGHT_GRAY;
		col[18][2] = Color.LIGHT_GRAY;
		col[17][2] = Color.LIGHT_GRAY;
		col[17][3] = Color.LIGHT_GRAY;
		col[17][4] = Color.LIGHT_GRAY;
		col[17][5] = Color.LIGHT_GRAY;
		col[16][5] = Color.LIGHT_GRAY;
		col[15][5] = Color.LIGHT_GRAY;
		col[14][5] = Color.LIGHT_GRAY;
		col[13][5] = Color.LIGHT_GRAY;
		col[12][5] = Color.LIGHT_GRAY;
		col[11][5] = Color.LIGHT_GRAY;
		col[10][5] = Color.LIGHT_GRAY;
		col[9][5] = Color.LIGHT_GRAY;
		col[8][5] = Color.LIGHT_GRAY;
		col[7][5] = Color.LIGHT_GRAY;
		col[6][5] = Color.LIGHT_GRAY;
		col[5][6] = Color.LIGHT_GRAY;
		col[6][6] = Color.LIGHT_GRAY;
		col[6][7] = Color.LIGHT_GRAY;
		col[5][7] = Color.LIGHT_GRAY;
		col[6][8] = Color.LIGHT_GRAY;
		col[6][9] = Color.LIGHT_GRAY;
		col[6][10] = Color.LIGHT_GRAY;
		col[6][11] = Color.LIGHT_GRAY;
		col[6][12] = Color.LIGHT_GRAY;
		col[6][15] = Color.LIGHT_GRAY;
		col[6][16] = Color.LIGHT_GRAY;
		col[6][17] = Color.LIGHT_GRAY;
		col[6][18] = Color.LIGHT_GRAY;
		col[6][19] = Color.LIGHT_GRAY;
		col[7][19] = Color.LIGHT_GRAY;
		col[8][19] = Color.LIGHT_GRAY;
		col[9][19] = Color.LIGHT_GRAY;
		col[10][19] = Color.LIGHT_GRAY;
		col[11][19] = Color.LIGHT_GRAY;
		col[12][19] = Color.LIGHT_GRAY;
		col[13][19] = Color.LIGHT_GRAY;
		col[12][20] = Color.LIGHT_GRAY;
		col[13][20] = Color.LIGHT_GRAY;
		col[14][20] = Color.LIGHT_GRAY;
		col[15][20] = Color.LIGHT_GRAY;
		col[16][20] = Color.LIGHT_GRAY;
		col[17][20] = Color.LIGHT_GRAY;
		col[18][20] = Color.LIGHT_GRAY;
		col[18][21] = Color.LIGHT_GRAY;
		col[18][22] = Color.LIGHT_GRAY;
		col[18][23] = Color.LIGHT_GRAY;
		col[18][24] = Color.LIGHT_GRAY;
		col[18][25] = Color.LIGHT_GRAY;
		col[18][26] = Color.LIGHT_GRAY;
		col[19][26] = Color.LIGHT_GRAY;
		col[22][26] = Color.LIGHT_GRAY;
		col[23][26] = Color.LIGHT_GRAY;
		col[24][26] = Color.LIGHT_GRAY;
		col[25][25] = Color.LIGHT_GRAY;
		col[25][26] = Color.LIGHT_GRAY;
		col[26][25] = Color.LIGHT_GRAY;
		col[26][26] = Color.LIGHT_GRAY;
		col[25][27] = Color.LIGHT_GRAY;
		col[25][28] = Color.LIGHT_GRAY;
		col[25][29] = Color.LIGHT_GRAY;
		col[12][21] = Color.LIGHT_GRAY;
		col[12][22] = Color.LIGHT_GRAY;
		col[12][23] = Color.LIGHT_GRAY;
		col[12][24] = Color.LIGHT_GRAY;
		col[12][25] = Color.LIGHT_GRAY;
		col[12][26] = Color.LIGHT_GRAY;
		col[11][26] = Color.LIGHT_GRAY;
		col[10][26] = Color.LIGHT_GRAY;
		col[9][26] = Color.LIGHT_GRAY;
		col[9][27] = Color.LIGHT_GRAY;
		col[9][28] = Color.LIGHT_GRAY;
		col[8][28] = Color.LIGHT_GRAY;
		col[7][28] = Color.LIGHT_GRAY;
		col[6][28] = Color.LIGHT_GRAY;
		col[5][28] = Color.LIGHT_GRAY;
		col[5][27] = Color.LIGHT_GRAY;
		col[5][26] = Color.LIGHT_GRAY;
		col[5][25] = Color.LIGHT_GRAY;
		col[5][24] = Color.LIGHT_GRAY;
		col[5][23] = Color.LIGHT_GRAY;
		col[4][23] = Color.LIGHT_GRAY;
		col[3][23] = Color.LIGHT_GRAY;
		col[3][22] = Color.LIGHT_GRAY;
		col[2][22] = Color.LIGHT_GRAY;
		col[1][22] = Color.LIGHT_GRAY;
		col[1][23] = Color.LIGHT_GRAY;
		col[1][24] = Color.LIGHT_GRAY;
		col[1][25] = Color.LIGHT_GRAY;
		col[1][26] = Color.LIGHT_GRAY;
		col[1][27] = Color.LIGHT_GRAY;
		col[2][26] = Color.LIGHT_GRAY;
		col[2][27] = Color.LIGHT_GRAY;
		
		//CYAN
		
		for (int i=0;i<=8;i++) {
			if ( i == 2 ) {
				col[20][30 - i - 1] = col[21][30 - i - 1] = Color.cyan;
				continue;
			}
			
			if ( i == 3 ) {
				col[20][i] = col[21][i] = Color.cyan;
				continue;
			}
			
			col[20][i] = col[21][i] = Color.cyan;
			col[20][30 - i - 1] = col[21][30 - i - 1] = Color.cyan;
		}
		
		for (int i=22;i<30;i++) {
			col[i][7] = col[i][8] = Color.cyan;
			col[i][22] = col[i][21] = Color.cyan;
		}
		
		for (int i=0;i<30;i++) {
			if ( i == 6 ) {
				continue;
			}
			
			col[i][13] = col[i][14] = Color.cyan;
		}
		
		//BLACK
		
		col[20][2] = col[21][2] = Color.black;
		col[6][13] = col[6][14] = Color.black;
		col[20][26] = col[21][26] = Color.black;
		
		for (int i=0;i<30;i++) {
			table.getColumnModel().getColumn(i).setCellRenderer(new StatusColumnCellRenderer());
		}
		
		table.setValueAt("A", 28, 1);
		table.setValueAt("B", 6, 6);
		table.setValueAt("C", 13, 19);
		table.setValueAt("D", 2, 26);
		table.setValueAt("E", 26, 25);
		
		button1 = new JButton("Start");
		button1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				KompozicijaController b = new KompozicijaController();
				b.start();
				VozilaController a = new VozilaController();
				a.start();
			}
		});
		
		button1.setBounds(10, 507, 130, 23);
		
		button2 = new JButton("Prikaz");
		button2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if ( flagProzor ) {
					Prikaz a = new Prikaz();
					a.setVisibility(true);
					flagProzor = false;
				}
			}
		});
		button2.setBounds(162, 507, 130, 23);
		
		for (int i=0;i<30;i++) {
			table.getColumnModel().getColumn(i).setCellRenderer(new cellColorRenderer());
		}
	}
}

@SuppressWarnings("serial")
class StatusColumnCellRenderer extends DefaultTableCellRenderer {
	  @Override
	  public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int col) {
	    JLabel l = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, col);
	    
	    l.setBackground(init.col[row][col]);
	    
	    return l;
	  }
}









