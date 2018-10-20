package app;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;



import zookeeper.Configuration;


public class Set {
	

	public void setGallery(String parameter)  {
		
		System.out.println(parameter);

        HashMap<String, String> params = new HashMap<String, String>();
        params.put("token", Token.getVariable());
        params.put("action", "createGallery");
        params.put("galleryname", parameter);
        
        
        this.setObjectsConnection(params);

}
	
	public String setObjectsConnection(HashMap<String, String> params)
	{
		try
		{
			//get the element 0 of the list of the Directory Services
			URL url = new URL(Configuration.getInstance().getAvailableDirectorySystems().get(0).get("URL").toString());
		
		String result = null;
        Iterator i = params.entrySet().iterator();	        
        StringBuilder postData = new StringBuilder();
        for (Entry<String, String> param : params.entrySet()) {
            if (postData.length() != 0) {
                postData.append('&');
            }
            postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
            postData.append('=');
            postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
        }
        byte[] postDataBytes = postData.toString().getBytes("UTF-8");

        
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        conn.setRequestProperty("Content-Length", String.valueOf(postDataBytes.length));
        conn.setDoOutput(true);
        conn.getOutputStream().write(postDataBytes);
        BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
        StringBuilder builder = new StringBuilder();
        for (String line = null; (line = reader.readLine()) != null;) {
            builder.append(line).append("\n");
        }
        reader.close();
        conn.disconnect();
        result = builder.toString();
        
        return result;
	} catch (MalformedURLException e) {
		e.printStackTrace();
		return null;
	} catch (IOException e) {
		e.printStackTrace();
		return null;
	}
	}

	


	
}