package jobSelectionAndAllocation;

import java.io.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;

import com.rp25.jobSelectionAndAllocation.Item;

public class Cancellation {
	
	private ArrayList<TestJob> allTestJobs = new ArrayList<TestJob>();
	private ArrayList<Item> allItems = new ArrayList<Item>();
	private BigDecimal probYes;
	private BigDecimal probNo;
	
	public Cancellation(String cancellations, String testJobs, ArrayList<Item> items) {
		try {
			BufferedReader testsReader = new BufferedReader(new FileReader(cancellations));
			BufferedReader cancellationReader = new BufferedReader(new FileReader(testJobs));
			allItems = items;
			String line1;
			String line2;
			while((line1 = testsReader.readLine()) != null) {
				line2 = cancellationReader.readLine();
				String[] testInfo = line1.split(",");
				String[] cancelInfo = line2.split(",");
				String itemName = null;
				int numOfItems = 0;
				int numOfTypes = 0;
				BigDecimal weight = new BigDecimal("0");
				BigDecimal reward = new BigDecimal("0");
				for(int i = 1; i<testInfo.length; i+=2) {
					itemName = testInfo[i];
					for(Item checkItem: allItems) {
						if(checkItem.getName().equals(itemName)) {
							numOfItems += Integer.parseInt(testInfo[i+1]);
							numOfTypes += 1;
							weight = weight.add(checkItem.getWeight());
							reward = reward.add(checkItem.getReward());
							break;
						}
					}
				}
				allTestJobs.add(new TestJob(itemName, numOfItems, numOfTypes, reward, weight, Integer.parseInt(cancelInfo[1])));
				this.learn();
			}
			
		} 
		catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void learn() {
		Collections.shuffle(allTestJobs);
		int trainingSetSize = (int) (allTestJobs.size()*0.8);
		int testSetSize = allTestJobs.size() - trainingSetSize;
		int yesesInTest = 0;
		for(int i = 0; i<trainingSetSize; i++) {
			if(allTestJobs.get(i).getIsCancelled() == 1) {
				yesesInTest += 1;
			}
		}
		probYes = new BigDecimal(yesesInTest).divide(new BigDecimal(trainingSetSize));
		probNo = new BigDecimal(1).subtract(probYes);
	}
	
	private class TestJob{
		private String name;
		private int numOfItems;
		private int numOfItemTypes;
		private BigDecimal reward;
		private BigDecimal weight;
		private int isCancelled;
		
		public TestJob(String name, int numOfItems, int numOfItemTypes, BigDecimal reward, BigDecimal weight, int isCancelled) {
			this.name = name;
			this.numOfItems = numOfItems;
			this.numOfItemTypes = numOfItemTypes;
			this.reward = reward;
			this.weight = weight;
			this.isCancelled = isCancelled;
		}
		
		public String getName() {
			return name;
		}
		
		public int getNumOfItems() {
			return numOfItems;
		}
		
		public int getNumOfItemTypes() {
			return numOfItemTypes;
		}

		public BigDecimal getReward() {
			return reward;
		}

		public BigDecimal getWeight() {
			return weight;
		}

		public int getIsCancelled() {
			return isCancelled;
		}
	}
}
