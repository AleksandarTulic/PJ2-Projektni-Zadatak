package elements.karta;

import java.util.*;
import java.io.*;

public class Spil {
	private static final String IMAGES_PATH = System.getProperty("user.dir") + File.separator + "images";
	private static final int NUM_CARDS = 52;
	private static final int MAX_NUM_FIELDS_CROSS = 12;
	
	private List<Karta> arrKarta = new ArrayList<Karta>();
	private Random rand = new Random();
	
	public Spil() {
		File f = new File(IMAGES_PATH);
		
		try {
			File images[] = f.listFiles();
			
			int br1 = 0;
			int br2 = 1;
			for (int i=0;i<NUM_CARDS;i++) {
				if (rand.nextInt(100) < 90)
					arrKarta.add(new ObicnaKarta(images[br1++].getPath(), br2++));
				else
					arrKarta.add(new SpecijalnaKarta(images[br1++].getPath(), rand.nextInt(4) + 1));
				
				if (br1 == images.length)
					br1 = 0;
				
				if (br2 == MAX_NUM_FIELDS_CROSS)
					br2 = 1;
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public Karta getRandomKarta() {
		int value = rand.nextInt(arrKarta.size());
		
		return arrKarta.get(value);
	}
}
