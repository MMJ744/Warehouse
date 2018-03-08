package com.rp25.motion.behavior;

import java.util.ArrayList;

import com.rp25.motion.detector.DetectSituation;
import com.rp25.motion.detector.ReadSensors;



//Imports
import lejos.robotics.subsumption.Behavior;

public class PassiveCheckSituation implements Behavior{

	ReadSensors sensors;
	ArrayList<Boolean> sensorStateMap;
	DetectSituation detector;

	public PassiveCheckSituation(ReadSensors sensors, DetectSituation detector){
		this.sensors = sensors;
		this.detector = detector;
	}
	
	
	@Override
	public boolean takeControl() {
		return true;
	}

	@Override
	public void action() {
		//This gets updates from the sensors and updates the behaviour stack.
		sensorStateMap = sensors.poll();
		detector.addBehaviors(sensorStateMap);
		
	}

	@Override
	public void suppress() {
		// TODO Auto-generated method stub
		
	}
	

	
}