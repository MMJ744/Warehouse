package com.rp25.interfaces.warehouse.gui;

import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class WarehouseInterfaceFrame extends JFrame {
	
	JPanel visualPanel;
	JPanel infoPanel;

	public WarehouseInterfaceFrame(String title) {
		super(title);
		setSize(600, 400);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLayout(new GridLayout(1, 2));
		
		visualPanel = new JPanel();
		infoPanel = new JPanel();
		
		setVisible(true);
	}
	
	public static void main(String[] args) {
		new WarehouseInterfaceFrame("ASd");
	}
	
}
