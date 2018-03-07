package route_planning;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;
import org.apache.log4j.Logger;

public class AStarSearch {
	PriorityQueue<Node> openList = new PriorityQueue<Node>();
	ArrayList<Node> closedList = new ArrayList<Node>();
	Node startNode;
	Point goalState;
	final static Logger logger = Logger.getLogger(AStarSearch.class);
	
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
				logger.debug("Current Node: " + current.getCoordinate());
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
	
	private Node[] generateChildren(Node parentNode) {
		Point parent = parentNode.getCoordinate();
		
		Point[] points = new Point[4];
		
		points[0] = new Point(parent.x + 1, parent.y); //north
		points[1] = new Point(parent.x, parent.y + 1); //east
		points[2] = new Point(parent.x - 1, parent.y); //south
		points[3] = new Point(parent.x, parent.y - 1); //west
		
		ArrayList<Node> h = new ArrayList<Node>();
		
		for (Point child : points) {
			if (checkIsOnGrid(child)) {
				Node childNode = new Node(child, goalState, parentNode);
				h.add(childNode);
			}
		}
		
		Node[] children = new Node[h.size()];
		children = h.toArray(children);
		for(int i = 0; i < children.length; i++) {
			logger.debug("Child " + i + " Coordinates: " + children[i]);
		}
		return children;
	}
	
	private boolean checkIsOnGrid(Point point) {
		return false;
	}
	
	private boolean checkLoop(Node node) { //NB this method has bad time complexity; look at using comparator & sort to improve
		ArrayList<Point> history = new ArrayList<Point>();
		int length = history.size();
		for (int i = 0; i < length; i++) {
			for (int j = i+1; j < length; j++)
				if (history.get(i).equals(history.get(j))) return true;
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
