package jobSelectionAndAllocation;

public class Item {
	
	private String name;
	private int reward;
	private int weight;
	
	public Item(String name, int reward, int weight) {
		this.name = name;
		this.reward = reward;
		this.weight = weight;
	}
	
	public String getName() {
		return name;
	}
	
	public int getReward() {
		return reward;
	}
	
	public int getWeight() {
		return weight;
	}

}
