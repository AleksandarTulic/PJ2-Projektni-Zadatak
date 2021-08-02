package controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import elements.drumskiSaobracaj.Automobil;
import elements.drumskiSaobracaj.Kamion;
import elements.drumskiSaobracaj.Vozilo;
import helpFunctions.Pair;
import initialization.init;

import java.util.Scanner;
import java.util.logging.Level;
import java.util.Random;
import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * @author Aleksandar Tulic
 * @version 1.0
 * @since 2021-07-28
 * */

public class VozilaController extends Thread{
	/**
	 * <b>rem</b>
	 * Pamtimo na X koliko je za koji put maksimalno vozila, dok na Y koliko smo kreirali do sada. 
	 * Pri cemu uvijek krece sa putem 1 pa onda put 2 i onda put 3. 
	 * */
	private Pair []rem = {new Pair(-1, 0), new Pair(-1, 0), new Pair(-1, 0)};
	
	/**
	 * <b>svaVozila</b>
	 * Pamtimo sva vozila koja smo do sada kreirali ukoliko bude potrebno da ispisemo podatke neke o njima(iako nije navedeno u tekstu zadatka). 
	 * */
	public static ArrayList<Vozilo> svaVozila = new ArrayList<Vozilo>();
	
	/**
	 * <b>svc</b>
	 * Pamtimo sve kontrolere koje su zaduzene za pustanje vozila sa na prvu poziciju ako ima vec guzve. 
	 * Pamtimo jer treba vozilo nekako da zna na osnovu pozicije na koju hoce koji kontroler da ga obavjesti. 
	 * */
	public static Map<String, StartVoziloController> svc = new HashMap<String, StartVoziloController>();
	
	@Override
	public void run() {
		svc.put("29 8", new StartVoziloController(new Pair(29, 8)));
		svc.put("21 0", new StartVoziloController(new Pair(21, 0)));
		svc.put("29 14", new StartVoziloController(new Pair(29, 14)));
		svc.put("0 13", new StartVoziloController(new Pair(0, 13)));
		svc.put("29 22", new StartVoziloController(new Pair(29, 22)));
		svc.put("20 29", new StartVoziloController(new Pair(20, 29)));
		
		while (true) {
			try {
				//citamo konfiguracioni fajl
				File f = new File(System.getProperty("user.dir") + File.separator + "conf.txt");
				Scanner scan = new Scanner(f);
				
				int br = 0;
				String path = "";
				int brzina = -1;
				
				while ( scan.hasNextLine() ) {
					String []line = scan.nextLine().split(" ");
					
					int brojVozila = Integer.parseInt(line[1]);
					
					rem[br].setX(Math.max(rem[br].getX(), brojVozila));
					
					if (rem[br].getY() < rem[br].getX()) {
						brzina = Integer.parseInt(line[0]);
						int fileNum = rem[br].getY() + 1;
						path = System.getProperty("user.dir") + File.separator + line[2] + File.separator + fileNum + ".txt";
						rem[br].setY(rem[br].getY() + 1);
						break;
					}else {
						br++;
					}
				}
				
				scan.close();
				Kamion kamion = null;
				Automobil auto = null;
				Vozilo vozilo = null;
				
				int smjer = (new Random()).nextInt(2) + 1;
				f = new File(path);
				
				if ( brzina > 0 ) {
					int brzina_rand = (new Random()).nextInt(brzina) + 1;
					if ( f.exists() ) {
						scan = new Scanner(f);
						
						String []line = scan.nextLine().split(" ");
						
						if ( line[0].equals("Kamion") ) {
							kamion = new Kamion(line[1], line[2], Integer.parseInt(line[3]), brzina_rand, Integer.parseInt(line[4]));
							vozilo = (Vozilo) kamion;
						}else if ( line[0].equals("Automobil") ) {
							auto = new Automobil(line[1], line[2], Integer.parseInt(line[3]), brzina_rand, Integer.parseInt(line[4]));
							vozilo = (Vozilo) auto;
						}
					//ukoliko ne postoji fajl a prethodno je utvrdjeno da treba da se kreira jos vozila onda kreiramo sa fiksnim vrijednostima atributa
					}else {
						auto = new Automobil("BMW", "C3", 2005, brzina_rand, 4);
						vozilo = (Vozilo) auto;
					}
					
					if ( vozilo != null ) {	
						Pair start = MovingController.getStartingPosition(smjer, br + 1);
						vozilo.setX(start.getX());
						vozilo.setY(start.getY());
						
						vozilo.start();
						
						svaVozila.add(vozilo);
					}
					
					scan.close();
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
}
