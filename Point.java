
public class Point {

	public int x = 0;
	public int y = 0;
	public double n;
	
	public Point () {
		
	}
	
	public Point (int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public Point (int x, int y, int n) {
		this.x = x;
		this.y = y;
		this.n = n;
	}
	
	@Override
	public boolean equals (Object other) {
		if (this.x == ((Point)other).x && this.y == ((Point)other).y) {
			return true;
		} else {
			return false;
		}
	}
	
}
