package com.LT.framework;

import java.awt.Color;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.common.usermodel.HyperlinkType;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFCreationHelper;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFPalette;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFRegionUtil;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.LT.framework.*;

public class ExcelDataAccess {
	private final String filePath;
	private final String fileName;
	private String datasheetName;

	public String getDatasheetName() {
		return this.datasheetName;
	}

	public void setDatasheetName(String datasheetName) {
		this.datasheetName = datasheetName;
	}

	public ExcelDataAccess(String filePath, String fileName) {
		this.filePath = filePath;
		this.fileName = fileName;
	}

	private void checkPreRequisites() {
		if (this.datasheetName == null)
			throw new FrameworkException("ExcelDataAccess.datasheetName is not set!");
	}

	/*
	 * private HSSFWorkbook openFileForReading_1() { String absoluteFilePath =
	 * this.filePath + Util.getFileSeparator() + this.fileName + ".xls";
	 * FileInputStream fileInputStream; try { fileInputStream = new
	 * FileInputStream(absoluteFilePath); } catch (FileNotFoundException e) {
	 * e.printStackTrace(); throw new FrameworkException("The specified file \"" +
	 * absoluteFilePath + "\" does not exist!"); } HSSFWorkbook workbook; try {
	 * FileInputStream fileInputStream1; workbook = new
	 * HSSFWorkbook(fileInputStream); } catch (IOException e) { e.printStackTrace();
	 * throw new FrameworkException(
	 * "Error while opening the specified Excel workbook \"" + absoluteFilePath +
	 * "\""); } HSSFWorkbook workbook1; return workbook; }
	 */
	private XSSFWorkbook openFileForReading() {
	    String absoluteFilePath = this.filePath + Util.getFileSeparator() + this.fileName + ".xlsx";
	    FileInputStream fileInputStream;
	    try {
	        fileInputStream = new FileInputStream(absoluteFilePath);
	    } catch (FileNotFoundException e) {
	        e.printStackTrace();
	        throw new FrameworkException("The specified file \"" + absoluteFilePath + "\" does not exist!");
	    }

	    XSSFWorkbook workbook;
	    try {
	        workbook = new XSSFWorkbook(fileInputStream);
	    } catch (IOException e) {
	        e.printStackTrace();
	        throw new FrameworkException(
	                "Error while opening the specified Excel workbook \"" + absoluteFilePath + "\"");
	    }

	    return workbook;
	}


	/*
	 * private void writeIntoFile_1(HSSFWorkbook workbook) { String absoluteFilePath
	 * = this.filePath + Util.getFileSeparator() + this.fileName + ".xls";
	 * FileOutputStream fileOutputStream; try { fileOutputStream = new
	 * FileOutputStream(absoluteFilePath); } catch (FileNotFoundException e) {
	 * e.printStackTrace(); throw new FrameworkException("The specified file \"" +
	 * absoluteFilePath + "\" does not exist!"); } try { FileOutputStream
	 * fileOutputStream1; workbook.write(fileOutputStream);
	 * fileOutputStream.close(); } catch (IOException e) { e.printStackTrace();
	 * throw new FrameworkException(
	 * "Error while writing into the specified Excel workbook \"" + absoluteFilePath
	 * + "\""); } }
	 */
	
	private void writeIntoFile(XSSFWorkbook workbook) {
	    String absoluteFilePath = this.filePath + Util.getFileSeparator() + this.fileName + ".xlsx";
	    FileOutputStream fileOutputStream;
	    try {
	        fileOutputStream = new FileOutputStream(absoluteFilePath);
	    } catch (FileNotFoundException e) {
	        e.printStackTrace();
	        throw new FrameworkException("The specified file \"" + absoluteFilePath + "\" does not exist!");
	    }

	    try {
	        workbook.write(fileOutputStream);
	        fileOutputStream.close();
	    } catch (IOException e) {
	        e.printStackTrace();
	        throw new FrameworkException(
	                "Error while writing into the specified Excel workbook \"" + absoluteFilePath + "\"");
	    }
	}

	/*
	 * private HSSFSheet getWorkSheet_1(HSSFWorkbook workbook) { HSSFSheet worksheet
	 * = workbook.getSheet(this.datasheetName); if (worksheet == null) { throw new
	 * FrameworkException("The specified sheet \"" + this.datasheetName + "\"" +
	 * "does not exist within the workbook \"" + this.fileName + ".xls\""); }
	 * 
	 * return worksheet; }
	 */
	
	private XSSFSheet getWorkSheet(XSSFWorkbook workbook) {
	    XSSFSheet worksheet = workbook.getSheet(this.datasheetName);
	    if (worksheet == null) {
	        throw new FrameworkException("The specified sheet \"" + this.datasheetName + "\""
	                + "does not exist within the workbook \"" + this.fileName + ".xlsx\"");
	    }

	    return worksheet;
	}


	/*
	 * public int getRowNum_1(String key, int columnNum, int startRowNum) {
	 * checkPreRequisites();
	 * 
	 * HSSFWorkbook workbook = openFileForReading(); HSSFSheet worksheet =
	 * getWorkSheet(workbook); FormulaEvaluator formulaEvaluator =
	 * workbook.getCreationHelper().createFormulaEvaluator();
	 * 
	 * for (int currentRowNum = startRowNum; currentRowNum <=
	 * worksheet.getLastRowNum();) { HSSFRow row = worksheet.getRow(currentRowNum);
	 * HSSFCell cell = row.getCell(columnNum); String currentValue =
	 * getCellValueAsString(cell, formulaEvaluator);
	 * 
	 * if (currentValue.equals(key)) return currentRowNum; ++currentRowNum; }
	 * 
	 * return -1; }
	 */

	public int getRowNum(String key, int columnNum, int startRowNum) {
	    checkPreRequisites();

	    XSSFWorkbook workbook = openFileForReading();
	    XSSFSheet worksheet = getWorkSheet(workbook);
	    FormulaEvaluator formulaEvaluator = workbook.getCreationHelper().createFormulaEvaluator();

	    for (int currentRowNum = startRowNum; currentRowNum <= worksheet.getLastRowNum();) {
	        XSSFRow row = worksheet.getRow(currentRowNum);
	        XSSFCell cell = row.getCell(columnNum);
	        String currentValue = getCellValueAsString(cell, formulaEvaluator);

	        if (currentValue.equals(key))
	            return currentRowNum;
	        ++currentRowNum;
	    }

	    return -1;
	}

	
	
	/*
	 * private String getCellValueAsString(HSSFCell cell, FormulaEvaluator
	 * formulaEvaluator) { if ((cell == null) || (cell.getCellType() == 3)) { return
	 * ""; } if (formulaEvaluator.evaluate(cell).getCellType() == 5) { throw new
	 * FrameworkException("Error in formula within this cell! Error code: " +
	 * cell.getErrorCellValue()); }
	 * 
	 * DataFormatter dataFormatter = new DataFormatter(); return
	 * dataFormatter.formatCellValue(formulaEvaluator.evaluateInCell(cell)); }
	 */
	 
	
	/*
	 * private String getCellValueAsString(XSSFCell cell, FormulaEvaluator
	 * formulaEvaluator) { if ((cell == null) || (cell.getCellType() ==
	 * CellType.BLANK)) { return ""; } if
	 * (formulaEvaluator.evaluate(cell).getCellType() == CellType.ERROR) { throw new
	 * FrameworkException("Error in formula within this cell! Error code: " +
	 * cell.getErrorCellValue()); }
	 * 
	 * DataFormatter dataFormatter = new DataFormatter(); return
	 * dataFormatter.formatCellValue(formulaEvaluator.evaluateInCell(cell)); }
	 */

	private String getCellValueAsString(XSSFCell cell, FormulaEvaluator formulaEvaluator) {
	    if (cell == null || cell.getCellTypeEnum() == CellType.BLANK) {
	        return "";
	    }
	    
	    if (formulaEvaluator.evaluate(cell).getCellTypeEnum() == CellType.ERROR) {
	        throw new FrameworkException("Error in formula within this cell! Error code: " + cell.getErrorCellValue());
	    }

	    DataFormatter dataFormatter = new DataFormatter();
	    return dataFormatter.formatCellValue(formulaEvaluator.evaluateInCell(cell));
	}


	public int getRowNum(String key, int columnNum) {
		return getRowNum(key, columnNum, 0);
	}

	/*
	 * public int getLastRowNum_1() { checkPreRequisites();
	 * 
	 * HSSFWorkbook workbook = openFileForReading(); HSSFSheet worksheet =
	 * getWorkSheet(workbook);
	 * 
	 * return worksheet.getLastRowNum(); }
	 */
	public int getLastRowNum() {
	    checkPreRequisites();

	    XSSFWorkbook workbook = openFileForReading();
	    XSSFSheet worksheet = getWorkSheet(workbook);

	    return worksheet.getLastRowNum();
	}


	/*
	 * public int getRowCount_1(String key, int columnNum, int startRowNum) {
	 * checkPreRequisites();
	 * 
	 * HSSFWorkbook workbook = openFileForReading(); HSSFSheet worksheet =
	 * getWorkSheet(workbook); FormulaEvaluator formulaEvaluator =
	 * workbook.getCreationHelper().createFormulaEvaluator();
	 * 
	 * int rowCount = 0; boolean keyFound = false;
	 * 
	 * for (int currentRowNum = startRowNum; currentRowNum <=
	 * worksheet.getLastRowNum();) { HSSFRow row = worksheet.getRow(currentRowNum);
	 * HSSFCell cell = row.getCell(columnNum); String currentValue =
	 * getCellValueAsString(cell, formulaEvaluator);
	 * 
	 * if (currentValue.equals(key)) { ++rowCount; keyFound = true; } else { if
	 * (keyFound) break; } ++currentRowNum; }
	 * 
	 * return rowCount; }
	 */
	
	public int getRowCount(String key, int columnNum, int startRowNum) {
	    checkPreRequisites();

	    XSSFWorkbook workbook = openFileForReading();
	    XSSFSheet worksheet = getWorkSheet(workbook);
	    FormulaEvaluator formulaEvaluator = workbook.getCreationHelper().createFormulaEvaluator();

	    int rowCount = 0;
	    boolean keyFound = false;

	    for (int currentRowNum = startRowNum; currentRowNum <= worksheet.getLastRowNum();) {
	        XSSFRow row = worksheet.getRow(currentRowNum);
	        XSSFCell cell = row.getCell(columnNum);
	        String currentValue = getCellValueAsString(cell, formulaEvaluator);

	        if (currentValue.equals(key)) {
	            ++rowCount;
	            keyFound = true;
	        } else {
	            if (keyFound)
	                break;
	        }
	        ++currentRowNum;
	    }

	    return rowCount;
	}


	public int getRowCount(String key, int columnNum) {
		return getRowCount(key, columnNum, 0);
	}

	/*
	 * public int getColumnNum_1(String key, int rowNum) { checkPreRequisites();
	 * 
	 * HSSFWorkbook workbook = openFileForReading(); HSSFSheet worksheet =
	 * getWorkSheet(workbook); FormulaEvaluator formulaEvaluator =
	 * workbook.getCreationHelper().createFormulaEvaluator();
	 * 
	 * HSSFRow row = worksheet.getRow(rowNum);
	 * 
	 * for (int currentColumnNum = 0; currentColumnNum < row.getLastCellNum();) {
	 * HSSFCell cell = row.getCell(currentColumnNum); String currentValue =
	 * getCellValueAsString(cell, formulaEvaluator);
	 * 
	 * if (currentValue.equals(key)) return currentColumnNum; ++currentColumnNum; }
	 * 
	 * return -1; }
	 */

	public int getColumnNum(String key, int rowNum) {
	    checkPreRequisites();

	    XSSFWorkbook workbook = openFileForReading();
	    XSSFSheet worksheet = getWorkSheet(workbook);
	    FormulaEvaluator formulaEvaluator = workbook.getCreationHelper().createFormulaEvaluator();

	    XSSFRow row = worksheet.getRow(rowNum);

	    for (int currentColumnNum = 0; currentColumnNum < row.getLastCellNum();) {
	        XSSFCell cell = row.getCell(currentColumnNum);
	        String currentValue = getCellValueAsString(cell, formulaEvaluator);

	        if (currentValue.equals(key))
	            return currentColumnNum;
	        ++currentColumnNum;
	    }

	    return -1;
	}

	/*
	 * public String getValue_1(int rowNum, int columnNum) { checkPreRequisites();
	 * 
	 * HSSFWorkbook workbook = openFileForReading(); HSSFSheet worksheet =
	 * getWorkSheet(workbook); FormulaEvaluator formulaEvaluator =
	 * workbook.getCreationHelper().createFormulaEvaluator();
	 * 
	 * HSSFRow row = worksheet.getRow(rowNum); HSSFCell cell =
	 * row.getCell(columnNum); return getCellValueAsString(cell, formulaEvaluator);
	 * }
	 */
	public String getValue(int rowNum, int columnNum) {
	    checkPreRequisites();

	    XSSFWorkbook workbook = openFileForReading();
	    XSSFSheet worksheet = getWorkSheet(workbook);
	    FormulaEvaluator formulaEvaluator = workbook.getCreationHelper().createFormulaEvaluator();

	    XSSFRow row = worksheet.getRow(rowNum);
	    XSSFCell cell = row.getCell(columnNum);
	    return getCellValueAsString(cell, formulaEvaluator);
	}


	/*
	 * public String getValue(int rowNum, String columnHeader) {
	 * checkPreRequisites();
	 * 
	 * HSSFWorkbook workbook = openFileForReading(); HSSFSheet worksheet =
	 * getWorkSheet(workbook); FormulaEvaluator formulaEvaluator =
	 * workbook.getCreationHelper().createFormulaEvaluator();
	 * 
	 * HSSFRow row = worksheet.getRow(0); int columnNum = -1;
	 * 
	 * for (int currentColumnNum = 0; currentColumnNum < row.getLastCellNum();) {
	 * HSSFCell cell = row.getCell(currentColumnNum); String currentValue =
	 * getCellValueAsString(cell, formulaEvaluator);
	 * 
	 * if (currentValue.equals(columnHeader)) { columnNum = currentColumnNum; break;
	 * } ++currentColumnNum; }
	 * 
	 * if (columnNum == -1) { throw new
	 * FrameworkException("The specified column header \"" + columnHeader + "\"" +
	 * "is not found in the sheet \"" + this.datasheetName + "\"!"); } row =
	 * worksheet.getRow(rowNum); HSSFCell cell = row.getCell(columnNum); return
	 * getCellValueAsString(cell, formulaEvaluator); }
	 */
	
	public String getValue(int rowNum, String columnHeader) {
	    checkPreRequisites();

	    XSSFWorkbook workbook = openFileForReading();
	    XSSFSheet worksheet = getWorkSheet(workbook);
	    FormulaEvaluator formulaEvaluator = workbook.getCreationHelper().createFormulaEvaluator();

	    XSSFRow row = worksheet.getRow(0);
	    int columnNum = -1;

	    for (int currentColumnNum = 0; currentColumnNum < row.getLastCellNum();) {
	        XSSFCell cell = row.getCell(currentColumnNum);
	        String currentValue = getCellValueAsString(cell, formulaEvaluator);

	        if (currentValue.equals(columnHeader)) {
	            columnNum = currentColumnNum;
	            break;
	        }
	        ++currentColumnNum;
	    }

	    if (columnNum == -1) {
	        throw new FrameworkException("The specified column header \"" + columnHeader + "\""
	                + " is not found in the sheet \"" + this.datasheetName + "\"!");
	    }
	    row = worksheet.getRow(rowNum);
	    XSSFCell cell = row.getCell(columnNum);
	    return getCellValueAsString(cell, formulaEvaluator);
	}


	/*
	 * private HSSFCellStyle applyCellStyle(HSSFWorkbook workbook,
	 * ExcelCellFormatting cellFormatting) { HSSFCellStyle cellStyle =
	 * workbook.createCellStyle(); if (cellFormatting.centred) {
	 * //cellStyle.setAlignment((short) 2); //WNLIP
	 * cellStyle.setAlignment(HorizontalAlignment.CENTER); }
	 * cellStyle.setFillForegroundColor(cellFormatting.getBackColorIndex());
	 * //cellStyle.setFillPattern((short) 1); //WNLIP
	 * 
	 * cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
	 * 
	 * HSSFFont font = workbook.createFont();
	 * font.setFontName(cellFormatting.getFontName());
	 * font.setFontHeightInPoints(cellFormatting.getFontSize()); if
	 * (cellFormatting.bold) { //font.setBoldweight((short) 700); //WNLIP
	 * font.setBold(true); } font.setColor(cellFormatting.getForeColorIndex());
	 * cellStyle.setFont(font);
	 * 
	 * return cellStyle; }
	 */
	
	private XSSFCellStyle applyCellStyle(XSSFWorkbook workbook, ExcelCellFormatting cellFormatting) {
	    XSSFCellStyle cellStyle = workbook.createCellStyle();
	    if (cellFormatting.centred) {
	        cellStyle.setAlignment(HorizontalAlignment.CENTER);
	    }
	    cellStyle.setFillForegroundColor(cellFormatting.getBackColorIndex());
	    cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

	    XSSFFont font = workbook.createFont();
	    font.setFontName(cellFormatting.getFontName());
	    font.setFontHeightInPoints(cellFormatting.getFontSize());
	    if (cellFormatting.bold) {
	        font.setBold(true);
	    }
	    font.setColor(cellFormatting.getForeColorIndex());
	    cellStyle.setFont(font);

	    return cellStyle;
	}


	public void setValue(int rowNum, int columnNum, String value) {
		setValue(rowNum, columnNum, value, null);
	}

	/*
	 * public void setValue(int rowNum, int columnNum, String value,
	 * ExcelCellFormatting cellFormatting) { // done checkPreRequisites();
	 * 
	 * HSSFWorkbook workbook = openFileForReading(); HSSFSheet worksheet =
	 * getWorkSheet(workbook);
	 * 
	 * HSSFRow row = worksheet.getRow(rowNum); HSSFCell cell =
	 * row.createCell(columnNum); cell.setCellType(1); cell.setCellValue(value);
	 * 
	 * if (cellFormatting != null) { HSSFCellStyle cellStyle =
	 * applyCellStyle(workbook, cellFormatting); cell.setCellStyle(cellStyle); }
	 * 
	 * writeIntoFile(workbook); }
	 */
	
	public void setValue(int rowNum, int columnNum, String value, ExcelCellFormatting cellFormatting) {
	    checkPreRequisites();

	    XSSFWorkbook workbook = openFileForReading();
	    XSSFSheet worksheet = getWorkSheet(workbook);

	    XSSFRow row = worksheet.getRow(rowNum);
	    XSSFCell cell = row.createCell(columnNum);
	    cell.setCellValue(value);

	    if (cellFormatting != null) {
	        XSSFCellStyle cellStyle = applyCellStyle(workbook, cellFormatting);
	        cell.setCellStyle(cellStyle);
	    }

	    writeIntoFile(workbook);
	}


	public void setValue(int rowNum, String columnHeader, String value) {
		setValue(rowNum, columnHeader, value, null);
	}

	/*
	 * public void setValue(int rowNum, String columnHeader, String value,
	 * ExcelCellFormatting cellFormatting) { checkPreRequisites();
	 * 
	 * HSSFWorkbook workbook = openFileForReading(); HSSFSheet worksheet =
	 * getWorkSheet(workbook); FormulaEvaluator formulaEvaluator =
	 * workbook.getCreationHelper().createFormulaEvaluator();
	 * 
	 * HSSFRow row = worksheet.getRow(0); int columnNum = -1;
	 * 
	 * for (int currentColumnNum = 0; currentColumnNum < row.getLastCellNum();) {
	 * HSSFCell cell = row.getCell(currentColumnNum); String currentValue =
	 * getCellValueAsString(cell, formulaEvaluator);
	 * 
	 * if (currentValue.equals(columnHeader)) { columnNum = currentColumnNum; break;
	 * } ++currentColumnNum; }
	 * 
	 * if (columnNum == -1) { throw new
	 * FrameworkException("The specified column header \"" + columnHeader + "\"" +
	 * "is not found in the sheet \"" + this.datasheetName + "\"!"); } row =
	 * worksheet.getRow(rowNum); HSSFCell cell = row.createCell(columnNum);
	 * cell.setCellType(1); cell.setCellValue(value);
	 * 
	 * if (cellFormatting != null) { HSSFCellStyle cellStyle =
	 * applyCellStyle(workbook, cellFormatting); cell.setCellStyle(cellStyle); }
	 * 
	 * writeIntoFile(workbook); }
	 */
	
	public void setValue(int rowNum, String columnHeader, String value, ExcelCellFormatting cellFormatting) {
	    checkPreRequisites();

	    XSSFWorkbook workbook = openFileForReading();
	    XSSFSheet worksheet = getWorkSheet(workbook);
	    FormulaEvaluator formulaEvaluator = workbook.getCreationHelper().createFormulaEvaluator();

	    XSSFRow row = worksheet.getRow(0);
	    int columnNum = -1;

	    for (int currentColumnNum = 0; currentColumnNum < row.getLastCellNum();) {
	        XSSFCell cell = row.getCell(currentColumnNum);
	        String currentValue = getCellValueAsString(cell, formulaEvaluator);

	        if (currentValue.equals(columnHeader)) {
	            columnNum = currentColumnNum;
	            break;
	        }
	        ++currentColumnNum;
	    }

	    if (columnNum == -1) {
	        throw new FrameworkException("The specified column header \"" + columnHeader + "\""
	                + " is not found in the sheet \"" + this.datasheetName + "\"!");
	    }
	    row = worksheet.getRow(rowNum);
	    XSSFCell cell = row.createCell(columnNum);
	    cell.setCellValue(value);

	    if (cellFormatting != null) {
	        XSSFCellStyle cellStyle = applyCellStyle(workbook, cellFormatting);
	        cell.setCellStyle(cellStyle);
	    }

	    writeIntoFile(workbook);
	}

	/*
	 * public void setHyperlink(int rowNum, int columnNum, String linkAddress) {
	 * checkPreRequisites();
	 * 
	 * HSSFWorkbook workbook = openFileForReading(); HSSFSheet worksheet =
	 * getWorkSheet(workbook);
	 * 
	 * HSSFRow row = worksheet.getRow(rowNum); HSSFCell cell =
	 * row.getCell(columnNum); if (cell == null) { throw new
	 * FrameworkException("Specified cell is empty! Please set a value before including a hyperlink..."
	 * ); }
	 * 
	 * setCellHyperlink(workbook, cell, linkAddress);
	 * 
	 * writeIntoFile(workbook); }
	 */
	
	public void setHyperlink(int rowNum, int columnNum, String linkAddress) {
	    checkPreRequisites();

	    XSSFWorkbook workbook = openFileForReading();
	    XSSFSheet worksheet = getWorkSheet(workbook);

	    XSSFRow row = worksheet.getRow(rowNum);
	    XSSFCell cell = row.getCell(columnNum);
	    if (cell == null) {
	        throw new FrameworkException("Specified cell is empty! Please set a value before including a hyperlink...");
	    }

	    setCellHyperlink(workbook, cell, linkAddress);

	    writeIntoFile(workbook);
	}


	
	  private void setCellHyperlink(HSSFWorkbook workbook, HSSFCell cell, String
	  linkAddress) { HSSFCellStyle cellStyle = cell.getCellStyle(); HSSFFont font =
	  cellStyle.getFont(workbook); font.setUnderline((byte) 1);
	  cellStyle.setFont(font);
	  
	  CreationHelper creationHelper = workbook.getCreationHelper(); Hyperlink
	  hyperlink = creationHelper.createHyperlink(HyperlinkType.URL);
	  hyperlink.setAddress(linkAddress);
	  
	  cell.setCellStyle(cellStyle); cell.setHyperlink(hyperlink); }
	 

	private void setCellHyperlink(XSSFWorkbook workbook, XSSFCell cell, String linkAddress) {
	    XSSFCellStyle cellStyle = cell.getCellStyle();
	    XSSFFont font = cellStyle.getFont();
	    font.setUnderline(FontUnderline.SINGLE);
	    cellStyle.setFont(font);

	    CreationHelper creationHelper = workbook.getCreationHelper();
	    Hyperlink hyperlink = creationHelper.createHyperlink(HyperlinkType.URL);
	    hyperlink.setAddress(linkAddress);

	    cell.setCellStyle(cellStyle);
	    cell.setHyperlink(hyperlink);
	}

	/*
	 * public void setHyperlink(int rowNum, String columnHeader, String linkAddress)
	 * { checkPreRequisites();
	 * 
	 * HSSFWorkbook workbook = openFileForReading(); HSSFSheet worksheet =
	 * getWorkSheet(workbook); FormulaEvaluator formulaEvaluator =
	 * workbook.getCreationHelper().createFormulaEvaluator();
	 * 
	 * HSSFRow row = worksheet.getRow(0); int columnNum = -1;
	 * 
	 * for (int currentColumnNum = 0; currentColumnNum < row.getLastCellNum();) {
	 * HSSFCell cell = row.getCell(currentColumnNum); String currentValue =
	 * getCellValueAsString(cell, formulaEvaluator);
	 * 
	 * if (currentValue.equals(columnHeader)) { columnNum = currentColumnNum; break;
	 * } ++currentColumnNum; }
	 * 
	 * if (columnNum == -1) { throw new
	 * FrameworkException("The specified column header \"" + columnHeader + "\"" +
	 * "is not found in the sheet \"" + this.datasheetName + "\"!"); } row =
	 * worksheet.getRow(rowNum); HSSFCell cell = row.getCell(columnNum); if (cell ==
	 * null) { throw new
	 * FrameworkException("Specified cell is empty! Please set a value before including a hyperlink..."
	 * ); }
	 * 
	 * setCellHyperlink(workbook, cell, linkAddress);
	 * 
	 * writeIntoFile(workbook); }
	 */
	
	public void setHyperlink(int rowNum, String columnHeader, String linkAddress) {
	    checkPreRequisites();

	    XSSFWorkbook workbook = openFileForReading();
	    XSSFSheet worksheet = getWorkSheet(workbook);
	    FormulaEvaluator formulaEvaluator = workbook.getCreationHelper().createFormulaEvaluator();

	    XSSFRow row = worksheet.getRow(0);
	    int columnNum = -1;

	    for (int currentColumnNum = 0; currentColumnNum < row.getLastCellNum();) {
	        XSSFCell cell = row.getCell(currentColumnNum);
	        String currentValue = getCellValueAsString(cell, formulaEvaluator);

	        if (currentValue.equals(columnHeader)) {
	            columnNum = currentColumnNum;
	            break;
	        }
	        ++currentColumnNum;
	    }

	    if (columnNum == -1) {
	        throw new FrameworkException("The specified column header \"" + columnHeader + "\""
	                + " is not found in the sheet \"" + this.datasheetName + "\"!");
	    }
	    row = worksheet.getRow(rowNum);
	    XSSFCell cell = row.getCell(columnNum);
	    if (cell == null) {
	        throw new FrameworkException("Specified cell is empty! Please set a value before including a hyperlink...");
	    }

	    setCellHyperlink(workbook, cell, linkAddress);

	    writeIntoFile(workbook);
	}


	/*
	 * public void createWorkbook() { HSSFWorkbook workbook = new HSSFWorkbook();
	 * 
	 * writeIntoFile(workbook); }
	 */
	
	public void createWorkbook() {
	    XSSFWorkbook workbook = new XSSFWorkbook();

	    writeIntoFile(workbook);
	}


	/*
	 * public void addSheet(String sheetName) { HSSFWorkbook workbook =
	 * openFileForReading();
	 * 
	 * HSSFSheet worksheet = workbook.createSheet(sheetName);
	 * worksheet.createRow(0);
	 * 
	 * writeIntoFile(workbook);
	 * 
	 * this.datasheetName = sheetName; }
	 */
	
	public void addSheet(String sheetName) {
	    XSSFWorkbook workbook = openFileForReading();

	    XSSFSheet worksheet = workbook.createSheet(sheetName);
	    worksheet.createRow(0);

	    writeIntoFile(workbook);

	    this.datasheetName = sheetName;
	}


	/*
	 * public int addRow() { checkPreRequisites();
	 * 
	 * HSSFWorkbook workbook = openFileForReading(); HSSFSheet worksheet =
	 * getWorkSheet(workbook);
	 * 
	 * int newRowNum = worksheet.getLastRowNum() + 1;
	 * worksheet.createRow(newRowNum);
	 * 
	 * writeIntoFile(workbook);
	 * 
	 * return newRowNum; }
	 */

	public int addRow() {
	    checkPreRequisites();

	    XSSFWorkbook workbook = openFileForReading();
	    XSSFSheet worksheet = getWorkSheet(workbook);

	    int newRowNum = worksheet.getLastRowNum() + 1;
	    worksheet.createRow(newRowNum);

	    writeIntoFile(workbook);

	    return newRowNum;
	}

	
	public void addColumn(String columnHeader) {
		addColumn(columnHeader, null);
	}

	/*
	 * public void addColumn(String columnHeader, ExcelCellFormatting
	 * cellFormatting) { checkPreRequisites();
	 * 
	 * HSSFWorkbook workbook = openFileForReading(); HSSFSheet worksheet =
	 * getWorkSheet(workbook);
	 * 
	 * HSSFRow row = worksheet.getRow(0); int lastCellNum = row.getLastCellNum(); if
	 * (lastCellNum == -1) { lastCellNum = 0; }
	 * 
	 * HSSFCell cell = row.createCell(lastCellNum); cell.setCellType(1);
	 * cell.setCellValue(columnHeader);
	 * 
	 * if (cellFormatting != null) { HSSFCellStyle cellStyle =
	 * applyCellStyle(workbook, cellFormatting); cell.setCellStyle(cellStyle); }
	 * 
	 * writeIntoFile(workbook); }
	 */
	
	public void addColumn(String columnHeader, ExcelCellFormatting cellFormatting) {
	    checkPreRequisites();

	    XSSFWorkbook workbook = openFileForReading();
	    XSSFSheet worksheet = getWorkSheet(workbook);

	    XSSFRow row = worksheet.getRow(0);
	    int lastCellNum = row.getLastCellNum();
	    if (lastCellNum == -1) {
	        lastCellNum = 0;
	    }

	    XSSFCell cell = row.createCell(lastCellNum);
	    cell.setCellValue(columnHeader);

	    if (cellFormatting != null) {
	        XSSFCellStyle cellStyle = applyCellStyle(workbook, cellFormatting);
	        cell.setCellStyle(cellStyle);
	    }

	    writeIntoFile(workbook);
	}

	/*
	 * public void setCustomPaletteColor(int i, String hexColor) { HSSFWorkbook
	 * workbook = openFileForReading(); HSSFPalette palette =
	 * workbook.getCustomPalette();
	 * 
	 * if ((i < 8) || (i > 64)) { throw new FrameworkException(
	 * "Valid indexes for the Excel custom palette are from 0x8 to 0x40 (inclusive)!"
	 * ); }
	 * 
	 * Color color = Color.decode(hexColor); palette.setColorAtIndex((short) i,
	 * (byte) color.getRed(), (byte) color.getGreen(), (byte) color.getBlue());
	 * 
	 * writeIntoFile(workbook); }
	 */
	
	/*
	 * public void setCustomPaletteColor(int i, String hexColor) { XSSFWorkbook
	 * workbook = openFileForReading(); XSSFPalette palette =
	 * workbook.getCustomPalette();
	 * 
	 * if ((i < 8) || (i > 64)) { throw new FrameworkException(
	 * "Valid indexes for the Excel custom palette are from 0x8 to 0x40 (inclusive)!"
	 * ); }
	 * 
	 * Color color = Color.decode(hexColor); palette.setColorAtIndex((short) i,
	 * (byte) color.getRed(), (byte) color.getGreen(), (byte) color.getBlue());
	 * 
	 * writeIntoFile(workbook); }
	 */
	
	public void setCustomPaletteColor(int i, String hexColor) {
	    XSSFWorkbook workbook = openFileForReading();
	    
	    if ((i < 8) || (i > 64)) {
	        throw new FrameworkException(
	                "Valid indexes for the Excel custom palette are from 0x8 to 0x40 (inclusive)!");
	    }

	    Color color = Color.decode(hexColor);

	    XSSFColor xssfColor = new XSSFColor(new java.awt.Color(color.getRed(), color.getGreen(), color.getBlue()));
	    
	    // Assuming your workbook has only one style
	    XSSFCellStyle style = workbook.createCellStyle();
	    style.setFillForegroundColor(xssfColor);
	    style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
	    
	    // Apply the style to a cell in the sheet
	    XSSFSheet sheet = workbook.getSheetAt(0);
	    XSSFRow row = sheet.getRow(0);
	    XSSFCell cell = row.createCell(0);
	    cell.setCellStyle(style);

	    writeIntoFile(workbook);
	}



	/*
	 * public void mergeCells(int firstRow, int lastRow, int firstCol, int lastCol)
	 * { checkPreRequisites();
	 * 
	 * HSSFWorkbook workbook = openFileForReading(); HSSFSheet worksheet =
	 * getWorkSheet(workbook);
	 * 
	 * CellRangeAddress cellRangeAddress = new CellRangeAddress(firstRow, lastRow,
	 * firstCol, lastCol); worksheet.addMergedRegion(cellRangeAddress);
	 * 
	 * writeIntoFile(workbook); }
	 */
	
	public void mergeCells(int firstRow, int lastRow, int firstCol, int lastCol) {
	    checkPreRequisites();

	    XSSFWorkbook workbook = openFileForReading();
	    XSSFSheet worksheet = getWorkSheet(workbook);

	    CellRangeAddress cellRangeAddress = new CellRangeAddress(firstRow, lastRow, firstCol, lastCol);
	    worksheet.addMergedRegion(cellRangeAddress);

	    writeIntoFile(workbook);
	}

	
	/*
	 * public void setRowSumsBelow(boolean rowSumsBelow) { checkPreRequisites();
	 * 
	 * HSSFWorkbook workbook = openFileForReading(); HSSFSheet worksheet =
	 * getWorkSheet(workbook);
	 * 
	 * worksheet.setRowSumsBelow(rowSumsBelow);
	 * 
	 * writeIntoFile(workbook); }
	 */
	public void setRowSumsBelow(boolean rowSumsBelow) {
	    checkPreRequisites();

	    XSSFWorkbook workbook = openFileForReading();
	    XSSFSheet worksheet = getWorkSheet(workbook);

	    worksheet.setRowSumsBelow(rowSumsBelow);

	    writeIntoFile(workbook);
	}


	/*
	 * public void groupRows(int firstRow, int lastRow) { checkPreRequisites();
	 * 
	 * HSSFWorkbook workbook = openFileForReading(); HSSFSheet worksheet =
	 * getWorkSheet(workbook);
	 * 
	 * worksheet.groupRow(firstRow, lastRow);
	 * 
	 * writeIntoFile(workbook); }
	 */
	
	public void groupRows(int firstRow, int lastRow) {
	    checkPreRequisites();

	    XSSFWorkbook workbook = openFileForReading();
	    XSSFSheet worksheet = getWorkSheet(workbook);

	    worksheet.groupRow(firstRow, lastRow);

	    writeIntoFile(workbook);
	}


	/*
	 * public void autoFitContents(int firstCol, int lastCol) {
	 * checkPreRequisites();
	 * 
	 * HSSFWorkbook workbook = openFileForReading(); HSSFSheet worksheet =
	 * getWorkSheet(workbook);
	 * 
	 * if (firstCol < 0) { firstCol = 0; }
	 * 
	 * if (firstCol > lastCol) { throw new
	 * FrameworkException("First column cannot be greater than last column!"); }
	 * 
	 * for (int currentColumn = firstCol; currentColumn <= lastCol;) {
	 * worksheet.autoSizeColumn(currentColumn);
	 * 
	 * ++currentColumn; }
	 * 
	 * writeIntoFile(workbook); }
	 */

	public void autoFitContents(int firstCol, int lastCol) {
	    checkPreRequisites();

	    XSSFWorkbook workbook = openFileForReading();
	    XSSFSheet worksheet = getWorkSheet(workbook);

	    if (firstCol < 0) {
	        firstCol = 0;
	    }

	    if (firstCol > lastCol) {
	        throw new FrameworkException("First column cannot be greater than last column!");
	    }

	    for (int currentColumn = firstCol; currentColumn <= lastCol;) {
	        worksheet.autoSizeColumn(currentColumn);

	        ++currentColumn;
	    }

	    writeIntoFile(workbook);
	}

	/*
	 * public void addOuterBorder(int firstCol, int lastCol) { checkPreRequisites();
	 * 
	 * HSSFWorkbook workbook = openFileForReading(); HSSFSheet worksheet =
	 * getWorkSheet(workbook);
	 * 
	 * CellRangeAddress cellRangeAddress = new CellRangeAddress(0,
	 * worksheet.getLastRowNum(), firstCol, lastCol);
	 * HSSFRegionUtil.setBorderBottom(1, cellRangeAddress, worksheet, workbook);
	 * HSSFRegionUtil.setBorderRight(1, cellRangeAddress, worksheet, workbook);
	 * 
	 * writeIntoFile(workbook); }
	 */

	/* 
	 * outer border removed: LT
	 * public void addOuterBorder(int firstCol, int lastCol) { checkPreRequisites();
	 * 
	 * XSSFWorkbook workbook = openFileForReading(); XSSFSheet worksheet =
	 * getWorkSheet(workbook);
	 * 
	 * CellRangeAddress cellRangeAddress = new CellRangeAddress(0,
	 * worksheet.getLastRowNum(), firstCol, lastCol);
	 * XSSFRegionUtil.setBorderBottom(1, cellRangeAddress, worksheet);
	 * XSSFRegionUtil.setBorderRight(1, cellRangeAddress, worksheet);
	 * 
	 * writeIntoFile(workbook); }
	 */

	
	/*
	 * public void addOuterBorder(int firstRow, int lastRow, int firstCol, int
	 * lastCol) { checkPreRequisites();
	 * 
	 * HSSFWorkbook workbook = openFileForReading(); HSSFSheet worksheet =
	 * getWorkSheet(workbook);
	 * 
	 * CellRangeAddress cellRangeAddress = new CellRangeAddress(firstRow, lastRow,
	 * firstCol, lastCol); HSSFRegionUtil.setBorderBottom(1, cellRangeAddress,
	 * worksheet, workbook); HSSFRegionUtil.setBorderTop(1, cellRangeAddress,
	 * worksheet, workbook); HSSFRegionUtil.setBorderRight(1, cellRangeAddress,
	 * worksheet, workbook); HSSFRegionUtil.setBorderLeft(1, cellRangeAddress,
	 * worksheet, workbook);
	 * 
	 * writeIntoFile(workbook); }
	 */
	/*
	 * outer border removed: LT
	 * public void addOuterBorder(int firstRow, int lastRow, int firstCol, int
	 * lastCol) { checkPreRequisites();
	 * 
	 * XSSFWorkbook workbook = openFileForReading(); XSSFSheet worksheet =
	 * getWorkSheet(workbook);
	 * 
	 * CellRangeAddress cellRangeAddress = new CellRangeAddress(firstRow, lastRow,
	 * firstCol, lastCol); XSSFRegionUtil.setBorderBottom(1, cellRangeAddress,
	 * worksheet); XSSFRegionUtil.setBorderTop(1, cellRangeAddress, worksheet);
	 * XSSFRegionUtil.setBorderRight(1, cellRangeAddress, worksheet);
	 * XSSFRegionUtil.setBorderLeft(1, cellRangeAddress, worksheet);
	 * 
	 * writeIntoFile(workbook); }
	 */

}