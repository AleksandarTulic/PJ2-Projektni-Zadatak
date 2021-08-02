package elements.drumskiSaobracaj;

/**
 * @author Aleksandar Tulic
 * @version 1.0
 * @since 2021-07-28
 * */

public class Kamion extends Vozilo {
	private int nosivost;
	
	public Kamion(String model, String marka, int godiste, int brzina, int nosivost) {
		super(model, marka, godiste, brzina/10);
		this.nosivost = nosivost;
	}

	public int getNosivost() {
		return nosivost;
	}

	public void setNosivost(int nosivost) {
		this.nosivost = nosivost;
	}
	
	@Override
	public String toString() {
		return "=========================================" + "\nTip Vozila: Kamion" +
				super.toString() + "Nosivost: " + nosivost + "\n" + 
				"=========================================";
	}
}
