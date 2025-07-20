package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import businesscomponents.CommonFunctions;
import supportlibraries.ScriptHelper;

public class PartyInformation_Page extends  MasterPage{
	
	CommonFunctions CF = new CommonFunctions(scriptHelper);
	public PartyInformation_Page(ScriptHelper scriptHelper)
	{
		super(scriptHelper);	
	}
	
	By CauseOfDamage_Dropdown = By.cssSelector("#causeOfDamage");
	By WhoCausedDamage_TextArea = By.cssSelector("#whoCausedDamage.duet-input");
	By DriverLicValidYes_RadioButton = By.xpath("//*[@name='wasDriverLicenseValidAtAccident' and @value='yes']");
	By PassengerYes_RadioButton = By.xpath("//*[@name='wereYouPassengerInVehicle' and @value='yes']");
	By LicenseIssuedYear_Dropdown = By.cssSelector("#licenseIssuedYear");
	By TypeOfDrivingLicense_Dropdown = By.cssSelector("#typeOfDrivingLicense");
	By Next_Button = By.xpath("(//duet-button[contains(@class,'duet-m-0 duet-expand ') and contains(@class,'hydrated')])[1]");
	By PassengerNo_RadioButton = By.xpath("//*[@name='wereYouPassengerInVehicle' and @value='no']");
	By DriverLicValidNo_RadioButton = By.xpath("//*[@name='wasDriverLicenseValidAtAccident' and @value='no']");
	By WereYouDriverInVehicleYes_RadioButton = By.xpath("(//*[@formcontrolname=\"wereYouDriverInVehicle\"]//duet-choice)[1]");
	By WereYouDriverInVehicleNo_RadioButton = By.xpath("(//*[@formcontrolname=\"wereYouDriverInVehicle\"]//duet-choice)[2]");
	By AdmitGuiltYes_RadioButton = By.xpath("(//*[@formcontrolname=\"doesPartyPleadGuilty\"]//child::duet-choice)[1]");
	
	String WereYouDriverInVehicleYesRadioButton = "return document.querySelector('input[name=\"wereYouDriverInVehicle\"][value=\"yes\"]')";
	String didOtherVehicleHitOptionsYes = "return document.querySelector(\"input[name='didOtherVehicleHitOptions'][value='yes']\")";
	String wereYouDriverInVehicleYes = "return document.querySelector(\"input[name='wereYouDriverInVehicle'][value='yes']\")";
	
	public PartyInformation_Page PartyInformation() throws InterruptedException {
		CF.selectDropDownByIndex(CauseOfDamage_Dropdown, "CauseOfDamage Dropdown", 1, "PASS");
		CF.enterValues(WhoCausedDamage_TextArea, "WhoCausedDamage TextArea", 100, "PASS");
		CF.clickRadioButton(AdmitGuiltYes_RadioButton,didOtherVehicleHitOptionsYes, "AdmitGuilt Yes RadioButton", "RadioButton");
		CF.clickRadioButton(WereYouDriverInVehicleYes_RadioButton, wereYouDriverInVehicleYes, "WereYouDriverInVehicle Yes RadioButton", "RadioButton");
		//CF.clickOnElement(DriverLicValidYes_RadioButton, "DriverLicValid Yes RadioButton", "RadioButton", "PASS");
		WebElement DriverLicValidYes = driver.findElement(DriverLicValidYes_RadioButton);
		DriverLicValidYes.click();
		Thread.sleep(1000);
		CF.selectDropDownByIndex(LicenseIssuedYear_Dropdown, "LicenseIssuedYear Dropdown", 1, "PASS");
		CF.selectDropDownByIndex(TypeOfDrivingLicense_Dropdown, "TypeOfDrivingLicense Dropdown", 1, "PASS");
		CF.clickOnElement(Next_Button, "Next", "Button", "PASS");
		return new PartyInformation_Page(scriptHelper);
	}

}
