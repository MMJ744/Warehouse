package tools;

public class Robot {
	
	private final int id;
	private Job currentJob;
	private int x, y;
	
	public Robot(Job j, int idNumber, int xCoor, int yCoor) {
		currentJob = j;
		id = idNumber;
		x = xCoor;
		y = yCoor;
	}

	public Job getCurrentJob() {
		return currentJob;
	}

	public void setCurrentJob(Job newJob) {
		currentJob = newJob;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public void updateCoordinates(int newX, int newY) {
		x = newX;
		y = newY;
	}

}
