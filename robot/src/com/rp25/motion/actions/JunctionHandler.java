package com.rp25.motion.actions;

import java.util.ArrayList;

import lejos.robotics.navigation.DifferentialPilot;

import com.rp25.motion.detector.ReadSensors;

public class JunctionHandler{
	
	private final ReadSensors sensors;
	private final DifferentialPilot pilot;
	private ArrayList<Boolean> sensorStateMap;
	private final static int TURN_OFFSET = 15;
	private final static int SPIN_OFFSET = 105;
	
	public JunctionHandler(ReadSensors sensors, DifferentialPilot pilot){
		this.sensors = sensors;
		this.pilot = pilot;
	}
	
	private String getNextDirection(){
		//use this to receive the next direction from the server.
		return "";
	}
	
	private ArrayList<Boolean> getStateMap(){
		return this.sensors.poll();
	}
	
	public void goLeft(){
		this.pilot.rotate(-TURN_OFFSET);
		this.pilot.rotateLeft();
		while(true){
			sensorStateMap = getStateMap();
			if (sensorStateMap.get(1)){
				pilot.stop();
				break;
			}
		}
	}
	
	public void goRight(){
		this.pilot.rotate(TURN_OFFSET);
		this.pilot.rotateRight();
		while(true){
			sensorStateMap = getStateMap();
			if (sensorStateMap.get(1)){
				pilot.stop();
				break;
			}
		}
	}
	
	public void turnAround(){
		this.pilot.rotate(SPIN_OFFSET);
		this.pilot.rotateRight();
		while(true){
			sensorStateMap = getStateMap();
			if (sensorStateMap.get(1)){
				pilot.stop();
				break;
			}
		}
	}
	
	public void run(){
		String nextDirection = getNextDirection();
		if (nextDirection.equals("left")){
			goLeft();
		}
		else if (nextDirection.equals("right")){
			goRight();
		}
		else if (nextDirection.equals("turnAround")){
			turnAround();
		}
	}
	
}