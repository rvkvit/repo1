package testscripts.scenario1;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.LT.framework.IterationOptions;
import com.LT.framework.selenium.Browser;

import supportlibraries.DriverScript;
import supportlibraries.TestCase;



/**
 * Test for selection of Release and Bank portal
 * @author LT
 */
public class CollisionWithAnotherVehicle_YES_TU extends TestCase
{
	@Test
	@Parameters("browser")
	public void runTC1(String browser) throws InvalidFormatException
	{
		testParameters.setCurrentTestDescription("Test MFNOL (TU) - Cause : Collision(with another vehicle); User : PH");
		testParameters.setIterationMode(IterationOptions.RunAllIterations);
		if(browser.equalsIgnoreCase("chrome"))
			testParameters.setBrowser(Browser.Chrome);
		else if (browser.equalsIgnoreCase("firefox"))
			testParameters.setBrowser(Browser.Firefox);
		else if(browser.equalsIgnoreCase("internetExplorer"))
			testParameters.setBrowser(Browser.InternetExplorer);
		else if(browser.equalsIgnoreCase("edge"))
			testParameters.setBrowser(Browser.Edge);
		driverScript = new DriverScript(testParameters);
		driverScript.driveTestExecution();
	}
}