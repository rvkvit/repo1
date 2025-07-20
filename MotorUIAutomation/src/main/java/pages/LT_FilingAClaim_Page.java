package pages;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import supportlibraries.*;

import businesscomponents.CommonFunctions;



public class LT_FilingAClaim_Page extends MasterPage{
	
	CommonFunctions CF = new CommonFunctions(scriptHelper);
	public LT_FilingAClaim_Page(ScriptHelper scriptHelper)
	{
		super(scriptHelper);	
	}
	String strDamageType;
	String strTimeOfTheInjury;
	String strVehicleName;
	String strApp = dataTable.getData("General_Data", "Application");
	/*
	 * Locator details
	 */
	By ShadowHost = By.cssSelector("#content");
	By ShadowRootForApplyCompensation = By.cssSelector("div > div > div.duet-hero-text > duet-button");
	By ApplyForCompensation_Button = By.cssSelector("#claimOpenBtn");
	By Seuraava_Button = By.xpath("//app-choose-policy-type//duet-button");
	By Vechile_DamageType_RadioButton = By.id("vehicle");

	By TimeOfTheInjury_Title = By.xpath(".//*[contains(text(),'The time of the injury')]");
	By TimeOfTheInjury_RadioButton = By.cssSelector("#damage-time > duet-fieldset > div > duet-choice:nth-child(1)");
	By Date_DamageWasDone_TextBox = By.xpath("(.//*[@id='damage-occurred-date']//input)[1]");
	By Time_DamageWasDone_TextBox = By.xpath(".//input[@id='damage-occurred-time']");

	By SelectVehicleType_RadioButton = By.xpath(".//*[contains(text(),'ABC-123')]");
	By ShadowHost_Next = By.cssSelector("#main-layout > div > duet-layout > div > app-choose-policy-type > div > form > duet-card > duet-button");
	By ShadowHost_Next_TimeOfInjury = By.cssSelector("#main-layout > div > duet-layout > div > lossdate > duet-card > div > div > duet-button.primary.hydrated");
	By Shadow_Next_VehicleDamage = By.cssSelector("#main-layout > div > duet-layout > div > ss-vehicle-list > duet-card > div > div > duet-grid > duet-grid-item:nth-child(1) > duet-button");
	By Next_Button = By.cssSelector("span > button");
	By Back_Button = By.xpath("//duet-button[@class=\"default hydrated\"]");
	By Yes_Button = By.xpath("(//duet-button[@class=\"istrackable ng-star-inserted primary hydrated\"])[1]");
	By Verification_Button = By.xpath("//*[@id=\"ltClaimPage\"]");
	By ApplyCompensataion_button = By.id("claimlisthero_applycompensation_linkbutton");
	
	String NextButton= "return document.querySelector(\"#main > lossdate > duet-card > div > div > duet-button.primary.hydrated\").shadowRoot.querySelector(\"span > button\")";
	String Next_VehicleDamage= "return document.querySelector(\"#main-layout > div > duet-layout > div > ss-vehicle-list > duet-card > div > div > duet-grid > duet-grid-item:nth-child(1) > duet-button\").shadowRoot.querySelector(\"span > button\")";
	String VechileDamageTypeRadioButton = "return document.querySelector(\"#vehicle > div > div > input\")";
	String TimeOfTheInjuryRadioButton = "return document.querySelector(\"#damage-time > duet-fieldset > div > duet-choice:nth-child(1) > div > div > input\")";

	WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));

	public LT_FilingAClaim_Page ClickOnApplyForCompensation() throws InterruptedException {
		try {
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(".//*[@class='content-without-background']")));
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(".//*[@class='content-without-background']")));
		} catch (Exception e) {
			// System.out.println(e);
		}
		wait.until(ExpectedConditions.visibilityOfElementLocated(ApplyCompensataion_button));
		CF.clickOnElement(ApplyCompensataion_button, "Submit a claim", "Button", "PASS");

		return new LT_FilingAClaim_Page(scriptHelper);
	}
	
	private void clickRadioButton(By element, String shadowElement, String elementName) throws InterruptedException {
        if (CF.isSafari()) {
            CF.mbClickShadow(shadowElement, elementName, "RadioButton", "PASS");
        } else {
            CF.clickOnElement(element, elementName, "RadioButton", "PASS");
        }
    }

	public LT_FilingAClaim_Page SelectDamageTypeAndMoveNext() throws InterruptedException {
		clickRadioButton(Vechile_DamageType_RadioButton, VechileDamageTypeRadioButton, "Damage Type");
		CF.scrollDown(Seuraava_Button);
		CF.clickOnElement(Seuraava_Button, "Next", "Button", "PASS");
		return new LT_FilingAClaim_Page(scriptHelper);
	}

	public LT_FilingAClaim_Page SelectTimeOfTheInjuryAndMoveNext(String timeOfTheInjury) throws InterruptedException {
		strTimeOfTheInjury = timeOfTheInjury;
		String strDate = CF.getCurrentDate("dd.MM.yyyy");
		String strTime = dataTable.getData("General_Data", "Time");
		clickRadioButton(TimeOfTheInjury_RadioButton, TimeOfTheInjuryRadioButton, timeOfTheInjury);
		CF.enterValuesInTextBox(Date_DamageWasDone_TextBox, "Date", strDate, "PASS");
		CF.enterValuesInTextBox(Time_DamageWasDone_TextBox, "Time", strTime, "PASS");
		if (CF.isMobilePlatform()) {
			CF.mbClickShadow(NextButton, "Next", "Button", "PASS");
		}else {
		CF.clickOnShadowElementWithScrollView(ShadowHost_Next_TimeOfInjury, Next_Button, "Next", "Button", "PASS");
		}
		if (CF.isSafari()) {
			WebElement next = driver.findElement(By.xpath("(//lossdate/duet-card//duet-button)[2]"));
			next.click();
		}
		return new LT_FilingAClaim_Page(scriptHelper);
	}

	public LT_FilingAClaim_Page SelectVechileTypeAndMoveNext(String vehicleName) {
		strVehicleName = vehicleName;
		CF.clickOnElement(SelectVehicleType_RadioButton, "Vehicle Type", "Radio Button", "PASS");
		if (CF.isMobilePlatform()) {
			CF.mbClickShadow(Next_VehicleDamage, "Next", "Button", "PASS");
		} else {
			CF.clickOnShadowElementWithScrollView(Shadow_Next_VehicleDamage, Next_Button, "Next", "Button", "PASS");
		}
		return new LT_FilingAClaim_Page(scriptHelper);
	}
	
	public LT_FilingAClaim_Page Canceledpolicy() throws InterruptedException {
		CF.clickOnElement(Back_Button, "Back_Button", "Button", "PASS");
		CF.clickOnElement(Yes_Button, "Yes Button", "Button", "PASS");
		try {
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(".//*[@class='content-without-background']")));
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(".//*[@class='content-without-background']")));
		} catch (Exception e) {
			// System.out.println(e);
		}
		try {
			wait.until(ExpectedConditions.visibilityOfElementLocated(ShadowHost));
		} catch (Exception e) {
			// System.out.println(e);
		}
		CF.ElementVisible(Verification_Button, "Claimlist Page", "PASS");
		return new LT_FilingAClaim_Page(scriptHelper);
	}
}

