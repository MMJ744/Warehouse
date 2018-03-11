package com.rp25.routeExecution;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;

public class Tests {

	@Test
	public void OrientationTurnRight() {
		Assertions.assertEquals(Command.RIGHT, Orientation.rotate(Orientation.N, Orientation.E));
		Assertions.assertEquals(Command.RIGHT, Orientation.rotate(Orientation.E, Orientation.S));
		Assertions.assertEquals(Command.RIGHT, Orientation.rotate(Orientation.S, Orientation.W));
		Assertions.assertEquals(Command.RIGHT, Orientation.rotate(Orientation.W, Orientation.N));
	}
	
	@Test
	public void OrientationTurnLeft() {
		Assertions.assertEquals(Command.LEFT, Orientation.rotate(Orientation.N, Orientation.W));
		Assertions.assertEquals(Command.LEFT, Orientation.rotate(Orientation.E, Orientation.N));
		Assertions.assertEquals(Command.LEFT, Orientation.rotate(Orientation.S, Orientation.E));
		Assertions.assertEquals(Command.LEFT, Orientation.rotate(Orientation.W, Orientation.S));
	}
	
	@Test
	public void OrientationTurn180() {
		Assertions.assertEquals(Command.UTURN, Orientation.rotate(Orientation.N, Orientation.S));
		Assertions.assertEquals(Command.UTURN, Orientation.rotate(Orientation.E, Orientation.W));
		Assertions.assertEquals(Command.UTURN, Orientation.rotate(Orientation.S, Orientation.N));
		Assertions.assertEquals(Command.UTURN, Orientation.rotate(Orientation.W, Orientation.E));
	}
	
	@Test // note this should never be the case in practice just to test theory
	public void OrientationNoChange() {
		Assertions.assertEquals(Command.FORWARD, Orientation.rotate(Orientation.N, Orientation.N));
		Assertions.assertEquals(Command.FORWARD, Orientation.rotate(Orientation.E, Orientation.E));
		Assertions.assertEquals(Command.FORWARD, Orientation.rotate(Orientation.S, Orientation.S));
		Assertions.assertEquals(Command.FORWARD, Orientation.rotate(Orientation.W, Orientation.W));
	}
}
