package helpFunctions;

public class Pair {
	private int x;
	private int y;
	
	public Pair() {
	}
	
	public Pair(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public Pair(Pair a) {
		x = a.x;
		y = a.y;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	@Override
	public String toString() {
		return "X = " + x + ", Y = " + y;
	}
	
	@Override
	public boolean equals(Object obj) {
		if ( obj == this ) {
			return true;
		}
		
		if ( !(obj instanceof Pair) ) {
			return false;
		}
		
		Pair a = (Pair)obj;
		
		return a.x == x && a.y == y ? true : false;
	}
}
