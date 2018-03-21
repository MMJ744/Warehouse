package com.rp25.tools;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

public class Channel {
private DataInputStream in;
private DataOutputStream out;

public  Channel(InputStream in, OutputStream out) {
	this.in = new DataInputStream(in);
	this.out = new DataOutputStream(out);
}

public DataOutputStream getOutput() {
	return out;
}

public DataInputStream getInput() {
		return in;
	}
}
