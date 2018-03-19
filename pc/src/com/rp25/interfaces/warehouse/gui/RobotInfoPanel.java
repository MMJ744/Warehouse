package com.rp25.interfaces.warehouse.gui;

import java.awt.FlowLayout;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.rp25.tools.Robot;

/**
 * JPanel that holds information about a single robot.
 * Also allows for cancellation of that robot's job.
 * @author ass782
 *
 */
public class RobotInfoPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	
	JTextField name, coordinates, currentJob, currentAction;
	JTextArea textArea;
	JButton cancelButton;
	
	RobotInfoPanel(Robot r) {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		name = new JTextField(r.nameString());
		name.setEditable(false);
		add(name);
		
		coordinates = new JTextField(r.posString());
		coordinates.setEditable(false);
		add(coordinates);
		
		currentJob = new JTextField(r.jobString());
		currentJob.setEditable(false);
		add(currentJob);
		
		textArea = new JTextArea(r.jobPartString());
		textArea.setEditable(false);
		add(textArea);
		
		currentAction = new JTextField(r.getCurrentAction());
		currentAction.setEditable(false);
		add(currentAction);
		
		cancelButton = new JButton("Cancel Current Job");
		add(cancelButton);
	}
	
	public void addCancelListener(ActionListener listener) {
		cancelButton.addActionListener(listener);
	}
	
	JTextArea  getTextArea()  { return textArea; }
	JTextField getNameField() { return name; }
	JTextField getCoorField() { return coordinates; }
	JTextField getJobField()  { return currentJob; }
	JButton    getButton()    { return cancelButton; }
	JTextField getActionField() { return currentAction; }
}
