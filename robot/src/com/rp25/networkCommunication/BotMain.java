package com.rp25.networkCommunication;
import java.io.DataInputStream;
import java.io.DataOutputStream;

import com.rp25.motion.behavior.CorrectLeftSkew;
import com.rp25.motion.behavior.CorrectRightSkew;
import com.rp25.motion.behavior.GoTheFuckForward;
import com.rp25.motion.behavior.NavigateJunction;
import com.rp25.motion.detector.JunctionDetector;
import com.rp25.motion.detector.LineDetector;
import com.rp25.tools.BlockingQueue;
import com.rp25.tools.Command;

import lejos.nxt.Button;
import lejos.nxt.ButtonListener;
import lejos.nxt.Motor;
import lejos.nxt.SensorPort;
import lejos.nxt.comm.Bluetooth;
import lejos.nxt.comm.NXTConnection;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.robotics.subsumption.Arbitrator;
import lejos.robotics.subsumption.Behavior;

public class BotMain {
	static BlockingQueue<Command> moveQueue = 
			new BlockingQueue <Command>();
	static BlockingQueue<String> jobQueue = 
			new BlockingQueue <String>();
	static BlockingQueue<Integer> feedbackQueue = 
			new BlockingQueue <Integer>();

private static NXTConnection connection;
	public static void main(String[] args) {
		System.out.println("Running");
		connection = Bluetooth.waitForConnection();
		DataInputStream in = connection.openDataInputStream();
		DataOutputStream out = connection.openDataOutputStream();
		(new Dispatcher(in, out, moveQueue, jobQueue,feedbackQueue)).start();		
		
		initRobot(moveQueue, feedbackQueue);
		
		//pass Robot interface jobQueue
		//pass Motion feedback Queue
		//pass Motion Control moveQueue
	}

	private static void initRobot(BlockingQueue<Command> moveQueue, BlockingQueue<Integer> feedbackQueue) {
		DifferentialPilot pilot = new DifferentialPilot(56, 110.5, Motor.C, Motor.B);
		LineDetector lineDetector = new LineDetector(SensorPort.S3, SensorPort.S2);
		JunctionDetector junctionDetector = new JunctionDetector(SensorPort.S3, SensorPort.S2);
		
		Behavior go = new GoTheFuckForward(pilot);
		Behavior correctLeftSkew = new CorrectLeftSkew(pilot, lineDetector);
		Behavior correctRightSkew = new CorrectRightSkew(pilot, lineDetector);
		Behavior navJunction = new NavigateJunction(pilot, junctionDetector, moveQueue, feedbackQueue);
		
		lineDetector.start();
		junctionDetector.start();
		
		Button.LEFT.addButtonListener(new ButtonListener() {
			@Override
			public void buttonReleased(Button b) { }

			@Override
			public void buttonPressed(Button b) {
				System.exit(3000);
			}
		});
		
		Arbitrator arb = new Arbitrator(new Behavior[] {go, correctLeftSkew, correctRightSkew, navJunction});
		arb.start();
	}
}
