import java.awt.EventQueue;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.logging.Level;

import javax.swing.JFrame;

import initialization.init;

/**
 * @author Aleksandar Tulic
 * @version 1.0
 * @since 2021-07-28
 * 
 * <h1>Glavna forma aplikacija.</h1>
 * Na ovom dijelu aplikacije je sadrzan frejm u kome se vrsi simulacija kretanja.
 * Dok sami elementi frejma su dodani u drugoj klasi(Klasa: init).
 * */

public class Main {

	private JFrame frame;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Main window = new Main();
					window.frame.setVisible(true);
				} catch (Exception e) {
					StringWriter sw = new StringWriter();
		            e.printStackTrace(new PrintWriter(sw));
		            String mess = sw.toString();
					String message = "\nKomentar: Nije moguce ukljuciti aplikaciju!!!\nMessage: " + mess + "\n";
					init.log.setValue(Level.OFF, message);
					init.log.publish();
					init.log.flush();
				}
			}
		});
	}

	public Main() throws FileNotFoundException{
		initialize();
	}

	private void initialize() throws FileNotFoundException{
		frame = new JFrame();
		frame.setBounds(100, 100, 703, 592);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		init.fill();
		
		frame.getContentPane().add(init.table);
		frame.getContentPane().add(init.button1);
		frame.getContentPane().add(init.button2);
	
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				File f = new File(System.getProperty("user.dir") + File.separator + "log");
				String []file = f.list();
				
				for (int i=0;i<file.length;i++) {
					if ( file[i].endsWith(".xml") || file[i].endsWith(".lck") ) {
						File f1 = new File(System.getProperty("user.dir") + File.separator + "log" + File.separator + file[i]);
						if (f1.exists() && f1.length() == 0) {
							f1.delete();
						}
					}
				}
			}
		});
	}
}
