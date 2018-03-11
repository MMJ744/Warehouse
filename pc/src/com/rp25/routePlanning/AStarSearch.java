package com.rp25.routePlanning;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.PriorityQueue;
import org.apache.log4j.Logger;

public class AStarSearch {
	Comparator<Node> comparator = new NodeComparatorF();
	PriorityQueue<Node> openList = new PriorityQueue<Node>(comparator);
	ArrayList<Node> closedList = new ArrayList<Node>();
	Node startNode;
	Point goalState;
	final static Logger logger = Logger.getLogger(AStarSearch.class);
	
	Grid grid;
	
	public AStarSearch (Point c, Point g) {
		grid = new Grid();
		
		startNode = new Node(grid.getLocation(c), g);
		goalState = g;
		openList.add(startNode);
	}
	
	public ArrayList<Point> search() {
		while (true) {
			Node current = openList.poll();
			if (current == null) break; //signifies the end of the open list.
			
			if (current.isGoal()) {
				logger.debug("Current Node: " + current.getCoordinate());
				ArrayList<Point> route = new ArrayList<Point>();
				route = traceRoute(current);
				return route;
			}
			
			Node[] children = generateChildrenFromParent(current);
			for (Node child : children)
				if (!(checkLoop(child) || checkOpen(child) || checkClosed(child)))
					openList.add(child);
			
			closedList.add(current);
		}
		//if this point is reached, the algorithm has been unable to find the route
		//should report this (to warehouse interface?)
		
		return null;
	}
	
	private Node[] generateChildrenFromParent(Node parentNode) {
		Point parent = parentNode.coordinate;
		
		Point[] points = new Point[4];
		points[0] = new Point(parent.x + 1, parent.y); //north
		points[1] = new Point(parent.x, parent.y + 1); //east
		points[2] = new Point(parent.x - 1, parent.y); //south
		points[3] = new Point(parent.x, parent.y - 1); //west
		
		ArrayList<Node> h = new ArrayList<Node>();
		for (Point child : points)
			if (grid.isOnGrid(child) && grid.isEmpty(child))
				h.add(new Node(grid.getLocation(child), parentNode.goal, parentNode));
		
		Node[] children = new Node[h.size()];
		children = h.toArray(children);
		for(int i = 0; i < children.length; i++) {
			logger.debug("Child " + i + " Coordinates: " + children[i]);
		}
		return children;
	}
	
	private boolean checkLoop(Node node) { 		
		ArrayList<Point> history = new ArrayList<Point>();
		history = traceRoute(node);
		
		Collections.sort(history, new NodeComparatorPoint());
		
		for (int i = 0; (i < history.size() - 1); i++) {
			if (history.get(i).equals(history.get(i+1))) return true;
		}
		return false;
	}
	
	private boolean checkOpen(Node node) {
		Iterator<Node> iterator = openList.iterator();
		while(iterator.hasNext()) {
			if (iterator.next().getCoordinate().equals(node.getCoordinate())) return true;
		}
		return false;
	}
	
	private boolean checkClosed(Node node) {
		for (int i = 0; i < closedList.size(); i++) {
			if (closedList.get(i).getCoordinate().equals(node.getCoordinate())) {
				return true;
			}
		}
		return false;
	}
	
	private ArrayList<Point> traceRoute(Node node) {
		Node parent = node.getParent();
		ArrayList<Point> route = new ArrayList<Point>();
		if (!(parent == null)) route = traceRoute(parent);
		route.add(node.getCoordinate());
		return route;
	}
}
