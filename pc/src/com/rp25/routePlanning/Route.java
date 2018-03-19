package com.rp25.routePlanning;

import java.awt.Point;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Optional;

public class Route {
	private LinkedList<RouteAction> routePlan;
	int robotID;

	public Route(int robotID) {
		routePlan = new LinkedList<>();
		this.robotID = robotID;
	}
	
	public Optional<RouteAction> getNextAction() {
		if(isRouteEmpty()) return Optional.empty();
		
		return Optional.of(routePlan.removeFirst());
	}
	
	public void addToRoute(RouteAction action) {
		routePlan.add(action);
	}
	
	public boolean isRouteEmpty() {
		return routePlan.isEmpty();
	}
	
	public int size() {
		return routePlan.size();
	}
}

class RouteDesc {
	public Point startXY;
	public Collection<Point> itemXYs;
	public Point goalXY;
	public int priority;
	
	public RouteDesc(Point startXY, Collection<Point> itemXYs, Point goalXY, int priority) {
		this.startXY = startXY;
		this.itemXYs = itemXYs;
		this.goalXY = goalXY;
		this.priority = priority;
	}
}