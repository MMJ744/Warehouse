package com.rp25.jobSelectionAndAllocation;

import java.util.ArrayList;
import com.rp25.tools.*;

import org.apache.log4j.Logger;

import com.rp25.tools.Job;
import com.rp25.tools.JobPart;

public class JobAllocation {
	
	private static ArrayList<JobPart> robot1Parts = new ArrayList<JobPart>();
	private static ArrayList<Job> robot1Jobs = new ArrayList<Job>();
	private static ArrayList<Job> robot2Jobs = new ArrayList<Job>();
	private static ArrayList<Job> robot3Jobs = new ArrayList<Job>();
	private final static Logger logger = Logger.getLogger(JobAllocation.class);
	
	public static void allocateJob(Job nextJob) {
		int numRobot1Jobs = robot1Jobs.size();
		int numRobot2Jobs = robot2Jobs.size();
		int numRobot3Jobs = robot3Jobs.size();
		if(Math.max(numRobot1Jobs, Math.max(numRobot2Jobs, numRobot3Jobs))==numRobot1Jobs) {
			robot1Jobs.add(nextJob);
		}
		else if(Math.max(numRobot1Jobs, Math.max(numRobot2Jobs, numRobot3Jobs))==numRobot2Jobs) {
			robot2Jobs.add(nextJob);
		}
		else {
			robot3Jobs.add(nextJob);
		}
			
		logger.debug("Current Job: " + nextJob.getName());
	}
	
	public static Job getNextJob(int robotID) {
		Job nextJob;
		switch(robotID) {
		case 1:
			nextJob = robot1Jobs.get(0);
			robot1Jobs.remove(0);
			break;
		case 2:
			nextJob = robot2Jobs.get(0);
			robot2Jobs.remove(0);
			break;
		case 3:
			nextJob = robot3Jobs.get(0);
			robot3Jobs.remove(0);
			break;
		}
		return nextJob;
		
	}
	
	public static Job getJob() {
		return getNextJob(1);
	}

}

