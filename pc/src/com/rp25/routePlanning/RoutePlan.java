package com.rp25.routePlanning;

import java.awt.Point;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

import com.rp25.jobSelectionAndAllocation.JobAllocation;
import com.rp25.tools.*;

public class RoutePlan {
	static final Point DROPOFF = new Point(4, 7);
	static LinkedBlockingQueue<Route> routeList = new LinkedBlockingQueue<Route>();
	/* the plan:
	 	* get the next job
	 	* split the job into its individual parts, storing as an ArrayList
	 	* check the overall weight of the job, and split into two if necessary
	 	* order the job parts according to proximity
	 	* change job parts into pairs of coordinates with actions (pick up/drop off)
	 	* change pairs of coordinates into a path (by calling A* search)
	 	* add path/action pairs to a queue accessible by robot control 
	*/
	
	
	
	//job weights per item stored as BigDecimal
	

	public static void main(String[] args) {
		while (true) {
			Job job = JobAllocation.getJob(); // will get when Katie next pushes
			ArrayList<JobPart> hold = new ArrayList<JobPart>();
			hold = job.getParts();
			JobPart[] jobParts = hold.toArray(new JobPart[hold.size()]);
			
			//assuming that the weight will not exceed the limit; must change later
			
			JobPart[] orderedParts = orderParts(jobParts);
			
			Point start = DROPOFF;
			Point goal;
			int itemCount = 0;
			for (JobPart part : orderedParts) {
				goal = new Point(part.getX(), part.getY());
				itemCount = part.getNumOfItems();
				routeList.offer(new Route(start, goal, itemCount));
				start = goal;
			}
			routeList.offer(new Route(start, DROPOFF, itemCount));
		}
	}
	
	private static JobPart[] orderParts(JobPart[] unordered) {
		
		JobPart[] ordered = new JobPart[unordered.length];
		Point startPoint = DROPOFF;
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
	
	private BigDecimal calcOverallWeight (JobPart[] jobs) {
		BigDecimal total = new BigDecimal("0");
		for (JobPart jobPart : jobs) {
			total+=jobPart.getWeight();
		}
		return total;
	}
	
	private void generateWeightArray(JobPart[] jobs) {
		JobPart[][] weightedParts = new JobPart[jobs.length][2];
		for (int i = 0; i < jobs.length; i++) {
			weightedParts[i][0] = jobs[i];
			weightedParts[i][1] = calcWeight(jobs[i]);
		}
	}
	
	

}
