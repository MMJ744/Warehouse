package com.rp25.routePlanning;

import java.math.BigDecimal;
import java.util.ArrayList;

import com.rp25.tools.*;

public class RoutePlan {
	//job weights per item stored as BigDecimal
	

	public static void main(String[] args) {
		while(true) {
			Job job = getJob(); // will get when Katie next pushes
			ArrayList<JobPart> hold = new ArrayList<JobPart>();
			hold = job.getParts();
			JobPart[] jobPart = hold.toArray(new JobPart[hold.size()]);
			
		}

	}
	
	private BigDecimal calcOverallWeight (JobPart[] jobs) {
		return null;
	}
	
	private void generateWeightArray(JobPart[] jobs) {
		JobPart[][] weightedParts = new JobPart[jobs.length][2];
		for (int i = 0; i < jobs.length; i++) {
			weightedParts[i][0] = jobs[i];
			weightedParts[i][1] = calcWeight(jobs[i]);
		}
	}
	
	

}
