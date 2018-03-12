package com.rp25.routeExecution;

import java.awt.Point;
import java.util.Queue;

import org.apache.log4j.Logger;

import com.rp25.interfaces.warehouse.WarehouseState;
import com.rp25.interfaces.warehouse.sim.WarehouseGridSim;
import com.rp25.networkCommunication.Sender;
import com.rp25.routePlanning.Route;
import com.rp25.routePlanning.Route.ACTION;

public class RouteExecutor {

	final int robotID;
	Point current;
	Orientation direction;
	Queue<Point> path;
	Boolean cancled;
	ACTION action;
	int items;
	WarehouseState state;
	final static Logger logger = Logger.getLogger(RouteExecutor.class);

	public RouteExecutor(int robotNumber, int startingX, int startingY, WarehouseState _state) {
		robotID = robotNumber;
		current.setLocation(startingX, startingY);
		current.y = startingY;
		direction = Orientation.N;
		cancled = false;
		state = _state;
	}

	public boolean execute(Route route) {
		Point next;
		path = route.getPath();
		action = route.getAction();
		items = route.getItemCount();
		while (!path.isEmpty() && !cancled) {
			next = (Point) path.pop();
			orentate(next);
			updatePosition(next);
			logger.debug("moved to next point");
		}
		if (cancled) {
			logger.debug("job cancled");
			tellInterface(action.toString());
			cancled = false;
			return false;
		}
		tellInterface(action.toString());
		logger.debug("path done");
		return true;
	}

	private void orentate(Point next) {
		Orientation desired;
		if (next.x > current.x)
			desired = Orientation.E;
		else if (next.x < current.x)
			desired = Orientation.W;
		else if (next.y > current.y)
			desired = Orientation.S;
		else if (next.y < current.y)
			desired = Orientation.N;
		else {
			logger.debug("robot has been told to go to the location it is already at");
			return;
		}
		if (desired == direction) {
			tellRobot(Command.FORWARD);
			return;
		}
		tellRobot(Orientation.rotate(direction, desired)); // sends the command to point the robot in the right
															// direction
		direction = desired;
	}

	private boolean tellRobot(Command c) {
		boolean r = false;
		if(Sender.sendMove(robotID, c) == 0)
			r = true;
		return r;
	}

	public void cancel() {
		cancled = true;
	}

	private boolean tellInterface(String action) {
		boolean r = false;
		// r = how ever to send the action
		if(action.equalsIgnoreCase("pickup")){
			//send the number of items to pickup
		}
		return r;
	}

	private void updatePosition(Point next) {
		state.updateBotPos(robotID, (int) next.getX(), (int) next.getY());
	}
}
