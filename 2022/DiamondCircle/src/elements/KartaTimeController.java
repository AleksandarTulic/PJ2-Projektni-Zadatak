package elements;

import java.util.List;

import elements.figura.Figura;
import forms.*;
import help.Elements;
import help.Pair;

public class KartaTimeController extends Thread{
	private boolean flag = true;
	private int multiply = 1;
	private int currLocation = 0;
	private Figura figura = null;
	
	@Override
	public void run() {
		List <Pair> arrPutanja = Elements.putanja.getArrPutanja();
		
		do {
			if (!flag)
				break;
			
			try {
				Thread.sleep(1000);
			}catch (Exception e) {
				e.printStackTrace();
			}
			
			Other.changeImageIcon(Elements.karta,  figura, 
					Elements.mapNumber[arrPutanja.get(currLocation == 0 ? 0 : currLocation - 1).getX()][arrPutanja.get(currLocation == 0 ? 0 : currLocation - 1).getY()], 
					Elements.mapNumber[arrPutanja.get(Math.min(arrPutanja.size()-1, currLocation + Elements.karta.getNum() * multiply - 1)).getX()][arrPutanja.get(Math.min(arrPutanja.size()-1, currLocation + Elements.karta.getNum() * multiply - 1)).getY()]);
		}while(true);
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}
	
	public void setFigura(Figura figura) {
		this.figura = figura;
	}
	
	public void setMultiply(int multiply) {
		this.multiply = multiply;
	}
	
	public void setCurrLocation(int currLocation) {
		this.currLocation = currLocation;
	}
}
