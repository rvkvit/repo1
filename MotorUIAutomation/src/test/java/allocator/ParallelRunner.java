package allocator;

import java.util.Date;

import supportlibraries.DriverScript;
import com.LT.framework.selenium.*;

import com.LT.framework.FrameworkParameters;
import com.LT.framework.Util;


public class ParallelRunner implements Runnable
{
	private final SeleniumTestParameters testParameters;
	private final ResultSummaryManager resultSummaryManager;
	
	
	
	public ParallelRunner(SeleniumTestParameters testParameters, ResultSummaryManager resultSummaryManager)
	{
		super();
		
		this.testParameters = testParameters;
		this.resultSummaryManager = resultSummaryManager;
	}
	
	@Override
	public void run()
	{
		Date startTime = Util.getCurrentTime();
		String testStatus;
		
		
		testStatus = invokeTestScript();
				
		/*} catch (InvalidFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		Date endTime = Util.getCurrentTime();
		
		String executionTime = Util.getTimeDifference(startTime, endTime);
		resultSummaryManager.updateResultSummary(testParameters.getCurrentScenario(),
									testParameters.getCurrentTestcase(),
									testParameters.getCurrentTestDescription(),
									executionTime, testStatus);
	}
	
	private String invokeTestScript()  
	{
		String testStatus;
		FrameworkParameters frameworkParameters = FrameworkParameters.getInstance();
		
		if(frameworkParameters.getStopExecution()) {
			testStatus = "Aborted";
		} else {
			DriverScript driverScript = new DriverScript(this.testParameters);
			driverScript.setTestExecutedInUnitTestFramework(false);
		
				driverScript.driveTestExecution();
			
				testStatus = driverScript.getTestStatus();
		}
		
		return testStatus;
	}
}
