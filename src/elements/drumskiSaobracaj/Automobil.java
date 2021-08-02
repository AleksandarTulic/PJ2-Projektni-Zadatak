package elements.drumskiSaobracaj;

/**
 * @author Aleksandar Tulic
 * @version 1.0
 * @since 2021-07-28
 * */

public class Automobil extends Vozilo {
	private int brojVrata;
	
	public Automobil(String model, String marka, int godiste, int brzina, int brojVrata) {
		super(model, marka, godiste, brzina/10);
		this.brojVrata = brojVrata;
	}

	public int getBrojVrata() {
		return brojVrata;
	}

	public void setBrojVrata(int brojVrata) {
		this.brojVrata = brojVrata;
	}
	
	@Override
	public String toString() {
		return "=========================================" + "\nTip Vozila: Automobil" +
				super.toString() + "Broj Vrata: " + brojVrata + "\n" + 
				"=========================================";
	}
}
