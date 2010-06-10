package com.phonegap;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import android.util.Log;
import android.webkit.WebSettings;

public class WebViewReflect {
	   private static Method mWebSettings_setDatabaseEnabled;
	   private static Method mWebSettings_setDatabasePath;
	   private static Method mWebSettings_setDomStorageEnabled;
	   private static Method mWebSettings_setGeolocationEnabled;
	   
	   static 
	   {
		   checkCompatibility();
	   }
	   
	   public static void checkCompatibility() {
	       try {
	           mWebSettings_setDatabaseEnabled = WebSettings.class.getMethod(
	                   "setDatabaseEnabled", new Class[] { boolean.class } );
	           mWebSettings_setDatabasePath = WebSettings.class.getMethod(
	        		   "setDatabasePath", new Class[] { String.class });
	           mWebSettings_setDomStorageEnabled = WebSettings.class.getMethod(
	        		   "setDomStorageEnabled", new Class[] { boolean.class });
	           mWebSettings_setGeolocationEnabled = WebSettings.class.getMethod(
	        		   "setGeolocationEnabled", new Class[] { boolean.class });
	       } catch (NoSuchMethodException nsme) {
	    	   Log.d(DroidGap.LOG_TAG, "checkCompatability failed due to an exception (must be an older device): " + nsme.getMessage());
	       }
	   }

	   public static void setStorage(WebSettings setting, boolean enable, String path) {
	       if (mWebSettings_setDatabaseEnabled != null) {
	           /* feature is supported */
	    	   try {
				mWebSettings_setDatabaseEnabled.invoke(setting, enable);
				mWebSettings_setDatabasePath.invoke(setting, path);
	    	   } catch (IllegalArgumentException e) {
	    		   Log.d(DroidGap.LOG_TAG, "setStorage failed due to an exception: " + e.getMessage());
	    	   } catch (IllegalAccessException e) {
	    		   Log.d(DroidGap.LOG_TAG, "setStorage failed due to an exception: " + e.getMessage());
	    	   } catch (InvocationTargetException e) {
	    		   Log.d(DroidGap.LOG_TAG, "setStorage failed due to an exception: " + e.getMessage());
	    	   }
	       } else {
	    	   Log.d(DroidGap.LOG_TAG, "setStorage feature not supported");
	           /* TODO: feature not supported, do something else */
	       }
	   }
	   public static void setGeolocationEnabled(WebSettings setting, boolean enable) {
		   if (mWebSettings_setGeolocationEnabled != null) {
			   /* feature is supported */
			   try {
				mWebSettings_setGeolocationEnabled.invoke(setting, enable);
			} catch (IllegalArgumentException e) {
				Log.d(DroidGap.LOG_TAG, "setGeolocationEnabled failed due to an exception: " + e.getMessage());
			} catch (IllegalAccessException e) {
				Log.d(DroidGap.LOG_TAG, "setGeolocationEnabled failed due to an exception: " + e.getMessage());
			} catch (InvocationTargetException e) {
				Log.d(DroidGap.LOG_TAG, "setGeolocationEnabled failed due to an exception: " + e.getMessage());
			}
		   } else {
			   Log.d(DroidGap.LOG_TAG, "Native Geolocation not supported - we're ok");
		   }
	   }
	   public static void setDomStorage(WebSettings setting)
	   {
		   if(mWebSettings_setDomStorageEnabled != null)
		   {
			     /* feature is supported */
	    	   try {
	    		   mWebSettings_setDomStorageEnabled.invoke(setting, true);
			} catch (IllegalArgumentException e) {
				Log.d(DroidGap.LOG_TAG, "setDomStorage failed due to an exception: " + e.getMessage());
			} catch (IllegalAccessException e) {
				Log.d(DroidGap.LOG_TAG, "setDomStorage failed due to an exception: " + e.getMessage());
			} catch (InvocationTargetException e) {
				Log.d(DroidGap.LOG_TAG, "setDomStorage failed due to an exception: " + e.getMessage());
			}
	       } else {
	    	   Log.d(DroidGap.LOG_TAG, "setGeolocationEnabled feature not supported!" );
	           /* feature not supported, do something else */
	       }
	   }
}
