package com.rp25.routeExecution;

import java.awt.Point;
import java.util.Map;
import java.util.Queue;

import org.apache.log4j.Logger;

public class RouteExecutor {

	final int robotID;
	Point current;
	Orientation direction;
	Queue<Point> path;
	Boolean cancled;
	final static Logger logger = Logger.getLogger(RouteExecutor.class);

	public RouteExecutor(int robotNumber, int startingX, int startingY) {
		robotID = robotNumber;
		current.x = startingX;
		current.y = startingY;
		direction = Orientation.N;
		cancled = false;
	}

	public boolean execute(Queue<Point> _path, String action) {
		Point next;
		path = _path;
		while ( !path.isEmpty() && !cancled) {
			next = (Point) path.pop();
			orentate(next);
			tellRobot(Command.FORWARD);
			updatePosition(next);
		}
		if(cancled) {
			cancled = false;
			return false;
		}
		if(action == "finished" || action == "cancled") {
			tellInterface(action);
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
			logger.debug("robot has been told to go to the location it is already at");
			return;
		}
		if(desired == direction) {
			tellRobot(Command.FORWARD);
			return;
		}
		tellRobot(Orientation.rotate(direction, desired)); //sends the command to point the robot in the right direction
	}
	
	private boolean tellRobot(Command c) {
		boolean r = false;
		// r = how ever to send to c to the robot
		return r;
	}
	
	public void cancel() {
		cancled = true;
	}
	
	private boolean tellInterface(String action) {
		
		boolean r = false;
		// r = how ever to send the map to the robot
		return r;
	}
	
	private void updatePosition(Point next) {
		//pass the new point to warehouse interface.
	}
}
