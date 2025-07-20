package pages;

import java.time.Duration;


import org.openqa.selenium.By;

import org.openqa.selenium.support.ui.WebDriverWait;
import supportlibraries.*;


import businesscomponents.CommonFunctions;



public class LT_BankPortal_Page extends MasterPage{
	CommonFunctions CF = new CommonFunctions(scriptHelper);
	public LT_BankPortal_Page(ScriptHelper scriptHelper)
	{
		super(scriptHelper);	
	}

	/*
	 * Locator details
	 */
	By Nordea_Link = By.linkText("Nordea");

	WebDriverWait wait = new WebDriverWait(driver, Duration.ofMinutes(1));

	public LT_BankPortal_Page SelectBankPortal() throws InterruptedException{

		CF.clickOnElement(Nordea_Link, "Nordea", "Link", "PASS");
		return new LT_BankPortal_Page(scriptHelper);
	}




}
