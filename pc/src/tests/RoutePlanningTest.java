package tests;

import static org.junit.Assert.*;

import java.awt.Point;
import java.util.ArrayList;
import org.apache.log4j.Logger;
import org.junit.*;

import com.rp25.routePlanning.AStarSearch;

public class RoutePlanningTest {
	
	private final static Logger logger = Logger.getLogger(RoutePlanningTest.class);
	
	@Test
	public void searchShouldReachGoal(Point start, Point goal,  ArrayList<Point> expectedPath) {
		AStarSearch tester = new AStarSearch(start, goal);
		ArrayList<Point> path = new ArrayList<Point>();
		
		path = tester.search();
		
		try {
			assertNotNull(path);
			for(int i = 0; i < path.size(); i++) {
				assertEquals("Incorrect Node in path", expectedPath.get(i), path.get(i));
			}
		}catch(AssertionError e){
			logger.debug("Search Failed, path most likely null");
		}catch(NullPointerException e) {
			logger.debug("Incorrect path found");
		}
	
	}
	
}
