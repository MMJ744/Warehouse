package com.rp25.motion.behavior;

import lejos.robotics.navigation.DifferentialPilot;
import lejos.robotics.subsumption.Behavior;
import lejos.util.Delay;

public class Forward implements Behavior{
	
	DifferentialPilot pilot;
	BehaviorVariable behaviorVar;
	
	public Forward(DifferentialPilot pilot, BehaviorVariable behaviorVar){
		this.pilot = pilot;
		this.behaviorVar = behaviorVar;
	}

	@Override
	public boolean takeControl() {
		Delay.msDelay(10);
		if (behaviorVar.get().equals("forward")){
			behaviorVar.reset();
			return true;
		}

		return false;
	}

	@Override
	public void action() {
		System.out.println("forward behavior active!");
		//pilot.forward();

		// TODO Auto-generated method stub
		
	}

	@Override
	public void suppress() {
		// TODO Auto-generated method stub
		
	}
	

	
}