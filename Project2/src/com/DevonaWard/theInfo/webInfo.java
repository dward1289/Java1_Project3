package com.DevonaWard.theInfo;

import java.io.BufferedInputStream;
import java.net.URL;
import java.net.URLConnection;

import android.util.Log;

public class webInfo {

	//Getting the data from the web
	public static String getURLStringResponse(URL url){
		String response = "";
		
		try{
			URLConnection urlCon = url.openConnection();
			BufferedInputStream bin = new BufferedInputStream(urlCon.getInputStream());
			
			byte[] contentBytes = new byte[1024];
			int bytesRead = 0;
			StringBuffer responseBuffer = new StringBuffer();
			
			while((bytesRead = bin.read(contentBytes)) != -1){
				response = new String(contentBytes,0,bytesRead);
				responseBuffer.append(response);
			}
			return responseBuffer.toString();
		}catch (Exception e){
			Log.e("URL RESPONSE ERROR", "getURLStringResponse");
		}
		return response;
	}
}
