package com.rp25.interfaces.warehouse.cli;

import java.util.Scanner;

import org.apache.log4j.Logger;

import com.rp25.interfaces.warehouse.WarehouseState;
import com.rp25.tools.Robot;

public class WarehouseCLI implements Runnable {

	private WarehouseState warehouseState;
	private Scanner userInput;
	final static Logger logger = Logger.getLogger(WarehouseCLI.class);
	
	public WarehouseCLI(WarehouseState state) {
		warehouseState = state;
		userInput = new Scanner(System.in);
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

	@Override
	public void run() {
		String i;
		
		while(!(i = getInput()).equalsIgnoreCase("quit")) {
			switch(i) {
			case "get":
				int id = Integer.parseInt(getInput());
				logger.debug(warehouseState.getRobot(id).toString());
				break;
				
			case "move": 
				int botid = Integer.parseInt(getInput());
				Robot current = warehouseState.getRobot(botid);
				String movement = getInput();
				switch(movement) {
				case "up":
					warehouseState.updateBotPos(botid, current.getX(), current.getY() + 1);
					break;
				case "down":
					warehouseState.updateBotPos(botid, current.getX(), current.getY() - 1);
					break;
				case "left":
					warehouseState.updateBotPos(botid, current.getX() - 1, current.getY());
					break;
				case "right":
					warehouseState.updateBotPos(botid, current.getX() + 1, current.getY());
					break;
				}
				break;
			}
		}
		
		close();
	}
}
