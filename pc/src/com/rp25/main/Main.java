package com.rp25.main;

import com.rp25.interfaces.warehouse.WarehouseState;
import com.rp25.interfaces.warehouse.gui.WarehouseInterfaceView;
import com.rp25.interfaces.warehouse.sim.WarehouseGridSim;
import com.rp25.jobSelectionAndAllocation.JobSelection;
import com.rp25.networkCommunication.PCMain;
import com.rp25.routeExecution.RouteExecutor;
import com.rp25.routePlanning.RouteIntegration;
import com.rp25.tools.Robot;

import lejos.geom.Point;

public class Main {

	static final String JOBS = "/jobs.csv";
	static final String ITEMS = "/items.csv";
	static final String LOCATIONS = "/locations.csv";
	static final String CANCELLATIONS = "/cancellations.csv";
	static final String TRAINING_SET = "/training_jobs.csv";
	static final String DROP_LOCATIONS = "/drops.csv";

	public static void main(String[] args) {
		JobSelection selection = new JobSelection(JOBS, ITEMS, LOCATIONS, CANCELLATIONS, TRAINING_SET);
		System.out.println("hi");
		Point r1Start = new Point(0, 7);
		Robot r1 = new Robot(1, (int) r1Start.getX(), (int) r1Start.getY());
		Point r2Start = new Point(1, 7);
		Robot r2 = new Robot(2, (int) r2Start.getX(), (int) r2Start.getY());
		Point r3Start = new Point(2, 7);
		Robot r3 = new Robot(3, (int) r3Start.getX(), (int) r3Start.getY());
		RouteIntegration routeStuff = new RouteIntegration();
		RouteExecutor executor = new RouteExecutor(r1, r2, r3, routeStuff);
		WarehouseState state = new WarehouseState();
		state.setExecutor(executor);
		state.addRobot(r1.getID(), r1);
		state.addRobot(r2.getID(), r2);
		state.addRobot(r3.getID(), r3);
		GivenData provider = new GivenData();
		WarehouseGridSim simulation = new WarehouseGridSim(state.getAllRobots(), provider.read(LOCATIONS),
				provider.read(DROP_LOCATIONS));
		new WarehouseInterfaceView(state, simulation);
		PCMain.main(args);
		// Delay.msDelay(10000);
		executor.Execute();
	}
}
