package com.rp25.routePlanning;

import java.awt.Point;
import java.util.ArrayList;

public class AStarTest {

	public static void main(String[] args) {
		Point start = new Point(2,4);
		Point goal = new Point(3,5);
		AStarSearch search = new AStarSearch(start, goal);
		ArrayList<Point> path = new ArrayList<Point>();
		path = search.search();
		if (path == null) System.out.println("search returned null"); 
		else {
			for (int i = 0; i < path.size(); i++) {
				System.out.println(path.get(i));
			}
		}

	}

}
