package com.rp25.routePlanning;

import java.awt.Point;
import java.util.Optional;

public class Node implements Comparable<Node> {
	private Point xy;
	private Point goal;

	private int step;

	private Optional<Node> parent;
	private Grid grid;

	private int f;
	private int g;
	private int h;

	public Node(Point xy, Point goal, int step, Node parent, Grid grid) {
		this.xy = xy;
		this.goal = goal;

		this.step = step;

		this.parent = Optional.ofNullable(parent);
		this.grid = grid;

		g = this.parent.isPresent() ? parent.g + 1 : 0;
		h = calcH();
		f = g + h;
	}

	public int getF() {
		return f;
	}

	public int getStep() {
		return step;
	}

	public Point getXY() {
		return xy;
	}

	public Optional<Node> getParent() {
		return parent;
	}

	private int calcH() {
		if (grid.isObstacle(xy))
			return Integer.MAX_VALUE;

		if (parent.isPresent()) {
			Point pXY = parent.get().getXY();
			Cell oldCell = new Cell(pXY, step - 1);
			Cell newCell = new Cell(xy, step);

			if (grid.isCellUnavailable(oldCell, newCell))
				return Integer.MAX_VALUE;
		}

		int x = (int) Math.abs(xy.getX() - goal.getX());
		int y = (int) Math.abs(xy.getY() - goal.getY());
		return x + y;
	}

	@Override
	public int compareTo(Node o) {
		return f - o.getF();
	}
}