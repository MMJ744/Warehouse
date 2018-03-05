package com.rp25.routeExecution;

import java.awt.Point;
import java.util.Queue;

public class RouteExecutor {

	final int robotID;
	Point current;
	Orientation direction;
	Queue<Point> path;

	public RouteExecutor(int robotNumber, int startingX, int startingY) {
		robotID = robotNumber;
		current.x = startingX;
		current.y = startingY;
		direction = Orientation.N;
	}

	public boolean execute(Queue<Point> _path) {
		Point next;
		path = _path;
		while ((next = path.poll()) != null) {
			orentate(next);
			tellRobot(Command.FORWARD);
		}
		return true;
	}

	private void orentate(Point next) {
		Orientation desired;
		if(next.x > current.x)
			desired = Orientation.E;
		else if(next.x < current.x)
			desired = Orientation.W;
		else if(next.y > current.y)
			desired = Orientation.S;
		else if(next.y < current.y)
			desired = Orientation.N;
		else {
			System.err.println("robot has been told to go to the location it is already at");
			return;
		}
		if(desired == direction)
			return;
		tellRobot(Orientation.rotate(direction, desired)); //sends the command to point the robot in the right direction
	}
	
	private boolean tellRobot(Command c) {
		boolean r = false;
		// r = how ever to send to c to the robot
		return r;
	}
}
