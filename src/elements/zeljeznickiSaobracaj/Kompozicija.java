package elements.zeljeznickiSaobracaj;

import java.awt.Color;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.logging.Level;

import controller.KompozicijaController;
import controller.MovingController;
import elements.mapa.Stanica;
import helpFunctions.Pair;
import initialization.ColorController;
import initialization.init;

/**
 * @author Aleksandar Tulic
 * @version 1.0
 * @since 2021-07-28
 * */

public class Kompozicija extends Thread{
	//brzina s'kojom se krece
	private int brzina;
	
	//pozicija u linija - pocetna stanica
	private int pX;

	//pozicija u linija - odredisna stanica
	private int pY;
	//stanice kroz koje kompozicija prolazi
	private char []linija = null;
	
	//pozicija na koju se ide
	private Pair pos = null;
	
	//komponente koje sacinjavaju kompoziciju
	private ArrayList <Object> komponente = new ArrayList<Object>();
	//kog tipa je koji element u nizu komponente
	private ArrayList <Boolean> tip = new ArrayList<Boolean>();
	
	//pamtimo pozicije na kojima se nalazila neka kompozicija
	private ArrayList <Pair> history = new ArrayList<Pair>();
	
	//koliko polja smo od pocetne tacke neke stanice prosli
	private int brPolja = 0;
	
	//ukoliko smo stigli do neke stanice
	private boolean flagStigao = false;

	public Kompozicija(int brzina, char []linija, ArrayList<Lokomotiva> lokomotive, ArrayList <Vagon> vagoni, ArrayList <Boolean> tip){
		this.brzina = brzina;
		this.linija = linija;
		pX = 0;
		pY = 1;
		
		int bL = 0;
		int bV = 0;
		
		try {
			for (int i=0;i<tip.size();i++) {
				komponente.add(tip.get(i) ? lokomotive.get(bL++) : vagoni.get(bV++));
			}
		}catch (Exception e) {
			StringWriter sw = new StringWriter();
            e.printStackTrace(new PrintWriter(sw));
            String mess = sw.toString();
			String message = "\nMessage: " + mess + "\n";
			init.log.setValue(Level.INFO, message);
			init.log.publish();
			init.log.flush();
		}
		
		this.tip = tip;
	}

	public int getBrzina() {
		return brzina;
	}

	public void setBrzina(int brzina) {
		this.brzina = brzina;
	}
	
	public Pair getPos() {
		return pos;
	}
	
	public void setPos(Pair pos) {
		this.pos = pos;
	}
	
	public int getpX() {
		return pX;
	}

	public void setpX(int pX) {
		this.pX = pX;
	}

	public int getpY() {
		return pY;
	}

	public void setpY(int pY) {
		this.pY = pY;
	}
	
	public int getLengthLinija() {
		return linija.length;
	}
	
	public char getCharLinija(int i) {
		return linija[i];
	}
	
	public char startingPos() {
		return linija[0];
	}
	
	public char endPos() {
		return linija[linija.length - 1];
	}

	@Override
	public void run() {
		int br = 0;
		history.add(new Pair(pos));
		
		while (true) {
			//ukoliko smo stigli do neke stanice onda trebamo smanjivati brisanje elemenata u svakom koraku za jedan
			if ( flagStigao ) br++;
			
			//odakle pocinjemo sa brisanje 
			int start;
			//doklen idemo sa brisanjem
			int end;
			if ( flagStigao ) {
				start = history.size() - 1;
				end = start - Math.min(komponente.size() - br, brPolja - br);
			}else {
				start = history.size() - 2;
				end = history.size() - 2 - Math.min(komponente.size() - 1, brPolja - 1);
			}
			
			//vrsimo brisanje pozicija ne kojoj je neka kompozicija bila
			for (int i = start;i >= end && i >= 0;i--) {
				Pair posRes = history.get(i);
				ColorController.changeBackColor(posRes.getX(), posRes.getY());
				init.matrix[posRes.getX()][posRes.getY()] = null;
			}
			
			//ukoliko je kompozicija kompletno usla u stanicu
			synchronized(this) {
				if ( br == komponente.size() ) {
					if ( pY == linija.length - 1 ) {
						for (int i=0;i<Stanica.sekcija.size();i++) {
							if ( Stanica.sekcija.get(i).getEnd1() == linija[pX] && Stanica.sekcija.get(i).getEnd2() == linija[pY] ) {
								Stanica.flagSekcija.put(i, Stanica.flagSekcija.get(i) - 1);
								Stanica.sekcija.get(i).getPp().decFlag();
							}
						}
						return;
					}
					
					KompozicijaController.stanica.get(linija[pY]).kompozicijaStigla(this);
					
					br = 0;
					flagStigao = false;
					brPolja = 0;
					try {
						wait();
						history.add(pos);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
			
			//vrsim pomjeranje kompozicije
			for (int i=br;i<Math.min(komponente.size(), brPolja + 1);i++) {
				Pair posRes = history.get(history.size() - 1 - i + br);
				init.matrix[posRes.getX()][posRes.getY()] = komponente.get(i);

				if ( !tip.get(i) ) {
					ColorController.changeBorderColor(posRes.getX(), posRes.getY(), Color.green);
				}else {
					ColorController.changeBorderColor(posRes.getX(), posRes.getY(), Color.orange);
				}
			}
			
			try {
				sleep(brzina);
			}catch (Exception e) {
				StringWriter sw = new StringWriter();
	            e.printStackTrace(new PrintWriter(sw));
	            String mess = sw.toString();
				String message = "\nMessage: " + mess + "\n";
				init.log.setValue(Level.INFO, message);
				init.log.publish();
				init.log.flush();
			}
			
			//trazim poziciju za kompoziciju ukoliko smo samo na prvom elementu
			if ( brPolja == 0 ) {
				pos = MovingController.nextFieldVoz(-1, -1, pos.getX(), pos.getY());
			//trazim slejdecu poziciju ukoliko nismo na prvom elementu
			}else {
				pos = MovingController.nextFieldVoz(history.get(history.size() - 2).getX(), history.get(history.size() - 2).getY(), pos.getX(), pos.getY());
			}
			
			//da li je stigao u stanicu
			if ( !flagStigao ) {
				flagStigao = KompozicijaController.stanica.get(linija[pY]).isInStanica(pos);
				//pamtimo trenutnu poziciju na koju smo dosl
				
				if ( !flagStigao ) {
					history.add(new Pair(pos));
				}
			}
			
			brPolja++;
		}
	}
}
