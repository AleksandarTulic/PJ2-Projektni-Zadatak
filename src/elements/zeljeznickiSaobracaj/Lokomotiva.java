package elements.zeljeznickiSaobracaj;

import enumarations.tipLokomotive;
import enumarations.vrstaPogona;

/**
 * @author Aleksandar Tulic
 * @version 1.0
 * @since 2021-07-28
 * */

public class Lokomotiva {
	private tipLokomotive tip;
	private vrstaPogona vrsta;
	private int snaga;
	private String oznaka;
	
	public Lokomotiva(tipLokomotive tip, vrstaPogona vrsta, int snaga, String oznaka) {
		this.tip = tip;
		this.vrsta = vrsta;
		this.snaga = snaga;
		this.oznaka = oznaka;
	}

	public tipLokomotive getTip() {
		return tip;
	}

	public void setTip(tipLokomotive tip) {
		this.tip = tip;
	}

	public vrstaPogona getVrsta() {
		return vrsta;
	}

	public void setVrsta(vrstaPogona vrsta) {
		this.vrsta = vrsta;
	}

	public int getSnaga() {
		return snaga;
	}

	public void setSnaga(int snaga) {
		this.snaga = snaga;
	}

	public String getOznaka() {
		return oznaka;
	}

	public void setOznaka(String oznaka) {
		this.oznaka = oznaka;
	}
}
