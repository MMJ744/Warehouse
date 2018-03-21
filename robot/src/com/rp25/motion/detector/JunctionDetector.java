package com.rp25.motion.detector;

import lejos.nxt.LightSensor;
import lejos.nxt.SensorPort;

public class JunctionDetector extends Thread {
	private static final int THRESHOLD = 400;
	
	private LightSensor left;
	private LightSensor right;
		
	private boolean leftOnLine;
	private boolean rightOnLine;
	
	private boolean junctionDetected;
	
	public JunctionDetector(SensorPort leftPort, SensorPort rightPort) {
		left = new LightSensor(leftPort);
		right = new LightSensor(rightPort);
				
		leftOnLine = false;
		rightOnLine = false;
		junctionDetected = false;
	}
	
	@Override
	public void run() {
		while(true) {
			leftOnLine = left.getNormalizedLightValue() < THRESHOLD;
			rightOnLine = right.getNormalizedLightValue() < THRESHOLD;
						
			junctionDetected = (leftOnLine && rightOnLine);
		}
	}
	
	public boolean isJunctionDetected() {
		return junctionDetected;
	}

	public boolean isLeftOnLine() {
		return leftOnLine;
	}

	public boolean isRightOnLine() {
		return rightOnLine;
	}
}
