package com.rp25.motion.behavior;

//Imports
import lejos.robotics.navigation.DifferentialPilot;
import lejos.robotics.subsumption.Behavior;

public class RotateRight implements Behavior{

	DifferentialPilot pilot;
	BehaviorVariable behaviorVar;
	
	public RotateRight(DifferentialPilot pilot,BehaviorVariable behaviorVar){
			this.pilot = pilot;
			this.behaviorVar = behaviorVar;
	}
	
	
	@Override
	public boolean takeControl() {
		// TODO Auto-generated method stub
		if (this.behaviorVar.get().equals("rotateRight")){
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