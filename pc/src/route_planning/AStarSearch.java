package route_planning;

import java.awt.Point;
import java.util.ArrayList;
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
			Node current = openList.poll(); //signifies the end of the open list.
			if (current == null) break;
			if (current.isGoal()) {
				ArrayList<Point> route = new ArrayList<Point>();
				route = traceRoute(current);
				return route;
			} 
			Node[] children = generateChildren(current);
			for (Node child : children) {
				if (!(child.checkLoop() || checkOpen(child) || checkClosed(child))) {
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

	private boolean isGoal(Node node) {
		if (node.getCoordinate().equals(goalState)) return true;
		else return false;
	}
	
	private ArrayList<Point> traceRoute(Node node) {
		Node parent = node.getParent();
		ArrayList<Point> route = new ArrayList<Point>();
		if (!(parent == null)) route = traceRoute(parent);
		route.add(node.getCoordinate());
		return route;
	}
}
