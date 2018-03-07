package com.rp25.interfaces.warehouse;

import java.util.ArrayList;
import java.util.Scanner;

import tools.Robot;

public class WarehouseCLI {

	private WarehouseState warehouseState;
	private Scanner userInput;
	
	public WarehouseCLI(ArrayList<Robot> robots) {
		warehouseState = new WarehouseState();
		userInput = new Scanner(System.in);
		initialise(robots);
	}
	
	private void initialise(ArrayList<Robot> robots) {
		for (Robot r : robots) {
			warehouseState.addRobot(r.getID(), r);
		}
	}
	
	public WarehouseState getState() {
		return warehouseState;
	}
	
	public String getInput() {
		return userInput.next();
	}
	
	public void close() {
		userInput.close();
	}
}
