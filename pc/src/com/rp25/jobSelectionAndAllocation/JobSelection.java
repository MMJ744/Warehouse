package src.com.rp25.jobSelectionAndAllocation;

import java.io.BufferedReader;
import com.rp25.tools.*;

import rp.util.Collections;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;

import org.apache.log4j.Logger;

public class JobSelection {
	
	private BufferedReader brJobs;
	private BufferedReader brItems;
	private BufferedReader brLocation;
	private ArrayList<Item> items = new ArrayList<Item>();
	private ArrayList<Job> allJobs = new ArrayList<Job>();
	private final static Logger logger = Logger.getLogger(JobSelection.class);
	private Cancellation cancel;
	
	public JobSelection(String jobLocation, String itemsLocation, String locationLocation, Cancellation cancel) {
		try {
			this.cancel = cancel;
			brJobs = new BufferedReader(new FileReader(jobLocation));
			brItems = new BufferedReader(new FileReader(itemsLocation));
			brLocation = new BufferedReader(new FileReader(locationLocation));
			String line;
			String line2;
			while((line = brLocation.readLine()) != null) {
				logger.debug("Location = " + line);
				brItems.close();
				brItems = new BufferedReader(new FileReader(itemsLocation));
				String[] locationParts = HelperMethods.split(line, ",", 0);
				while((line2 = brItems.readLine()) != null) {
					logger.debug("Item = " + line2);
					String itemParts[] = HelperMethods.split(line2, ",", 0);
					if(itemParts[0].equals(locationParts[2])) {
						Item item = new Item(itemParts[0], Integer.parseInt(locationParts[0]), Integer.parseInt(locationParts[1]), new BigDecimal(itemParts[1]), new BigDecimal(itemParts[2]));
						items.add(item);
					}
				}
			}
			while((line = brJobs.readLine()) != null) {
				logger.debug("Job = " + line);
				String[] jobParts = HelperMethods.split(line, ",", 0);
				Job job = new Job(jobParts[0]);
				allJobs.add(job);
				for(int i = 1; i < jobParts.length; i+=2) {
					for(Item item: items) {
						if(jobParts[i].equals(item.getName())) {
							job.addPart(new JobPart(item.getName(), item.getX(), item.getY(), Integer.parseInt(jobParts[i+1]), item.getWeight(), item.getReward()));
						}
					}
				}
			}
		}
		catch(FileNotFoundException e) {
			logger.debug("File not found", e);
		}
		catch(IOException e) {
			logger.debug("Data Streams Broke", e);
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
			BigDecimal priority = reward.divide(weight.add(new BigDecimal(numberOfPlaces))).subtract(cancel.probOfCancellation(job));
			job.setPriority(priority);
		}
		Collections.sort(allJobs, (a, b) -> b.getPriority().compareTo(a.getPriority()));
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
	
	public ArrayList<Job> getJobs(){
		return allJobs;
	}

}
