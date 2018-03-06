package tools;

import java.util.ArrayList;

public class Job {
	private ArrayList<JobPart> parts = new ArrayList<JobPart>();
	
	public void addPart(JobPart part) {
		parts.add(part);
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
}

