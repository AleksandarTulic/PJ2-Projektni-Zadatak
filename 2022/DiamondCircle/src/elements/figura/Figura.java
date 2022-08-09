package elements.figura;

import java.awt.Color;
import java.util.*;

import elements.karta.Karta;
import elements.karta.SpecijalnaKarta;
import forms.Other;
import help.Elements;
import help.Pair;
import help.Rupa;

public abstract class Figura extends Thread{
	private static int MSECONDS_SLEEP = 1000;
	
	private Color color;
	private int multiply = 1;
	private List<Pair> arrPositions = new ArrayList<Pair>();
	private boolean flagEnd = false;
	private boolean flagStart = false;
	private int numFields = 0;
	private int currLocation = 0;
	private String idFigura;
	private Color colorChange = Color.WHITE;
	private boolean flagStop = false;
	
	public Figura(Color color, String idFigura) {
		this.color = color;
		this.idFigura = idFigura;
	}
	
	public Figura(Color color, int multiply, String idFigura) {
		this.color = color;
		this.multiply = multiply;
		this.idFigura = idFigura;
	}
	
	public Color getColor() {
		return color;
	}
	
	public int getMultiply() {
		return multiply;
	}
	
	public String getIdFigura() {
		return idFigura;
	}
	
	public boolean getFlagEnd() {
		return flagEnd;
	}
	
	public boolean getFlagStart() {
		return flagStart;
	}
	
	public List<Pair> getArrPositions(){
		return arrPositions;
	}
	
	public boolean getFlagStop() {
		return flagStop;
	}
	
	public void setColor(Color color) {
		this.color = color;
	}
	
	public void setIdFigura(String idFigura) {
		this.idFigura = idFigura;
	}
	
	public void setMultiply(int multiply) {
		this.multiply = multiply;
	}
	
	public void setNumFields(int numFields) {
		this.numFields = numFields;
	}
	
	public void setFlagEnd(boolean flagEnd) {
		this.flagEnd = flagEnd;
	}
	
	public void setFlagStart(boolean flagStart) {
		this.flagStart = flagStart;
	}
	
	public void setArrPositions(List <Pair> arrPositions) {
		this.arrPositions = arrPositions;
	}
	
	@Override
	public void run() {
		synchronized(Elements.map) {
			if (Thread.currentThread().getState() == Thread.State.RUNNABLE && Elements.duhFigura.getState() == Thread.State.NEW)
				Elements.duhFigura.start();
		}
		
		flagStart = true;
		
		List <Pair> arrPutanja = Elements.putanja.getArrPutanja();
		int x = -1;
		int y = -1;
		
		Pair pre = null;
		
		do {
			synchronized(Elements.map) {
				Elements.karta = Elements.spil.getRandomKarta();
				
				Elements.kc.setFigura(this);
				Elements.kc.setCurrLocation(currLocation);
				Elements.kc.setMultiply(multiply);
			}
			
			numFields = Elements.karta.getNum();

			/*
				Other.changeImageIcon(karta,  this, 
					Elements.mapNumber[arrPutanja.get(currLocation == 0 ? 0 : currLocation - 1).getX()][arrPutanja.get(currLocation == 0 ? 0 : currLocation - 1).getY()], 
					Elements.mapNumber[arrPutanja.get(Math.min(arrPutanja.size()-1, currLocation + numFields * multiply - 1)).getX()][arrPutanja.get(Math.min(arrPutanja.size()-1, currLocation + numFields * multiply - 1)).getY()]);
			*/
			
			if (Elements.karta instanceof SpecijalnaKarta) {
				numFields = 0;
				
				try {
					Thread.sleep(MSECONDS_SLEEP);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			
			try {
				for (int i=0;i<numFields*multiply && currLocation < arrPutanja.size();i++) {
					if (Elements.flagGame % 2 == 0) {
						flagStop = true;
						
						try {
							synchronized(this) {
								wait();
							}
						}catch (Exception e) {
							e.printStackTrace();
						}
						
						flagStop = false;
					}
					
					if (pre != null){
						if (!colorChange.equals(Color.BLACK))
							Elements.map[pre.getX()][pre.getY()] = null;
						Other.labelTip[pre.getX()][pre.getY()].setText("");
						Other.panelArr[pre.getX()][pre.getY()].setBackground(colorChange);
						colorChange = Color.WHITE;
					}
					
					x = arrPutanja.get(currLocation).getX();
					y = arrPutanja.get(currLocation).getY();
					
					if (Elements.map[x][y] != null) {
						if (Elements.map[x][y] instanceof Rupa) {
							if (this instanceof LebdecaFigura) {
								Other.panelArr[x][y].setBackground(color);
								Other.labelTip[x][y].setText(idFigura);
								colorChange = Color.BLACK;
								
								pre = new Pair(x, y);
								currLocation++;
								i--;
								
								arrPositions.add(pre);
								Thread.sleep(MSECONDS_SLEEP);
							}else {
								currLocation = arrPutanja.size();
								Other.labelTip[x][y].setText("");
								Elements.map[x][y] = null;
								Other.panelArr[x][y].setBackground(colorChange);
								Elements.redoslijed.setFlagChange(true);
								colorChange = Color.WHITE;
								
								Thread.sleep(MSECONDS_SLEEP);
								break;
							}
						}else {
							currLocation++;
							i--;
						}
					}else {
						synchronized(Elements.map) {
							Other.panelArr[x][y].setBackground(color);
							Other.labelTip[x][y].setText(idFigura);
							Elements.map[x][y] = this;
							pre = new Pair(x, y);
							currLocation++;
							
							numFields += Integer.parseInt(Other.labelDijamant[x][y].getText());
							Other.labelDijamant[x][y].setText("0");
							
							arrPositions.add(pre);
							Thread.sleep(MSECONDS_SLEEP);
						}
					}
				}
			}catch (Exception e) {
				e.printStackTrace();
			}
			
			synchronized(Elements.redoslijed) {
				Elements.redoslijed.notify();
			}
			
			//ovo ovde kada radimo moramo sinhronizovati kada redoslijedkretanja poziva neku figuru da se krece
			if (currLocation >= arrPutanja.size()) {
				flagEnd = true;
				
				if (pre != null) {
					if (!colorChange.equals(Color.BLACK))
						Elements.map[pre.getX()][pre.getY()] = null;
					
					Other.labelTip[pre.getX()][pre.getY()].setText("");
					Other.panelArr[pre.getX()][pre.getY()].setBackground(colorChange);
					colorChange = Color.WHITE;
				}
				
				break;
			}
			
			try {
				synchronized(this) {
					wait();
				}
			}catch (Exception e) {
				e.printStackTrace();
			}
			
			//npr bio si u wait i onda se probudis i neka figura je izvukla sp kartu i sada gdje se ti nalazis nalazi se rupa
			//tako da ti onda propadas
			if (pre != null && Elements.map[pre.getX()][pre.getY()] instanceof Rupa) {
				if (!colorChange.equals(Color.BLACK))
					Elements.map[pre.getX()][pre.getY()] = null;
				
				Other.panelArr[pre.getX()][pre.getY()].setBackground(colorChange);
				colorChange = Color.WHITE;
				Other.labelTip[pre.getX()][pre.getY()].setText(idFigura);
				
				try {
					Thread.sleep(MSECONDS_SLEEP);
				}catch (Exception e) {
					e.printStackTrace();
				}
				
				flagEnd = true;
				
				Elements.redoslijed.setFlagChange(true);
				synchronized(Elements.redoslijed) {
					Elements.redoslijed.notify();
				}
				
				break;
			}
		}while(true);
	}
}
