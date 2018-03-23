package com.rp25.motion.behavior;

import static com.rp25.motion.behavior.GoForward.SPTS;

import com.rp25.motion.detector.LineDetector;

import lejos.nxt.Motor;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.robotics.subsumption.Behavior;

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

		if (!pilot.isMoving()) {
			pilot.forward();
			SPTS(pilot);
		}

		Motor.C.setSpeed(Motor.C.getSpeed() * 1.25f);

		while (detector.isRightOnLine() && !detector.isLeftOnLine() && !suppressed)
			Thread.yield();
	}

	@Override
	public void suppress() {
		suppressed = true;
	}

}
