package businesscomponents;

import java.text.SimpleDateFormat;
import java.text.DateFormat;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.LT.framework.Status;
import com.LT.framework.selenium.WebDriverUtil;

import supportlibraries.ReusableLibrary;
import supportlibraries.ScriptHelper;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.remote.RemoteWebDriver;

import static java.nio.charset.StandardCharsets.*;

/**
 * Verification Components class
 * 
 * @author LT TCS
 */
public class CommonFunctions extends ReusableLibrary {
	private WebDriverUtil driverUtil;

	/**
	 * Constructor to initialize the component library
	 * 
	 * @param scriptHelper The {@link ScriptHelper} object passed from the
	 *                     {@link DriverScript}
	 */
	public CommonFunctions(ScriptHelper scriptHelper) {
		super(scriptHelper);

		driverUtil = new WebDriverUtil(driver);
	}

	public static ThreadLocal<Boolean> failflag = new ThreadLocal<Boolean>();
	WebDriverWait wait = new WebDriverWait(driver, Duration.ofMinutes(1));
	WebDriverWait wait1 = new WebDriverWait(driver, Duration.ofMinutes(2));
	WebDriverWait wait2 = new WebDriverWait(driver, Duration.ofSeconds(30));
	WebDriverWait wait3 = new WebDriverWait(driver, Duration.ofSeconds(10));

	String strLang = dataTable.getData("General_Data", "Language");
	String strFIURL = dataTable.getData("General_Data", "GB FI URL ");
	String strTUURL = dataTable.getData("General_Data", "GB  SV URL");

	String platformName = ((RemoteWebDriver) driver).getCapabilities().getCapability("platformName").toString()
			.toLowerCase();
	String browserName = ((RemoteWebDriver) driver).getCapabilities().getBrowserName().toLowerCase();

	public void clickOnElement(By identifer, String strElementName, String strElementType, String StatusFlag) {

		By strA = identifer;
		waitForPageLoad(driver);

		wait.until(ExpectedConditions.visibilityOfElementLocated(strA));
		JavascriptExecutor js = (JavascriptExecutor) driver;
		WebElement webElement = driver.findElement(strA);
		js.executeScript("arguments[0].scrollIntoView();", webElement);
		// js.executeScript("arguments[0].scrollTop += 800;", webElement);
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (webElement.isDisplayed()) {
			webElement.click();
			System.out.print("");
			report.updateTestLog("Click on " + strElementName,
					strElementName + " " + strElementType + " is clicked successfully", Status.valueOf(StatusFlag));
		} else {
			report.updateTestLog("Verify element:", strElementName + ": is not displayed", Status.FAIL);
		}

	}

	public void verifyElement(By identifer, String strElementName, String strElementType, String StatusFlag) {

		By strA = identifer;
		waitForPageLoad(driver);
		// wait condition check
		wait1.until(ExpectedConditions.visibilityOfElementLocated(strA));
		WebElement webElement = driver.findElement(strA);
		if (webElement.isDisplayed()) {
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("arguments[0].style.border='2px solid black'", webElement);
			report.updateTestLog("Verification of " + strElementName,
					strElementName + " " + strElementType + " is displayed", Status.valueOf(StatusFlag));
		} else {
			report.updateTestLog("Verify element:", strElementName + ": is not displayed", Status.FAIL);
		}

	}

	public void enterValuesInTextBox(By identifer, String strTextBoxName, String strValue, String StatusFlag) {
		By strA = identifer;
		waitForPageLoad(driver);
		wait.until(ExpectedConditions.visibilityOfElementLocated(strA));
		JavascriptExecutor js = (JavascriptExecutor) driver;
		WebElement webElement = driver.findElement(strA);
		js.executeScript("arguments[0].scrollIntoView();", webElement);
		// js.executeScript("arguments[0].scrollTop += 800;", webElement);
		if (webElement.isDisplayed()) {
			webElement.clear();
			webElement.sendKeys(strValue);
			report.updateTestLog("Enter values in " + strTextBoxName, strValue + " is entered successfully",
					Status.valueOf(StatusFlag));
		} else {
			report.updateTestLog("Verify element:", strTextBoxName + ": is not displayed", Status.FAIL);

		}

	}

	public int getCount(By identifer) {
		By strA = identifer;
		int C = 0;
		try {
			String strCount;
			strCount = driver.findElement(strA).getText();
			C = Integer.parseInt(strCount);

		} catch (Exception e) {

		}

		return C;

	}

	public String getWindowHandle() {

		String winHandleBefore = driver.getWindowHandle();
		return winHandleBefore;

	}

	public void enterValues(By identifer, String strName, String strValue, String StatusFlag) {
		By strA = identifer;
		waitForPageLoad(driver);
		wait1.until(ExpectedConditions.visibilityOfElementLocated(strA));
		JavascriptExecutor js = (JavascriptExecutor) driver;
		WebElement webElement = driver.findElement(strA);
		js.executeScript("arguments[0].scrollIntoView(true);", webElement);
		if (webElement.isDisplayed()) {
			webElement.clear();
			webElement.sendKeys(strValue);
			report.updateTestLog("Enter values in " + strName, strValue + " is entered successfully",
					Status.valueOf(StatusFlag));
		} else {
			report.updateTestLog("Verify element:", strName + ": is not displayed", Status.FAIL);

		}
	}

	public void enterValues(By identifer, String strName, int len, String StatusFlag) {
		By strA = identifer;
		waitForPageLoad(driver);
		wait1.until(ExpectedConditions.visibilityOfElementLocated(strA));
		JavascriptExecutor js = (JavascriptExecutor) driver;
		WebElement webElement = driver.findElement(strA);
		js.executeScript("arguments[0].scrollIntoView();", webElement);
		js.executeScript("arguments[0].scrollTop += 800;", webElement);
		String strValue = RandomStringUtils.randomAlphabetic(len);
		if (webElement.isDisplayed()) {
			webElement.clear();
			webElement.sendKeys(strValue);
			report.updateTestLog("Enter values in " + strName, "value is entered successfully",
					Status.valueOf(StatusFlag));
		} else {
			report.updateTestLog("Verify element:", strName + ": is not displayed", Status.FAIL);

		}
	}

	public void enterValuesShadowElement(By ShadowHost, By ShadowRoot, String ObjJSElementPath, String strElementName, int len,
			String StatusFlag) {
		waitForPageLoad(driver);
		wait1.until(ExpectedConditions.visibilityOfElementLocated(ShadowHost));
		String strValue = RandomStringUtils.randomAlphabetic(len);
		if (isMobilePlatform()) {
			JavascriptExecutor js = (JavascriptExecutor) driver;
			WebElement shadowrootElement = (WebElement) js.executeScript(ObjJSElementPath);
			js.executeScript("arguments[0].scrollIntoViewIfNeeded(true);", shadowrootElement);
			shadowrootElement.clear();
			shadowrootElement.sendKeys(strValue);
		}
		else {
		WebElement shadowHost = driver.findElement(ShadowHost);
		SearchContext shadowRoot = shadowHost.getShadowRoot();
		WebElement shadowContent = shadowRoot.findElement(ShadowRoot);
		if (shadowContent.isDisplayed()) {
			shadowContent.clear();
			shadowContent.sendKeys(strValue);
			report.updateTestLog("Enter values in " + strElementName, "value is entered successfully",
					Status.valueOf(StatusFlag));
		} else {
			report.updateTestLog("Verify element:", strElementName + ": is not displayed", Status.FAIL);

		}}
	}

	public void selectValueFromDropDown(By identifer, String strDropDownName, String strValue, String StatusFlag) {
		By strA = identifer;
		waitForPageLoad(driver);
		wait1.until(ExpectedConditions.visibilityOfElementLocated(strA));
		WebElement webElement = driver.findElement(strA);
		if (webElement.isDisplayed()) {
			Select dropDownOption = new Select(webElement);
			dropDownOption.selectByVisibleText(strValue);
			report.updateTestLog("Select values from " + strDropDownName, strValue + " is selected successfully",
					Status.valueOf(StatusFlag));
		} else {
			report.updateTestLog("Select values from " + strDropDownName, strValue + " is not selected successfully",
					Status.FAIL);

		}

	}

	public void selectDropDownByIndex(By identifer, String strDropDownName, int intIndexNo, String StatusFlag) {
		By strA = identifer;
		waitForPageLoad(driver);
		JavascriptExecutor js = (JavascriptExecutor) driver;
		WebElement webElement = driver.findElement(strA);
		js.executeScript("arguments[0].scrollIntoView();", webElement);
		js.executeScript("arguments[0].scrollTop += 800;", webElement);
		if (webElement.isEnabled()) {
			Select dropDownOption = new Select(webElement);
			dropDownOption.selectByIndex(intIndexNo);
			report.updateTestLog("Select values from " + strDropDownName, intIndexNo + " is selected successfully",
					Status.valueOf(StatusFlag));
		}

		else {
			report.updateTestLog("Select values from " + strDropDownName, intIndexNo + " is not selected successfully",
					Status.FAIL);

		}

	}

	public void performTab(By identifer, int intCount) {
		By strA = identifer;
		waitForPageLoad(driver);
		WebElement webElement = driver.findElement(strA);
		if (webElement.isDisplayed()) {
			for (int i = 1; i <= intCount; i++) {
				webElement.sendKeys(Keys.TAB);
			}
		}

	}

	public String getValue(By identifer, int beginIndex, int endIndex) {
		By strA = identifer;
		waitForPageLoad(driver);
		String value = null;
		String getValue;
		WebElement webElement = driver.findElement(strA);
		if (webElement.isDisplayed()) {
			getValue = webElement.getText();
			value = getValue.substring(beginIndex, endIndex);

		}
		return value;
	}

	public String getText(By identifer) {
		By strA = identifer;
		waitForPageLoad(driver);
		String getValue = null;
		wait1.until(ExpectedConditions.visibilityOfElementLocated(strA));
		WebElement webElement = driver.findElement(strA);
		if (webElement.isDisplayed()) {
			getValue = webElement.getText();
		}
		return getValue;
	}

	public void takeScreenshot() {
		report.updateTestLog("Take Screenshot", "Screenshot taken", Status.SCREENSHOT);

	}

	public String getAttribute(By identifer, String strAttrType) {
		By strA = identifer;
		String getValue = null;
		WebElement webElement = driver.findElement(strA);
		if (webElement.isDisplayed()) {
			getValue = webElement.getAttribute(strAttrType);
		}
		return getValue;
	}
	
	public void verifyShadowElement(By ShadowHost, By ShadowRoot, String strElementName, String strElementType,String StatusFlag) {
		waitForPageLoad(driver);
		wait1.until(ExpectedConditions.visibilityOfElementLocated(ShadowHost));
		WebElement shadowHost = driver.findElement(ShadowHost);
		SearchContext shadowRoot = null;
		if (browserName.contains("safari")) {
			try {
				if (shadowHost != null) {
					JavascriptExecutor js = (JavascriptExecutor) driver;
					shadowRoot = (SearchContext) js.executeScript("return arguments[0].shadowRoot", shadowHost);
				}
			} catch (Exception e) {
				report.updateTestLog("Verify " + strElementName,
						"Error accessing Shadow DOM for " + strElementName + ": " + e.getMessage(), Status.FAIL);
				return;
			}
		} else {
			shadowRoot = shadowHost.getShadowRoot();
		}

		if (shadowRoot != null) {
			WebElement shadowContent = shadowRoot.findElement(ShadowRoot);
			if (shadowContent.isDisplayed()) {
				report.updateTestLog("Verify " + strElementName,
						strElementName + " " + strElementType + " is displayed successfully",
						Status.valueOf(StatusFlag));
			} else {
				report.updateTestLog("Verify element:", strElementName + ": is not displayed", Status.FAIL);
			}
		} else {
			report.updateTestLog("Verify element:", "Shadow Root not found for " + strElementName, Status.FAIL);
		}
	}
	
	public void clickOnShadowElement(By ShadowHost, By ShadowRoot, String strElementName,String strElementType, String StatusFlag) {
	    waitForPageLoad(driver);
	    wait1.until(ExpectedConditions.visibilityOfElementLocated(ShadowHost));
	    WebElement shadowHost = driver.findElement(ShadowHost);
	    SearchContext shadowRoot = null;
	    if (driver instanceof RemoteWebDriver) {
	        if (browserName.contains("safari")) {
	            try {
	                if (shadowHost != null) {
	                    JavascriptExecutor js = (JavascriptExecutor) driver;
	                    shadowRoot = (SearchContext) js.executeScript("return arguments[0].shadowRoot", shadowHost);
	                }
	            } catch (Exception e) {
	                report.updateTestLog("Click on " + strElementName,
	                        "Error accessing Shadow DOM for " + strElementName + ": " + e.getMessage(), Status.FAIL);
	                return;
	            }
	        } else {
	            shadowRoot = shadowHost.getShadowRoot();
	        }
	    } else {
	        shadowRoot = shadowHost.getShadowRoot();
	    }

	    if (shadowRoot != null) {
	        try {
	            WebElement shadowContent = shadowRoot.findElement(ShadowRoot);
	            if (shadowContent.isDisplayed()) {
	                shadowContent.click(); 
	                report.updateTestLog("Click on " + strElementName,
	                		strElementType + " clicked successfully", Status.valueOf(StatusFlag));
	            } else {
	                report.updateTestLog("Click on element:", strElementName + ": is not displayed", Status.FAIL);
	            }
	        } catch (Exception e) {
	            report.updateTestLog("Click on element:", "Error while clicking " + strElementName + ": " + e.getMessage(), Status.FAIL);
	        }
	    } else {
	        report.updateTestLog("Click on element:", "Shadow Root not found for " + strElementName, Status.FAIL);
	    }
	}

	public void clickOnShadowElementWithScrollView(By ShadowHost, By ShadowRoot, String strElementName,
			String strElementType, String StatusFlag) {
		waitForPageLoad(driver);
		wait1.until(ExpectedConditions.visibilityOfElementLocated(ShadowHost));
		WebElement shadowHost = driver.findElement(ShadowHost);
		SearchContext shadowRoot = null;
		if (driver instanceof RemoteWebDriver) {
			if (browserName.contains("safari")) {
				try {
					if (shadowHost != null) {
						JavascriptExecutor js = (JavascriptExecutor) driver;
						shadowRoot = (SearchContext) js.executeScript("return arguments[0].shadowRoot", shadowHost);
					}
				} catch (Exception e) {
					report.updateTestLog("Click on " + strElementName,
							"Error accessing Shadow DOM for " + strElementName + ": " + e.getMessage(), Status.FAIL);
					return;
				}
			} else {
				shadowRoot = shadowHost.getShadowRoot();
			}
		} else {
			shadowRoot = shadowHost.getShadowRoot();
		}

		if (shadowRoot != null) {
			try {
				WebElement shadowContent = shadowRoot.findElement(ShadowRoot);
				if (shadowContent.isDisplayed()) {
					JavascriptExecutor js = (JavascriptExecutor) driver;
					js.executeScript("arguments[0].scrollIntoView(true);", shadowContent);
					Thread.sleep(1000);
					shadowContent.click();
					report.updateTestLog("Click on " + strElementName, strElementType + " clicked successfully",
							Status.valueOf(StatusFlag));
				} else {
					report.updateTestLog("Click on element:", strElementName + ": is not displayed", Status.FAIL);
				}
			} catch (Exception e) {
				report.updateTestLog("Click on element:",
						"Error while clicking " + strElementName + ": " + e.getMessage(), Status.FAIL);
			}
		} else {
			report.updateTestLog("Click on element:", "Shadow Root not found for " + strElementName, Status.FAIL);
		}
	}

	public void clickOnNestedShadowElement(By ShadowHost, By ShadowRoot1, By ShadowRoot2, String strElementName,
			String strElementType, String StatusFlag) {
		waitForPageLoad(driver);
		wait1.until(ExpectedConditions.visibilityOfElementLocated(ShadowHost));
		WebElement shadowHost = driver.findElement(ShadowHost);
		SearchContext shadowRoot = shadowHost.getShadowRoot();
		WebElement shadowContent = shadowRoot.findElement(ShadowRoot1);
		SearchContext shadowRootTwo = shadowContent.getShadowRoot();
		WebElement shadowContent1 = shadowRootTwo.findElement(ShadowRoot2);

		if (shadowContent1.isDisplayed()) {
			shadowContent1.click();
			report.updateTestLog("Click on " + strElementName,
					strElementName + " " + strElementType + " is clicked successfully", Status.valueOf(StatusFlag));
		} else {
			report.updateTestLog("Verify element:", strElementName + ": is not displayed", Status.FAIL);

		}

	}

	/*
	 * incase, we need to click on a sub-menu that is visible only when users do
	 * mouse-hover on the main-menu, then we can do it using this function. Just
	 * pass web element position to this function. e.g
	 * MouseOver(driver.findElement(By.name(�Main-Menu�)))
	 */
	public void MouseOver(WebElement we) {
		Actions actObj = new Actions(driver);
		actObj.moveToElement(we).build().perform();
	}

	public static void scrollToElementAndClick(WebDriver driver, WebElement element) {
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
		element.click();
		try {
			Thread.sleep(4000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/*
	 * Scenario � While reporting your script status, you need to pass time & date
	 * that when your test-script has finished. Another scenario: If you need unique
	 * username every time, you can append the timestamp with any constant string
	 * (e.g XYZ_May28201513_15_10 PM), so here you are:
	 */

	public static String fn_GetTimeStamp() {
		DateFormat DF = DateFormat.getDateTimeInstance();
		Date dte = new Date();
		String DateValue = DF.format(dte);
		DateValue = DateValue.replaceAll(":", "_");
		DateValue = DateValue.replaceAll(",", "");
		return DateValue;
	}

	public void clickOnButton(By identifer, String strElementName, String StatusFlag) {
		By strA = identifer;
		waitForPageLoad(driver);
		wait1.until(ExpectedConditions.visibilityOfElementLocated(strA));
		WebElement webElement = driver.findElement(strA);
		wait1.until(ExpectedConditions.elementToBeClickable(webElement));
		if (webElement.isDisplayed()) {
			webElement.click();
			report.updateTestLog("Click on " + strElementName, strElementName + " button is clicked successfully",
					Status.valueOf(StatusFlag));
		} else {
			report.updateTestLog("Verify element:", strElementName + ": is not displayed", Status.FAIL);
		}
	}

	public void waitForPageLoad(WebDriver driver) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
		wait.until(ExpectedConditions.jsReturnsValue("return document.readyState === 'complete'"));

	}

	public String getCurrentDate(String format) {
		String Date = new String();
		Date date = new Date();
		SimpleDateFormat formatter = null;
		if (format != null) {
			formatter = new SimpleDateFormat(format);
		} else {
			formatter = new SimpleDateFormat("dd.MM.yyyy");
		}
		String strDate = formatter.format(date);

		Date = strDate;
		return Date;
	}

	public void isnewtabOpened() throws InterruptedException {
		ArrayList<String> tabs = new ArrayList<>(driver.getWindowHandles());
		System.out.println(tabs);

		String targetTab = (driver instanceof RemoteWebDriver)
				? ((RemoteWebDriver) driver).getCapabilities().getBrowserName().toLowerCase().contains("safari")
						? tabs.get(0)
						: tabs.get(1)
				: tabs.get(1);

		driver.switchTo().window(targetTab);

		Thread.sleep(3000);
		String currentUrl = driver.getCurrentUrl();
		System.out.println(driver.getCurrentUrl());
		String expectedURL = "";
		if (strLang.contains("FI")) {

			expectedURL = strFIURL;

		} else {
			expectedURL = strTUURL;
		}
		if (currentUrl.equals(expectedURL)) {

			report.updateTestLog("Redirection to partner Portal Success", "Page URL :" + currentUrl + "<br/>",
					Status.PASS);

		} else {

			report.updateTestLog("Redirection to partner Portal Failed", "Page URL :" + currentUrl + "<br/>",
					Status.FAIL);
		}

	}

	public void scrollDown(By identifer) throws InterruptedException {

		By strA = identifer;
		JavascriptExecutor js = (JavascriptExecutor) driver;
		WebElement modal = driver.findElement(strA);
		js.executeScript("arguments[0].scrollIntoView();", modal);
		js.executeScript("arguments[0].scrollTop += 800;", modal);

	}

	public boolean ElementFound(By identifer) {
		try {
			By strA = identifer;
			waitForPageLoad(driver);
			// wait.until(ExpectedConditions.visibilityOfElementLocated(strA));
			WebElement webElement = driver.findElement(strA);
			if (webElement.isEnabled()) {

				return true;
			} else {
				return false;
			}
		}

		catch (Exception e) {

			return false;
		}
	}

	public void MouseOverelement(By identifer) {
		By strA = identifer;
		WebElement webElement = driver.findElement(strA);
		Actions actObj = new Actions(driver);
		actObj.moveToElement(webElement).build().perform();
	}

	public String getElementCount(String strLocatorXpath) {
		List<WebElement> elements = driver.findElements(By.xpath(strLocatorXpath));
		Integer elementsCount = elements.size() - 1;
		String s = elementsCount.toString();
		return s;
	}

	public void Zoomout(By identifer) {
		By strA = identifer;
		waitForPageLoad(driver);
		// wait1.until(ExpectedConditions.visibilityOfElementLocated(strA));
		WebElement webElement = driver.findElement(strA);
		webElement.sendKeys(Keys.chord(Keys.CONTROL, Keys.SUBTRACT));
	}

	public void Zoomin(By identifer) {
		By strA = identifer;
		waitForPageLoad(driver);
		// wait1.until(ExpectedConditions.visibilityOfElementLocated(strA));
		WebElement webElement = driver.findElement(strA);
		webElement.sendKeys(Keys.chord(Keys.CONTROL, Keys.ADD));
	}

	public static ThreadLocal<WebElement> webtemp = new ThreadLocal<WebElement>();

	public static void setWebtemp(WebElement webtemp1) {
		webtemp.set(webtemp1);

	}

	public void highLighterMethod(By identifer) {
		try {
			By strA = identifer;
			waitForPageLoad(driver);
			WebElement element = driver.findElement(strA);
			JavascriptExecutor js = (JavascriptExecutor) driver;
			{
				// new Actions(driver.moveToElement(element).perform();
				js.executeScript("arguments[0].scrollIntoView(true);", element);
				js.executeScript("window.scrollBy(0,-50)", "");
				js.executeScript("arguments[0].style.border='2px solid yellow'", element);
				setWebtemp(element);
			}
		} catch (Exception e) {
			// TODO: handle exception
			// setWebtemp(element);
		}
	}

	public String Extracttext(By identifer) {
		By strA = identifer;
		waitForPageLoad(driver);
		// wait1.until(ExpectedConditions.visibilityOfElementLocated(strA));
		WebElement webElement = driver.findElement(strA);
		if (webElement.isEnabled()) {
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("arguments[0].scrollIntoView(true);", webElement);
			js.executeScript("arguments[0].style.border='4px solid green'", webElement);
			String Text = webElement.getText();
			return Text;
		}
		return null;

	}

	public void verifyWebElementContent(String webElement, String Contentfulvalue, String StatusFlag) {

		try {

			webElement = webElement.replaceAll("&nbsp;", " ");
			byte[] ptext1 = webElement.getBytes(UTF_8);
			webElement = new String(ptext1, UTF_8);
			webElement = StringUtils.stripStart(webElement, null);
			webElement = webElement.trim();

			Contentfulvalue = Contentfulvalue.replaceAll("Ã„", "Ä");
			Contentfulvalue = Contentfulvalue.replaceAll("Ã–", "Ö");
			Contentfulvalue = Contentfulvalue.replaceAll("Ã…", "Å");
			Contentfulvalue = Contentfulvalue.replaceAll("(\\r|\\n)", " ");
			Contentfulvalue = Contentfulvalue.replaceAll("  ", " ");
			Contentfulvalue = Contentfulvalue.replaceAll("Ã¥", "å");
			Contentfulvalue = Contentfulvalue.replaceAll("Ã¶", "ö");
			Contentfulvalue = Contentfulvalue.replaceAll("Ã¤", "ä");
			Contentfulvalue = Contentfulvalue.replaceAll("â‚¬", "€");
			// Contentfulvalue = Contentfulvalue.replaceAll("ä", "ä");

			/* String ct1= Contenfulvalue.replace("  \n","\n"); */

			Contentfulvalue = Contentfulvalue.replaceAll("â€“", "###REPLACE###");
			// byte[] ptext = Contentfulvalue.getBytes(ISO_8859_1);
			// Contentfulvalue = new String(ptext, UTF_8);
			// Contentfulvalue = Contentfulvalue.replaceAll("ä","ä");
			Contentfulvalue = Contentfulvalue.replaceAll("###REPLACE###", "–");
			Contentfulvalue = Contentfulvalue.replaceAll("\\n", " ");
			Contentfulvalue = Contentfulvalue.replaceAll("\\u00a0", " "); // removes &nbsp;
			Contentfulvalue = Contentfulvalue.trim();

			if (webElement.equals(Contentfulvalue)) {
				report.updateTestLog("Verified:", "Value from application" + " " + "'" + webElement + "'" + " "
						+ "matches" + " " + "Value from CC" + " " + "'" + Contentfulvalue + "'",
						Status.valueOf(StatusFlag));
			} else {
				report.updateTestLog("Verified:", "Value from application" + " " + "'" + webElement + "'" + " "
						+ "Not matches" + " " + "Value from CC" + " " + "'" + Contentfulvalue + "'", Status.FAIL);
			}
			unhighlightWebElement(webtemp.get());
		} catch (Exception e) {
			try {
				unhighlightWebElement(webtemp.get());
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}

	public boolean registrationInfoLoaded() {

		String strEnv = properties.getProperty("Environment");
		String strRegInfoURL = dataTable.getData("General_Data", "Registration Info URL");
		String strRegInfoURLUAT = dataTable.getData("General_Data", "Registration Info URL UAT");

		try {
			wait2.until(ExpectedConditions.visibilityOfElementLocated(
					By.xpath(".//*[@class=\"duet-button-container primary duet-button-is-loading\"]")));
			wait3.until(ExpectedConditions.invisibilityOfElementLocated(
					By.xpath(".//*[@class=\"duet-button-container primary duet-button-is-loading\"]")));
		} catch (Exception e) {
			// System.out.println(e);
		}

		String currentUrl = driver.getCurrentUrl();
		System.out.println(currentUrl);
		return (strEnv.contains("SIT")) ? currentUrl.equals(strRegInfoURL) : currentUrl.equals(strRegInfoURLUAT);

	}

	public boolean isPurchaseAllowed() {

		String strFIURL = dataTable.getData("General_Data", "Purchase Allowed");

		try {
			wait2.until(ExpectedConditions.visibilityOfElementLocated(
					By.xpath(".//*[@class=\"duet-button-container primary duet-button-is-loading\"]")));
			// wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(".//*[@class=\"duet-button-container
			// primary duet-button-is-loading\"]")));
		} catch (Exception e) {
			// System.out.println(e);
		}

		String currentUrl = driver.getCurrentUrl();
		System.out.println(currentUrl);
		if (currentUrl.equals(strFIURL)) {
			return true;
		} else {
			return false;
		}
	}

	public void unhighlightWebElement(WebElement objApplnElement) {
		try {
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("arguments[0].style.border='0px solid red'", objApplnElement);
			/*
			 * if (driver.get() instanceof JavascriptExecutor) { ((JavascriptExecutor)
			 * driver.get()).executeScript("arguments[0].style.border='0px solid red'",
			 * objApplnElement); }
			 */
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	public void selectValueFromDropDown(By identifer, String strDropDownName, int indexno, String StatusFlag) {
		By strA = identifer;
		waitForPageLoad(driver);
		// wait1.until(ExpectedConditions.visibilityOfElementLocated(strA));
		WebElement webElement = driver.findElement(strA);
		if (webElement.isEnabled()) {
			Select dropDownOption = new Select(webElement);
			dropDownOption.selectByIndex(indexno);
			report.updateTestLog("Select values from " + strDropDownName, indexno + " is selected successfully",
					Status.valueOf(StatusFlag));
		} else {
			report.updateTestLog("Select values from " + strDropDownName, indexno + " is not selected successfully",
					Status.FAIL);

		}

	}

	public void ElementVisible(By identifer, String strName, String StatusFlag) {
		By strA = identifer;
		waitForPageLoad(driver);
		// wait1.until(ExpectedConditions.visibilityOfElementLocated(strA));
		JavascriptExecutor js = (JavascriptExecutor) driver;
		WebElement webElement = driver.findElement(strA);
		js.executeScript("arguments[0].scrollIntoView();", webElement);
		js.executeScript("arguments[0].style.border='2px solid red'", webElement);
		if (webElement.isDisplayed()) {
			report.updateTestLog("Verified:", strName + " " + "is Visible", Status.valueOf(StatusFlag));
		} else {
			report.updateTestLog("Verified:", strName + " " + "is not Visible", Status.FAIL);

		}
	}

	public static void scrollToElement(WebDriver driver, By identifer) {
		By strA = identifer;
		WebElement webElement = driver.findElement(strA);
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", webElement);
	}

	public void verifyShadowElementvisibility(By ShadowHost, By ShadowRoot, String strElementName,
			String strElementType, String StatusFlag) throws NoSuchElementException {
		try {
			waitForPageLoad(driver);
			wait1.until(ExpectedConditions.visibilityOfElementLocated(ShadowHost));
			WebElement shadowHost = driver.findElement(ShadowHost);
			SearchContext shadowRoot = shadowHost.getShadowRoot();
			WebElement shadowContent = shadowRoot.findElement(ShadowRoot);
			report.updateTestLog("Verify " + strElementName,
					strElementName + " " + strElementType + " is displayed successfully", Status.FAIL);
			failflag.set(false);
		} catch (Exception e) {
			report.updateTestLog("Verify " + strElementName, strElementName + " is not displayed",
					Status.valueOf(StatusFlag));
		}
	}

	public void highLighterMethodShadowElement(By ShadowHost, By ShadowRoot) {
		try {
			waitForPageLoad(driver);
			wait1.until(ExpectedConditions.visibilityOfElementLocated(ShadowHost));
			WebElement shadowHost = driver.findElement(ShadowHost);
			SearchContext shadowRoot = shadowHost.getShadowRoot();
			WebElement shadowContent = shadowRoot.findElement(ShadowRoot);
			JavascriptExecutor js = (JavascriptExecutor) driver;
			{
				// new Actions(driver.moveToElement(element).perform();
				js.executeScript("arguments[0].scrollIntoView(true);", shadowContent);
				js.executeScript("window.scrollBy(0,-50)", "");
				js.executeScript("arguments[0].style.border='2px solid red'", shadowContent);
				setWebtemp(shadowContent);
			}
		} catch (Exception e) {
			// TODO: handle exception
			// setWebtemp(element);
		}
	}

	public void verifyWebElementContentpolicy(String webElement, String Contentfulvalue, String StatusFlag) {

		try {
			webElement = webElement.replaceAll("&nbsp;", " ");
			byte[] ptext1 = webElement.getBytes(UTF_8);
			webElement = new String(ptext1, UTF_8);
			webElement = StringUtils.stripStart(webElement, null);
			webElement = webElement.trim();

			Contentfulvalue = Contentfulvalue.replaceAll("â€“", "###REPLACE###");
			byte[] ptext = Contentfulvalue.getBytes(ISO_8859_1);
			Contentfulvalue = new String(ptext, UTF_8);
			Contentfulvalue = Contentfulvalue.replaceAll("###REPLACE###", "ï¿½");
			Contentfulvalue = Contentfulvalue.replaceAll("\\n", " ");
			Contentfulvalue = Contentfulvalue.replaceAll("\\u00a0", " "); // removes &nbsp;
			Contentfulvalue = Contentfulvalue.trim();

			if (webElement.equals(Contentfulvalue)) {
				report.updateTestLog("Verified:",
						"Value from PC" + " " + "'" + webElement + "'" + " " + "matches" + " "
								+ "Value from application" + " " + "'" + Contentfulvalue + "'",
						Status.valueOf(StatusFlag));
				System.out.println();
			} else {
				report.updateTestLog("Verified:", "Value from PC" + " " + "'" + webElement + "'" + " " + "Not matches"
						+ " " + "Value from application" + " " + "'" + Contentfulvalue + "'", Status.FAIL);
				failflag.set(false);
			}
			unhighlightWebElement(webtemp.get());
		} catch (Exception e) {
			try {
				unhighlightWebElement(webtemp.get());
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}

	public String ExtracttextShadowElement(By ShadowHost, By ShadowRoot) {
		waitForPageLoad(driver);
		wait1.until(ExpectedConditions.visibilityOfElementLocated(ShadowHost));
		WebElement shadowHost = driver.findElement(ShadowHost);
		SearchContext shadowRoot = shadowHost.getShadowRoot();
		WebElement shadowContent = shadowRoot.findElement(ShadowRoot);

		if (shadowContent.isEnabled()) {
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("arguments[0].style.border='4px solid green'", shadowContent);
			String Text = shadowContent.getText();
			return Text;
		}
		return null;

	}

	public String getPastFutureDate(int days) {

		Date today = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(today);
		calendar.add(Calendar.DAY_OF_MONTH, days);
		Date threeDaysAgo = calendar.getTime();
		SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
		String strDate = formatter.format(threeDaysAgo);
		return strDate;
	}

	public boolean isShadowElementDisabled(By shadowhost, By shadowroot) {
		// Locate the shadow host
		WebElement shadowHost = driver.findElement(shadowhost);

		// Access the shadow root
		SearchContext shadowRoot = shadowHost.getShadowRoot();

		// Find the element inside the shadow DOM
		WebElement shadowElement = shadowRoot.findElement(shadowroot);

		// Check if the element is disabled
		boolean isDisabled = shadowElement.getAttribute("aria-disabled") != null;
		System.out.println("Disabled : " + isDisabled);
		return isDisabled;		
	}

	/* Mobile automation */

	public boolean isMobilePlatform() {
		boolean isMobilePlatform = "ios".equals(platformName) || "android".equals(platformName);
		return isMobilePlatform;
	}

	public boolean isSafari() {
		// System.out.println("Browser is " + browserName);
		boolean isMobilePlatform = "safari".equals(browserName) && "ios".equals(platformName);
		return isMobilePlatform;
	}

	public void mbverifyShadowElement(String ObjJSElementPath, String strElementName, String strElementType,
			String StatusFlag) {
		waitForPageLoad(driver);
		JavascriptExecutor js = (JavascriptExecutor) driver;
		WebElement shadowrootElement = (WebElement) js.executeScript(ObjJSElementPath);

		if (shadowrootElement.isDisplayed()) {
			report.updateTestLog("Verify " + strElementName,
					strElementName + " " + strElementType + " is displayed successfully", Status.valueOf(StatusFlag));
		} else {
			report.updateTestLog("Verify element:", strElementName + ": is not displayed", Status.FAIL);
		}
	}

	public void mbClickShadow(String ObjJSElementPath, String strElementName, String strElementType,
			String StatusFlag) {
		waitForPageLoad(driver);
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		JavascriptExecutor js = (JavascriptExecutor) driver;
		WebElement shadowrootElement = (WebElement) js.executeScript(ObjJSElementPath);
		js.executeScript("arguments[0].scrollIntoViewIfNeeded(true);", shadowrootElement);
		shadowrootElement.click();
		report.updateTestLog("Click on " + strElementName,
				strElementName + " " + strElementType + " is clicked successfully", Status.valueOf(StatusFlag));

	}

	public String mbExtractTextShadow(String ObjJSElementPath) {
		waitForPageLoad(driver);
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		JavascriptExecutor js = (JavascriptExecutor) driver;
		WebElement shadowrootElement = (WebElement) js.executeScript(ObjJSElementPath);
		js.executeScript("arguments[0].scrollIntoViewIfNeeded(true);", shadowrootElement);
		String Text = shadowrootElement.getText();
		return Text;

	}

	public boolean mbisShadowElementDisabled(String ObjJSElementPath) {
		JavascriptExecutor js = (JavascriptExecutor) driver;

		// Execute JavaScript to get shadow root element
		WebElement shadowrootElement = (WebElement) js.executeScript(ObjJSElementPath);

		// Execute JavaScript to get shadow element and check aria-disabled attribute
		boolean isDisabled = (Boolean) js.executeScript("return arguments[0].hasAttribute('aria-disabled');",
				shadowrootElement);

		System.out.println("Disabled : " + isDisabled);
		return isDisabled;
	}
	
	 public void clickRadioButton(By element, String shadowElement, String elementName ,String elementType) throws InterruptedException {
		 if (isSafari()) {
	            mbClickShadow(shadowElement, elementName, elementType, "PASS");
	        } else {
	            clickOnElement(element, elementName, elementType, "PASS");
	        }
	    }
	 
	 public void clickElement(By element, String shadowElement, String elementName, String elementType) throws InterruptedException {
		 if (isMobilePlatform()) {
	            mbClickShadow(shadowElement, elementName, elementType, "PASS");
	        } else {
	            clickOnElement(element, elementName, elementType, "PASS");
	        }
	    }
}
