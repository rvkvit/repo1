package supportlibraries;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import com.LT.framework.*;
import com.LT.framework.ReportThemeFactory.Theme;
import com.LT.framework.selenium.*;

import org.apache.commons.io.FileUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.testng.Assert;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.lang.reflect.*;
import java.net.MalformedURLException;
import java.time.Duration;


import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;


/**
 * Driver script class which encapsulates the core logic of the framework
 * @author LT TCS
 */
public class DriverScript
{
	private List<String> businessFlowData;
	private int currentIteration, currentSubIteration;
	private Date startTime, endTime;

	private CraftDataTable dataTable;
	private ReportSettings reportSettings;
	private SeleniumReport report;
	private WebDriver driver;
	private ScriptHelper scriptHelper;

	private Properties properties;
	private ExecutionMode executionMode;
	private final FrameworkParameters frameworkParameters =
			FrameworkParameters.getInstance();
	private Boolean testExecutedInUnitTestFramework = true;
	private Boolean linkScreenshotsToTestLog = true;
	private String testStatus;

	private final SeleniumTestParameters testParameters;
	private String reportPath;

	/**
	 * Function to indicate whether the test is executed in JUnit/TestNG or not
	 * @param testExecutedInUnitTestFramework Boolean variable indicating whether the test is executed in JUnit/TestNG
	 */
	public void setTestExecutedInUnitTestFramework(Boolean testExecutedInUnitTestFramework)
	{
		this.testExecutedInUnitTestFramework = testExecutedInUnitTestFramework;
	}

	/**
	 * Function to configure the linking of screenshots to the corresponding test log
	 * @param linkScreenshotsToTestLog Boolean variable indicating whether screenshots should be linked to the corresponding test log
	 */
	public void setLinkScreenshotsToTestLog(Boolean linkScreenshotsToTestLog)
	{
		this.linkScreenshotsToTestLog = linkScreenshotsToTestLog;
	}

	/**
	 * Function to get the status of the test case executed
	 * @return The test status
	 */
	public String getTestStatus()
	{
		return testStatus;
	}


	/**
	 * DriverScript constructor
	 * @param testParameters A {@link SeleniumTestParameters} object
	 */
	public DriverScript(SeleniumTestParameters testParameters)
	{
		this.testParameters = testParameters;
	}

	/**
	 * Function to execute the given test case
	 * @throws InvalidFormatException 
	 */
	public void driveTestExecution()
	{
		startUp();

		initializeTestIterations();
		initializeWebDriver();
		initializeTestReport();
		initializeDatatable();
		initializeTestScript();
		try {
			driver.manage().timeouts().scriptTimeout(Duration.ofMinutes(2));
		} catch(Exception e) {
			System.out.println(e);
		}
		executeTestIterations();

		quitWebDriver();
		wrapUp();
	}

	private void startUp()
	{

		startTime = Util.getCurrentTime();

		properties = Settings.getInstance();

		setDefaultTestParameters();
		String os = System.getProperty("os.name");
		if(os.toLowerCase().contains("linux")) {
			try {
				System.out.println("Entered into chrome driver kill process on Linux");
				// Execute the pkill command to kill all ChromeDriver and Chrome browser processes
				Process process = Runtime.getRuntime().exec("pkill -f chromedriver");
				process.waitFor();
				process = Runtime.getRuntime().exec("pkill -f chrome");
				process.waitFor();

				// Print a message indicating that the processes have been killed
				System.out.println("ChromeDriver and Chrome browser processes killed successfully.");
			} catch (IOException | InterruptedException e) {
				System.out.println("exception seen while killing chrome driver and chrome browser processes");
				e.printStackTrace();
			}
		}
	}

	private void setDefaultTestParameters()
	{
		if (testParameters.getIterationMode() == null) {
			testParameters.setIterationMode(IterationOptions.RunAllIterations);
		}

		if(System.getProperty("Browser") != null) {
			testParameters.setBrowser(Browser.valueOf(System.getProperty("Browser")));
		} else {
			if (testParameters.getBrowser() == null) {
				testParameters.setBrowser(Browser.valueOf(properties.getProperty("DefaultBrowser")));
			}
		}

		if(System.getProperty("BrowserVersion") !=null) {
			testParameters.setBrowserVersion(System.getProperty("BrowserVersion"));
		}

		if(System.getProperty("Platform") != null) {
			testParameters.setPlatform(Platform.valueOf(System.getProperty("Platform")));
		} else {
			if (testParameters.getPlatform() == null) {
				testParameters.setPlatform(Platform.valueOf(properties.getProperty("DefaultPlatform")));
			}
		}
	}
	// rovin changes 3
	private void initializeTestIterations()
	{
		switch(testParameters.getIterationMode()) {
		case RunAllIterations:
			String datatablePath = frameworkParameters.getRelativePath() +
			Util.getFileSeparator() + "Datatables";
			ExcelDataAccess testDataAccess =
					new ExcelDataAccess(datatablePath, testParameters.getCurrentScenario());
			testDataAccess.setDatasheetName(properties.getProperty("DefaultDataSheet"));

			int startRowNum = testDataAccess.getRowNum(testParameters.getCurrentTestcase(), 0);
			int nTestcaseRows = testDataAccess.getRowCount(testParameters.getCurrentTestcase(), 0, startRowNum);
			int nSubIterations = testDataAccess.getRowCount("1", 1, startRowNum);	// Assumption: Every test case will have at least one iteration
			int nIterations = nTestcaseRows / nSubIterations;
			testParameters.setEndIteration(nIterations);

			currentIteration = 1;
			break;

		case RunOneIterationOnly:
			currentIteration = 1;
			break;

		case RunRangeOfIterations:
			if(testParameters.getStartIteration() > testParameters.getEndIteration()) {
				throw new FrameworkException("Error","StartIteration cannot be greater than EndIteration!");
			}
			currentIteration = testParameters.getStartIteration();
			break;

		default:
			throw new FrameworkException("Unhandled Iteration Mode!");
		}
	}

	private void initializeWebDriver() 
	{
		executionMode = ExecutionMode.valueOf(properties.getProperty("ExecutionMode"));
		System.setProperty("webdriver.gecko.driver", "C:\\Users\\Test\\Desktop\\driver\\geckodriver.exe");
		switch(executionMode) {
		case Local:
			driver = WebDriverFactory.getDriver(testParameters.getBrowser());
			break;

		case Remote:
			driver = WebDriverFactory.getDriver(testParameters.getBrowser(),
					properties.getProperty("RemoteUrl"));
			break;

		case Grid:
			driver = WebDriverFactory.getDriver(testParameters.getBrowser(),
					testParameters.getBrowserVersion(),
					testParameters.getPlatform(),
					properties.getProperty("RemoteUrl"));
			break;
			/*
			 * code to enable browser stack execution
			 */
		case BrowserStack:

			try {
				driver = WebDriverFactory.getDriver();

			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				report.updateTestLog("BrowserStack URL:", "BrowserStack URL is malformed", Status.FAIL);
			}
			break;

		default:
			throw new FrameworkException("Unhandled Execution Mode!");
		}
		
		//Platform platform = ((RemoteWebDriver) driver).getCapabilities().getPlatformName();
		String platformName = ((RemoteWebDriver) driver).getCapabilities().getCapability("platformName").toString().toLowerCase();
		System.out.println("Platform is : " + platformName);
		if (!"android".equals(platformName)) {
			driver.manage().window().maximize();
		}
	}

	private void initializeTestReport()
	{
		initializeReportSettings();
		ReportTheme reportTheme =
				ReportThemeFactory.getReportsTheme(Theme.valueOf(properties.getProperty("ReportsTheme")));

		report = new SeleniumReport(reportSettings, reportTheme);

		report.initialize();
		report.setDriver(driver);
		report.initializeTestLog();
		createTestLogHeader();
	}

	private void initializeReportSettings()
	{
		if(System.getProperty("ReportPath") != null) {
			reportPath = System.getProperty("ReportPath");
		} else {
			reportPath = TimeStamp.getInstance();
		}

		reportSettings = new ReportSettings(reportPath,
				testParameters.getCurrentScenario() +
				"_" + testParameters.getCurrentTestcase());

		reportSettings.setDateFormatString(properties.getProperty("DateFormatString"));
		reportSettings.setLogLevel(Integer.parseInt(properties.getProperty("LogLevel")));
		reportSettings.setProjectName(properties.getProperty("ProjectName"));
		reportSettings.generateExcelReports =
				Boolean.parseBoolean(properties.getProperty("ExcelReport"));
		reportSettings.generateHtmlReports =
				Boolean.parseBoolean(properties.getProperty("HtmlReport"));
		reportSettings.takeScreenshotFailedStep =
				Boolean.parseBoolean(properties.getProperty("TakeScreenshotFailedStep"));
		reportSettings.takeScreenshotPassedStep =
				Boolean.parseBoolean(properties.getProperty("TakeScreenshotPassedStep"));
		reportSettings.consolidateScreenshotsInWordDoc = 
				Boolean.parseBoolean(properties.getProperty("ConsolidateScreenshotsInWordDoc"));
		if (testParameters.getBrowser().equals(Browser.HtmlUnit)) {
			// Screenshots not supported in headless mode
			reportSettings.linkScreenshotsToTestLog = false;
		} else {
			reportSettings.linkScreenshotsToTestLog = this.linkScreenshotsToTestLog;
		}
	}

	private void createTestLogHeader()
	{
		report.addTestLogHeading(reportSettings.getProjectName() +
				" - " + reportSettings.getReportName() +
				" Automation Execution Results");
		report.addTestLogSubHeading("Date & Time",
				": " + Util.getCurrentFormattedTime(properties.getProperty("DateFormatString")),
				"Iteration Mode", ": " + testParameters.getIterationMode());
		report.addTestLogSubHeading("Start Iteration", ": " + testParameters.getStartIteration(),
				"End Iteration", ": " + testParameters.getEndIteration());

		switch(executionMode) {
		case Local:
			report.addTestLogSubHeading("Browser", ": " + testParameters.getBrowser(),
					"Executed on", ": " + "Local Machine");
			break;

		case Remote:
			report.addTestLogSubHeading("Browser", ": " + testParameters.getBrowser(),
					"Executed on", ": " + properties.getProperty("RemoteUrl"));
			break;

		case Grid:
			String browserVersion = testParameters.getBrowserVersion();
			if (browserVersion == null) {
				browserVersion = "Not specified";
			}
			report.addTestLogSubHeading("Browser", ": " + testParameters.getBrowser(),
					"Version", ": " + browserVersion);
			report.addTestLogSubHeading("Platform", ": " + testParameters.getPlatform().toString(),
					"Executed on", ": " + "Grid @ " + properties.getProperty("RemoteUrl"));
			break;

		case BrowserStack:
			report.addTestLogSubHeading("Browser", ": " + testParameters.getBrowser(),
					"Executed on", ": " + properties.getProperty("RemoteUrl"));
			break;

		default:
			throw new FrameworkException("Unhandled Execution Mode!");
		}

		report.addTestLogTableHeadings();
	}

	private void initializeDatatable()
	{
		String datatablePath = frameworkParameters.getRelativePath() +
				Util.getFileSeparator() + "Datatables";

		String runTimeDatatablePath;
		Boolean includeTestDataInReport =
				Boolean.parseBoolean(properties.getProperty("IncludeTestDataInReport"));
		if (includeTestDataInReport) {
			runTimeDatatablePath = datatablePath;
			//reportPath + Util.getFileSeparator() + "Datatables";

			File runTimeDatatable = new File(runTimeDatatablePath + Util.getFileSeparator() +
					testParameters.getCurrentScenario() + ".xlsx");
			if (!runTimeDatatable.exists()) {
				File datatable = new File(datatablePath + Util.getFileSeparator() +
						testParameters.getCurrentScenario() + ".xlsx");

				try {
					FileUtils.copyFile(datatable, runTimeDatatable);
				} catch (IOException e) {
					e.printStackTrace();
					throw new FrameworkException("Error in creating run-time datatable: Copying the datatable failed...");
				}
			}

			File runTimeCommonDatatable = new File(runTimeDatatablePath +
					Util.getFileSeparator() +
					"Common Testdata.xlsx");
			if (!runTimeCommonDatatable.exists()) {
				File commonDatatable = new File(datatablePath +
						Util.getFileSeparator() + "Common Testdata.xlsx");

				try {
					FileUtils.copyFile(commonDatatable, runTimeCommonDatatable);
				} catch (IOException e) {
					e.printStackTrace();
					throw new FrameworkException("Error in creating run-time datatable: Copying the common datatable failed...");
				}
			}
		} else {
			runTimeDatatablePath = datatablePath;
		}

		dataTable = new CraftDataTable(runTimeDatatablePath, testParameters.getCurrentScenario());
		dataTable.setDataReferenceIdentifier(properties.getProperty("DataReferenceIdentifier"));
	}

	private void initializeTestScript()
	{
		scriptHelper = new ScriptHelper(dataTable, report, driver);

		businessFlowData = getBusinessFlow();
	}

	private List<String> getBusinessFlow()
	{
		ExcelDataAccess businessFlowAccess =
				new ExcelDataAccess(frameworkParameters.getRelativePath() +
						Util.getFileSeparator() + "Datatables",
						testParameters.getCurrentScenario());
		businessFlowAccess.setDatasheetName("Business_Flow");

		int rowNum = businessFlowAccess.getRowNum(testParameters.getCurrentTestcase(), 0);
		if (rowNum == -1) {
			throw new FrameworkException("The test case \"" + testParameters.getCurrentTestcase() + "\" is not found in the Business Flow sheet!");
		}

		String dataValue;
		List<String> businessFlowData = new ArrayList<String>();
		int currentColumnNum = 1;
		while (true) {
			dataValue = businessFlowAccess.getValue(rowNum, currentColumnNum);
			if (dataValue.equals("")) {
				break;
			}
			businessFlowData.add(dataValue);
			currentColumnNum++;
		}

		if (businessFlowData.isEmpty()) {
			throw new FrameworkException("No business flow found against the test case \"" + testParameters.getCurrentTestcase() + "\"");
		}

		return businessFlowData;
	}

	private void executeTestIterations()
	{
		while(currentIteration <= testParameters.getEndIteration()) {
			report.addTestLogSection("Iteration: " + Integer.toString(currentIteration));

			// Evaluate each test iteration for any errors
			try {
				executeTestcase(businessFlowData);
			}
			catch (WebDriverException we) {
				// Check if the exception message matches the specific error
				if (we.getMessage().contains("unknown error: session deleted because of page crash")) {
					// Handle the page crash error here
					System.out.println("Page crash error occurred. Handling it...");
					exceptionHandler((Exception)we.getCause(), "Error");
					// Add your recovery or retry mechanism here
				} else {
					// Handle other WebDriverException cases here
					System.out.println("Other WebDriverException occurred: " + we.getMessage());
					exceptionHandler((Exception)we.getCause(), "Error");
				}
			}
			catch (FrameworkException fx) {
				exceptionHandler(fx, fx.errorName);
			} catch (InvocationTargetException ix) {
				exceptionHandler((Exception)ix.getCause(), "Error");
			} catch (Exception ex) {
				exceptionHandler(ex, "Error");
			}

			currentIteration++;
		}
	}

	private void executeTestcase(List<String> businessFlowData)
			throws IllegalAccessException, InvocationTargetException,
			ClassNotFoundException, InstantiationException
	{
		HashMap<String, Integer> keywordDirectory = new HashMap<String, Integer>();

		for (int currentKeywordNum = 0; currentKeywordNum < businessFlowData.size(); currentKeywordNum++) {
			String[] currentFlowData = businessFlowData.get(currentKeywordNum).split(",");
			String currentKeyword = currentFlowData[0]; 

			int nKeywordIterations;
			if(currentFlowData.length > 1) {
				nKeywordIterations = Integer.parseInt(currentFlowData[1]);
			} else {
				nKeywordIterations = 1;
			}

			for (int currentKeywordIteration = 0; currentKeywordIteration < nKeywordIterations; currentKeywordIteration++) {
				if(keywordDirectory.containsKey(currentKeyword)) {
					keywordDirectory.put(currentKeyword, keywordDirectory.get(currentKeyword) + 1);
				} else {
					keywordDirectory.put(currentKeyword, 1);
				}
				currentSubIteration = keywordDirectory.get(currentKeyword);

				dataTable.setCurrentRow(testParameters.getCurrentTestcase(), currentIteration, currentSubIteration);

				if (currentSubIteration > 1) {
					report.addTestLogSubSection(currentKeyword + " (Sub-Iteration: " + currentSubIteration + ")");
				} else {
					report.addTestLogSubSection(currentKeyword);
				}

				invokeBusinessComponent(currentKeyword);
			}
		}
	}

	private void invokeBusinessComponent(String currentKeyword)
			throws IllegalAccessException, InvocationTargetException,
			ClassNotFoundException, InstantiationException
	{
		Boolean isMethodFound = false;
		final String CLASS_FILE_EXTENSION = ".java";  //LT changes from .class to .java
		String os = System.getProperty("os.name");
		File[] packageDirectories = null;
		if(os.toLowerCase().contains("win")) {

			packageDirectories = new File[]{
					new File(frameworkParameters.getRelativePath() +
							Util.getFileSeparator() + "src\\main\\java\\" + "businesscomponents"),
					new File(frameworkParameters.getRelativePath() +
							Util.getFileSeparator() + "src\\main\\java\\" + "componentgroups") };
		}
		else if(os.toLowerCase().contains("linux")){

			packageDirectories = new File[]{
					new File(frameworkParameters.getRelativePath() +
							Util.getFileSeparator() + "src/main/java/" + "businesscomponents"),
					new File(frameworkParameters.getRelativePath() +
							Util.getFileSeparator() + "src/main/java/" + "componentgroups") };
		}
		else {
			packageDirectories = new File[]{
					new File(frameworkParameters.getRelativePath() +
							Util.getFileSeparator() + "src\\main\\java\\" + "businesscomponents"),
					new File(frameworkParameters.getRelativePath() +
							Util.getFileSeparator() + "src\\main\\java\\" + "componentgroups") };

		}
		/*
		 * new File(frameworkParameters.getRelativePath() + Util.getFileSeparator() +
		 * "bin\\" + "businesscomponents"), new
		 * File(frameworkParameters.getRelativePath() + Util.getFileSeparator() +
		 * "bin\\" + "componentgroups") };
		 */

		for (File packageDirectory : packageDirectories)
		{
			File[] packageFiles = packageDirectory.listFiles();
			String packageName = packageDirectory.getName();

			for (int i = 0; i < packageFiles.length; i++) {
				File packageFile = packageFiles[i];
				String fileName = packageFile.getName(); // FunctionalComponents.class

				// We only want the .class files
				if (fileName.endsWith(CLASS_FILE_EXTENSION)) {
					// Remove the .class extension to get the class name
					String className = fileName.substring(0, fileName.length() - CLASS_FILE_EXTENSION.length());

					Class<?> reusableComponents = Class.forName(packageName+"." + className);
					Method executeComponent;

					try {
						// Convert the first letter of the method to lowercase (in line with java naming conventions)
						currentKeyword = currentKeyword.substring(0, 1).toLowerCase() + currentKeyword.substring(1);
						executeComponent = reusableComponents.getMethod(currentKeyword, (Class<?>[]) null);
					} catch(NoSuchMethodException ex) {
						// If the method is not found in this class, search the next class
						continue;
					}

					isMethodFound = true;

					Constructor<?> ctor = reusableComponents.getDeclaredConstructors()[0];
					Object businessComponent = ctor.newInstance(scriptHelper);

					executeComponent.invoke(businessComponent, (Object[]) null);

					break;
				} 
			}
		}

		if(!isMethodFound) {
			throw new FrameworkException("Keyword " + currentKeyword + 
					" not found within any class " +
					"inside the businesscomponents package");
		}
	}

	private void exceptionHandler(Exception ex, String exceptionName)
	{
		// Error reporting
		String exceptionDescription = ex.getMessage();
		if(exceptionDescription == null) {
			exceptionDescription = ex.toString();
		}

		if(ex.getCause() != null) {
			report.updateTestLog(exceptionName, exceptionDescription + " <b>Caused by: </b>" +
					ex.getCause(), Status.FAIL);
		} else {
			report.updateTestLog(exceptionName, exceptionDescription, Status.FAIL);
		}
		ex.printStackTrace();

		// Error response
		if (frameworkParameters.getStopExecution()) {
			report.updateTestLog("CRAFT Info",
					"Test execution terminated by user! All subsequent tests aborted...",
					Status.DONE);
			currentIteration = testParameters.getEndIteration();
		} else {
			OnError onError = OnError.valueOf(properties.getProperty("OnError"));
			switch(onError) {
			// Stop option is not relevant when run 
			case NextIteration:
				report.updateTestLog("CRAFT Info",
						"Test case iteration terminated by user! Proceeding to next iteration (if applicable)...",
						Status.DONE);
				break;

			case NextTestCase:
				report.updateTestLog("CRAFT Info",
						"Test case terminated by user! Proceeding to next test case (if applicable)...",
						Status.DONE);
				currentIteration = testParameters.getEndIteration();
				break;

			case Stop:
				frameworkParameters.setStopExecution(true);
				report.updateTestLog("CRAFT Info",
						"Test execution terminated by user! All subsequent tests aborted...",
						Status.DONE);
				currentIteration = testParameters.getEndIteration();
				break;

			default:
				throw new FrameworkException("Unhandled OnError option!");
			}
		}
	}

	private void quitWebDriver()
	{
		driver.quit();
	}

	private void wrapUp()  
	{
		endTime = Util.getCurrentTime();
		closeTestReport();

		testStatus = report.getTestStatus();
		setBSTestStatus();
		String Jira_TestCase_ID = dataTable.getData("General_Data", "Jira_TestCase_ID");
		setJiraStatus(Jira_TestCase_ID);// DIGI-27575 DIGI-31365
		
		if(testExecutedInUnitTestFramework && testStatus.equalsIgnoreCase("Failed")) {
			Assert.fail(report.getFailureDescription());
		}           
	}

	private void closeTestReport()  
	{
		String executionTime = Util.getTimeDifference(startTime, endTime);
		report.addTestLogFooter(executionTime);
		// marking it ROVIN
		if (reportSettings.consolidateScreenshotsInWordDoc) {
			report.consolidateScreenshotsInWordDoc();
		}
	}
	/**
	 * This method will set the test status for the corresponding JIRA test case
	 * 
	 * @author Rovin
	 **/


	public void setJiraStatus(String issueKey) {
		String ISSUE_URL = properties.getProperty("ISSUE_URL").trim();
		String EXECUTION_URL = properties.getProperty("EXECUTION_URL").trim();
		String EXECUTION_CANONICAL_PATH = properties.getProperty("EXECUTION_CANONICAL_PATH").trim();
		String EXECUTE_URL = properties.getProperty("EXECUTE_URL").trim();
		String EXECUTE_CANONICAL_PATH = properties.getProperty("EXECUTE_CANONICAL_PATH").trim();

		ZephyrUtils zephyrUtils = new ZephyrUtils();

		int projectId = Integer.parseInt(properties.getProperty("PROJECT_ID").trim()); 
		int issueId =  zephyrUtils.getSessionId(ISSUE_URL, issueKey);
		String cycleId = properties.getProperty("CYCLE_ID").trim(); 

		String folderId = dataTable.getData("General_Data", "Folder ID").trim();
		//String folderId =  properties.getProperty("FOLDER_ID").trim(); 
		String executionId = zephyrUtils.getJIRAExecutionId(EXECUTION_URL,
				EXECUTION_CANONICAL_PATH, projectId, cycleId, folderId, issueId);
		int execution_Status;
		if (executionId != null) {
			if(report.getTestStatus().contains("Passed")) {
				execution_Status = 1;
			}else {
				execution_Status = 2; 
			}
			int status = execution_Status;
			zephyrUtils.updateJIRAExecutionStatus(EXECUTE_URL,
					EXECUTE_CANONICAL_PATH, projectId, cycleId, folderId, issueId,
					executionId, status);
		} else {
			System.out.println("Execution id does not exists");
		}
	}

	/**
	 * This method will set the test status in BS
	 * 
	 * @author ROVIN
	 **/
	public void setBSTestStatus() {
		String BSUsername = properties.getProperty("BROWSERSTACK_USERNAME").trim(); 
		String BSAccessKey = properties.getProperty("BROWSERSTACK_ACCESS_KEY").trim(); 

		// String testReason;
		String sessionID = getBrowserStackSessioIDFromExecutionSettingsFile("BROWSERSTACK_SESSION_ID");

//		if (report.getTestStatus().contains("Passed")) {
//			testStatus = "Passed";
//			testReason = "Test Completed Successfully";
//			// testReason = ""+strScenarioName+"";
//		} else {
//			testStatus = "Failed";
//			testReason = "Test Failed";
//		}


//		try {
//			String cmdGetDocId = "curl -u \"" + BSUsername + ":" + BSAccessKey + "\""
//					+ " -X PUT -H \"Content-Type: application/json\"  -d \"{\\\"status\\\":\\\"" + testStatus
//					+ "\\\", \\\"reason\\\":\\\"" + testReason
//					+ "\\\"}\"  https://api.browserstack.com/automate/sessions/" + sessionID + ".json";
//			Process process = Runtime.getRuntime().exec(cmdGetDocId);
//		} catch (IOException e) {
//			e.printStackTrace();
//		}

		try {
			HttpClient client = HttpClients.createDefault();
			HttpPut httpPut = new HttpPut("https://api.browserstack.com/automate/sessions/" + sessionID + ".json");

			// Set authentication credentials
			httpPut.setHeader("Authorization", "Basic " + encodeCredentials(BSUsername, BSAccessKey));

			// Set JSON payload for updating test status
			StringEntity entity = new StringEntity("{\"status\": \"" + report.getTestStatus() + "\"}");
			entity.setContentType("application/json");
			httpPut.setEntity(entity);

			// Execute HTTP request
			HttpResponse response = client.execute(httpPut);
			System.out.println("Test status updated successfully. Response code: " + response.getStatusLine().getStatusCode());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static String encodeCredentials(String username, String accessKey) {
		String credentials = username + ":" + accessKey;
		return java.util.Base64.getEncoder().encodeToString(credentials.getBytes());
	}

	public static String getBrowserStackSessioIDFromExecutionSettingsFile( String key) {
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
		Properties properties = new Properties();
		FileInputStream fileInputStream = null;
		String value= null;
		try {
			// Provide the path to your properties file
			fileInputStream = new FileInputStream(filePath);
			properties.load(fileInputStream);

			// Get the value of a specific key
			value = properties.getProperty(key);
			System.out.println("Value of 'yourKey': " + value);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (fileInputStream != null) {
				try {
					fileInputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return value;
	}
}