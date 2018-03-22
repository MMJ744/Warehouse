package com.rp25.networkCommunication;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.OutputStreamWriter;

import org.apache.log4j.Logger;

import com.rp25.routeExecution.Command;
import com.rp25.tools.Channel;
import com.rp25.tools.Job;


public class Sender  {

	public static enum Purpose{JOB,MOVE, POLL}

	private final static Logger logger = Logger.getLogger(Sender.class);
	
	public static Channel[] channels = new Channel[PCMain.robotNo]; //keeps track of senders for each robot
	/**
	 * Please don't use this in a different thread to where you use sendJob or sendMove for a single bot.
	 * it'll mess everything up
	 * */
	public static int pollButton(int i){
		
		try {
			DataOutputStream out = channels[i].getOutput(); //gets output stream for that robot.
			out.writeInt(Purpose.POLL.ordinal()); //writes the enum int to stream
			logger.debug("Button poll sent: " + Purpose.POLL.ordinal());
			out.flush();
			return (new DataInputStream(channels[i].getInput())).readInt();
		}
		catch(Exception e){
			logger.info("Poll failed", e);
			return -1;
		}	
	}	
	public static int sendJob(int i, String s) {
		--i;
		
		try {
			DataOutputStream out = channels[i].getOutput(); //gets output stream for that robot.
			out.writeInt(Purpose.JOB.ordinal()); //writes the enum int to stream
			logger.debug("Job int sent: " + Purpose.JOB.ordinal());
			new BufferedWriter(new OutputStreamWriter(System.out)).write(s);
			out.flush();
			return (new DataInputStream(channels[i].getInput())).readInt();
		}
		
		catch(Exception e){
			logger.info("Job send failed", e);
			return -1;
		}		
	}
	public static int sendMove(int i, Command c ) {
		--i;
		try {
			DataOutputStream out = channels[i].getOutput(); //gets output stream for that robot.
			out.writeInt(Purpose.MOVE.ordinal()); //writes the enum int to stream
			out.writeInt(c.ordinal()); // writes move enum int to stream
			logger.debug("Command int sent: " + c.ordinal());
			out.flush();
			return channels[i].getInput().readInt();
		}
		
		catch(Exception e){
			logger.debug("Move send failed", e);
			return -1;
		}
	}
}

class SendCommand {
	int i;
	Command c;
}
