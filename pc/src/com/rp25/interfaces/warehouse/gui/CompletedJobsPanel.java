package com.rp25.interfaces.warehouse.gui;

import java.awt.Color;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class CompletedJobsPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	private JTextArea completedJobs;
	private JButton pauseButton;

	public CompletedJobsPanel() {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		JLabel title = new JLabel("COMPLETED JOBS:");
		add(title);

		completedJobs = new JTextArea("NO JOBS HAVE YET BEEN COMPLETED");
		completedJobs.setColumns(30);
		completedJobs.setEditable(false);

		JScrollPane scroll = new JScrollPane(completedJobs);
		scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		add(scroll);

		pauseButton = new JButton();
		pauseButton.setForeground(Color.WHITE);
		pauseButton.setText("BIG RED PAUSE BUTTON");
		pauseButton.setBackground(Color.RED);
		add(pauseButton);

	}

	public void updateCompletedJobs(String s) {
		completedJobs.setText(s);
	}

	public void addPauseListener(ActionListener _listener) {
		pauseButton.addActionListener(_listener);
	}
}
