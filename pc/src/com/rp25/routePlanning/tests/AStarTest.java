package com.rp25.routePlanning.tests;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Queue;

import com.rp25.routePlanning.AStarSearch;

public class AStarTest {

	public static void main(String[] args) {
		Point start = new Point(2,4);
		Point goal = new Point(3,5);
		AStarSearch search = new AStarSearch(start, goal);
		Queue<Point> path = new Queue<Point>();
		path = search.search();
		if (path == null) System.out.println("search returned null"); 
		else {
			for (int i = 0; i < path.size(); i++) {
				System.out.println(path.pop());
			}
		}

	}

}
