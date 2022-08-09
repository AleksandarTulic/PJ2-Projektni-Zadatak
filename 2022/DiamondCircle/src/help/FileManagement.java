package help;

import java.io.*;
import java.util.*;

import elements.figura.Figura;
import elements.igrac.Igrac;

public class FileManagement {
	private static final String FILE_RESULTS = System.getProperty("user.dir") + File.separator + "rezultat";
	private File[] files = null;
	
	public FileManagement() {
		File f = new File(FILE_RESULTS);
		
		files = f.listFiles();
	}
	
	public int getNumberOfGames() {
		return files.length;
	}
	
	public void writeResultToFile(List <Igrac> arr, long timePlaying) {
		try {
			PrintWriter out = new PrintWriter(FILE_RESULTS + File.separator + "IGRA_" + System.currentTimeMillis() + ".txt");
			
			for (int i=0;i<arr.size();i++) {
				out.println(arr.get(i).getIme() + " - Ime Igraca");
				
				Figura []figure = arr.get(i).getFigure();
				for (int j=0;j<figure.length;j++) {
					String colorName = Elements.help.getColorName(figure[j].getColor());
					String part1 = "          Figura " + (j+1) + " (" + Elements.help.getExactFigura(figure[j]) + ", " + colorName + ") - predjeni put ";
					String part2 = "(";
					
					List<Pair> arrPositions = figure[j].getArrPositions();
					
					for (Pair pos : arrPositions)
						part2 += Elements.mapNumber[pos.getX()][pos.getY()] + "-";
					
					if (!"(".equals(part2))
						part2 = part2.substring(0, part2.length() - 1);
					
					List<Pair> arrPutanja = Elements.putanja.getArrPutanja();
					if (arrPositions.size() >= 1 && Elements.mapNumber[arrPutanja.get(arrPutanja.size() - 1).getX()][arrPutanja.get(arrPutanja.size() - 1).getY()] == Elements.mapNumber[arrPositions.get(arrPositions.size() - 1).getX()][arrPositions.get(arrPositions.size() - 1).getY()])
						part2 += ") - stigla do cilja da";
					else
						part2 += ") - stigla do cilja ne";
					
					out.println("        " + part1 + part2);
				}
			}
			
			
			out.print("Ukupno vrijeme trajanja igre: " + (timePlaying/1000.0) + "s");
			out.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public File[] getFiles() {
		return files;
	}
	
	public File getFile(int index) {
		if (index < files.length && index >= 0)
			return files[index];
		
		return null;
	}
	
	@SuppressWarnings("resource")
	public List<String> getAllLines(File f){
		List<String> arr = new ArrayList<String>();
		
		try {
			if (f.exists()) {
				Scanner scan = new Scanner(f);
				
				while (scan.hasNextLine()) {
					arr.add(scan.nextLine());
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return arr;
	}
}
