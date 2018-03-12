package com.rp25.main;

import java.awt.Point;
import java.util.Queue;

import com.rp25.interfaces.warehouse.WarehouseState;
import com.rp25.interfaces.warehouse.sim.WarehouseGridSim;
import com.rp25.routeExecution.RouteExecutor;

public class RobotThread extends Thread {
	int robotID;
	RouteExecutor executor;
	public RobotThread(int id, int x , int y, WarehouseState state) {
		robotID = id;
		executor = new RouteExecutor(robotID, 1, 1, state);
	}
	public void run() {
		
	}
	
	public void executeJob(Queue<Point> path) {
		
	}
}
