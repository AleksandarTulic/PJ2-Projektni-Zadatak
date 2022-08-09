package elements.gui;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.Insets;

import javax.swing.border.Border;

//https://stackhowto.com/how-to-create-rounded-jbutton-in-java/
public class RoundButton implements Border{
	private int radius;
	
	public RoundButton(int radius) {
		this.radius = radius;
	}

	@Override
	public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
		g.drawRoundRect(x, y, width - 1, height - 1, radius, radius);
	}

	@Override
	public Insets getBorderInsets(Component c) {
		return new Insets(radius + 1, radius + 1, radius + 2, radius);
	}

	@Override
	public boolean isBorderOpaque() {
		return true;
	}
}
