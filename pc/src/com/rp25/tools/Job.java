package com.rp25.tools;

import java.math.BigDecimal;
import java.util.ArrayList;
import org.apache.log4j.Logger;

public class Job {
	private ArrayList<JobPart> parts = new ArrayList<JobPart>();
	private String name;
	private BigDecimal priority = new BigDecimal("0");
	private final static Logger logger = Logger.getLogger(Job.class);
	
	public Job(String name) {
		this.name = name;
	}
	
	public void addPart(JobPart part) {
		parts.add(part);
	}
	
	public String getName() {
		return name;
	}
	
	public ArrayList<JobPart> getParts(){
		for(int i = 0; i < parts.size(); i++) {
			logger.debug("Part " + i + ": " + parts.get(i).getName());
		}
		return parts;
	}
	
	public JobPart getPart(String name) {
		for (JobPart jobPart : parts) {
			String jobName = jobPart.getName();
			logger.debug("Job Part Recieved: " + jobName);

			if(name.equals(jobName)) {
				logger.debug("Job Part Returned: " + jobName);
				return jobPart;
			}
		}
		return null;
	}
	
	public void setPriority(BigDecimal newPriority) {
		priority = newPriority;
		logger.debug("Priority set to " + getPriority());
	}
	
	public BigDecimal getPriority() {
		return priority;
	}
}

