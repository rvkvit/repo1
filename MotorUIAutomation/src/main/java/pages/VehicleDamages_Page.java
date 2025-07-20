package pages;

import org.openqa.selenium.By;

import businesscomponents.CommonFunctions;
import supportlibraries.ScriptHelper;

public class VehicleDamages_Page extends MasterPage{
	
	CommonFunctions CF = new CommonFunctions(scriptHelper);
	public VehicleDamages_Page(ScriptHelper scriptHelper)

	{
		super(scriptHelper);
	}
	
	/*
	 * Locator details
	 */
	By FrontSelect_Button = By.cssSelector("#frontSelect");
	By DamageSelect_CheckBox = By.cssSelector("duet-checkbox:nth-child(1)");
	By Next_Button = By.xpath("(//duet-button[contains(@class,'duet-m-0 duet-expand ') and contains(@class,'hydrated')])[1]");
	By NextNo_Button = By.xpath("//*[@class='duet-m-0 duet-expand primary hydrated']");
	By VehicleDamagedYes_RadioButton = By.xpath("(//*[@id=\"vehicleDamage\"]//duet-choice)[1]");
	By VehicleDamagedNo_RadioButton = By.xpath("(//*[@id=\"vehicleDamage\"]//duet-choice)[2]");
	By DamageDescription_Textarea_Host = By.cssSelector("duet-textarea");
	By DamageDescription_Textarea_Shadow = By.cssSelector("textarea");
	
	String mbFrontSelect_Button = "return document.querySelector(\"#frontSelect\").shadowRoot.querySelector(\"input[value='Bonnet']\")";
	String VehicleDamagedYesRadioButton = "return document.querySelector('input[name=\"was-vehicle-damaged\"][value=\"yes\"]')";
	String VehicleDamagedNoRadioButton = "return document.querySelector('input[name=\"was-vehicle-damaged\"][value=\"no\"]')";
	String FrontSelectButton = "return document.querySelector(\"#frontSelect\").shadowRoot.querySelector(\"button\")";
	String DamageDescription = "return document.querySelector(\"duet-textarea\").shadowRoot.querySelector(\"textarea\")";
	
	public VehicleDamages_Page VehicleDamagedYes() throws InterruptedException{
		CF.clickRadioButton(VehicleDamagedYes_RadioButton, VehicleDamagedYesRadioButton, "Vehicle Damaged Yes","RadioButton");
		CF.clickRadioButton(FrontSelect_Button, FrontSelectButton, "FrontSelect","Button");		
		Thread.sleep(3000);
		if (CF.isMobilePlatform()) {
			CF.mbClickShadow(mbFrontSelect_Button, "DamageSelect", "DamageSelect", "PASS");}
		else {
		CF.clickOnShadowElementWithScrollView(FrontSelect_Button ,DamageSelect_CheckBox, "DamageSelect", "DamageSelect","PASS");}
		CF.enterValuesShadowElement(DamageDescription_Textarea_Host, DamageDescription_Textarea_Shadow, DamageDescription,"DamageDescription TextArea", 200, "PASS");
		CF.clickOnElement(Next_Button, "Next", "Button","PASS");
		return new VehicleDamages_Page(scriptHelper);
	}
}
