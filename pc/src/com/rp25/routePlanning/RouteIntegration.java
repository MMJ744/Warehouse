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
			new Point(1, 2),	
			new Point(1, 3),	
			new Point(1, 4),	
			new Point(1, 5),	
			new Point(1, 6),	
			new Point(4, 2),	
			new Point(4, 3),	
			new Point(4, 4),	
			new Point(4, 5),	
			new Point(4, 6),
			new Point(7, 2),	
			new Point(7, 3),	
			new Point(7, 4),	
			new Point(7, 5),	
			new Point(7, 6),
			new Point(10, 2),	
			new Point(10, 3),	
			new Point(10, 4),	
			new Point(10, 5),	
			new Point(10, 6)
		};
		
		List<Point> obstacles = new ArrayList<>(Arrays.asList(obstacleArray));
		
		int height;
		int width;
	
	Grid grid = new Grid(height, width, obstacles);
	
	
	
	public List<Route> planRoute (Robot r) {
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
		
		//get the current step (& add 1)
		//how???
		
		//make an empty list to hold the return values
		List<Route> routePlan= new LinkedList<Route>();
		
		//actually plot the route
		Point goal;
		int itemCount = 0;
		
		for (JobPart part : orderedParts) {
			goal = new Point(part.getX(), part.getY());
			itemCount = part.getNumOfItems(); //don't forget to sort out drop off points
			//get the route here
				//AStar ultimately returns a list of Points
			//add it to routePlan
			start = goal;
		}
		
		return routePlan;
			
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
	
}
