package com.rp25.interfaces.warehouse.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.rp25.interfaces.warehouse.sim.WarehouseGridSim;
import com.rp25.interfaces.warehouse.WarehouseState;
import com.rp25.tools.Robot;

/**
 * Handles the initialisation of the GUI
 * @author ass782
 *
 */
public class WarehouseInterfaceView {
	
	WarehouseInterfaceFrame frame;
	WarehouseState warehouseState;
	boolean paused = false;
		
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
		frame.addPauseListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				warehouseState.getExecutor().setPause();
				
			}
		});
		
		for (Robot r : warehouseState.getAllRobots()) {
			frame.addInfo(r);
			frame.addListeners(r.getID(), new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					warehouseState.getExecutor().cancel(r.getID());
				}
			});
		}
	}
}
