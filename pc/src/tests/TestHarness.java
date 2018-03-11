package tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({
	RoutePlanningTest.class,
	JobSelectionAndAllocationTest.class,
	RouteExecutionTest.class
})

public class TestHarness {

}
