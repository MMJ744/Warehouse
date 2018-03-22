package com.rp25.motion.behavior;

import com.rp25.motion.detector.JunctionDetector;
import com.rp25.motion.detector.LineDetector;

import lejos.nxt.Button;
import lejos.nxt.ButtonListener;
import lejos.nxt.Motor;
import lejos.nxt.SensorPort;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.robotics.subsumption.Arbitrator;
import lejos.robotics.subsumption.Behavior;

public class MotionMain {

	public static void main(String[] args) {
		Button.waitForAnyPress();
		
		DifferentialPilot pilot = new DifferentialPilot(56, 110.5, Motor.C, Motor.B);
		LineDetector lineDetector = new LineDetector(SensorPort.S3, SensorPort.S2);
		JunctionDetector junctionDetector = new JunctionDetector(SensorPort.S3, SensorPort.S2);
		
		Behavior go = new GoTheFuckForward(pilot);
		Behavior correctLeftSkew = new CorrectLeftSkew(pilot, lineDetector);
		Behavior correctRightSkew = new CorrectRightSkew(pilot, lineDetector);
		Behavior navJunction = new NavigateJunction(pilot, junctionDetector, null, null);
		
		lineDetector.start();
		junctionDetector.start();
		
		Button.LEFT.addButtonListener(new ButtonListener() {
			@Override
			public void buttonReleased(Button b) { }

			@Override
			public void buttonPressed(Button b) {
				System.exit(3000);
			}
		});
		
		
		
		Arbitrator arb = new Arbitrator(new Behavior[] {go, correctLeftSkew, correctRightSkew, navJunction});
		arb.start();
	}


}
