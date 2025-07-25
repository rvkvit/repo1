package com.LT.framework.selenium;


import com.LT.framework.FrameworkException;
import com.LT.framework.Report;
import com.LT.framework.ReportSettings;
import com.LT.framework.ReportTheme;
import java.io.File;
import java.io.IOException;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.Augmenter;
import org.openqa.selenium.remote.RemoteWebDriver;

public class SeleniumReport extends Report {
	private WebDriver driver;

	public void setDriver(WebDriver driver) {
		this.driver = driver;
	}

	public SeleniumReport(ReportSettings reportSettings, ReportTheme reportTheme) {
		super(reportSettings, reportTheme);
	}

	protected void takeScreenshot(String screenshotPath) {
		if (this.driver == null) {
			throw new FrameworkException("Report.driver is not initialized!");
		}

		if ((this.driver.getClass().getSimpleName().equals("HtmlUnitDriver")) || (this.driver.getClass()
				.getGenericSuperclass().toString().equals("class org.openqa.selenium.htmlunit.HtmlUnitDriver")))
			return;
		File scrFile;
		//File scrFile;
		if (this.driver.getClass().getSimpleName().equals("RemoteWebDriver")) {
			Capabilities capabilities = ((RemoteWebDriver) this.driver).getCapabilities();
			if (capabilities.getBrowserName().equals("htmlunit")) {
				return;
			}
			WebDriver augmentedDriver = new Augmenter().augment(this.driver);
			scrFile = (File) ((TakesScreenshot) augmentedDriver).getScreenshotAs(OutputType.FILE);
		} else {
			scrFile = (File) ((TakesScreenshot) this.driver).getScreenshotAs(OutputType.FILE);
		}

		try {
			FileUtils.copyFile(scrFile, new File(screenshotPath), true);
		} catch (IOException e) {
			e.printStackTrace();
			throw new FrameworkException("Error while writing screenshot to file");
		}
	}
}