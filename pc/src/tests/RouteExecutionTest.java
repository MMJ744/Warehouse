package tests;

import org.junit.Assert;
import org.junit.Test;

import com.rp25.routeExecution.Command;
import com.rp25.routeExecution.Orientation;

public class RouteExecutionTest {

	@Test
	public void OrientationTurnRight() {
		Assert.assertEquals(Command.RIGHT, Orientation.rotate(Orientation.N, Orientation.E));
		Assert.assertEquals(Command.RIGHT, Orientation.rotate(Orientation.E, Orientation.S));
		Assert.assertEquals(Command.RIGHT, Orientation.rotate(Orientation.S, Orientation.W));
		Assert.assertEquals(Command.RIGHT, Orientation.rotate(Orientation.W, Orientation.N));
	}
	
	@Test
	public void OrientationTurnLeft() {
		Assert.assertEquals(Command.LEFT, Orientation.rotate(Orientation.N, Orientation.W));
		Assert.assertEquals(Command.LEFT, Orientation.rotate(Orientation.E, Orientation.N));
		Assert.assertEquals(Command.LEFT, Orientation.rotate(Orientation.S, Orientation.E));
		Assert.assertEquals(Command.LEFT, Orientation.rotate(Orientation.W, Orientation.S));
	}
	
	@Test
	public void OrientationTurn180() {
		Assert.assertEquals(Command.UTURN, Orientation.rotate(Orientation.N, Orientation.S));
		Assert.assertEquals(Command.UTURN, Orientation.rotate(Orientation.E, Orientation.W));
		Assert.assertEquals(Command.UTURN, Orientation.rotate(Orientation.S, Orientation.N));
		Assert.assertEquals(Command.UTURN, Orientation.rotate(Orientation.W, Orientation.E));
	}
	
	@Test // note this should never be the case in practice just to test theory
	public void OrientationNoChange() {
		Assert.assertEquals(Command.FORWARD, Orientation.rotate(Orientation.N, Orientation.N));
		Assert.assertEquals(Command.FORWARD, Orientation.rotate(Orientation.E, Orientation.E));
		Assert.assertEquals(Command.FORWARD, Orientation.rotate(Orientation.S, Orientation.S));
		Assert.assertEquals(Command.FORWARD, Orientation.rotate(Orientation.W, Orientation.W));
	}
}
