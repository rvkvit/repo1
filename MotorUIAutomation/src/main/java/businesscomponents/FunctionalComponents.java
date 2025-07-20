package businesscomponents;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.io.IOException;

import com.LT.framework.Status;
import com.LT.framework.selenium.WebDriverUtil;

import pages.AdditionalInformation_Page;
import pages.Attachments_Page;

import pages.DamageConditions_Page;
import pages.DamageType_Page;
import pages.GlassBreakage_Page;

import pages.LT_BankPortal_Page;

import pages.LT_FilingAClaim_Page;
import pages.LT_Home_Page;
import pages.LT_NordeaBankPortalSignIn_Page;
import pages.LT_ReleaseFlagSelection_Page;
import pages.LT_SSN_Page;
import pages.LT_VehicleList_Page;
import pages.LocationDetails_Page;
import pages.LossCause_Page;
import pages.LossLocation_Page;
import pages.LossTypeSelectionPage;
import pages.OtherDamageConditions_Page;
import pages.OtherVehicleDamages_Page;
import pages.OtherVehicleProperty_Page;
import pages.OwnPropertyDamages_Page;
import pages.OwnVehicleDetails_Page;

import pages.PartyInformation_Page;
import pages.PersonalInjuries_Page;
import pages.Summary_Page;
import pages.ThankYou_Page;
import pages.TowingDetails_Page;
import pages.TripInterruptionExpenses_Page;
import pages.VehicleDamagesInfo_Page;
import pages.VehicleDamages_Page;
import supportlibraries.DriverScript;
//import bsh.util.Util;
import supportlibraries.ReusableLibrary;
import supportlibraries.ScriptHelper;

/**
 * Functional Components class
 * 
 * @author LT TCS
 */
public class FunctionalComponents extends ReusableLibrary {
	private WebDriverUtil driverUtil;
	CommonFunctions CF = new CommonFunctions(scriptHelper);

	/**
	 * Constructor to initialize the component library
	 * 
	 * @param scriptHelper The {@link ScriptHelper} object passed from the
	 *                     {@link DriverScript}
	 */
	public FunctionalComponents(ScriptHelper scriptHelper) {
		super(scriptHelper);
		driverUtil = new WebDriverUtil(driver);
	}

	WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
	WebDriverWait wait1 = new WebDriverWait(driver, Duration.ofSeconds(20));

	public String errorMessage;
	public String strMessage;
	public String strCode;
	public String strAppend;
	public String strBatchName;
	public String InvoiceNum;

	// driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	
	public void invokeApplication() {
		String strEnv = properties.getProperty("Environment");
		String strSITURL = dataTable.getData("General_Data", "SIT URL");
		String strUATURL = dataTable.getData("General_Data", "UAT URL");
		String url = strEnv.contains("SIT") ? strSITURL : strUATURL;
		driver.get(url);
		report.updateTestLog("URL :" , url + " is launched successfully", Status.PASS);
	}

	public void selectReleaseFlag() throws InterruptedException {
		
		LT_ReleaseFlagSelection_Page RF = new LT_ReleaseFlagSelection_Page(scriptHelper);
		RF.SelectReleaseFlag();
		RF.SaveSelection();

	}

	public void selectBankPortalAndLogin() throws InterruptedException {
		LT_BankPortal_Page BP = new LT_BankPortal_Page(scriptHelper);
		BP.SelectBankPortal();
		LT_NordeaBankPortalSignIn_Page NBPS = new LT_NordeaBankPortalSignIn_Page(scriptHelper);
		NBPS.LoginToNordeaPortal();
		LT_SSN_Page SP = new LT_SSN_Page(scriptHelper);
		SP.EnterSSNonLT();

	}

	public void verifyHomePageAndAccessCompensationAndDamageMatters() throws InterruptedException {

		LT_Home_Page HP = new LT_Home_Page(scriptHelper);
		
		try {
			wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.id("onetrust-accept-btn-handler"))));
			driver.findElement(By.id("onetrust-accept-btn-handler")).click();

		} catch (Exception e) {
			System.out.println("");
		}
		
		HP.VerifyHomePage();

		try {
			wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.id("onetrust-accept-btn-handler"))));
			driver.findElement(By.id("onetrust-accept-btn-handler")).click();

		} catch (Exception e) {
			System.out.println("");
		}

		HP.LanguageSelection();
		HP.clickOnSpecificTabAvailableOnHomePage("Compensation and Damage Matters");

	}

	public void fillAClaim() throws InterruptedException {
		LT_FilingAClaim_Page FC = new LT_FilingAClaim_Page(scriptHelper);
		FC.ClickOnApplyForCompensation();
		FC.SelectDamageTypeAndMoveNext();
		FC.SelectTimeOfTheInjuryAndMoveNext("The damage was done");
		// FC.SelectVechileTypeAndMoveNext("ABC-123");
	}

	/*
	 * 
	 * MFNOL
	 * 
	 */

	public void gb_LT() throws InterruptedException {
	    LT_VehicleList_Page VL = new LT_VehicleList_Page(scriptHelper);
	    LossTypeSelectionPage LTS = new LossTypeSelectionPage(scriptHelper);
	    GlassBreakage_Page GB = new GlassBreakage_Page(scriptHelper);
	    String strRole = dataTable.getData("General_Data", "Role");
	    // Select the vehicle type based on the input argument
	    switch (strRole) {
	        case "Driver":
	            VL.SelectVechileTypeAndMoveNext_Driver();
	            break;
	        case "PH":
	            VL.SelectVechileTypeAndMoveNext_PH();
	            break;
	        case "Owner":
	            VL.SelectVechileTypeAndMoveNext_Owner();
	            break;
	        default:
	            throw new IllegalArgumentException("Invalid vehicle type: " + strRole);
	    }

	    // Proceed with loss type selection and glass breakage handling
	    LTS.selectLossTypeAndProceed("GlassBreakage");
	    GB.LossTypeSelection_LT_GB();
	}

	public void collision_OV_PH_Yes() throws InterruptedException {

		LT_VehicleList_Page VL = new LT_VehicleList_Page(scriptHelper);
		LossTypeSelectionPage LTS = new LossTypeSelectionPage(scriptHelper);
		LossCause_Page LC = new LossCause_Page(scriptHelper);
		DamageType_Page DT = new DamageType_Page(scriptHelper);
		AdditionalInformation_Page AI = new AdditionalInformation_Page(scriptHelper);
		LossLocation_Page LL = new LossLocation_Page(scriptHelper);
		LocationDetails_Page LD = new LocationDetails_Page(scriptHelper);
		VehicleDamages_Page VD = new VehicleDamages_Page(scriptHelper);
		VehicleDamagesInfo_Page VDI = new VehicleDamagesInfo_Page(scriptHelper);
		OwnVehicleDetails_Page OV = new OwnVehicleDetails_Page(scriptHelper);
		DamageConditions_Page DC = new DamageConditions_Page(scriptHelper);
		OtherVehicleProperty_Page OVP = new OtherVehicleProperty_Page(scriptHelper);
		OtherVehicleDamages_Page OVD = new OtherVehicleDamages_Page(scriptHelper);
		OtherDamageConditions_Page ODC = new OtherDamageConditions_Page(scriptHelper);
		OwnPropertyDamages_Page OPD = new OwnPropertyDamages_Page(scriptHelper);
		PartyInformation_Page PI = new PartyInformation_Page(scriptHelper);
		PersonalInjuries_Page PerInj = new PersonalInjuries_Page(scriptHelper);
		TowingDetails_Page TD = new TowingDetails_Page(scriptHelper);
		TripInterruptionExpenses_Page TI = new TripInterruptionExpenses_Page(scriptHelper);
		Attachments_Page AP = new Attachments_Page(scriptHelper);
		Summary_Page SP = new Summary_Page(scriptHelper);
		ThankYou_Page TP = new ThankYou_Page(scriptHelper);
		VL.SelectVechileTypeAndMoveNext_PH();
		LTS.selectLossTypeAndProceed("Collision");
		LC.LossCauseOV();
		DT.clickHowDidDamageHappen_Dropdown();
		DT.clickDescriptionOfDamage_RadioButton();
		DT.enterDamageDescription_Textarea();
		AI.clickTypeOfInfluence_Dropdown();
		AI.clickRescueVisitionOptionYes_RadioButton();
		AI.clickPoliceVisitYes_RadioButton();
		AI.clickComplaintFiledYes_RadioButton();
		AI.clickNext_RadioButton();
		LL.SelectLossLocationAndMoveNext();
		LD.clickDamageSite_Dropdown();
		LD.clickPopulationDensityYES_RadioButton();
		LD.clickRoadSurface_Dropdown();
		LD.clickTrafficLight_Dropdown();
		LD.clickRoadLighting_Dropdown();
		VD.VehicleDamagedYes();
		VDI.vehicleDamagesInfoYes();
		OV.OwnVehicleDetailsYes();
		DC.DamageConditionYes();
		OVP.addOtherVehicle();
		Thread.sleep(5000);
		OVP.addOtherProperty1();
		Thread.sleep(5000);
		OVP.clicknext();
		OVD.OtherVehicleDamagedYes();
		ODC.OtherDamageConditionYes();
		OPD.AddPropertyDamagesReal();
		PI.PartyInformation();
		PerInj.AddInjury();
		TD.TowedorLiftedYes();
		TI.TripInterruptionExpenses();
		AP.AddAttachments();
		SP.FinalSubmit();
		TP.ThankYouPageConfirm();
	}
}