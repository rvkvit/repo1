package pages;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.WebDriverWait;
import businesscomponents.CommonFunctions;
import supportlibraries.ScriptHelper;

public class LossTypeSelectionPage extends  MasterPage{
	CommonFunctions CF = new CommonFunctions(scriptHelper);
	public LossTypeSelectionPage(ScriptHelper scriptHelper)
	{
		super(scriptHelper);	
	}

	/*
	 * Locator details
	 */
	By Theft = (By.xpath("(//*[@value=\"theft\"])[1]"));
	By Parkinglotcollision = (By.xpath("(//*[@value='carParking'])[1]"));
	By GlassBreakage = (By.xpath("(//*[@value=\"windShield\"])[1]"));
	By Collision = (By.xpath("(//*[@value=\"collision\"])[1]"));
	By Vandalism = (By.xpath("(//*[@value='vandalism'])[1]"));
	By TripTowing = (By.xpath("(//*[@value='repairEngine'])[1]"));
	By Fire = (By.xpath("(//*[@value='fire'])[1]"));
	By Next_Button = By.xpath("(//duet-button[contains(@class,'duet-m-0 duet-expand ') and contains(@class,'hydrated')])[1]");
	By NaturalPhenomenon = By.xpath("//*[@value=\"naturalPhenomenon\"]");
	
	String theftButton = "return document.querySelector('input[value=\"theft\"]')";
	String ParkinglotcollisionButton = "return document.querySelector('input[value=\"carParking\"]')";
	String GlassBreakageButton = "return document.querySelector('input[value=\"windShield\"]')";
	String CollisionButton = "return document.querySelector('input[value=\"collision\"]')";
	String VandalismButton = "return document.querySelector('input[value=\"vandalism\"]')";
	String fireButton = "return document.querySelector('input[value=\"fire\"]')";
	String repairEngineButton = "return document.querySelector('input[value=\"repairEngine\"]')";
	String naturalPhenomenonButton = "return document.querySelector('input[value=\"naturalPhenomenon\"]')";
	
	WebDriverWait wait = new WebDriverWait(driver, Duration.ofMinutes(1));

	public LossTypeSelectionPage selectLossTypeAndProceed(String lossType) throws InterruptedException {
		if (CF.isSafari()) {
			switch (lossType) {
			case "Theft":
				CF.mbClickShadow(theftButton, "LossTypeSelection", "Button", "PASS");
				break;
			case "GlassBreakage":
				CF.mbClickShadow(GlassBreakageButton, "LossTypeSelection", "Button", "PASS");
				break;
			case "Collision":
				CF.mbClickShadow(CollisionButton, "LossTypeSelection", "Button", "PASS");
				break;
			case "Vandalism":
				CF.mbClickShadow(VandalismButton, "LossTypeSelection", "Button", "PASS");
				break;
			case "TripTowing":
				CF.mbClickShadow(repairEngineButton, "LossTypeSelection", "Button", "PASS");
				break;
			case "Fire":
				CF.mbClickShadow(fireButton, "LossTypeSelection", "Button", "PASS");
				CF.scrollDown(Next_Button);
				break;
			case "NaturalPhenomenon":
				CF.mbClickShadow(naturalPhenomenonButton, "LossTypeSelection", "Button", "PASS");
				break;
			case "Parkinglotcollision":
				CF.mbClickShadow(ParkinglotcollisionButton, "Parkinglotcollision", "Button", "PASS");
				break;
			default:
				throw new IllegalArgumentException("Invalid loss type: " + lossType);
			}
		} else {
			switch (lossType) {
			case "Theft":
				CF.clickOnElement(Theft, "LossTypeSelection", "Button", "PASS");
				break;
			case "GlassBreakage":
				CF.clickOnElement(GlassBreakage, "LossTypeSelection", "Button", "PASS");
				break;
			case "Collision":
				CF.clickOnElement(Collision, "LossTypeSelection", "Button", "PASS");
				break;
			case "Vandalism":
				CF.clickOnElement(Vandalism, "LossTypeSelection", "Button", "PASS");
				break;
			case "TripTowing":
				CF.clickOnElement(TripTowing, "LossTypeSelection", "Button", "PASS");
				break;
			case "Fire":
				CF.clickOnElement(Fire, "LossTypeSelection", "Button", "PASS");
				CF.scrollDown(Next_Button);
				break;
			case "NaturalPhenomenon":
				CF.clickOnElement(NaturalPhenomenon, "LossTypeSelection", "Button", "PASS");
				break;
			case "Parkinglotcollision":
				CF.clickOnElement(Parkinglotcollision, "Parkinglotcollision", "Button", "PASS");
				break;
			default:
				throw new IllegalArgumentException("Invalid loss type: " + lossType);
			}
		}
	    CF.clickOnElement(Next_Button, "Next", "Button", "PASS");
	    return new LossTypeSelectionPage(scriptHelper);
	}

}
