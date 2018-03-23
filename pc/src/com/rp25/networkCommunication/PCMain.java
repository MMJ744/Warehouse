package com.rp25.networkCommunication;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;

import org.apache.log4j.Logger;

import lejos.pc.comm.NXTCommException;
import lejos.pc.comm.NXTCommFactory;
import lejos.pc.comm.NXTInfo;

import com.rp25.routeExecution.Command;
import com.rp25.tools.Channel;

public class PCMain {
	public static final int robotNo = 3;

	private final static Logger logger = Logger.getLogger(PCMain.class);
	private static String[][] robots = {
			{"iRobot", "00:16:53:17:BA:74"},
			{"Thunderbird 6","00:16:53:17:BD:EA"},
			//{"Spike","00:16:53:0A:A6:81"},
			//{"Chappie","00:16:53:1A:F6:D6"}
			{"Dave","00:16:53:08:DA:D2"}
			} ; //robots

	public static void main(String[] args) {
		try {
			
			//sets up channels of communication between PC and robot.
			for (int i = 0; i<robotNo; i++) {
				lejos.pc.comm.NXTComm nxtComm = NXTCommFactory.createNXTComm(NXTCommFactory.BLUETOOTH);
				String[] robot = robots[i];
				NXTInfo info = new NXTInfo(NXTCommFactory.BLUETOOTH, robot[0],robot[1]);
				nxtComm.open(info);//reached
				OutputStream out = nxtComm.getOutputStream();
				InputStream in = nxtComm.getInputStream();
				Sender.channels[i] = (new Channel(in,out));

			}
			logger.debug(Arrays.toString(Sender.channels));

				//Sender sender = new Sender();
				// pass sender object to RouteExecution class {Sender is now static}
				
		}catch (NXTCommException e) {
			// TODO Auto-generated catch block
			logger.info("\n\nError using Bluetooth Drivers", e);
		}
		//logger.debug(Sender.sendMove(1, Command.FORWARD));
		//logger.debug(Sender.sendMove(2, Command.LEFT));
		//logger.debug(Sender.sendMove(3, Command.UTURN));
		//Sender.sendMove(1,Command.LEFT);
		//Sender.sendMove(2, Command.FORWARD);
		//Sender.sendMove(3,Command.FORWARD);

	}
	

}
