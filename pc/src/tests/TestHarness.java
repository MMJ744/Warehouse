package tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import tests.com.rp25.jobSelectionAndAllocation.JobSelectionAndAllocationTest;
import tests.com.rp25.routeExecution.RouteExecutionTest;
import tests.com.rp25.routePlanning.RoutePlanningTest;

@RunWith(Suite.class)
@SuiteClasses({
	RoutePlanningTest.class,
	JobSelectionAndAllocationTest.class,
	RouteExecutionTest.class
})

public class TestHarness {

}
