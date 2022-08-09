package elements.igrac;

import elements.figura.*;

public class Igrac{
	private String ime;
	private Figura []figure = null;
	
	public Igrac() {
	}
	
	public Igrac(String ime, Figura []figure) {
		this.ime = ime;
		this.figure = figure;
	}
	
	public String getIme() {
		return ime;
	}
	
	public Figura[] getFigure() {
		return figure;
	}
	
	public void setIme(String ime) {
		this.ime = ime;
	}
	
	public void setFigure(Figura []figure) {
		this.figure = figure;
	}
	
	public void pozovi() {
		for (Figura i : figure) {
			if (i.getFlagStop()) {
				synchronized (i) {
					i.notify();
				}
			}
		}
	}
}
