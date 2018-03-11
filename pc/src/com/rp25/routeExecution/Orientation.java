package com.rp25.routeExecution;

import org.apache.log4j.Logger;

public enum Orientation {
	N(0),E(1),S(2),W(3);
	int num;
	final static Logger logger = Logger.getLogger(Orientation.class);
	
	Orientation(int i) {
		num = i;
	}
	
	public static Command rotate(Orientation start, Orientation goal) {
		if(goal.toInt() == start.toInt() + 1 || goal.toInt() == start.toInt() - 3)
			return Command.RIGHT;
		if(goal.toInt() == start.toInt() + 2 || goal.toInt() == start.toInt() - 2)
			return Command.UTURN;
		if(goal.toInt() == start.toInt() + 3 || goal.toInt() == start.toInt() - 1)
			return Command.LEFT;
		logger.debug("invalid robot is already pointing in correct direction");
		return Command.FORWARD;
	}
	
	public int toInt() {
		return num;
	}
}
