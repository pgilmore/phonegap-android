package com.phonegap;
/* License (MIT)
 * Copyright (c) 2008 Nitobi
 * website: http://phonegap.com
 * Permission is hereby granted, free of charge, to any person obtaining
 * a copy of this software and associated documentation files (the
 * Software), to deal in the Software without restriction, including
 * without limitation the rights to use, copy, modify, merge, publish,
 * distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so, subject to
 * the following conditions:
 *
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED AS IS, WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 * LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
 * OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION
 * WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

import java.util.TimeZone;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import android.content.Context;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Vibrator;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.webkit.WebView;

public class Device{
	/*
	 * UUID, version and availability	
	 */
	public boolean droid = true;
	public static String version = "0.91";
	public static String platform = "Android";
	public static String uuid;
	private Context mCtx;
    private int notificationResource = -1;
    
	public Device(WebView appView, Context ctx) {
        this.mCtx = ctx;
        uuid = getUuid();
    }
	
	public void beep(final long pattern)
	{
		new Thread () {
			public void run() {
				ExecutorService executor = Executors.newCachedThreadPool();
		        Future<?> future = executor.submit(
		            new Runnable() {
		                public void run() {
		                    try {
		                        Thread.sleep(pattern);
		                    } catch (InterruptedException e) {
		                        Log.d(DroidGap.LOG_TAG, "beep failed with an exception: " + e.getMessage());
		                    }
		                }
		            }
		        );

		        MediaPlayer notification = null;
		        if(notificationResource > -1) {
		        	notification = MediaPlayer.create(mCtx, notificationResource);
		        }
		        else {
		        	Uri ringtone = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
		        	notification = MediaPlayer.create(mCtx, ringtone);
		        }
				
				if(notification == null) {
					Log.d(DroidGap.LOG_TAG, "beep failed to find the default notification sound!");
					return;
				}
				notification.setLooping(true);
				notification.start();
		    
		        Log.d(DroidGap.LOG_TAG, "Waiting for beep task to finish..");
		        try {
					future.get();
				} catch (InterruptedException e) {
					Log.d(DroidGap.LOG_TAG, "beep failed with an exception: " + e.getMessage());
				} catch (ExecutionException e) {
					Log.d(DroidGap.LOG_TAG, "beep failed with an exception: " + e.getMessage());
				} finally {
					notification.stop();
				}
		        Log.d(DroidGap.LOG_TAG, "Beep task finished!");
		        executor.shutdown();
			};
		}.start();
	}
	
	public void setNotificationResource(int notificationResource) {
		Log.d(DroidGap.LOG_TAG,"Setting beeper resource to int: " + notificationResource);
		this.notificationResource = notificationResource;
	}
	
	public void vibrate(long pattern){
        // Start the vibration, 0 defaults to half a second.
		if (pattern == 0)
			pattern = 500;
        Vibrator vibrator = (Vibrator) mCtx.getSystemService(Context.VIBRATOR_SERVICE);
        vibrator.vibrate(pattern);
	}
	
	public String getPlatform()
	{
		return Device.platform;
	}
	
	public String getUuid()
	{		
		//TelephonyManager operator = (TelephonyManager) mCtx.getSystemService(Context.TELEPHONY_SERVICE);		
		//String uuid = operator.getDeviceId();
		//TODO: Figure out why this is done in this way?
		String uuid = Settings.Secure.getString(mCtx.getContentResolver(), android.provider.Settings.Secure.ANDROID_ID);
		return uuid;
	}

	public String getLine1Number(){
	  TelephonyManager operator = (TelephonyManager)mCtx.getSystemService(Context.TELEPHONY_SERVICE);
	  return operator.getLine1Number();
	}
	
	public String getDeviceId(){
	  TelephonyManager operator = (TelephonyManager)mCtx.getSystemService(Context.TELEPHONY_SERVICE);
	  return operator.getDeviceId();
	}
	
	public String getSimSerialNumber(){
	  TelephonyManager operator = (TelephonyManager)mCtx.getSystemService(Context.TELEPHONY_SERVICE);
	  return operator.getSimSerialNumber();
  }
  
	public String getSubscriberId(){
	  TelephonyManager operator = (TelephonyManager)mCtx.getSystemService(Context.TELEPHONY_SERVICE);
	  return operator.getSubscriberId();
	}
	
	public String getModel()
	{
		String model = android.os.Build.MODEL;
		return model;
	}
	public String getProductName()
	{
		String productname = android.os.Build.PRODUCT;
		return productname;
	}
	public String getOSVersion()
	{
		String osversion = android.os.Build.VERSION.RELEASE;
		return osversion;
	}
	public String getSDKVersion()
	{
		String sdkversion = android.os.Build.VERSION.SDK;
		return sdkversion;
	}
	
	public String getVersion()
	{
		return version;
	}	
    
    public String getTimeZoneID() {
       TimeZone tz = TimeZone.getDefault();
        return(tz.getID());
    } 
    
}

