package elements;

import elements.figura.Figura;
import elements.igrac.Igrac;
import forms.ShowMessage;
import help.Elements;

public class RedoslijedKretanja extends Thread{
	private int curr = 0;
	private long start = 0;
	private boolean flagChange = false;
	
	@Override
	public void run() {
		curr = 0;
		start = System.currentTimeMillis();
		
		do {
			flagChange = false;
			int first = -1;
			Figura figure[] = Elements.arrIgrac.get(curr).getFigure();
			for (int i=0;i<figure.length;i++) {
				if (!figure[i].getFlagEnd()) {
					first = i;
					break;
				}
			}
			
			if (first == -1) {
				curr++;
			}else {
				if (figure[first].getFlagStart()) {
					synchronized(figure[first]) {
						figure[first].notify();
					}
				}else {
					figure[first].start();
				}
				
				try {
					synchronized(this) {
						wait();
					}
				}catch (Exception e) {
					e.printStackTrace();
				}
				
				if (!flagChange)
					curr++;
			}
			
			if (curr >= Elements.arrIgrac.size())
				curr = 0;
		}while (check());
		
		Elements.gtc.setFlag(false);
		Elements.fileManagement.writeResultToFile(Elements.arrIgrac, System.currentTimeMillis() - start);
		Elements.duhFigura.setFlag(false);
		Elements.kc.setFlag(false);
		Elements.flagGame = 0;
		@SuppressWarnings("unused")
		ShowMessage showMessage = new ShowMessage("Igra zavrsena.");
	}
	
	private boolean check() {
		for (Igrac i : Elements.arrIgrac)
			for (Figura j : i.getFigure())
				if (!j.getFlagEnd())
					return true;
		
		return false;
	}
	
	public void setFlagChange(boolean flagChange) {
		this.flagChange = flagChange;
	}
}
