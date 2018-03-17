package com.rp25.interfaces.warehouse;

import java.util.Collection;
import java.util.HashMap;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import com.rp25.tools.Job;
import com.rp25.tools.Robot;

public class WarehouseState {
	
	/*
	 * List of robots in the warehouse. 
	 * Key: ID Number
	 * Value: Robot Object
	 */
	HashMap<Integer, Robot> robotList = new HashMap<>(); 
	BlockingQueue<Integer> cancellations = new LinkedBlockingQueue<>();
	
	
	public Collection<Robot> getAllRobots() {
		return robotList.values();
	}
	
	public void addRobot(int id, Robot r) {
		robotList.put(id, r);
	}
	
	public Robot getRobot(int id) {
		return robotList.get(id);
	}
	
	public void updateBotPos(int id, int x, int y) {
		Robot bot = robotList.get(id);
		bot.updateCoordinates(x, y);
	}
	
	public void updateBotJob(int id, Job j) {
		Robot bot = robotList.get(id);
		bot.setCurrentJob(j);
	}
	
	public BlockingQueue<Integer> getCancellations(){
		return cancellations;
	}
}
