package src.com.rp25.tools;

import java.awt.Point;
import java.math.BigDecimal;

import java.awt.Point;
import java.math.BigDecimal;

public class JobPart {
	
	private int xCoord;
	private int yCoord;
	private String name;
	private int numberOfItems;
	private BigDecimal weight;
<<<<<<< HEAD
	private BigDecimal reward;
	
	public JobPart(String name,int x,int y,int numItems, BigDecimal weight, BigDecimal reward) {
=======
	
	public JobPart(String name,int x,int y,int numItems, BigDecimal weight) {
>>>>>>> c1b1dc892ba8bb4a0b8bfa9354bbf87a301662ac
		this.name = name;
		xCoord = x;
		yCoord = y;
		numberOfItems = numItems;
		this.weight = weight;
<<<<<<< HEAD
		this.reward = reward;
=======
>>>>>>> c1b1dc892ba8bb4a0b8bfa9354bbf87a301662ac
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

