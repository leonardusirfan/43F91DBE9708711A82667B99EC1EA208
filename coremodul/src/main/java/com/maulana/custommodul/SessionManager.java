package com.maulana.custommodul;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import java.util.HashMap;

public class SessionManager {
	// Shared Preferences
	SharedPreferences pref;
	
	// Editor for Shared preferences
	Editor editor;
	
	// Context
	Context context;
	
	// Shared pref mode
	int PRIVATE_MODE = 0;
	
	// Sharedpref file name
	private static final String PREF_NAME = "GmediaPerkasaApp";
	
	// All Shared Preferences Keys
	public static final String TAG_USERNAME = "username";
	public static final String TAG_NIK_GA = "nik_ga";
	public static final String TAG_NIK_MKIOS = "nik_mkios";
	public static final String TAG_NAMA = "nama";

	// Constructor
	public SessionManager(Context context){
		this.context = context;
		pref = this.context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
		editor = pref.edit();
	}
	
	/**
	 * Create login session
	 * */
	public void createLoginSession(String username, String nikGa, String nikMkios, String nama){

		editor.putString(TAG_USERNAME, username);

		editor.putString(TAG_NIK_GA, nikGa);

		editor.putString(TAG_NIK_MKIOS, nikMkios);

		editor.putString(TAG_NAMA, nama);

		// commit changes
		editor.commit();
	}
	
	/**
	 * Get stored session data
	 * */

	public String getUserInfo(String key){
		return pref.getString(key, "");
	}

	public String getUsername(){
		return pref.getString(TAG_USERNAME, "");
	}

	public String getNikMkios(){
		return pref.getString(TAG_NIK_MKIOS, "");
	}

	public String getNikGa(){
		return pref.getString(TAG_NIK_GA, "");
	}

	public String getNama(){
		return pref.getString(TAG_NAMA, "");
	}

	/**
	 * Clear session details
	 * */
	public void logoutUser(Intent logoutIntent){

		// Clearing all data from Shared Preferences
		try {
			editor.clear();
			editor.commit();
		}catch (Exception e){
			e.printStackTrace();
		}

		logoutIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		context.startActivity(logoutIntent);
		((Activity)context).finish();
		((Activity)context).overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
	}
	
	/**
	 * Quick check for login
	 * **/
	// Get Login State
	public boolean isLoggedIn(){
		if(getUsername().isEmpty()){
			return false;
		}else{
			return true;
		}
		/*return pref.getBoolean(IS_LOGIN, false);*/
	}
}
