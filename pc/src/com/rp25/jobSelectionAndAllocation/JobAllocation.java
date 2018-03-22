package com.rp25.jobSelectionAndAllocation;

import java.math.BigDecimal;
import java.util.ArrayList;
import com.rp25.tools.*;

import org.apache.log4j.Logger;

import com.rp25.tools.Job;
import com.rp25.tools.JobPart;

public class JobAllocation {
	
	private static ArrayList<Job> jobs = new ArrayList<Job>();
	private static ArrayList<Job> robot1Jobs = new ArrayList<Job>();
	private static ArrayList<Job> robot2Jobs = new ArrayList<Job>();
	private static ArrayList<Job> robot3Jobs = new ArrayList<Job>();
	private final static Logger logger = Logger.getLogger(JobAllocation.class);
	
	public static void allocateJob(Job nextJob) {
		BigDecimal totalWeight = BigDecimal.ZERO;
		boolean jobIsSplit = false;
		for(JobPart part: nextJob.getParts()) {
			totalWeight = totalWeight.add(part.getWeight());
		}
		ArrayList<Job> splitJobs = null;
		while(totalWeight.compareTo(new BigDecimal("50")) > 0) {
			jobIsSplit = true;
			splitJobs = splitUpJob(nextJob);
		}
		jobs.add(nextJob);
		int numRobot1Jobs = robot1Jobs.size();
		int numRobot2Jobs = robot2Jobs.size();
		int numRobot3Jobs = robot3Jobs.size();
		if(jobIsSplit) {
			if(Math.max(numRobot1Jobs, Math.max(numRobot2Jobs, numRobot3Jobs))==numRobot1Jobs) {
				robot1Jobs.addAll(splitJobs);
			}
			else if(Math.max(numRobot1Jobs, Math.max(numRobot2Jobs, numRobot3Jobs))==numRobot2Jobs) {
				robot2Jobs.addAll(splitJobs);
			}
			else {
				robot3Jobs.addAll(splitJobs);
			}
		}
		else {
			if(Math.max(numRobot1Jobs, Math.max(numRobot2Jobs, numRobot3Jobs))==numRobot1Jobs) {
				robot1Jobs.add(nextJob);
			}
			else if(Math.max(numRobot1Jobs, Math.max(numRobot2Jobs, numRobot3Jobs))==numRobot2Jobs) {
				robot2Jobs.add(nextJob);
			}
			else {
				robot3Jobs.add(nextJob);
			}
		}
		logger.trace("Current Job: " + nextJob.getName());
	}
	
	private static ArrayList<Job> splitUpJob(Job job){
		ArrayList<Job> splitJobs = new ArrayList<Job>();
		BigDecimal weight = BigDecimal.ZERO;
		int partsSplit = 0;
		ArrayList<JobPart> parts = new ArrayList<JobPart>();
		for(JobPart part: job.getParts()) {
			if(weight.add(part.getWeight()).compareTo(new BigDecimal("50"))>0) {
				Job splitJob = new Job(job.getName());
				for(JobPart currentPart: parts) {
					splitJob.addPart(currentPart);
				}
				splitJobs.add(splitJob);
				splitJob.setIsSplit();
				parts.removeAll(parts);
			}
			else {
				weight = weight.add(part.getWeight());
				parts.add(part);
			}
		}
		return splitJobs;
	}
	
	public static Job getNextJob(int robotID) {
		Job nextJob = null;
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
	static int count = 0;
	public static Job getJob() {
		System.out.println(count++);
		return jobs.remove(0);
	}

}

