package com.rp25.robotInterface;

import lejos.nxt.Button;
import lejos.nxt.ButtonListener;
import lejos.nxt.Sound;

import com.rp25.tools.BlockingQueue;


public class RobotInterface extends Thread {

	String itemCode;
	int noOfItems;
	int buttonPressed;
	boolean jobComplete;
	int itemsCollected;
	boolean itemsDelivered;
	String function;
	BlockingQueue<String> queue;
	BlockingQueue<Integer> feedback;

	public RobotInterface(BlockingQueue<String> jobQueue, BlockingQueue<Integer> feedback) { // id of robot (1/2/3)
		jobComplete = false;
		itemsCollected = 0;
		itemsDelivered = false;
		this.feedback = feedback;
		queue = jobQueue;
	}

	@Override
	public void run() {
		System.out.println("RUNNING");
		while (true) {		
			function = queue.take();
			System.out.println(function);
			if (function.equals("pickup")) {
				System.out.println("ENTERING PICKUP");
				Sound.setVolume(100);
				Sound.beep();
				itemCode = queue.take();
				String noOfItemsString = queue.take();
				noOfItems = Integer.valueOf(noOfItemsString);
				System.out.println("Please give: " + noOfItems + " of: " + itemCode);
				pickingUp();
			}

			else if (function.equals("finished")) {
				System.out.println("Job " + itemCode + " has been completed");
				itemsDelivered = true;
			} else if (function.equals("cancelled")) {
				System.out.println("Job has been cancelled, dropping items");
				break;
			}
			else{System.out.println("FUCKED");}
			sendData();
			// send back 1 for true.
		}
	}

	/**
	 * Checks whether the button has been pressed the correct number of times, to
	 * pick up the correct number of items.
	 */
	private void pickingUp() {
		itemsCollected = 0;
		itemsDelivered = false;
		Sound.beepSequenceUp();
		System.out.println("Please give " + noOfItems + " of " + itemCode);
		for(int i = 0; i < noOfItems; i ++){
			Button.waitForAnyPress();
		}
		System.out.println("thanks for items");
		/*while (itemsCollected == 0) {
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

			} 
		} */
	}

	public void sendData() {
		feedback.push(1);
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
