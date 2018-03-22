package com.rp25.routePlanning;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

public class Grid {
	private int width;
	private int height;

	private int step = 0;

	private List<Point> obstacles;
	private List<Point> items;

	public Grid(int width, int height) {
		this(width, height, new ArrayList<Point>());
	}

	public Grid(int width, int height, List<Point> obstacles) {
		this(width, height, obstacles, new ArrayList<Point>());
	}

	public Grid(int width, int height, List<Point> obstacles, List<Point> items) {
		this.width = width;
		this.height = height;

		this.obstacles = obstacles;
		this.items = items;
	}

	private HashMap<Cell, Integer> reservationTable = new HashMap<>();

	public boolean containsXY(Point xy) {
		return (xy.getX() >= 0 && xy.getX() < width && xy.getY() >= 0 && xy.getY() < height);
	}

	public int getStep() {
		return step;
	}

	public boolean isObstacle(Point xy) {
		return obstacles.contains(xy);
	}

	public boolean reserveCell(Cell cell, int id) {
		return !Optional.ofNullable(reservationTable.put(cell, id)).isPresent();
	}

	public boolean reserveCellForDuration(Cell cell, int duration, int priority) {
		Point p = cell.getXY();
		int startStep = cell.getStep();

		for(int i = 0; i < duration; i++) {
			reservationTable.put(new Cell(p, startStep + i), priority);
		}

		return true;
	}

	private boolean isCellReserved(Cell cell) {
		return reservationTable.containsKey(cell);
	}

	public boolean isCellUnavailable(Cell oldCell, Cell newCell) {
		if(reservationTable.containsKey(newCell)) return true;
		
		boolean isNextPosReservedLastStep = isCellReserved(new Cell(newCell.getXY(), oldCell.getStep()));
		if(isNextPosReservedLastStep) {
			int r1 = reservationTable.get(new Cell(newCell.getXY(), oldCell.getStep()));
			boolean isLastPosReservedNextStep = isCellReserved(new Cell(oldCell.getXY(), newCell.getStep()));
			
			if(isLastPosReservedNextStep) {
				int r2 = reservationTable.get(new Cell(oldCell.getXY(), newCell.getStep()));
				
				return r1 == r2;
			} else return false;
		} else return false;
	}
	
	public void output() {
		for(int y = 0; y < height; y++) {
			for(int x = 0; x < width; x++) {
				if(obstacles.contains(new Point(x, y))) System.out.print("[#]");
				else if(items.contains(new Point(x, y))) System.out.print("[I]");
				else System.out.print("[ ]");
			}

			System.out.println();
		}
	}

	public void outputRightWayUp() {
		for(int y = height - 1; y >= 0; y--) {
			for(int x = 0; x < width; x++) {
				if(obstacles.contains(new Point(x, y))) System.out.print("[#]");
				else if(items.contains(new Point(x, y))) System.out.print("[I]");
				else System.out.print("[ ]");
			}

			System.out.println();
		}
	}
	
	public void outputWithMarker(Point markerPos, String marker) {
		for(int y = -1; y < height; y++) {
			if(y == -1) System.out.print("[   ]");
			else System.out.print("[ " + y + " ]");
			for(int x = 0; x < width; x++) {
				if(y == -1) {
					System.out.print("[ " + x + " ]");
				} else {
					if(markerPos.equals(new Point(x, y))) System.out.print("[" + marker + "]");
					else if(obstacles.contains(new Point(x, y))) System.out.print("[ # ]");
					else if(items.contains(new Point(x, y))) System.out.print("[ I ]");
					else System.out.print("[   ]");
				}
			}

			System.out.println();
		}
	}

	public void outputWithPath(List<Point> path) {
		for(int y = 0; y < height; y++) {
			for(int x = 0; x < width; x++) {
				if(obstacles.contains(new Point(x, y))) System.out.print("[#]");
				else if(path.contains(new Point(x, y))) System.out.print("[P]");
				else if(items.contains(new Point(x, y))) System.out.print("[I]");
				else System.out.print("[ ]");
			}

			System.out.println();
		}
	}

	public void outputAtStep(int step) {
		for(int y = 0; y < height; y++) {
			for(int x = 0; x < width; x++) {
				if(obstacles.contains(new Point(x, y))) {
					System.out.print("[ # ]");
				} else {
					Optional<Integer> prio = Optional.ofNullable(reservationTable.get(new Cell(new Point(x, y), step)));

					if(prio.isPresent()) {
						System.out.print("[ " + prio.get() + " ]");
					} else if(items.contains(new Point(x, y))) {
						System.out.print("[ I ]");
					} else {
						System.out.print("[   ]");
					}
				}

			}

			System.out.println();
		}
	}

	public void outputWithMarkerAtStep(Point markerPos, String marker, int step) {
		for(int y = -1; y < height; y++) {
			if(y == -1) System.out.print("[   ]");
			else System.out.print("[ " + y + " ]");
			for(int x = 0; x < width; x++) {
				if(y == -1) {
					System.out.print("[ " + x + " ]");
				} else {
					if(markerPos.equals(new Point(x, y))) System.out.print("[" + marker + "]");
					else if(obstacles.contains(new Point(x, y))) System.out.print("[ # ]");
					else {
						Optional<Integer> prio = Optional.ofNullable(reservationTable.get(new Cell(new Point(x, y), step)));

						if(prio.isPresent()) {
							System.out.print("[ " + prio.get() + " ]");
						} else if(items.contains(new Point(x, y))) {
							System.out.print("[ I ]");
						} else {
							System.out.print("[   ]");
						}
					}
				}
			}

			System.out.println();
		}
	}
}