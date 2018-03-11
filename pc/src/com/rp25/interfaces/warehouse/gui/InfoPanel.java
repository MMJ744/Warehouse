package com.rp25.interfaces.warehouse.gui;

import java.awt.Color;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.rp25.tools.Robot;

public class InfoPanel extends JPanel {
	
	Map<Integer, JTextArea> robots;
	
	public InfoPanel() {
		setBackground(Color.WHITE);
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		robots = new HashMap<>();
	}
	
	public void addInfo(Robot r) {
		JTextArea textArea = new JTextArea(3, 16);
		textArea.setText(r.toString());
		textArea.setEditable(false);
		
		robots.put(r.getID(), textArea);
		this.add(textArea);
	}
	
	public void update(Robot r) {
		JTextArea robot = robots.get(r.getID());
		robot.setText(r.toString());
		robot.setEditable(false);
	}
	
}
