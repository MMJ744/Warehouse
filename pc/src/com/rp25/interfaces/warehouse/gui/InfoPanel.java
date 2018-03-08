package com.rp25.interfaces.warehouse.gui;

import java.awt.Color;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class InfoPanel extends JPanel {
	
	public InfoPanel() {
		setBackground(Color.WHITE);
	}
	
	public void addInfo(String s) {
		JTextArea textArea = new JTextArea(3, 16);
		textArea.setText(s);
		textArea.setEditable(false);
		
		add(textArea);
		
	}
	
}
