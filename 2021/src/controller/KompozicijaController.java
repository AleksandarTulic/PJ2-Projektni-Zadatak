package controller;

import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.Level;

import elements.mapa.PruzniPrelaz;
import elements.mapa.Sekcija;
import elements.mapa.Stanica;
import elements.zeljeznickiSaobracaj.*;
import enumarations.tipLokomotive;
import enumarations.vrstaPogona;
import helpFunctions.Pair;
import initialization.init;

/**
 * @author Aleksandar Tulic
 * @version 1.0
 * @since 2021-07-28
 * 
 * <h1>KompozicijaController</h1>
 * U ovom dijelu vrsimo kreiranje kompozicija. Takodje vrsimo inicijalizaciju varijabli koji nam pomazu pri implementaciji samog kretanja. 
 * */

public class KompozicijaController extends Thread {
	public static int brojTrazenogFajla = 1;
	
	/**
	 * <h3>kompozicija</h3>
	 * Pamtim sve kompozicije koje su kreirane kako bih mogao u funkciji PRIKAZ da pokazem polja kroz koja je kompozicija prosla.
	 * */
	public static ArrayList<Kompozicija> kompozicija = new ArrayList<Kompozicija>();
	
	/**
	 * <h3>stanica</h3>
	 * Pamtim sve stanice koje su kreirane(ima ih pet). Jedan od glavnih razloga jeste da dodijelimo kompoziciju nekoj stanici koja onda njega pusta da se krece u odgovarajucem smjeru i onda nema vise direktne komunikacije.
	 * */
	public static Map <Character, Stanica> stanica = new HashMap<Character, Stanica>();
	
	/**
	 * <h3>pp</h3>
	 * Pamtim sve pruzne prelaze koji postoje. Glavni razlog jeste kako bi vozilo moglo da pronadje dogovarajuci Pruzni prelaz kako bi on kasnije se obavjestio od PP da moze da nastavi sa kretanjem.
	 * */
	public static ArrayList <PruzniPrelaz> pp = new ArrayList<PruzniPrelaz>();
	
	@Override
	public void run() {
		PruzniPrelaz pp1 = new PruzniPrelaz(new Pair(21, 2), new Pair(20, 2));
		PruzniPrelaz pp2 = new PruzniPrelaz(new Pair(-1, -1), new Pair(-1, -1));
		PruzniPrelaz pp3 = new PruzniPrelaz(new Pair(6, 13), new Pair(6, 14));
		PruzniPrelaz pp4 = new PruzniPrelaz(new Pair(21, 26), new Pair(20, 26));
		
		stanica.put('A', new Stanica('A', 500, new Pair[] {new Pair(27, 1), new Pair(27, 2), new Pair(28, 1), new Pair(28, 2)}));
		stanica.put('B', new Stanica('B', 500, new Pair[] {new Pair(5, 6), new Pair(6, 6), new Pair(5, 7), new Pair(6, 7)}));
		stanica.put('C', new Stanica('C', 500, new Pair[] {new Pair(12, 19), new Pair(12, 20), new Pair(13, 19), new Pair(13, 20)}));
		stanica.put('D', new Stanica('D', 500, new Pair[] {new Pair(1, 26), new Pair(1, 27), new Pair(2, 26), new Pair(2, 27)}));
		stanica.put('E', new Stanica('E', 500, new Pair[] {new Pair(25, 25), new Pair(25, 26), new Pair(26, 25), new Pair(26, 26)}));
		
		//KREIRAMO SVE PRUZNE PRELAZE
		pp.add(pp1);
		pp.add(pp2);
		pp.add(pp3);
		pp.add(pp4);
		
		//POPUNJAVAMO SEKECIJE
		Stanica.sekcija.add(new Sekcija('A', 'B', new Pair(26, 2), new Pair(6, 5), pp1));
		Stanica.sekcija.add(new Sekcija('B', 'A', new Pair(6, 5), new Pair(26, 2), pp1));
		
		Stanica.sekcija.add(new Sekcija('A', 'E', new Pair(29, 2), new Pair(25, 27), pp2));
		Stanica.sekcija.add(new Sekcija('E', 'A', new Pair(25, 27), new Pair(29, 2), pp2));
		
		Stanica.sekcija.add(new Sekcija('B', 'C', new Pair(6, 8), new Pair(11, 19), pp3));
		Stanica.sekcija.add(new Sekcija('C', 'B', new Pair(11, 19), new Pair(6, 8), pp3));
		
		Stanica.sekcija.add(new Sekcija('C', 'D', new Pair(12, 21), new Pair(1, 25), pp2));
		Stanica.sekcija.add(new Sekcija('D', 'C', new Pair(1, 25), new Pair(12, 21), pp2));
		
		Stanica.sekcija.add(new Sekcija('C', 'E', new Pair(14, 20), new Pair(24, 26), pp4));
		Stanica.sekcija.add(new Sekcija('E', 'C', new Pair(24, 26), new Pair(14, 20), pp4));
		
		for (int i=0;i<Stanica.sekcija.size();i++) {
			Stanica.flagSekcija.put(i, 0);
		}
		
		stanica.get('A').start();
		stanica.get('B').start();
		stanica.get('C').start();
		stanica.get('D').start();
		stanica.get('E').start();
		
		while (true) {
			try {
			File f = new File(System.getProperty("user.dir") + File.separator + "vozovi" + File.separator + brojTrazenogFajla + ".txt");
			
			if ( f.exists() ) {
				Scanner scan = new Scanner(f);

				//brzina s'kojom se krece kompozicija
				int brzina = 0;
					
				//broj lokomotiva
				int brojL = 0;
					
				//linija kretanja
				char []linija = null;
					
				//broj vagona
				int brojV = 0;
					
				//pamtimo kreiranje vagone i lokomotive koje kasnije koristimo za kreiranje kompozicije
				ArrayList <Lokomotiva> lokomotive = new ArrayList<Lokomotiva>();
				ArrayList <Vagon> vagoni = new ArrayList<Vagon>();
				//pamtim koji je tip na kojem mjestu u nizu, da li V ili L ----- dodatno pogledati kompoziciju konstruktor
				ArrayList <Boolean> tip = new ArrayList<Boolean>();
					
				//pamtimo sve tipove vagona kako bi mi olaksalo provjeru kompatibilnosti
				ArrayList <String> remVagon = new ArrayList<String>();
					
				//pamtim red citanja
				int br = 0;
					
				//dobaijamo informacije za kompoziciju
				while ( scan.hasNextLine() ) {
					String []line = scan.nextLine().split(" ");
						
					//prva linija u fajlu
					if ( br == 0 ) {
						brojL = Integer.parseInt(line[0]);
						brojV = Integer.parseInt(line[1]);
						brzina = (int)(Double.parseDouble(line[2]) * 1000);
							
						linija = new char[line.length - 3];
						for (int i=0;i<linija.length;i++) {
							linija[i] = line[i + 3].charAt(0);
						}	
					//druga linija u fajlu
					}else if (br == 1) {
						for (int i=0;i<line.length;i++) {
							//V = Vagon L = lokomotiva
							tip.add(line[i].equals("V") ? false : true);
						}
					//sve ostale na kojima se nalaze ili vagoni ili vozovi
					}else {
						if ( lokomotive.size() < brojL  ) {
							lokomotive.add(createLokomotiva(line));
						}else if ( vagoni.size() < brojV ) {
							vagoni.add(createVagon(line));
							remVagon.add(line[0]);
						}
					}
						
						br++;
				}
					
				scan.close();

				//provjeravam da li kompozicija kompatibilna
				if ( checkKompozicija(linija, brzina, brojL, lokomotive, remVagon) ) {
					//kreiranje kompozicije
					Kompozicija buffer = new Kompozicija(brzina, linija, lokomotive, vagoni, tip);
					kompozicija.add(buffer);
						
					switch(linija[0]) {
						case ('A'):
							stanica.get('A').addKompozicija(buffer);
							break;
						case ('B'):
							stanica.get('B').addKompozicija(buffer);
							break;
						case ('C'):
							stanica.get('C').addKompozicija(buffer);
							break;
						case ('D'):
							stanica.get('D').addKompozicija(buffer);
							break;
						case ('E'):
							stanica.get('E').addKompozicija(buffer);
							break;
					}
				}
				
				brojTrazenogFajla++;
			}
			
			sleep(2000);
			
			}catch (Exception e) {
				StringWriter sw = new StringWriter();
	            e.printStackTrace(new PrintWriter(sw));
	            String mess = sw.toString();
				String message = "\nMessage: " + mess + "\n";
				//SEVERE JER PRETHODNO VRSIMO CITANJE IZ FAJLOVA
				init.log.setValue(Level.SEVERE, message);
				init.log.publish();
				init.log.flush();
			}
		}
	}
	
	public Lokomotiva createLokomotiva(String []line) {
		return new Lokomotiva(getTipLokomotive(line[0]), getVrstaPogona(line[1]), Integer.parseInt(line[2]), line[3]);
	}
	
	/**
	 * <h3>createVagon</h3>
	 * Vrsimo kreiranje vagona na osnovu datih podataka. 
	 * @param line - linija za reprezentaciju jednog vagona
	 * @return Vagon - varaca nam objekat tipa vagon
	 * */
	public Vagon createVagon(String []line) {
		switch(line[0]) {
			case ("sjediste"):
				return new SjedisteVagon(Double.parseDouble(line[1]), line[2], Integer.parseInt(line[3]));
			case ("teretni"):
				return new TeretniVagon(Double.parseDouble(line[1]), line[2], Integer.parseInt(line[3]));
			case ("p_namjene"):
				return new ZaPosebneNamjeneVagon(Double.parseDouble(line[1]), line[2]);
			case ("spavanje"):
				return new SpavanjeVagon(Double.parseDouble(line[1]), line[2]);
			case ("lezaj"):
				return new LezajVagon(Double.parseDouble(line[1]), line[2], Integer.parseInt(line[3]));
			case ("restoran"):
				String opis = "";
				for (int i=3;i<line.length;i++) {
					opis+=line[i];
				}
				
				return new RestoranVagon(Double.parseDouble(line[1]), line[2], opis);
			default:
				return null;
		}
	}
	
	/**
	 * <h3>getVrstaPogona</h3>
	 * Pronalazim koje je vrste pogoga neka lokomotiva. 
	 * @param a - string koji reprezentuje tip Lokomotive
	 * @return vrstaPogona - vraca vrstu pogona kao enumeraciju
	 * */
	public vrstaPogona getVrstaPogona(String a) {
		switch(a) {
			case ("elektricni"):
				return vrstaPogona.elektricni;
			case ("dizelski"):
				return vrstaPogona.dizelski;
			case ("parni"):
				return vrstaPogona.parni;
			default:
				return null;
		}
	}
	
	/**
	 * <h3>getTipLokomotive</h3>
	 * Pronalazim kog je tipa lokomotiva ukoliko je dat string te lokomotive. 
	 * @param a - string koji reprezentuje tip lokomotive
	 * @return tipLokomotive - vraca nam tip enumeracije
	 * */
	public tipLokomotive getTipLokomotive(String a) {
		switch(a) {
			case ("putnicke"):
				return tipLokomotive.putnicke;
			case ("teretne"):
				return tipLokomotive.teretne;
			case ("manevarske"):
				return tipLokomotive.manevarske;
			case ("univerzalne"):
				return tipLokomotive.univerzalne;
			default:
				return null;
		}
	}
	
	/**
	 * <h3>flagChar</h3>
	 * Provjeravam da li polazna stanica kompatibilna sa odredisnom stanicom. 
	 * @param a - polazna stanica
	 * @param b - odredisna stanica
	 * @return true - ukoliko je kompatibilno, false - ukoliko nije kompatibilno
	 * */
	public boolean flagChar(char a, char b) {
		if ( a == 'A' && (b == 'E' || b == 'B') ) {
			return true;
		}
		
		if ( a == 'B' && (b == 'A' || b == 'C') ) {
			return true;
		}
		
		if ( a == 'C' && (b == 'B' || b == 'E' || b == 'D') ) {
			return true;
		}
		
		if ( a == 'D' && b == 'C' ) {
			return true;
		}
		
		if ( a == 'E' && (b == 'A' || b == 'C') ) {
			return true;
		}
		
		return false;
	}
	
	/**
	 * <h3>checkKompozicija</h3>
	 * Provjeravam da li su podaci dobri tj kompatibilni. 
	 * @param linija - stanice kroz koje prolazi kompozicija
	 * @param brzina - brzina s'kojom se kompozicija krece
	 * @param brojL - broj lokomotiva
	 * @param lokomotive - kreirane lokomotive
	 * @param remVagon - tipovi vagona zapisani kao string
	 * @return true - ukoliko je kompatibilno, false - ukoliko nije kompatibilno
	 * */
	public boolean checkKompozicija(char []linija, Integer brzina, Integer brojL, ArrayList <Lokomotiva> lokomotive, ArrayList <String> remVagon) {
		if ( linija == null || brzina == null || brojL == null || lokomotive == null || remVagon == null ) {
			return false;
		}
		for (int i=1;i<linija.length;i++) {
			if ( !flagChar(linija[i-1], linija[i]) ) {
				return false;
			}
		}
		
		for (int i=0;i<linija.length;i++) {
			if ( linija[i] < 'A' || linija[i] > 'E' || linija.length < 2 ) {
				return false;
			}
		}
		
		//ekvivalentno 0.5s
		if ( brzina < 500 || brojL < 1 || lokomotive.size() < 1 ) {
			return false;
		}

		Map <String, Boolean> okVagon = new HashMap<String, Boolean>();
		
		//moraju svi biti istog pogona
		for (int i=0;i<lokomotive.size();i++) {
			//pamtimo koje lokomotive su dozvoljene
			switch (lokomotive.get(i).getTip()) {
				case univerzalne:
					okVagon.put("sjediste", true);
					okVagon.put("p_namjene", true);
					okVagon.put("teretni", true);
					okVagon.put("spavanje", true);
					okVagon.put("lezaj", true);
					okVagon.put("restoran", true);
					break;
				case putnicke:
					okVagon.put("sjediste", true);
					okVagon.put("p_namjene", true);
					okVagon.put("spavanje", true);
					okVagon.put("lezaj", true);
					okVagon.put("restoran", true);
					break;
				case teretne:
					okVagon.put("teretni", true);
					break;
				case manevarske:
					okVagon.put("p_namjene", true);
					break;
			}
			
			for (int j=i+1;j<lokomotive.size();j++) {
				if ( lokomotive.get(i).getTip() != lokomotive.get(j).getTip() ) {
					return false;
				}
			}
		}
		
		//prolazimo kroz sve vagone i provjeravamo da li su svi iz dozvoljene grupe
		for (String i : remVagon) {
			if ( !okVagon.containsKey(i) ) {
				return false;
			}
		}

		return true;
	}
}
