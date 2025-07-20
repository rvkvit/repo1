package pages;

import java.time.Duration;
import org.openqa.selenium.By;

import org.openqa.selenium.support.ui.WebDriverWait;

import businesscomponents.CommonFunctions;
import supportlibraries.ScriptHelper;

public class Attachments_Page extends MasterPage {

	CommonFunctions CF = new CommonFunctions(scriptHelper);

	public Attachments_Page(ScriptHelper scriptHelper) {
		super(scriptHelper);
	}

	By Next_Button = By.xpath("//*[@class='duet-m-0 duet-expand primary hydrated']");
	By FileUpload_Button = By.xpath("//*[@id=\"damageSituationUploadButton\"]");
	By DamageSituation_DropDown = By.xpath("//*[@id=\"damageSituation\"]");

	WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));

	public Attachments_Page AddAttachments() throws InterruptedException {
		Thread.sleep(1000);
		CF.clickOnElement(Next_Button, "Next", "Button", "PASS");
		return new Attachments_Page(scriptHelper);
	}

}
