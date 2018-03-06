package com.rp25.motion.behavior;

//Imports
import lejos.robotics.navigation.DifferentialPilot;
import lejos.robotics.subsumption.Behavior;

public class RotateRight implements Behavior{

	DifferentialPilot pilot;
	
	public RotateRight(DifferentialPilot pilot){
			this.pilot = pilot;
	}
	
	
	@Override
	public boolean takeControl() {
		// TODO Auto-generated method stub
		//System.out.println("MoveRight taken control.");
		return true;
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