package com.rp25.interfaces.warehouse;

import java.awt.Point;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.rp25.interfaces.warehouse.cli.WarehouseCLI;
import com.rp25.interfaces.warehouse.gui.WarehouseInterfaceView;
import com.rp25.interfaces.warehouse.sim.WarehouseGridSim;
import com.rp25.routeExecution.RouteExecutor;
import com.rp25.tools.Job;
import com.rp25.tools.Robot;

public class WHIMainWithSimulation {

	final static Logger logger = Logger.getLogger(WHIMainWithSimulation.class);

	public static void main(String[] args) {

		Job j1 = new Job("job 1");
		Job j2 = new Job("job 2");

		Robot r1 = new Robot(1, 3, 0);
		r1.setCurrentJob(j1);
		Robot r2 = new Robot(2, 2, 4);
		r2.setCurrentJob(j2);
		Robot r3 = new Robot(3, 3, 1);

		WarehouseState state = new WarehouseState();
		state.addRobot(r1.getID(), r1);
		state.addRobot(r2.getID(), r2);
		state.addRobot(r3.getID(), r3);

		logger.debug("Robot " + state.getRobot(r1.getID()).getID() + " added");
		logger.debug("Robot " + state.getRobot(r2.getID()).getID() + " added");
		logger.debug("Robot " + state.getRobot(r3.getID()).getID() + " added");

		ArrayList<Point> pickups = new ArrayList<>();
		pickups.add(new Point(2, 1));
		pickups.add(new Point(2, 2));
		pickups.add(new Point(2, 3));
		pickups.add(new Point(2, 4));
		pickups.add(new Point(2, 5));
		pickups.add(new Point(3, 1));
		pickups.add(new Point(3, 2));
		pickups.add(new Point(3, 3));
		pickups.add(new Point(3, 4));
		pickups.add(new Point(3, 5));
		pickups.add(new Point(5, 1));
		pickups.add(new Point(5, 2));
		pickups.add(new Point(5, 3));
		pickups.add(new Point(5, 4));
		pickups.add(new Point(5, 5));
		pickups.add(new Point(6, 1));
		pickups.add(new Point(6, 2));
		pickups.add(new Point(6, 3));
		pickups.add(new Point(6, 4));
		pickups.add(new Point(6, 5));
		pickups.add(new Point(8, 1));
		pickups.add(new Point(8, 2));
		pickups.add(new Point(8, 3));
		pickups.add(new Point(8, 4));
		pickups.add(new Point(8, 5));
		pickups.add(new Point(9, 1));
		pickups.add(new Point(9, 2));
		pickups.add(new Point(9, 3));
		pickups.add(new Point(9, 4));
		pickups.add(new Point(9, 5));

		ArrayList<Point> drops = new ArrayList<>();
		drops.add(new Point(4, 7));
		drops.add(new Point(7, 7));

		RouteExecutor executor = new RouteExecutor(r1, r2, r3, null);

		WarehouseGridSim simulation = new WarehouseGridSim(state.getAllRobots(), pickups, drops);
		state.setExecutor(executor);

		new WarehouseInterfaceView(state, simulation);
		(new Thread(new WarehouseCLI(state))).start();

	}

}
