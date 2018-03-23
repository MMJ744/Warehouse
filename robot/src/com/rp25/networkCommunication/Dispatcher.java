package com.rp25.networkCommunication;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import com.rp25.tools.BlockingQueue;
import com.rp25.tools.Command;

import lejos.util.Delay;


public class Dispatcher extends Thread {
	public static enum Purpose{JOB,MOVE,POLL}

	DataInputStream in;
	DataOutputStream out;
	private BlockingQueue<Command> moves;
	private BlockingQueue<String> jobs;
	private BlockingQueue<Integer> feedBack;
	
	public Dispatcher(DataInputStream in, DataOutputStream out, BlockingQueue<Command> moveQueue,
			BlockingQueue<String> jobQueue,	BlockingQueue<Integer> feedBackQueue) {
		this.setDaemon(true);
		this.in = in;
		this.out = out;
		this.moves = moveQueue;
		this.jobs = jobQueue;
		this.feedBack = feedBackQueue;
	}

	@Override
	public void run() {
		while(true) {
			try {
				Purpose purpose = Purpose.values()[in.readInt()];
				if (purpose == Purpose.JOB) {//dispatching job string
					String s =in.readUTF();
					jobs.push(s);
					out.writeInt(0);
					out.flush();
				}
				else if (purpose == Purpose.MOVE) { //dispatching a movement
					moves.push(Command.values()[in.readInt()]);
					out.writeInt(feedBack.take());
					out.flush();
				}
				else{ //returning poll of the button
					out.writeInt(feedBack.take());
					out.flush();
				}
			} catch (IOException e) {
				//Do nothing -readInt isn't blocking
			}
		}
	}
}
