package com.rp25.interfaces.warehouse;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import com.rp25.routeExecution.RouteExecutor;
import com.rp25.tools.Job;
import com.rp25.tools.Robot;

/**
 * A class that holds information about the state of the warehouse
 * i.e. the robot positions/jobs and  job cancellations.
 * @author ass782
 *
 */

public class WarehouseState {
	
	/*
	 * List of robots in the warehouse. 
	 * Key: ID Number
	 * Value: Robot Object
	 */
	HashMap<Integer, Robot> robotList = new HashMap<>(); 
	Collection<Job> completedJobs = new ArrayList<>();
	RouteExecutor executor = null;
	
	public void addCompletedJob(Job j) {
		completedJobs.add(j);
	}
	
	public Collection<Job> getAllCompletedJobs() {
		return completedJobs;
	}
	
	public void setExecutor(RouteExecutor e) {
		executor = e;
	}
	
	public RouteExecutor getExecutor() {
		return executor;
	}
	
	/**
	 * Returns a collection of all robots inside the warehouse.
	 * @return Collection of Robot objects
	 */
	public Collection<Robot> getAllRobots() {
		return robotList.values();
	}
	
	/**
	 * Adds a robot to the current collection of robots.
	 * @param id ID number of the robot
	 * @param r the Robot object
	 */
	public void addRobot(int id, Robot r) {
		robotList.put(id, r);
	}
	
	/**
	 * Gets a specific robot in the warehouse
	 * @param id ID number of the desired robot
	 * @return The desired Robot object
	 */
	public Robot getRobot(int id) {
		return robotList.get(id);
	}

	public String completedJobsString() {
		StringBuilder output = new StringBuilder();
		
		for (Job j : getAllCompletedJobs()) {
			output.append(j.getName())
				  .append("\r\n");
		}
		
		return output.toString();
	}
}
