package controller;

import java.util.LinkedList;
import java.util.Queue;

import elements.drumskiSaobracaj.Vozilo;
import helpFunctions.Pair;

/**
 * @author Aleksandar Tulic
 * @version 1.0
 * @since 2021-07-28
 * */

public class StartVoziloController {
	Pair pos;
	private Queue<Vozilo> q = new LinkedList<Vozilo>();
	
	/**
	 * <b>constructor StartVoziloController</b>
	 * @param pos pozicija pocetne tacke za svaki put (za svaki smjer)
	 * */
	public StartVoziloController(Pair pos) {
		this.pos = pos;
	}
	
	/**
	 * <b>addVozilo</b>
	 * @param p vozilo koje pamtimo jer ne moze trenutno da se doda na put zbog guzve
	 * @return nista
	 * */
	public void addVozilo(Vozilo p) {
		q.add(p);
	}
	
	/**
	 * <b>removeVozilo</b>
	 * @param p vozilo brisemo sa reda
	 * @return Vozilo koje brisemo
	 * */
	public Vozilo removeVozilo() {
		return q.remove();
	}
	
	/**
	 * <b>notifyVOzilo</b>
	 * Obavjestavamo vozilo da na prvoj poziciji nema nikoga i moze da nastavi sa kretanjem. 
	 * @return da li ima vozila da obavjestimo da moze da nastavi sa kretanjem
	 * */
	public boolean notifyVOzilo() {
		if ( q.size() > 0 ) {
			Vozilo p = q.remove();
			synchronized(p) {
				p.notify();
			}
			
			return true;
		}
		
		return false;
	}
}
