package com.phonegap;

import java.io.ByteArrayOutputStream;

import org.apache.commons.codec.binary.Base64;

import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.util.Log;
import android.webkit.WebView;


public class CameraLauncher {
		
	private WebView mAppView;
	private DroidGap mGap;
	int mQuality;	
	
	CameraLauncher(WebView view, DroidGap gap)
	{
		mAppView = view;
		mGap = gap;
	}
	
	public void takePicture(int quality)
	{
		mQuality = quality;
		mGap.startCamera();
	}
	
	/* Return Base64 Encoded String to Javascript */
	public void processPicture( Bitmap bitmap )
	{		
		ByteArrayOutputStream jpeg_data = new ByteArrayOutputStream();
		try {
			if (bitmap.compress(CompressFormat.JPEG, mQuality, jpeg_data))
			{
				byte[] code  = jpeg_data.toByteArray();
				byte[] output = Base64.encodeBase64(code);
				String js_out = new String(output);
				mAppView.loadUrl("javascript:navigator.camera.win('" + js_out + "');");	
			}	
		}
		catch(Exception e)
		{
			Log.d(DroidGap.LOG_TAG, "processPicture failed due to an exception: " + e.getMessage());
			failPicture("fail");
		}
				
	}
	
	public void failPicture(String err)
	{
		Log.d(DroidGap.LOG_TAG, "failPicture due to an error: " + err);
		mAppView.loadUrl("javascript:navigator.camera.fail('" + err + "');");
	}
}
