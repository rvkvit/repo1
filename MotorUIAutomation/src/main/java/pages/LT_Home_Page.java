package pages;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;

import org.openqa.selenium.support.ui.WebDriverWait;

import supportlibraries.*;

import businesscomponents.CommonFunctions;

public class LT_Home_Page extends MasterPage {

	CommonFunctions CF = new CommonFunctions(scriptHelper);
	
	public LT_Home_Page(ScriptHelper scriptHelper) {
		super(scriptHelper);
	}

	/*
	 * Locator details
	 */
	By shadowHost = By.cssSelector("body > app-root > ss-app-base > ss-nav-bar > duet-header");
	By CompensationAndDamageMatters_Link = By.cssSelector("#generic\\.claims_tab");
	By SignOutSession_Link = By.cssSelector("#session");
	By FrontPage_Link = By.cssSelector("#generic\\.home_tab");
	By LanguageSelection_Link = By.cssSelector("#duet-header-language-button");
	By SwedishSelection_Link = By.cssSelector("#generic\\.secondarylang1_tab");
	By FinnishSelection_Link = By.cssSelector("#generic\\.deafaultlang_tab");
	By InsuranceTab = By.cssSelector("#generic\\.insurance_tab");
	By InvoiceTab = By.cssSelector("#generic\\.invoices_tab");
	//By Insurance_Link = By.xpath("(//duet-button)[1]");
	By Insurance_Link = By.id("dynamichero_mainserviceslist_insurances_linkbutton");
	
	String Hamburger_button = "return document.querySelector(\"body > app-root > ss-app-base > ss-nav-bar > duet-header\").shadowRoot.querySelector(\"div > header > div.duet-header-top > button\")";
	String LangSelection = "return document.querySelector(\"body > app-root > ss-app-base > ss-nav-bar > duet-header\").shadowRoot.querySelector(\"#duet-header-language-button\")";
	String LangFI = "return document.querySelector(\"body > app-root > ss-app-base > ss-nav-bar > duet-header\").shadowRoot.querySelector(\"#generic\\\\.deafaultlang_tab\")";
	String LangSV = "return document.querySelector(\"body > app-root > ss-app-base > ss-nav-bar > duet-header\").shadowRoot.querySelector(\"#generic\\\\.secondarylang1_tab\")";
	String CompensationTab = "return document.querySelector(\"body > app-root > ss-app-base > ss-nav-bar > duet-header\").shadowRoot.querySelector(\"#generic\\\\.claims_tab\")";

	WebDriverWait wait = new WebDriverWait(driver, Duration.ofMinutes(1));

	public LT_Home_Page VerifyHomePage() throws InterruptedException {
		try {
			wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.id("onetrust-accept-btn-handler"))));
			driver.findElement(By.id("onetrust-accept-btn-handler")).click();

		} catch (Exception e) {
			System.out.println("");
		}
		wait.until(ExpectedConditions.visibilityOfElementLocated(shadowHost));
		if (CF.isMobilePlatform()) {
			CF.mbverifyShadowElement(Hamburger_button, "Hamburger", "Button", "PASS");
		} else {
			//wait.until(ExpectedConditions.visibilityOfElementLocated(shadowHost));
			CF.verifyShadowElement(shadowHost, CompensationAndDamageMatters_Link, "Compensation and Damage Matters","Tab", "PASS");
		}
		return new LT_Home_Page(scriptHelper);
	}

	public LT_Home_Page LanguageSelection() {
		String strLang = dataTable.getData("General_Data", "Language");
		boolean isSwedish = strLang.contains("SV");

		// Common method for language selection (Mobile/Desktop)
		clickLanguageSelection(isSwedish);
		return new LT_Home_Page(scriptHelper);
	}

	// Method to handle language selection for both mobile and desktop platforms
	private void clickLanguageSelection(boolean isSwedish) {
	    if (CF.isMobilePlatform()) {
	        CF.mbClickShadow(Hamburger_button, "Hamburger", "Button", "PASS");
	        CF.mbClickShadow(LangSelection, "Language Selection", "Link", "PASS");
	        CF.mbClickShadow(isSwedish ? LangSV : LangFI, 
	                         isSwedish ? "SwedishSelection_Link" : "FinnishSelection_Link", 
	                         "Link", "PASS");
	    } else {
	        CF.clickOnShadowElement(shadowHost, LanguageSelection_Link, "Language Selection", "Link", "PASS");
	        wait.until(ExpectedConditions.visibilityOfElementLocated(shadowHost));
	        CF.clickOnShadowElement(shadowHost, 
	                                isSwedish ? SwedishSelection_Link : FinnishSelection_Link, 
	                                isSwedish ? "Swedish Selection" : "Finnish Selection", 
	                                "Link", "PASS");
	    }
	}

	public LT_Home_Page clickOnSpecificTabAvailableOnHomePage(String strTabName) {
		switch (strTabName) {
		case "Compensation and Damage Matters":
			if (CF.isMobilePlatform()) {
				CF.mbClickShadow(Hamburger_button, "Hamburger", "Button", "PASS");
				CF.mbClickShadow(CompensationTab, "CompensationAndDamageMatters_Link", "Tab", "PASS");
			} else {
				CF.clickOnShadowElement(shadowHost, CompensationAndDamageMatters_Link,"Compensation and Damage Matters", "Tab", "PASS");
			}
			break;
		case "":
			break;
		}
		return new LT_Home_Page(scriptHelper);
	}

	public void clickOnInsuranceTab(String strTabName) {
		switch (strTabName) {
		case "Insurance":
			CF.clickOnShadowElement(shadowHost, InsuranceTab, "Insurance", "Tab", "PASS");
			break;
		case "":
			break;
		}
	}

	public void clickOnInvoiceTab(String strTabName) {
		switch (strTabName) {
		case "Invoice":
			CF.clickOnShadowElement(shadowHost, InvoiceTab, "Invoice", "Tab", "PASS");
			break;
		case "":
			break;
		}
	}

	public LT_Home_Page InsuranceTabSelection() throws InterruptedException {
		CF.scrollDown(Insurance_Link);
		CF.clickOnElement(Insurance_Link, "Insurance", "Link", "PASS");
		return new LT_Home_Page(scriptHelper);
	}

}