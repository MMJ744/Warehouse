package com.rp25.networkCommunication;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import lejos.nxt.comm.Bluetooth;
import lejos.nxt.comm.NXTConnection;
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
		connection = Bluetooth.waitForConnection();
		DataInputStream in = connection.openDataInputStream();
		DataOutputStream out = connection.openDataOutputStream();
		(new Dispatcher(in, out, moveQueue, jobQueue,feedbackQueue)).start();
		//pass Robot interface jobQueue
		//pass Motion feedback Queue
		//pass Motion Control moveQueue
	}

}
