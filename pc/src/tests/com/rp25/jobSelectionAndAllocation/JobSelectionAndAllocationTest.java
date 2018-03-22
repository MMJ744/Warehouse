package tests.com.rp25.jobSelectionAndAllocation;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import com.rp25.jobSelectionAndAllocation.JobAllocation;
import com.rp25.jobSelectionAndAllocation.JobSelection;
import com.rp25.tools.Job;

public class JobSelectionAndAllocationTest {
	
	JobSelection getJobs;
	
	@Before
	public void initialiseJobSelection(String jobFilePath, String itemFilePath, String locationFilePath, String cancelLocation, String testLocation) {
		getJobs = new JobSelection(jobFilePath, itemFilePath, locationFilePath, cancelLocation, testLocation);
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
