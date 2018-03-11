package com.rp25.networkCommunication;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.log4j.Logger;

import lejos.pc.comm.NXTCommException;
import lejos.pc.comm.NXTCommFactory;
import lejos.pc.comm.NXTInfo;
import tools.Channel;

public class PCMain {
	public static final int robotNo = 2;
	private static String[][] robots = {{"NXT1", "00:16:53:00:78:48"}} ; //must update to real values
	private final static Logger logger = Logger.getLogger(PCMain.class);
	public static void main(String[] args) {
		try {
			lejos.pc.comm.NXTComm nxtComm = NXTCommFactory.createNXTComm(NXTCommFactory.BLUETOOTH);
			//sets up channels of communication between PC and robot.
			for (int i = 0; i<robotNo; i++) {
				String[] robot = robots[i];
				NXTInfo info = new NXTInfo(NXTCommFactory.BLUETOOTH, robot[0],robot[1]);
				nxtComm.open(info);
				OutputStream out = nxtComm.getOutputStream();
				InputStream in = nxtComm.getInputStream();
				Sender.channels[i] = (new Channel(in,out));

			}
				Sender sender = new Sender();
				// pass sender object to RouteExecution class
				
		} catch (NXTCommException e) {
			// TODO Auto-generated catch block
			logger.debug("\n\nError using Bluetooth Drivers", e);
		}
	}

}
