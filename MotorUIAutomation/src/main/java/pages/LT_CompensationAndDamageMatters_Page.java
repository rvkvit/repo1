package pages;

import java.time.Duration;


import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.WebDriverWait;


import supportlibraries.*;


import businesscomponents.CommonFunctions;



public class LT_CompensationAndDamageMatters_Page extends MasterPage{
	CommonFunctions CF = new CommonFunctions(scriptHelper);
	public LT_CompensationAndDamageMatters_Page(ScriptHelper scriptHelper)
	{
		super(scriptHelper);	
	}

	/*
	 * Locator details
	 */
	By ApplyForCompensation_Button = By.id("claimOpenBtn");



	WebDriverWait wait = new WebDriverWait(driver, Duration.ofMinutes(1));

	public LT_CompensationAndDamageMatters_Page ClickOnApplyForCompensation() throws InterruptedException{
		CF.clickOnElement(ApplyForCompensation_Button, "Apply for Compensation", "Button", "PASS");

		return new LT_CompensationAndDamageMatters_Page(scriptHelper);
	}

}
