package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;

import com.LT.framework.Status;

import businesscomponents.CommonFunctions;
import supportlibraries.ScriptHelper;

public class GlassBreakage_Page extends MasterPage {
	
	CommonFunctions CF = new CommonFunctions(scriptHelper);
	public GlassBreakage_Page(ScriptHelper scriptHelper)
	{
		super(scriptHelper);	
	}
	
	/*
	 * Locator details
	 */
	By GlassBreakage_Button = By.xpath("(//duet-button)[2]");
	
	
	
	
	public GlassBreakage_Page LossTypeSelection_LT_GB() throws InterruptedException{
		if (CF.isSafari()) {
			JavascriptExecutor js = (JavascriptExecutor) driver;
			String newURL = (String) js.executeScript("return document.querySelector(\"[class='duet-fixed primary hydrated']\").shadowRoot.querySelector(\"span > a\").href");
			System.out.println(newURL);
			driver.get(newURL);
			
			String strLang = dataTable.getData("General_Data", "Language");
			String strFIURL = dataTable.getData("General_Data", "GB FI URL ");
			String strTUURL = dataTable.getData("General_Data", "GB  SV URL");
			String expectedURL = strLang.contains("FI") ? strFIURL : strTUURL;

			// Compare the current URL with the expected URL
			String currentUrl = driver.getCurrentUrl();
			if (currentUrl.equals(expectedURL)) {
			    report.updateTestLog("Redirection to partner Portal Success", "Page URL: " + currentUrl + "<br/>", Status.PASS);
			} else {
			    report.updateTestLog("Redirection to partner Portal Failed", "Page URL: " + currentUrl + "<br/>", Status.FAIL);
			}
		}else {
			CF.clickOnElement(GlassBreakage_Button, "GlassBreakage", "Button","PASS");
			CF.isnewtabOpened();
		}
				
		return new GlassBreakage_Page(scriptHelper);
	}

}
