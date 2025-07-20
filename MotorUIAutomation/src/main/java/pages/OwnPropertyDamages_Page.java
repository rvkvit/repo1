package pages;

import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import businesscomponents.CommonFunctions;
import supportlibraries.ScriptHelper;

public class OwnPropertyDamages_Page extends MasterPage{
	
	CommonFunctions CF = new CommonFunctions(scriptHelper);
	public OwnPropertyDamages_Page(ScriptHelper scriptHelper)
	{
		super(scriptHelper);	
	}
	
	String strPrice = RandomStringUtils.randomNumeric(5);
	
	By AddProperty_Button = By.cssSelector("#main-layout > div > duet-layout > div > lt-cel-platform-feature-vehicle-fnol > duet-card > lt-cel-platform-own-property-damages > div > duet-button");
	By RealEstateProperty_TextArea = By.xpath("(//*[@id='propertyDamageModal']//following::input)[3]");
	By Save_Button = By.cssSelector("#propertyDamageModal > div > duet-grid > duet-grid-item:nth-child(1) > duet-button");
	By Next_Button = By.xpath("//*[@class=\"duet-m-0 duet-expand primary hydrated\"]");
	By MovableModel_TextArea = By.xpath("(//*[@id='propertyDamageModal']//following::input)[4]");
	By CommisioningYear_Dropdown = By.cssSelector("#movableYearOfAcquisition");
	By PurchasePrice_TextArea = By.xpath("(//*[@id='propertyDamageModal']//following::input)[5]");
	By RealProperty_RadioButton = By.xpath("(//*[@formcontrolname=\"damagePropertyChoice\"]//duet-choice)[1]");
	By RealEstateDescription_TextArea_host = By.cssSelector("duet-textarea[formcontrolname='realEstateDescription']");
	By RealEstateDescription_TextArea_shadow = By.cssSelector("textarea");
	By realEstateAddedTaxYes_RadioButton = By.xpath("(//*[@formcontrolname=\"realEstateAddedTax\"]//duet-choice)[1]");
	By LienHolderYes_RadioButton = By.xpath("(//*[@formcontrolname=\"realEstateIsAlienHolder\"]//duet-choice)[1]");
	By MovableProperty_RadioButton = By.xpath("(//*[@formcontrolname=\"damagePropertyChoice\"]//duet-choice)[2]");
	By MovableIsLeasingYes_RadioButton = By.xpath("(//*[@formcontrolname=\"movableIsLeasing\"]//duet-choice)[1]");
	By PersonalPropertyinDeclaredVehicle_RadioButton = By.xpath("(//*[@formcontrolname=\"whereWasPersonalProperty\"]//duet-choice)[1]");
	By movableAddedTaxYes_RadioButton = By.xpath("(//*[@formcontrolname=\"movableAddedTax\"]//duet-choice)[1]");
	
	String realEstateProperty = "return document.querySelector(\"input[name='damagePropertyChoice'][value='realEstateProperty']\")";
	String realEstateAddedTax = "return document.querySelector(\"input[name='realEstateAddedTax'][value='yes']\")";
	String realEstateIsAlienHolder = "return document.querySelector(\"input[name='realEstateIsAlienHolder'][value='yes']\")";
	String movableProperty = "return document.querySelector(\"input[name='damagePropertyChoice'][value='movableProperty']\")";
	String movableAddedTax = "return document.querySelector(\"input[name='movableAddedTax'][value='yes']\")";
	String realEstateDescription = "return document.querySelector(\"duet-textarea[formcontrolname='realEstateDescription']\").shadowRoot.querySelector(\"textarea\")";
	
	public OwnPropertyDamages_Page AddPropertyDamagesReal() throws InterruptedException {
		CF.clickOnElement(AddProperty_Button, "AddProperty Button", "Button", "PASS");
		CF.clickRadioButton(RealProperty_RadioButton,realEstateProperty, "RealProperty RadioButton","RadioButton");
		Thread.sleep(2000);
		CF.enterValues(RealEstateProperty_TextArea, "RealEstateProperty TextArea", 100, "PASS");
		CF.scrollDown(RealEstateDescription_TextArea_host);
		CF.enterValuesShadowElement(RealEstateDescription_TextArea_host,RealEstateDescription_TextArea_shadow,realEstateDescription, "Movable Modal TextArea", 200, "PASS");
		CF.scrollDown(realEstateAddedTaxYes_RadioButton);
		CF.clickRadioButton(realEstateAddedTaxYes_RadioButton, realEstateAddedTax, "realEstateAddedTax Yes RadioButton","RadioButton");
		CF.scrollDown(Save_Button);
		CF.clickRadioButton(LienHolderYes_RadioButton,realEstateIsAlienHolder, "LienHolder Yes RadioButton","RadioButton");
		CF.clickOnElement(Save_Button, "Save Button", "Button", "PASS");
		CF.clickOnElement(Next_Button, "Next", "Button", "PASS");
		return new OwnPropertyDamages_Page(scriptHelper);
	}
	
}
