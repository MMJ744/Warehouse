package com.rp25.routePlanning;

import java.awt.Point;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Optional;

import com.rp25.tools.Job;
import com.rp25.tools.Robot;

public class Route {
	private LinkedList<RouteAction> routePlan;
	int robotID;
	Job job;
	
	public Route(int robotID) {
		this(robotID, null);
	}

	public Route(int robotID, Job job) {
		routePlan = new LinkedList<>();
		this.robotID = robotID;
		this.job = job;
	}
	
	public Job getJob() {
		return job;
	}
	
	public Optional<RouteAction> getNextAction() {
		if(isRouteEmpty()) return Optional.empty();
		
		return Optional.ofNullable(routePlan.remove(0));
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
	public String toString(){
		return routePlan.toString();
	}
}

class RouteDesc {
	public Robot r;
	public Point startXY;
	public Collection<Point> itemXYs;
	public Point goalXY;
	public int priority;
	
	public RouteDesc(Robot r, Point startXY, Collection<Point> itemXYs, Point goalXY, int priority) {
		this.r = r;
		this.startXY = startXY;
		this.itemXYs = itemXYs;
		this.goalXY = goalXY;
		this.priority = priority;
	}
}