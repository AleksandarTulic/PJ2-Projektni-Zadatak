package controller;

import helpFunctions.Pair;
import initialization.init;

import java.awt.Color;
import java.util.ArrayList;

/**
 * @author Aleksandar Tulic
 * @version 1.0
 * @since 2021-07-28
 * */

public class MovingController {
	
	/**
	 * <b>look</b>
	 * Koristim kako bih odredio 4 susjedne celije. 
	 * */
	public static Pair []look = {new Pair(0, 1), new Pair(1, 0), new Pair(0, -1), new Pair(-1, 0)};

	/**
	 * @param x1 red prethodne pozicije
	 * @param y1 kolona prethodne pozicije
	 * @param x2 red trenutne pozicije
	 * @param y2 kolona trenutne pozicije
	 * @return naredna pozicija na koju vozilo ide.
	 * */
	public static Pair nextFieldVozilo(int x1, int y1, int x2, int y2) {
		ArrayList <Pair> arr = new ArrayList<Pair>();
		Pair ret = null;
		
		for (int i=0;i<4;i++) {
			if ( isCyanOrBlack(x2 + look[i].getX(), y2 + look[i].getY()) && ( x1 != x2 + look[i].getX() || y1 != y2 + look[i].getY() ) ) {
				arr.add(new Pair(x2 + look[i].getX(), y2 + look[i].getY()));
			}
		}
		
		if (x1 == x2) {
			if ( arr.size() == 3 ) {
				int i1 = -1;
				int i2 = -1;
				for (int i=0;i<3;i++) {
					for (int j=i+1;j<3;j++) {
						if ( arr.get(i).getY() == arr.get(j).getY() ) {
							i1 = i;
							i2 = j;
							break;
						}
					}
				}
				
				ret = arr.get(i1).getX() > arr.get(i2).getX() ? arr.get(i1) : arr.get(i2);
			}else {
				for (Pair i : arr) {
					if ( i.getX() == x2 ) {
						ret = i;
						break;
					}
				}
			}
		}else {
			if ( arr.size() == 3 ) {
				int i1 = -1;
				int i2 = -1;
				for (int i=0;i<3;i++) {
					for (int j=i+1;j<3;j++) {
						if ( arr.get(i).getX() == arr.get(j).getX() ) {
							i1 = i;
							i2 = j;
							break;
						}
					}
				}
				
				ret = arr.get(i1).getY() > arr.get(i2).getY() ? arr.get(i1) : arr.get(i2);
			}else {
				for (Pair i : arr) {
					if ( i.getY() == y2 ) {
						ret = i;
						break;
					}
				}
			}
		}
		
		if ( ret == null ) {
			try {
				Pair pos = arr.get(0);
				if ( init.flagKrajVozilo.get(String.valueOf(pos.getX()) + " " + String.valueOf(pos.getY())) ) {
					return new Pair(-1, -1);
				}else {
					return arr.get(0);
				}
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return ret;
	}
	
	/**
	 * @param x1 red prethodne pozicije
	 * @param y1 kolona prethodne pozicije
	 * @param x2 red trenutne pozicije
	 * @param y2 kolona trenutne pozicije
	 * @return naredna pozicija na koju kompozicija ide.
	 * */
	public static Pair nextFieldVoz(int x1, int y1, int x2, int y2) {
		if ( x2 == 25 && y2 == 29 && x1 != -1 && y1 != -1 ) {
			return new Pair(29, 2);
		}
		if ( x2 == 29 && y2 == 2 && x1 == -1 && y1 == -1) {
			return new Pair(25, 29);
		}
		
		if ( x1 == -1 && y1 == -1 ) {
			for (int i=0;i<4;i++) {
				//xUn = x unknown yUn = y unknown
				int xUn = x2 + look[i].getX();
				int yUn = y2 + look[i].getY();
				
				if ( !isGrayOrBlack(xUn, yUn) ) {
					continue;
				}
				
				int br = 0;
				
				if ( isGray(xUn + 1, yUn + 0) ) br++;
				if ( isGray(xUn + 0, yUn + 1) ) br++;
				if ( isGray(xUn + 1, yUn + 1) ) br++;
				if ( isGray(xUn - 1, yUn - 1) ) br++;
				if ( isGray(xUn - 0, yUn - 1) ) br++;
				if ( isGray(xUn - 1, yUn - 0) ) br++;
				if ( isGray(xUn - 1, yUn + 1) ) br++;
				if ( isGray(xUn + 1, yUn - 1) ) br++;
				if ( isGray(xUn + 0, yUn + 0) ) br++;
			
				if ( br == 5 || br == 6 ) {
					x1 = xUn;
					y1 = yUn;
					break;
				}
			}
		}

		for (int i=0;i<4;i++) {
			Pair buffer1 = new Pair(x2 + look[i].getX(), y2 + look[i].getY());
			Pair buffer2 = new Pair(x1, y1);
			if ( isGrayOrBlack(x2 + look[i].getX(), y2 + look[i].getY()) && !buffer1.equals(buffer2) ) {
				return new Pair(x2 + look[i].getX(), y2 + look[i].getY());
			}
		}
		
		return null;
	}
	
	/**
	 * <b>isCyanOrBlack</b>
	 * Provjeravam da li je polje crne ili svijetlo plave boje.
	 * @param x red polja
	 * @param y kolona polja
	 * @return true ukoliko jeste te boje a false ukoliko nije
	 * */
	public static boolean isCyanOrBlack(int x, int y) {
		if ( x < 0 || y < 0 || x > 29 || y > 29 ) return false;

		return init.col[x][y] == Color.cyan || init.col[x][y] == Color.black ? true : false;
	}
	
	public static boolean isGray(int x, int y) {
		if ( x < 0 || y < 0 || x > 29 || y > 29 ) return false;

		return init.col[x][y] == Color.LIGHT_GRAY ? true : false;
	}
	
	/**
	 * <b>isGrayOrBlack</b>
	 * Provjeravam da li je polje crne ili svijetlo sive boje.
	 * @param x red polja
	 * @param y kolona polja
	 * @return true ukoliko jeste te boje a false ukoliko nije
	 * */
	public static boolean isGrayOrBlack(int x, int y) {
		if ( x < 0 || y < 0 || x > 29 || y > 29 ) return false;
		
		return init.col[x][y] == Color.LIGHT_GRAY || init.col[x][y] == Color.black ? true : false;
	}
	
	/**
	 * <b>previouslyForBeginningVehicle</b>
	 * Pronalazimo prethodnu poziciju za vozilo koje se nalazi na prvoj poziciji.
	 * @param x red polja
	 * @param y kolona polja
	 * @return pozicija na koju treba da se postavi na pocetku
	 * */
	public static Pair previouslyForBeginningVehicle(int x, int y) {
		for (Pair i : look) {
			int xNew = x + i.getX();
			int yNew = y + i.getY();
			
			if ( xNew < 0 || yNew < 0 || xNew >= 30 || yNew >= 30 ) {
				return new Pair(xNew, yNew);
			}
		}
		
		return null;
	}
	
	/**
	 * <b>getStartingPosition</b>
	 * Pronalazimo prethodnu poziciju za vozilo koje se nalazi na prvoj poziciji.
	 * @param x red polja
	 * @param y kolona polja
	 * @return pozicija na koju treba da se postavi na pocetku
	 * */
	public static Pair getStartingPosition(int smjer, int put) {
		if ( put == 1 ) {
			return smjer == 1 ? new Pair(29, 8) : new Pair(21, 0);
		}else if ( put == 2 ) {
			return smjer == 1 ? new Pair(29, 14) : new Pair(0, 13);
		}else {
			return smjer == 1 ? new Pair(29, 22) : new Pair(20, 29);
		}
	}
}
