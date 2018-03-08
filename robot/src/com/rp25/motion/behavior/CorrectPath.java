package com.rp25.motion.behavior;

import lejos.robotics.navigation.DifferentialPilot;
import lejos.robotics.subsumption.Behavior;

public class CorrectPath implements Behavior{
	
	DifferentialPilot pilot;
	BehaviorStack behaviorStack;
	int rotationDirection = 0; //1 to turn clockwise and -1 to turn counter-clockwise? 
	
	public CorrectPath(DifferentialPilot pilot, BehaviorStack behaviorStack){
		this.pilot = pilot;
		this.behaviorStack = behaviorStack;
	}

	@Override
	public boolean takeControl() {
		if (this.behaviorStack.peek().equals("skewingLeft") || this.behaviorStack.peek().equals("skewingRight")){
			this.behaviorStack.pop();
			//Set the rotation direction depending on what direction it is skewing to.
			if(rotationDirection == 0){
				if (behaviorStack.peek().equals("skewingLeft")){
					rotationDirection = 1;
				}
				else{
					rotationDirection = -1;
				}
			}
			//Stop the robot in anticipation of the following action.
			pilot.stop();
			
			return true;
		}
		return false;
	}

	@Override
	public void action() {
		//Rotate the robot a small amount
		
	}

	@Override
	public void suppress() {
		// TODO Auto-generated method stub
		
	}
	

	
}