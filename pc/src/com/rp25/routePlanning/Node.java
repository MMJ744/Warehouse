package com.rp25.routePlanning;

import java.awt.Point;

import com.rp25.routePlanning.Location.STATE;

public class Node {
	private double f;
	private double h;
	private double g;
	private Node parent;
	
	Location location;
	
	Point coordinate;
	Point goal;
	
	//constructor for root node only
	Node (Location location, Point goal) {
		parent = null;
		
		this.location = location;
		this.goal = goal;
		coordinate = location.coordinate;
		
		g = 0;
		h = heuristic();
		f = g + h;
		
	}
	
	//general constructor
	Node (Location location, Point goal, Node p) {
		parent = p;
		
		this.location = location;
		this.goal = goal;
		coordinate = location.coordinate;
		
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
	
	public double getF() {
		return f;
	}
	
	public double getG() {
		return g;
	}
	
	public boolean isGoal() {
		if (coordinate.equals(goal)) return true;
		else return false;
	}
	
	private double heuristic() {
		if(location.state != STATE.EMPTY) return Integer.MAX_VALUE;
		
		double x = Math.abs(goal.getX() - coordinate.getX());
		double y = Math.abs(goal.getY() - coordinate.getY());
		return (x + y);
	}
}
