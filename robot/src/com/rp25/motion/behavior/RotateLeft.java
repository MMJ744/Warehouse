package com.rp25.motion.behavior;

//Imports
import java.util.ArrayList;

import com.rp25.motion.detector.ReadSensors;

import lejos.robotics.navigation.DifferentialPilot;
import lejos.robotics.subsumption.Behavior;

public class RotateLeft implements Behavior{

	DifferentialPilot pilot;
	BehaviorVariable behaviorVar;
	ReadSensors sensors;
	
	public RotateLeft(DifferentialPilot pilot,BehaviorVariable behaviorVar){
		this.pilot = pilot;
		this.behaviorVar = behaviorVar;
	}
	
	
	@Override
	public boolean takeControl() {
		// TODO Auto-generated method stub
		if (this.behaviorVar.get().equals("rotateLeft")){
			return true;
		}
		return false;
	}

	@Override
	public void action() {
		// TODO Auto-generated method stub
		this.pilot.rotate(-15);
		this.pilot.rotateLeft();
		
		ArrayList<Boolean> sensorStateMap = sensors.poll();
		if(sensorStateMap.get(1) == true){
			suppress();
			pilot.stop();
		}
	
		
		
		
	}

	@Override
	public void suppress() {
		// TODO Auto-generated method stub
		
	}
	

	
}