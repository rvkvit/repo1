package businesscomponents;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.LT.framework.selenium.WebDriverUtil;

import supportlibraries.ReusableLibrary;
import supportlibraries.ScriptHelper;

public class Contentful extends ReusableLibrary {

	private WebDriverUtil driverUtil;

	public Contentful(ScriptHelper scriptHelper) {
		super(scriptHelper);

		driverUtil = new WebDriverUtil(driver);
	}

	public JSONObject json3 = null;
	public JSONObject json4 = null;
	public JSONObject json6 = null;
	public JSONObject json5 = null;
	public JSONObject json8 = null;
	public JSONObject json10 = null;
	public static ThreadLocal<String> Lang = new ThreadLocal<String>();

	String strApp = dataTable.getData("General_Data", "Application");
	String strLang = dataTable.getData("General_Data", "Language");
	
	public String Fetchcontent(String Contentfulkey, String ContentfulSpace) throws Exception {
	    try {
	        // Check the App and Language
	        String baseUrl = "https://cdn.contentful.com/spaces/";
	        String space = strApp.equalsIgnoreCase("TU") ? "bh8a74vqhkkx" : "xsycyoa21q1u";
	        String locale = strLang.equals("FI") ? "fi-FI" : "sv-FI";
	        String accessToken = strApp.equalsIgnoreCase("TU") ? 
	            "RJ--Gj6mLP2HIGAZaHTOtVbjJUX3LZ0zXRf9I1HAVnI" : 
	            "UF_xQztG-wLdkxtWeUdBNumV3z_-q1Agr7g67kK0Bik";

	        // Fetch the JSON response
	        JSONObject json10 = fetchContentfulData(baseUrl, space, ContentfulSpace, locale, accessToken);

	        // Retrieve the content using the key
	        if (json10 != null) {
	            Object contentValue = json10.get(Contentfulkey);
	            return contentValue == null ? "" : contentValue.toString();
	        } else {
	            return null;
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	        System.out.println("Key not found or issue with JSON, Key is: " + Contentfulkey);
	        return null;
	    }
	}

	private JSONObject fetchContentfulData(String baseUrl, String space, String contentfulSpace, String locale, String accessToken) throws Exception {
	    // Build URL
	    String url = baseUrl + space + "/environments/uat/entries/" + contentfulSpace + "?locale=" + locale + "&access_token=" + accessToken;

	    // Create HTTP client and request
	    HttpClient client = HttpClientBuilder.create().build();
	    HttpGet request = new HttpGet(url);
	    HttpResponse response = client.execute(request);
	    
	    // Read response
	    //BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
	    BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent(),StandardCharsets.UTF_8));
	    String line = "";
	    StringBuilder jsonString = new StringBuilder();
	    while ((line = rd.readLine()) != null) {
	        jsonString.append(line);
	    }

	    // Parse JSON response
	    JSONParser parser = new JSONParser();
	    JSONObject json = (JSONObject) parser.parse(jsonString.toString());
	    JSONObject json2 = (JSONObject) json.get("fields");
	    return (JSONObject) json2.get("json");
	}

}
