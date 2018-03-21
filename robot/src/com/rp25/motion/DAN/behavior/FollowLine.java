package com.rp25.motion.DAN.behavior;

import com.rp25.motion.DAN.detector.LineDetector;

import lejos.robotics.navigation.DifferentialPilot;
import lejos.robotics.subsumption.Behavior;

public class FollowLine implements Behavior {
	private DifferentialPilot pilot;
	private LineDetector detector;
	
	private boolean suppressed = false;
	
	public FollowLine(DifferentialPilot pilot, LineDetector detector) {
		this.pilot = pilot;
		this.detector = detector;
	}
	
	@Override
	public boolean takeControl() {
		return detector.isLineDetected();
	}

	@Override
	public void action() {
		suppressed = false;
		System.out.println("LINE");
		
		if(detector.isLeftOnLine()) {
			while(detector.isLineDetected()) {
				pilot.stop();
				pilot.rotate(11.25);
			}
		} else if(detector.isRightOnLine()) {
			while(detector.isLineDetected()) {
				pilot.stop();
				pilot.rotate(-11.25);
			}
		}
		
		while(pilot.isMoving() && !suppressed)
			Thread.yield();
	}

	@Override
	public void suppress() {
		suppressed = true;
	}
}
