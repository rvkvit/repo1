package pages;

import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.By;

import businesscomponents.CommonFunctions;
import supportlibraries.ScriptHelper;

public class TripInterruptionExpenses_Page extends MasterPage{
	
	CommonFunctions CF = new CommonFunctions(scriptHelper);
	public TripInterruptionExpenses_Page(ScriptHelper scriptHelper)
	{
		super(scriptHelper);	
	}
	String amountOfCompensation = RandomStringUtils.randomNumeric(4);
	
	By AddExpenses_Button = By.cssSelector("#main-layout > div > duet-layout > div > lt-cel-platform-feature-vehicle-fnol > duet-card > lt-cel-platform-trip-interruption-expenses > duet-button");
	By ExpenseType_DropDown = By.cssSelector("#expenseType");
	By AmountOfCompensation_Textarea = By.xpath("//*[@formcontrolname='compensationAmount']//following::input");
	By Save_Button = By.cssSelector("body > duet-modal > duet-grid > duet-button:nth-child(1)");
	By Next_Button = By.xpath("(//duet-button[contains(@class,'duet-m-0 duet-expand ') and contains(@class,'hydrated')])[1]");
	By CostDescription_Textarea_Host = By.cssSelector("duet-textarea");
	By CostDescription_Textarea_Shadow = By.cssSelector("textarea");
	
	String costDescription = "return document.querySelector(\"duet-textarea\").shadowRoot.querySelector(\"textarea\")";
	
	public TripInterruptionExpenses_Page TripInterruptionExpenses() throws InterruptedException {
		CF.clickOnElement(AddExpenses_Button, "AddExpenses", "Button", "PASS");
		CF.selectDropDownByIndex(ExpenseType_DropDown, "ExpenseType DropDown", 1, "PASS");
		Thread.sleep(1000);	
		CF.enterValuesInTextBox(AmountOfCompensation_Textarea, "AmountOfCompensation TextArea", amountOfCompensation, "PASS");
		CF.scrollDown(Save_Button);
		CF.enterValuesShadowElement(CostDescription_Textarea_Host, CostDescription_Textarea_Shadow, costDescription,"TravelDestination TextArea", 255, "PASS");
		CF.scrollDown(Save_Button);
		CF.clickOnElement(Save_Button, "Save", "Button", "PASS");
		CF.scrollDown(Next_Button);
		CF.clickOnElement(Next_Button, "Next", "Button", "PASS");
		return new TripInterruptionExpenses_Page(scriptHelper);
	}
	
}
