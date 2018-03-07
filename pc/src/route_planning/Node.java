package route_planning;

import java.awt.Point;

public class Node {
	private double f;
	private double h;
	private double g;
	private Node parent;
	Point coordinate;
	Point goal;
	
	Node (Point c, Point goal) {
		parent = null;
		coordinate = c;
		this.goal = goal;
		g = 0;
		h = heuristic();
		f = g + h;
		
	}
	
	Node (Point c, Point goal, Node p) {
		parent = p;
		coordinate = c;
		this.goal = goal;
		g = parent.getG() + 1;
		h = heuristic();
		f = g + h;
	}
	
	public Node getParent() {
		return parent;
	}
	
	public Point getCoordinate() {
		return this.coordinate;
	}
	
	public double getG() {
		return g;
	}

	
	public boolean isGoal() {
		if (coordinate.equals(goal)) return true;
		else return false;
	}
	
	private double heuristic() {
		double x = Math.abs(goal.getX() - coordinate.getX());
		double y = Math.abs(goal.getY() - coordinate.getY());
		return (x + y);
	}
}
