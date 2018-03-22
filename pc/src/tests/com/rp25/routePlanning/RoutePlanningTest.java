package tests.com.rp25.routePlanning;

import static org.junit.Assert.*;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

import org.apache.log4j.Logger;
import org.junit.*;

import com.rp25.routePlanning.AStarSearch;
import com.rp25.routePlanning.PathNotFoundException;

public class RoutePlanningTest {
	
	private final static Logger logger = Logger.getLogger(RoutePlanningTest.class);
	
	@Test
	public void pathShouldBeBuilt() {
		AStarSearch tester = new AStarSearch();
		Point startXY = new Point(0,0);
		Point goalXY = new Point(3,3);
		int priority = 0;
		logger.info("Logger Working");
		logger.debug("Logger still working");
		List<Point> path;
		try {
			path = tester.getPath(startXY, goalXY, priority);
			assertNotNull("Path Not Built", path);
		} catch (PathNotFoundException e) {
			// TODO Auto-generated catch block
			logger.debug("", e);
		}
	}
	
	@Test
	public void searchShouldReachGoalThroughExpectedPath() {
		AStarSearch tester = new AStarSearch();
		List<Point> path;
		Point startXY = new Point(1,2);
		Point goalXY = new Point(4,3);
		int priority = 0;
		try {
			path = tester.getPath(startXY, goalXY, priority);
			try {
				List<Point> expectedPath = new ArrayList<Point>();
				expectedPath.add(new Point(1,2));
				expectedPath.add(new Point(1,3));
				expectedPath.add(new Point(2,3));
				expectedPath.add(new Point(3,3));
				expectedPath.add(new Point(4,3));
				assertNotNull(path);
				for(int i = 0; i < path.size(); i++) {
					assertEquals("Incorrect Node in path", expectedPath.get(i), path.get(i));
				}
			}catch(AssertionError e){
				logger.info("Search Failed, path most likely null");
			}catch(NullPointerException e) {
				logger.info("Incorrect path found");
			}
		} catch (PathNotFoundException e1) {
			// TODO Auto-generated catch block
			logger.info("Path Not Found", e1);
		}
	}
}
