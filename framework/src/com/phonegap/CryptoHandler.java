package com.phonegap;

import android.util.Log;
import android.webkit.WebView;

public class CryptoHandler {
	
	WebView mView;
	
	CryptoHandler(WebView view)
	{
		mView = view;
	}
	
	public void encrypt(String pass, String text)
	{
		try {
			String encrypted = SimpleCrypto.encrypt(pass,text);
			mView.loadUrl("javascript:Crypto.gotCryptedString('" + encrypted + "')");
		} catch (Exception e) {
			Log.d(DroidGap.LOG_TAG, "encrypt failed due to an exception: " + e.getMessage());
		}
		
	}
	
	public void decrypt(String pass, String text)
	{
		try {
			String decrypted = SimpleCrypto.decrypt(pass,text);
			mView.loadUrl("javascript:Crypto.gotPlainString('" + decrypted + "')");
		} catch (Exception e) {
			Log.d(DroidGap.LOG_TAG, "decrypt failed due to an exception: " + e.getMessage());
		}
	}
}
