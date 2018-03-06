package com.rp25.motion;

import java.util.LinkedList;
import java.util.Queue;

import rp.config.RobotConfigs;
import rp.systems.WheeledRobotSystem;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.robotics.subsumption.Arbitrator;
import lejos.robotics.subsumption.Behavior;




//Import behaviours 
import com.rp25.motion.behavior.*;

public class Controller{
	
	
	public static void main(String[] args){
		//Run main
		System.out.println("Main running.");
		
		//Create the differential pilot and link the robot, check am
		DifferentialPilot pilot = (new WheeledRobotSystem(RobotConfigs.CASTOR_BOT)).getPilot();
		
		
		
		//Create a fake sample queue. 
		Queue<String> testQueue = new LinkedList<String>();
		for (int i=0;i < 20;i++){
			String[] directions = {"forward","turn-around","left","right"};
			int randomNum = (int) Math.floor(Math.random() * 4);
			testQueue.add(directions[randomNum]);
		}
		
		//Create the behavior objects
		Behavior BehaviorForward = new Forward(pilot);
		Behavior BehaviorTurnAround = new TurnAround(pilot);
		Behavior BehaviorRotateLeft = new RotateLeft(pilot);
		Behavior BehaviorRotateRight = new RotateRight(pilot);
		
		
		//Create and run arbitrator with the list of behaviours. 
		Behavior[] behaviorList = {BehaviorTurnAround,BehaviorRotateRight,BehaviorRotateLeft,BehaviorForward};
		Arbitrator robotArby = new Arbitrator(behaviorList);
		robotArby.start();
		
		//Create a separate thread which will constantly check the sensors and get the situation required.
		
		

		
		
	}
	
}