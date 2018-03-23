package com.rp25.interfaces.warehouse.sim;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.util.ArrayList;

import lejos.geom.Point;
import rp.robotics.mapping.IGridMap;
import rp.robotics.mapping.LineMap;
import rp.robotics.visualisation.MapVisualisationComponent;

/**
 * Effectively a copy of rp.robotics.visualisation.GridMapVisualsation, with
 * extra features for more GUI flexibility
 * 
 * @author ass782
 *
 */

public class WarehouseGridViz extends MapVisualisationComponent {

	private static final long serialVersionUID = 1L;
	protected IGridMap m_gridMap;
	private ArrayList<java.awt.Point> pickUpPoints;
	private ArrayList<java.awt.Point> dropOffPoints;

	public WarehouseGridViz(IGridMap _gridMap, LineMap _lineMap, float _scaleFactor,
			ArrayList<java.awt.Point> _pickupPoints, ArrayList<java.awt.Point> _dropOffPoints) {
		super(_lineMap, _scaleFactor);
		m_gridMap = _gridMap;
		pickUpPoints = _pickupPoints;
		dropOffPoints = _dropOffPoints;
	}

	@Override
	public Dimension getPreferredSize() {
		return new Dimension(650, 600);
	}

	private void connectToNeighbour(Graphics2D _g2, int _x, int _y, int _dx, int _dy) {

		if (m_gridMap.isValidTransition(_x, _y, _x + _dx, _y + _dy)) {
			Point p1 = m_gridMap.getCoordinatesOfGridPosition(_x, _y);
			Point p2 = m_gridMap.getCoordinatesOfGridPosition(_x + _dx, _y + _dy);
			renderLine(p1, p2, _g2);
		}

	}

	@Override
	protected void renderMap(Graphics2D _g2) {
		// render lines first
		super.renderMap(_g2);

		_g2.setStroke(new BasicStroke(1));
		_g2.setPaint(Color.BLUE);

		// add grid
		for (int x = 0; x < m_gridMap.getXSize(); x++) {
			for (int y = 0; y < m_gridMap.getYSize(); y++) {
				if (!m_gridMap.isObstructed(x, y)) {
					Point gridPoint = m_gridMap.getCoordinatesOfGridPosition(x, y);
					if (isPickupPoint(x, y)) {
						_g2.setStroke(new BasicStroke(5));
						_g2.setPaint(Color.GREEN);
					} else if (isDropOffPoint(x, y)) {
						_g2.setStroke(new BasicStroke(5));
						_g2.setPaint(Color.RED);
					} else {
						_g2.setStroke(new BasicStroke(1));
						_g2.setPaint(Color.BLUE);
					}

					renderPoint(gridPoint, _g2, 0.02);
				}
			}
		}

		// and visualise valid connections
		for (int x = 0; x < m_gridMap.getXSize(); x++) {
			for (int y = 0; y < m_gridMap.getYSize(); y++) {

				if (m_gridMap.isValidGridPosition(x, y)) {
					connectToNeighbour(_g2, x, y, 1, 0);
					connectToNeighbour(_g2, x, y, 0, 1);
					connectToNeighbour(_g2, x, y, -1, 0);
					connectToNeighbour(_g2, x, y, 0, -1);
				}
			}
		}

	}

	private boolean isDropOffPoint(int x, int y) {
		for (java.awt.Point p : dropOffPoints) {
			if (p.getX() == x && p.getY() == y)
				return true;
		}
		return false;
	}

	private boolean isPickupPoint(int x, int y) {
		for (java.awt.Point p : pickUpPoints) {
			if (p.getX() == x && p.getY() == y)
				return true;
		}
		return false;
	}
}
