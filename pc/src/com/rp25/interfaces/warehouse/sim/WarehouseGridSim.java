package com.rp25.interfaces.warehouse.sim;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Collection;

import com.rp25.tools.Robot;

import lejos.robotics.RangeFinder;
import rp.config.MobileRobotConfiguration;
import rp.robotics.MobileRobotWrapper;
import rp.robotics.control.RandomGridWalk;
import rp.robotics.mapping.GridMap;
import rp.robotics.mapping.MapUtils;
import rp.robotics.navigation.GridPose;
import rp.robotics.navigation.Heading;
import rp.robotics.simulation.MapBasedSimulation;
import rp.robotics.simulation.MovableRobot;
import rp.robotics.simulation.SimulatedRobots;
import rp.robotics.visualisation.GridMapVisualisation;
import rp.robotics.visualisation.MapVisualisationComponent;

public class WarehouseGridSim {
	
	private GridMapVisualisation viz;
	
	public WarehouseGridSim(Collection<Robot> collection) {
		GridMap map = MapUtils.createRealWarehouse();
		
		MapBasedSimulation sim = new MapBasedSimulation(map);
		
		for (Robot r : collection) {
			GridPose gridStart = new GridPose(r.getX(), r.getY(), Heading.PLUS_Y);

			MobileRobotConfiguration config = new MobileRobotConfiguration(0.2f, 0.2f); 
			
			MobileRobotWrapper<MovableRobot> wrapper = sim.addRobot(config, map.toPose(gridStart));

			WarehouseRobotSimController controller = new WarehouseRobotSimController(wrapper.getRobot(),
					map, gridStart, r);

			Thread bot = new Thread(controller);
			bot.start();
		}
		
		viz = new GridMapVisualisation(map, sim.getMap()) {
			private static final long serialVersionUID = 1L;
			@Override
			public Dimension getPreferredSize() {
			    return new Dimension(450, 400);
			}
		};
		
		MapVisualisationComponent.populateVisualisation(viz, sim);
	}
	
	public GridMapVisualisation getViz() {
		return viz;
	}

}
