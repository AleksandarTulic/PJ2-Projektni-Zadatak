package elements.drumskiSaobracaj;

import controller.KompozicijaController;
import controller.MovingController;
import controller.VozilaController;
import helpFunctions.Pair;
import initialization.init;

import java.awt.Color;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.logging.Level;

import initialization.ColorController;

/**
 * @author Aleksandar Tulic
 * @version 1.0
 * @since 2021-07-28
 * */

public class Vozilo extends Thread {
	private String model;
	private String marka;
	private int godiste;
	private int brzina;
	
	//njegova trenutna pozicija
	private int x = -1;
	private int y = -1;
	
	private Vozilo p = null;
	
	//pamtimo istoriju odgovarajuceg auta --- na kojim sve poljima je on bio
	private ArrayList<Pair> history = new ArrayList<Pair>();
	
	public Vozilo(String model, String marka, int godiste, int brzina) {
		this.model = model;
		this.marka = marka;
		this.godiste = godiste;
		this.brzina = brzina;
	}
	
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	public String getMarka() {
		return marka;
	}
	public void setMarka(String marka) {
		this.marka = marka;
	}
	public int getGodiste() {
		return godiste;
	}
	public void setGodiste(int godiste) {
		this.godiste = godiste;
	}
	public int getBrzina() {
		return brzina;
	}
	public void setBrzina(int brzina) {
		this.brzina = brzina;
	}
	
	public Vozilo getP() {
		return p;
	}
	
	public void setP(Vozilo p) {
		this.p = p;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public void setX(int x) {
		this.x = x;
	}
	
	public void setY(int y) {
		this.y = y;
	}
	
	@Override
	public void run() {
		synchronized(this) {
			if ( init.matrix[x][y] != null ) {
				try {
					VozilaController.svc.get(String.valueOf(x) + " " + String.valueOf(y)).addVozilo(this);
					wait();
				} catch (Exception e) {
					StringWriter sw = new StringWriter();
		            e.printStackTrace(new PrintWriter(sw));
		            String mess = sw.toString();
					String message = "\nMessage: " + mess + "\n";
					init.log.setValue(Level.INFO, message);
					init.log.publish();
					init.log.flush();
				}
			}
				
			init.matrix[x][y] = this;
			history.add(new Pair(x, y));
			
			ColorController.changeBorderColor(x, y, Color.red);
		}
		
		//prethodna pozicija
		Pair pre = MovingController.previouslyForBeginningVehicle(x, y);
		
		boolean flagZastao = false;
		while (true) {
			try {
				sleep((long) (brzina));
			}catch (Exception e) {
				StringWriter sw = new StringWriter();
	            e.printStackTrace(new PrintWriter(sw));
	            String mess = sw.toString();
				String message = "\nMessage: " + mess + "\n";
				init.log.setValue(Level.INFO, message);
				init.log.publish();
				init.log.flush();
			}
			
			Pair pair;
			
			if ( flagZastao ) {
				flagZastao = false;
				pair = new Pair(x, y);
			}else {
			    pair = MovingController.nextFieldVozilo(pre.getX(), pre.getY(), x, y);
				pre.setX(x);
				pre.setY(y);
			}
			
			//pozicija na koju zelimo da idemo
			x = pair.getX();
			y = pair.getY();
			
			synchronized(this) {
				//kada je kraj kretanja vozila
				if (x == -1 && y == -1) {
					init.matrix[pre.getX()][pre.getY()] = null;
					ColorController.changeBackColor(pre.getX(), pre.getY());
					
					if ( p != null && !flagZastao ) {
						synchronized(p) {
							p.notify();
							p = null;
						}
					}
					
					return;
				}
				
				if ( init.matrix[x][y] instanceof Vozilo ) {
					Vozilo ref = (Vozilo)(init.matrix[x][y]);

					//smanjujemo brzinu kako ne bi opet doslo zaustavljanja
					brzina = Math.max(brzina, ref.brzina + 100);
					
					try {
						//vozilo ispred nas zbog koga idemo u wait pamti nas kako bi nas obavjestilo kada se ono pomakne tj sljedeca pozicija je slobodna
						ref.p = this;
						wait();
						//moram da zapamtim da nisam se pomjerio unaprijed
						flagZastao = true;
					}catch (Exception e) {
						StringWriter sw = new StringWriter();
			            e.printStackTrace(new PrintWriter(sw));
			            String mess = sw.toString();
						String message = "\nMessage: " + mess + "\n";
						init.log.setValue(Level.INFO, message);
						init.log.publish();
						init.log.flush();
					}
				}else {
					//vrsim provjeru da li pozicija na koju zelim da idem je pruzni prelaz
					if ( init.col[x][y] == Color.black ) {
						int i = 0;
						//pronazalim poziciju pruznog prelaza u static varijabli pp iz kompozicijacontroller
						for (;i<KompozicijaController.pp.size();i++) {
							Pair pos1 = KompozicijaController.pp.get(i).getPos1();
							Pair pos2 = KompozicijaController.pp.get(i).getPos2();
							if ( (x == pos1.getX() && y == pos1.getY()) || (x == pos2.getX() && y == pos2.getY()) ) {
								break;
							}
						}
						
						//ukoliko postoji ttaj pruzni prelaz onda obavjestavamo da zapamti trenutno vozilo
						//kako bi kasnije mu javio da moze da nastavi sa kretanjem
						if (  i < KompozicijaController.pp.size() ) {
							if ( KompozicijaController.pp.get(i).getFlag() > 0 ) {
								KompozicijaController.pp.get(i).addVozilo(this);
								try {
									wait();
								} catch (InterruptedException e) {
									e.printStackTrace();
								}
							}
						}
					}

					//prethodna pozicija slobodna
					init.matrix[pre.getX()][pre.getY()] = null;	

					//trenutna pozicija zauzeta
					init.matrix[x][y] = this;

					//postavljamo boju BORDER-a kako bi naznacili prelazak sa jedne pozicije na drugu
					ColorController.changeBackColor(pre.getX(), pre.getY());
					ColorController.changeBorderColor(x, y, Color.red);
					
					//dodajemo novu poziciju nakoju smo presli
					history.add(new Pair(x, y));
				}
			}
			
			//provjeravamo da li je neko vozilo zbog nas bilo blokirano(islo prebrzo pa naislo na nase zauzeto polje)
			//ukoliko jeste onda ga obavjestavamo da moze da nastavi kretanje naravno sa brzinom umanjenom(treba drzati distancu pri voznji +100 milisekundi)
			if ( p != null && !flagZastao ) {
				synchronized(p) {
					p.notify();
					p = null;
				}
			}
			
			//ukoliko je na drugom polju onda je prvo slobodno jer je s'njega tek presao
			//moze da, da obavjestenje StartVoziloController da pusti sljedece vozilo koje je cekalo
			if ( history.size() == 2 ) {
				VozilaController.svc.get(String.valueOf(history.get(0).getX()) + " " + String.valueOf(history.get(0).getY())).notifyVOzilo();
			}
		}
	}
	
	@Override
	public String toString() {
		return "\nModel: " + model + "\nMarka: " + marka + "\nGodiste: " + godiste + "\nBrzina: " + brzina + "\n";
	}
};