package com.rp25.interfaces.warehouse.gui;

import java.awt.Dimension;
import java.awt.event.ActionListener;

import javax.swing.*;

import com.rp25.interfaces.warehouse.sim.WarehouseGridSim;
import com.rp25.tools.Robot;

/**
 * JFrame that holds all the GUI elements of the interface.
 * @author ass782
 *
 */

public class WarehouseInterfaceFrame extends JFrame {
	
	private static final long serialVersionUID = 1L;
	
	VisualPanel visualPanel;
	InfoPanel infoPanel;
	CompletedJobsPanel completedJobsPanel;

	public WarehouseInterfaceFrame(String title, WarehouseGridSim sim) {
		//JFrame setup
		super(title);
		setSize(600, 400);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLayout(new BoxLayout(this.getContentPane(), BoxLayout.X_AXIS));
		
		
		//Display panel that handles warehouse simulation
		visualPanel = new VisualPanel(sim);
		add(visualPanel);
		
		//Display panel that shows robot information
		infoPanel = new InfoPanel();
		add(infoPanel);

		completedJobsPanel = new CompletedJobsPanel();
		add(completedJobsPanel);
		
		setMinimumSize(new Dimension(1300, 500));
		setResizable(false);
	}
	
	public void addInfo(Robot r) {
		infoPanel.addInfo(r);
	}
	
	public void updateInfo(Robot r) {
		infoPanel.update(r);
	}
	
	public void updateCompletedJobs(String s) {
		completedJobsPanel.updateCompletedJobs(s);
	}
	
	public void addListeners(int id, ActionListener listener) {
		infoPanel.addButtonListeners(id, listener);
	}

}
