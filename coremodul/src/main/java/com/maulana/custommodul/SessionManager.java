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
	public static final String TAG_NIK_GA = "Nik-Ga";
	public static final String TAG_NIK_MKIOS = "nik_mkios";
	public static final String TAG_NAMA = "nama";
	public static final String TAG_PASSWORD = "password";
	public static final String TAG_SESSION = "session";
	public static final String TAG_EXPIRATION = "expiration";
	public static final String TAG_SUPERUSER = "superuser";
	public static final String TAG_NIK_HR = "nik_hr";
	public static final String TAG_JABATAN = "jabatan";
	private static final String TAG_FLAG = "flag";
	private static final String TAG_AREA = "area";
	private static final String TAG_LEVEL = "level";
	private static final String TAG_EXP = "expired_at";
	private static final String TAG_JAB = "jabatan";
	private static final String TAG_SAVED = "saved";
	private static final String TAG_NIK = "nik";
	public static final String TAG_UID = "uid";


	// Constructor
	public SessionManager(Context context){
		this.context = context;
		pref = this.context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
		editor = pref.edit();
	}
	
	/**
	 * Create login session
	 * */
	public void createLoginSession(String username, String nikGa, String nikMkios, String nama, String password, String session, String expiration, String superuser, String nikHR, String jabatan){

		editor.putString(TAG_USERNAME, username);

		editor.putString(TAG_NIK_GA, nikGa);

		editor.putString(TAG_NIK_MKIOS, nikMkios);

		editor.putString(TAG_NAMA, nama);

		editor.putString(TAG_PASSWORD, password);

		editor.putString(TAG_SESSION, session);

		editor.putString(TAG_EXPIRATION, expiration);

		editor.putString(TAG_SUPERUSER, superuser);

		editor.putString(TAG_NIK_HR, nikHR);

		editor.putString(TAG_JABATAN, jabatan);
		// commit changes
		editor.commit();
	}

	public void savePassword(String password){

		editor.putString(TAG_PASSWORD, password);

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

	public String getNikGa(){ return pref.getString(TAG_NIK_GA, "");
	}

	public String getNama(){
		return pref.getString(TAG_NAMA, "");
	}

	public String getPassword(){
		return pref.getString(TAG_PASSWORD, "");
	}

	public String getSession(){
		return pref.getString(TAG_SESSION, "");
	}

	public String getExpiration(){
		return pref.getString(TAG_EXPIRATION, "");
	}

	public String getSuperuser(){
		return pref.getString(TAG_SUPERUSER, "");
	}

	public String getNikHR(){
		return pref.getString(TAG_NIK_HR, "");
	}

	public String getJabatan(){
		return pref.getString(TAG_JABATAN, "");
	}

	public boolean isSuperuser(){

		return pref.getString(TAG_SUPERUSER, "").equals("1");
	}

	public boolean isBM(){

		return (this.getJabatan().toUpperCase().contains("BM") || this.getJabatan().toUpperCase().contains("SUPERVISOR BDO"));
	}

	// getUser
	public HashMap<String, String> getUserDetails(){
		HashMap<String, String> user = new HashMap<String, String>();
		// user uid
		user.put(TAG_UID, pref.getString(TAG_UID, ""));

		// user nik
		user.put(TAG_NIK_GA, pref.getString(TAG_NIK_GA, ""));
		
		user.put(TAG_NIK, pref.getString(TAG_NIK, ""));

		user.put(TAG_NAMA, pref.getString(TAG_NAMA, ""));

		user.put(TAG_USERNAME, pref.getString(TAG_USERNAME, ""));

		user.put(TAG_PASSWORD, pref.getString(TAG_PASSWORD, ""));

		user.put(TAG_SAVED, pref.getString(TAG_SAVED, ""));

		user.put(TAG_JAB, pref.getString(TAG_JAB, ""));

		user.put(TAG_EXP, pref.getString(TAG_EXP, ""));

		user.put(TAG_LEVEL, pref.getString(TAG_LEVEL, ""));

		user.put(TAG_AREA, pref.getString(TAG_AREA, ""));

		user.put(TAG_FLAG, pref.getString(TAG_FLAG, ""));
		// return user
		return user;
	}



	public String getUser(){
		return pref.getString(TAG_NAMA, "");
	}


	public String getNikGA(){
		return pref.getString(TAG_NIK_GA, "");
	}


	public String getLevel(){
		return pref.getString(TAG_LEVEL, "");
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
