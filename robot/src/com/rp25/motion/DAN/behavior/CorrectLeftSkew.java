package com.rp25.motion.DAN.behavior;

import com.rp25.motion.DAN.detector.LineDetector;

import lejos.nxt.Motor;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.robotics.subsumption.Behavior;

import static com.rp25.motion.DAN.behavior.GoTheFuckForward.SPTS;

public class CorrectLeftSkew implements Behavior {
	private DifferentialPilot pilot;
	private LineDetector detector;
	
	private boolean suppressed = false;
	
	public CorrectLeftSkew(DifferentialPilot pilot, LineDetector detector) {
		this.pilot = pilot;
		this.detector = detector;
	}
	
	@Override
	public boolean takeControl() {
		return detector.isRightOnLine();
	}

	@Override
	public void action() {
		suppressed = false;
		
		System.out.println("ROTRGHT");
		//pilot.setRotateSpeed(50);
		//pilot.rotateRight();
		
		if(!pilot.isMoving()) {
			pilot.forward();
			SPTS(pilot);
		}
		
		Motor.C.setSpeed(Motor.C.getSpeed() * 1.3f);
		
		while(detector.isRightOnLine() && !detector.isLeftOnLine())
			Thread.yield();
	}

	@Override
	public void suppress() {
		suppressed = true;
	}

}
