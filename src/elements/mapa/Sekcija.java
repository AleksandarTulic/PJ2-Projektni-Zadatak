package elements.mapa;

import helpFunctions.Pair;

/**
 * @author Aleksandar Tulic
 * @version 1.0
 * @since 2021-07-28
 * */

public class Sekcija {
	//end1 - char pocetne stanice
	private char end1;
	//end2 - char odredisne stanice
	private char end2;
	//pos1 - pozicija na koju se voz pusta kada krece sa neke stanice od strane stanice
	private Pair pos1;
	//pos2 - pozicija na ispred odredisne stanice
	private Pair pos2;
	
	//zbog mape koja je data pamtimo maksimalno jedan pruzni prelaz za sekciju
	PruzniPrelaz pp;
	
	public Sekcija(char end1, char end2, Pair pos1, Pair pos2, PruzniPrelaz pp) {
		this.end1 = end1;
		this.end2 = end2;
		this.pos1 = pos1;
		this.pos2 = pos2;
		this.pp = pp;
	}

	public char getEnd1() {
		return end1;
	}

	public void setEnd1(char end1) {
		this.end1 = end1;
	}

	public char getEnd2() {
		return end2;
	}

	public void setEnd2(char end2) {
		this.end2 = end2;
	}

	public Pair getPos1() {
		return pos1;
	}

	public void setPos1(Pair pos1) {
		this.pos1 = pos1;
	}

	public Pair getPos2() {
		return pos2;
	}

	public void setPos2(Pair pos2) {
		this.pos2 = pos2;
	}

	public PruzniPrelaz getPp() {
		return pp;
	}

	public void setPp(PruzniPrelaz pp) {
		this.pp = pp;
	}
}
