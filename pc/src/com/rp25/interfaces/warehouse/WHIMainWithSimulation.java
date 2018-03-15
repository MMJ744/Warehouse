package com.rp25.interfaces.warehouse;

import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.rp25.interfaces.warehouse.cli.WarehouseCLI;
import com.rp25.interfaces.warehouse.gui.WarehouseInterfaceView;
import com.rp25.interfaces.warehouse.sim.WarehouseGridSim;
import com.rp25.tools.Job;
import com.rp25.tools.Robot;

public class WHIMainWithSimulation {

	final static Logger logger = Logger.getLogger(WHIMainWithSimulation.class);
	
	public static void main(String[] args) {
		
		Job j1 = new Job("job 1");
		Job j2 = new Job("job 2");
		
		Robot r1 = new Robot(j1, 1, 3, 0);
		Robot r2 = new Robot(j2, 2, 2, 4);
		
		WarehouseState state = new WarehouseState();
		state.addRobot(r1.getID(), r1);
		state.addRobot(r2.getID(), r2);
		
		logger.debug("Robot " + state.getRobot(r1.getID()).getID() + " added");
		logger.debug("Robot " + state.getRobot(r2.getID()).getID() + " added");
		
		WarehouseGridSim simulation = new WarehouseGridSim(state.getAllRobots());
		new WarehouseInterfaceView(state, simulation);
		(new Thread(new WarehouseCLI(state))).start();
		
	}

}
