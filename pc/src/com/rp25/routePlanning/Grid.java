package com.rp25.routePlanning;

import java.awt.Point;

import com.rp25.routePlanning.Location.STATE;

public class Grid {
	/*needs:
		2D array of nodes
		actual nodes call from node class (remember that internal walls)
		methods for generating surrounding nodes
	*/

	int width;
	int height;
	
	Location[][] locations;
	
	public Grid() {
		this(6, 6);
	}
	
	public Grid(int width, int height) {
		this(width, height, new Point[0]);
	}
	
	public Grid(int width, int height, Point[] obstacles) {
		this.width = width;
		this.height = height;
		
		locations = new Location[width][height];
		for(int i = 0; i < width; i++)
			for(int j = 0; j < height; j++)
				locations[i][j] = new Location(new Point(i, j));
		
		for(Point p : obstacles)
			locations[p.x][p.y].state = STATE.OBSTACLE;
	}
	
	public Location getLocation(Point loc) {
		return locations[loc.x][loc.y];
	}
	
	public boolean isOnGrid(Point point) {
		return point.x >= 0 && point.x < width && point.y >= 0 && point.y < height;
	}
	
	public boolean isEmpty(Point point) {
		return getLocation(point).state == STATE.EMPTY;
	}
}
