package com.rp25.main;


import com.rp25.interfaces.warehouse.WarehouseState;
import com.rp25.interfaces.warehouse.gui.WarehouseInterfaceView;
import com.rp25.interfaces.warehouse.sim.WarehouseGridSim;
import com.rp25.networkCommunication.PCMain;
import com.rp25.routePlanning.RoutePlan;
import com.rp25.tools.Robot;
import com.rp25.routePlanning.*;

import lejos.geom.Point;

public class Main {

	public static void main(String[] args) {
		Point r1Start = new Point(4,7);
		Robot r1 = new Robot(null, 1, (int) r1Start.getX(), (int) r1Start.getY());
		WarehouseState state = new WarehouseState();
		state.addRobot(r1.getID(), r1);
		WarehouseGridSim simulation = new WarehouseGridSim(state.getAllRobots());
		new WarehouseInterfaceView(state, simulation);
		RoutePlan route = new RoutePlan();
		route.start();
		PCMain.main(args);
		RobotThread robot1 = new RobotThread(1, (int) 4, (int) 7, state);
		robot1.start();
		Route r;
		while((r = route.routeList.poll()) != null) {
			robot1.executor.execute(r);
		}
	}

}
