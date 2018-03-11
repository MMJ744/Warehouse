package com.rp25.interfaces.warehouse.gui;

import com.rp25.interfaces.warehouse.WarehouseState;
import com.rp25.tools.Robot;

import rp.util.Rate;

public class FrameUpdater extends Thread {
	
	private final int TIME_PER_UPDATE = 2000;
	private WarehouseInterfaceFrame frame;
	private WarehouseState warehouseState;
	
	public FrameUpdater(WarehouseInterfaceFrame _frame, WarehouseState _state) {
		frame = _frame;
		warehouseState = _state;
	}

	@Override
	public void run() {
		Rate r = new Rate(TIME_PER_UPDATE);
		while(true) {
			
			for (Robot bot : warehouseState.getAllRobots()) {
				frame.updateInfo(bot);
			}
			
			r.sleep();
		}
	}

}
