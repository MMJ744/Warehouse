package route_planning;

import java.util.PriorityQueue;

public class AStarSearch {
	PriorityQueue<Node> openList = new PriorityQueue<Node>();
	//closedList = ???
	Node startNode;
	Coordinate goalNode;
	
	AStarSearch (Coordinate c, Coordinate g) {
		startNode = new Node(c);
		goalNode = g;
	}
	
	private boolean isGoal(Node node) {
		if (node.getCoordinate.equals(goalNode)) return true;
		else return false;
	}
}
