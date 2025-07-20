package pages;

import java.time.Duration;
import java.util.concurrent.ThreadLocalRandom;

import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import businesscomponents.CommonFunctions;
import supportlibraries.ScriptHelper;

public class OtherVehicleProperty_Page extends  MasterPage{
	
	CommonFunctions CF = new CommonFunctions(scriptHelper);
	int randomNum = ThreadLocalRandom.current().nextInt(1, 247 + 1);
	String index =  Integer.toString(randomNum);
	public OtherVehicleProperty_Page(ScriptHelper scriptHelper)
	{
		super(scriptHelper);	
	}
	
	WebDriverWait wait = new WebDriverWait(driver, Duration.ofMinutes(2));
	WebDriverWait wait1 = new WebDriverWait(driver, Duration.ofSeconds(5));
	
	String strVehNo = dataTable.getData("General_Data", "Vehicle Number");
	String strRandAlpha = RandomStringUtils.randomAlphabetic(1);
	
	By AddOtherPartyVehicle_Button = By.cssSelector("#main-layout > div > duet-layout > div > lt-cel-platform-feature-vehicle-fnol > duet-card > lt-cel-platform-third-party-vehicle-details-damages > div > duet-button:nth-child(9)");
	By registerNumberInput_Textarea = By.cssSelector("#registerNumberInput > div > div.duet-input-relative.sc-duet-input.sc-duet-input-s>input");
	By Save_Button = By.xpath("(//*[@id='addPartVehicleModal']//following::duet-button)[1]");
	By AddOtherPartyProperty_Button = By.cssSelector("#main-layout > div > duet-layout > div > lt-cel-platform-feature-vehicle-fnol > duet-card > lt-cel-platform-third-party-vehicle-details-damages > div > duet-button:nth-child(15)");
	By AddOtherPartyProperty1_Button = By.cssSelector("#main-layout > div > duet-layout > div > lt-cel-platform-feature-vehicle-fnol > duet-card > lt-cel-platform-third-party-vehicle-details-damages > div > duet-button:nth-child(14)");
	By DamagedPropertyName_Textarea = By.xpath("(//*[@id='addDetailsModal']//following::input)[1]");
	By SaveAddProperty_Button = By.xpath("(//*[@id='addDetailsModal']//following::duet-button)[1]");
	By Next_Button = By.xpath("//*[@class=\"duet-m-0 duet-expand primary hydrated\"]");
	By Modal_id = By.cssSelector("#addPartVehicleModal");
	By VehicleNotRegisteredinFinland_RadioButton = By.xpath("//*[@name='isRegisteredInFinland' and @value='No']");
	By CountryOfRegisteration_Textbox = By.xpath("(//*[@formcontrolname='country']//following::input)[1]");
	By CountryOfRegisteration_Dropdown = By.xpath("//*[@role=\"listbox\"]/li[2]");
	By RegID_Textarea = By.xpath("(//*[@formcontrolname='country']//following::input)[2]");
	By VehicleModal_Textarea = By.xpath("(//*[@formcontrolname='country']//following::input)[4]");
	By VehicleMake_Textarea = By.xpath("(//*[@formcontrolname='country']//following::input)[3]");
	By DamageDescription_Textarea_host = By.cssSelector("duet-textarea[formcontrolname='damageDescription']");
	By DamageDescription_Textarea_shadow = By.cssSelector("textarea");
	
	String damageDescription = "return document.querySelector(\"duet-textarea[formcontrolname='damageDescription']\").shadowRoot.querySelector(\"textarea\")";
	
	public OtherVehicleProperty_Page addOtherVehicle() throws InterruptedException {
		
		CF.clickOnElement(AddOtherPartyVehicle_Button, "Add Other Party Vehicle Button", "Button", "PASS");
		Thread.sleep(1000);
		CF.scrollDown(registerNumberInput_Textarea);
		CF.enterValuesInTextBox(registerNumberInput_Textarea, "Reg Number", strVehNo,"PASS");
		Thread.sleep(1000);
		CF.scrollDown(Save_Button);
		CF.clickOnElement(Save_Button, "Save vehicle details", "Button", "PASS");
		return new OtherVehicleProperty_Page(scriptHelper);
	}
	
	public OtherVehicleProperty_Page addOtherProperty1() throws InterruptedException {

		CF.clickOnElement(AddOtherPartyProperty1_Button, "Add Other Party Property Button", "Button", "PASS");
		CF.enterValues(DamagedPropertyName_Textarea, "DamagedPropertyName TextArea", 100, "PASS");
		CF.enterValuesShadowElement(DamageDescription_Textarea_host, DamageDescription_Textarea_shadow,damageDescription,"DescriptionOfDamage TextArea", 200, "PASS");
		CF.scrollDown(SaveAddProperty_Button);
		CF.clickOnElement(SaveAddProperty_Button, "Save vehicle details", "Button", "PASS");
		return new OtherVehicleProperty_Page(scriptHelper);
	}

	public OtherVehicleProperty_Page clicknext() throws InterruptedException {
		Thread.sleep(1000);
		CF.clickOnElement(Next_Button, "Next", "Button", "PASS");
		return new OtherVehicleProperty_Page(scriptHelper);
	}
}
