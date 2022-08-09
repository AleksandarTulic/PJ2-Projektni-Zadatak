package elements.mapa;

import java.util.ArrayList;

import elements.drumskiSaobracaj.Vozilo;
import helpFunctions.Pair;

/**
 * @author Aleksandar Tulic
 * @version 1.0
 * @since 2021-07-28
 * */

public class PruzniPrelaz {
	private int flagPrelaz;
	//stavio sam pos1 i po2 jer na mapi koju koristimo oni zauzimaju samo dva polja uvijek
	//u suprotnom sam mogao koristiti arraylist
	private Pair pos1;
	private Pair pos2;
	private ArrayList <Vozilo> awake = new ArrayList <Vozilo>();
	
	public PruzniPrelaz(Pair pos1, Pair pos2) {
		this.pos1 = pos1;
		this.pos2 = pos2;
		flagPrelaz = 0;
	}
	
	public Pair getPos1() {
		return pos1;
	}
	
	public Pair getPos2() {
		return pos2;
	}
	
	//ova dva setera zapravo nisu ni potrebno u ovoj implementaciji ali sam dodao dodatno ako kasnije zatreba
	public void setPos1(Pair pos1) {
		this.pos1 = pos1;
	}
	
	public void setPos2(Pair pos2) {
		this.pos2 = pos2;
	}
	
	public int getFlag() {
		return flagPrelaz;
	}
	
	public void setFlag(int flagPrelaz) {
		this.flagPrelaz = flagPrelaz;
	}
	
	/**
	 * <b>incFlag</b>
	 * Voz je krenuo sa neke stanice i onda odgovarajuci PP povecava flag kako bi obavjestio vozace da ne mogu preci preko pruznog prelaza. 
	 * Koristio sam int jer potrebno je da pamtim koliko vozova se nalazi na nekom segmentu. 
	 * @return nista
	 * */
	public void incFlag() {
		flagPrelaz++;
	}
	
	/**
	 * <b>decFlag</b>
	 * Ukoliko neki voz je stigao do neke stanice onda pruzni prelaz na nekoj sekciji se umanjuje. 
	 * Ako je neko vozilo cekalo da prodje voz onda PP obavjestava ta vozila da mogu da anstave sa kretanjem. 
	 * @return nista
	 * */
	public void decFlag() {
		flagPrelaz--;
		
		//obavjestavamo vozila da mogu da krenu
		if ( flagPrelaz == 0 ) {
			for (Vozilo i : awake) {
				synchronized(i) {
					i.notify();
				}
			}
			
			//obavjestio sam ih
			awake.clear();
		}
	}
	
	public void addVozilo(Vozilo vozilo) {
		awake.add(vozilo);
	}
}
