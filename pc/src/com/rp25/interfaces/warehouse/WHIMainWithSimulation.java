package com.rp25.interfaces.warehouse;

import java.util.ArrayList;

import com.rp25.interfaces.warehouse.gui.WarehouseInterfaceView;
import com.rp25.interfaces.warehouse.sim.WarehouseGridSim;
import com.rp25.tools.Job;
import com.rp25.tools.Robot;

public class WHIMainWithSimulation {

	public static void main(String[] args) {
		
		Job j1 = new Job("job 1");
		Job j2 = new Job("job 2");
		
		Robot r1 = new Robot(j1, 1, 3, 0);
		Robot r2 = new Robot(j2, 2, 2, 4);
		
		
		ArrayList<Robot> rs = new ArrayList<>();
		rs.add(r1);
		rs.add(r2);
		
		WarehouseGridSim simulation = new WarehouseGridSim(2);
		new WarehouseInterfaceView(rs, simulation);

	}

}
