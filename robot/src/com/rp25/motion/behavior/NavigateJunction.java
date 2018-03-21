package com.rp25.motion.behavior;

import static com.rp25.motion.behavior.GoTheFuckForward.SPTS;

import com.rp25.motion.detector.JunctionDetector;
import com.rp25.tools.BlockingQueue;
import com.rp25.tools.Command;

import lejos.robotics.navigation.DifferentialPilot;
import lejos.robotics.subsumption.Behavior;

public class NavigateJunction implements Behavior {
	private DifferentialPilot pilot;
	private JunctionDetector detector;

	private boolean acting = false;
	private boolean suppressed = false;
	
	private BlockingQueue<Command> moveQueue;
	private BlockingQueue<Integer> feedbackQueue;
	
	int curAction = 0;

	public NavigateJunction(DifferentialPilot pilot, JunctionDetector detector, BlockingQueue<Command> moveQueue, BlockingQueue<Integer> feedbackQueue) {
		this.pilot = pilot;
		this.detector = detector;
		
		this.moveQueue = moveQueue;
		this.feedbackQueue = feedbackQueue;
	}

	@Override
	public boolean takeControl() {
		return detector.isJunctionDetected() && !suppressed;
	}

	@Override
	public void action() {
		Command c = moveQueue.take();
		
		if(c.equals(Command.FORWARD)) {
			pilot.forward();
			SPTS(pilot);
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else if(c.equals(Command.LEFT)) {
			pilot.travel(75);
			SPTS(pilot);
			pilot.setRotateSpeed(pilot.getMaxRotateSpeed());
			pilot.rotate(92.5);
			
			while(pilot.isMoving() && !suppressed)
				Thread.yield();
			
		} else if(c.equals(Command.RIGHT)) {
			pilot.travel(75);
			SPTS(pilot);
			pilot.setRotateSpeed(pilot.getMaxRotateSpeed());
			pilot.rotate(-92.5);
			
			while(pilot.isMoving() && !suppressed)
				Thread.yield();
		} else if(c.equals(Command.UTURN)) {
			pilot.setRotateSpeed(pilot.getMaxRotateSpeed() / 2);
			pilot.rotate(185);
			
			while(pilot.isMoving() && !suppressed)
				Thread.yield();
		}
		
		feedbackQueue.push(0);
	}

	@Override
	public void suppress() {
		suppressed = true;
	}
}
