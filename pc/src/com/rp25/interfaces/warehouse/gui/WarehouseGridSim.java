package com.rp25.interfaces.warehouse.gui;

import java.util.ArrayList;

import lejos.robotics.RangeFinder;
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
	private ArrayList<Thread> robotThreads;
	
	public GridMapVisualisation makeSim(int numBots) {
		GridMap map = MapUtils.createRealWarehouse();
		
		MapBasedSimulation sim = new MapBasedSimulation(map);
		
		robotThreads = new ArrayList<>();
		
		for (int i = 0; i < numBots; i++) {
			GridPose gridStart = new GridPose(3 * i, 0, Heading.PLUS_Y);

			MobileRobotWrapper<MovableRobot> wrapper = sim.addRobot(
					SimulatedRobots.makeConfiguration(false, true),
					map.toPose(gridStart));

			RangeFinder ranger = sim.getRanger(wrapper);

			RandomGridWalk controller = new RandomGridWalk(wrapper.getRobot(),
					map, gridStart, ranger);

			Thread bot = new Thread(controller);
			bot.start();
			robotThreads.add(bot);
		}
		
		viz = new GridMapVisualisation(map, sim.getMap());
		MapVisualisationComponent.populateVisualisation(viz, sim);
		
		return viz;
	}
	
}
