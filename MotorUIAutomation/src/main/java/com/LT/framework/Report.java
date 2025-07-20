package com.LT.framework;


import java.awt.AWTException;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

public class Report {
	private ReportSettings reportSettings;
	private ReportTheme reportTheme;
	private int stepNumber;
	private int nStepsPassed = 0;
	private int nStepsFailed = 0;
	private int nTestsPassed = 0;
	private int nTestsFailed = 0;

	private List<ReportType> reportTypes = new ArrayList();

	public String testStatus = "Passed";
	private String failureDescription;

	public String getTestStatus() {
		return this.testStatus;
	}

	public String getFailureDescription() {
		return this.failureDescription;
	}

	public Report(ReportSettings reportSettings, ReportTheme reportTheme) {
		this.reportSettings = reportSettings;
		this.reportTheme = reportTheme;
	}

	public void initialize() {
		if (this.reportSettings.generateExcelReports) {
			new File(this.reportSettings.getReportPath() + Util.getFileSeparator() + "Excel Results").mkdir();

			ExcelReport excelReport = new ExcelReport(this.reportSettings, this.reportTheme);
			this.reportTypes.add(excelReport);
		}

		if (this.reportSettings.generateHtmlReports) {
			new File(this.reportSettings.getReportPath() + Util.getFileSeparator() + "HTML Results").mkdir();

			HtmlReport htmlReport = new HtmlReport(this.reportSettings, this.reportTheme);
			this.reportTypes.add(htmlReport);
		}

		new File(this.reportSettings.getReportPath() + Util.getFileSeparator() + "Screenshots").mkdir();
	}

	public void initializeTestLog() {
		if (this.reportSettings.getReportName().equals("")) {
			throw new FrameworkException("The report name cannot be empty!");
		}

		for (int i = 0; i < this.reportTypes.size(); ++i)
			((ReportType) this.reportTypes.get(i)).initializeTestLog();
	}

	public void addTestLogHeading(String heading) {
		for (int i = 0; i < this.reportTypes.size(); ++i)
			((ReportType) this.reportTypes.get(i)).addTestLogHeading(heading);
	}

	public void addTestLogSubHeading(String subHeading1, String subHeading2, String subHeading3, String subHeading4) {
		for (int i = 0; i < this.reportTypes.size(); ++i)
			((ReportType) this.reportTypes.get(i)).addTestLogSubHeading(subHeading1, subHeading2, subHeading3,
					subHeading4);
	}

	public void addTestLogTableHeadings() {
		for (int i = 0; i < this.reportTypes.size(); ++i)
			((ReportType) this.reportTypes.get(i)).addTestLogTableHeadings();
	}

	public void addTestLogSection(String section) {
		for (int i = 0; i < this.reportTypes.size(); ++i) {
			((ReportType) this.reportTypes.get(i)).addTestLogSection(section);
		}

		this.stepNumber = 1;
	}

	public void addTestLogSubSection(String subSection) {
		for (int i = 0; i < this.reportTypes.size(); ++i)
			((ReportType) this.reportTypes.get(i)).addTestLogSubSection(subSection);
	}

	public void updateTestLog(String stepName, String stepDescription, Status stepStatus) {
		if (stepStatus.equals(Status.FAIL)) {
			this.testStatus = "Failed";

			if (this.failureDescription == null)
				this.failureDescription = stepDescription;
			else {
				this.failureDescription = this.failureDescription + "; " + stepDescription;
			}

			this.nStepsFailed += 1;
		}

		if (stepStatus.equals(Status.PASS)) {
			this.nStepsPassed += 1;
		}

		if (stepStatus.ordinal() > this.reportSettings.getLogLevel()) // for done 4, 4 for pass its, 2>4 and for fail its, 
			return;
		String screenshotName = null;

		if ((stepStatus.equals(Status.FAIL)) && (this.reportSettings.takeScreenshotFailedStep)) {
			screenshotName = this.reportSettings.getReportName() + "_"
					+ Util.getCurrentFormattedTime(this.reportSettings.getDateFormatString()).replace(" ", "_")
							.replace(":", "-")
					+ ".png";
			takeScreenshot(this.reportSettings.getReportPath() + Util.getFileSeparator() + "Screenshots"
					+ Util.getFileSeparator() + screenshotName);
		}

		if ((stepStatus.equals(Status.PASS)) && (this.reportSettings.takeScreenshotPassedStep)) {
			screenshotName = this.reportSettings.getReportName() + "_"
					+ Util.getCurrentFormattedTime(this.reportSettings.getDateFormatString()).replace(" ", "_")
							.replace(":", "-")
					+ ".png";
			takeScreenshot(this.reportSettings.getReportPath() + Util.getFileSeparator() + "Screenshots"
					+ Util.getFileSeparator() + screenshotName);
		}

		if (stepStatus.equals(Status.SCREENSHOT)) {
			screenshotName = this.reportSettings.getReportName() + "_"
					+ Util.getCurrentFormattedTime(this.reportSettings.getDateFormatString()).replace(" ", "_")
							.replace(":", "-")
					+ ".png";
			takeScreenshot(this.reportSettings.getReportPath() + Util.getFileSeparator() + "Screenshots"
					+ Util.getFileSeparator() + screenshotName);
		}

		/*
		 * for (int i = 0; i < this.reportTypes.size(); ++i) { ((ReportType)
		 * this.reportTypes.get(i)).updateTestLog(Integer.toString(this.stepNumber),
		 * stepName, stepDescription, stepStatus, screenshotName); }
		 */
		/*
		 * Quick fix AA to have Pass status updated on report
		 */
		((ReportType) this.reportTypes.get(1)).updateTestLog(Integer.toString(this.stepNumber), stepName,
				stepDescription, stepStatus, screenshotName);
		
		this.stepNumber += 1;
	}

	protected void takeScreenshot(String screenshotPath) {
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Dimension screenSize = toolkit.getScreenSize();
		Rectangle rectangle = new Rectangle(0, 0, screenSize.width, screenSize.height);
		Robot robot;
		try {
			robot = new Robot();
		} catch (AWTException e) {
			e.printStackTrace();
			throw new FrameworkException("Error while creating Robot object (for taking screenshot)");
		}
		Robot robot1;
		BufferedImage screenshotImage = robot.createScreenCapture(rectangle);
		File screenshotFile = new File(screenshotPath);
		try {
			ImageIO.write(screenshotImage, "jpg", screenshotFile);
		} catch (IOException e) {
			e.printStackTrace();
			throw new FrameworkException("Error while writing screenshot to .jpg file");
		}
	}

	public void addTestLogFooter(String executionTime) {
		for (int i = 0; i < this.reportTypes.size(); ++i)
			((ReportType) this.reportTypes.get(i)).addTestLogFooter(executionTime, this.nStepsPassed,
					this.nStepsFailed);
	}

	public void consolidateScreenshotsInWordDoc()  {
		String screenshotsConsolidatedFolderPath = this.reportSettings.getReportPath() + Util.getFileSeparator()
				+ "Screenshots (Consolidated)";
		new File(screenshotsConsolidatedFolderPath).mkdir();

		WordDocumentManager documentManager = new WordDocumentManager(screenshotsConsolidatedFolderPath,
				this.reportSettings.getReportName());

		String screenshotsFolderPath = this.reportSettings.getReportPath() + Util.getFileSeparator() + "Screenshots";
		File screenshotsFolder = new File(screenshotsFolderPath);

		FilenameFilter filenameFilter = new FilenameFilter() {
			public boolean accept(File dir, String fileName) {
				return (fileName.contains(Report.this.reportSettings.getReportName()));
			}
		};
		File[] screenshots = screenshotsFolder.listFiles(filenameFilter);
		if ((screenshots != null) && (screenshots.length > 0)) {
			documentManager.createDocument();

			for (File screenshot : screenshots)
				try {
					documentManager.addPicture(screenshot);
				} catch (InvalidFormatException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
	}

	public void initializeResultSummary() {
		for (int i = 0; i < this.reportTypes.size(); ++i)
			((ReportType) this.reportTypes.get(i)).initializeResultSummary();
	}

	public void addResultSummaryHeading(String heading) {
		for (int i = 0; i < this.reportTypes.size(); ++i)
			((ReportType) this.reportTypes.get(i)).addResultSummaryHeading(heading);
	}

	public void addResultSummarySubHeading(String subHeading1, String subHeading2, String subHeading3,
			String subHeading4) {
		for (int i = 0; i < this.reportTypes.size(); ++i)
			((ReportType) this.reportTypes.get(i)).addResultSummarySubHeading(subHeading1, subHeading2, subHeading3,
					subHeading4);
	}
	/*
	 * New addition for summary report
	 */
	public void addResultSummarySubHeading1(String subHeading1, String subHeading2) {
		for (int i = 0; i < this.reportTypes.size(); ++i)
			((ReportType) this.reportTypes.get(i)).addResultSummarySubHeading1(subHeading1, subHeading2);
	}

	public void addResultSummaryTableHeadings() {
		for (int i = 0; i < this.reportTypes.size(); ++i)
			((ReportType) this.reportTypes.get(i)).addResultSummaryTableHeadings();
	}

	public synchronized void updateResultSummary(String scenarioName, String testcaseName, String testcaseDescription,
			String executionTime, String testStatus) {
		if (testStatus.equalsIgnoreCase("failed")) {
			testStatus="Fail";
			this.nTestsFailed += 1;
		} else if (testStatus.equalsIgnoreCase("passed")) {
			testStatus="Pass";
			this.nTestsPassed += 1;
		}

		for (int i = 0; i < this.reportTypes.size(); ++i)
			((ReportType) this.reportTypes.get(i)).updateResultSummary(scenarioName, testcaseName, testcaseDescription,
					executionTime, testStatus);
	}

	public void addResultSummaryFooter(String totalExecutionTime) {
		for (int i = 0; i < this.reportTypes.size(); ++i)
			((ReportType) this.reportTypes.get(i)).addResultSummaryFooter(totalExecutionTime, this.nTestsPassed,
					this.nTestsFailed);
	}
}