package com.rp25.interfaces.warehouse;

import java.util.HashMap;
import tools.Robot;

public class WarehouseState {
	
	/*
	 * List of robots in the warehouse. 
	 * Key: ID Number
	 * Value: Robot Object
	 */
	HashMap<Integer, Robot> robotList = new HashMap<>(); 
	
	public void addRobot(int id, Robot r) {
		robotList.put(id, r);
	}
	
	public Robot getRobot(int id) {
		return robotList.get(id);
	}
}
