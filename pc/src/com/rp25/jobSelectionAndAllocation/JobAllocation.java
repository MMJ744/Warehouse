package com.rp25.jobSelectionAndAllocation;

import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.rp25.tools.Job;
import com.rp25.tools.JobPart;

public class JobAllocation {
	
	private static ArrayList<JobPart> robot1Parts = new ArrayList<JobPart>();
	private static ArrayList<Job> robot1Jobs = new ArrayList<Job>();
	private final static Logger logger = Logger.getLogger(JobAllocation.class);
	
	public static void allocateJob(Job nextJob) {
		robot1Parts.addAll(nextJob.getParts());
		robot1Jobs.add(nextJob);
		logger.debug("Current Job: " + nextJob.getName());
	}
	
	public static JobPart getNextJobPart(int robotID) {
		JobPart nextJob;
		switch(robotID) {
		case 1:
			nextJob = robot1Parts.get(0);
			robot1Parts.remove(0);
			return nextJob;
		}
		return null;
		
	}
	
	public static Job getNextJob(int robotID) {
		if(robot1Jobs.isEmpty())
			return null;
		Job nextJob;
		switch(robotID) {
		case 1:
			nextJob = robot1Jobs.get(0);
			robot1Jobs.remove(0);
			robot1Parts.removeAll(nextJob.getParts());
			return nextJob;
		}
		return null;
		
	}
	
	public static Job getJob() {
		return getNextJob(1);
	}

}

