package elements.zeljeznickiSaobracaj;

/**
 * @author Aleksandar Tulic
 * @version 1.0
 * @since 2021-07-28
 * */

public class SjedisteVagon extends PutnickiVagon {
	private int brojMjesta;
	
	public SjedisteVagon(double duzina, String oznaka, int brojMjesta) {
		super(duzina, oznaka);
		this.brojMjesta = brojMjesta;
	}

	public int getBrojMjesta() {
		return brojMjesta;
	}

	public void setBrojMjesta(int brojMjesta) {
		this.brojMjesta = brojMjesta;
	}
}
