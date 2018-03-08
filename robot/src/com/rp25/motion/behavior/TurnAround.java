package com.rp25.motion.behavior;

//Imports
import lejos.robotics.navigation.DifferentialPilot;
import lejos.robotics.subsumption.Behavior;

public class TurnAround implements Behavior{
	
	DifferentialPilot pilot;
	BehaviorStack behaviorStack;
	
	public TurnAround(DifferentialPilot pilot,BehaviorStack behaviorStack){
		this.pilot = pilot;
		this.behaviorStack = behaviorStack;
	}

	@Override
	public boolean takeControl() {
		// TODO Auto-generated method stub
		if (this.behaviorStack.peek().equals("turn around")){
			this.behaviorStack.pop();
			return true;
		}
		return false;
	}

	@Override
	public void action() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void suppress() {
		// TODO Auto-generated method stub
		
	}
	

	
}