package com.rp25.motion.detector;

import com.rp25.motion.actions.CorrectPath;
import com.rp25.motion.actions.JunctionHandler;

import java.util.ArrayList;

import lejos.robotics.navigation.DifferentialPilot;

public class DetectSituation{

	//Initialise sensorStateMap variable
	ArrayList<Boolean> sensorStateMap;

	public DetectSituation(){
	}
	
	//This adds behaviours to the behaviour stack depending on the situation.
	public String getSituation(ArrayList<Boolean> state){
		sensorStateMap = state;
		if (skewingLeft()){
			return "skewingLeft";
		}
		if (skewingRight()){
			return "skewingRight";
		}
		if (detectJunction()){
			return "junction";
		}
		return "none";
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
			//Code goes here to choose where to get @ junction
			return true;
		}
		return false;
	}
	
	private Boolean skewingLeft(){
		if (!getState("middle")){
			if (!getState("left")){
				return true;
			}
		}
		return false;
	}
	
	private Boolean skewingRight(){
		if (!getState("middle")){
			if (!getState("right")){
				return true;
			}
		}
		return false;
	}
	
	
	//This deals with any arisen situation by calling the appropriate actions. When returns, robot should be repositioned.
	public void dealWith(String situation,ReadSensors sensors, DifferentialPilot pilot){
		//Stop the robot.
		pilot.stop();
		
		if (situation.equals("skewingLeft") || situation.equals("skewingRight")){
			CorrectPath pathCorrector = new CorrectPath(situation,sensors,pilot);
			pathCorrector.run();
		}
		
		if (situation.equals("junction")){
			JunctionHandler handleJunction = new JunctionHandler(sensors,pilot);
			handleJunction.run();
		}
		
		
		//By now all situations have been alleviated, and we can continue.
		pilot.forward();
		
	}
	
	
	
	
	
	
	
	
	
}