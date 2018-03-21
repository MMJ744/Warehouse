package com.rp25.motion.behavior;

import static com.rp25.motion.behavior.GoTheFuckForward.SPTS;

import com.rp25.motion.detector.LineDetector;

import lejos.nxt.Motor;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.robotics.subsumption.Behavior;

public class CorrectRightSkew implements Behavior {
	private DifferentialPilot pilot;
	private LineDetector detector;
	
	private boolean suppressed = false;
	
	public CorrectRightSkew(DifferentialPilot pilot, LineDetector detector) {
		this.pilot = pilot;
		this.detector = detector;
	}
	
	@Override
	public boolean takeControl() {
		return detector.isLeftOnLine();
	}

	@Override
	public void action() {
		suppressed = false;
		
		System.out.println("ROTLFT");
//		pilot.setRotateSpeed(50);
//		pilot.rotateLeft();
		
		if(!pilot.isMoving()) {
			pilot.forward();
			SPTS(pilot);
		}
		
		Motor.B.setSpeed(Motor.B.getSpeed() * 1.3f);
		
		while(detector.isLeftOnLine() && !detector.isRightOnLine())
			Thread.yield();
	}

	@Override
	public void suppress() {
		suppressed = true;
	}

}
