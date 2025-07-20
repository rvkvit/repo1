package pages;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import supportlibraries.*;

import businesscomponents.CommonFunctions;

public class LT_ReleaseFlagSelection_Page extends MasterPage {
	CommonFunctions CF = new CommonFunctions(scriptHelper);

	public LT_ReleaseFlagSelection_Page(ScriptHelper scriptHelper) {
		super(scriptHelper);
	}

	/*
	 * Locator details
	 */
	By ReleaseMotor1_CheckBox = By.xpath("//div[contains(text(),'RELEASE_MOTOR1')]//ancestor::duet-choice");
	By ReleaseLife1_CheckBox = By.xpath(".//div[contains(text(),'RELEASE_LIFE1')]//ancestor::label[1]//div[2]//div");
	By Save_Button = By.xpath(".//*[contains(text(),'Save')]");
	By ReleaseMay2024_CheckBox = By.xpath("//div[contains(text(),'RELEASE_MAY2024')]//ancestor::duet-choice");
	By ReleaseMarch2024_Checkbox = By.xpath("//div[contains(text(),'RELEASE_MARCH2024')]//ancestor::duet-choice");
	By ReleaseLife1_Checkbox = By.xpath("//div[contains(text(),'RELEASE_LIFE1')]//ancestor::duet-choice");
	By ReleaseApril2024_Checkbox = By.xpath("//div[contains(text(),'RELEASE_APRIL2024')]//ancestor::duet-choice");
	By RELEASE_OCT2023_Checkbox = By.xpath("//div[contains(text(),'RELEASE_OCT2023')]//ancestor::duet-choice");
	By RELEASE_DEC2023_Checkbox = By.xpath("//div[contains(text(),'RELEASE_DEC2023')]//ancestor::duet-choice");
	By RELEASE_MAY2025_Checkbox = By.xpath("//div[contains(text(),'RELEASE_MAY2025')]//ancestor::duet-choice");

	String strEnv = properties.getProperty("Environment");
	String strMayEnabled = properties.getProperty("MAY_FLAG");

	WebDriverWait wait = new WebDriverWait(driver, Duration.ofMinutes(1));

	public LT_ReleaseFlagSelection_Page SelectReleaseFlag() throws InterruptedException {
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(ReleaseMotor1_CheckBox));
		if (strEnv.contains("SIT")) {
			if (driver.findElement(ReleaseMotor1_CheckBox).getAttribute("checked") == null) {
				CF.clickOnElement(ReleaseMotor1_CheckBox, "Release Motor 1", "Checkbox", "PASS");
			}

			if (strMayEnabled.contains("true")) {

				CF.scrollDown(RELEASE_MAY2025_Checkbox);
				if (driver.findElement(RELEASE_MAY2025_Checkbox).getAttribute("checked") == null) {
					CF.clickOnElement(RELEASE_MAY2025_Checkbox, "RELEASE MAY 2025", "Checkbox", "PASS");
				}

			}
			 
			/*
			 * CF.scrollDown(RELEASE_MAY2025_Checkbox); if
			 * (driver.findElement(RELEASE_MAY2025_Checkbox).getAttribute("checked") ==
			 * null) { CF.clickOnElement(RELEASE_MAY2025_Checkbox, "RELEASE MAY 2025",
			 * "Checkbox", "PASS"); }
			 */
			/*
			 * CF.scrollDown(ReleaseApril2024_Checkbox); if
			 * (driver.findElement(ReleaseApril2024_Checkbox).getAttribute("checked") !=
			 * null) { CF.clickOnElement(ReleaseApril2024_Checkbox, "Release April 2024",
			 * "Checkbox", "PASS"); } CF.scrollDown(ReleaseMay2024_CheckBox); if
			 * (driver.findElement(ReleaseMay2024_CheckBox).getAttribute("checked") == null)
			 * { CF.clickOnElement(ReleaseMay2024_CheckBox, "Release May 2024", "Checkbox",
			 * "PASS"); } CF.scrollDown(ReleaseLife1_Checkbox); if
			 * (driver.findElement(ReleaseLife1_Checkbox).getAttribute("checked") != null) {
			 * CF.clickOnElement(ReleaseLife1_Checkbox, "Release Life", "Checkbox", "PASS");
			 * } CF.scrollDown(ReleaseMarch2024_Checkbox); if
			 * (driver.findElement(ReleaseMarch2024_Checkbox).getAttribute("checked") !=
			 * null) { CF.clickOnElement(ReleaseMarch2024_Checkbox, "Release March 2024",
			 * "Checkbox", "PASS"); }
			 */
		}

		else {
			if (driver.findElement(ReleaseMotor1_CheckBox).getAttribute("checked") == null) {
				CF.clickOnElement(ReleaseMotor1_CheckBox, "Release Motor 1", "Checkbox", "PASS");
			}

			if (strMayEnabled.contains("true")) {

				CF.scrollDown(RELEASE_MAY2025_Checkbox);
				if (driver.findElement(RELEASE_MAY2025_Checkbox).getAttribute("checked") == null) {
					CF.clickOnElement(RELEASE_MAY2025_Checkbox, "RELEASE MAY 2025", "Checkbox", "PASS");
				}

			}
			/*
			 * CF.scrollDown(ReleaseMay2024_CheckBox); if
			 * (driver.findElement(ReleaseMay2024_CheckBox).getAttribute("checked") == null)
			 * { CF.clickOnElement(ReleaseMay2024_CheckBox, "Release May 2024", "Checkbox",
			 * "PASS"); } CF.scrollDown(ReleaseMarch2024_Checkbox); if
			 * (driver.findElement(ReleaseMarch2024_Checkbox).getAttribute("checked") !=
			 * null) { CF.clickOnElement(ReleaseMarch2024_Checkbox, "Release March 2024",
			 * "Checkbox", "PASS"); }
			 */
		}
		return new LT_ReleaseFlagSelection_Page(scriptHelper);
	}

	public LT_ReleaseFlagSelection_Page SaveSelection() throws InterruptedException {
		Thread.sleep(3000);
		CommonFunctions.scrollToElementAndClick(driver, driver.findElement(Save_Button));
		return new LT_ReleaseFlagSelection_Page(scriptHelper);
	}

}
