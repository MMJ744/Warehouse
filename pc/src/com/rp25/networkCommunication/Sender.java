package com.rp25.networkCommunication;
import java.io.DataInputStream;
import java.io.DataOutputStream;

import org.apache.log4j.Logger;

import com.rp25.routeExecution.Command;
import com.rp25.tools.Channel;
import com.rp25.tools.Job;


public class Sender  {

	public static enum Purpose{JOB,MOVE}

	private final static Logger logger = Logger.getLogger(Sender.class);
	
	public static Channel[] channels = new Channel[PCMain.robotNo]; //keeps track of senders for each robot

	public static int sendJob(int i, Job j) {
		--i;
		try {
			DataOutputStream out = channels[i].getOutput(); //gets output stream for that robot.
			out.writeInt(Purpose.JOB.ordinal()); //writes the enum int to stream
			logger.debug("Job int sent: " + Purpose.JOB.ordinal());
			out.flush();
			//send interface shizzle
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
