package com.rp25.interfaces.warehouse.gui;

import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.rp25.tools.Robot;

/**
 * JPanel that holds information about a single robot. Also allows for
 * cancellation of that robot's job.
 * 
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
		textArea.setRows(3);
		textArea.setEditable(false);
		JScrollPane scroll = new JScrollPane(textArea);
		scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		add(scroll);

		currentAction = new JTextField(r.actionString());
		currentAction.setEditable(false);
		add(currentAction);

		cancelButton = new JButton("Cancel Current Job");
		add(cancelButton);
	}

	public void addCancelListener(ActionListener listener) {
		cancelButton.addActionListener(listener);
	}

	JTextArea getTextArea() {
		return textArea;
	}

	JTextField getNameField() {
		return name;
	}

	JTextField getCoorField() {
		return coordinates;
	}

	JTextField getJobField() {
		return currentJob;
	}

	JButton getButton() {
		return cancelButton;
	}

	JTextField getActionField() {
		return currentAction;
	}
}
