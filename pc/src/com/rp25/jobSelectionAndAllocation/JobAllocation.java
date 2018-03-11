package com.rp25.jobSelectionAndAllocation;

import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.rp25.tools.Job;

public class JobAllocation {
	
	private static ArrayList<Job> robot1 = new ArrayList<Job>();
	private final static Logger logger = Logger.getLogger(JobAllocation.class);
	
	public static void allocateJob(Job nextJob) {
		robot1.add(nextJob);
		logger.debug("Current Job: " + nextJob.getName());
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
