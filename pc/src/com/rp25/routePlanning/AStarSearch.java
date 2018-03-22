package com.rp25.routePlanning;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.PriorityQueue;

import com.rp25.routePlanning.RouteAction.ACTION;


public class AStarSearch {
	private Grid grid;

	private static AStarSearch instance;
	public AStarSearch() {}

	public static AStarSearch getInstance() {
		if(instance == null)
			instance = new AStarSearch();
		
		return instance;
	}

	public List<Point> getPath(Point startXY, Point goalXY, int priority) throws PathNotFoundException {
		return getPath(startXY, goalXY, priority, 0);
	};

	public List<Point> getPath(Point startXY, Point goalXY, int priority, int startStep) throws PathNotFoundException {
		List<Point> path = new AStar(grid, startXY, goalXY).getPath();

		int s = grid.getStep() + startStep;
		for(Point xy : path) {
			System.out.println("reserving " + xy.toString() + " at " + s++);
			grid.reserveCell(new Cell(xy, s), priority);
		}

		return path;
	}

	public List<Point> getFullPath(Point startXY, Collection<Point> itemXYs, Point goalXY, int priority) throws PathNotFoundException {
		Iterator<Point> items = itemXYs.iterator();
		List<Point> path = getPath(startXY, items.next(), priority);

		while(items.hasNext())
			path.addAll(getPath(items.next(), goalXY, priority, path.size()));

		return path;
	}

	public Route getFullRoute(RouteDesc desc) throws PathNotFoundException {
		Route route = new Route(desc.priority);

		Iterator<Point> items = desc.itemXYs.iterator();
		
		if(items.hasNext()) {
			Point item = items.next();
			Point lastPoint = desc.startXY;
			List<Point> path = getPath(desc.startXY, item, desc.priority);
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
				path = getPath(item, nextItem, desc.priority, route.size());
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
			path = getPath(finalPathStart, desc.goalXY, desc.priority, route.size());
			
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
	
	public static void test() {
		Point start1 = new Point(1, 2);
		Point start2 = new Point(0, 0);
		
		Point item1 = new Point(1, 5);
		Point item2 = new Point(5, 4);
		Point item3 = new Point(7, 7);
		Point item4 = new Point(2, 6);
		
		Point goal1 = new Point(3, 3);
		Point goal2 = new Point(5, 1);

		List<Point> obstacles = new ArrayList<>();
		for(Point p : new Point[] {new Point(0, 3), new Point(1, 3), new Point(2, 3), new Point(4, 3)}) {
			obstacles.add(p);
		}
		
		List<Point> items = new ArrayList<>();
		for(Point p : new Point[] {item1, item2, item3, item4}) {
			items.add(p);
		}
		
		Grid grid = new Grid(8, 8, obstacles, items);
		grid.reserveCell(new Cell(start1, 0), 1);
		grid.reserveCell(new Cell(start2, 0), 2);

		AStarSearch search = AStarSearch.getInstance();
		search.setGrid(grid);

		try {
			RouteDesc desc1 = new RouteDesc(start1, new ArrayList<Point>() {{add(item1); add(item2);}}, goal1, 1);
			Route route1 = search.getFullRoute(desc1);
			RouteDesc desc2 = new RouteDesc(start2, new ArrayList<Point>() {{add(item3); add(item4);}}, goal2, 2);
			Route route2 = search.getFullRoute(desc2);

			int i = 0;
			while(!route1.isRouteEmpty() || !route2.isRouteEmpty()) {
				Optional<RouteAction> act = route1.getNextAction();

				if(act.isPresent())
					System.out.println(act.get().toString());

				System.out.println();
				grid.outputAtStep(i++);
				Optional<RouteAction> act2 = route2.getNextAction();

				if(act2.isPresent())
					System.out.println(act2.get().toString());
				
				System.out.println();
			}
		} catch (PathNotFoundException e) {
			System.out.println(e.getMessage());
		}
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
				if(searchNode.getF() != Integer.MAX_VALUE && searchNode.getF() > 15) break;
			
				if(searchNode.getXY().equals(goalXY)) {
					List<Point> path = grabRoute(searchNode);
					return path.subList(1, path.size());
				}

				for(Node child : genChildrenFromNode(searchNode))
					if(!nodeVisited(child))
						if(child.getF() != Integer.MAX_VALUE) openNodes.add(child);

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

						if(grid.containsXY(newXY))
							children.add(new Node(newXY, goalXY, parent.getStep() + 1, parent, grid));
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