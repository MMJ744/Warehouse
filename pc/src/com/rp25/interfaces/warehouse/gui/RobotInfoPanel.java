package com.rp25.interfaces.warehouse.gui;

import java.awt.FlowLayout;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import com.rp25.tools.Robot;

/**
 * JPanel that holds information about a single robot.
 * Also allows for cancellation of that robot's job.
 * @author ass782
 *
 */
public class RobotInfoPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	
	JTextArea textArea;
	JButton cancelButton;
	
	RobotInfoPanel(Robot r) {
		setLayout(new FlowLayout(FlowLayout.LEFT));
		
		textArea = new JTextArea(3, 16);
		textArea.setText(r.toString());
		textArea.setEditable(false);
		add(textArea);
		
		cancelButton = new JButton("Cancel Current Job");
		add(cancelButton);
	}
	
	public void addCancelListener(ActionListener listener) {
		cancelButton.addActionListener(listener);
	}
	
	JTextArea getTextArea() { return textArea; }
	JButton   getButton()   { return cancelButton; }
}
