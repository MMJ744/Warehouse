package com.rp25.motion.detector;

import com.rp25.motion.Controller;
import com.rp25.motion.behavior.BehaviorStack;

import java.util.ArrayList;
import java.util.Stack;

import lejos.nxt.LightSensor;
import lejos.nxt.SensorPort;

public class DetectSituation{

	//Initialise sensorStateMap variable
	ArrayList<Boolean> sensorStateMap;
	//Initialise the behaviour stack variable
	BehaviorStack behaviorStack;
	
	public void giveStateMap(ArrayList<Boolean> stateMap){
		sensorStateMap = stateMap;
	}
	
	public void giveBehaviorStack(BehaviorStack stack){
		behaviorStack = stack;
	}
	
	//This adds behaviours to the behaviour stack depending on the situation.
	public void addBehaviors(){
		detectJunction();
		offCourse();
	}
	
	private Boolean getState(String sensor){
		if(sensor.equals("left")){
			return sensorStateMap.get(0);
		}
		else if (sensor.equals("middle")){
			return sensorStateMap.get(1);
		}
		//return right.
		return sensorStateMap.get(2);
	}
		
	private Boolean detectJunction(){	
		if (getState("left") && getState("middle") && getState("right")){
			//Code goes here to find next route when a junction has been reached.
			
			
			return true;
		}
		return false;
	}
	
	private Boolean offCourse(){
		//If the middle sensor is not on the line, this is bad and should never ever happen.
		if (getState("middle") == false){
			//Check if one of the side sensors is on the line. If they are, we know in what direction the robot is skewed.
			if (getState("left")){
				behaviorStack.push("skewingRight");

			}
			else if (getState("right")){
				behaviorStack.push("skewingLeft");
			}
			return true;
		}
		return false;
	}
	
	
	
}