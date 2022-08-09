package help;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import elements.figura.*;

public class Help {
	private Random rand = new Random();
	private List<Color> flagColor = new ArrayList<Color>();
	
	public Color getRandomColor() {
		Color color;
		int value = -1;
		
		do {
			value = rand.nextInt(4) + 1;
			if (value == 1)
				color = Color.RED;
			else if (value == 2)
				color = Color.YELLOW;
			else if (value == 3)
				color = Color.GREEN;
			else
				color = Color.BLUE;
		}while(flagColor.contains(color));
		
		flagColor.add(color);

		return color;
	}
	
	public Figura getRandomFigura(Color color) {
		int value = rand.nextInt(3) + 1;
		
		if (value == 1)
			return new LebdecaFigura(color);
		else if (value == 2)
			return new NormalnaFigura(color);
		
		return new SuperBrzaFigura(color);
	}
	
	public List<Integer> getRandomPositionOfPlay(int playerNum) {
		List <Integer> arr = new ArrayList<Integer>();
		
		for (int i=0;i<playerNum;i++) {
			int value = 0;
			do {
				value = rand.nextInt(playerNum);
				if (!arr.contains(value)) {
					arr.add(value);
					break;
				}
			}while (true);
		}
		
		return arr;
	}
	
	public String getExactFigura(Figura figura) {
		if (figura instanceof LebdecaFigura)
			return "LebdecaFigura";
		else if (figura instanceof NormalnaFigura)
			return "NormalnaFigura";
		
		return "SuperBrzaFigura";
	}
	
	public String getColorName(Color color) {
		if (color.equals(Color.RED))
			return "RED";
		else if (color.equals(Color.BLUE))
			return "BLUE";
		else if (color.equals(Color.GREEN))
			return "GREEN";
		
		return "YELLOW";
	}
	
	/*
	 * *************************************************************
	 * RESETING VALUES *********************************************
	 * *************************************************************
	 * */
	
	public void resetFlagColor() {
		flagColor.clear();
	}
}
