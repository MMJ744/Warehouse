package com.rp25.interfaces.warehouse.cli;

import java.util.ArrayList;
import java.util.Scanner;

import com.rp25.interfaces.warehouse.WarehouseState;
import com.rp25.tools.Robot;

public class WarehouseCLI implements Runnable {

	private WarehouseState warehouseState;
	private Scanner userInput;
	
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
				System.out.println(warehouseState.getRobot(id).toString());
				break;
				
			case "move": 
				int botid = Integer.parseInt(getInput());
				Robot current = warehouseState.getRobot(botid);
				String movement = getInput();
				switch(movement) {
				case "up":
					current.updateCoordinates(current.getX(), current.getY() + 1);
					break;
				case "down":
					current.updateCoordinates(current.getX(), current.getY() - 1);
					break;
				case "left":
					current.updateCoordinates(current.getX(), current.getY() - 1);
					break;
				case "right":
					current.updateCoordinates(current.getX(), current.getY() + 1);
					break;
				}
				break;
			}
		}
		
		close();
	}
}
