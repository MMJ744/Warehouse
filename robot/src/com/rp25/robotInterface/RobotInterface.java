package com.rp25.robotInterface;

import lejos.nxt.Button;
import lejos.nxt.ButtonListener;
import lejos.nxt.Sound;
import lejos.util.Delay;
import java.util.*;
import java.io.*;
import com.rp25.networkCommunication.BlockingQueue;
// blockingQueue.take();

class RobotInterface extends Thread{

	String itemCode;
	int noOfItems;
	int buttonPressed;
	boolean jobComplete;
	int itemsCollected;
	boolean itemsDelivered;
	String function;
	BlockingQueue<String> queue = new BlockingQueue<String>;
	
	public RobotInterface(BlockingQueue<String> jobQueue) { // id of robot (1/2/3)
		jobComplete = false;
		itemsCollected = 0;
		itemsDelivered = false;
		getInfo();
		perform();
	}

	public void getInfo() {
		// BufferedReader fromBT = new BufferedReader(new InputStreamReader(System.in));
		// Will need to receive: itemCode, noOfItems, function
		// (cancelled/finished/pickup)
		String info;
	}

	@Override
	public void run() {
		while (true) {
			
		}
	}
	
	
	private void perform() {

		if (function.equals("pickup")) {
			pickingUp();
		}
		
		while (!itemsDelivered) {
		if (function.equals("finished")) {
			System.out.println("Job " + itemCode + " has been completed");
			itemsDelivered = true;
		}
		else if (function.equals("cancelled")) {
			System.out.println("Job " + itemCode + "has been cancelled");
			break;
		}
		}
	}
	
	/**
	 * Checks whether the button has been pressed the correct number of times, to pick up the
	 * correct number of items.
	 */
	private void pickingUp() {
		itemsCollected = 0;
		itemsDelivered = false;
		while (itemsCollected == 0) {
			Button.ENTER.addButtonListener(new ButtonListener() {
				@Override
				public void buttonReleased(Button _b) {
					Sound.beep();
					buttonPressed++;
					System.out.println(buttonPressed + " items collected.");
				}

				@Override
				public void buttonPressed(Button _b) {

				}
			});

			if (buttonPressed == noOfItems) {
				Sound.twoBeeps();
				System.out.println("All items for job " + itemCode + " have been collected");
				buttonPressed = 0;
				itemsCollected = 1;
				System.out.println("Delivering items for job: " + itemCode);
				sendData();
			} else if (function.equals("cancelled")) {
				System.out.println("Job " + itemCode + " has been cancelled");
				break;
			}
		}
		getInfo();
	}


	public void sendData() {
		// Need to show when the robot has collected up the correct number of items.
	}

	/**
	 * Returns whether the button on the robot has been pressed the correct no. of
	 * times
	 * 
	 * @return integer showing whether correct no. of items has been collected (0 or 1)
	 */
	public int getCollected() {
		return itemsCollected;
	}

}
