package tests.com.rp25.jobSelectionAndAllocation;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import com.rp25.jobSelectionAndAllocation.JobAllocation;
import com.rp25.jobSelectionAndAllocation.JobSelection;
import com.rp25.tools.Job;

public class JobSelectionAndAllocationTest {

	JobSelection getJobs;
	String jobFilePath = "jobs.csv";
	String itemFilePath = "items.csv";
	String locationFilePath = "locations.csv";
	String cancelLocation = "cancellations.csv";
	String testLocation = jobFilePath;

	@Before
	public void initialiseJobSelection() {
		getJobs = new JobSelection(jobFilePath, itemFilePath, locationFilePath, cancelLocation, testLocation);
	}

	@Test
	public void jobsAreAdded() {
		getJobs = new JobSelection(jobFilePath, itemFilePath, locationFilePath, cancelLocation, testLocation);
		assertNotNull("Jobs are not added", JobAllocation.getNextJob(1));
	}

	@Test
	public void shouldDeleteGivenJob() {

		getJobs = new JobSelection(jobFilePath, itemFilePath, locationFilePath, cancelLocation, testLocation);
		String givenJob = "10000";
		ArrayList<Job> jobs = getJobs.getJobs();
		getJobs.cancelJobByName(givenJob);
		boolean jobFound = false;
		for (int i = 0; i < jobs.size(); i++) {
			if (givenJob == jobs.get(i).getName()) {
				jobFound = true;
			}
		}
		assertFalse("Job was not removed", jobFound);

	}

	@Test
	public void jobsChangeOrder() {
		getJobs = new JobSelection(jobFilePath, itemFilePath, locationFilePath, cancelLocation, testLocation);
		ArrayList<Job> jobs = getJobs.getJobs();
		assertTrue(jobs.size() > 0);
		boolean firstJobIsFirst = false;
		if (jobs.get(0).getName().equals("10000")) {
			firstJobIsFirst = true;
		}
		assertFalse("Jobs haven't changed order", firstJobIsFirst);
	}

	@Test
	public void jobsShouldBeAllocatedToAllRobots() {
		getJobs = new JobSelection(jobFilePath, itemFilePath, locationFilePath, cancelLocation, testLocation);
		boolean jobIsNotAllocated = false;
		if (JobAllocation.getNextJob(1).equals(null)) {
			jobIsNotAllocated = true;
		} else if (JobAllocation.getNextJob(2).equals(null)) {
			jobIsNotAllocated = true;
		} else if (JobAllocation.getNextJob(3).equals(null)) {
			jobIsNotAllocated = true;
		}
		assertFalse("A robot hasn't been allocated any jobs", jobIsNotAllocated);

	}

}
