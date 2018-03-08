package com.rp25.networkCommunication;
import java.io.DataInputStream;
import java.io.DataOutputStream;

import tools.Channel;
import tools.Job;

public class Sender  {

	public static enum Purpose{JOB,MOVE}
	public enum Command {
	FORWARD, LEFT, RIGHT, UTURN,
	}

	public static Channel[] channels = new Channel[PCMain.robotNo]; //keeps track of senders for each robot


	public int sendJob(int i, Job j) {
		try {
			DataOutputStream out = new DataOutputStream(channels[i].getOutput()); //gets output stream for that robot.
			out.writeInt(Purpose.JOB.ordinal()); //writes the enum int to stream
			out.flush();
			//send interface shizzle
			out.flush();
			return (new DataInputStream(channels[i].getInput())).readInt();
		}
		
		catch(Exception e){
			return -1;
		}
		
	}
	public int sendMove(int i, Command c ) {
		try {
			DataOutputStream out = new DataOutputStream(channels[i].getOutput()); //gets output stream for that robot.
			out.writeInt(Purpose.MOVE.ordinal()); //writes the enum int to stream
			out.writeInt(c.ordinal()); // writes move enum int to stream
			out.flush();
			return (new DataInputStream(channels[i].getInput())).readInt();
		}
		
		catch(Exception e){
			return -1;
		}
		
	}
}
