package com.rp25.motion;

import java.util.LinkedList;
import java.util.Queue;

import rp.config.RobotConfigs;
import rp.systems.WheeledRobotSystem;
import lejos.nxt.Button;
import lejos.nxt.ButtonListener;
import lejos.nxt.SensorPort;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.robotics.subsumption.Arbitrator;
import lejos.robotics.subsumption.Behavior;



//Import behaviours 
import com.rp25.motion.behavior.*;
import com.rp25.motion.detector.DetectSituation;
import com.rp25.motion.detector.ReadSensors;

public class Controller{
	
	
	public static void main(String[] args){
		//Run main
		boolean run = true;
		System.out.println("Main running.");
		
		//Create the differential pilot and link the robot, check am
		DifferentialPilot pilot = (new WheeledRobotSystem(RobotConfigs.CASTOR_BOT)).getPilot();
		
		//Create the behaviour stack
		BehaviorVariable behaviorVar = new BehaviorVariable();
		//Tell the robot to start going forward.
		behaviorVar.set("forward");
		
		//Create sensors object to poll
		ReadSensors sensors = new ReadSensors(SensorPort.S4,SensorPort.S3, SensorPort.S1);
		
		//Create a detector object 
		DetectSituation detector = new DetectSituation();
		detector.giveBehaviorVar(behaviorVar);
		
		//Create a fake sample queue. 
		LinkedList<String> testQueue = new LinkedList<String>();
		for (int i=0;i < 20;i++){
			String[] directions = {"forward","turn-around","left","right"};
			int randomNum = (int) Math.floor(Math.random() * 4);
			testQueue.add(directions[randomNum]);
		}
		
		//Create the behavior objects
		Behavior BehaviorForward = new Forward(pilot,behaviorVar);
		Behavior BehaviorTurnAround = new TurnAround(pilot,behaviorVar);
		Behavior BehaviorRotateLeft = new RotateLeft(pilot,behaviorVar);
		Behavior BehaviorRotateRight = new RotateRight(pilot,behaviorVar);
		Behavior BehaviorPassiveCheck = new PassiveCheckSituation(sensors,detector);
		
		
		
		//Create and run arbitrator with the list of behaviours. 
		Behavior[] behaviorList = {BehaviorPassiveCheck,BehaviorTurnAround,BehaviorRotateRight,BehaviorRotateLeft,BehaviorForward};
		Arbitrator robotArby = new Arbitrator(behaviorList);
		robotArby.start();
		
		
		//Add an escape route
		Button.LEFT.addButtonListener(new ButtonListener() {
			@Override
			public void buttonReleased(Button b) { }

			@Override
			public void buttonPressed(Button b) {
				System.exit(3000);
			}
		});
		
		
		//Create the run while loop which will constantly poll the sensors 
		while(run){
			System.out.println("running");
			//Sleep the thread to prevent too many resources being used
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
		}
		
		

		
		
	}
	
}