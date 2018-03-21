package com.rp25.motion;

import com.rp25.tools.Command;

public enum Orientation {
	N(0), E(1), S(2), W(3);
	int num;

	Orientation(int i) {
		num = i;
	}

	public static Command rotate(Orientation start, Orientation goal) {
		if (goal.toInt() == start.toInt() + 1 || goal.toInt() == start.toInt() - 3)
			return Command.RIGHT;
		if (goal.toInt() == start.toInt() + 2 || goal.toInt() == start.toInt() - 2)
			return Command.UTURN;
		if (goal.toInt() == start.toInt() + 3 || goal.toInt() == start.toInt() - 1)
			return Command.LEFT;
		
		return Command.FORWARD;
	}

	public int toInt() {
		return num;
	}
}