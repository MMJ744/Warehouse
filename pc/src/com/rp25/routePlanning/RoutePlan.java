package com.rp25.routePlanning;

import java.awt.Point;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

import com.rp25.tools.*;

public class RoutePlan {
	static final Point DROPOFF = new Point(4, 7);
	static Queue<Route> routeList = new LinkedBlockingQueue<Route>();
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
			Job job = getJob(); // will get when Katie next pushes
			ArrayList<JobPart> hold = new ArrayList<JobPart>();
			hold = job.getParts();
			JobPart[] jobParts = hold.toArray(new JobPart[hold.size()]);
			
			//assuming that the weight will not exceed the limit; must change later
			
			JobPart[] orderedParts = orderParts(jobParts);
			
			Point start = DROPOFF;
			Point goal;
			for (JobPart part : orderedParts) {
				goal = new Point(part.getX(), part.getY());
				int itemCount = orderedParts[i].getNumOfItems();
				routeList.offer(new Route(start, goal, itemCount));
				start = goal;
			}
			routeList.offer(new Route(start, DROPOFF, itemCount));
		}
	}
	
	private static JobPart[] orderParts(JobPart[] unordered) {
		//this isn't working yet, so just returns the input
		//all this means is that it's likely the robot will be ping-ponging across the pen
		//instead of taking a sensible route
		JobPart[] ordered = new JobPart[unordered.length];
		Point compare = DROPOFF;
		for (int i = 0; i < ordered.length; i++) {
			JobPart compPart = unordered[0];
			int compDist = Math.abs(compare.x - compPart.getX()) + Math.abs(compare.y - compPart.getY());
			int index = 0;
			for (int j = 0; j < unordered.length; j++) {
				int manDist = Math.abs(compare.x - unordered[j].getX()) + Math.abs(compare.y - unordered[j].getY());
				if (manDist < compDist) {
					compPart = unordered[j];
					compDist = manDist;
					index = j;
				}
			}
			ordered[i] = unordered[index];
			
		}
		
		
		
		
		return unordered;
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
