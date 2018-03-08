package com.rp25.routePlanning;

import java.awt.Point;

public class Location {
	public enum STATE {
		EMPTY,
		OCCUPIED,
		OBSTACLE;
	}
	
	Point coordinate;
	STATE state;
	
	public Location(Point coordinate) {
		this(coordinate, STATE.EMPTY);
	}
	
	public Location(Point coordinate, STATE state) {
		this.coordinate = coordinate;
		this.state = state;
	}
}
