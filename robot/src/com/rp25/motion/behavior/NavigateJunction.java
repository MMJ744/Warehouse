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

	private boolean suppressed = false;
	private boolean flag = false;

	BlockingQueue<Command> moveQueue = new BlockingQueue<>();
	BlockingQueue<Integer> feedbackQueue = new BlockingQueue<>();

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
		if(flag) feedbackQueue.push(0);
		
		if(!flag) flag = true;
		
		pilot.stop();
		Command action = moveQueue.take();
		
		if(action.equals(Command.FORWARD)) {
			try {
				Thread.sleep(1275);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			pilot.forward();
			SPTS(pilot);
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else if(action.equals(Command.LEFT)) {
			pilot.travel(70);
			SPTS(pilot);
			pilot.setRotateSpeed(pilot.getMaxRotateSpeed() / 1.75);
			pilot.rotateLeft();
			
			try{Thread.sleep(175);}catch(Exception e){}

			while(!detector.isLeftOnLine() && !suppressed)
				Thread.yield();
			
			pilot.rotate(15);
			while(pilot.isMoving())
				Thread.yield();
					
		} else if(action.equals(Command.RIGHT)) {
			pilot.travel(70);
			SPTS(pilot);
			pilot.setRotateSpeed(pilot.getMaxRotateSpeed() / 1.75);
			pilot.rotateRight();
			
			try{Thread.sleep(175);}catch(Exception e){}
			
			while(!detector.isRightOnLine() && !suppressed)
				Thread.yield();
			
			pilot.rotate(-15);
			while(pilot.isMoving())
				Thread.yield();
			
		} else if(action.equals(Command.UTURN)) {
			pilot.setRotateSpeed(pilot.getMaxRotateSpeed() / 2);
			pilot.rotate(110);
			pilot.rotateLeft();
			
			try{Thread.sleep(350);}catch(Exception e){}
			
			while(!detector.isLeftOnLine() && !suppressed)
				Thread.yield();
			
			pilot.rotate(15);
			while(pilot.isMoving())
				Thread.yield();
		}
	}

	@Override
	public void suppress() {
		suppressed = true;
	}
}
