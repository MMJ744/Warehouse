package com.rp25.interfaces.warehouse;

import java.util.ArrayList;

import com.rp25.interfaces.warehouse.gui.WarehouseInterfaceView;
import com.rp25.tools.Job;
import com.rp25.tools.Robot;

public class WHIMainWithSimulation {

	public static void main(String[] args) {
		
		Job j1 = new Job("job 1");
		Job j2 = new Job("job 2");
		Job j3 = new Job("job 3");
		
		Robot r1 = new Robot(j1, 1, 3, 0);
		Robot r2 = new Robot(j2, 2, 2, 4);
		Robot r3 = new Robot(j3, 3, 3, 6);
		
		
		ArrayList<Robot> rs = new ArrayList<>();
		rs.add(r1);
		rs.add(r2);
		rs.add(r3);
		
		WarehouseGridSim simulation = new WarehouseGridSim(2);
		WarehouseInterfaceView view = new WarehouseInterfaceView(rs, simulation);

	}

}
