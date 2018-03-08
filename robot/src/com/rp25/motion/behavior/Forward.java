package com.rp25.motion.behavior;

import lejos.robotics.navigation.DifferentialPilot;
import lejos.robotics.subsumption.Behavior;

public class Forward implements Behavior{
	
	DifferentialPilot pilot;
	BehaviorStack behaviorStack;
	
	public Forward(DifferentialPilot pilot, BehaviorStack behaviorStack){
		this.pilot = pilot;
		this.behaviorStack = behaviorStack;
	}

	@Override
	public boolean takeControl() {
		if (this.behaviorStack.peek().equals("forward")){
			this.behaviorStack.pop();
			return true;
		}
		return false;
	}

	@Override
	public void action() {
		System.out.println("forward behavior active!");
		pilot.forward();
		suppress();
		// TODO Auto-generated method stub
		
	}

	@Override
	public void suppress() {
		// TODO Auto-generated method stub
		
	}
	

	
}