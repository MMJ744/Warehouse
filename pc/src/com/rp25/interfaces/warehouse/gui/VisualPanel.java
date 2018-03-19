package com.rp25.interfaces.warehouse.gui;

import javax.swing.JPanel;

import com.rp25.interfaces.warehouse.sim.WarehouseGridSim;

/**
 * JPanel that simply holds the warehouse simulation
 * @author ass782
 *
 */
public class VisualPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	public VisualPanel(WarehouseGridSim sim) { add(sim.getViz()); }

}
