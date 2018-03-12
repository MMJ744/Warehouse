package com.rp25.routePlanning;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.PriorityQueue;
import java.util.Queue;

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
	
	public Queue<Point> search() {
		while (true) {
			Node current = openList.poll();
			if (current == null) break; //signifies the end of the open list.
			
			if (current.isGoal()) {
				logger.debug("Current Node: " + current.getCoordinate());
				Queue<Point> route = new Queue<Point>();
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
	
	private boolean checkLoop(Node node) { 		// This may be broken now were using a queue sorry -Matty
		Queue<Point> history = new Queue<Point>();
		history = traceRoute(node);
		
		//Collections.sort(history, new NodeComparatorPoint());
		
		for (int i = 0; (i < history.size() - 1); i++) {
			if (history.pop().equals(history.pop())) return true;
		}
		return false;
	}
	
	private boolean checkOpen(Node node) {
		Iterator<Node> iterator = openList.iterator();
		while(iterator.hasNext()) {
			Node openNode = iterator.next();
			if (openNode.getCoordinate().equals(node.getCoordinate())) {
				if (openNode.getF() < node.getF()) return true;
			}
		}
		return false;
	}
	
	private boolean checkClosed(Node node) {
		for (int i = 0; i < closedList.size(); i++) {
			Node closedNode = closedList.get(i);
			if (closedNode.getCoordinate().equals(node.getCoordinate())) {
				if (closedNode.getF() < node.getF()) return true;
			}
		}
		return false;
	}
	
	private Queue<Point> traceRoute(Node node) {
		Node parent = node.getParent();
		Queue<Point> route = new Queue<Point>();
		if (!(parent == null)) route = traceRoute(parent);
		route.push(node.getCoordinate());
		return route;
	}
}
