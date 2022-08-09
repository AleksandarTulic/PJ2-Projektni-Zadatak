package elements.karta;

public class Karta {
	private String imagePath = "";
	private int num = 0;
	
	public Karta(String imagePath, int num) {
		this.imagePath = imagePath;
		this.num = num;
	}
	
	public String getImagePath() {
		return imagePath;
	}
	
	public int getNum() {
		return num;
	}
	
	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}
	
	public void setNum(int num) {
		this.num = num;
	}
	
	@Override
	public String toString() {
		return "Image Path: " + imagePath + ", Number: " + num;
	}
}
