package com.rp25.interfaces.warehouse.gui;

import com.rp25.interfaces.warehouse.WarehouseState;
import com.rp25.tools.Robot;

import lejos.util.Delay;

public class FrameUpdater extends Thread {
	
	private final int TIME_PER_UPDATE = 1000;
	private WarehouseInterfaceFrame frame;
	private WarehouseState warehouseState;
	
	public FrameUpdater(WarehouseInterfaceFrame _frame, WarehouseState _state) {
		frame = _frame;
		warehouseState = _state;
	}

	@Override
	public void run() {
		while(true) {
			
			for (Robot bot : warehouseState.getAllRobots()) {
				frame.updateInfo(bot);
			}
			
			Delay.msDelay(TIME_PER_UPDATE);
		}
	}

}
