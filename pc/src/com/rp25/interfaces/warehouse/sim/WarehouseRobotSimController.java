package com.rp25.interfaces.warehouse.sim;

import java.util.Random;

import com.rp25.tools.Robot;

import lejos.robotics.RangeFinder;
import rp.robotics.mapping.GridMap;
import rp.robotics.navigation.GridPilot;
import rp.robotics.navigation.GridPose;
import rp.robotics.simulation.MovableRobot;
import rp.systems.StoppableRunnable;

public class WarehouseRobotSimController implements StoppableRunnable {

	private final GridMap m_map;
	private final GridPilot m_pilot;

	private boolean m_running = true;
	private final RangeFinder m_ranger;
	private final MovableRobot m_robot;
	private final Robot m_interfaceBot;
	
	public WarehouseRobotSimController(MovableRobot _robot, GridMap _map, GridPose _start,
			RangeFinder _ranger, Robot _interfaceBot) {
		m_map = _map;
		m_pilot = new GridPilot(_robot.getPilot(), _map, _start);
		m_ranger = _ranger;
		m_robot = _robot;
		m_interfaceBot = _interfaceBot;
	}
	
	
	@Override
	public void run() {
		while (m_running) {
			
			GridPose current = m_pilot.getGridPose();

		}
	}

	@Override
	public void stop() {
		m_running = false;
	}
}
