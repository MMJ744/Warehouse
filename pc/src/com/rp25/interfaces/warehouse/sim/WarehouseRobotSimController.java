package com.rp25.interfaces.warehouse.sim;

import com.rp25.tools.Robot;

import lejos.util.Delay;
import rp.robotics.mapping.GridMap;
import rp.robotics.navigation.GridPilot;
import rp.robotics.navigation.GridPose;
import rp.robotics.navigation.Heading;
import rp.robotics.simulation.MovableRobot;
import rp.systems.StoppableRunnable;

/**
 * The controller for each robot in the warehouse simulation.
 * @author ass782
 *
 */
public class WarehouseRobotSimController implements StoppableRunnable {

	private final GridMap m_map;

	private boolean m_running = true;
	private final MovableRobot m_robot;
	private final Robot m_interfaceBot;
	
	public WarehouseRobotSimController(MovableRobot _robot, GridMap _map, GridPose _start, Robot _interfaceBot) {
		m_map = _map;
		m_robot = _robot;
		m_interfaceBot = _interfaceBot;
	}
	
	
	@Override
	public void run() {
		while (m_running) {

			m_robot.setPose(m_map.toPose(new GridPose(m_interfaceBot.getX(),
					m_interfaceBot.getY(),
					Heading.PLUS_Y)));
			
			Delay.msDelay(1000);

		}
	}

	@Override
	public void stop() {
		m_running = false;
	}
}
