package com.rp25.routePlanning;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Queue;

public class Route {
	Queue<Point> path;
	ACTION action;
	public Queue<Point> getPath() {
		return path;
	}

	public ACTION getAction() {
		return action;
	}


	public int getItemCount() {
		return itemCount;
	}

	int itemCount;
	

	Route(Point s, Point g, int i) {
		path = makePath(s, g);
		itemCount = i;
		if (i == 0) action = ACTION.DROPOFF;
		else action = ACTION.PICKUP;
	}
	
	public enum ACTION {
		PICKUP,
		DROPOFF
	}
	
	private Queue<Point> makePath(Point s, Point g) {
		AStarSearch search = new AStarSearch(s, g);
		Queue<Point> path= new Queue<Point>();
		//holdPath = search.search();  dont think we need this
		//Point[] path = holdPath.toArray(new Point[holdPath.size()]);
		return path;
	}
	
}
