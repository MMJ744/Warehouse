package com.rp25.interfaces.warehouse.gui;

import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.*;

import rp.robotics.mapping.GridMap;
import rp.robotics.mapping.MapUtils;
import rp.robotics.visualisation.GridMapVisualisation;
import tools.Job;
import tools.Robot;

public class WarehouseInterfaceFrame extends JFrame {
	
	GridMapVisualisation visualPanel;
	InfoPanel infoPanel;

	public WarehouseInterfaceFrame(String title) {
		super(title);
		setSize(600, 400);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLayout(new GridLayout(1, 2));
		
		GridMap map = MapUtils.createRealWarehouse();
		visualPanel = new GridMapVisualisation(map, map, 150f); 
		add(visualPanel);
		
		infoPanel = new InfoPanel();
		add(infoPanel);
		
		
		setSize(1300, 500);

		
		setVisible(true);
	}
	
	public void addInfo(String s) {
		infoPanel.addInfo(s);
		setVisible(true);
	}

}
