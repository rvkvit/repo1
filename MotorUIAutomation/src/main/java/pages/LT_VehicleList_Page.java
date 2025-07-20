package pages;

import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import businesscomponents.CommonFunctions;
import supportlibraries.ScriptHelper;

public class LT_VehicleList_Page extends MasterPage {

    CommonFunctions CF = new CommonFunctions(scriptHelper);

    public LT_VehicleList_Page(ScriptHelper scriptHelper) {
        super(scriptHelper);
    }

    String strPolicyNo = dataTable.getData("General_Data", "Policy Number");
    String strPolicyNoUAT = dataTable.getData("General_Data", "Policy Number UAT");
    String strRegNo = dataTable.getData("General_Data", "Reg Number");
    String strRegNoUAT = dataTable.getData("General_Data", "Reg Number UAT");
    String strEnv = properties.getProperty("Environment");

    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(60));
    WebDriverWait shortWait = new WebDriverWait(driver, Duration.ofSeconds(10));

    /*
     * Locator details
     */
    By SelectVehicleTypeUAT_RadioButton = By.xpath(".//*[@data-policy-number='Policy Number']".replace("Policy Number", strPolicyNoUAT));
    By SelectVehicleType_RadioButton = By.xpath(".//*[@data-policy-number='Policy Number']".replace("Policy Number", strPolicyNo));
    By Seuraava_Button = By.xpath("(//ss-vehicle-list//duet-button)[5]");
    By SelectRoleDriver_Radiobutton = By.cssSelector("#roleChoiceGroup > duet-fieldset > div > duet-choice:nth-child(2)");
    By SearchRoleDriver_Button = By.xpath("(//*[@id='otherVehicleInput']/div/div[2]/div/duet-button)[2]");
	By SelectRoleThirdParty_Radiobutton = By.cssSelector("#roleChoiceGroup > duet-fieldset > div > duet-choice:nth-child(1)");
	By SelectRoleThirdParty_TextBox = By.cssSelector("#otherVehicleInput > div > div.duet-input-relative.sc-duet-input.sc-duet-input-s>input");
	By SearchRoleThirdParty_Button = By.cssSelector("#otherVehicleInput > div > div.duet-input-relative.sc-duet-input.sc-duet-input-s > div > duet-button");
	By SelectRoleOwner_Radiobutton = By.cssSelector("#roleChoiceGroup > duet-fieldset > div > duet-choice:nth-child(3)");
	By SearchRoleOwner_Button = By.xpath("(//*[@id='otherVehicleInput']/div/div[2]/div/duet-button)[3]");
    By SelectRoleDriver_TextBox = By.xpath("(//*[@id=\"otherVehicleInput\"]//child::input)[2]");
    By SelectRoleOwner_TextBox = By.xpath("(//*[@id=\"otherVehicleInput\"]//input)[3]");
    By DriverPolicyCard = By.xpath("(//*[@id=\"otherVehicleChoice\"])[2]");
    By OwnerPolicyCard = By.xpath("(//*[@id=\"otherVehicleChoice\"])[3]");
    By thirdPartyPolicyCard = By.xpath("(//*[@id=\"otherVehicleChoice\"])[1]");
    
    String SelectVehicleTypeRadioButton = ("return document.querySelector('[data-policy-number=\"Policy Number\"] input');".replace("Policy Number", strPolicyNo));
    String SelectVehicleTypeUATRadioButton = ("return document.querySelector('[data-policy-number=\"Policy Number\"] input');".replace("Policy Number", strPolicyNoUAT));
    String SelectRoleDriverRadiobutton = "return document.querySelector(\"input[value='damageNotOwnedCar']\")";
    String SelectRoleThirdPartyRadiobutton = "return document.querySelector(\"input[value='otherVehicle']\")";
    String SelectRoleOwnerRadiobutton = "return document.querySelector(\"input[value='damageNotOwnedCar']\")";
     
    // Helper Method to wait for visibility and invisibility
    private void waitForElementVisibilityAndInvisibility(By locator) {
        try {
            shortWait.until(ExpectedConditions.visibilityOfElementLocated(locator));
            wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
        } catch (Exception e) {
            // Handle Exception if needed
        }
    }

    // Generic Method to handle role-based vehicle selection
    private LT_VehicleList_Page selectRoleAndMoveNext(By roleRadioButton, By roleTextBox, By searchButton, String regNumber, String roleName, String element,By policycard) throws InterruptedException {
        waitForElementVisibilityAndInvisibility(By.xpath(".//*[@class='content-without-background']"));
        
        CF.clickRadioButton(roleRadioButton, element,roleName , "RadioButton");

        String regNo = strEnv.contains("SIT") ? strRegNo : strRegNoUAT;
        String regLabel = strEnv.contains("SIT") ? "Reg Number" : "Reg Number UAT";
        CF.enterValuesInTextBox(roleTextBox, regLabel, regNo, "PASS");

        CF.clickOnElement(searchButton, "Vehicle Search", "Button", "PASS");
        wait.until(ExpectedConditions.visibilityOfElementLocated(policycard));
        
        CF.scrollDown(Seuraava_Button);
        wait.until(ExpectedConditions.elementToBeClickable(Seuraava_Button));
        CF.clickOnElement(Seuraava_Button, "Next", "Button", "PASS");

        return new LT_VehicleList_Page(scriptHelper);
    }

    // Simplified Methods for each specific case
    public LT_VehicleList_Page SelectVechileTypeAndMoveNext_PH() throws InterruptedException {
        By selectedRadioButton = strEnv.contains("SIT") ? SelectVehicleType_RadioButton : SelectVehicleTypeUAT_RadioButton;
        String selectVehicleTypeRadioButton = strEnv.contains("SIT") ? SelectVehicleTypeRadioButton : SelectVehicleTypeUATRadioButton;
        CF.clickRadioButton(selectedRadioButton ,selectVehicleTypeRadioButton ,"Vehicle Type","Radio Button" );
        CF.scrollDown(Seuraava_Button);
        CF.clickOnElement(Seuraava_Button, "Next", "Button", "PASS");

        return new LT_VehicleList_Page(scriptHelper);
    }

    public LT_VehicleList_Page SelectVechileTypeAndMoveNext_Driver() throws InterruptedException {
        return selectRoleAndMoveNext(SelectRoleDriver_Radiobutton, SelectRoleDriver_TextBox, SearchRoleDriver_Button, strRegNo, "Driver", SelectRoleDriverRadiobutton, DriverPolicyCard);
    }

    public LT_VehicleList_Page SelectVechileTypeAndMoveNext_ThirdParty() throws InterruptedException {
        return selectRoleAndMoveNext(SelectRoleThirdParty_Radiobutton, SelectRoleThirdParty_TextBox, SearchRoleThirdParty_Button, strRegNo, "ThirdParty",SelectRoleThirdPartyRadiobutton, thirdPartyPolicyCard);
    }

    public LT_VehicleList_Page SelectVechileTypeAndMoveNext_Owner() throws InterruptedException {
        return selectRoleAndMoveNext(SelectRoleOwner_Radiobutton, SelectRoleOwner_TextBox, SearchRoleOwner_Button, strRegNo, "Owner",SelectRoleOwnerRadiobutton, OwnerPolicyCard);
    }
}
