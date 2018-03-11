<<<<<<< HEAD
package com.rp25.tools;

import java.math.BigDecimal;
import java.util.ArrayList;

public class Job {
	private ArrayList<JobPart> parts = new ArrayList<JobPart>();
	private String name;
	private BigDecimal priority = new BigDecimal("0");
	
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
		return parts;
	}
	
	public JobPart getPart(String name) {
		for (JobPart jobPart : parts) {
			String jobName = jobPart.getName();
			if(name.equals(jobName)) {
				return jobPart;
			}
		}
		return null;
	}
	
	public void setPriority(BigDecimal newPriority) {
		priority = newPriority;
	}
	
	public BigDecimal getPriority() {
		return priority;
	}
}
=======
package com.rp25.tools;

import java.math.BigDecimal;
import java.util.ArrayList;

public class Job {
	private ArrayList<JobPart> parts = new ArrayList<JobPart>();
	private String name;
	private BigDecimal priority = new BigDecimal("0");
	
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
		return parts;
	}
	
	public JobPart getPart(String name) {
		for (JobPart jobPart : parts) {
			String jobName = jobPart.getName();
			if(name.equals(jobName)) {
				return jobPart;
			}
		}
		return null;
	}
	
	public void setPriority(BigDecimal newPriority) {
		priority = newPriority;
	}
	
	public BigDecimal getPriority() {
		return priority;
	}
}

>>>>>>> 40b6f248a414f59a982df65f4144dffa324bdeb7
