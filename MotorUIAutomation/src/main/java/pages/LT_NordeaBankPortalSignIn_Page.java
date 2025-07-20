package pages;

import java.time.Duration;

import org.openqa.selenium.By;

import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import supportlibraries.*;

import businesscomponents.CommonFunctions;



public class LT_NordeaBankPortalSignIn_Page extends MasterPage{
	CommonFunctions CF = new CommonFunctions(scriptHelper);
	public LT_NordeaBankPortalSignIn_Page(ScriptHelper scriptHelper)
	{
		super(scriptHelper);	
	}

	/*
	 * Locator details
	 */
	By NordeaUserID_TextBox = By.id("demo-mock-user-id");
	By OK_Button = By.id("auth-button");

	WebDriverWait wait = new WebDriverWait(driver, Duration.ofMinutes(1));

	public LT_NordeaBankPortalSignIn_Page LoginToNordeaPortal() throws InterruptedException{
		
		String strUserID = dataTable.getData("General_Data", "UserID");
		
		CF.enterValuesInTextBox(NordeaUserID_TextBox, "User Id", strUserID, "PASS");
		Assert.assertEquals(CF.getAttribute(OK_Button, "aria-disabled"), "false");
		CF.clickOnButton(OK_Button, "OK", "PASS");
		
		return new LT_NordeaBankPortalSignIn_Page(scriptHelper);
	}




}
