package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import businesscomponents.CommonFunctions;
import supportlibraries.ScriptHelper;

public class OwnVehicleDetails_Page extends  MasterPage{
	
	CommonFunctions CF = new CommonFunctions(scriptHelper);
	public OwnVehicleDetails_Page(ScriptHelper scriptHelper)
	{
		super(scriptHelper);	
	}
	
	private String DeductVATYesRadioButton = "return document.querySelector('input[name=\"deduct-VAT\"][value=\"yes\"]')";
	private String CompanyVehicleYesRadioButton = "return document.querySelector('input[name=\"is-company-vehicle\"][value=\"yes\"]')";
	private String LeasedVehicleYesRadioButton = "return document.querySelector('input[name=\"is-leased-vehicle\"][value=\"yes\"]')";
	private String DeductVATNoRadioButton = "return document.querySelector('input[name=\"deduct-VAT\"][value=\"no\"]')";
	private String CompanyVehicleNoRadioButton = "return document.querySelector('input[name=\"is-company-vehicle\"][value=\"no\"]')";
	private String LeasedVehicleNoRadioButton = "return document.querySelector('input[name=\"is-leased-vehicle\"][value=\"no\"]')";
	
	By DeductVATYes_RadioButton = By.xpath("(//*[@formcontrolname=\"deductVAT\"]//duet-choice)[1]");
	By CompanyVehicleYes_RadioButton = By.xpath("(//*[@formcontrolname=\"employeeBenefit\"]//duet-choice)[1]");
	By LeasedVehicleYes_RadioButton = By.xpath("(//*[@formcontrolname=\"leasedVehicle\"]//duet-choice)[1]");
	By DeductVATNo_RadioButton = By.xpath("(//*[@formcontrolname=\"deductVAT\"]//duet-choice)[2]");
	By CompanyVehicleNo_RadioButton = By.xpath("(//*[@formcontrolname=\"employeeBenefit\"]//duet-choice)[2]");
	By LeasedVehicleNo_RadioButton = By.xpath("(//*[@formcontrolname=\"leasedVehicle\"]//duet-choice)[2]");
	By FinanacLeasing_Button = By.cssSelector("duet-radio[value='financial']");
	By Next_Button = By.xpath("(//duet-button[contains(@class,'duet-m-0 duet-expand ') and contains(@class,'hydrated')])[1]");
	
	public OwnVehicleDetails_Page OwnVehicleDetailsYes() throws InterruptedException {
		CF.clickRadioButton(DeductVATYes_RadioButton, DeductVATYesRadioButton, "DeductVAT Yes RadioButton", "RadioButton");
		CF.clickRadioButton(CompanyVehicleYes_RadioButton, CompanyVehicleYesRadioButton, "CompanyVehicle Yes RadioButton", "RadioButton");
		CF.clickRadioButton(LeasedVehicleYes_RadioButton, LeasedVehicleYesRadioButton, "LeasedVehicle Yes RadioButton", "RadioButton");
		CF.scrollDown(Next_Button);
		CF.clickOnElement(Next_Button, "Next", "Button", "PASS");
		return new OwnVehicleDetails_Page(scriptHelper);
	}

}
