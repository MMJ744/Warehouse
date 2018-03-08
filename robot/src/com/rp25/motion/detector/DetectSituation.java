package com.rp25.motion.detector;

import com.rp25.motion.Controller;
import com.rp25.motion.behavior.BehaviorVariable;

import java.util.ArrayList;
import java.util.Stack;

import lejos.nxt.LightSensor;
import lejos.nxt.SensorPort;

public class DetectSituation{

	//Initialise sensorStateMap variable
	ArrayList<Boolean> sensorStateMap;
	//Initialise the behaviour stack variable
	BehaviorVariable behaviorVar;
	
	public DetectSituation(){
		this.sensorStateMap = sensorStateMap;
		this.behaviorVar = behaviorVar;
	}
	
	private void giveStateMap(ArrayList<Boolean> stateMap){
		this.sensorStateMap = stateMap;
	}
	
	public void giveBehaviorVar(BehaviorVariable behaviorVar){
		this.behaviorVar = behaviorVar;
	}
	
	//This adds behaviours to the behaviour stack depending on the situation.
	public void addBehaviors(ArrayList<Boolean> state){
		giveStateMap(state);
		detectJunction();
		offCourse();
	}
	
	private Boolean getState(String sensor){
		if(sensor.equals("left")){
			return this.sensorStateMap.get(0);
		}
		else if (sensor.equals("middle")){
			return this.sensorStateMap.get(1);
		}
		//return right.
		return this.sensorStateMap.get(2);
	}
		
	private Boolean detectJunction(){	
		if (getState("left") && getState("middle") && getState("right")){
			//Code goes here to find next route when a junction has been reached.
			System.out.println("JUNCTION FOUND!");
			
			return true;
		}
		return false;
	}
	
	private Boolean offCourse(){
		//If the middle sensor is not on the line, this is bad and should never ever happen.
		if (getState("middle") == false){
			//Check if one of the side sensors is on the line. If they are, we know in what direction the robot is skewed.
			if (getState("left")){
				this.behaviorVar.set("skewingRight");
				System.out.println("SKEWING RIGHT!");

			}
			else if (getState("right")){
				this.behaviorVar.set("skewingLeft");
				System.out.println("SKEWING LEFT!");
			}
			return true;
		}
		return false;
	}
	
	
	
}