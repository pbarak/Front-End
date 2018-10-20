package app;


import java.awt.BorderLayout;
import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import zookeeper.Configuration;

//import com.sun.javafx.collections.MappingChange.Map;
//import com.sun.net.ssl.HttpsURLConnection;

import javax.crypto.Cipher;
import javax.crypto.Mac;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
public class Get {
	
	Get(){System.out.println("Get is created");}  
	
	public String getAllGalleries(String parameter)  {
	
			System.out.println(parameter);
			
			
	        HashMap<String, String> params = new HashMap<String, String>();
	        params.put("token", Token.getVariable() );
	        params.put("action", "getMyGalleries");
			return this.getObjectsConnection(params);
	
    }
	

	public String getGallery(String parameter)  {
		
			System.out.println(parameter);
	
	        HashMap<String, String> params = new HashMap<String, String>();
	        params.put("token", Token.getVariable());
	        params.put("action", "getGallery");
	        params.put("galleryid", parameter);
	        
	        
	        return this.getObjectsConnection(params);
   
	}
	
	
	public String getFriendGalleries(String parameter0, String parameter1)  {
		

        HashMap<String, String> params = new HashMap<String, String>();
        params.put("token", Token.getVariable());
        params.put("action", "getFriendGalleries");
        params.put("friendname", parameter1);

        
        
        return this.getObjectsConnection(params);

}
	
	public String getFriends(String parameter)  {
		

        HashMap<String, String> params = new HashMap<String, String>();
        params.put("token", Token.getVariable());
        params.put("action", "getFriends");
      

        
        return this.getObjectsConnection(params);

}
	
	public ArrayList<Comment> getComments(String parameter)  {
		String result = null;
        ArrayList<Comment> commented = new ArrayList<Comment>();

	        HashMap<String, String> params = new HashMap<String, String>();
	        params.put("token", Token.getVariable());
	        params.put("action", "getComments");
	        params.put("imageid", parameter);

	        result = this.getObjectsConnection(params);
	    	
				JSONObject jsonObj = new JSONObject(result);
				
				JSONArray jsonArray = jsonObj.getJSONArray("comments");
				
				if(jsonArray!=null) {

									
					for(int l=0;l<jsonArray.length();l++){
						System.out.println(jsonArray.getJSONObject(l).getString("text"));
						commented.add(new Comment(jsonArray.getJSONObject(l).getString("text"), jsonArray.getJSONObject(l).getString("commenter"), jsonArray.getJSONObject(l).getString("timestamp"))) ;
					
							}

						}
						return commented;	
	}

	public String getObjectsConnection(HashMap<String, String> params)
	{
		try
		{
		//////////////////////////////////////////////////////////////////////////////////////////////////////////////	
			//get the element 0 of the list of the Directory Services
		URL url = new URL(Configuration.getInstance().getAvailableDirectorySystems().get(0).get("URL").toString());
		//////////////////////////////////////////////////////////////////////////////////////////////////////////////
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
        conn.setRequestMethod("GET");
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
