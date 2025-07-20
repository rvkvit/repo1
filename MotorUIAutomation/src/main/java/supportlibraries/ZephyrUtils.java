package supportlibraries;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Collectors;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;




import com.LT.framework.*;
import com.LT.framework.selenium.SeleniumReport;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;


public class ZephyrUtils {


	private static Properties properties = loadFromPropertiesFile();
	private SeleniumReport report;


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

	
	private static String jiraBasUrl = properties.getProperty("JIRA_BASE_URL");
	
	private static String zephyrBaseUrl = properties.getProperty("ZEPHYR_BASE_URL");
	private static Logger log = Logger.getLogger("devpinoyLogger");


	
	private static String user = properties.getProperty("user");
	private static String authenticationToken = properties.getProperty("authenticationToken");
	private static String projectName = properties.getProperty("projectName");
	private static String accessKey = properties.getProperty("accessKey");
	private static String secretKeyString = properties.getProperty("secretKeyString");
	private static String accountID = properties.getProperty("accountID");
	private static String cycleName = properties.getProperty("cycleName");
	private String auth = user + ":" + authenticationToken;
	private byte[] encodedBytes = Base64.getEncoder().encode(auth.getBytes(StandardCharsets.UTF_8));
	private String encodedAuth = new String(encodedBytes, StandardCharsets.UTF_8);

	public int getProjectId(String apiPath) {
		int projectId = 0;
		String urlEndpoint = jiraBasUrl + apiPath + projectName;

		HttpClient client = HttpClientBuilder.create().build();
		HttpGet request = new HttpGet(urlEndpoint);
		request.setHeader("Content-Type", "application/json");
		request.setHeader("Authorization", "Basic " + encodedAuth);

		try {
			HttpResponse response = client.execute(request);
			int statusCode = response.getStatusLine().getStatusCode();

			if (statusCode == 404) {
				System.out.println("Project with name " + projectName + ", does not exists");
				log.error("Project with name " + projectName + ", does not exists");
				report.updateTestLog("Execution Info",
						"Project with name " + projectName + ", does not exists",
						Status.FAIL);
			} else if (statusCode == 500) {
				System.out.println("Error 500, there can be multiple issues, check parameters");
				log.error("Error 500, there can be multiple issues, check parameters");
				report.updateTestLog("Execution Info",
						"Error 500, there can be multiple issues, check parameters, function getProjectID",
						Status.FAIL);
			} else if (statusCode == 200) {
				BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
				String jsonString = rd.lines().collect(Collectors.joining());				
				JSONParser parser = new JSONParser();
				JSONObject json = (JSONObject) parser.parse(jsonString);
				projectId = Integer.parseInt((String) json.get("id"));
			} else {
				System.out.println("Unknown error occured");
				log.error("Unknown error occured");
			}
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return projectId;
	}

	public int getSessionId(String apiPath, String issueKey) {
		int issueId = 0;
		String urlEndpoint = jiraBasUrl + apiPath + issueKey + "?fields=id";

		HttpClient client = HttpClientBuilder.create().build();
		HttpGet request = new HttpGet(urlEndpoint);
		request.setHeader("Content-Type", "application/json");
		request.setHeader("Authorization", "Basic " + encodedAuth);

		try {
			HttpResponse response = client.execute(request);
			int statusCode = response.getStatusLine().getStatusCode();

			if (statusCode == 404) {
				System.out.println("Issue with key " + issueKey + ", does not exists");
				log.error("Issue with key " + issueKey + ", does not exists");
				report.updateTestLog("Execution Info",
						"Issue with key " + issueKey + ", does not exists",
						Status.FAIL);
				
			} else if (statusCode == 500) {
				System.out.println("Error 500, there can be multiple issues, check parameters");
				log.error("Error 500, there can be multiple issues, check parameters");
				report.updateTestLog("Execution Info",
						"Error 500, there can be multiple issues, check parameters, function getSessionID",
						Status.FAIL);
			} else if (statusCode == 200) {
				BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
				String jsonString = rd.lines().collect(Collectors.joining());				
				JSONParser parser = new JSONParser();
				JSONObject json = (JSONObject) parser.parse(jsonString);
				issueId = Integer.parseInt((String) json.get("id"));
			} else {
				System.out.println("Unknown error occured");
				log.error("Unknown error occured");
			}
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return issueId;
	}

	public String getCycleId(String apiPath, String canonicalPath, int projectId) {
		String cycleId = null;
		String urlEndpoint = zephyrBaseUrl + apiPath + "?projectId=" + projectId + "&versionId=-1";
		String getAllCyclesCanonicalPath = canonicalPath + "&projectId=" + projectId + "&versionId=-1";
		String jtwToken = getJWTToken(getAllCyclesCanonicalPath);

		try {
			HttpClient client = HttpClientBuilder.create().build();
			HttpGet request = new HttpGet(urlEndpoint);
			request.setHeader("Authorization", "JWT " + jtwToken);
			request.setHeader("Content-Type", "text/plain");
			request.setHeader("zapiAccessKey", accessKey);

			HttpResponse response = client.execute(request);
			int statusCode = response.getStatusLine().getStatusCode();

			if (statusCode == 404) {
				System.out.println("Cycle with key " + cycleId + ", does not exists");
				log.error("Cycle with key " + cycleId + ", does not exists");
				report.updateTestLog("Execution Info",
						"Cycle with key " + cycleId + ", does not exists",
						Status.FAIL);
			} else if (statusCode == 500) {
				System.out.println("Error 500, there can be multiple issues, check parameters");
				log.error("Error 500, there can be multiple issues, check parameters");
				report.updateTestLog("Execution Info",
						"Error 500, there can be multiple issues, check parameters, function getCycleID",
						Status.FAIL);
			} else if (statusCode == 200) {
				BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
				String jsonString = rd.lines().collect(Collectors.joining());				
				JSONParser parser = new JSONParser();
				JSONArray jsonArray = (JSONArray) parser.parse(jsonString);
				for (Object obj : jsonArray) {
					if (obj instanceof JSONObject) {
						JSONObject jsonObject = (JSONObject) obj;
						String cycleNamevalue = (String) jsonObject.get("name");
						if (cycleNamevalue.equalsIgnoreCase(cycleName)) {
							return cycleId = (String) jsonObject.get("id");
						}
					}
				}
			} else {
				System.out.println("Unknown error occured");
				log.error("Unknown error occured");
			}
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return cycleId;
	}

	public String getFolderId(String apiPath, String canonicalPath, int projectId, String cycleId, String targetFolderName) {
		String folderId = null;
		String urlEndpoint = zephyrBaseUrl + apiPath + "?&cycleId=" + cycleId + "&projectId=" + projectId + "&versionId=-1";
		String folderCanonicalPath = canonicalPath + "&cycleId=" + cycleId + "&projectId=" + projectId + "&versionId=-1";
		String jtwToken = getJWTToken(folderCanonicalPath);

		try {
			HttpClient client = HttpClientBuilder.create().build();
			HttpGet request = new HttpGet(urlEndpoint);
			request.setHeader("Authorization", "JWT " + jtwToken);
			request.setHeader("Content-Type", "text/plain");
			request.setHeader("zapiAccessKey", accessKey);

			HttpResponse response = client.execute(request);
			int statusCode = response.getStatusLine().getStatusCode();

			if (statusCode == 404) {
				System.out.println("Folder with name " + targetFolderName + ", does not exists");
				log.error("Folder with name " + targetFolderName + ", does not exists");
			} else if (statusCode == 500) {
				System.out.println("Error 500, there can be multiple issues, check parameters");
				log.error("Error 500, there can be multiple issues, check parameters");
			} else if (statusCode == 200) {
				BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
				String jsonString = rd.lines().collect(Collectors.joining());			
				JSONParser parser = new JSONParser();
				JSONArray jsonArray = (JSONArray) parser.parse(jsonString);
				for (Object obj : jsonArray) {
					if (obj instanceof JSONObject) {
						JSONObject jsonObject = (JSONObject) obj;
						String folderNamevalue = (String) jsonObject.get("name");
						if (folderNamevalue.equalsIgnoreCase(targetFolderName)) {
							return folderId = (String) jsonObject.get("id");
						}
					}
				}
			} else {
				System.out.println("Unknown error occured");
				log.error("Unknown error occured");
			}
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return folderId;
	}

	public String getJIRAExecutionId(String apiPath, String canonicalPath, int projectId, String cycleId,
			String folderId, int issueId) {
		String executionId = null;
		String urlEndpoint = zephyrBaseUrl + apiPath;
		String jwtToken = getJWTToken(canonicalPath);

		HashMap<String, Object> executionData = new HashMap<String, Object>();
		executionData.put("cycleId", cycleId);
		executionData.put("issueId", issueId);
		executionData.put("projectId", projectId);
		executionData.put("folderId", folderId);
		executionData.put("versionId", -1);

		try {
			JSONObject executionJson = new JSONObject(executionData);
			StringEntity params = new StringEntity(executionJson.toString());

			HttpClient client = HttpClientBuilder.create().build();
			HttpPost postRequest = new HttpPost(urlEndpoint);
			postRequest.setHeader("Authorization", "JWT " + jwtToken);
			postRequest.setHeader("Content-Type", "application/json");
			postRequest.setHeader("zapiAccessKey", accessKey);
			postRequest.setEntity(params);

			HttpResponse response = client.execute(postRequest);
			int statusCode = response.getStatusLine().getStatusCode();

			if (statusCode == 404) {
				System.out.println("Execution id does not exists");
				log.error("Execution id does not exists");
			} else if (statusCode == 500) {
				System.out.println("Error 500, there can be multiple issues, check parameters");
				log.error("Error 500, there can be multiple issues, check parameters");
			} else if (statusCode == 200) {
				BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
				String jsonString = rd.lines().collect(Collectors.joining());				
				JSONParser parser = new JSONParser();
				JSONObject json = (JSONObject) parser.parse(jsonString);
				JSONObject executionObject = (JSONObject) json.get("execution");
				executionId = (String) executionObject.get("id");
			} else {
				System.out.println("Unknown error occured");
				log.error("Unknown error occured");
			}
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return executionId;
	}

	public void updateJIRAExecutionStatus(String apiPath, String canonicalPath, int projectId, String cycleId,
			String folderId, int issueId, String executionId, int status) {
		String executeCanonicalPath = canonicalPath + executionId + "&";
		String urlEndpoint = zephyrBaseUrl + apiPath + executionId;
		String jtwToken = getJWTToken(executeCanonicalPath);

		HashMap<String, Object> statusData = new HashMap<String, Object>();
		statusData.put("id", status);
		JSONObject statusJson = new JSONObject(statusData);

		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.uuuu HH:mm");
		LocalDateTime now = LocalDateTime.now();
		String comment = "";
		if (status == 1) {
			comment = "Test is Passed. Executed On : " + dateTimeFormatter.format(now);
		} else {
			comment = "Test is Failed. Executed On : " + dateTimeFormatter.format(now);
		}

		HashMap<String, Object> executionData = new HashMap<String, Object>();
		executionData.put("cycleId", cycleId);  
		executionData.put("comment", comment);
		executionData.put("issueId", issueId); 
		executionData.put("projectId", projectId);  
		executionData.put("status", statusJson); 
		executionData.put("folderId", folderId);
		executionData.put("versionId", -1);

		try {
			JSONObject executionJson = new JSONObject(executionData);
			StringEntity params = new StringEntity(executionJson.toString());

			HttpClient client = HttpClientBuilder.create().build();
			HttpPut putRequest = new HttpPut(urlEndpoint);
			putRequest.setHeader("Authorization", "JWT " + jtwToken);
			putRequest.setHeader("Content-Type", "application/json");
			putRequest.setHeader("zapiAccessKey", accessKey);
			putRequest.setEntity(params);

			HttpResponse response = client.execute(putRequest);
			int statusCode = response.getStatusLine().getStatusCode();

			if (statusCode == 404) {
				System.out.println("Execution with key " + executionId + ", does not exists");
				log.error("Execution with key " + executionId + ", does not exists");
				/*
				 * report.updateTestLog("Execution Info", "Execution with key " + executionId +
				 * ", does not exists", Status.FAIL);
				 */
			} else if (statusCode == 500) {
				System.out.println("Error 500, there can be multiple issues, check parameters");
				log.error("Error 500, there can be multiple issues, check parameters");
				/*
				 * report.updateTestLog("Execution Info",
				 * "Error 500, there can be multiple issues, check parameters, function updateJiraExecutionStatus"
				 * , Status.FAIL);
				 */
			} else if (statusCode == 200) {
				System.out.println("Test case status updated successfully");
				/*
				 * report.updateTestLog("Execution Info",
				 * "Test case status updated successfully in JIRA", Status.PASS);
				 */
			} else {
				System.out.println("Unknown error occured");
				log.error("Unknown error occured");
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String getJWTToken(String canonicalPath) {
		long JWT_EXPIRE = 3600;

		Map<String, Object> payloadToken = new HashMap<>();
		payloadToken.put("sub", accountID);
		payloadToken.put("qsh", sha256Hash(canonicalPath));
		payloadToken.put("iss", accessKey);
		payloadToken.put("exp", new Date().getTime() + JWT_EXPIRE * 1000);
		payloadToken.put("iat", new Date().getTime());

		byte[] secretKeyBytes = secretKeyString.getBytes();
		if (secretKeyBytes.length < 32) {
			throw new IllegalArgumentException("Secret key length must be at least 32 bytes for HS256");
		}

		Key secretKey = Keys.hmacShaKeyFor(secretKeyBytes);
		String jwtToken = Jwts.builder().setClaims(payloadToken).signWith(secretKey).compact();
		return jwtToken;
	}

	public String sha256Hash(String originalString) {
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			byte[] encodedhash = digest.digest(originalString.getBytes(StandardCharsets.UTF_8));

			StringBuilder hexString = new StringBuilder(2 * encodedhash.length);
			for (int i = 0; i < encodedhash.length; i++) {
				String hex = Integer.toHexString(0xff & encodedhash[i]);
				if (hex.length() == 1) {
					hexString.append('0');
				}
				hexString.append(hex);
			}
			return hexString.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return originalString;
	}
}
