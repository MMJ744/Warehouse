package com.rp25.interfaces.warehouse.gui;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import com.rp25.tools.Robot;

public class RobotInfoPanel extends JPanel {
	JTextArea textArea;
	JButton cancelButton;
	
	RobotInfoPanel(Robot r) {
		textArea = new JTextArea(3, 16);
		textArea.setText(r.toString());
		textArea.setEditable(false);
		add(textArea);
		
		cancelButton = new JButton("Cancel Current Job");
		add(cancelButton);
	}
	
	JTextArea getTextArea() { return textArea; }
	JButton   getButton()   { return cancelButton; }
}
