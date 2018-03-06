package tools;
import java.io.InputStream;
import java.io.OutputStream;

public class Channel {
private InputStream in;
private OutputStream out;

public  Channel(InputStream in, OutputStream out) {
	this.in = in;
	this.out = out;
}

public OutputStream getOutput() {
	return out;
}

public InputStream getInput() {
		return in;
	}
}
