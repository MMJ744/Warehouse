package tests;

import jobSelectionAndAllocation.JobSelection;
import jobSelectionAndAllocation.JobAllocation;
import tools.Job;

import static org.junit.Assert.*;

import java.awt.Point;
import java.util.ArrayList;
import org.apache.log4j.Logger;
import org.junit.*;

public class JobSelectionAndAllocationTest {
	
	JobSelection getJobs;
	
	@Before
	public void initialiseJobSelection(String jobFilePath, String itemFilePath, String locationFilePath) {
		getJobs = new JobSelection(jobFilePath, itemFilePath, locationFilePath);
	}
	
	@Test
	public void jobsAreAdded() {
		assertNotNull("Jobs are not added", JobAllocation.getNextJob(0));
	}
	
	@Test
	public void shouldDeleteGivenJob(Job givenJob) {
	
		ArrayList<Job> jobs = getJobs.getJobs();
		getJobs.cancelJobByJob(givenJob);
		boolean jobFound = false;
		for(int i = 0; i < jobs.size(); i++) {
			if(givenJob == jobs.get(i)) {
				jobFound = true;
			}
		}
		assertFalse("Job was not removed", jobFound);
		
	}

}
