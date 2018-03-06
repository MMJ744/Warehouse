package com.rp25.motion.detector;

import java.util.HashMap;
import java.util.Map;

import lejos.nxt.LightSensor;
import lejos.nxt.SensorPort;

public class ReadSensors extends Thread{
	
	//Initialise the variables for each of the 3 sensors.
	private final LightSensor lightSensor1; 
	private final LightSensor lightSensor2; 
	private final LightSensor lightSensor3;
	//Initialise the threshold to determine whether or not the sensor is pointing on a black line.
	private static final int THRESHOLD = 355;
	//Initialise the detector object
	DetectSituation detector = new DetectSituation();
	
	
	public ReadSensors(SensorPort port1, SensorPort port2, SensorPort port3){
		//Assign the sensor objects. Assuming left=1, middle=2, right=3.
		this.lightSensor1 = new LightSensor(port1);
		this.lightSensor2 = new LightSensor(port2);
		this.lightSensor3 = new LightSensor(port3);
	}


	public Map<String,Boolean> getSensorStateMap(){
		//Create the return map
		Map<String,Boolean> state = new HashMap<String,Boolean>();
		//Mapping: SENSOR NAME -> TRUE IF ON LINE, FALSE IF NOT.
		state.put("left",this.lightSensor1.getNormalizedLightValue() < THRESHOLD);
		state.put("middle",this.lightSensor2.getNormalizedLightValue() < THRESHOLD);
		state.put("right",this.lightSensor3.getNormalizedLightValue() < THRESHOLD);
		return state;
	}
	
	//The run method of the thread. 
	public void run(){
		while(true){
			//Sleep the run so it does not use up too many resources.
			try {
				Thread.sleep(15);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			//Get the current state map and give it to the situation detector.
			Map<String,Boolean> stateMap = getSensorStateMap();
			detector.giveStateMap(stateMap);
			detector.addBehaviors();
			
			
		}
	}
	
	
	
	
}