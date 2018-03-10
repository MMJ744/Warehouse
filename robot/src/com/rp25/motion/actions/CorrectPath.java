package com.rp25.motion.actions;
import java.util.ArrayList;

import lejos.robotics.navigation.DifferentialPilot;

import com.rp25.motion.detector.ReadSensors;

public class CorrectPath{
	
	String skewDirection;
	ReadSensors sensors;
	DifferentialPilot pilot;
	
	public CorrectPath(String skewDirection, ReadSensors sensors, DifferentialPilot pilot){
		this.skewDirection = skewDirection;
		this.sensors = sensors;
		this.pilot = pilot;
	}
	
	private void correctLeftSkew(){
		//Local vars
		ArrayList<Boolean> sensorStateMap;
		
		this.pilot.rotateRight();
	
		while(true){
			//Get an up to date sensor reading.
			sensorStateMap = this.sensors.poll();
			if(sensorStateMap.get(1)){
				this.pilot.stop();
				break;
			}
		}
	}
	
	private void correctRightSkew(){
		//Local vars
		ArrayList<Boolean> sensorStateMap;
		
		this.pilot.rotateLeft();
	
		while(true){
			//Get an up to date sensor reading.
			sensorStateMap = this.sensors.poll();
			if(sensorStateMap.get(1)){
				this.pilot.stop();
				break;
			}
		}
	}
	
	
	
	public void run(){
		if (this.skewDirection.equals("skewingLeft")){
			correctLeftSkew();
		}
		else if (this.skewDirection.equals("skewingRight")){
			correctRightSkew();

		}
	}
	
}