package com.rp25.interfaces.warehouse.sim;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Collection;

import com.rp25.tools.Robot;

import rp.config.MobileRobotConfiguration;
import rp.robotics.MobileRobotWrapper;
import rp.robotics.mapping.GridMap;
import rp.robotics.mapping.MapUtils;
import rp.robotics.navigation.GridPose;
import rp.robotics.navigation.Heading;
import rp.robotics.simulation.MapBasedSimulation;
import rp.robotics.simulation.MovableRobot;
import rp.robotics.visualisation.MapVisualisationComponent;

/**
 * Creates a simulation of the warehouse.
 * 
 * @author ass782
 *
 */
public class WarehouseGridSim {

	private WarehouseGridViz viz;

	public WarehouseGridSim(Collection<Robot> collection, ArrayList<Point> pickups, ArrayList<Point> drops) {
		GridMap map = MapUtils.createRealWarehouse();

		MapBasedSimulation sim = new MapBasedSimulation(map);

		viz = new WarehouseGridViz(map, sim.getMap(), 150, pickups, drops);

		for (Robot r : collection) {
			GridPose gridStart = new GridPose(r.getX(), r.getY(), Heading.PLUS_Y);

			MobileRobotConfiguration config = new MobileRobotConfiguration(0.1f, 0.15f);

			MobileRobotWrapper<MovableRobot> wrapper = sim.addRobot(config, map.toPose(gridStart));

			WarehouseRobotSimController controller = new WarehouseRobotSimController(wrapper.getRobot(), map, gridStart,
					r);

			Thread bot = new Thread(controller);
			bot.start();
		}

		MapVisualisationComponent.populateVisualisation(viz, sim);
	}

	public WarehouseGridViz getViz() {
		return viz;
	}

}
