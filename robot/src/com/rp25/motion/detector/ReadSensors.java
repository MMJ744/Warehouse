package com.rp25.motion.detector;

import java.util.ArrayList;

import lejos.nxt.LightSensor;
import lejos.nxt.SensorPort;

public class ReadSensors{
	
	//Initialise the variables for each of the 3 sensors.
	private final LightSensor lightSensor1; 
	private final LightSensor lightSensor2; 
	private final LightSensor lightSensor3;
	//Initialise the threshold to determine whether or not the sensor is pointing on a black line.
	private static final int THRESHOLD = 330; // 385 = white, 285 = black , if > threshold, then white
	//Initialise the detector object
	DetectSituation detector = new DetectSituation();
	
	
	public ReadSensors(SensorPort port1, SensorPort port2, SensorPort port3){
		//Assign the sensor objects. Assuming left=1, middle=2, right=3.
		this.lightSensor1 = new LightSensor(port1);
		this.lightSensor2 = new LightSensor(port2);
		this.lightSensor3 = new LightSensor(port3);
	}


	private ArrayList<Boolean> getSensorStateMap(){
		//Create the return map
		ArrayList<Boolean> state = new ArrayList<Boolean>();
		//Mapping: SENSOR NAME -> TRUE IF ON LINE, FALSE IF NOT.

		state.add(this.lightSensor1.getNormalizedLightValue() < THRESHOLD); //.get(0) == left sensor
		state.add(this.lightSensor2.getNormalizedLightValue() < THRESHOLD); //.get(1) == middle sensor 
		state.add(this.lightSensor3.getNormalizedLightValue() < THRESHOLD); //.get(2) == right sensor
		
		return state;
	}
	
	
	public ArrayList<Boolean> poll(){
		//Get the current state map and give it to the situation detector.
		return getSensorStateMap();
	}
	
	
	
	
	
	
	
}