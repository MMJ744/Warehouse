package com.rp25.interfaces.warehouse.gui;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.*;

import rp.robotics.mapping.GridMap;
import rp.robotics.mapping.MapUtils;
import rp.robotics.visualisation.GridMapVisualisation;

public class WarehouseInterfaceFrame extends JFrame {
	
	JPanel visualPanel;
	InfoPanel infoPanel;

	public WarehouseInterfaceFrame(String title) {
		//JFrame setup
		super(title);
		setSize(600, 400);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLayout(new GridLayout(1, 2));
		
		//Display panel that handles warehouse simulation
		GridMap map = MapUtils.createRealWarehouse();
		GridMapVisualisation v = new GridMapVisualisation(map, map, 150f) {
			
			private static final long serialVersionUID = 1L;

			@Override
			public Dimension getPreferredSize() {
			    return new Dimension(650, 400);
			}
		}; 
		visualPanel = new JPanel();
		visualPanel.add(v);
		add(visualPanel);
		
		//Display panel that shows robot information
		infoPanel = new InfoPanel();
		add(infoPanel);
		pack();
	}
	
	public void addInfo(String s) {
		infoPanel.addInfo(s);
	}

}
