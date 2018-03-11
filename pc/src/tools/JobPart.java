package src.tools;

import java.awt.Point;
import java.math.BigDecimal;

public class JobPart {
	
	private int xCoord;
	private int yCoord;
	private String name;
	private int numberOfItems;
	private BigDecimal weight;
	
	public JobPart(String name,int x,int y,int numItems, BigDecimal weight) {
		this.name = name;
		xCoord = x;
		yCoord = y;
		numberOfItems = numItems;
		this.weight = weight;
	}
	
	public String getName() {
		return name;
	}
	
	public int getX() {
		return xCoord;
	}
	
	public int getY() {
		return yCoord;
	}
	
	public Point getCoordinates() {
		return new Point(xCoord, yCoord);
	}
	
	public int getNumOfItems() {
		return numberOfItems;
	}
	
	public BigDecimal getWeight() {
		return weight;
	}

}
