package com.LT.framework;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class Settings {
	private static Properties properties = loadFromPropertiesFile();
	private static Properties execution_properties = loadFromExecutionSettingsPropertiesFile();

	public static Properties getInstance() {
		return properties;
	}
	
	public static Properties getExecutionInstance() {
		return execution_properties;
	}

	private static Properties loadFromPropertiesFile() {
		FrameworkParameters frameworkParameters = FrameworkParameters.getInstance();

		if (frameworkParameters.getRelativePath() == null) {
			throw new FrameworkException("FrameworkParameters.relativePath is not set!");
		}

		Properties properties = new Properties();
		try {
			properties.load(new FileInputStream(
					frameworkParameters.getRelativePath() + Util.getFileSeparator() + "Global Settings.properties"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			throw new FrameworkException("FileNotFoundException while loading the Global Settings file");
		} catch (IOException e) {
			e.printStackTrace();
			throw new FrameworkException("IOException while loading the Global Settings file");
		}

		return properties;
	}

	private static Properties loadFromExecutionSettingsPropertiesFile() {
		FrameworkParameters frameworkParameters = FrameworkParameters.getInstance();

		if (frameworkParameters.getRelativePath() == null) {
			throw new FrameworkException("FrameworkParameters.relativePath is not set!");
		}

		Properties execution_properties = new Properties();
		try {
			properties.load(new FileInputStream(
					frameworkParameters.getRelativePath() + Util.getFileSeparator() + "ExecutionSettings.properties"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			throw new FrameworkException("FileNotFoundException while loading the Execution Settings file");
		} catch (IOException e) {
			e.printStackTrace();
			throw new FrameworkException("IOException while loading the Execution Settings file");
		}

		return execution_properties;
	}
	public Object clone() throws CloneNotSupportedException {
		throw new CloneNotSupportedException();
	}
}