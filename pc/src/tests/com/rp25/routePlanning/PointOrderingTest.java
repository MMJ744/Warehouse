package tests.com.rp25.routePlanning;

import java.awt.Point;
import java.util.ArrayList;

import com.rp25.tools.JobPart;

public class PointOrderingTest {
	public static final Point DROPOFF = new Point(4, 7);
	
	
	public static void main(String[] args) {
		Point[] unordered = new Point[3];
		unordered[0] = new Point(5, 11);
		unordered[1] = new Point(5, 6);
		unordered[2] = new Point(13, 11);
		
		
		for (Point point: unordered) {
			System.out.println(point.x + ", " + point.y);
		}
		
		Point[] ordered = new Point[3];
		
		
		
		ordered = orderParts(unordered);
		
		for (Point point: ordered) {
			System.out.println(point.x + ", " + point.y);
		}
	}

	
	private static Point[] orderParts(Point[] unordered) {
		
		Point[] ordered = new Point[unordered.length];
		Point startPoint = DROPOFF;
		for (int i = 0; i < ordered.length; i++) {
			Point compPart = unordered[0];
			int compDist = Math.abs(startPoint.x - compPart.x) + Math.abs(startPoint.y - compPart.y);
			int index = 0;
			for (int j = 0; j < unordered.length; j++) {
				int manDist = Math.abs(startPoint.x - unordered[j].x) + Math.abs(startPoint.y - unordered[j].y);
				if (manDist < compDist) {
					compPart = unordered[j];
					compDist = manDist;
					index = j;
				}
			}
			ordered[i] = unordered[index];
			System.out.println(ordered[i].x + ", " + ordered[i].y);
			startPoint = unordered[index];
			unordered = removeElement(unordered, index);
		}
		
		return ordered;
	}
	
	private static Point[] removeElement(Point[] original, int index) {
		ArrayList<Point> newArrayList = new ArrayList<Point>();
		for (int i = 0; i < original.length; i++) {
			if (i != index) newArrayList.add(original[i]);
		}
		return newArrayList.toArray(new Point[original.length -1]);
	}
}
