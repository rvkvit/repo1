package pages;

import org.openqa.selenium.By;
import businesscomponents.CommonFunctions;
import supportlibraries.ScriptHelper;

public class DamageType_Page extends MasterPage {


	CommonFunctions CF = new CommonFunctions(scriptHelper);

	public DamageType_Page(ScriptHelper scriptHelper)

	{
		super(scriptHelper);
	}

	/*
	 * Locator details
	 */

	By DescriptionOfDamage_RadioButton = By.cssSelector("#choiceGroup > duet-fieldset > div > div:nth-child(5) > div:nth-child(1)");
	By Next_Button = By.xpath("(//duet-button[contains(@class,'duet-m-0 duet-expand ') and contains(@class,'hydrated')])[1]");
	By AnimalType_Dropdown = By.cssSelector("#animal-type-hit-by");
	By HowDidDamageHappen_Dropdown = By.xpath("//*[@id=\"collisionWithVehicle\"]");
	By HowDidDamageHappenBicycle_Dropdown = By.xpath("//*[@id=\"collisionWithBicycle\"]");
	By DamageDescription_Textarea_host = By.cssSelector("duet-textarea");
	By DamageDescription_Textarea_shadow = By.cssSelector("textarea");
	By other = By.xpath("(//*[@id=\"choiceGroup\"]//child::duet-choice)[1]");
	By DidAnimalHitYes_RadioButton = By.cssSelector("[formcontrolname='didHitAnimal'] duet-choice[value='yes']");
	By DidAnimalHitNo_RadioButton = By.cssSelector("[formcontrolname='didHitAnimal'] duet-choice[value='no']");
	By DescriptionOfDamageAnimal_RadioButton = By.cssSelector("duet-choice[value='CollisionWith_Animal']");
	By AnimalOwnerKnownYes_RadioButton = By.xpath("(//*[@id=\"ownerAnimalChoice\"]//child::duet-choice)[1]");
	By DescriptionOfDamageBicycle_RadioButton = By.cssSelector("duet-choice[value='Cyclist_Elsewhere']");
	By HowDidDamageHappenPedestrian_Dropdown = By.id("collisionWithPedestrian");
	By Vehicleparkedrightside = By.cssSelector("duet-choice[value='Collision_VehParked_RightSide']");
	By Vehicleparkedleftside = By.cssSelector("duet-choice[value='Collision_VehParked_LeftSide']");
	By HowDidDamageHappencollisionOther_Dropdown = By.id("collisionOther");
	By DescriptionOfDamageother_RadioButton = By.xpath("//duet-choice[@value=\"ROR_CrossRoads\"]");
	By DescriptionOfDamagePedestrian_RadioButton = By.xpath("//duet-choice[@value=\"PedCrossing_Other_PedestrianAccident\"]");
	
	String DescriptionOfDamage = "return document.querySelector(\"input\")";
	String didAnimalHitYes = "return document.querySelector(\"input[name='did-animal-hit'][value='yes']\")";
	String didAnimalHitNo = "return document.querySelector(\"input[name='did-animal-hit'][value='yes']\")";
	String DamageDescription = "return document.querySelector(\"duet-textarea\").shadowRoot.querySelector(\"textarea\")";

	public DamageType_Page clickHowDidDamageHappen_Dropdown() throws InterruptedException {
		CF.selectDropDownByIndex(HowDidDamageHappen_Dropdown, "HowDidDamageHappen Dropdown", 1, "PASS");
		return new DamageType_Page(scriptHelper);
	}
	
	public DamageType_Page clickDescriptionOfDamage_RadioButton() throws InterruptedException {
		CF.clickRadioButton(DescriptionOfDamage_RadioButton, DescriptionOfDamage, "DescriptionOfDamage", "RadioButton");
		return new DamageType_Page(scriptHelper);
	}
	
	public DamageType_Page enterDamageDescription_Textarea() throws InterruptedException {
		CF.enterValuesShadowElement(DamageDescription_Textarea_host, DamageDescription_Textarea_shadow, DamageDescription,"DescriptionOfDamage TextArea", 1000, "PASS");
		CF.clickOnElement(Next_Button, "Next", "Button", "PASS");
		return new DamageType_Page(scriptHelper);
	}
}
