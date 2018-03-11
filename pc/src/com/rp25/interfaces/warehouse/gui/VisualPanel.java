package com.rp25.interfaces.warehouse.gui;

import java.awt.Dimension;

import javax.swing.JPanel;

import com.rp25.interfaces.warehouse.sim.WarehouseGridSim;

import rp.robotics.mapping.GridMap;
import rp.robotics.mapping.MapUtils;
import rp.robotics.visualisation.GridMapVisualisation;

public class VisualPanel extends JPanel {
	
	GridMap map = MapUtils.createRealWarehouse();
	
	GridMapVisualisation v = new GridMapVisualisation(map, map, 150f) {
		
		private static final long serialVersionUID = 1L;

		@Override
		public Dimension getPreferredSize() {
		    return new Dimension(650, 400);
		}
	};
	
	public VisualPanel() {
		add(v);
	}
	
	public VisualPanel(WarehouseGridSim sim) {
		add(sim.getViz());
	}
	
	
}
