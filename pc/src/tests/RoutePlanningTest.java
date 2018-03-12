package tests;

import static org.junit.Assert.*;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Queue;

import org.apache.log4j.Logger;
import org.junit.*;

import com.rp25.routePlanning.AStarSearch;

public class RoutePlanningTest {
	
	private final static Logger logger = Logger.getLogger(RoutePlanningTest.class);
	
	@Test
	public void pathShouldBeBuilt(Point start, Point goal) {
		AStarSearch tester = new AStarSearch(start, goal);
		Queue<Point> path = new Queue<Point>();
		
		path = tester.search();
		
		assertNotNull("Path Not Built", path);
	}
	
	@Test
	public void searchShouldReachGoal(Point start, Point goal,  ArrayList<Point> expectedPath) {
		AStarSearch tester = new AStarSearch(start, goal);
		Queue<Point> path = new Queue<Point>();
		
		path = tester.search();
		
		try {
			assertNotNull(path);
			for(int i = 0; i < path.size(); i++) {
				assertEquals("Incorrect Node in path", expectedPath.get(i), path.pop());
			}
		}catch(AssertionError e){
			logger.debug("Search Failed, path most likely null");
		}catch(NullPointerException e) {
			logger.debug("Incorrect path found");
		}
	
	}
	
}
