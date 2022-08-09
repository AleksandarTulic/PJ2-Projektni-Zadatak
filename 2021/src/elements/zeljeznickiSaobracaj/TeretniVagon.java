package elements.zeljeznickiSaobracaj;

/**
 * @author Aleksandar Tulic
 * @version 1.0
 * @since 2021-07-28
 * */

public class TeretniVagon extends Vagon {
	private int maksNosivost;
	
	public TeretniVagon(double duzina, String oznaka, int maksNosivost) {
		super(duzina, oznaka);
		this.maksNosivost = maksNosivost;
	}

	public int getMaksNosivost() {
		return maksNosivost;
	}

	public void setMaksNosivost(int maksNosivost) {
		this.maksNosivost = maksNosivost;
	}
}
