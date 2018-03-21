package com.rp25.routePlanning;

import java.awt.Point;

public class RouteAction {
	public enum ACTION { MOVE, WAIT, PICKUP, DROPOFF }
	
	ACTION action;
	Point point;
	int robotID;
	
	public RouteAction(ACTION action, Point point) {
		this(action, point, -1);
	}
	
	public RouteAction(ACTION action, Point point, int robotID) {
		this.action = action;
		this.point = point;
		this.robotID = robotID;
	}
	
	public Point getPoint() {
		return point;
	}
	
	public ACTION getAction() {
		return action;
	}
	
	@Override
	public String toString() {
		return robotID + " -> ACTION: " + enumToString(action) + " x: " + point.x + ", y: " + point.y;
	}
	
	public String enumToString(ACTION action) {
		switch(action) {
		case MOVE:
			return "MOVE to";
		case WAIT:
			return "WAIT at";
		case PICKUP:
			return "PICKUP at";
		case DROPOFF:
			return "DROPOFF at";
		}
		
		return "";
	}
}
