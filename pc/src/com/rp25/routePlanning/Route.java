package com.rp25.routePlanning;

import java.awt.Point;
import java.util.ArrayList;

public class Route {
	Point[] path;
	ACTION action;
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
	
	private Point[] makePath(Point s, Point g) {
		AStarSearch search = new AStarSearch(s, g);
		ArrayList<Point> holdPath= new ArrayList<Point>();
		holdPath = search.search();
		Point[] path = holdPath.toArray(new Point[holdPath.size()]);
		return path;
	}
	
}
