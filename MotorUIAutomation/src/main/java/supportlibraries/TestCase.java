package supportlibraries;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import com.LT.framework.FrameworkParameters;
import com.LT.framework.Util;
import com.LT.framework.selenium.ResultSummaryManager;
import com.LT.framework.selenium.SeleniumTestParameters;
import org.testng.ITestContext;
import org.testng.SkipException;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;



/**
 * Abstract base class for all the test cases to be automated
 * @author LT TCS
 */
public abstract class TestCase
{

	/**
	 * The {@link SeleniumTestParameters} object to be used to specify the test parameters
	 */
	protected SeleniumTestParameters testParameters;
	/**
	 * The {@link DriverScript} object to be used to execute the required test case
	 */
	protected DriverScript driverScript;

	private ResultSummaryManager resultSummaryManager = new ResultSummaryManager();
	private Date startTime, endTime;


	/**
	 * Function to do the required set-up activities before executing the overall test suite in TestNG
	 * @param testContext The TestNG {@link ITestContext} of the current test suite 
	 * @return 
	 */
	@BeforeSuite
	public void suiteSetup(ITestContext testContext)
	{
		resultSummaryManager.setRelativePath();
		resultSummaryManager.initializeTestBatch(testContext.getSuite().getName());

		int nThreads;
		if (testContext.getSuite().getParallel().equalsIgnoreCase("false")) {
			nThreads = 1;
		} else {
			nThreads = testContext.getCurrentXmlTest().getThreadCount();
		}
		resultSummaryManager.initializeSummaryReport(nThreads); 
		resultSummaryManager.setupErrorLog();
		LocalDateTime now = LocalDateTime.now();
        // Define a custom date-time format
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        // Format the date-time using the custom format
        String formattedDateTime = now.format(formatter);
		updatePropertyValue( "BROWSERSTACK_EXECUTION_TIME", formattedDateTime);
		updatePropertyValue( "APPLICATION", testContext.getSuite().getName());
	}

	/**
	 * Function to do the required set-up activities before executing each test case in TestNG
	 */
	@BeforeMethod
	public void testMethodSetup()
	{
		FrameworkParameters frameworkParameters = FrameworkParameters.getInstance();
		if(frameworkParameters.getStopExecution()) {
			suiteTearDown();

			throw new SkipException("Aborting all subsequent tests!");
		} else {
			startTime = Util.getCurrentTime();

			String currentScenario =
					capitalizeFirstLetter(this.getClass().getPackage().getName().substring(12));
			String currentTestcase = this.getClass().getSimpleName();
			testParameters = new SeleniumTestParameters(currentScenario, currentTestcase);
		}
	}

	private String capitalizeFirstLetter(String myString)
	{
		StringBuilder stringBuilder = new StringBuilder(myString);
		stringBuilder.setCharAt(0, Character.toUpperCase(stringBuilder.charAt(0)));
		return stringBuilder.toString();
	}

	/**
	 * Function to do the required wrap-up activities after executing each test case in TestNG
	 */
	@AfterMethod
	public void testMethodTearDown()
	{
		String testStatus = driverScript.getTestStatus();
		endTime = Util.getCurrentTime();
		String executionTime = Util.getTimeDifference(startTime, endTime);
		resultSummaryManager.updateResultSummary(testParameters.getCurrentScenario(),
				testParameters.getCurrentTestcase(),
				testParameters.getCurrentTestDescription(),
				executionTime, testStatus);
	}

	/**
	 * Function to do the required wrap-up activities after executing the overall test suite in TestNG
	 */
	@AfterSuite
	public void suiteTearDown()
	{
		resultSummaryManager.wrapUp(true);
		//resultSummaryManager.launchResultSummary();  # commenting to test smoke pipeline - report not generated in second stage - DLL 32exe
	}

	// Update time in settings.property file 
	public void updatePropertyValue( String key, String value) {
		String currentDir = System.getProperty("user.dir");
		String os = System.getProperty("os.name");
		System.out.println("Operating System: " + os);
		String filePath;
		// Construct the file path using the current directory and the provided file name
		if(os.toLowerCase().contains("linux")) {
			filePath = currentDir + File.separator + "ExecutionSettings.properties";
			System.out.println("File path at linux " + filePath);
		}
		else {
			filePath = currentDir + File.separator + "ExecutionSettings.properties";
			System.out.println("File path at windows " + filePath);
		}
		// Read the existing properties file
		Map<String, String> propertiesMap = new HashMap<>();
		try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
			String line;
			while ((line = reader.readLine()) != null) {
				if (line.trim().startsWith(key + " ")) {
					// Update the value for the specified key
					line = key + " = " + value;
				}
				propertiesMap.put(line.split("=")[0].trim(), line);
			}
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
		// Write the modified properties back to the file
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
			for (String line : propertiesMap.values()) {
				writer.write(line);
				writer.newLine();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}