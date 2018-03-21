package com.rp25.interfaces.warehouse;

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
	BlockingQueue<Integer> cancellations = new LinkedBlockingQueue<>();
	RouteExecutor executor = null;
	
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
	
	/**
	 * Updates a specific robot's coordinates
	 * @param id ID number of the desired robot
	 * @param x x-coordinate of the desired robot
	 * @param y y-coordinate of the desired robot
	 */
	public void updateBotPos(int id, int x, int y) {
		Robot bot = robotList.get(id);
		bot.updateCoordinates(x, y);
	}
	
	/**
	 * Updates a specific robot's current Job
	 * @param id ID number of the desired robot
	 * @param j The new job
	 */
	public void updateBotJob(int id, Job j) {
		Robot bot = robotList.get(id);
		bot.setCurrentJob(j);
	}
	
	/**
	 * Get the queue of job cancellations from the warehouse interface
	 * @return A BlockingQueue of job cancellations
	 */
	public BlockingQueue<Integer> getCancellations(){
		return cancellations;
	}
}
