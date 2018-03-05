package route_planning;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;

public class AStarSearch {
	PriorityQueue<Node> openList = new PriorityQueue<Node>();
	ArrayList<Node> closedList = new ArrayList<Node>();
	Node startNode;
	Point goalState;
	
	AStarSearch (Point c, Point g) {
		startNode = new Node(c, g);
		goalState = g;
		openList.add(startNode);
	}
	
	public ArrayList<Point> search() {
		while (true) {
			Node current = openList.poll(); 
			if (current == null) break; //signifies the end of the open list.
			if (current.isGoal()) {
				ArrayList<Point> route = new ArrayList<Point>();
				route = traceRoute(current);
				return route;
			} 
			Node[] children = generateChildren(current);
			for (Node child : children) {
				if (!(checkLoop(child) || checkOpen(child) || checkClosed(child))) {
					openList.add(child);
				}
			}
			closedList.add(current);
		}
		return null;
		//if this point is reached, the algorithm has been unable to find the route
		//should report this (to warehouse interface?)
	}
	
	private Node[] generateChildren(Node current) {
		// calculate the four cardinal points around the point passed in the node
		// check these points are on the board & accessible
		// if so, add to an array
		return null;
	}
	
	private boolean checkLoop(Node node) {
		Comparator<Point> comp = new Comparator<Point>();
		ArrayList<Point> hold = new ArrayList<Point>();
		Point[] history = new Point[hold.size()];
		history = hold.toArray(history);
		int length = history.length;
		history.sort();
		for (int i = 0; i < length; i++) {
			for (int j = i+1; j < length; j++)
				if (history.get(i));
		}
		return false;
	}
	
	private boolean checkOpen(Node node) {
		//find some way to iterate through a PriorityQueue ???
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
