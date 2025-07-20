package pages;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.WebDriverWait;

import businesscomponents.CommonFunctions;
import supportlibraries.ScriptHelper;

public class LossCause_Page extends  MasterPage{
	
	CommonFunctions CF = new CommonFunctions(scriptHelper);
	public LossCause_Page(ScriptHelper scriptHelper)
	{
		super(scriptHelper);	
	}
	WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

	/*
	 * Locator details
	 */
	
	By OV_Radiobutton = By.xpath("//*[@data-testid=\"vfnol-collision-loss-choice-CollisionWithAnotherVehicle_LT\"]");
	By Animal_Radiobutton = By.xpath("//*[@data-testid=\"vfnol-collision-loss-choice-animalcollision\"]");
	By Bicycle_Radiobutton = By.xpath("//*[@data-testid=\"vfnol-collision-loss-choice-bikecollision\"]");
	By Somethingelse_Radiobutton = By.xpath("//*[@data-testid=\"vfnol-collision-loss-choice-Collision_LT\"]");
	By Next_Button = By.xpath("//*[@data-testid=\"vfnol-collision-loss-button-next\"]");
	By What_Cause_Damage = By.cssSelector("select#naturalPhenomenonDamageType");
	By Other_Reason = By.xpath("//*[@id='otherDamageReason']");
	By Had_Vehicle_Stopped = By.xpath("(//*[@formcontrolname=\"vehicleParkingStatus\"]//child::duet-choice)[1]");
	By More_Details_host = By.cssSelector("#damageDescription");
	By EngineStatus_Yes =By.xpath("(//*[@formcontrolname=\"engineStatus\"]//child::duet-choice)[1]");
	By Pedestrian_Radiobutton = By.xpath("//*[@data-testid=\"vfnol-collision-loss-choice-pedcollision\"]");
	By Next_button_lossDetails = By.xpath("//*[@data-testid=\"vfnol-loss-details-next-button\"]");
	
	String CollisionWithAnotherVehicle = "return document.querySelector(\"input[value='CollisionWithAnotherVehicle_LT']\")";
	String animalcollision = "return document.querySelector(\"input[value='animalcollision']\")";
	String pedcollision = "return document.querySelector(\"input[value='pedcollision']\")";
	String bikecollision = "return document.querySelector(\"input[value='bikecollision']\")";
	String Collision_LT = "return document.querySelector(\"input[value='Collision_LT']\")";
	String vehicleParkStatusYes = "return document.querySelector(\"input[name='vehicleParkStatus'][value='Yes']\")";
	String engineStatusDataYes = "return document.querySelector(\"input[name='engineStatusData'][value='Yes']\")";
	String damageDescription = "return document.querySelector(\"#damageDescription\").shadowRoot.querySelector(\"#damageDescription\")";
		
	private LossCause_Page selectLossCauseAndProceed(String jsSelector, By radioButton) throws InterruptedException {
		if (CF.isSafari()) {
			CF.mbClickShadow(jsSelector, "LossCauseSelection", "RadioButton", "PASS");
		} else {
			CF.clickOnElement(radioButton, "LossCauseSelection", "RadioButton", "PASS");
		}
		CF.clickOnElement(Next_Button, "Next", "Button", "PASS");
		return new LossCause_Page(scriptHelper);
	}

	// Methods for each specific loss cause
	public LossCause_Page LossCauseOV() throws InterruptedException {
		return selectLossCauseAndProceed(CollisionWithAnotherVehicle, OV_Radiobutton);
	}

}
