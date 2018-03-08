package com.rp25.interfaces.warehouse;

import java.util.ArrayList;
import com.rp25.interfaces.warehouse.gui.WarehouseInterfaceFrame;

import tools.Job;
import tools.Robot;

public class WarehouseInterfaceMain {
	
	public static void main(String[] args) {

		WarehouseInterfaceFrame frame = new WarehouseInterfaceFrame("Robot Warehouse Interface");
		
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
		
		for (Robot r : rs) {
			frame.addInfo(r.toString());
		}
	}

}
