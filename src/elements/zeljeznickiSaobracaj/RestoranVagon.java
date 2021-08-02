package elements.zeljeznickiSaobracaj;

/**
 * @author Aleksandar Tulic
 * @version 1.0
 * @since 2021-07-28
 * */

public class RestoranVagon extends PutnickiVagon {
	private String opis;
	
	public RestoranVagon(double duzina, String oznaka, String opis) {
		super(duzina, oznaka);
		this.opis = opis;
	}

	public String getOpis() {
		return opis;
	}

	public void setOpis(String opis) {
		this.opis = opis;
	}
}
