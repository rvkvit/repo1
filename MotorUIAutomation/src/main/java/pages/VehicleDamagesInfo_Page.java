package pages;

import org.openqa.selenium.By;

import businesscomponents.CommonFunctions;
import supportlibraries.ScriptHelper;

public class VehicleDamagesInfo_Page extends  MasterPage{
	
	CommonFunctions CF = new CommonFunctions(scriptHelper);
	public VehicleDamagesInfo_Page(ScriptHelper scriptHelper)
	{
		super(scriptHelper);	
	}

	/*
	 * Locator details
	 */
	By VehicleOperableYes_RadioButton = By.xpath("(//*[@formcontrolname=\"isVehicleOperable\"]//child::duet-choice)[1]");
	By VehicleOperableNo_RadioButton = By.xpath("(//*[@formcontrolname=\"isVehicleOperable\"]//child::duet-choice)[2]");
	By DamagePriorToAccYes_RadioButton = By.xpath("(//*[@formcontrolname=\"isDamagePriorToAcc\"]//child::duet-choice)[1]");
	By DamagePriorToAccNo_RadioButton = By.xpath("(//*[@formcontrolname=\"isDamagePriorToAcc\"]//child::duet-choice)[2]");
	By VehicleDamageDescription_TextArea_host = By.cssSelector("duet-textarea[formcontrolname='vehicleDamageDescription']");
	By VehicleDamageDescription_TextArea_Shadow = By.cssSelector("textarea");
	By VehicleInspectedYes_RadioButton = By.xpath("(//*[@formcontrolname=\"hasVehicleInspected\"]//child::duet-choice)[1]");
	By VehicleInspectedNo_RadioButton = By.xpath("(//*[@formcontrolname=\"hasVehicleInspected\"]//child::duet-choice)[2]");
	By DamageInspectionDescription_TextArea = By.xpath("//*[@formcontrolname=\"whereHasDamageVehBeenChecked\"]//child::input");
	By Next_Button = By.xpath("(//duet-button[contains(@class,'duet-m-0 duet-expand ') and contains(@class,'hydrated')])[1]");
	
	String vehicleDamageDescription = "return document.querySelector(\"duet-textarea[formcontrolname='vehicleDamageDescription']\").shadowRoot.querySelector(\"textarea\")";
	
	private String VehicleOperableYesRadioButton = "return document.querySelector('input[name=\"is-vehicle-operable\"][value=\"yes\"]')";
	private String DamagePriorToAccYesRadioButton = "return document.querySelector('input[name=\"is-damage-prior-to-acc\"][value=\"yes\"]')";
	private String VehicleInspectedYesRadioButton = "return document.querySelector('input[name=\"is-vehicle-inspected\"][value=\"yes\"]')";
	
	public VehicleDamagesInfo_Page vehicleDamagesInfoYes() throws InterruptedException {
		CF.clickRadioButton(VehicleOperableYes_RadioButton,VehicleOperableYesRadioButton,"VehicleOperable Yes RadioButton","RadioButton");
		CF.clickRadioButton(DamagePriorToAccYes_RadioButton,DamagePriorToAccYesRadioButton,"DamagePriorToAcc Yes RadioButton","RadioButton");	
		CF.enterValuesShadowElement(VehicleDamageDescription_TextArea_host, VehicleDamageDescription_TextArea_Shadow, vehicleDamageDescription,"VehicleDamageDescription TextArea", 255, "PASS");
		CF.clickRadioButton(VehicleInspectedYes_RadioButton,VehicleInspectedYesRadioButton,"VehicleOperable Yes RadioButton","RadioButton");	
		CF.enterValues(DamageInspectionDescription_TextArea, "DamageInspectionDescription TextArea", 100, "PASS");
		CF.clickOnElement(Next_Button, "Next", "Button", "PASS");
		return new VehicleDamagesInfo_Page(scriptHelper);
	}
	
	
}
