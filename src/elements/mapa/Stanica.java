package elements.mapa;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

import elements.zeljeznickiSaobracaj.Kompozicija;
import helpFunctions.Pair;
import initialization.init;

/**
 * @author Aleksandar Tulic
 * @version 1.0
 * @since 2021-07-28
 * */

public class Stanica extends Thread{
	private char naziv;
	
	private Pair []posStanica = new Pair[4];
	
	private ArrayList <Kompozicija> kompozicija = new ArrayList<Kompozicija>();
	
	public static ArrayList <Sekcija> sekcija = new ArrayList<Sekcija>();
	public static Map <Integer, Integer> flagSekcija = new HashMap <Integer, Integer>();

	private int brzina;
	
	public Stanica(char naziv, int brzina, Pair[] posStanica) {
		this.naziv = naziv;
		this.brzina = brzina;
		for (int i=0;i<4;i++) {
			this.posStanica[i] = posStanica[i];
		}
	}
	
	//exception out of bounds ja mislim
	public void removeKompozicija(int i) {
		try {
			kompozicija.remove(i);
		}catch (Exception e) {
			StringWriter sw = new StringWriter();
            e.printStackTrace(new PrintWriter(sw));
            String mess = sw.toString();
			String message = "\nKomentar: Nije moguce ukljuciti aplikaciju!!!\nMessage: " + mess + "\n";
			init.log.setValue(Level.INFO, message);
			init.log.publish();
			init.log.flush();
		}
	}
	
	public void addKompozicija(Kompozicija i) {
		kompozicija.add(i);
	}
	
	public void setBrzina(int brzina) {
		this.brzina = brzina;
	}
	
	public int getBrzina() {
		return brzina;
	}
	
	public void setNaziv(char naziv) {
		this.naziv = naziv;
	}
	
	public char getNaziv() {
		return naziv;
	}
	
	/**
	 * <b>IsInStanica</b>
	 * Provjeravam da li je neka kompozicija stigla do stanice neke.
	 * @param a pozicija na koju kompozicija zeli da ide
	 * @return true ukoliko je kompozicija stigla do stanice a false ukoliko nije
	 * */
	public boolean isInStanica(Pair a) {
		for (Pair i : posStanica) {
			if ( i.equals(a) ) {
				return true;
			}
		}
		
		return false;
	}
	
	@Override
	public void run() {
		while (true) {
			try {
				//provjeravamo da li stanica ima neku kompoziciju koju moze da pusti da se krece
				if ( !kompozicija.isEmpty() ) {
					int re_br = 0;
					//pamtimo kompozicije iz liste koje trebamo da obrisemo jer je stanica pustila te kompozicije da se krecu
					//VAZNO: stanica moze da pusti u istom trenutku vise kompozicija ali se oni moraju kretati sa razlicitih pocetnih tacaka
					ArrayList <Integer> removeElements = new ArrayList<Integer>();
					for (Kompozicija i : kompozicija) {
						boolean flag = false;
						int j_normalno = -1;
						int j_obrnuto = -1;
						for (int j = 0;j<sekcija.size();j++) {
							if ( j_normalno == -1 && sekcija.get(j).getEnd1() == naziv && sekcija.get(j).getEnd2() == i.getCharLinija(i.getpY()) ) {
								//pronalazimo normalnu sekciju na koju pustamo voz
								j_normalno = j;
							}
							
							if ( j_obrnuto == -1 && sekcija.get(j).getEnd1() == i.getCharLinija(i.getpY()) && sekcija.get(j).getEnd2() == naziv ) {
								//pronalazimo obrnutu sekciju na koju pustamo voz jer treba da povecamo flag kako ne bi pustila stanica odredisna voz u suprotnom smjeru
								j_obrnuto = j;
							}
						}
						
						Pair pos = sekcija.get(j_normalno).getPos1();
						
						synchronized(this) {	
							if ( init.matrix[pos.getX()][pos.getY()] == null && flagSekcija.get(j_obrnuto) == 0 ) {
								flagSekcija.put(j_normalno, flagSekcija.get(j_normalno) + 1);
								sekcija.get(j_normalno).getPp().incFlag();
								
								//postavljamo poziciju na koju kompozicija treba da ide
								i.setPos(new Pair(pos));

								//pronasli smo kompoziciju koju pustamo da se krece
								flag = true;
								
								//pustamo voz sa stanice sa minimalnom brzinom od (trenutne brzine, brzine voza koji je vec pusten ali nije stigao do odredista)
								i.setBrzina(Math.max(i.getBrzina(), brzina));
								removeElements.add(re_br-1);
							}
						}
						
						if ( flag ) {
							brzina = i.getBrzina();
							
							synchronized(i) {
								//ukoliko nije prvi put da se pusta onda treba da obavjestimo da moze da se opet nastavi sa kretanjem jer je bilo u wait 
								if ( i.getpX() != 0 ) {
									i.notify();
								//polazi sa prve stanice pa onda koristimo start
								}else {
									i.start();
								}
							}
						}
						
						re_br++;
						
						//ukoliko nema nikakvog voza na sekciji onda opet maksimalna brzina za sljedeci voz je 500s
						if ( flagSekcija.get(j_normalno) == 0 ) {
							brzina = 500;
						}
					}
					
					//brisemo kompozicije koje je stanica pustila da se krecu
					for (int i=removeElements.size() - 1;i>=0;i--) {
						kompozicija.remove(i);
					}
				}
				
				sleep(brzina);
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
	}
	
	public synchronized void kompozicijaStigla(Kompozicija a) {
		//provjeravmo da li je ovo krajnja tacka kompozicije tj zadnja stanica
		if ( a.getpY() != a.getLengthLinija() - 1 ) {
			for (int i=0;i<sekcija.size();i++) {
				if ( sekcija.get(i).getEnd1() == a.getCharLinija(a.getpX()) && sekcija.get(i).getEnd2() == naziv ) {
					//smanjujemo broj kompozicija na sekciji
					flagSekcija.put(i, flagSekcija.get(i) - 1);
					//smanjujemo broj kompozicija na nekoj sekciji gdje ima PP
					sekcija.get(i).getPp().decFlag();
				}
			}
			
			//postavljamo drugaciju pocetnu i odredisnu stanicu
			a.setpX(a.getpX() + 1);
			a.setpY(a.getpY() + 1);
			//dodajemo ovu kompoziciju i trenutnu stanicu, pri cemu ce ona nekad kasnije da je pusti da se krece
			kompozicija.add(a);
		}
	}
}
