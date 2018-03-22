package com.rp25.robotInterface;

import lejos.nxt.Button;
import lejos.nxt.ButtonListener;
import lejos.nxt.Sound;
import lejos.util.Delay;
import java.util.*;
import com.rp25.tools.BlockingQueue;

import java.io.*;

class RobotInterface extends Thread {

	String itemCode;
	int noOfItems;
	int buttonPressed;
	boolean jobComplete;
	int itemsCollected;
	boolean itemsDelivered;
	String function;
	java.util.concurrent.BlockingQueue<String> queue;

	public RobotInterface(BlockingQueue<String> jobQueue) { // id of robot (1/2/3)
		jobComplete = false;
		itemsCollected = 0;
		itemsDelivered = false;
		queue = jobQueue;
		run();
	}


	@Override
	public void run() {
		while (true) {
			function = queue.take();
			if function.equals("pickup") {
				itemCode = queue.take();
				String noOfItemsString = queue.take();
				noOfItems.valueOf(noOfItemsString);
				pickingUp();
			}
			
				if (function.equals("finished")) {
					System.out.println("Job " + itemCode + " has been completed");
					itemsDelivered = true;
				} else if (function.equals("cancelled")) {
					System.out.println("Job has been cancelled, dropping items");
					break;
				}
			// send back 1 for true.
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
			} else if (function.equals("cancelled")) {
				System.out.println("Job " + itemCode + "has been cancelled");
				break;
			}
		}
	}

	/**
	 * Checks whether the button has been pressed the correct number of times, to
	 * pick up the correct number of items.
	 */
	private void pickingUp() {
		itemsCollected = 0;
		itemsDelivered = false;
		Sound.playTone(1000, 100, 100);
		while (itemsCollected == 0) {
			Button.ENTER.addButtonListener(new ButtonListener() {
				@Override
				public void buttonReleased(Button _b) {
					Sound.beep();
					buttonPressed++;
				}

				@Override
				public void buttonPressed(Button _b) {
					
				}
			});

			if (buttonPressed == noOfItems) {
				Sound.twoBeeps();
				System.out.println("All items have been collected");
				buttonPressed = 0;
				itemsCollected = 1;
				sendData();
			} else if (function.equals("cancelled")) {
				System.out.println("Job has been cancelled");
				break;
			}
		}
	}

	public void sendData() {
		// Need to show when the robot has collected up the correct number of items.
	}

	/**
	 * Returns whether the button on the robot has been pressed the correct no. of
	 * times
	 * 
	 * @return integer showing whether correct no. of items has been collected (0 or
	 *         1)
	 */
	public int getCollected() {
		return itemsCollected;
	}

}
