package elements;

import help.Pair;
import java.util.*;

public class PutanjaKretanja {
	private List<Pair> arrPutanja = new ArrayList<Pair>();
	
	public PutanjaKretanja(int row, int col) {
		Pair start = new Pair(0, col % 2 != 0 ? col / 2 : col / 2 - 1);
		
		int deg = row % 2 == 0 ? row / 2 - 1 : row / 2;
		
		Pair pre = new Pair(start.getX(), start.getY());
		arrPutanja.add(pre);
		do {
			for (int i=0;i<deg;i++) {
				Pair next = new Pair(pre.getX() + 1, pre.getY() + 1);
				arrPutanja.add(next);
				pre = next;
			}
			
			for (int i=0;i<deg;i++) {
				Pair next = new Pair(pre.getX() + 1, pre.getY() - 1);
				arrPutanja.add(next);
				pre = next;
			}
			
			for (int i=0;i<deg;i++) {
				Pair next = new Pair(pre.getX() - 1, pre.getY() - 1);
				arrPutanja.add(next);
				pre = next;
			}
			
			for (int i=0;i<deg - 1;i++) {
				Pair next = new Pair(pre.getX() - 1, pre.getY() + 1);
				arrPutanja.add(next);
				pre = next;
			}
			
			Pair next = new Pair(pre.getX(), pre.getY()+1);
			arrPutanja.add(next);
			pre = next;
			deg--;
		}while (deg > 0);
	}

	public List<Pair> getArrPutanja(){
		return arrPutanja;
	}
	
	public int getLength() {
		return arrPutanja.size();
	}
	
	public Pair getIndex(int position) {
		if (position >= arrPutanja.size())
			return null;
		
		return arrPutanja.get(position);
	}
}
