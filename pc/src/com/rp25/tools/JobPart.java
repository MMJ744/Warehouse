package src.com.rp25.tools;

import java.awt.Point;
import java.math.BigDecimal;

public class JobPart {
	
	private int xCoord;
	private int yCoord;
	private String name;
	private int numberOfItems;
	private BigDecimal weight;
	private BigDecimal reward;
	
	public JobPart(String name,int x,int y,int numItems, BigDecimal weight, BigDecimal reward) {
		this.name = name;
		xCoord = x;
		yCoord = y;
		numberOfItems = numItems;
		this.weight = weight;
		this.reward = reward;
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
		// TODO Auto-generated method stub
		return weight;
	}
	
	public BigDecimal getReward() {
		// TODO Auto-generated method stub
		return reward;
	}

}

