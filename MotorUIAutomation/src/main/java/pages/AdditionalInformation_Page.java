package pages;
import org.openqa.selenium.By;
import businesscomponents.CommonFunctions;
import supportlibraries.ScriptHelper;

public class AdditionalInformation_Page extends MasterPage {
    
    CommonFunctions CF = new CommonFunctions(scriptHelper);

    public AdditionalInformation_Page(ScriptHelper scriptHelper) {
        super(scriptHelper);
    }

    /*
     * Locator details
     */
    
    By TypeOfInfluence_Dropdown = By.cssSelector("#typeOfInfluence");
    By Next_Button = By.xpath("//*[@class=\"duet-m-0 duet-expand primary hydrated\"]");
    By RescueVisitionOptionYes_RadioButton = By.xpath("(//*[@formcontrolname=\"rescueVisitionOptions\"]//duet-choice)[1]");
    By RescueVisitionOptionNo_RadioButton = By.xpath("(//*[@formcontrolname=\"rescueVisitionOptions\"]//duet-choice)[2]");
    By PoliceVisitYes_RadioButton = By.xpath("(//*[@formcontrolname=\"didPoliceVisited\"]//duet-choice)[1]");
    By PoliceVisitNo_RadioButton = By.xpath("(//*[@formcontrolname=\"didPoliceVisited\"]//duet-choice)[2]");
    By ComplaintFiledNo_RadioButton = By.xpath("(//*[@formcontrolname=\"criminalComplaintFiled\"]//duet-choice)[2]");
    By ComplaintFiledYes_RadioButton = By.xpath("(//*[@formcontrolname=\"criminalComplaintFiled\"]//duet-choice)[1]");

    // JavaScript code for shadow DOM elements
    private String rescueVisitionOptions_yes = "return document.querySelector('input[name=\"rescueVisitionOptions\"][value=\"Yes\"]')";
    private String PoliceVisitYes = "return document.querySelector('input[name=\"did-police-visited\"][value=\"Yes\"]')";
    private String ComplaintFiledYes = "return document.querySelector('input[name=\"criminal-complaint\"][value=\"Yes\"]')";
   

    // Refactored method for selecting options from a dropdown
    public AdditionalInformation_Page clickTypeOfInfluence_Dropdown() throws InterruptedException {
        CF.selectDropDownByIndex(TypeOfInfluence_Dropdown, "TypeOfInfluence Dropdown", 1, "PASS");
        return this;
    }

    // Method to handle Rescue Visit options
    public AdditionalInformation_Page clickRescueVisitionOptionYes_RadioButton() throws InterruptedException {
        CF.clickRadioButton(RescueVisitionOptionYes_RadioButton, rescueVisitionOptions_yes, "RescueVisitionOption Yes", "RadioButton");
        return this;
    }

    // Method to handle Police Visit options
    public AdditionalInformation_Page clickPoliceVisitYes_RadioButton() throws InterruptedException {
        CF.clickRadioButton(PoliceVisitYes_RadioButton, PoliceVisitYes, "PoliceVisit Yes", "RadioButton");
        return this;
    }
    
    // Method to handle Complaint Filed options
    public AdditionalInformation_Page clickComplaintFiledYes_RadioButton() throws InterruptedException {
        CF.clickRadioButton(ComplaintFiledYes_RadioButton, ComplaintFiledYes, "ComplaintFiled Yes", "RadioButton");
        return this;
    }

    // Next button click method
    public AdditionalInformation_Page clickNext_RadioButton() throws InterruptedException {
        CF.clickOnElement(Next_Button, "Next", "RadioButton", "PASS");
        return this;
    }
}
