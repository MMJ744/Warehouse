package com.rp25.routePlanning;

import java.awt.Point;
import java.util.Comparator;

public class NodeComparatorPoint implements Comparator<Point> {

	@Override
	public int compare(Point a, Point b) {
		//returns pos if a > b, 0 if equal, neg if a < b
		
		double xDiff = (a.x - b.x);
		if (xDiff == 0) return (int) (a.y - b.y);
		else return (int) xDiff;  //casting here is clumsy, but should be fine, as all coordinates will be whole numbers
	}

}
