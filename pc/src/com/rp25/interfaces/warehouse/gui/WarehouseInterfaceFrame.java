package com.rp25.interfaces.warehouse.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionListener;

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
		setLayout(new BorderLayout());
		
		
		//Display panel that handles warehouse simulation
		visualPanel = new VisualPanel(sim);
		add(visualPanel, BorderLayout.WEST);
		
		//Display panel that shows robot information
		infoPanel = new InfoPanel();
		add(infoPanel);
		
		setMinimumSize(new Dimension(800, 450));
		pack();
	}
	
	public void addInfo(Robot r) {
		infoPanel.addInfo(r);
	}
	
	public void updateInfo(Robot r) {
		infoPanel.update(r);
	}
	
	public void addListeners(int id, ActionListener listener) {
		infoPanel.addButtonListeners(id, listener);
	}

}
