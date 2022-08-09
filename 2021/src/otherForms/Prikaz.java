package otherForms;

import javax.swing.JFrame;

import initialization.init;
import initialization.initPrikaz;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * @author Aleksandar Tulic
 * @version 1.0
 * @since 2021-07-28
 * */

public class Prikaz {

	private JFrame frame;

	/**
	 * Create the application.
	 */
	public Prikaz() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {
				init.flagProzor = true;
			}
		});
		
		@SuppressWarnings("unused")
		initPrikaz a = new initPrikaz(frame);
	}
	
	public void setVisibility(boolean flag) {
		frame.setVisible(flag);
	}
}
