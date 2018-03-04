package src.jobSelectionAndAllocation;

import java.math.BigDecimal;

public class Item {
	
	private int xCoord;
	private int yCoord;
	private String name;
	private BigDecimal weight;
	private BigDecimal reward;
	
	public Item(String name,int x,int y, BigDecimal weight, BigDecimal reward) {
		this.name = name;
		xCoord = x;
		yCoord = y;
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
	
	public BigDecimal getWeight() {
		return weight;
	}
	
	public BigDecimal getReward() {
		return reward;
	}

}
