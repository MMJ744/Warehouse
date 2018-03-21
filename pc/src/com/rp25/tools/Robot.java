package com.rp25.tools;

import org.apache.log4j.Logger;

/**
 * A class to hold information about the coordinates and current job of each
 * robot such that it can be referenced in other classes.
 * 
 * @author ASS782
 *
 */

public class Robot {

	private final int id;
	private Job currentJob;
	private String currentAction;
	private int x, y;

	private final static Logger logger = Logger.getLogger(Robot.class);

	/**
	 * Constructor to initialize the robot class.
	 * @param j Current job that the robot is assigned to.
	 * @param idNumber ID number of the robot.
	 * @param xCoor X-coordinate of the robot.
	 * @param yCoor Y-coordinate of the robot.
	 */
	public Robot(int idNumber, int xCoor, int yCoor) {
		id = idNumber;
		x = xCoor;
		y = yCoor;
	}

	/** @return ID number of the robot */
	public int getID() {
		return id;
	}

	/** @return Current assigned job. */
	public Job getCurrentJob() {
		return currentJob;
	}

	/**
	 * Allows for updating the current job of the robot.
	 * @param newJob The new job.
	 */
	public void setCurrentJob(Job newJob) {
		currentJob = newJob;
		logger.debug("Job set to: " + getCurrentJob().getName());

	}

	/** @return String of the current action. */
	public String getCurrentAction() {
		return currentAction;
	}

	/**
	 * Updates the current action.
	 * @param s String of the new action.
	 */
	public void setCurrentAction(String s) {
		currentAction = s;
	}

	/** @return x-coordinate. */
	public int getX() {
		return x;
	}

	/** @return y-coordinate. */
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

	public String nameString() {
		return "ID Number: " + getID();
	}

	public String posString() {
		return "Position: (" + getX() + ", " + getY() + ")";
	}

	public String actionString() {
		return (currentAction == null) ? "Current Action: NONE" : "Current Action: " + getCurrentAction();
	}

	public String jobString() {
		return (currentJob == null) ? "Current Job: NONE" : "CurrentJob: " + currentJob.getName();
	}

	public String jobPartString() {
		StringBuilder parts = new StringBuilder();

		if (currentJob == null || currentJob.getParts().isEmpty())
			return "NO PARTS";

		for (JobPart part : currentJob.getParts()) {
			parts.append(part.getName()).append(": get ").append(part.getNumOfItems()).append(" at position ")
					.append("(" + part.getX() + ", " + part.getY() + ")").append("\r\n");
		}

		return parts.toString();
	}
}
