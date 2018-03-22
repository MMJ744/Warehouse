package com.rp25.jobSelectionAndAllocation;





import java.io.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;

import com.rp25.tools.HelperMethods;

public class Cancellation {
	
	
	private int[] probGivenTypes = new int[15];
	private int[] probGivenItems = new int[15]; //goes up in twos
	private int[] probGivenWeight = new int[15]; //Divide by 10 and round to int 
	private int[] probGivenReward = new int[20]; //Divide by 4 and round
	private int[] probYesGivenTypes = new int[15];
	private int[] probYesGivenItems = new int[15]; //goes up in twos
	private int[] probYesGivenWeight = new int[15]; //round to int 
	private int[] probYesGivenReward = new int[19]; //Divide by 4 and round
	private ArrayList<TestJob> allTestJobs = new ArrayList<TestJob>();
	private ArrayList<Item> allItems = new ArrayList<Item>();
	private BigDecimal probYes;
	private BigDecimal probNo;
	private int timesLearnt = 0;
	
	public Cancellation(String cancellations, String testJobs, ArrayList<Item> items) {
		try {
			BufferedReader testsReader = new BufferedReader(new FileReader(testJobs));
			BufferedReader cancellationReader = new BufferedReader(new FileReader(cancellations));
			allItems = items;
			String line1;
			String line2;
			int linesRead = 0;
			while((line1 = testsReader.readLine()) != null && (line2 = testsReader.readLine()) != null){
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
						}
					}
				}
				allTestJobs.add(new TestJob(itemName, numOfItems, numOfTypes, reward, weight, Integer.parseInt(cancelInfo[1])));
				
			}
			this.learn();
			testsReader.close();
			cancellationReader.close();
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
		if(trainingSetSize>0) {
			probYes = (new BigDecimal(yesesInTest)).divide(new BigDecimal(trainingSetSize),5, RoundingMode.HALF_UP);
			probNo = new BigDecimal(1).subtract(probYes);
		}
		else {
			System.out.println("Something's gone wrong");
		}
		for(int i = 0; i<trainingSetSize; i++) {
			TestJob testJob = allTestJobs.get(i);
			probGivenTypes[testJob.getNumOfItemTypes() - 1] += 1;
			if(testJob.getNumOfItems() == 1) {
				probGivenTypes[0] += 1;
				if(testJob.getIsCancelled()==1) {
					probYesGivenTypes[0] += 1;
				}
			}
			else {
				probGivenTypes[testJob.getNumOfItems()/2 - 1] += 1;
			}
			if(testJob.getWeight().divide(new BigDecimal("10")).intValue() - 1<0) {
				probGivenWeight[0] += 1;
			}
			else {
				probGivenWeight[testJob.getWeight().divide(new BigDecimal("10")).intValue() - 1] += 1;
			}
			if(testJob.getReward().divide(new BigDecimal("4")).intValue() - 1<0) {
				probGivenReward[0] += 1;
			}
			else {
				probGivenReward[testJob.getReward().divide(new BigDecimal("4")).intValue() - 1] += 1;
			}
			if(testJob.isCancelled == 1) {
				probYesGivenTypes[testJob.getNumOfItemTypes() - 1] += 1;
				if(testJob.getNumOfItems()/2 - 1<0) {
					probYesGivenItems[0] += 1;
				}
				else {
					probYesGivenItems[testJob.getNumOfItems()/2 - 1] += 1;
				}
				if(testJob.getWeight().divide(new BigDecimal("10")).intValue() - 1 < 0) {
					probYesGivenWeight[0] += 1;
				}
				else {
					probYesGivenWeight[testJob.getWeight().divide(new BigDecimal("10")).intValue() - 1] += 1;
				}
				if(testJob.getReward().divide(new BigDecimal("4")).intValue() - 1<0) {
					probYesGivenReward[0] += 1;
				}
				else {
					probYesGivenReward[testJob.getReward().divide(new BigDecimal("4")).intValue() - 1] += 1;
				}
			}
		}
		int correctPredicts = 0;
		for(int i = trainingSetSize; i<allTestJobs.size(); i++) {
			TestJob testJob = allTestJobs.get(i);
			int thisCancellation = testJob.getIsCancelled();
			BigDecimal calculatedCancelltion = testProbOfCancellation(testJob);
			if((thisCancellation == 1 && calculatedCancelltion.compareTo(new BigDecimal("0.5"))>-1)||(thisCancellation == 0 && calculatedCancelltion.compareTo(new BigDecimal("0.5"))<1)) {
				correctPredicts += 1;
			}
		}
		timesLearnt += 1;
		if(correctPredicts<(allTestJobs.size()-trainingSetSize)*0.7) {
			if(timesLearnt<3) {
				learn();
			}
		}
		
	}
	
	public BigDecimal testProbOfCancellation(TestJob job) {
		BigDecimal probOfCancellation = new BigDecimal("0");
		int numOfItems = job.getNumOfItems();
		BigDecimal weight = job.getWeight();
		BigDecimal reward = job.getReward();
		BigDecimal typesProb;
		if(job.getNumOfItemTypes()-1<0) {
			if(probGivenTypes[0] == 0) {
				typesProb = new BigDecimal("0.001");
			}
			else {
				typesProb = new BigDecimal(probYesGivenTypes[0]).divide(new BigDecimal(probGivenTypes[0]),5, RoundingMode.HALF_UP);
			}
		}
		else if(probYesGivenTypes[job.getNumOfItemTypes()-1] == 0) {
			typesProb = new BigDecimal("0.001");
		}
		else {
			typesProb = new BigDecimal(probYesGivenTypes[job.getNumOfItemTypes()-1]).divide(new BigDecimal(probGivenTypes[job.getNumOfItemTypes()-1]),5, RoundingMode.HALF_UP);
		}
		BigDecimal itemsProb;
		if(numOfItems/2-1<0) {
			if(probGivenItems[0]==0) {
				itemsProb = new BigDecimal("0.001");
			}
			else {
				itemsProb = new BigDecimal(probYesGivenItems[0]).divide(new BigDecimal(probGivenItems[0]),5 , RoundingMode.HALF_UP);
			}
		}
		else if(probGivenItems[numOfItems/2-1] == 0) {
			itemsProb = new BigDecimal("0.001");
		}
		else {
			itemsProb = new BigDecimal(probYesGivenItems[numOfItems/2-1]).divide(new BigDecimal(probGivenItems[numOfItems/2-1]),5 , RoundingMode.HALF_UP);
		}
		BigDecimal weightProb;
		if(weight.divide(new BigDecimal("10")).intValue()-1<0){
			if(probGivenWeight[0]==0) {
				weightProb = new BigDecimal("0.001");
			}
			else {
				weightProb = new BigDecimal(probYesGivenWeight[0]).divide(new BigDecimal(probGivenWeight[0]),5 , RoundingMode.HALF_UP);
			}			
		}
		else if(probYesGivenWeight[weight.divide(new BigDecimal("10")).intValue()-1] == 0) {
			weightProb = new BigDecimal("0.001");
		}
		else {
			weightProb = new BigDecimal(probYesGivenWeight[weight.divide(new BigDecimal("10")).intValue()-1]).divide(new BigDecimal(probGivenWeight[weight.divide(new BigDecimal("10")).intValue()-1]),5 , RoundingMode.HALF_UP);
		}
		BigDecimal rewardProb;
		if(reward.divide(new BigDecimal("4")).intValue()-1<0) {
			if(probGivenReward[0]==0) {
				rewardProb = new BigDecimal("0.001");
			}
			else {
				rewardProb = new BigDecimal(probYesGivenReward[0]).divide(new BigDecimal(probGivenReward[0]),5, RoundingMode.HALF_UP);
			}
		}
		else if(probYesGivenWeight[reward.divide(new BigDecimal("4")).intValue()-1] == 0) {
			rewardProb = new BigDecimal("0.001");
		}
		else {
			rewardProb = new BigDecimal(probYesGivenReward[reward.divide(new BigDecimal("4")).intValue()-1]).divide(new BigDecimal(probGivenTypes[reward.divide(new BigDecimal("4")).intValue()-1]),5 , RoundingMode.HALF_UP);
		}
		probOfCancellation = probYes.multiply(typesProb).multiply(itemsProb).multiply(weightProb).multiply(rewardProb);
		return probOfCancellation;
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
		BigDecimal typesProb;
			if(parts.size()-1<0) {
				if(probGivenTypes[0]==0) {
					typesProb = new BigDecimal("0.0001");
				}
				else {
					typesProb = new BigDecimal(probYesGivenTypes[0]).divide(new BigDecimal(probGivenTypes[0]), RoundingMode.HALF_UP);
				}
			}
			else if(probGivenTypes[parts.size()-1]==0) {
				typesProb = new BigDecimal("0.0001");
			}
			else {
				typesProb = new BigDecimal(probYesGivenTypes[parts.size()-1]).divide(new BigDecimal(probGivenTypes[parts.size()-1]), RoundingMode.HALF_UP);
			}
		BigDecimal itemsProb;
			if(numOfItems/2-1<0) {
				if(probGivenItems[0]==0) {
					itemsProb = new BigDecimal("0.0001");
				}
				else {
					itemsProb = new BigDecimal(probYesGivenItems[0]).divide(new BigDecimal(probGivenItems[0]), RoundingMode.HALF_UP);
				}
			}
			else if(probGivenItems[numOfItems/2-1]==0) {
				itemsProb = new BigDecimal("0.0001");
			}
			else {
				itemsProb = new BigDecimal(probYesGivenItems[numOfItems/2-1]).divide(new BigDecimal(probGivenItems[numOfItems/2-1]), RoundingMode.HALF_UP);
			}
		BigDecimal weightProb;
			if(weight.divide(new BigDecimal("10")).intValue()-1<0){
				if(probGivenWeight[0]==0) {
					weightProb = new BigDecimal("0.0001");
				}
				else {
					weightProb = new BigDecimal(probYesGivenWeight[0]).divide(new BigDecimal(probGivenWeight[0]), RoundingMode.HALF_UP);
				}
			}
			else if(probGivenWeight[weight.divide(new BigDecimal("10")).intValue()-1] == 0) {
				weightProb = new BigDecimal("0.0001");
			}
			else {
				weightProb = new BigDecimal(probYesGivenWeight[weight.divide(new BigDecimal("10")).intValue()-1]).divide(new BigDecimal(probGivenWeight[weight.divide(new BigDecimal("10")).intValue()-1]), RoundingMode.HALF_UP);
			}
		BigDecimal rewardProb;
			if(reward.divide(new BigDecimal("4")).intValue()-1<0) {
				if(probGivenReward[0]==0) {
					rewardProb = new BigDecimal("0.0001");
				}
				else {
					rewardProb = new BigDecimal(probYesGivenReward[0]).divide(new BigDecimal(probGivenReward[0]), RoundingMode.HALF_UP);
				}
			}
			else if(probYesGivenReward[reward.divide(new BigDecimal("4")).intValue()-1] == 0) {
				rewardProb = new BigDecimal("0.0001");
			}
			else {
				rewardProb = new BigDecimal(probYesGivenReward[reward.divide(new BigDecimal("4")).intValue()-1]).divide(new BigDecimal(probGivenReward[reward.divide(new BigDecimal("4")).intValue()-1]), RoundingMode.HALF_UP);
			}
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
