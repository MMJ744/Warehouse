package com.rp25.motion.DAN.behavior;

import java.util.ArrayList;
import java.util.List;

import com.rp25.motion.DAN.detector.JunctionDetector;

import lejos.robotics.navigation.DifferentialPilot;
import lejos.robotics.subsumption.Behavior;

import static com.rp25.motion.DAN.behavior.GoTheFuckForward.SPTS;

public class NavigateJunction implements Behavior {
	private DifferentialPilot pilot;
	private JunctionDetector detector;

	private boolean acting = false;
	private boolean suppressed = false;

	List<String> route = new ArrayList<String>() {{
		add("fwd");
		add("lft");
		add("fwd");
		add("fwd");
		add("fwd");
		add("fwd");
		add("fwd");
		add("lft");
		add("fwd");
		add("lft");
		add("fwd");
		add("fwd");
		add("fwd");
		add("fwd");
		add("fwd");
		add("lft");
	}};

	int curAction = 0;

	public NavigateJunction(DifferentialPilot pilot, JunctionDetector detector) {
		this.pilot = pilot;
		this.detector = detector;
	}

	@Override
	public boolean takeControl() {
		return detector.isJunctionDetected() && !suppressed;
	}

	@Override
	public void action() {
		System.out.println("JUNCT " + curAction);

		String action = route.get((curAction++) % route.size());
		
		if(action.equals("fwd")) {
			pilot.forward();
			SPTS(pilot);
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else if(action.equals("lft")) {
			pilot.travel(75);
			SPTS(pilot);
			pilot.setRotateSpeed(pilot.getMaxRotateSpeed());
			pilot.rotate(92.5);
			
			while(pilot.isMoving() && !suppressed)
				Thread.yield();
			
		} else if(action.equals("rght")) {
			pilot.travel(75);
			SPTS(pilot);
			pilot.setRotateSpeed(pilot.getMaxRotateSpeed());
			pilot.rotate(-92.5);
			
			while(pilot.isMoving() && !suppressed)
				Thread.yield();
		}
	}

	@Override
	public void suppress() {
		suppressed = true;
	}
}
