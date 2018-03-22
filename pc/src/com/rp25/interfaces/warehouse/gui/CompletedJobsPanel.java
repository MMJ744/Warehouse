package com.rp25.interfaces.warehouse.gui;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class CompletedJobsPanel extends JPanel {
	
	private static final long serialVersionUID = 1L;
	
	private JTextArea completedJobs;
	
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

	}
	
	public void updateCompletedJobs(String s) {
		completedJobs.setText(s);
	}
}
