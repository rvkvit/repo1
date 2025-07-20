package pages;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.WebDriverWait;
import supportlibraries.*;
import businesscomponents.CommonFunctions;



public class LT_SSN_Page extends MasterPage{
	
	CommonFunctions CF = new CommonFunctions(scriptHelper);
	public LT_SSN_Page(ScriptHelper scriptHelper)
	{
		super(scriptHelper);	
	}

	/*
	 * Locator details
	 */
	By SSN_TextBox = By.id("ssn");
	By Continue_Button = By.xpath(".//*[@id='continue']");

	WebDriverWait wait = new WebDriverWait(driver, Duration.ofMinutes(1));

	public LT_SSN_Page EnterSSNonLT() throws InterruptedException{
		
		String strUATSSN = dataTable.getData("General_Data", "UAT SSN");
		String strSITSSN = dataTable.getData("General_Data", "SIT SSN");
		String strEnv = properties.getProperty("Environment");
		if (strEnv.contains("SIT")) {
			
			CF.enterValuesInTextBox(SSN_TextBox, "SSN", strSITSSN,"PASS");
			
		}
		
		else {
			
			CF.enterValuesInTextBox(SSN_TextBox, "SSN", strUATSSN,"PASS");
			
		}
		
		CF.clickOnElement(Continue_Button, "Continue", "Button","PASS");

		return new LT_SSN_Page(scriptHelper);
	}




}
