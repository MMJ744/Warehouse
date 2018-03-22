package com.rp25.routePlanning;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

import com.rp25.jobSelectionAndAllocation.JobAllocation;
import com.rp25.routePlanning.RouteAction.ACTION;
import com.rp25.tools.Job;
import com.rp25.tools.JobPart;
import com.rp25.tools.Robot;

public class RouteIntegration {
	/* read in jobs from Katie (how???)
	 * is this running as a thread? 
	 * set up grid once at the start
	 * 
	 */
	
	Point[] obstacleArray = new Point[] {
			new Point(1, 1),	
			new Point(1, 2),	
			new Point(1, 3),	
			new Point(1, 4),	
			new Point(1, 5),	
			new Point(4, 1),	
			new Point(4, 2),	
			new Point(4, 3),	
			new Point(4, 4),	
			new Point(4, 5),
			new Point(7, 1),	
			new Point(7, 2),	
			new Point(7, 3),	
			new Point(7, 4),	
			new Point(7, 5),
			new Point(10, 1),	
			new Point(10, 2),	
			new Point(10, 3),	
			new Point(10, 4),
			new Point(10, 5)
		};
		
		List<Point> obstacles = new ArrayList<>(Arrays.asList(obstacleArray));
		
		int height = 8;
		int width = 12;
	
	Grid grid = new Grid(width, height, obstacles);
	
	{
		AStarSearch.getInstance().setGrid(grid);
	};
	
	static final Point[] DROPOFFLOCATIONS = {new Point(4, 7), new Point(6, 5)};
	
	
	
	public Route planRoute (Robot r, int s) throws PathNotFoundException {
		Robot robot = r;
		
		//get robot's current location
		Point start = new Point(robot.getX(), robot.getY());
		
		//read the next job off of JobAllocation
		Job job = JobAllocation.getJob(); //possibly needs to change if now robot specific?
		
		//split the job into its individual parts
		ArrayList<JobPart> hold = new ArrayList<JobPart>();
		hold = job.getParts();
		JobPart[] jobParts = hold.toArray(new JobPart[hold.size()]);
		
		//order the parts according to location
		JobPart[] orderedParts = orderParts(jobParts, start);
		
		//find the nearest drop off location using the final point
		Point dropPoint = findClosestDropOff(new Point(orderedParts[orderedParts.length - 1].getX(), orderedParts[orderedParts.length - 1].getY()));
		//death statement just gets the x & y coordinates of the final job part to be conducted
		
		
		//get the step the robot will start the route on (passed in)
		int nextStep = s;
		
		//make an empty route to hold the plan
		Route route = new Route(robot.getID());
		
		//actually plot the route
		Point goal;
		int itemCount = 0;
		
		
		for (int i = 0; i <= orderedParts.length; i++) {
			
			if (i == orderedParts.length) {
				goal = dropPoint;
				itemCount = 0;
			}
			else {
				JobPart part = orderedParts[i]; //some form of pickup
				goal = new Point(part.getX(), part.getY());
				itemCount = part.getNumOfItems(); 
			}
			
			//get the route here
			List<Point> path = AStarSearch.getInstance().getPath(r, start, goal, 0, nextStep);
			nextStep += path.size();
			
			
			//for each coordinate in the list, create an action and add to the route
			
			for (int j = 0; j < path.size(); j++) {
				Point point = path.get(j);
				ACTION action;
				if (j + 1 == path.size()) {
					//this is the end of the route
					if (itemCount == 0) action = ACTION.DROPOFF;
					else action = ACTION.PICKUP;
					//need to change something to allow number of items to be stored
				}
				else {
					Point next = path.get(j+1);
					if (point.equals(next)) action = ACTION.WAIT;
					else action = ACTION.MOVE;
				}
				
				route.addToRoute(new RouteAction(action, point, robot.getID()));
			}
			start = goal;
		}
		
		
		
		
		return route;
			
	}
	
private static JobPart[] orderParts(JobPart[] unordered, Point start) {
		
		JobPart[] ordered = new JobPart[unordered.length];
		Point startPoint = start;
		for (int i = 0; i < ordered.length; i++) {
			JobPart compPart = unordered[0];
			int compDist = Math.abs(startPoint.x - compPart.getX()) + Math.abs(startPoint.y - compPart.getY());
			int index = 0;
			for (int j = 0; j < unordered.length; j++) {
				int manDist = Math.abs(startPoint.x - unordered[j].getX()) + Math.abs(startPoint.y - unordered[j].getY());
				if (manDist < compDist) {
					compPart = unordered[j];
					compDist = manDist;
					index = j;
				}
			}
			ordered[i] = unordered[index];
			startPoint = new Point(unordered[index].getX(), unordered[index].getY());
			unordered = removeElement(unordered, index);
		}
		
		return ordered;
	}
	
	private static JobPart[] removeElement(JobPart[] original, int index) {
		ArrayList<JobPart> newArrayList = new ArrayList<JobPart>();
		for (int i = 0; i < original.length; i++) {
			if (i != index) newArrayList.add(original[i]);
		}
		return newArrayList.toArray(new JobPart[original.length -1]);
	}
	
	private static Point findClosestDropOff(Point point) {
		Point closestPoint = DROPOFFLOCATIONS[0];
		int lowestDist = Math.abs(closestPoint.x - point.x) + Math.abs(closestPoint.y - point.y);
		for (Point dropPoint : DROPOFFLOCATIONS) {
			int dist =  Math.abs(dropPoint.x - point.x) + Math.abs(dropPoint.y - point.y);
			if (dist < lowestDist) {
				lowestDist = dist;
				closestPoint = dropPoint;
			}
		}
		return closestPoint;
	}
	
}
