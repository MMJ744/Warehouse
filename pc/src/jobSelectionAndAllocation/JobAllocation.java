package jobSelectionAndAllocation;

import java.util.ArrayList;

import tools.Job;

public class JobAllocation {
	
	private static ArrayList<Job> robot1 = new ArrayList<Job>();
	
	public static void allocateJob(Job nextJob) {
		robot1.add(nextJob);
	}
	
	public static Job getNextJob(int robot) {
		Job nextJob;
		switch(robot) {
		case 1:
			nextJob = robot1.get(0);
			robot1.remove(0);
			return nextJob;
		}
		return null;
		
	}

}
