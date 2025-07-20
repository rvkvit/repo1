package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import businesscomponents.CommonFunctions;
import supportlibraries.ScriptHelper;

public class LocationDetails_Page extends MasterPage{

	CommonFunctions CF = new CommonFunctions(scriptHelper);
	public LocationDetails_Page(ScriptHelper scriptHelper)

	{
		super(scriptHelper);
	}
	
	/*
	 * Locator details
	 */
	By DamageSite_Dropdown = By.cssSelector("#damageSite");
	By RoadSurface_Dropdown = By.cssSelector("#roadSurface");
	By TrafficLight_Dropdown = By.cssSelector("#trafficLight");
	By RoadLighting_Dropdown = By.cssSelector("#roadLighting");
	By Next_Button = By.xpath("(//duet-button[contains(@class,'duet-m-0 duet-expand ') and contains(@class,'hydrated')])[1]");
	By WhichRoadDidAccOccur_Dropdown = By.xpath("//*[@id=\"typeOfRoad\"]");
	By RoadType_Dropdown = By.cssSelector("#typeOfRoad");
	By SpeedBeforeAcc_Dropdown = By.cssSelector("#speed-before-accident");
	By PopulationDensityYES_RadioButton = By.xpath("(//*[@formcontrolname=\"populationDensity\"]//child::duet-choice)[1]");
	By PopulationDensityNo_RadioButton = By.xpath("(//*[@formcontrolname=\"populationDensity\"]//child::duet-choice)[2]");
	By wasTrailerUsedNo = By.xpath("(//*[@formcontrolname=\"wasTrailerUsed\"]//child::duet-choice)[2]");
	
	String PopulationDensityYESRadioButton = "return document.querySelector('input[name=\"populationDensity\"][value=\"Yes\"]')";
	String wasTrailerUsedNoRadioButton = "return document.querySelector('input[name=\"was-trailer-used\"][value=\"no\"]')";
	
	public LocationDetails_Page clickDamageSite_Dropdown() throws InterruptedException {
		CF.selectDropDownByIndex(DamageSite_Dropdown, "DamageSite Dropdown", 1, "PASS");
		return new LocationDetails_Page(scriptHelper);
	}
	
	public LocationDetails_Page clickPopulationDensityYES_RadioButton() throws InterruptedException {
		CF.clickRadioButton(PopulationDensityYES_RadioButton, PopulationDensityYESRadioButton, "PopulationDensity YES", "RadioButton");	
		return new LocationDetails_Page(scriptHelper);
	}
	
	public LocationDetails_Page clickRoadSurface_Dropdown() throws InterruptedException {
		CF.selectDropDownByIndex(RoadSurface_Dropdown, "RoadSurface Dropdown", 1, "PASS");
		return new LocationDetails_Page(scriptHelper);
	}
	
	public LocationDetails_Page clickTrafficLight_Dropdown() throws InterruptedException {
		CF.selectDropDownByIndex(TrafficLight_Dropdown, "TrafficLight Dropdown", 1, "PASS");
		return new LocationDetails_Page(scriptHelper);
	}
	
	public LocationDetails_Page clickRoadLighting_Dropdown() throws InterruptedException {
		CF.selectDropDownByIndex(RoadLighting_Dropdown, "RoadLighting Dropdown", 1, "PASS");
		CF.clickOnElement(Next_Button, "Next", "Button", "PASS");
		return new LocationDetails_Page(scriptHelper);
	}
	
}
