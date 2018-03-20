package com.rp25.routeExecution;

import java.awt.Point;
import java.util.Queue;

import org.apache.log4j.Logger;

import com.rp25.interfaces.warehouse.WarehouseState;
import com.rp25.networkCommunication.Sender;
import com.rp25.routePlanning.Route;
import com.rp25.routePlanning.RouteAction;
import com.rp25.tools.Robot;
import static com.rp25.routePlanning.RouteAction.ACTION.DROPOFF;
import static com.rp25.routePlanning.RouteAction.ACTION.PICKUP;
import static com.rp25.routePlanning.RouteAction.ACTION.MOVE;
import static com.rp25.routePlanning.RouteAction.ACTION.WAIT;

public class RouteExecutor {

	Robot r1, r2, r3;
	Orientation d1, d2, d3;
	Route route1, route2, route3;
	Boolean c1, c2, c3;
	int items;
	Thing t1, t2, t3;
	WarehouseState state;
	final static Logger logger = Logger.getLogger(RouteExecutor.class);

	public RouteExecutor(Robot _r1, Robot _r2, Robot _r3) {
		r1 = _r1;
		r2 = _r2;
		r3 = _r3;
	}

	public void Execute() {
		while (true) {
			checkRoutes();
			t1 = new Thing(route1.getNextAction().get(), r1, d1);
			t2 = new Thing(route2.getNextAction().get(), r2, d2);
			t3 = new Thing(route3.getNextAction().get(), r3, d3);
			t1.start();
			t2.start();
			t3.start();
			try {
				t1.join();
				t2.join();
				t3.join();
			} catch (InterruptedException e) {}
		}
	}

	private void checkRoutes() {
		if (route1.isRouteEmpty() || c1)
			route1 = null;// how ever i get a new route
		if (route2.isRouteEmpty() || c2)
			route2 = null;// how ever to get a new route
		if (route3.isRouteEmpty() || c3)
			route3 = null;// how ever to get a new route
		if(c1) {
			sendInterface("cancel", 1);
			c1 = false;
		}
		if(c2) {
			sendInterface("cancel", 2);
			c2 = true;
		}
		if(c3) {
			sendInterface("cancel", 3);	
		}
	}
	
	private boolean sendInterface(String action, int id) {
		boolean r = false;
		// r = how ever to send the action
		if (action.equalsIgnoreCase("pickup")) {
			// send the number of items to pickup
		}
		return r;
	}
	public void cancel(int id) {
		switch (id) {
		case 1:
			c1 = true;
		case 2:
			c2 = true;
		case 3:
			c3 = true;
		}
	}

	class Thing extends Thread {
		RouteAction a;
		Robot r;
		Orientation d;

		Thing(RouteAction _a, Robot _r, Orientation _d) {
			a = _a;
			r = _r;
			d = _d;
		}

		void Run() {
			
			if (a.getAction() != WAIT) {
				Point point = a.getPoint();
				orientate(point, r, d);
			}
			switch (a.getAction()) {
			case PICKUP:
				tellInterface("pickup", r.getID());
				break;
			case DROPOFF:
				tellInterface("finished", r.getID());
				break;
			default: break;
			}
		}
		
		private boolean tellInterface(String action, int id) {
			boolean r = false;
			// r = how ever to send the action
			if (action.equalsIgnoreCase("pickup")) {
				// send the number of items to pickup
			}
			return r;
		}

		private void updatePosition(Point next, Robot r) {
			r.updateCoordinates((int) next.getX(), (int) next.getY());
		}
		
		private void orientate(Point next, Robot r, Orientation d) {
			Orientation desired;
			if (next.x > r.getX())
				desired = Orientation.E;
			else if (next.x < r.getX())
				desired = Orientation.W;
			else if (next.y > r.getY())
				desired = Orientation.S;
			else if (next.y < r.getY())
				desired = Orientation.N;
			else {
				logger.debug("robot has been told to go to the location it is already at");
				return;
			}
			if (desired == d) {
				tellRobot(Command.FORWARD, r.getID());
				return;
			}
			tellRobot(Orientation.rotate(d, desired), r.getID()); // sends the command to point the robot in the right
			updatePosition(next, r); // direction
			d = desired;
		}

		private boolean tellRobot(Command c, int id) {
			boolean r = false;
			if (Sender.sendMove(id, c) == 0)
				r = true;
			return r;
		}
	}
}
