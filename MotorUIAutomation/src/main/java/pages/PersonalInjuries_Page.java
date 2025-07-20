package pages;

import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import businesscomponents.CommonFunctions;
import supportlibraries.ScriptHelper;

public class PersonalInjuries_Page extends MasterPage{
	
	CommonFunctions CF = new CommonFunctions(scriptHelper);
	public PersonalInjuries_Page(ScriptHelper scriptHelper)
	{
		super(scriptHelper);	
	}
	
	String strPhoneNo = "040" + RandomStringUtils.randomNumeric(8);
	
	By MildInjury_RadioButton = By.xpath("(//*[@name=\"injury-severity\"])[1]");
	By Situation_Dropdown = By.cssSelector("#situation");
	By FirstName_TextArea = By.xpath("(//*[@id='addInjuryModal']//following::input)[1]");
	By LastName_TextArea = By.xpath("(//*[@id='addInjuryModal']//following::input)[2]");
	By PhoneNo_TextArea = By.xpath("(//*[@id='addInjuryModal']//following::input)[3]");
	By InjuredPersonPosition_Dropdown = By.cssSelector("#injured-person-position");
	By Save_Button = By.cssSelector("#addInjuryModal > div > duet-grid:nth-child(4) > duet-grid-item:nth-child(1) > duet-button");
	By Next_Button = By.xpath("(//duet-button[contains(@class,'duet-m-0 duet-expand ') and contains(@class,'hydrated')])[1]");
	By WereYouInjuredYes_RadioButton = By.xpath("(//*[@formcontrolname=\"wereYouInjured\"]//duet-choice)[1]");
	By WereYouInjuredNo_RadioButton = By.xpath("(//*[@formcontrolname=\"wereYouInjured\"]//duet-choice)[2]");
	By WereOtherInjuriesNo_RadioButton = By.xpath("(//*[@formcontrolname=\"wereOtherInjuries\"]//duet-choice)[2]");
	By WereOtherInjuriesYes_RadioButton = By.xpath("(//*[@formcontrolname=\"wereOtherInjuries\"]//duet-choice)[1]");
	By AddInjuredParty_RadioButton = By.xpath("//*[@formcontrolname=\"wereOtherInjuries\"]//child::duet-button");
	
	private String WereYouInjuredNoRadioButton = "return document.querySelector('input[name=\"were-you-injured\"][value=\"no\"]')";
	private String WereYouInjuredYesRadioButton = "return document.querySelector('input[name=\"were-you-injured\"][value=\"yes\"]')";
	private String WereOtherInjuriesNoRadioButton = "return document.querySelector('input[name=\"were-there-other-injuries\"][value=\"no\"]')";
	private String WereOtherInjuriesYesRadioButton = "return document.querySelector('input[name=\"were-there-other-injuries\"][value=\"yes\"]')";
	
	public PersonalInjuries_Page AddInjury() throws InterruptedException {		
		CF.clickRadioButton(WereYouInjuredYes_RadioButton,WereYouInjuredYesRadioButton, "WereYouInjured Yes", "RadioButton");
		//CF.clickOnElement(MildInjury_RadioButton, "MildInjury RadioButton", "RadioButton", "PASS");
		WebElement MildInjury = driver.findElement(MildInjury_RadioButton);
		MildInjury.click();
		CF.selectDropDownByIndex(Situation_Dropdown, "Situation Dropdown", 1, "PASS");
		CF.clickRadioButton(WereOtherInjuriesYes_RadioButton,WereOtherInjuriesYesRadioButton, "WereOtherInjuries Yes", "RadioButton");
		CF.clickOnElement(AddInjuredParty_RadioButton, "AddInjuredParty RadioButton", "RadioButton", "PASS");
		CF.enterValues(FirstName_TextArea, "FirstName TextArea", 50, "PASS");
		CF.enterValues(LastName_TextArea, "LastName TextArea", 50, "PASS");
		CF.enterValuesInTextBox(PhoneNo_TextArea, "Phone Number", strPhoneNo,"PASS");
		CF.selectDropDownByIndex(InjuredPersonPosition_Dropdown, "InjuredPersonPosition Dropdown", 1, "PASS");
		CF.clickOnElement(Save_Button, "Save Button", "Button", "PASS");
		CF.clickOnElement(Next_Button, "Next", "Button", "PASS");
		return new PersonalInjuries_Page(scriptHelper);
	}
	
}
