package route_planning;

public class Node {
	int f;
	int h;
	int g;
	Node parent;
	Coordinate coordinate;
	
	Node (Coordinate c) {
		f = 0;
		h = 0;
		g = 0;
		parent = null;
		coordinate = c;
	}
	
	public void setF (int f) {
		this.f = f;
	}
	
	public void setH (int h) {
		this.h= h;
	}
	
	public void setG (int g) {
		this.g= g;
	}
	
	public void setParent (Node parent) {
		this.parent = parent;
	}
}
