package elements.zeljeznickiSaobracaj;

/**
 * @author Aleksandar Tulic
 * @version 1.0
 * @since 2021-07-28
 * */

public class Vagon {
	private double duzina;
	private String oznaka;
	
	public Vagon(double duzina, String oznaka) {
		this.duzina = duzina;
		this.oznaka = oznaka;
	}
	
	public double getDuzina() {
		return duzina;
	}
	public void setDuzina(double duzina) {
		this.duzina = duzina;
	}
	public String getOznaka() {
		return oznaka;
	}
	public void setOznaka(String oznaka) {
		this.oznaka = oznaka;
	}
}
