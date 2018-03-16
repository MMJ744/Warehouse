package com.rp25.interfaces.warehouse.gui;

import java.awt.GridLayout;
import java.awt.Dimension;
import javax.swing.*;

import com.rp25.interfaces.warehouse.sim.WarehouseGridSim;
import com.rp25.tools.Robot;

public class WarehouseInterfaceFrame extends JFrame {
	
	VisualPanel visualPanel;
	InfoPanel infoPanel;

	public WarehouseInterfaceFrame(String title, WarehouseGridSim sim) {
		//JFrame setup
		super(title);
		setSize(600, 400);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLayout(new GridLayout(1,2));
		
		//Display panel that handles warehouse simulation
		visualPanel = new VisualPanel(sim);
		add(visualPanel);
		
		//Display panel that shows robot information
		infoPanel = new InfoPanel();
		add(infoPanel);
		pack();
		setMinimumSize(new Dimension(1400, 450));
	}
	
	public void addInfo(Robot r) {
		infoPanel.addInfo(r);
	}
	
	public void updateInfo(Robot r) {
		infoPanel.update(r);
	}

}
