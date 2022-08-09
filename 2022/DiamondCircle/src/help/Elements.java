package help;

import java.awt.Color;
import java.util.*;

import elements.GameTimeController;
import elements.KartaTimeController;
import elements.PutanjaKretanja;
import elements.RedoslijedKretanja;
import elements.figura.DuhFigura;
import elements.figura.Figura;
import elements.igrac.Igrac;
import elements.karta.Karta;
import elements.karta.Spil;
import forms.Other;

public class Elements {
	public static final int MAX_ROW = 10;
	public static final int MAX_COLUMN = 10;
	public static final int MIN_ROW = 7;
	public static final int MIN_COLUMN = 7;
	public static final int MAX_PLAYERS = 4;
	public static final int MIN_PLAYERS = 2;
	public static int currRow = -1;
	public static int currCol = -1;
	public static final int NUMBER_OF_FIGURA = 4;
	
	public static Object[][] map = null;
	public static int[][] mapNumber = null;
	public static List<Igrac> arrIgrac = new ArrayList<Igrac>();
	public static List<Integer> arrPosition = new ArrayList<Integer>();
	
	public static FileManagement fileManagement = new FileManagement();
	public static Help help = new Help();
	public static RedoslijedKretanja redoslijed = null;
	public static PutanjaKretanja putanja = null;
	public static Spil spil = null;
	public static GameTimeController gtc = null;
	public static DuhFigura duhFigura = null;
	public static Karta karta = null;
	public static KartaTimeController kc = null;
	
	public static int flagGame = 0;
	
	public static void init(int row, int col, int playerNum) {
		arrPosition.clear();
		arrIgrac.clear();
		help.resetFlagColor();

		map = new Object[row][col];
		mapNumber = new int[row][col];
		
		int br = 0;
		for (int i=0;i<row;i++) {
			for (int j=0;j<col;j++) {
				map[i][j] = null; 
				mapNumber[i][j] = ++br;
			}
		}
		
		char uniqueName = 'A';
		for (int i=0;i<playerNum;i++) {
			Figura []figure = new Figura[NUMBER_OF_FIGURA];
			Color color = help.getRandomColor();
			
			for (int j=0;j<4;j++)
				figure[j] = help.getRandomFigura(color);
			
			arrIgrac.add(new Igrac("PLAYER_" + uniqueName, figure));
			uniqueName++;
			
			currRow = row;
			currCol = col;
		}
		
		//random redoslijed igranja
		arrPosition = help.getRandomPositionOfPlay(playerNum);
		
		//put koji koriste sve figure svih igraca pri kretanju do nekog cilja
		//nije receno da ne smijemo unaprijed pronaci taj put
		putanja = new PutanjaKretanja(Elements.currRow, Elements.currCol);
		
		//odrzava redolsijed kretanja npr. igrac igrac jedan pa onda igrac 2 itd...
		redoslijed = new RedoslijedKretanja();
		
		spil = new Spil();
		
		duhFigura = new DuhFigura(Elements.currRow * Elements.currCol);
		
		kc = new KartaTimeController();
		
		Other.setListModel();
		
		gtc = new GameTimeController();
		gtc.start();

		redoslijed.start();
		
		kc.start();
	}
}
