package com.rp25.interfaces.warehouse.gui;

import java.awt.Color;
import java.util.HashMap;
import java.util.Map;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.rp25.tools.Robot;

public class InfoPanel extends JPanel {
	
	Map<Integer, RobotInfoPanel> robots;
	
	public InfoPanel() {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		robots = new HashMap<>();
	}
	
	public void addInfo(Robot r) {
		RobotInfoPanel robotPanel = new RobotInfoPanel(r);
		robots.put(r.getID(), robotPanel);
		this.add(robotPanel);
		this.add(Box.createVerticalStrut(20));
	}
	
	public void update(Robot r) {
		RobotInfoPanel robot = robots.get(r.getID());
		JTextArea textArea = robot.getTextArea();
		textArea.setText(r.toString());
		textArea.setEditable(false);
	}
	
}
