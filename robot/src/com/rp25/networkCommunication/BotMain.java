package com.rp25.networkCommunication;
import java.io.DataInputStream;
import java.io.DataOutputStream;

import lejos.nxt.comm.Bluetooth;
import lejos.nxt.comm.NXTConnection;
import lejos.util.Delay;
import tools.BlockingQueue;

public class BotMain {
	static BlockingQueue<Dispatcher.Command> moveQueue = 
			new BlockingQueue <Dispatcher.Command>();
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
		
		boolean running = true;
		System.out.println("Connected");
		while (running){
			System.out.println("Waiting for move");
			com.rp25.networkCommunication.Dispatcher.Command x = moveQueue.take();
			System.out.println(x);
			System.out.println("Movement Received");
			feedbackQueue.push(Integer.valueOf(0));
			Delay.msDelay(3000);
		}
		//pass Robot interface jobQueue
		//pass Motion feedback Queue
		//pass Motion Control moveQueue
	}

}
