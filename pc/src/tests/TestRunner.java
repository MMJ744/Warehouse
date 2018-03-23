package tests;

import org.apache.log4j.Logger;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

public class TestRunner {
	
	private final static Logger logger = Logger.getLogger(TestRunner.class);
	
	public static void main(String[] args) {
		Result result = JUnitCore.runClasses(TestHarness.class);
		
		System.out.println((result.getRunCount() - result.getFailureCount()) + "/" + result.getRunCount() + " Tests Passed");
		
		for (Failure failure : result.getFailures()) {
			logger.info(failure.toString());
			
		}
	}
}
