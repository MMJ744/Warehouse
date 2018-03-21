package com.rp25.main;


import com.rp25.interfaces.warehouse.WarehouseState;
import com.rp25.interfaces.warehouse.gui.WarehouseInterfaceView;
import com.rp25.interfaces.warehouse.sim.WarehouseGridSim;
import com.rp25.jobSelectionAndAllocation.Cancellation;
import com.rp25.jobSelectionAndAllocation.JobSelection;
import com.rp25.networkCommunication.PCMain;
import com.rp25.routeExecution.RouteExecutor;
import com.rp25.routePlanning.RoutePlan;
import com.rp25.tools.Robot;
import com.rp25.routePlanning.*;

import lejos.geom.Point;

public class Main {

	public static void main(String[] args) {
		JobSelection selection = new JobSelection("jobs.csv", "items.csv", "locations.csv", "cancellations.csv", "training_jobs.csv");
		Point r1Start = new Point(4,7);
		Robot r1 = new Robot(1, (int) r1Start.getX(), (int) r1Start.getY());
		Point r2Start = new Point(0,1);
		Robot r2 = new Robot(2, (int) r2Start.getX(), (int) r2Start.getY());
		Point r3Start = new Point(0,0);
		Robot r3 = new Robot(3, (int) r3Start.getX(), (int) r3Start.getY());
		//RouteIntegration routeStuff = new RouteIntegration();
		//RouteExecutor executor = new RouteExecutor(r1, r2, r3, routeStuff);
		WarehouseState state = new WarehouseState();
		//state.setExecutor(executor);
		state.addRobot(r1.getID(), r1);
		state.addRobot(r2.getID(), r2);
		state.addRobot(r3.getID(), r3);
		WarehouseGridSim simulation = new WarehouseGridSim(state.getAllRobots(), 
										  GivenData.read("locations.csv"), 
										  GivenData.read("drops.csv"));
		new WarehouseInterfaceView(state, simulation);
		PCMain.main(args);
		//executor.Execute();
	}
}
