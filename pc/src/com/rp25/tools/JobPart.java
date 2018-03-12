package com.rp25.tools;

public class JobPart {
	
	private int xCoord;
	private int yCoord;
	private String name;
	private int numberOfItems;
	
	public JobPart(String name,int x,int y,int numItems) {
		this.name = name;
		xCoord = x;
		yCoord = y;
		numberOfItems = numItems;
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
	
<<<<<<< HEAD:pc/src/com/rp25/tools/JobPart.java
=======
	@Deprecated
	public Point getCoordinates() {
		return new Point(xCoord, yCoord);
	}
	
>>>>>>> jobSelectionAndAllocation:pc/src/tools/JobPart.java
	public int getNumOfItems() {
		return numberOfItems;
	}

}

