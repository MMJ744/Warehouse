package com.rp25.main;

import java.awt.Point;
import java.util.Queue;

import com.rp25.interfaces.warehouse.WarehouseState;
import com.rp25.interfaces.warehouse.sim.WarehouseGridSim;
import com.rp25.routeExecution.RouteExecutor;

public class RobotThread extends Thread {
	int robotID;
	int x;
	int y;
	WarehouseState state;
	RouteExecutor executor;
	public RobotThread(int id, int _x , int _y, WarehouseState _state) {
		robotID = id;
		x = _x;
		y = _y;
		state = _state;
	}
	public void run() {
		executor = new RouteExecutor(robotID, x, y, state);
	}
	
	public void executeJob(Queue<Point> path) {
		
	}
}
