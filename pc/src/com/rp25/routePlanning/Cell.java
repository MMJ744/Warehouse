package com.rp25.routePlanning;

import java.awt.Point;

public class Cell {
	private Point xy;
	private int step;
	
	public Cell(Point xy, int step) {
		this.xy = xy;
		this.step = step;
	}
	
	public Point getXY() {
		return xy;
	}
	
	public int getStep() {
		return step;
	}
	
	@Override
	public String toString() {
		return "x: " + xy.x + ", y: " + xy.y + ", t: " + step;
	}
	
	private int pointHashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + xy.x;
		result = prime * result + xy.y;
		return result;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + step;
		result = prime * result + ((xy == null) ? 0 : pointHashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Cell other = (Cell) obj;
		if (step != other.step)
			return false;
		if (xy == null) {
			if (other.xy != null)
				return false;
		} else if (!xy.equals(other.xy))
			return false;
		return true;
	}
	
	
}
