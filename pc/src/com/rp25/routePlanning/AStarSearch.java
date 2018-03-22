package com.rp25.routePlanning;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.PriorityQueue;

import com.rp25.routePlanning.RouteAction.ACTION;
import com.rp25.tools.Robot;


public class AStarSearch {
	private Grid grid;

	private static AStarSearch instance;
	private AStarSearch() {}

	public static AStarSearch getInstance() {
		if(instance == null)
			instance = new AStarSearch();
		
		return instance;
	}

	public List<Point> getPath(Robot r, Point startXY, Point goalXY, int priority) throws PathNotFoundException {
		return getPath(r, startXY, goalXY, priority, 0);
	};

	public List<Point> getPath(Robot r, Point startXY, Point goalXY, int priority, int startStep) throws PathNotFoundException {
		List<Point> path = new AStar(grid, startXY, goalXY).getPath();

		int s = grid.getStep() + startStep;
		for(Point xy : path) {
			System.out.println("R" + r.getID() + " reserving " + xy.toString() + " at " + s++);
			grid.reserveCell(new Cell(xy, s), r.getID());
		}

		return path;
	}

	public List<Point> getFullPath(Robot r, Point startXY, Collection<Point> itemXYs, Point goalXY, int priority) throws PathNotFoundException {
		Iterator<Point> items = itemXYs.iterator();
		List<Point> path = getPath(r, startXY, items.next(), priority);

		while(items.hasNext())
			path.addAll(getPath(r, items.next(), goalXY, priority, path.size()));

		return path;
	}

	public Route getFullRoute(RouteDesc desc) throws PathNotFoundException {
		Route route = new Route(desc.priority);

		Iterator<Point> items = desc.itemXYs.iterator();
		
		if(items.hasNext()) {
			Point item = items.next();
			Point lastPoint = desc.startXY;
			List<Point> path = getPath(desc.r, desc.startXY, item, desc.priority);
			boolean hasPickedUp = false;

			for(Point point : path) {
				ACTION a = (point.equals(item) && !hasPickedUp) ? ACTION.PICKUP : (point.equals(lastPoint) ? ACTION.WAIT : ACTION.MOVE);
				if(a.equals(ACTION.PICKUP)) hasPickedUp = true;
				lastPoint = point;
				route.addToRoute(new RouteAction(a, point, desc.priority));
			}
			
			Point nextItem = null;
			
			while(items.hasNext()) {
				nextItem = items.next();
				path = getPath(desc.r, item, nextItem, desc.priority, route.size());
				hasPickedUp = false;
				
				for(Point point : path) {
					ACTION a = (point.equals(item) && !hasPickedUp) ? ACTION.PICKUP : (point.equals(lastPoint) ? ACTION.WAIT : ACTION.MOVE);
					if(a.equals(ACTION.PICKUP)) hasPickedUp = true;
					
					lastPoint = point;
					route.addToRoute(new RouteAction(a, point, desc.priority));
				}
			}
			
			Point finalPathStart = Optional.ofNullable(nextItem).orElse(item);
			lastPoint = finalPathStart;
			path = getPath(desc.r, finalPathStart, desc.goalXY, desc.priority, route.size());
			
			for(Point point : path) {
				ACTION a = point.equals(lastPoint) ? ACTION.WAIT : ACTION.MOVE;
				if(point.equals(path.get(path.size()-1))) a = ACTION.DROPOFF;
				
				lastPoint = point;
				route.addToRoute(new RouteAction(a, point, desc.priority));
			}
		}

		return route;
	}

	public void setGrid(Grid grid) {
		this.grid = grid;
	}

	public static void main(String[] args) {		
		warehouseTest();
	}

	public static void warehouseTest() {
		Point[] obstacleArray = new Point[] {
			new Point(1, 2),	
			new Point(1, 3),	
			new Point(1, 4),	
			new Point(1, 5),	
			new Point(1, 6),	
			new Point(4, 2),	
			new Point(4, 3),	
			new Point(4, 4),	
			new Point(4, 5),	
			new Point(4, 6),
			new Point(7, 2),	
			new Point(7, 3),	
			new Point(7, 4),	
			new Point(7, 5),	
			new Point(7, 6),
			new Point(10, 2),	
			new Point(10, 3),	
			new Point(10, 4),	
			new Point(10, 5),	
			new Point(10, 6)
		};
		
		List<Point> obstacles = new ArrayList<>();
		for(Point p : obstacleArray) {
			obstacles.add(p);
		}
		
		Grid grid = new Grid(12, 8, obstacles);
		grid.outputRightWayUp();

		AStarSearch search = AStarSearch.getInstance();
		search.setGrid(grid);
	}
	
	private class AStar {
		private Grid grid;

		private PriorityQueue<Node> openNodes = new PriorityQueue<>();
		private ArrayList<Node> closedNodes = new ArrayList<>();

		private Point startXY;
		private Point goalXY;

		private int startStep;

		public AStar(Grid grid, Point startXY, Point goalXY) {
			this.grid = grid;

			this.startXY = startXY;
			this.goalXY = goalXY;

			this.startStep = grid.getStep();
		}

		public List<Point> getPath() throws PathNotFoundException {
			Node startNode = new Node(startXY, goalXY, startStep, null, grid);
			openNodes.add(startNode);

			Node searchNode;
			while(!openNodes.isEmpty()) {
				searchNode = openNodes.poll();
				if(searchNode.getF() != Integer.MAX_VALUE && searchNode.getF() > 30) break;
			
				if(searchNode.getXY().equals(goalXY)) {
					List<Point> path = grabRoute(searchNode);
					return path.subList(1, path.size());
				}
				
				for(Node child : genChildrenFromNode(searchNode)) {					
					if(!nodeVisited(child))
						if(child.getF() != Integer.MAX_VALUE) openNodes.add(child);
				}

				closedNodes.add(searchNode);
			}

			throw new PathNotFoundException("Could not find a path starting at: " + startXY.toString() + " and ending at: " + goalXY.toString());
		}

		public boolean nodeVisited(Node node) {
			return openContains(node) || closedContains(node);
		}

		public boolean openContains(Node node) {
			for(Node fromList : openNodes)
				if(fromList.getXY().equals(node.getXY()) && fromList.getStep() == node.getStep())
					return true;

			return false;
		}

		public boolean closedContains(Node node) {
			for(Node fromList : closedNodes)
				if(fromList.getXY().equals(node.getXY()) && fromList.getStep() == node.getStep())
					return true;

			return false;
		}

		public List<Node> genChildrenFromNode(Node parent) {
			List<Node> children = new ArrayList<>();

			for(int xC = -1; xC <= 1; xC++)
				for(int yC = -1; yC <= 1; yC++)
					if(xC == 0 || yC == 0) {
						Point newXY = new Point(parent.getXY().x + xC, parent.getXY().y + yC);
						
						if(grid.containsXY(newXY))children.add(new Node(newXY, goalXY, parent.getStep() + 1, parent, grid));
					}
			
			return children;
		}

		public List<Point> grabRoute(Node node) {
			List<Point> route = new ArrayList<Point>();

			Optional<Node> parent = node.getParent();
			if(parent.isPresent()) route.addAll(grabRoute(parent.get()));

			route.add(node.getXY());
			return route;
		}
	}
}