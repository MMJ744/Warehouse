package com.rp25.routeExecution;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Queue;

import org.apache.log4j.Logger;

import com.rp25.interfaces.warehouse.WarehouseState;
import com.rp25.networkCommunication.Sender;
import com.rp25.routePlanning.Route;
import com.rp25.routePlanning.RouteAction;
import com.rp25.routePlanning.RouteIntegration;
import com.rp25.tools.Job;
import com.rp25.tools.Robot;

import lejos.util.Delay;

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
	Navigator n1, n2, n3;
	Integer currentStep = -1;
	RouteIntegration routePlanner;
	final static Logger logger = Logger.getLogger(RouteExecutor.class);
	Collection<Job> completed = new ArrayList<Job>();

	public RouteExecutor(Robot _r1, Robot _r2, Robot _r3, RouteIntegration _routePlanner) {
		routePlanner = _routePlanner;
		r1 = _r1;
		r2 = _r2;
		r3 = _r3;
		c1 = false;
		c2 = false;
		c3 = false;
		d1 = Orientation.N;
		d2 = Orientation.N;
		d3 = Orientation.N;
	}

	public Collection<Job> getCompletedJobs() {
		return completed;
	}

	public void Execute() {
		System.out.println("exe");
		while (true) {
			++currentStep;
			checkRoutes();
			System.out.println("" + (route1 == null) + (r1 == null) + (d1 == null));
			n1 = new Navigator(route1.getNextAction().get(), r1, d1);
			n2 = new Navigator(route2.getNextAction().get(), r2, d2);
			n3 = new Navigator(route3.getNextAction().get(), r3, d3);
			n1.start();
			n2.start();
			n3.start();

			try {
				n1.join();
				n2.join();
				n3.join();
			} catch (InterruptedException e) {
			}

		}
	}

	private void checkRoutes() {
		try {
			if (route1 == null || route1.isRouteEmpty() || c1) {
				if (!c1 && route1 != null) {
					completed.add(route1.getJob());
					//sendInterface("finished", r1.getID());
				}
				route1 = routePlanner.planRoute(r1, currentStep);
				r1.setCurrentJob(route1.getJob());
			}
			if (route2 == null || route2.isRouteEmpty() || c2) {
				if (!c2 && route2 != null) {
					completed.add(route2.getJob());
					//sendInterface("finished", r2.getID());
				}
				route2 = routePlanner.planRoute(r2, currentStep);
				r2.setCurrentJob(route2.getJob());
			}
			if (route3 == null || route3.isRouteEmpty() || c3) {
				if (!c3 && route3 != null) {
					completed.add(route3.getJob());
					//sendInterface("finished", r3.getID());
				}
				route3 = routePlanner.planRoute(r3, currentStep);
				r3.setCurrentJob(route3.getJob());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (c1) {
			//sendInterface("cancel", 1);
			c1 = false;
		}
		if (c2) {
			//sendInterface("cancel", 2);
			c2 = false;
		}
		if (c3) {
			//sendInterface("cancel", 3);
			c3 = false;
		}
	}

	private int sendInterface(String action, int id) {
		return Sender.sendJob(id, action);
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

	private class Navigator extends Thread {
		RouteAction a;
		Robot r;
		Orientation d;

		Navigator(RouteAction _a, Robot _r, Orientation _d) {
			a = _a;
			r = _r;
			d = _d;
		}

		public void run() {
			if (a.getAction() != WAIT) {
				Point point = a.getPoint();

				System.out.println(r.getID() + " going to " + point.toString());

				orientate(point, r, d);
			}
			/*switch (a.getAction()) {
			case PICKUP:
				System.out.println("pickng up");
				tellInterface(r.getID(), "pickup", a.getItemID(), a.getItemCount());
				System.out.println("Waiting");
				Sender.pollButton(r.getID());
				System.out.println("done");
				break;
			case DROPOFF:
				//tellInterface(r.getID(), "finished", "", 0);
				break;
			default:
				break;
			} */
		}

		private int tellInterface(int id, String action, String itemID, Integer numberOfItem) {
			// either: cancelled. finished. pickup.
			System.out.println(1);
			int	r = Sender.sendJob(id, action);
			System.out.println(2);
			if (action.equalsIgnoreCase("pickup")) {
				Sender.sendJob(id, itemID);
				System.out.println(3);
				Sender.sendJob(id, numberOfItem.toString());
				System.out.println(4);
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
			else if (next.y < r.getY())
				desired = Orientation.S;
			else if (next.y > r.getY())
				desired = Orientation.N;
			else {
				logger.debug("robot has been told to go to the location it is already at");
				return;
			}
			updatePosition(next, r); // direction
			if (desired == d) {
				tellRobot(Command.FORWARD, r.getID());
				return;
			}
			tellRobot(Orientation.rotate(d, desired), r.getID()); // sends the command to point the robot in the right
			switch (r.getID()) {
			case 1:
				d1 = desired;
				break;
			case 2:
				d2 = desired;
				break;
			case 3:
				d3 = desired;
				break;
			}
		}

		private boolean tellRobot(Command c, int id) {
			System.out.println("send command: " + c + " to " + id);
			boolean r = false;
			if (Sender.sendMove(id, c) == 0)
				r = true;
			else
				logger.error("robot failed motion");
			return r;
		}
	}
}
