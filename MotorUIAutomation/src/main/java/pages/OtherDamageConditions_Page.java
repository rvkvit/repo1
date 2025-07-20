package pages;

import org.openqa.selenium.By;

import businesscomponents.CommonFunctions;
import supportlibraries.ScriptHelper;

public class OtherDamageConditions_Page extends  MasterPage{
	
	CommonFunctions CF = new CommonFunctions(scriptHelper);
	public OtherDamageConditions_Page(ScriptHelper scriptHelper)
	{
		super(scriptHelper);	
	}
	
	By TypeOfStreet_Dropdown = By.cssSelector("#type-of-street");
	By SpeedBeforeAcc_Dropdown = By.cssSelector("#speed-before-accident");
	By SpeedLimit_Dropdown = By.cssSelector("#speed-limit-at-scene");
	By Next_Button = By.xpath("(//*[@class=\"duet-m-0 duet-expand primary hydrated\"])");
	By ForwardDirection_RadioButton = By.xpath("(//*[@formcontrolname=\"directionOfTravel\"]//duet-choice)[1]");
	By BackwardDirection_RadioButton = By.xpath("(//*[@formcontrolname=\"directionOfTravel\"]//duet-choice)[2]");
	By TrailerUsedYes_RadioButton = By.xpath("(//*[@formcontrolname=\"wasTrailerUsed\"]//duet-choice)[1]");
	By TrailerUsedNo_RadioButton = By.xpath("(//*[@formcontrolname=\"wasTrailerUsed\"]//duet-choice)[2]");
	
	String ForwardDirection = "return document.querySelector(\"input[name='direction-of-travel'][value='Forward']\")";
	String TrailerusedYes = "return document.querySelector(\"input[name='was-trailer-used'][value='yes']\")";
	String TrailerusedNo = "return document.querySelector(\"input[name='was-trailer-used'][value='no']\")";
		
	public OtherDamageConditions_Page OtherDamageConditionYes() throws InterruptedException {
		CF.selectDropDownByIndex(TypeOfStreet_Dropdown, "TypeOfStreet Dropdown", 1, "PASS");
		CF.selectDropDownByIndex(SpeedBeforeAcc_Dropdown, "SpeedBeforeAccident Dropdown", 2, "PASS");
		CF.selectDropDownByIndex(SpeedLimit_Dropdown, "SpeedLimit at scene Dropdown", 1, "PASS");
		CF.clickRadioButton(ForwardDirection_RadioButton, ForwardDirection, "Direction of travel Forward RadioButton", "RadioButton");
		CF.clickRadioButton(TrailerUsedYes_RadioButton, TrailerusedYes, "TrailerUsed Yes RadioButton", "RadioButton");
		CF.clickOnElement(Next_Button, "Next", "Button", "PASS");
		return new OtherDamageConditions_Page(scriptHelper);
	}
}
