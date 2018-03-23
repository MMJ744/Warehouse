package tests.com.rp25.routePlanning;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Test;

import com.rp25.routePlanning.AStarSearch;
import com.rp25.routePlanning.Grid;
import com.rp25.routePlanning.PathNotFoundException;
import com.rp25.tools.Robot;

public class RoutePlanningTest {

	private final static Logger logger = Logger.getLogger(RoutePlanningTest.class);

	@Test
	public void pathShouldBeBuilt() {
		Robot r = new Robot(0, 0, 0);
		Grid grid = new Grid(12, 8);
		AStarSearch.getInstance().setGrid(grid);
		Point startXY = new Point(0, 0);
		Point goalXY = new Point(3, 3);
		int priority = 0;
		List<Point> path;
		try {
			path = AStarSearch.getInstance().getPath(r, startXY, goalXY, priority);
			assertNotNull("Path Not Built", path);
		} catch (PathNotFoundException e) {
			// TODO Auto-generated catch block
			logger.debug("", e);
		}
	}

	@Test
	public void searchShouldReachGoalThroughExpectedPath() {
		Robot r = new Robot(0, 0, 0);
		Grid grid = new Grid(12, 8);
		AStarSearch.getInstance().setGrid(grid);
		Point startXY = new Point(1, 2);
		Point goalXY = new Point(4, 3);
		int priority = 0;
		List<Point> path;
		try {
			path = AStarSearch.getInstance().getPath(r, startXY, goalXY, priority);
			try {
				List<Point> expectedPath = new ArrayList<Point>();
				expectedPath.add(new Point(1, 3));
				expectedPath.add(new Point(2, 3));
				expectedPath.add(new Point(3, 3));
				expectedPath.add(new Point(4, 3));
				assertNotNull(path);
				for (int i = 0; i < path.size(); i++) {
					assertEquals("Incorrect Node in path", expectedPath.get(i), path.get(i));
				}
			} catch (NullPointerException e) {
				logger.info("Incorrect path found");
			}
		} catch (PathNotFoundException e1) {
			// TODO Auto-generated catch block
			logger.info("Path Not Found", e1);
		}
	}

	@Test
	public void searchShouldReachGoalAroundObstacles() {
		Robot r = new Robot(0, 0, 0);
		Point[] obstacleArray = new Point[] { new Point(1, 1), new Point(1, 2), new Point(1, 3), new Point(1, 4),
				new Point(1, 5), new Point(4, 1), new Point(4, 2), new Point(4, 3), new Point(4, 4), new Point(4, 5),
				new Point(7, 1), new Point(7, 2), new Point(7, 3), new Point(7, 4), new Point(7, 5), new Point(10, 1),
				new Point(10, 2), new Point(10, 3), new Point(10, 4), new Point(10, 5) };
		Grid grid = new Grid(12, 8, Arrays.asList(obstacleArray));
		AStarSearch.getInstance().setGrid(grid);
		Point startXY = new Point(0, 0);
		Point goalXY = new Point(5, 3);
		int priority = 0;
		List<Point> path;
		try {
			path = AStarSearch.getInstance().getPath(r, startXY, goalXY, priority);
			try {
				List<Point> expectedPath = new ArrayList<Point>();
				expectedPath.add(new Point(1, 0));
				expectedPath.add(new Point(2, 0));
				expectedPath.add(new Point(3, 0));
				expectedPath.add(new Point(4, 0));
				expectedPath.add(new Point(5, 0));
				expectedPath.add(new Point(5, 1));
				expectedPath.add(new Point(5, 2));
				expectedPath.add(new Point(5, 3));
				assertNotNull(path);
				for (int i = 0; i < path.size(); i++) {
					assertEquals("Incorrect Node in path", expectedPath.get(i), path.get(i));
				}
			} catch (NullPointerException e) {
				logger.info("Incorrect path found");
			}
		} catch (PathNotFoundException e1) {
			// TODO Auto-generated catch block
			logger.info("Path Not Found", e1);
		}
	}
}
