package com.rp25.jobSelectionAndAllocation;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;

import com.rp25.tools.Job;
import com.rp25.tools.JobPart;

public class JobSelection {
	
	private BufferedReader brJobs;
	private BufferedReader brItems;
	private BufferedReader brLocation;
	private ArrayList<Item> items = new ArrayList<Item>();
	private ArrayList<Job> allJobs = new ArrayList<Job>();
	
	public JobSelection(String jobLocation, String itemsLocation, String locationLocation) {
		try {
			brJobs = new BufferedReader(new FileReader(jobLocation));
			brItems = new BufferedReader(new FileReader(itemsLocation));
			brLocation = new BufferedReader(new FileReader(locationLocation));
			String line;
			String line2;
			while((line = brLocation.readLine()) != null) {
				brItems.close();
				brItems = new BufferedReader(new FileReader(itemsLocation));
				String[] locationParts = line.split(",");
				while((line2 = brItems.readLine()) != null) {
					String itemParts[] = line2.split(",");
					if(itemParts[0].equals(locationParts[2])) {
						Item item = new Item(itemParts[0], Integer.parseInt(locationParts[0]), Integer.parseInt(locationParts[1]), new BigDecimal(itemParts[1]), new BigDecimal(itemParts[2]));
						items.add(item);
					}
				}
			}
			while((line = brJobs.readLine()) != null) {
				String[] jobParts = line.split("");
				Job job = new Job(jobParts[0]);
				allJobs.add(job);
				for(int i = 1; i < jobParts.length; i+=2) {
					for(Item item: items) {
						if(jobParts[i].equals(item.getName())) {
							job.addPart(new JobPart(item.getName(), item.getX(), item.getY(), Integer.parseInt(jobParts[i+1])));
						}
					}
				}
			}
		}
		catch(FileNotFoundException e) {
			System.out.println("Did not pass filename");
		}
		catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public void calculatePriority() {
		
		for(Job job: allJobs) {
			BigDecimal reward = new BigDecimal("0");
			BigDecimal weight = new BigDecimal("0");
			int numberOfPlaces = 0;
			ArrayList<JobPart> parts = job.getParts();
			for(JobPart part: parts) {
				Item currentItem = null;
				numberOfPlaces += 1;
				for(Item item: items) {
					if(item.getName().equals(part.getName())) {
						currentItem = item;
					}
				}
				weight.add(currentItem.getWeight());
				reward.add(currentItem.getReward());
			}
			BigDecimal priority = reward.divide(weight.add(new BigDecimal(numberOfPlaces)));
			job.setPriority(priority);
		}
		
		allJobs.sort(new PriorityComparator());
	}
	
	private static class PriorityComparator implements Comparator<Job>{
		@Override
		public int compare(Job o1, Job o2) {
			// TODO Auto-generated method stub
			return o1.getPriority().compareTo(o2.getPriority());
		}
		
	}
	
	public void nextJob() {
		Job nextJob = allJobs.get(0);
		allJobs.remove(0);
		JobAllocation.allocateJob(nextJob);
	}
	
	public void cancelJobByJob(Job toCancel) {
		for(Job job: allJobs) {
			if(job.getName().equals(toCancel.getName())) {
				allJobs.remove(job);
				break;
			}
		}
	}
	
	public void cancelJobByName(String cancelName) {
		for(Job job: allJobs) {
			if(job.getName().equals(cancelName)) {
				allJobs.remove(job);
				break;
			}
		}
	}

}
