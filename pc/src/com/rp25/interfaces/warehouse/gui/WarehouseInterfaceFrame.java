package com.rp25.interfaces.warehouse.gui;

import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

import rp.robotics.mapping.GridMap;
import rp.robotics.mapping.MapUtils;
import rp.robotics.visualisation.GridMapVisualisation;

public class WarehouseInterfaceFrame extends JFrame {
	
	GridMapVisualisation visualPanel;
	JPanel infoPanel;

	public WarehouseInterfaceFrame(String title) {
		super(title);
		setSize(600, 400);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLayout(new GridLayout(1, 2));
		
		GridMap map = MapUtils.createRealWarehouse();
		visualPanel = new GridMapVisualisation(map, map, 150f); 
		add(visualPanel);
		
		infoPanel = new JPanel();
		add(infoPanel);
		
		setSize(1290, 500);
		
		setVisible(true);
	}
	
	public static void main(String[] args) {
		new WarehouseInterfaceFrame("Robot Warehouse Interface");
	}
	
}
