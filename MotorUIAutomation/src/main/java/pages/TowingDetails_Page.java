package pages;

import org.openqa.selenium.By;

import businesscomponents.CommonFunctions;
import supportlibraries.ScriptHelper;

public class TowingDetails_Page extends MasterPage{
	
	CommonFunctions CF = new CommonFunctions(scriptHelper);
	public TowingDetails_Page(ScriptHelper scriptHelper)
	{
		super(scriptHelper);	
	}
	
	By WereWasVehicleTowed_TextArea = By.xpath("(//*[@formcontrolname='whereWasVehicleTowed']//following::input)[1]");
	By AnyExpensesCausedYes_RadioButton = By.xpath("(//*[@name='anyExpensesCaused'])[1]");
	By NumberOfPeopleOptions_DropDown = By.cssSelector("#numberOfPeopleOptions");
	By JourneyBegin_TextArea = By.xpath("(//*[@formcontrolname='startingPointOfTrip']//following::input)[1]");
	By TravelDestination_TextArea = By.xpath("(//*[@formcontrolname='startingPointOfTrip']//following::input)[2]");
	By Next_Button = By.xpath("(//duet-button[contains(@class,'duet-m-0 duet-expand ') and contains(@class,'hydrated')])[1]");
	By TripInterruptionExpensesYes_RadioButton = By.xpath("(//*[@formcontrolname=\"wasVehicleTowedOrLifted\"]//duet-choice)[1]");
	By TripInterruptionExpensesNo_RadioButton = By.xpath("(//*[@formcontrolname=\"wasVehicleTowedOrLifted\"]//duet-choice)[2]");
	
	private String TripInterruptionExpensesYesRadioButton = "return document.querySelector('input[name=\"tripInterruptionExpenses\"][value=\"yes\"]')";
	private String AnyExpensesCausedYesRadioButton = "return document.querySelector('input[name=\"anyExpensesCaused\"][value=\"yes\"]')";
	private String TripInterruptionExpensesNoRadioButton = "return document.querySelector('input[name=\"tripInterruptionExpenses\"][value=\"no\"]')";
	
	public TowingDetails_Page TowedorLiftedYes() throws InterruptedException {
		CF.clickRadioButton(TripInterruptionExpensesYes_RadioButton, TripInterruptionExpensesYesRadioButton, "TripInterruptionExpensesYes", "RadioButton");
		CF.enterValues(WereWasVehicleTowed_TextArea, "WereWasVehicleTowed TextArea", 50, "PASS");
		//CF.clickOnElement(AnyExpensesCausedYes_RadioButton, "AnyExpensesCaused Yes", "RadioButton", "PASS");
		//WebElement AnyExpensesCausedYes = driver.findElement(AnyExpensesCausedYes_RadioButton);
		//AnyExpensesCausedYes.click();
		CF.mbClickShadow(AnyExpensesCausedYesRadioButton, "Any Expenses Caused Yes", "RadioButton", "PASS");
		Thread.sleep(1000);
		CF.selectDropDownByIndex(NumberOfPeopleOptions_DropDown, "NumberOfPeopleOptions DropDown", 1, "PASS");
		CF.enterValues(JourneyBegin_TextArea, "JourneyBegin TextArea", 50, "PASS");
		CF.enterValues(TravelDestination_TextArea, "TravelDestination TextArea", 50, "PASS");
		CF.scrollDown(Next_Button);
		CF.clickOnElement(Next_Button, "Next", "Button", "PASS");
		return new TowingDetails_Page(scriptHelper);
	}

}
