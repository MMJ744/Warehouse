package com.rp25.tools;

/**
 * A class to hold information about the coordinates and current job of
 * each robot such that it can be referenced in other classes. 
 * @author ASS782
 *
 */

public class Robot {
	
	/**
	 * The identification number of the robot.
	 */
	private final int id;
	
	/**
	 * The current job the robot is assigned to.
	 */
	private Job currentJob;
	
	/**
	 * Coordinates of the robot.
	 */
	private int x, y;
	
	/**
	 * Constructor to initialize the robot class.
	 * @param j Current job that the robot is assigned to.
	 * @param idNumber ID number of the robot.
	 * @param xCoor X-coordinate of the robot.
	 * @param yCoor Y-coordinate of the robot.
	 */
	public Robot(Job j, int idNumber, int xCoor, int yCoor) {
		currentJob = j;
		id = idNumber;
		x = xCoor;
		y = yCoor;
	}
	
	/**
	 * @return ID number of the robot
	 */
	public int getID() {
		return id;
	}

	/**
	 * @return Current assigned job.
	 */
	public Job getCurrentJob() {
		return currentJob;
	}

	/**
	 * Allows for updating the current job of the robot.
	 * @param newJob The new job.
	 */
	public void setCurrentJob(Job newJob) {
		currentJob = newJob;
	}
	
	/**
	 * @return x-coordinate.
	 */
	public int getX() {
		return x;
	}
	
	/**
	 * @return y-coordinate.
	 */
	public int getY() {
		return y;
	}
	
	/**
	 * Allows for updating the coordinates of the robot.
	 * @param newX new x-coordinate.
	 * @param newY new y-coordinate.
	 */
	public void updateCoordinates(int newX, int newY) {
		x = newX;
		y = newY;
	}
	
	/*
	 * toString() method for interface purposes
	 */
	public String toString() {
		StringBuilder output = new StringBuilder();
		
			String strID  = "Robot ID: " + this.getID();
			String strPos = "Position: (" + this.getX() + ", " + this.getY() + ")";
			String strJob =	(this.getCurrentJob() == null) ?
								"Current Job: NONE" :
								"CurrentJob: " + this.getCurrentJob().getName();
			
			output.append(strID)
				  .append("\r\n")
				  .append(strPos)
				  .append("\r\n")
				  .append(strJob);
		
		return output.toString();
	}
}
