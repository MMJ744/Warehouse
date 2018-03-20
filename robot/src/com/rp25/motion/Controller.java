package com.rp25.motion;

import java.util.ArrayList;

import rp.config.RobotConfigs;
import rp.systems.WheeledRobotSystem;
import lejos.nxt.Button;
import lejos.nxt.ButtonListener;
import lejos.nxt.SensorPort;
import lejos.robotics.navigation.DifferentialPilot;



import lejos.util.Delay;


//Import behaviours 
import com.rp25.motion.detector.DetectSituation;
import com.rp25.motion.detector.ReadSensors;

public class Controller{
	
	public static void main(String[] args){
		//Run main
		boolean run = true;
		
		//Create the differential pilot and link the robot, check am
		DifferentialPilot pilot = (new WheeledRobotSystem(RobotConfigs.CASTOR_BOT)).getPilot();
		pilot.setRotateSpeed(pilot.getMaxRotateSpeed()/10);
		pilot.setTravelSpeed(pilot.getMaxTravelSpeed()/10);

		
		//Create sensors object to poll
		ReadSensors sensors = new ReadSensors(SensorPort.S4,SensorPort.S3, SensorPort.S1);
		ArrayList<Boolean> sensorStateMap;
		//Create a detector object 
		DetectSituation detector = new DetectSituation();
		
		//Create a fake sample queue. 
		ArrayList<String> testQueue = new ArrayList<String>();
		for (int i=0;i < 20;i++){
			String[] directions = {"forward","turn-around","left","right"};
			int randomNum = (int) Math.floor(Math.random() * 4);
			testQueue.add(directions[randomNum]);
		}
		
		//Add an escape route
		Button.LEFT.addButtonListener(new ButtonListener() {
			@Override
			public void buttonReleased(Button b) { }

			@Override
			public void buttonPressed(Button b) {
				System.exit(3000);
			}
		});
		
		
		Boolean debugPrints = false;
		while(run){
			//Delay the while for a tiny bit to preserve cpu usage.
			Delay.msDelay(100);
			
			//At the start of every while, we should get the sensor state to determine what's going on.
			sensorStateMap = sensors.poll();
			if(debugPrints){System.out.println("CSM:"+sensorStateMap);}
			
			//Get any situation that is going on. 
			String situation = detector.getSituation(sensorStateMap);
			if(debugPrints){System.out.println("CSIT:"+situation);}
			
			//Deal with the situation using the appropriate controls. 
			//This should run until the situation has cured itself- i.e the next while run should run fine. 
			if (!situation.equals("none")){
				detector.dealWith(situation,sensors,pilot);
			}
		}
		
			
		
	}
	
}