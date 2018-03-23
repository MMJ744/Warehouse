package com.rp25.motion.behavior;

import lejos.robotics.navigation.DifferentialPilot;
import lejos.robotics.subsumption.Behavior;

public class GoForward implements Behavior {
	private DifferentialPilot pilot;
	private boolean suppressed = false;

	public GoForward(DifferentialPilot pilot) {
		this.pilot = pilot;
	}

	@Override
	public boolean takeControl() {
		return true;
	}

	@Override
	public void action() {
		suppressed = false;

		pilot.forward();
		SPTS(pilot);

		while (!suppressed)
			Thread.yield();
	}

	@Override
	public void suppress() {
		suppressed = true;
	}

	public static void SPTS(DifferentialPilot pilot) {
		pilot.setTravelSpeed(pilot.getMaxTravelSpeed() / 2);
	}
}
