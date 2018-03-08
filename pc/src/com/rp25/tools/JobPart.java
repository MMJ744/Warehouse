package tools;

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
	
	public int getNumOfItems() {
		return numberOfItems;
	}

}
