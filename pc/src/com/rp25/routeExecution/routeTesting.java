package com.rp25.routeExecution;

import static com.rp25.routePlanning.RouteAction.ACTION.WAIT;

import java.awt.Point;
import java.util.ArrayList;

import com.rp25.routePlanning.Route;
import com.rp25.routePlanning.RouteAction;

public class routeTesting {
	Point pos;
	Orientation d;
	Route route;
	RouteAction a;
		
	ArrayList<Command> transalte(Point p, Orientation or, Route r) {
		ArrayList<Command> list = new ArrayList<Command>();
		while(!r.isRouteEmpty()) {
			a = r.getNextAction();
			if (a.getAction() != WAIT) {
				Point point = a.getPoint();
				list.add(orientate(point, d));
			} else {
				
			}
		}
		return list;
	}
	
	private Command orientate(Point next, Orientation d) {
		Orientation desired;
		if (next.x > pos.getX())
			desired = Orientation.E;
		else if (next.x < pos.getX())
			desired = Orientation.W;
		else if (next.y > pos.getY())
			desired = Orientation.S;
		else if (next.y < pos.getY())
			desired = Orientation.N;
		else
			return null;
		pos = next;
		if (desired == d) {
			return Command.FORWARD;
		}
		Command c = (Orientation.rotate(d, desired)); // sends the command to point the robot in the right
		d = desired;
		return c; // e
	}
}
