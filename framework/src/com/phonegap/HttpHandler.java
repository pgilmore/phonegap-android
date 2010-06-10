package com.phonegap;

import java.io.FileOutputStream;
import java.io.InputStream;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.util.Log;

public class HttpHandler {

	protected Boolean get(String url, String file)
	{
		HttpEntity entity = getHttpEntity(url);
		try {
			writeToDisk(entity, file);
		} catch (Exception e) { Log.d(DroidGap.LOG_TAG, "get failed due to an exception: " + e.getMessage()); return false; }
		try {
			entity.consumeContent();
		} catch (Exception e) { Log.d(DroidGap.LOG_TAG, "get failed due to an exception: " + e.getMessage()); return false; }
		return true;
	}
	
	private HttpEntity getHttpEntity(String url)
	/**
	 * get the http entity at a given url
	 */
	{
		HttpEntity entity=null;
		try {
			DefaultHttpClient httpclient = new DefaultHttpClient();
			HttpGet httpget = new HttpGet(url);
			HttpResponse response = httpclient.execute(httpget);
			entity = response.getEntity();
		} catch (Exception e) { Log.d(DroidGap.LOG_TAG, "getHttpEntity failed due to an exception: " + e.getMessage()); return null; }
		return entity;
	}
	
	private void writeToDisk(HttpEntity entity, String file)
	/**
	 * writes a HTTP entity to the specified filename and location on disk
	 */
	{  
		int i=0;
		String FilePath="/sdcard/" + file;
		try {
			InputStream in = entity.getContent();
			byte buff[] = new byte[1024];    
			FileOutputStream out=
				new FileOutputStream(FilePath);
			do {
				int numread = in.read(buff);
				if (numread <= 0)
                   	break;
				out.write(buff, 0, numread);
				System.out.println("numread" + numread);
				i++;
			} while (true);
			out.flush();
			out.close();	
		} catch (Exception e) { Log.d(DroidGap.LOG_TAG, "writeToDisk failed due to an exception: " + e.getMessage()); }
	}
}
