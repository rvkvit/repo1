package pages;

import java.time.Duration;
import java.util.concurrent.ThreadLocalRandom;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import businesscomponents.CommonFunctions;
import supportlibraries.ScriptHelper;

public class LossLocation_Page extends MasterPage {

	CommonFunctions CF = new CommonFunctions(scriptHelper);
	int randomNum = ThreadLocalRandom.current().nextInt(1, 310 + 1);
	String index = Integer.toString(randomNum);

	public LossLocation_Page(ScriptHelper scriptHelper)

	{
		super(scriptHelper);
	}

	WebDriverWait wait = new WebDriverWait(driver, Duration.ofMinutes(2));
	WebDriverWait wait1 = new WebDriverWait(driver, Duration.ofSeconds(5));

	/*
	 * Locator details
	 */
	By Shadow_Next_FormFill = By.xpath("//*[@data-testid=\"vfnol-loss-location-tab-group\"]");
	By FormFill_Button = By.cssSelector("button:nth-child(2)");
	
	By municipalityList_Dropdown = By.xpath("//*[@role=\"listbox\"]/li[2]".replace("2", index));
	By Next_Button = By.xpath("//*[@data-testid=\"vfnol-next-cancel-wrapper-next-button\"]");
	By municipalityList_ComboBox = By.xpath("//*[@id=\"municipalityListComboBox\"]//child::input");
	By Moredetails = (By.xpath("//*[@id=\"locationAdditionalInfo\" and @class=\"duet-textarea sc-duet-textarea\"]"));
	By addressOrOtherSpecifier_textarea = By.cssSelector("#addressOrOtherSpecifier");
	By LocationAdditionalInfo_Textarea = By.cssSelector("#locationAdditionalInfo");
	//By PlaceOfDamage = By.cssSelector("div > div.duet-m-0.duet-tab-group-tabs.duet-tab-group-collapses>button:nth-child(2)");
	//By PlaceOfDamage_Dropdown = (By.xpath("//*[@formcontrolname=\"country\"]//child::input"));
	//By PlaceOfDamageDropdown = (By.xpath("//*[@id=\"duet-combobox-list-element\"]/li[1]/a"));
	//By CityOfDamage = (By.xpath("//*[@formcontrolname=\"city\"]//child::input"));
	//By PlaceOfDamage_TextBox = By.xpath("//*[@class='duet-input-relative sc-duet-input sc-duet-input-s']");
	//By PlaceOfDamage_ComboBox = By.xpath("//*[@formcontrolname=\"country\"]//child::input");
	
	String mbFormFill_Button = "return document.querySelector(\"#main > lt-cel-platform-feature-vehicle-fnol > duet-card > lt-cel-platform-loss-location > div > div > duet-tab-group\").shadowRoot.querySelector(\"button:nth-child(2)\")";
	String municipalityListDropdown = ("return document.querySelector('li[data-index=\"0\"] > a');".replace("0", index));
	String addressOrOtherSpecifier = "return document.querySelector(\"#addressOrOtherSpecifier\").shadowRoot.querySelector(\"#addressOrOtherSpecifier\")";
	String locationAdditionalInfo = "return document.querySelector(\"#locationAdditionalInfo\").shadowRoot.querySelector(\"#locationAdditionalInfo\")";
	
	public LossLocation_Page SelectLossLocationAndMoveNext() throws InterruptedException {

		try {
			wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(".//*[@class='content-without-background']")));
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(".//*[@class='content-without-background']")));
		} catch (Exception e) {
			// System.out.println(e);
		}
		try {
			wait.until(ExpectedConditions.visibilityOfElementLocated(Shadow_Next_FormFill));
		} catch (Exception e) {
			// System.out.println(e);
		}
		if (CF.isMobilePlatform()) {
			CF.mbClickShadow(mbFormFill_Button, "FormFill", "Tab", "PASS");
		} else {
			CF.clickOnShadowElementWithScrollView(Shadow_Next_FormFill, FormFill_Button, "FormFill Tab", "Tab", "PASS");
		}
		CF.clickOnElement(municipalityList_ComboBox, "municipality List ComboBox", "ComboBox", "PASS");
		Thread.sleep(3000);
		CF.clickRadioButton(municipalityList_Dropdown, municipalityListDropdown,"Municipality List", "Dropdown");
		CF.enterValuesShadowElement(addressOrOtherSpecifier_textarea, addressOrOtherSpecifier_textarea, addressOrOtherSpecifier,"address Or Other Specifier Textarea", 60, "PASS");
		CF.enterValuesShadowElement(LocationAdditionalInfo_Textarea,LocationAdditionalInfo_Textarea, locationAdditionalInfo,"LocationAdditionalInfo Textarea", 200, "PASS");
		CF.scrollDown(Next_Button);
		CF.clickOnElement(Next_Button, "Next", "RadioButton", "PASS");
			
		return new LossLocation_Page(scriptHelper);
	}
}
