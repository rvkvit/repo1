package com.LT.framework.selenium;

import com.LT.framework.FrameworkException;
import com.LT.framework.Settings;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;


import org.openqa.selenium.Platform;
import org.openqa.selenium.Proxy;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;

import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
//import org.openqa.selenium.firefox.internal.ProfilesIni;
//import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;



public class WebDriverFactory {
	private static Properties properties;
	private static Properties execution_properties;

	static String projectDirectory = System.getProperty("user.dir");
	static String os = System.getProperty("os.name");

	public static WebDriver getDriver(Browser browser)
	{
		WebDriver driver = null;
		properties = Settings.getInstance();
		boolean proxyRequired = 
				Boolean.parseBoolean(properties.getProperty("ProxyRequired"));
		System.out.println("OS name is:" +os);
		switch (browser)
		{
		case Chrome:
			if(os.toLowerCase().contains("win")) {
				properties.setProperty("ChromeDriverPath", projectDirectory + File.separator + "\\driver\\chromedriver.exe");
			}
			else if (os.toLowerCase().contains("mac")){
				// to be updated 
			}
			else if (os.toLowerCase().contains("linux")){
				properties.setProperty("ChromeDriverPath", "./Driver/Linux/chromedriver");
			}

			System.setProperty("webdriver.chrome.driver",properties.getProperty("ChromeDriverPath")); 
			ChromeOptions options = new ChromeOptions();
			options.addArguments("--remote-allow-origins=*");
			//options.addArguments("--incognito");   
			driver = new ChromeDriver(options);
			break;

		case Firefox:
			properties.setProperty("FireFoxDriverPath", projectDirectory + File.separator + "\\driver\\geckodriver.exe");
			System.setProperty("webdriver.firefox.driver",properties.getProperty("FireFoxDriverPath")); 
			driver = new FirefoxDriver();
			/*
			 * Changes made to enable firefox window pop up haldles 
			 */
			/* rvk recent change
			 * System.setProperty("webdriver.firefox.profile", "MySeleniumProfile");
			 * DesiredCapabilities desiredCapabilities1 = new DesiredCapabilities("firefox",
			 * "", Platform.ANY); FirefoxProfile profile1 = new
			 * ProfilesIni().getProfile("MySeleniumProfile");
			 * desiredCapabilities1.setCapability("firefox_profile", profile1);
			 */
			//WebDriver webdriver = new RemoteWebDriver(desiredCapabilities1);
			/*FirefoxProfile profile = new FirefoxProfile();
			      profile.setAcceptUntrustedCertificates( true );
			      profile.setPreference( "security.enable_java", true ); 
			      profile.setPreference("plugin.state.java", 2);
			// driver = new FirefoxDriver(profile1);
			//driver = new FirefoxDriver(desiredCapabilities1);
			break;
		case HtmlUnit:
			/* driver = new HtmlUnitDriver();

			      if (!(proxyRequired)) break ;
			      //label266
			      boolean proxyAuthenticationRequired = 
			        Boolean.parseBoolean(properties.getProperty("ProxyAuthenticationRequired"));
			      if (proxyAuthenticationRequired)
			      {
			        driver = new HtmlUnitDriver()
			        {
			          protected WebClient modifyWebClient(WebClient client) {
			            DefaultCredentialsProvider credentialsProvider = new DefaultCredentialsProvider();
			            credentialsProvider.addNTLMCredentials(WebDriverFactory.properties.getProperty("Username"), 
			              WebDriverFactory.properties.getProperty("Password"), 
			              WebDriverFactory.properties.getProperty("ProxyHost"), 
			              Integer.parseInt(WebDriverFactory.properties.getProperty("ProxyPort")), 
			              "", WebDriverFactory.properties.getProperty("Domain"));
			            client.setCredentialsProvider(credentialsProvider);
			            return client;
			          }
			        };
			      }

			      ((HtmlUnitDriver)driver).setProxy(properties.getProperty("ProxyHost"), 
			        Integer.parseInt(properties.getProperty("ProxyPort")));
			 */
			break;
		case InternetExplorer:
			properties.setProperty("InternetExplorerDriverPath", projectDirectory + File.separator + "\\driver\\internetexplorer.exe");
			System.setProperty("webdriver.ie.driver", 
					properties.getProperty("InternetExplorerDriverPath"));
			driver = new InternetExplorerDriver();
			break;

		case Edge:
			properties.setProperty("EdgeDriverPath", projectDirectory + File.separator + "\\driver\\msedgedriver.exe");
			System.setProperty("webdriver.edge.driver", 
					properties.getProperty("EdgeDriverPath"));
			EdgeOptions edgeOptions = new EdgeOptions();
			String username1 = System.getProperty("user.name");
			edgeOptions.addArguments("user-data-dir=C://Users//"+username1+"//AppData//Local//Microsoft//Edge//User Data");
			edgeOptions.addArguments("profile-directory=Default");
			//edgeOptions.addArguments("--start-maximized");
			//edgeOptions.addArguments("disable-infobars"); // disabling infobars
			//edgeOptions.addArguments("--disable-extensions"); // disabling extensions
			//edgeOptions.addArguments("--disable-gpu"); // applicable to windows os only
			edgeOptions.addArguments("--disable-dev-shm-usage"); // overcome limited resource problems
			edgeOptions.addArguments("--no-sandbox"); // Bypass OS security model
			driver = new EdgeDriver(edgeOptions); 
			break;
		case Safari:
			driver = new SafariDriver();
			break;
		default:
			throw new FrameworkException("Unhandled browser!");
		}
		return driver;
	}

	private static DesiredCapabilities getProxyCapabilities() {
		Proxy proxy = new Proxy();
		proxy.setProxyType(Proxy.ProxyType.MANUAL);

		properties = Settings.getInstance();
		String proxyUrl = properties.getProperty("ProxyHost") + ":" + properties.getProperty("ProxyPort");

		proxy.setHttpProxy(proxyUrl);
		proxy.setFtpProxy(proxyUrl);
		proxy.setSslProxy(proxyUrl);
		DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
		desiredCapabilities.setCapability("proxy", proxy);

		return desiredCapabilities;
	}

	public static WebDriver getDriver(Browser browser, String remoteUrl) {
		return getDriver(browser, null, null, remoteUrl);
	}

	public static WebDriver getDriver(Browser browser, String browserVersion, Platform platform, String remoteUrl) {
		properties = Settings.getInstance();
		boolean proxyRequired = Boolean.parseBoolean(properties.getProperty("ProxyRequired"));

		DesiredCapabilities desiredCapabilities = null;
		if ((((browser.equals(Browser.HtmlUnit)) || (browser.equals(Browser.Opera)))) && (proxyRequired))
			desiredCapabilities = getProxyCapabilities();
		else {
			desiredCapabilities = new DesiredCapabilities();
		}

		desiredCapabilities.setBrowserName(browser.getValue());

		if (browserVersion != null) {
			desiredCapabilities.setVersion(browserVersion);
		}
		if (platform != null) {
			desiredCapabilities.setPlatform(platform);
		}

		//desiredCapabilities.setJavascriptEnabled(true);
		URL url;
		try {
			url = new URL(remoteUrl);
		} catch (MalformedURLException e) {
			e.printStackTrace();
			throw new FrameworkException("The specified remote URL is malformed");
		}
		return new RemoteWebDriver(url, desiredCapabilities);
	}

	public static WebDriver getDriver() throws MalformedURLException {

		properties = Settings.getInstance();
		execution_properties = Settings.getExecutionInstance();

		DesiredCapabilities capabilities = new DesiredCapabilities();
		String username = properties.getProperty("BROWSERSTACK_USERNAME");
		String accessKey = properties.getProperty("BROWSERSTACK_ACCESS_KEY");
		String server = properties.getProperty("BROWSERSTACK_SERVER_URL");

		String remoteUrl = "https://" + username + ":" + accessKey + "@" + server + "/wd/hub";
		String execution_Time = null;
		String application_Name = null;
		try {
			execution_Time = getBrowserStackExecutionTime("BROWSERSTACK_EXECUTION_TIME");
			application_Name = getBrowserStackExecutionTime("APPLICATION");

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		capabilities.setCapability("project", "Motor Automation");
		capabilities.setCapability("build","Latest Run For "+application_Name+" @"+execution_Time);
		//capabilities.setCapability("project", "Motor FNOL");
		//			capabilities.setCapability("build",
		//					"LatestRunFor Motor FNOL");
		//capabilities.setCapability("name", strScenarioName);
		capabilities.setCapability("os", properties.getProperty("BROWSERSTACK_OS"));
		capabilities.setCapability("os_version", properties.getProperty("BROWSERSTACK_OS_VERSION"));
		capabilities.setCapability("resolution", "1280x1024");
		capabilities.setCapability("acceptSslCerts", "true");
		capabilities.setCapability("browserstack.debug", "true");
		capabilities.setCapability("browserstack.local", "true");

		/*
		 * To view Network Logs in BS
		 * 
		 * @author Rovin
		 */
		HashMap<String, Boolean> networkLogsOptions = new HashMap<>();
		networkLogsOptions.put("captureContent", true);
		capabilities.setCapability("browserstack.networkLogs", true);
		capabilities.setCapability("browserstack.networkLogsOptions", networkLogsOptions);
		/*-------------------------------------------------------------------------------*/


		//			capabilities.setCapability("browser", "chrome");
		//			capabilities.setCapability("browser_version", "latest");

		capabilities.setCapability("browser", "chrome");
		capabilities.setCapability("browser_version", "latest");
		ChromeOptions options = new ChromeOptions();
		//options.addArguments("--incognito");                   # commenting to check issues related to smoke daily trigger pipeline
		if(properties.getProperty("HEADLESS_MODE") == "TRUE") {
			options.addArguments("--headless");
		}
		capabilities.setCapability(ChromeOptions.CAPABILITY, options);	


		URL url;
		try {
			url = new URL("https://" + username + ":" + accessKey + "@" + server + "/wd/hub");
		} catch (MalformedURLException e) {
			e.printStackTrace();
			throw new FrameworkException("The specified remote URL is malformed");
		}
		WebDriver driver = new RemoteWebDriver(url, capabilities);

		// Get the session ID
		String sessionId = ((RemoteWebDriver) driver).getSessionId().toString();
		System.out.println("Session ID: " + sessionId);

		updatePropertyValue("BROWSERSTACK_SESSION_ID", sessionId);
		return driver;
	}

	public static String getBrowserStackExecutionTime( String key) {
		String currentDir = System.getProperty("user.dir");
		String os = System.getProperty("os.name");
		System.out.println("Operating System: " + os);
		String filePath;
		// Construct the file path using the current directory and the provided file name
		if(os.toLowerCase().contains("linux")) {
			filePath = currentDir + File.separator + "ExecutionSettings.properties";
			System.out.println("File path at linux " + filePath);
		}
		else {
			filePath = currentDir + File.separator + "ExecutionSettings.properties";
			System.out.println("File path at windows " + filePath);
		}
		Properties properties = new Properties();
		FileInputStream fileInputStream = null;
		String value= null;
		try {
			// Provide the path to your properties file
			fileInputStream = new FileInputStream(filePath);
			properties.load(fileInputStream);

			// Get the value of a specific key
			value = properties.getProperty(key);
			System.out.println("Value of 'yourKey': " + value);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (fileInputStream != null) {
				try {
					fileInputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return value;
	}

	// Update time in settings.property file 
	public static void updatePropertyValue( String key, String value) {
		String currentDir = System.getProperty("user.dir");
		String os = System.getProperty("os.name");
		System.out.println("Operating System: " + os);
		String filePath;
		// Construct the file path using the current directory and the provided file name
		if(os.toLowerCase().contains("linux")) {
			filePath = currentDir + File.separator + "ExecutionSettings.properties";
			System.out.println("File path at linux " + filePath);
		}
		else {
			filePath = currentDir + File.separator + "ExecutionSettings.properties";
			System.out.println("File path at windows " + filePath);
		}
		// Read the existing properties file
		Map<String, String> propertiesMap = new HashMap<>();
		try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
			String line;
			while ((line = reader.readLine()) != null) {
				if (line.trim().startsWith(key + " ")) {
					// Update the value for the specified key
					line = key + " = " + value;
				}
				propertiesMap.put(line.split("=")[0].trim(), line);
			}
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
		// Write the modified properties back to the file
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
			for (String line : propertiesMap.values()) {
				writer.write(line);
				writer.newLine();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
