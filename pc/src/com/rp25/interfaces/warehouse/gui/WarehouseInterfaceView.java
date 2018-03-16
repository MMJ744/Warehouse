package com.rp25.interfaces.warehouse.gui;

import java.util.ArrayList;

import com.rp25.interfaces.warehouse.sim.WarehouseGridSim;
import com.rp25.interfaces.warehouse.WarehouseState;
import com.rp25.tools.Robot;

public class WarehouseInterfaceView {
	WarehouseInterfaceFrame frame;
	WarehouseState warehouseState;
		
	public WarehouseInterfaceView(WarehouseState state, WarehouseGridSim sim) {
		frame = new WarehouseInterfaceFrame("Robot Warehouse Interface", sim);
		warehouseState = state;
		initialise();
		frame.setVisible(true);
		
		Thread updateChecker = new FrameUpdater(frame, warehouseState);
		updateChecker.setDaemon(true);
		updateChecker.start();
	}
	
	private void initialise() {
		for (Robot r : warehouseState.getAllRobots()) {
			frame.addInfo(r);
		}
	}
	
	public void update(int id) {
		warehouseState.getRobot(id);
	}
}
