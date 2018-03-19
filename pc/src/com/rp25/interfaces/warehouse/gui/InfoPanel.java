package com.rp25.interfaces.warehouse.gui;

import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import com.rp25.tools.Robot;

/**
 * JPanel that holds the information of the robots inside the warehouse. 
 * @author ass782
 *
 */
public class InfoPanel extends JPanel {
	
	private static final long serialVersionUID = 1L;
	
	Map<Integer, RobotInfoPanel> robots;
	JScrollPane pane;
	
	public InfoPanel() {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		robots = new HashMap<>();
	}
	
	public void addInfo(Robot r) {
		RobotInfoPanel robotPanel = new RobotInfoPanel(r);
		robots.put(r.getID(), robotPanel);
		this.add(robotPanel);
	}
	
	public void update(Robot r) {
		RobotInfoPanel robot = robots.get(r.getID());
		JTextArea textArea = robot.getTextArea();
		textArea.setText(r.toString());
		textArea.setEditable(false);
	}
	
	public void addButtonListeners(int id, ActionListener listener) {
		robots.get(id).addCancelListener(listener);
	}
}
