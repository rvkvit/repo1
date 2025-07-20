package pages;

import java.time.Duration;

import org.openqa.selenium.By;

import org.openqa.selenium.support.ui.WebDriverWait;

import businesscomponents.CommonFunctions;
import supportlibraries.ScriptHelper;

public class ThankYou_Page extends MasterPage{
	
	CommonFunctions CF = new CommonFunctions(scriptHelper);
	public ThankYou_Page(ScriptHelper scriptHelper)
	{
		super(scriptHelper);	
	}
	
	WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
	
	By ThankYou_Heading = By.cssSelector("#thank-you-page-heading");
	
	public ThankYou_Page ThankYouPageConfirm() throws InterruptedException {
        CF.verifyElement(ThankYou_Heading, "Thank You Heading", "Heading","PASS");
		return new ThankYou_Page(scriptHelper);
	}

}
