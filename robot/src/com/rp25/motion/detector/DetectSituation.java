package com.rp25.motion.detector;

import com.rp25.motion.Controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

import lejos.nxt.LightSensor;
import lejos.nxt.SensorPort;

public class DetectSituation{

	//Initialise sensorStateMap variable
	Map<String,Boolean> sensorStateMap;
	//Initialise the behaviour stack variable
	Stack behaviorStack;
	
	public void giveStateMap(Map stateMap){
		sensorStateMap = stateMap;
	}
	
	public void giveBehaviorStack(Stack stack){
		behaviorStack = stack;
	}
	
	//This adds behaviours to the behaviour stack depending
	public void addBehaviors(){
		behaviorStack.add("forward");
		
	}
		
	private Boolean detectJunction(){	
		if (sensorStateMap.get("left") && sensorStateMap.get("middle") && sensorStateMap.get("right")){
			return true;
		}
		return false;
	}
	
	private Boolean offCourse(){
		//If the middle sensor is not on the line, this is bad and should never ever happen.
		if (sensorStateMap.get("middle") == false){
			//Check if one of the side sensors is on the line. If they are, we know in what direction the robot is skewed.
			if (sensorStateMap.get("left")){
				//The robot is skewing to the right 
			}
			else if (sensorStateMap.get("right")){
				//The robot is skewing to the left.
			}
			return true;
		}
		return false;
	}
	
	
	
}