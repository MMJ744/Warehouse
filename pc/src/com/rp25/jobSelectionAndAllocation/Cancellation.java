
package com.rp25.jobSelectionAndAllocation;


import java.io.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import com.rp25.tools.*;

import com.rp25.jobSelectionAndAllocation.Item;
import com.rp25.tools.Job;

public class Cancellation {
	
	
	private int[] probGivenTypes = new int[10];
	private int[] probGivenItems = new int[10]; //goes up in twos
	private int[] probGivenWeight = new int[10]; //round to int 
	private int[] probGivenReward = new int[10]; //Divide by 4 and round
	private int[] probYesGivenTypes = new int[10];
	private int[] probYesGivenItems = new int[10]; //goes up in twos
	private int[] probYesGivenWeight = new int[10]; //round to int 
	private int[] probYesGivenReward = new int[10]; //Divide by 4 and round
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
				String[] testInfo = HelperMethods.split(line1, ",", 0);
				String[] cancelInfo = HelperMethods.split(line2, ",", 0);
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
		for(TestJob testJob: allTestJobs) {
			probGivenTypes[testJob.getNumOfItemTypes() - 1] += 1;
			probGivenItems[testJob.getNumOfItems()/2 - 1] += 1;
			probGivenWeight[testJob.getWeight().intValue() - 1] += 1;
			probGivenReward[testJob.getReward().divide(new BigDecimal("4")).intValue() - 1] += 1;
			if(testJob.isCancelled == 1) {
				probYesGivenTypes[testJob.getNumOfItemTypes() - 1] += 1;
				probYesGivenItems[testJob.getNumOfItems()/2 - 1] += 1;
				probYesGivenWeight[testJob.getWeight().intValue() - 1] += 1;
				probYesGivenReward[testJob.getReward().divide(new BigDecimal("4")).intValue() - 1] += 1;
			}
		}
		
	}
	
	public BigDecimal probOfCancellation(Job job) {
		BigDecimal probOfCancellation = new BigDecimal("0");
		ArrayList<JobPart> parts = job.getParts();
		
		int numOfItems = 0;
		BigDecimal weight = new BigDecimal("0");
		BigDecimal reward = new BigDecimal("0");
		for(JobPart part: parts) {
			numOfItems += part.getNumOfItems();
			weight = weight.add(part.getWeight());
			reward = reward.add(part.getReward());
		}
		BigDecimal typesProb = new BigDecimal(probYesGivenTypes[parts.size()-1]).divide(new BigDecimal(probGivenTypes[parts.size()-1]));
		BigDecimal itemsProb = new BigDecimal(probYesGivenItems[numOfItems/2-1]).divide(new BigDecimal(probGivenItems[numOfItems/2-1]));
		BigDecimal weightProb = new BigDecimal(probYesGivenWeight[weight.intValue()-1]).divide(new BigDecimal(probGivenTypes[weight.intValue()-1]));
		BigDecimal rewardProb = new BigDecimal(probYesGivenReward[weight.divide(new BigDecimal("4")).intValue()-1]).divide(new BigDecimal(probGivenTypes[weight.divide(new BigDecimal("4")).intValue()-1]));
		probOfCancellation = probYes.multiply(typesProb).multiply(itemsProb).multiply(weightProb).multiply(rewardProb);
		return probOfCancellation;
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
