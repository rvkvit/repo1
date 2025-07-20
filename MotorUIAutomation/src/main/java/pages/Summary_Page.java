package pages;

import java.util.List;
import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.LT.framework.Status;

import businesscomponents.CommonFunctions;
import supportlibraries.ScriptHelper;

public class Summary_Page extends MasterPage{
	
	CommonFunctions CF = new CommonFunctions(scriptHelper);
	public Summary_Page(ScriptHelper scriptHelper)
	{
		super(scriptHelper);	
	}
	
	String strPhoneNo = "+358" + RandomStringUtils.randomNumeric(8);
	String LANGUAGE = dataTable.getData("General_Data", "Language");
	String compensationAmount = RandomStringUtils.randomNumeric(4);
	String strMayEnabled = properties.getProperty("MAY_FLAG");
	
	By OpenAll_Button = By.cssSelector("#toggleCardsBtn");
	By TelephoneNo_Textarea = By.xpath("//*[@data-testid=\"vfnol-summary-input-notifier-phone\"]//input");
	By InfoIsCorrect_CheckBox = By.xpath("//*[@data-testid=\"vfnol-summary-information-correct-checkbox\"]");
	By DecisionElectronicallyYes_RadioButton = By.xpath("(//*[@formcontrolname='decisionElectronically']//following::input)[1]");
	By TransmitInformationToPartnerYes_RadioButton = By.xpath("(//*[@formcontrolname='transmitInformationToPartner']//following::input)[1]");
	By Next_Button = By.xpath("//*[@data-testid=\"vfnol-summary-send-button\"]");
	By LocationDescription_FI = By.xpath("//*[text()='Vahinkopaikan kuvaus']//following::span[1]");
	By LocationDescription_SV = By.xpath("//*[text()='Beskrivning av skadeplatsen']//following::span[1]");
	By Registartion_No = By.xpath("//*[@data-testid=\"vfnol-vehicle-info-card-registration-text\"]");
	By DuetCard_host = By.xpath("//*[@id=\"main\"]//duet-card");
	By DuetCard_Shadow = By.cssSelector("[class='duet-card-heading-text']");

	/* EDIT */
	By More_Details = By.cssSelector("#damageDescription");
	By Next_button = By.xpath("(//*[@class=\"duet-m-0 duet-expand primary hydrated\"])[2]");
	By locationAdditionalInfo_textarea = By.cssSelector("#locationAdditionalInfo");
	By addressOrOtherSpecifier_textarea = By.cssSelector("#addressOrOtherSpecifier");
	By typeOfRoad_Dropdown = By.id("typeOfRoad");
	By damageSite_Dropdown = By.id("damageSite");
	By damageDescription_textarea = By.xpath("//*[@formcontrolname=\"damageDescription\"]//child::textarea");
	By eyewinessDetails_textarea = By.cssSelector("#eyewinessDetails");
	By expenseType_dropdown = By.id("expenseType");
	By whatOtherExpense_textarea = By.xpath("//*[@formcontrolname=\"whatOtherExpense\"]//child::input");
	By compensationAmount_textarea = By.xpath("//*[@formcontrolname=\"compensationAmount\"]//child::input");
	By textarea_host = By.cssSelector("duet-textarea");
	By textarea_shadow = By.cssSelector("textarea");
	By vehicleDamageSelectionDuetLink = By.cssSelector("lt-cel-platform-vehicle-damage-selection duet-link");
	By vehicleDamageAdditionalInfoDuetLink = By.cssSelector("lt-cel-platform-vehicle-damage-additional-info duet-link");
	By towingDetailsDuetLink = By.cssSelector("lt-cel-platform-towing-details duet-link");
	By deductVATPartly = By.xpath("(//*[@formcontrolname=\"deductVAT\"]//duet-choice)[3]");
	By VATpartly_input = By.xpath("//*[@formcontrolname=\"VATPartly\"]//input");
	By Next_addExpenseModal = By.xpath("//*[@id=\"lt-cel-platform-trip-expenses-modal\"]//duet-button[@class=\"primary hydrated\"]");

	String InfoCorrectCheckBox = "return document.querySelector(\"input[type='checkbox']\")";
	String damageDescription = "return document.querySelector(\"#damageDescription\").shadowRoot.querySelector(\"#damageDescription\")";
	String addressOrOtherSpecifier = "return document.querySelector(\"#addressOrOtherSpecifier\").shadowRoot.querySelector(\"#addressOrOtherSpecifier\")";
	String locationAdditionalInfo = "return document.querySelector(\"#locationAdditionalInfo\").shadowRoot.querySelector(\"#locationAdditionalInfo\")";
	String DamageDescription = "return document.querySelector(\"duet-textarea\").shadowRoot.querySelector(\"textarea\")";
	String eyewinessDetails = "return document.querySelector(\"#eyewinessDetails\").shadowRoot.querySelector(\"#eyewinessDetails\")";
	
	public Summary_Page FinalSubmit() throws InterruptedException {
		//summaryEdit();
		ExtractText();	
		CF.clickRadioButton(InfoIsCorrect_CheckBox, InfoCorrectCheckBox, "InfoIsCorrect CheckBox", "CheckBox");
		if (CF.isMobilePlatform()) {
			CF.scrollDown(DecisionElectronicallyYes_RadioButton);
		}
		WebElement DecisionElectronicallyYes = driver.findElement(DecisionElectronicallyYes_RadioButton);
		DecisionElectronicallyYes.click();
		WebElement TransmitInformationToPartnerYes = driver.findElement(TransmitInformationToPartnerYes_RadioButton);
		TransmitInformationToPartnerYes.click();
		Thread.sleep(5000);
		CF.clickOnElement(Next_Button, "Next", "Button", "PASS");
		return new Summary_Page(scriptHelper);
	}
	
	public Summary_Page ExtractText() throws InterruptedException {
		CF.clickOnElement(OpenAll_Button, "OpenAll Button", "Button", "PASS");
		String RegistartionNo = CF.Extracttext(Registartion_No);
		String Locdetails = LANGUAGE.matches("FI") ? CF.Extracttext(LocationDescription_FI) : CF.Extracttext(LocationDescription_SV);

		dataTable.putData("General_Data", "LocationDescription", Locdetails);
		dataTable.putData("General_Data", "RegistartionNo", RegistartionNo);

		//CF.scrollDown(TelephoneNo_Textarea);
		//CF.enterValuesInTextBox(TelephoneNo_Textarea, "Phone Number", strPhoneNo,"PASS");
		
		return new Summary_Page(scriptHelper);
	}
	
	public Summary_Page summaryEdit() throws InterruptedException {
		if (strMayEnabled.contains("true")) {
			// CF.clickOnElement(OpenAll_Button, "OpenAll Button", "Button", "PASS");
			List<WebElement> elements = driver.findElements(DuetCard_host);
			System.out.println("Number of elements:" + elements.size());

			for (int i = 3; i < elements.size(); i++) {
				String num = Integer.toString(i);
				By DuetCard_host = By.xpath("(//*[@id=\"main\"]//duet-card)[num]".replace("num", num));
				WebElement webElement = driver.findElement(DuetCard_host);
				if (webElement.isDisplayed()) {
					String duetcardName = CF.ExtracttextShadowElement(DuetCard_host, DuetCard_Shadow);
					System.out.println(duetcardName);
					By editButton = By.xpath("(//*[@id=\"main\"]//duet-card)[num]//child::duet-button[@icon=\"action-edit-2\"]".replace("num", num));
					By addExpense = By.xpath("(//*[@id=\"main\"]//duet-card)[num]//child::duet-button[@icon=\"action-add-circle\"]".replace("num", num));
					switch (duetcardName) {

					case "Luonnonilmiön kuvaus":
					case "Beskrivning av naturfenomen":
						CF.clickOnElement(OpenAll_Button, "OpenAll Button", "Button", "PASS");
						CF.clickOnElement(editButton, "Edit", "Button", "PASS");
						report.updateTestLog("Editing card : ", duetcardName, Status.PASS);
						CF.enterValuesShadowElement(More_Details, More_Details, damageDescription,"More Details", 1000, "PASS");
						CF.scrollDown(Next_button);
						CF.clickOnElement(Next_button, "Next", "Button", "PASS");
						CF.clickOnElement(OpenAll_Button, "OpenAll Button", "Button", "PASS");
						break;

					case "Skadeplats":
					case "Vahinkopaikka":
						CF.clickOnElement(OpenAll_Button, "OpenAll Button", "Button", "PASS");
						CF.clickOnElement(editButton, "Edit", "Button", "PASS");
						report.updateTestLog("Editing card : ", duetcardName, Status.PASS);
						CF.scrollDown(addressOrOtherSpecifier_textarea);
						CF.enterValuesShadowElement(addressOrOtherSpecifier_textarea, addressOrOtherSpecifier_textarea,addressOrOtherSpecifier,"address Or Other Specifier textarea", 45, "PASS");
						CF.scrollDown(locationAdditionalInfo_textarea);
						CF.enterValuesShadowElement(locationAdditionalInfo_textarea, locationAdditionalInfo_textarea,locationAdditionalInfo,"location Additional Info textarea", 250, "PASS");
						CF.scrollDown(Next_button);
						CF.clickOnElement(Next_button, "Next", "Button", "PASS");
						CF.clickOnElement(OpenAll_Button, "OpenAll Button", "Button", "PASS");
						break;

					case "Vahinkopaikan lisätiedot":
					case "Tilläggsuppgifter om skadeplatsen":
						CF.clickOnElement(OpenAll_Button, "OpenAll Button", "Button", "PASS");
						CF.clickOnElement(editButton, "Edit", "Button", "PASS");
						report.updateTestLog("Editing card : ", duetcardName, Status.PASS);
						CF.selectDropDownByIndex(damageSite_Dropdown, "damage Site Dropdown", 12, "PASS");
						CF.scrollDown(Next_button);
						CF.clickOnElement(Next_button, "Next", "Button", "PASS");
						CF.clickOnElement(OpenAll_Button, "OpenAll Button", "Button", "PASS");
						break;

					case "Skador på det försäkrade fordonet":
					case "Vakuutetun ajoneuvon vauriot":
						CF.clickOnElement(OpenAll_Button, "OpenAll Button", "Button", "PASS");
						CF.clickOnElement(editButton, "Edit", "Button", "PASS");
						report.updateTestLog("Editing card : ", duetcardName, Status.PASS);
						CF.verifyElement(vehicleDamageSelectionDuetLink, "vehicle Damage Selection", "DuetLink","PASS");
						if (CF.ElementFound(textarea_host)) {
							CF.scrollDown(textarea_host);
							CF.enterValuesShadowElement(textarea_host, textarea_shadow, DamageDescription,"damage Description textarea",150, "PASS");
						}
						CF.scrollDown(Next_button);
						CF.clickOnElement(Next_button, "Next", "Button", "PASS");
						CF.clickOnElement(OpenAll_Button, "OpenAll Button", "Button", "PASS");
						break;

					case "Vahinkotilanteen tiedot":
					case "Uppgifter om skadesituationen":
						CF.clickOnElement(OpenAll_Button, "OpenAll Button", "Button", "PASS");
						CF.clickOnElement(editButton, "Edit", "Button", "PASS");
						report.updateTestLog("Editing card : ", duetcardName, Status.PASS);
						if (CF.ElementFound(textarea_host)) {
							CF.scrollDown(textarea_host);
							CF.enterValuesShadowElement(textarea_host, textarea_shadow,DamageDescription,"collision Damage description textarea", 1000, "PASS");
						}
						CF.scrollDown(Next_button);
						CF.clickOnElement(Next_button, "Next", "Button", "PASS");
						CF.clickOnElement(OpenAll_Button, "OpenAll Button", "Button", "PASS");
						break;

					case "Tilläggsuppgifter om skadesituationen":
					case "Vahinkotilanteen lisätiedot":
						CF.clickOnElement(OpenAll_Button, "OpenAll Button", "Button", "PASS");
						CF.clickOnElement(editButton, "Edit", "Button", "PASS");
						report.updateTestLog("Editing card : ", duetcardName, Status.PASS);
						CF.scrollDown(eyewinessDetails_textarea);
						CF.enterValuesShadowElement(eyewinessDetails_textarea, eyewinessDetails_textarea,eyewinessDetails,"eyewiness Details textarea", 200, "PASS");
						CF.scrollDown(Next_button);
						CF.clickOnElement(Next_button, "Next", "Button", "PASS");
						CF.clickOnElement(OpenAll_Button, "OpenAll Button", "Button", "PASS");
						break;

					case "Matkan keskeytymisen kulut":
					case "Kostnader för avbruten resa":
						CF.clickOnElement(OpenAll_Button, "OpenAll Button", "Button", "PASS");
						CF.clickOnElement(addExpense, "Edit", "Button", "PASS");
						report.updateTestLog("Editing card : ", duetcardName, Status.PASS);
						CF.selectDropDownByIndex(expenseType_dropdown, "expense Type dropdown", 7, "PASS");
						CF.enterValues(whatOtherExpense_textarea, "what Other Expense textarea", 100, "PASS");
						CF.scrollDown(compensationAmount_textarea);
						CF.enterValues(compensationAmount_textarea, "compensation Amount textarea", compensationAmount,"PASS");
						CF.scrollDown(textarea_host);
						CF.enterValuesShadowElement(textarea_host, textarea_shadow,DamageDescription, "description textarea", 255,
								"PASS");
						CF.scrollDown(Next_addExpenseModal);
						CF.clickOnElement(Next_addExpenseModal, "Next", "Button", "PASS");
						CF.clickOnElement(OpenAll_Button, "OpenAll Button", "Button", "PASS");
						break;

					case "Ajoneuvovaurioiden lisätiedot":
					case "Tilläggsuppgifter om skadorna på fordonet":
						CF.clickOnElement(OpenAll_Button, "OpenAll Button", "Button", "PASS");
						CF.clickOnElement(editButton, "Edit", "Button", "PASS");
						report.updateTestLog("Editing card : ", duetcardName, Status.PASS);
						CF.scrollDown(Next_button);
						CF.verifyElement(vehicleDamageAdditionalInfoDuetLink, "vehicle Damage Additional Info","Duet Link", "PASS");
						CF.scrollDown(Next_button);
						CF.clickOnElement(Next_button, "Next", "Button", "PASS");
						CF.clickOnElement(OpenAll_Button, "OpenAll Button", "Button", "PASS");
						break;

					case "Hinauksen tiedot":
					case "Uppgifter om bogsering":
						CF.clickOnElement(OpenAll_Button, "OpenAll Button", "Button", "PASS");
						CF.clickOnElement(editButton, "Edit", "Button", "PASS");
						report.updateTestLog("Editing card : ", duetcardName, Status.PASS);
						CF.scrollDown(Next_button);
						CF.verifyElement(towingDetailsDuetLink, "towing Details", "Duet Link", "PASS");
						CF.scrollDown(Next_button);
						CF.clickOnElement(Next_button, "Next", "Button", "PASS");
						CF.clickOnElement(OpenAll_Button, "OpenAll Button", "Button", "PASS");
						break;

					case "Uppgifter om användningen av fordonet":
					case "Ajoneuvon käyttöön liittyvät tiedot":
						CF.clickOnElement(OpenAll_Button, "OpenAll Button", "Button", "PASS");
						CF.clickOnElement(editButton, "Edit", "Button", "PASS");
						report.updateTestLog("Editing card : ", duetcardName, Status.PASS);
						if (CF.ElementFound(deductVATPartly)) {
							CF.clickOnElement(deductVATPartly, "deduct VAT Partly", "RadioButton", "PASS");
							CF.enterValues(VATpartly_input, " VAT deduction in percentage", "30", "PASS");
						}
						CF.scrollDown(Next_button);
						CF.clickOnElement(Next_button, "Next", "Button", "PASS");
						CF.clickOnElement(OpenAll_Button, "OpenAll Button", "Button", "PASS");
						break;
					}
				}
			}
		}
		return new Summary_Page(scriptHelper);
	}

}
