package com.rp25.motion.behavior;

import lejos.robotics.navigation.DifferentialPilot;
import lejos.robotics.subsumption.Behavior;

public class Forward implements Behavior{
	
	DifferentialPilot pilot;
	public Forward(DifferentialPilot pilot){
		this.pilot = pilot;
	}

	@Override
	public boolean takeControl() {
		// TODO Auto-generated method stub
		//System.out.println("MoveUp taken control.");
		return true;
	}

	@Override
	public void action() {
		pilot.forward();
		// TODO Auto-generated method stub
		
	}

	@Override
	public void suppress() {
		// TODO Auto-generated method stub
		
	}
	

	
}