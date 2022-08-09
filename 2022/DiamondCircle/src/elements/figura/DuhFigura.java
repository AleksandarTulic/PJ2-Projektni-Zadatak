package elements.figura;

import java.util.List;

import forms.Other;
import help.Elements;
import help.Pair;
import java.util.*;

public class DuhFigura extends Thread{
	private static final int MSECONDS_SLEEP = 5000;
	private boolean flag = true;
	private int numDiamond = 0;
	private Random rand = new Random();
	
	public DuhFigura(int dimension) {
		numDiamond = rand.nextInt(dimension - 1) + 2;
	}
	
	@Override
	public void run() {
		List <Pair> arrPutanja = Elements.putanja.getArrPutanja();
	
		try {
			do {
				Thread.sleep(MSECONDS_SLEEP);
				
				int value = rand.nextInt(arrPutanja.size());
				
				Pair pair = arrPutanja.get(value);
				
				synchronized(Elements.map) {
					Other.labelDijamant[pair.getX()][pair.getY()].setText((Integer.parseInt(Other.labelDijamant[pair.getX()][pair.getY()].getText()) + 1) + "");
				}
				
				numDiamond--;
			}while(flag && numDiamond > 0);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void setFlag(boolean flag) {
		this.flag = flag;
	}
}
