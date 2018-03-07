package com.rp25.interfaces.warehouse;

import java.util.HashMap;
import tools.Robot;

public class WarehouseState {
	
	/**
	 * List of robots in the warehouse. 
	 * Key: ID Number
	 * Value: Robot Object
	 */
	HashMap<Integer, Robot> robotList = new HashMap<>(); 
	
	public void addRobot(int id, Robot r) {
		robotList.put(id, r);
	}
	
	public String getStringInfo(int id) {
		StringBuilder output = new StringBuilder();
		
		if (robotList.containsKey(id)) {
			Robot currentBot = robotList.get(id);
			
			String strID  = "Robot ID: " + currentBot.getID();
			String strPos = "Position: (" + currentBot.getX() + ", " + currentBot.getY() + ")";
			String strJob =	"Current Job: " + currentBot.getCurrentJob();
			
			output.append(strID)
				  .append("\r\n")
				  .append(strPos)
				  .append("\r\n")
				  .append(strJob);
		}
		
		return output.toString();
	}
}
