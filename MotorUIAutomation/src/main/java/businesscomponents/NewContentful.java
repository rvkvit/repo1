
package businesscomponents;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.JSONArray;
import org.json.JSONObject;
import com.LT.framework.selenium.WebDriverUtil;

import io.jsonwebtoken.io.IOException;
import supportlibraries.ReusableLibrary;
import supportlibraries.ScriptHelper;

public class NewContentful extends ReusableLibrary {

	private WebDriverUtil driverUtil;

	public NewContentful(ScriptHelper scriptHelper) {
		super(scriptHelper);

		driverUtil = new WebDriverUtil(driver);
	}
	
	
	String App = dataTable.getData("General_Data", "Application");
	String Lang = dataTable.getData("General_Data", "Language");
	String contentfulvalue;
	
	
	public String fetchcontentjson(String feildsKey, String contentfulkey, String contentlabel) throws IOException, ClientProtocolException, java.io.IOException {
	    String accessToken;
	    String spaceId;
	    
	    if (App.equals("TU")) {
	        if (Lang.equals("FI")) {
	            spaceId = "bh8a74vqhkkx";
	            accessToken = "6nMC-BvtwFDAyEb6fZf8XK0hUObhJo9JnxpUfnUYAbo";
	        } else {
	            spaceId = "bh8a74vqhkkx";
	            accessToken = "6nMC-BvtwFDAyEb6fZf8XK0hUObhJo9JnxpUfnUYAbo";
	        }
	    } else {
	        if (Lang.equals("FI")) {
	            spaceId = "xsycyoa21q1u";
	            accessToken = "K6A4VdbokYiIhLnmS9qToTflWwaqePHEm_h8kjW5hMk";
	        } else {
	            spaceId = "xsycyoa21q1u";
	            accessToken = "K6A4VdbokYiIhLnmS9qToTflWwaqePHEm_h8kjW5hMk";
	        }
	    }
	    
	    String url = String.format("https://preview.contentful.com/spaces/%s/environments/master/entries?include=10&locale=%s&limit=1000&content_type=flow&fields.key=%s&access_token=%s", 
	                               spaceId, Lang.equals("FI") ? "fi-FI" : "sv-FI", feildsKey, accessToken);
	    
	    return fetchContent(url, contentfulkey, contentlabel);
	}

	private String fetchContent(String url, String contentfulkey, String contentlabel) throws IOException, ClientProtocolException, java.io.IOException {
	    HttpClient client = HttpClientBuilder.create().build();
	    HttpGet request = new HttpGet(url);
	    HttpResponse response = client.execute(request);
	    BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), StandardCharsets.UTF_8));
	    
	    String line;
	    StringBuilder jsonString = new StringBuilder();
	    
	    while ((line = rd.readLine()) != null) {
	        jsonString.append(line);
	    }
	    
	    JSONObject jsonObject = new JSONObject(jsonString.toString());
	    JSONObject includes = jsonObject.getJSONObject("includes");
	    JSONArray entries = includes.getJSONArray("Entry");
	    
	    for (int k = 0; k < entries.length(); k++) {
	        JSONObject sysObject = entries.getJSONObject(k);
	        
	        if (sysObject.getJSONObject("sys").getString("id").equals(contentfulkey)) {
	            Object value = sysObject.getJSONObject("fields").get(contentlabel);
	            
	            if (value instanceof String) {
	                return sysObject.getJSONObject("fields").getString(contentlabel);
	            } else if (value instanceof JSONObject) {
	                JSONObject fieldsObject = sysObject.getJSONObject("fields");
	                JSONObject valueObject = fieldsObject.getJSONObject("value");
	                JSONArray contentArray = valueObject.getJSONArray("content");
	                
	                for (int i = 0; i < contentArray.length(); i++) {
	                    JSONObject paragraphObject = contentArray.getJSONObject(i);
	                    
	                    if ("paragraph".equals(paragraphObject.getString("nodeType"))) {
	                        JSONArray paragraphContentArray = paragraphObject.getJSONArray("content");
	                        
	                        for (int j = 0; j < paragraphContentArray.length(); j++) {
	                            JSONObject textObject = paragraphContentArray.getJSONObject(j);
	                            
	                            if ("text".equals(textObject.getString("nodeType"))) {
	                                return textObject.getString(contentlabel);
	                            }
	                        }
	                    }
	                }
	            }
	        }
	    }
	    
	    return "No contentful value match found";
	}

}