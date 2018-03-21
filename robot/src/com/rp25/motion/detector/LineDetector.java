package com.rp25.motion.detector;

import lejos.nxt.LightSensor;
import lejos.nxt.SensorPort;

public class LineDetector extends Thread {
	private static final int THRESHOLD = 400;

	private LightSensor left;
	private LightSensor right;

	private boolean leftOnLine;
	private boolean rightOnLine;

	private boolean lineDetected;

	public LineDetector(SensorPort leftPort, SensorPort rightPort) {
		setDaemon(true);
		
		left = new LightSensor(leftPort);
		right = new LightSensor(rightPort);

		leftOnLine = false;
		rightOnLine = false;
		lineDetected = false;
	}

	@Override
	public void run() {
		while(true) {
			leftOnLine = left.getNormalizedLightValue() < THRESHOLD;
			rightOnLine = right.getNormalizedLightValue() < THRESHOLD;
			
			lineDetected = leftOnLine || rightOnLine;
		}
	}

	public boolean testLeftOnLine() {
		return left.getNormalizedLightValue() < THRESHOLD;
	}
	
	public boolean testRightOnLine() {
		return right.getNormalizedLightValue() < THRESHOLD;
	}
	
	public boolean isLineDetected() {
		return lineDetected;
	}

	public boolean isLeftOnLine() {
		return leftOnLine;
	}

	public boolean isRightOnLine() {
		return rightOnLine;
	}
}
