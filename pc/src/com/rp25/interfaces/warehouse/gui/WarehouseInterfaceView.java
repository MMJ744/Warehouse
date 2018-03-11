package com.rp25.interfaces.warehouse.gui;

import java.util.ArrayList;

import com.rp25.interfaces.warehouse.sim.WarehouseGridSim;
import com.rp25.interfaces.warehouse.WarehouseState;
import com.rp25.tools.Robot;

public class WarehouseInterfaceView {
	WarehouseInterfaceFrame frame;
	WarehouseState state;
	
	public WarehouseInterfaceView(ArrayList<Robot> list) {
		frame = new WarehouseInterfaceFrame("Robot Warehouse Interface");
		state = new WarehouseState();
		initialise(list);
		frame.setVisible(true);
	}
	
	public WarehouseInterfaceView(ArrayList<Robot> list, WarehouseGridSim sim) {
		frame = new WarehouseInterfaceFrame("Robot Warehouse Interface", sim);
		state = new WarehouseState();
		initialise(list);
		frame.setVisible(true);
	}
	
	private void initialise(ArrayList<Robot> list) {
		for (Robot r : list) {
			state.addRobot(r.getID(), r);
			frame.addInfo(r);
		}
	}
	
	public void update(int id) {
		state.getRobot(id);
	}
}
