/*************************************************************************************
 * Product: Spin-Suite (Mobile Suite)                       		                 *
 * Copyright (C) 2012-2018 E.R.P. Consultores y Asociados, C.A.                      *
 * Contributor(s): Yamel Senih ysenih@erpya.com				  		                 *
 * Contributor(s): Carlos Parada cparada@erpya.com				  		             *
 * This program is free software: you can redistribute it and/or modify              *
 * it under the terms of the GNU General Public License as published by              *
 * the Free Software Foundation, either version 3 of the License, or                 *
 * (at your option) any later version.                                               *
 * This program is distributed in the hope that it will be useful,                   *
 * but WITHOUT ANY WARRANTY; without even the implied warranty of                    *
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the                     *
 * GNU General Public License for more details.                                      *
 * You should have received a copy of the GNU General Public License                 *
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.            *
 ************************************************************************************/
package org.erpya.base.util;

import java.io.File;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Map;
import java.util.logging.Level;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.Configuration;
import android.preference.PreferenceManager;
import android.util.DisplayMetrics;
import android.util.TypedValue;

/**
 * @author Yamel Senih
 *
 */
//	TODO Add support to Msg and other functionalities from old Spin-Suite
public final class Env {
	
	/**
	 * Get Context
	 * @return
	 * @return Context
	 */
	public static Context getContext() {
		return context;
	}
	
	/**
	 * Get Instance
	 * @param context
	 * @param reload
	 * @return
	 * @return Env
	 */
	public static Env getInstance(Context context, boolean reload) {
		if(instance == null
				|| reload) {
			instance = new Env(context);
		}
		//	Default Return
		return instance;
	}
	
	/**
	 * Get Instance
	 * @param context
	 * @return
	 * @return Env
	 */
	public static Env getInstance(Context context) {
		return getInstance(context, false);
	}
	
	/**
	 * Set Login Date and Valid date
	 * @param ctx
	 * @param date
	 * @return
	 * @return boolean
	 */
	public static boolean loginDate(Context ctx, Date date) {
		Calendar currentDate = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		//	Format Date yyyy-MM-dd hh:mm:ss
		Env.setContext("#Date", sdf.format(date.getTime()));
		
		sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		String curDate = sdf.format(currentDate.getTime());
		String ctxDate = sdf.format(date.getTime());
		
		//	Format Date yyyy-MM-dd
		
		Env.setContext("#DateP", ctxDate);
		
		if(!(curDate.equals(ctxDate))){
			Env.setContext("#IsCurrentDate", "N");
			return false;
		}
		//	Default
		Env.setContext("#IsCurrentDate", "Y");
		return true;
	}
	
	/**
	 * Private
	 * *** Constructor ***
	 * @param context
	 */
	private Env(Context context) {
		this.context = context;
	}
	
	/**
	 * Verify if is loaded environment
	 * @param ctx
	 * @return
	 * @return boolean
	 */
	public static boolean isEnvLoad(Context ctx) {
		return getContextAsBoolean(ctx, SET_ENV);
	}
	
	/**
	 * Verify if is loaded environment
	 * @return
	 * @return boolean
	 */
	public static boolean isEnvLoad() {
		return isEnvLoad(getContext());
	}
	
	/**
	 * Get is load activity
	 * @param ctx
	 * @return
	 * @return boolean
	 */
	public static boolean isLoadedActivity(Context ctx) {
		return getContextAsBoolean(ctx, "#IsLoadedActivity");
	}
	
	/**
	 * Get is load activity
	 * @return
	 * @return boolean
	 */
	public static boolean isLoadedActivity() {
		return isLoadedActivity(getContext());
	}
	
	/**
	 * Get if is login user
	 * @param ctx
	 * @return
	 * @return boolean
	 */
	public static boolean isLogin(Context ctx) {
		return getContextAsBoolean(ctx, "#IsLogin");
	}
	
	/**
	 * Get if is login user
	 * @return
	 * @return boolean
	 */
	public static boolean isLogin() {
		return isLogin(getContext());
	}
	
	/**
	 * Set is initial load
	 * @param ctx
	 * @param value
	 * @return void
	 */
	public static void setIsEnvLoad(Context ctx, boolean value) {
		setContext(ctx, SET_ENV, value);
	}
	
	/**
	 * Set is initial load
	 * @param value
	 * @return void
	 */
	public static void setIsEnvLoad(boolean value) {
		setIsEnvLoad(getContext(), value);
	}
	
	/**
	 * Set if loaded activity
	 * @param ctx
	 * @param value
	 * @return void
	 */
	public static void setIsLoadedActivity(Context ctx, boolean value) {
		setContext(ctx, "#IsLoadedActivity", value);
	}
	
	/**
	 * Set if loaded activity
	 * @param value
	 * @return void
	 */
	public static void setIsLoadedActivity(boolean value) {
		setIsLoadedActivity(getContext(), value);
	}
	
	/**
	 * Set is login
	 * @param ctx
	 * @param value
	 * @return void
	 */
	public static void setIsLogin(Context ctx, boolean value) {
		setContext(ctx, "#IsLogin", value);
	}
	
	/**
	 * Set is login
	 * @param value
	 * @return void
	 */
	public static void setIsLogin(boolean value) {
		setIsLogin(getContext(), value);
	}
	
	/**
	 * Load Role Access from Current Role
	 * @param ctx
	 * @param isForce
	 * @return void
	 */
    //  TODO Implement it
	public static void loadRoleAccess(Context ctx, boolean isForce) {
		/*int m_AD_Role_ID = getAD_Role_ID(ctx);
		if(!isForce
				&& (m_AD_Role_ID == 0
					|| isAccessLoaded(ctx, m_AD_Role_ID)))
			return;
		//	Do it
		loadRoleAccess(ctx, m_AD_Role_ID);
		//	Set Loaded
		setAccessLoaded(ctx, m_AD_Role_ID, true);*/
	}
	
	/**
	 * Load Role Access from Current Role
	 * @return void
	 */
	/*public static void loadRoleAccess() {
		loadRoleAccess(getContext(), false);
	}*/
	
    /**
     * Load Context Data
     * @return void
     */
	public static void loadContext(Context ctx) {
    	//	Carlos Parada, Load var in comntext
		/*if (isEnvLoad(ctx)) {
			String sql = new String("SELECT sc.Name, sc.Value FROM AD_SysConfig sc");
	    	DB con = new DB(ctx);
	    	con.openDB(DB.READ_ONLY);
	    	Cursor rs = con.querySQL(sql, null);
	    	if(rs.moveToFirst()) {
	    		Editor ep = getEditor(ctx);
				do {
					ep.putString("#" + rs.getString(0), rs.getString(1));
				} while(rs.moveToNext());
				//	Commit
				ep.commit();
			}
	    	con.closeDB(rs);
		}*/
    }
	
	/**
	 * Load Context Data
	 * @return void
	 */
	public static void loadContext() {
		loadContext(getContext());
	}
	
	/**
	 * Set access loaded
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 12/09/2014, 19:01:04
	 * @param ctx
	 * @param loaded
	 * @return void
	 */
	public static void setAccessLoaded(Context ctx, int m_AD_Role_ID, boolean loaded) {
		if(m_AD_Role_ID == 0)
			return;
		//	Set
		setContext(ctx, S_IS_ACCESS_LOADED + "|" + m_AD_Role_ID, loaded);
	}
	
	/**
	 * Set access loaded
	 * @param m_AD_Role_ID
	 * @param loaded
	 * @return void
	 */
	public static void setAccessLoaded(int m_AD_Role_ID, boolean loaded) {
		setAccessLoaded(getContext(), loaded);
	}
	
	/**
	 * Set Access Loaded for current role
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 12/09/2014, 19:08:00
	 * @param ctx
	 * @param loaded
	 * @return void
	 */
	public static void setAccessLoaded(Context ctx, boolean loaded) {
		setAccessLoaded(ctx, getAD_Role_ID(ctx), loaded);
	}
	
	/**
	 * Set Access Loaded for current role
	 * @param loaded
	 * @return void
	 */
	public static void setAccessLoaded(boolean loaded) {
		setAccessLoaded(getContext(), loaded);
	}
	
	/**
	 * Is Access Loaded
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 12/09/2014, 19:02:12
	 * @param ctx
	 * @param m_AD_Role_ID
	 * @return
	 * @return boolean
	 */
	public static boolean isAccessLoaded(Context ctx, int m_AD_Role_ID) {
		if(m_AD_Role_ID == 0)
			return false;
		//	Set
		return getContextAsBoolean(ctx, S_IS_ACCESS_LOADED + "|" + m_AD_Role_ID);
	}
	
	/**
	 * Is Access Loaded
	 * @param m_AD_Role_ID
	 * @return
	 * @return boolean
	 */
	public static boolean isAccessLoaded(int m_AD_Role_ID) {
		return isAccessLoaded(getContext(), m_AD_Role_ID);
	}
	
	/**
	 * Is Access Loaded with current role
	 * @param ctx
	 * @return
	 * @return boolean
	 */
	public static boolean isAccessLoaded(Context ctx) {
		return isAccessLoaded(ctx, Env.getAD_Role_ID(ctx));
	}
	
	/**
	 * Is Access Loaded with current role
	 * @return
	 * @return boolean
	 */
	public static boolean isAccessLoaded() {
		return isAccessLoaded(getContext());
	}
	
	/**
	 * Load Role Access
	 * @param ctx
	 * @param m_AD_Role_ID
	 * @return void
	 */
	//  TODO Implement it
	public static void loadRoleAccess(Context ctx, int m_AD_Role_ID) {
		//	Get Process Access
		/*KeyNamePair[] processAccess = DB.getKeyNamePairs(ctx,
				"SELECT pa.AD_Process_ID, COALESCE(pa.IsReadWrite, 'N') " +
				"FROM AD_Process_Access pa " +
				"WHERE pa.AD_Role_ID = ?", m_AD_Role_ID);
		//	Get Editor
		Editor ep = getEditor(ctx);
		//	Ok
		boolean ok = false;
		//	Delete if not exists
		if(processAccess == null
				|| processAccess.length == 0) {
			//	Cache Reset
			int deleted = cacheReset(ctx, S_PROCESS_ACCESS + "|" + m_AD_Role_ID + "|", false);
			LogM.log(ctx, "Env", Level.FINE, "Process Access Deleted = " + deleted);
		} else {
			for(KeyNamePair pAccess : processAccess) {
				ep.putString(S_PROCESS_ACCESS + "|" + m_AD_Role_ID + "|" + pAccess.getKey(), pAccess.getName());
			}
			//	Set Ok
			ok = true;
		}
		//	Get Windows Access
		KeyNamePair[] windowsAccess = DB.getKeyNamePairs(ctx, 
				"SELECT wa.SPS_Window_ID, COALESCE(wa.IsReadWrite, 'N') " +
				"FROM SPS_Window_Access wa " +
				"WHERE wa.AD_Role_ID = ?", m_AD_Role_ID);
		//	Delete if not exists
		if(windowsAccess == null
				|| windowsAccess.length == 0) {
			//	Cache Reset
			int deleted = cacheReset(ctx, S_WINDOW_ACCESS + "|" + m_AD_Role_ID + "|", false);
			LogM.log(ctx, "Env", Level.FINE, "Windows Access Deleted = " + deleted);
		} else {
			for(KeyNamePair wAccess : windowsAccess) {
				ep.putString(S_WINDOW_ACCESS + "|" + m_AD_Role_ID + "|" + wAccess.getKey(), wAccess.getName());
			}
			//	Set Ok
			ok = true;
		}
		//	Get Document Access
		KeyNamePair[] documentAccess = DB.getKeyNamePairs(ctx, 
				"SELECT da.C_DocType_ID, rl.Value " +
				"FROM AD_Document_Action_Access da " +
				"INNER JOIN AD_Ref_List rl ON(rl.AD_Ref_List_ID = da.AD_Ref_List_ID) " +
				"WHERE da.AD_Role_ID = ?", m_AD_Role_ID);
		//	Delete if not exists
		if(documentAccess == null
				|| documentAccess.length == 0) {
			//	Cache Reset
			int deleted = cacheReset(ctx, S_DOCUMENT_ACCESS + "|" + m_AD_Role_ID + "|", false);
			LogM.log(ctx, "Env", Level.FINE, "Document Access Deleted = " + deleted);
		} else {
			//	Delete Old
			int deleted = cacheReset(ctx, S_DOCUMENT_ACCESS + "|" + m_AD_Role_ID + "|", false);
			LogM.log(ctx, "Env", Level.FINE, "Document Access Deleted = " + deleted);
			//	
			for(KeyNamePair dAccess : documentAccess) {
				ep.putString(S_DOCUMENT_ACCESS + "|" + m_AD_Role_ID + "|" + dAccess.getKey() + "|" + dAccess.getName(), "Y");
			}
			//	Set Ok
			ok = true;
		}
		//	Commit
		if(ok)
			ep.commit();*/
	}
	
	/**
	 * Load Role Access
	 * @param m_AD_Role_ID
	 * @return void
	 */
	public static void loadRoleAccess(int m_AD_Role_ID) {
		loadRoleAccess(getContext(), m_AD_Role_ID);
	}
	
	/**
	 * Get Process Access with Role
	 * @param ctx
	 * @param m_AD_Role_ID
	 * @param m_AD_Process_ID
	 * @return
	 * @return boolean
	 */
	public static boolean getProcessAccess(Context ctx, int m_AD_Role_ID, int m_AD_Process_ID) {
		return getContextAsBoolean(ctx, S_PROCESS_ACCESS + "|" + m_AD_Role_ID + "|" + m_AD_Process_ID);
	}
	
	/**
	 * Get Process Access with Role
	 * @param m_AD_Role_ID
	 * @param m_AD_Process_ID
	 * @return
	 * @return boolean
	 */
	public static boolean getProcessAccess(int m_AD_Role_ID, int m_AD_Process_ID) {
		return getProcessAccess(getContext(), m_AD_Role_ID, m_AD_Process_ID);
	}
	
	/**
	 * Get Process Access without Role
	 * @param ctx
	 * @param m_AD_Process_ID
	 * @return
	 * @return boolean
	 */
	public static boolean getProcessAccess(Context ctx, int m_AD_Process_ID) {
		return getProcessAccess(ctx, getAD_Role_ID(ctx), m_AD_Process_ID);
	}
	
	/**
	 * Get Process Access without Role
	 * @param m_AD_Process_ID
	 * @return
	 * @return boolean
	 */
	public static boolean getProcessAccess(int m_AD_Process_ID) {
		return getProcessAccess(getContext(), m_AD_Process_ID);
	}
	
	
	/**
	 * Get Windows Access with Role
	 * @param ctx
	 * @param m_AD_Role_ID
	 * @param m_SPS_Window_ID
	 * @return
	 * @return boolean
	 */
	public static boolean getWindowsAccess(Context ctx, int m_AD_Role_ID, int m_SPS_Window_ID) {
		return getContextAsBoolean(ctx, S_WINDOW_ACCESS + "|" + m_AD_Role_ID + "|" + m_SPS_Window_ID);
	}
	
	/**
	 * Get Windows Access with Role
	 * @param m_AD_Role_ID
	 * @param m_SPS_Window_ID
	 * @return
	 * @return boolean
	 */
	public static boolean getWindowsAccess(int m_AD_Role_ID, int m_SPS_Window_ID) {
		return getWindowsAccess(getContext(), m_AD_Role_ID, m_SPS_Window_ID);
	}
	
	/**
	 * Get Valid DocAction
	 * @param ctx
	 * @param m_AD_Role_ID
	 * @param m_C_DocType_ID
	 * @param m_DocAction
	 * @return
	 * @return boolean
	 */
	public static boolean getDocumentAccess(Context ctx, int m_AD_Role_ID, int m_C_DocType_ID, String m_DocAction) {
		return getContextAsBoolean(ctx, S_DOCUMENT_ACCESS + "|" + m_AD_Role_ID + "|" + m_C_DocType_ID + "|" + m_DocAction);
	}
	
	/**
	 * Get Valid DocAction
	 * @param m_AD_Role_ID
	 * @param m_C_DocType_ID
	 * @param m_DocAction
	 * @return
	 * @return boolean
	 */
	public static boolean getDocumentAccess(int m_AD_Role_ID, int m_C_DocType_ID, String m_DocAction) {
		return getDocumentAccess(getContext(), m_AD_Role_ID, m_C_DocType_ID, m_DocAction);
	}
	
	/**
	 * Get Valid DocAction without Role
	 * @param ctx
	 * @param m_C_DocType_ID
	 * @param m_DocAction
	 * @return
	 * @return boolean
	 */
	public static boolean getDocumentAccess(Context ctx, int m_C_DocType_ID, String m_DocAction) {
		return getDocumentAccess(ctx, getAD_Role_ID(ctx), m_C_DocType_ID, m_DocAction);
	}
	
	/**
	 * Get Valid DocAction without Role
	 * @param m_C_DocType_ID
	 * @param m_DocAction
	 * @return
	 * @return boolean
	 */
	public static boolean getDocumentAccess(int m_C_DocType_ID, String m_DocAction) {
		return getDocumentAccess(getContext(), m_C_DocType_ID, m_DocAction);
	}
	
	/**
	 * Get Windows Access without Role
	 * @param ctx
	 * @param m_SPS_Window_ID
	 * @return
	 * @return boolean
	 */
	public static boolean getWindowsAccess(Context ctx, int m_SPS_Window_ID) {
		return getWindowsAccess(ctx, getAD_Role_ID(), m_SPS_Window_ID);
	}
	
	/**
	 * Get Windows Access without Role
	 * @param m_SPS_Window_ID
	 * @return
	 * @return boolean
	 */
	public static boolean getWindowsAccess(int m_SPS_Window_ID) {
		return getWindowsAccess(getContext(), m_SPS_Window_ID);
	}
	
	/**
	 * Cache Reset
	 * @param ctx
	 * @return
	 * @return int
	 */
	public static int cacheReset(Context ctx) {
		return cacheReset(ctx, "#", true);
	}
	
	/**
	 * Cache Reset
	 * @return
	 * @return int
	 */
	public static int cacheReset() {
		return cacheReset(getContext());
	}
	
	/**
	 * Cache Reset
	 * @param ctx
	 * @param m_Prefix
	 * @param m_IgnorePrefix
	 * @return
	 * @return int
	 */
	public static int cacheReset(Context ctx, String m_Prefix, boolean m_IgnorePrefix) {
		//	Set Default Prefix
		if(m_Prefix == null)
			m_Prefix = "";
		//	Get Preferences
		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(ctx);
		//	Get All Entries
		Map<String, ?> allEntries = preferences.getAll();
		//	Delete
		int deleted = 0;
		//	Get Editor
		Editor ep = getEditor(ctx);
		for (Map.Entry<String, ?> entry : allEntries.entrySet()) {
			String key = entry.getKey();
			if(key == null
					|| (key.startsWith(m_Prefix)
							&& m_IgnorePrefix)
					|| (!key.startsWith(m_Prefix)
							&& !m_IgnorePrefix))
				continue;
			//	
			ep.remove(key);
			//	Count
			deleted++;
			//	Log
			org.erpya.base.util.LogM.log(ctx, "ENV", Level.FINE, "Entry [" + key + "] Deleted");
		}
		//	Commit
		if(deleted != 0)
			ep.commit();
		//	Return
		return deleted;
	}
	
	/**
	 * Get share preference editor
	 * @param ctx
	 * @return
	 * @return Editor
	 */
	public static Editor getEditor(Context ctx) {
		m_ShareP = PreferenceManager.getDefaultSharedPreferences(ctx);
		return m_ShareP.edit();
	}
	
	/**
	 * Get Share Preferences
	 * @param ctx
	 * @return
	 * @return SharedPreferences
	 */
	public static SharedPreferences getSharePreferences(Context ctx) {
		if(m_ShareP == null) { 
			m_ShareP = PreferenceManager.getDefaultSharedPreferences(ctx);
		}
		return m_ShareP;
	}
	
	/**
	 * Remove a context value
	 * @param ctx
	 * @param context
	 * @return void
	 */
	public static void removeContext(Context ctx, String context) {
		if (ctx == null || context == null)
			return;
		//	Log
		org.erpya.base.util.LogM.log(ctx, "Env", Level.FINE, "removeContext("  + context + ")");
		//	
		Editor ep = getEditor(ctx);
		ep.remove(context);
		ep.commit();
	}
	
	/**
	 * Remove a context value
	 * @param context
	 * @return void
	 */
	public static void removeContext(String context) {
		removeContext(getContext(), context);
	}
	
	/**
	 *	Set Global Context to Value
	 *  @param ctx context
	 *  @param context context key
	 *  @param value context value
	 */
	public static void setContext (Context ctx, String context, String value) {
		if (ctx == null || context == null)
			return;
		//	Log
		org.erpya.base.util.LogM.log(ctx, "Env", Level.FINE, "setContext("  + context + ", " + value + ")");
		//
		if (value == null)
			value = "";
		Editor ep = getEditor(ctx);
		ep.putString(context, value);
		ep.commit();
	}	//	setContext
	
	/**
	 * Set Global Context to Value
	 * @param context
	 * @param value
	 * @return void
	 */
	public static void setContext (String context, String value) {
		setContext (getContext(), context, value);
	}
	
	/**
	 * Set Context Object
	 * @param ctx
	 * @param context
	 * @param value
	 * @return void
	 */
	public static void setContextObject(Context ctx, String context, Object value) {
		Editor prefsEditor = getEditor(ctx);
        /*Gson gson = new Gson();
        String json = gson.toJson(value);
        prefsEditor.putString(context, json);
        prefsEditor.commit();*/
	}
	
	/**
	 * Set Context Object
	 * @param context
	 * @param value
	 * @return void
	 */
	public static void setContextObject(String context, Object value) {
		setContextObject(getContext(), context, value);
	}
	
	/**
	 * Set Context Object with Activity No and Tab No
	 * @param ctx
	 * @param m_ActivityNo
	 * @param TabNo
	 * @param context
	 * @param value
	 * @return void
	 */
	public static void setContextObject(Context ctx, int m_ActivityNo, int TabNo, String context, Object value) {
		if (ctx == null || context == null)
			return;
		if (m_ActivityNo != WINDOW_FIND && m_ActivityNo != WINDOW_MLOOKUP)
			org.erpya.base.util.LogM.log(ctx, "Env", Level.FINE, "Context("+m_ActivityNo+","+TabNo+") " + context + "=" + value);
		//	
		setContextObject(ctx, m_ActivityNo+"|"+TabNo+"|"+context, value);
	}	//	setContext
	
	/**
	 * Set Context Object with Activity No and Tab No
	 * @param m_ActivityNo
	 * @param TabNo
	 * @param context
	 * @param value
	 * @return void
	 */
	public static void setContextObject(int m_ActivityNo, int TabNo, String context, Object value) {
		setContextObject(getContext(), m_ActivityNo, TabNo, context, value);
	}
	
	
	/**
	 * Set Context Object
	 * @param ctx
	 * @param context
	 * @param clazz
	 * @return
	 * @return Object
	 */
	//  TODO Implement it
	public static Object getContextObject(Context ctx, String context, Class<?> clazz) {
		/*Gson gson = new Gson();
		SharedPreferences pf = PreferenceManager.getDefaultSharedPreferences(ctx);
		String json = pf.getString(context, null);
		//	Valid null
		if(json == null)
			return null;
		//	
	    return gson.fromJson(json, clazz);*/
		return null;
	}
	
	/**
	 * Set Context Object
	 * @param context
	 * @param clazz
	 * @return
	 * @return Object
	 */
	public static Object getContextObject(String context, Class<?> clazz) {
		return getContextObject(getContext(), context, clazz);
	}
	
	/**
	 * Get Context Object with Activity No and Tab No
	 * @param ctx
	 * @param m_ActivityNo
	 * @param TabNo
	 * @param context
	 * @param clazz
	 * @return
	 * @return Object
	 */
	public static Object getContextObject(Context ctx, int m_ActivityNo, int TabNo, String context, Class<?> clazz) {
		if (ctx == null || context == null)
			throw new IllegalArgumentException ("Require Context");
		org.erpya.base.util.LogM.log(ctx, "Env", Level.INFO, "getContextObject=" + m_ActivityNo+"|"+TabNo+"|"+context);
		//	
		return getContextObject(ctx, m_ActivityNo+"|"+TabNo+"|"+context, clazz);
	}	//	getContext
	
	/**
	 * Get Context Object with Activity No and Tab No
	 * @param m_ActivityNo
	 * @param TabNo
	 * @param context
	 * @param clazz
	 * @return
	 * @return Object
	 */
	public static Object getContextObject(int m_ActivityNo, int TabNo, String context, Class<?> clazz) {
		return getContextObject(getContext(), m_ActivityNo, TabNo, context, clazz);
	}
	
	/**
	 * Set Context
	 * @param ctx
	 * @param context
	 * @param value
	 * @return void
	 */
	public static void setContext (Context ctx, String context, int value) {
		if (ctx == null || context == null)
			return;
		org.erpya.base.util.LogM.log(ctx, "Env", Level.INFO, "setContext(" + context+", " + value);
		setContext(context, String.valueOf(value));
	}	//	setContext
	
	
	/**
	 * Set Context for long
	 * @param ctx
	 * @param context
	 * @param value
	 * @return void
	 */
	public static void setContext(Context ctx, String context, long value) {
		if (ctx == null || context == null)
			return;
		org.erpya.base.util.LogM.log(ctx, "Env", Level.INFO, "setContext(" + context+", " + value);
		setContext(context, String.valueOf(value));
	}
	
	/**
	 * Set Context for Long
	 * @param context
	 * @param value
	 * @return void
	 */
	public static void setContext (String context, long value) {
		setContext (getContext(), context, value);
	}
	
	/**
	 * Set Context
	 * @param context
	 * @param value
	 * @return void
	 */
	public static void setContext (String context, int value) {
		setContext (getContext(), context, value);
	}
	
	
	/**
	 *	Set Context for Window & Tab to Value
	 *  @param ctx context
	 *  @param m_ActivityNo window no
	 *  @param TabNo tab no
	 *  @param context context key
	 *  @param value context value
	 *   */
	public static void setContext (Context ctx, int m_ActivityNo, int TabNo, String context, String value) {
		if (m_ActivityNo != WINDOW_FIND && m_ActivityNo != WINDOW_MLOOKUP)
			org.erpya.base.util.LogM.log(ctx, "Env", Level.FINE, "Context("+m_ActivityNo+","+TabNo+") " + context + "==" + value);
		//
		if (value == null)
			if (context.endsWith("_ID"))
				// TODO: Research potential problems with tables with Record_ID=0
				value = new String("0");
			else
				value = new String("");
		setContext(ctx, m_ActivityNo+"|"+TabNo+"|"+context, value);
	}	//	setContext
	
	/**
	 * Set Context for Window & Tab to Value
	 * @param m_ActivityNo
	 * @param TabNo
	 * @param context
	 * @param value
	 * @return void
	 */
	public static void setContext (int m_ActivityNo, int TabNo, String context, String value) {
		setContext(getContext(), m_ActivityNo, TabNo, context, value);
	}
	
	/**
	 * Set Context Array Int
	 * @param ctx
	 * @param m_ActivityNo
	 * @param TabNo
	 * @param context
	 * @param value
	 * @return void
	 */
	public static void setContextArray (Context ctx, int m_ActivityNo, int TabNo, String context, int[] value) {
		if (ctx == null || context == null)
			return;
		if (m_ActivityNo != WINDOW_FIND && m_ActivityNo != WINDOW_MLOOKUP)
			org.erpya.base.util.LogM.log(ctx, "Env", Level.FINE, "Context("+m_ActivityNo+","+TabNo+") " + context + "==" + value);
		//
		setContext(ctx, m_ActivityNo+"|"+TabNo+"|"+context, value);
	}	//	setContext
	
	/**
	 * Set Context Array Int
	 * @param m_ActivityNo
	 * @param TabNo
	 * @param context
	 * @param value
	 * @return void
	 */
	public static void setContextArray (int m_ActivityNo, int TabNo, String context, int[] value) {
		setContextArray(getContext(), m_ActivityNo, TabNo, context, value);
	}
	
	/**
	 * Set Context Array
	 * @param ctx
	 * @param m_ActivityNo
	 * @param TabNo
	 * @param context
	 * @param value
	 * @return void
	 */
	public static void setContextArray (Context ctx, int m_ActivityNo, int TabNo, String context, String[] value) {
		if (ctx == null || context == null)
			return;
		if (m_ActivityNo != WINDOW_FIND && m_ActivityNo != WINDOW_MLOOKUP)
			org.erpya.base.util.LogM.log(ctx, "Env", Level.FINE, "Context("+m_ActivityNo+","+TabNo+") " + context + "==" + value);
		//
		setContext(ctx, m_ActivityNo+"|"+TabNo+"|"+context, value);
	}	//	setContext
	
	/**
	 * Set Context Array
	 * @param m_ActivityNo
	 * @param TabNo
	 * @param context
	 * @param value
	 * @return void
	 */
	public static void setContextArray (int m_ActivityNo, int TabNo, String context, String[] value) {
		setContextArray(getContext(), m_ActivityNo, TabNo, context, value);
	}
	
	/**	
	 * Set Context as boolean
	 * @param ctx
	 * @param m_ActivityNo
	 * @param TabNo
	 * @param context
	 * @param value
	 * @return void
	 */
	public static void setContext(Context ctx, int m_ActivityNo, int TabNo, String context, boolean value) {
		if (ctx == null || context == null)
			return;
		if (m_ActivityNo != WINDOW_FIND && m_ActivityNo != WINDOW_MLOOKUP)
			org.erpya.base.util.LogM.log(ctx, "Env", Level.FINE, "Context("+m_ActivityNo+","+TabNo+") " + context + "==" + value);
		//	
		setContext(ctx, m_ActivityNo+"|"+TabNo+"|"+context, value);
	}	//	Set Context
	
	/**
	 * Set Context as boolean
	 * @param m_ActivityNo
	 * @param TabNo
	 * @param context
	 * @param value
	 * @return void
	 */
	public static void setContext(int m_ActivityNo, int TabNo, String context, boolean value) {
		setContext(getContext(), m_ActivityNo, TabNo, context, value);
	}
	
	/**
	 * Set Context as Boolean
	 * @param ctx
	 * @param m_ActivityNo
	 * @param context
	 * @param value
	 * @return void
	 */
	public static void setContext (Context ctx, int m_ActivityNo, String context, boolean value) {
		if (m_ActivityNo != WINDOW_FIND && m_ActivityNo != WINDOW_MLOOKUP)
			org.erpya.base.util.LogM.log(ctx, "Env", Level.FINE, "Context("+m_ActivityNo+") " + context + "==" + value);
		//
		setContext(ctx, m_ActivityNo+"|"+context, value);
	}	//	Set Context
	
	/**
	 * Set Context as Boolean
	 * @param m_ActivityNo
	 * @param context
	 * @param value
	 * @return void
	 */
	public static void setContext(int m_ActivityNo, String context, boolean value) {
		setContext(getContext(), m_ActivityNo, context, value);
	}
	
	/**
	 * Set Context with Activity No and Tab No
	 * @param ctx
	 * @param m_ActivityNo
	 * @param TabNo
	 * @param context
	 * @param value
	 * @return void
	 */
	public static void setContext (Context ctx, int m_ActivityNo, int TabNo, String context, int value) {
		if (m_ActivityNo != WINDOW_FIND && m_ActivityNo != WINDOW_MLOOKUP)
			org.erpya.base.util.LogM.log(ctx, "Env", Level.FINE, "Context("+m_ActivityNo+","+TabNo+") " + context + "=" + value);
		//	
		setContext(ctx, m_ActivityNo+"|"+TabNo+"|"+context, value);
	}	//	setContext
	
	/**
	 * Set Context with Activity No and Tab No
	 * @param m_ActivityNo
	 * @param TabNo
	 * @param context
	 * @param value
	 * @return void
	 */
	public static void setContext(int m_ActivityNo, int TabNo, String context, int value) {
		setContext(getContext(), m_ActivityNo, TabNo, context, value);
	}
	
	/**
	 * Set Context with Activity No
	 * @param ctx
	 * @param m_ActivityNo
	 * @param context
	 * @param value
	 * @return void
	 */
	public static void setContext(Context ctx, int m_ActivityNo, String context, int value) {
		if (m_ActivityNo != WINDOW_FIND && m_ActivityNo != WINDOW_MLOOKUP)
			org.erpya.base.util.LogM.log(ctx, "Env", Level.FINE, "Context("+m_ActivityNo+") " + context + "=" + value);
		//	
		setContext(ctx, m_ActivityNo+"|"+context, value);
	}	//	setContext
	
	/**
	 * Set Context with Activity No
	 * @param m_ActivityNo
	 * @param context
	 * @param value
	 * @return void
	 */
	public static void setContext(int m_ActivityNo, String context, int value) {
		setContext(getContext(), m_ActivityNo, context, value);
	}
	
	
	/**
	 *	Get Context and convert it to an integer (0 if error)
	 *  @param ctx context
	 *  @param m_ActivityNo window no
	 *  @param context context key
	 *  @return value or 0
	 */
	public static int getContextAsInt(Context ctx, int m_ActivityNo, String context) {
		String s = getContext(ctx, m_ActivityNo, context, false);
		if (s == null || s.length() == 0)
			return 0;
		//
		try {
			return Integer.parseInt(s);
		} catch (NumberFormatException e) {
			org.erpya.base.util.LogM.log(ctx, "Env", Level.SEVERE, "(" + context + ") = " + s, e);
		}
		//	Default Return
		return 0;
	}	//	getContextAsInt
	
	/**
	 * Get Context and convert it to an integer (0 if error)
	 * @param m_ActivityNo
	 * @param context
	 * @return
	 * @return int
	 */
	public static int getContextAsInt(int m_ActivityNo, String context) {
		return getContextAsInt(getContext(), m_ActivityNo, context);
	}
	
	/**
	 *	Get Context and convert it to an integer (0 if error)
	 *  @param ctx context
	 *  @param m_ActivityNo window no
	 *  @param context context key
	 *  @param onlyWindow  if true, no defaults are used unless explicitly asked for
	 *  @return value or 0
	 */
	public static int getContextAsInt(Context ctx, int m_ActivityNo, String context, boolean onlyWindow) {
		String s = getContext(ctx, m_ActivityNo, context, onlyWindow);
		if (s == null || s.length() == 0)
			return 0;
		//
		try {
			return Integer.parseInt(s);
		} catch (NumberFormatException e) {
			org.erpya.base.util.LogM.log(ctx, "Env", Level.SEVERE, "(" + context + ") = " + s, e);
		}
		//	Default Return
		return 0;
	}	//	getContextAsInt
	
	
	/**
	 * Get Context and convert it to an integer (0 if error)
	 * @param m_ActivityNo
	 * @param context
	 * @param onlyWindow
	 * @return
	 * @return int
	 */
	public static int getContextAsInt(int m_ActivityNo, String context, boolean onlyWindow) {
		return getContextAsInt(getContext(), m_ActivityNo, context, onlyWindow);
	}
	
	/**
	 *	Get Context and convert it to an integer (0 if error)
	 *  @param ctx context
	 *  @param WindowNo window no
	 *  @param TabNo tab no
	 * 	@param context context key
	 *  @return value or 0
	 */
	public static int getContextAsInt(Context ctx, int WindowNo, int TabNo, String context) {
		String s = getContext(ctx, WindowNo, TabNo, context);
		if (s == null || s.length() == 0)
			return 0;
		//
		try {
			return Integer.parseInt(s);
		} catch (NumberFormatException e) {
			org.erpya.base.util.LogM.log(ctx, "Env", Level.SEVERE, "(" + context + ") = " + s, e);
		}
		return 0;
	}	//	getContextAsInt
	
	/**
	 * Get Context and convert it to an integer (0 if error)
	 * @param WindowNo
	 * @param TabNo
	 * @param context
	 * @return
	 * @return int
	 */
	public static int getContextAsInt(int WindowNo, int TabNo, String context) {
		return getContextAsInt (getContext(), WindowNo, TabNo, context);
	}
	
	/**
	 *	Set SO Trx
	 *  @param ctx context
	 *  @param isSOTrx SO Context
	 */
	public static void setISOTrx (Context ctx, boolean isSOTrx) {
		if (ctx == null)
			return;
		setContext(ctx, "IsSOTrx", isSOTrx? "Y": "N");
	}	//	setSOTrx
	
	/**
	 * Set SO Trx
	 * @param isSOTrx
	 * @return void
	 */
	public static void setISOTrx (boolean isSOTrx) {
		setISOTrx(getContext(), isSOTrx);
	}
	
	/**
	 *	Get global Value of Context
	 *  @param ctx context
	 *  @param context context key
	 *  @return value or ""
	 */
	public static String getContext(Context ctx, String context) {
		SharedPreferences pf = getSharePreferences(ctx);
		String value = pf.getString(context, null);
		org.erpya.base.util.LogM.log(ctx, "Env", Level.FINE, "getContext(" + context + ") = " + value);
		return value;
	}	//	getContext
	
	/**
	 * Get global Value of Context
	 * @param context
	 * @return
	 * @return String
	 */
	public static String getContext(String context) {
		return getContext(getContext(), context);
	}
	
	/**
	 * Get Date with format to
	 * @param ctx
	 * @param context
	 * @param fromFormat
	 * @param toFormat
	 * @return
	 * @return String
	 */
	public static String getContextDateFormat(Context ctx, String context, String fromFormat, String toFormat) {
		String dateS = getContext(ctx, context);
		return getDateFormatString(dateS, fromFormat, toFormat);
	}	//	getContext
	
	/**
	 * Get Date with format to
	 * @param context
	 * @param fromFormat
	 * @param toFormat
	 * @return
	 * @return String
	 */
	public static String getContextDateFormat(String context, String fromFormat, String toFormat) {
		return getContextDateFormat(getContext(), context, fromFormat, toFormat);
	}
	
	/**
	 * Get Date with format
	 * @param dateS
	 * @param fromFormat
	 * @param toFormat
	 * @return
	 * @return String
	 */
	public static String getDateFormatString(String dateS, String fromFormat, String toFormat) {
		Date date;
		try {
			SimpleDateFormat fmtFront=new SimpleDateFormat(fromFormat);
	        SimpleDateFormat fmtBack=new SimpleDateFormat(toFormat);
			date = fmtFront.parse(dateS);
			return fmtBack.format(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		//	
        return null;
	}	//	getContext
	
	/**
	 * Get Date Format
	 * @param dateS
	 * @param fromFormat
	 * @param toFormat
	 * @return
	 * @return Date
	 */
	public static Date getDateFormat(String dateS, String fromFormat, String toFormat) {
		Date date;
		try {
			SimpleDateFormat fmtFront = new SimpleDateFormat(fromFormat);
			date = fmtFront.parse(dateS);
			return date;
		} catch (ParseException e) {
		//	
		}       
        return null;
	    
	}	//	getContext
	
	/**
	 * Get Current date formated
	 * @param format
	 * @return
	 * @return String
	 */
	public static String getCurrentDateFormat(String format) {
		Date date=new Date();
	    SimpleDateFormat fmt=new SimpleDateFormat(format);
	    return fmt.format(date);
	}	//	getContext
	
	/**
	 * Get Current Date
	 * @return
	 * @return Date
	 */
	public static Date getCurrentDate() {
		return new Date();		
	}	//	getContext
	
	/**
	 * Get Context As Boolean Value
	 * @param ctx
	 * @param context
	 * @return
	 * @return boolean
	 */
	public static boolean getContextAsBoolean (Context ctx, String context) {
		String s = getContext(ctx, context);
		//	
		boolean valid = (s != null && s.equals("Y"));
		//	Log
		org.erpya.base.util.LogM.log(ctx, "Env", Level.FINE, "getContextAsBoolean(" + context + ") = " + valid);
		return valid;
	}	//	getContext
	
	/**
	 * Get Context As Boolean Value
	 * @param context
	 * @return
	 * @return boolean
	 */
	public static boolean getContextAsBoolean (String context) {
		return getContextAsBoolean (getContext(), context);
	}
	
	/**
	 * Get Context as boolean
	 * @param ctx
	 * @param m_ActivityNo
	 * @param TabNo
	 * @param context
	 * @return
	 * @return boolean
	 */
	public static boolean getContextAsBoolean(Context ctx, int m_ActivityNo, int TabNo, String context) {
		String s = getContext(ctx, m_ActivityNo+"|"+TabNo+"|"+context);
		org.erpya.base.util.LogM.log(ctx, "Env", Level.INFO, "getContext=" + m_ActivityNo+"|"+TabNo+"|"+context);
		// If TAB_INFO, don't check Window and Global context - teo_sarca BF [ 2017987 ]
		if (TAB_INFO == TabNo)
			return s != null ? s.equals("Y") : false;
		//
		if (s == null)
			s = getContext(ctx, m_ActivityNo, context);
		return s != null ? s.equals("Y") : false;
	}	//	getContext
	
	/**
	 * Get Context as boolean
	 * @param m_ActivityNo
	 * @param TabNo
	 * @param context
	 * @return
	 * @return boolean
	 */
	public static boolean getContextAsBoolean(int m_ActivityNo, int TabNo, String context) {
		return getContextAsBoolean (getContext(), m_ActivityNo, TabNo, context);
	}
	
	/**
	 * Get Context as Boolean with Activity No
	 * @param ctx
	 * @param m_ActivityNo
	 * @param context
	 * @return
	 * @return boolean
	 */
	public static boolean getContextAsBoolean(Context ctx, int m_ActivityNo, String context) {
		String s = getContext(ctx, m_ActivityNo+"|"+context);
		org.erpya.base.util.LogM.log(ctx, "Env", Level.INFO, "getContext=" + m_ActivityNo+"|"+context);
		// If TAB_INFO, don't check Window and Global context - teo_sarca BF [ 2017987 ]
		//
		if (s == null)
			s = getContext(ctx, m_ActivityNo, context);
		return s != null ? s.equals("Y") : false;
	}	//	getContext
	
	/**
	 * Get Context as Boolean with Activity No
	 * @param m_ActivityNo
	 * @param context
	 * @return
	 * @return boolean
	 */
	public static boolean getContextAsBoolean(int m_ActivityNo, String context) {
		return getContextAsBoolean(getContext(), m_ActivityNo, context);
	}
	
	/**
	 *	Set Global Context to Y/N Value
	 *  @param ctx context
	 *  @param context context key
	 *  @param value context value
	 */
	public static void setContext(Context ctx, String context, boolean value) {
		setContext(ctx, context, value? "Y": "N");
	}	//	setContext
	
	/**
	 * Set Global Context to Y/N Value
	 * @param context
	 * @param value
	 * @return void
	 */
	public static void setContext(String context, boolean value) {
		setContext(getContext(), context, value);
	}

	/**
	 *	Set Context for Window to Value
	 *  @param ctx context
	 *  @param m_ActivityNo window no
	 *  @param context context key
	 *  @param value context value
	 */
	public static void setContext (Context ctx, int m_ActivityNo, String context, String value) {
		if (value == null)
			value = "";
		setContext(m_ActivityNo+"|"+context, value);
	}	//	setContext

	/**
	 * Set Context for Window to Value
	 * @param m_ActivityNo
	 * @param context
	 * @param value
	 * @return void
	 */
	public static void setContext(int m_ActivityNo, String context, String value) {
		setContext(getContext(), m_ActivityNo, context, value);
	}
	
	/**
	 *	Get Value of Context for Window.
	 *	if not found global context if available and enabled
	 *  @param ctx context
	 *  @param m_ActivityNo window
	 *  @param context context key
	 *  @param  onlyWindow  if true, no defaults are used unless explicitly asked for
	 *  @return value or ""
	 */
	public static String getContext(Context ctx, int m_ActivityNo, String context, boolean onlyWindow) {
		String s = getContext(ctx, m_ActivityNo+"|"+context);
		if (s == null) {
			//	Explicit Base Values
			if (context.startsWith("#") || context.startsWith("$"))
				return getContext(ctx, context);
			if (onlyWindow)			//	no Default values
				return "";
			return getContext(ctx, "#" + context);
		} else {
			org.erpya.base.util.LogM.log(ctx, "Env", Level.FINE, "getContext(" + m_ActivityNo+"|"+context + ") = " + s);
		}
		return s;
	}	//	getContext
	
	/**
	 * Get Value of Context for Window.
	 * @param m_ActivityNo
	 * @param context
	 * @param onlyWindow
	 * @return
	 * @return String
	 */
	public static String getContext(int m_ActivityNo, String context, boolean onlyWindow) {
		return getContext(getContext(), m_ActivityNo, context, onlyWindow);
	}
	
	/**
	 *	Get Value of Context for Window.
	 *	if not found global context if available
	 *  @param ctx context
	 *  @param m_ActivityNo window
	 *  @param context context key
	 *  @return value or ""
	 */
	public static String getContext(Context ctx, int m_ActivityNo, String context) {
		return getContext(ctx, m_ActivityNo, context, false);
	}	//	getContext
	
	/**
	 * Get Value of Context for Window.
	 * @param m_ActivityNo
	 * @param context
	 * @return
	 * @return String
	 */
	public static String getContext(int m_ActivityNo, String context) {
		return getContext(getContext(), m_ActivityNo, context);
	}

	/**
	 * Get Value of Context for Window & Tab,
	 * if not found global context if available.
	 * If TabNo is TAB_INFO only tab's context will be checked.
	 * @param ctx context
	 * @param m_ActivityNo window no
	 * @param TabNo tab no
	 * @param context context key
	 * @return value or ""
	 */
	public static String getContext(Context ctx, int m_ActivityNo, int TabNo, String context) {
		String s = getContext(ctx, m_ActivityNo+"|"+TabNo+"|"+context);
		org.erpya.base.util.LogM.log(ctx, "Env", Level.INFO, "getContext=" + m_ActivityNo+"|"+TabNo+"|"+context);
		// If TAB_INFO, don't check Window and Global context - teo_sarca BF [ 2017987 ]
		if (TAB_INFO == TabNo)
			return s != null ? s : "";
		//
		if (s == null || s.length() == 0)
			return getContext(ctx, m_ActivityNo, context, false);
		return s;
	}	//	getContext
	
	/**
	 * Get Value of Context for Window & Tab,
	 * @param m_ActivityNo
	 * @param TabNo
	 * @param context
	 * @return
	 * @return String
	 */
	public static String getContext(int m_ActivityNo, int TabNo, String context) {
		return getContext(getContext(), m_ActivityNo, TabNo, context);
	}
	
	/**
	 * Get Context As Array
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 18/10/2014, 15:15:20
	 * @param ctx
	 * @param context
	 * @return
	 * @return String[]
	 */
	public static String[] getContextAsArray(Context ctx, String context) {
		org.erpya.base.util.LogM.log(ctx, "Env", Level.INFO, "getContext=" + context);
		SharedPreferences pf = PreferenceManager.getDefaultSharedPreferences(ctx);
		String set = pf.getString(context, null);
		//	Default
		if(set == null)
			return null;
		//	Default
		String [] array = set.split("");
		return array;
	}	//	getContext
	
	/**
	 * Get Context As Array
	 * @param context
	 * @return
	 * @return String[]
	 */
	public static String[] getContextAsArray(String context) {
		return getContextAsArray(getContext(), context);
	}
	
	
	/**
	 * Get Context As Int Array
	 * @param ctx
	 * @param context
	 * @return
	 * @return int[]
	 */
	public static int[] getContextAsIntArray(Context ctx, String context) {
		String [] array = getContextAsArray(ctx, context);
		if(array == null)
			return new int[]{0};
		//	
		try {
			int[] arrayInt = new int[array.length];
			for(int i = 0; i < array.length; i++) {
				arrayInt[i] = Integer.parseInt(array[i]);
			}
			//	
			return arrayInt;
		} catch (Exception e) {
			//	
		}
		//	Default
		return new int[]{0};
	}
	
	/**
	 * Get Context As Int Array
	 * @param context
	 * @return
	 * @return int[]
	 */
	public static int[] getContextAsIntArray(String context) {
		return getContextAsIntArray(getContext(), context);
	}
	
	/**
	 * Get Context as Array with Activity and tab
	 * @param ctx
	 * @param m_ActivityNo
	 * @param TabNo
	 * @param context
	 * @return
	 * @return String[]
	 */
	public static String[] getContextAsArray(Context ctx, int m_ActivityNo, int TabNo, String context) {
		return getContextAsArray(ctx, m_ActivityNo+"|"+TabNo+"|"+context);
	}
	
	/**
	 * Get Context as Array with Activity and tab
	 * @param m_ActivityNo
	 * @param TabNo
	 * @param context
	 * @return
	 * @return String[]
	 */
	public static String[] getContextAsArray(int m_ActivityNo, int TabNo, String context) {
		return getContextAsArray(getContext(), m_ActivityNo, TabNo, context);
	}
	
	/**
	 * Get Context as Int Array with Activity and tab
	 * @param ctx
	 * @param m_ActivityNo
	 * @param TabNo
	 * @param context
	 * @return
	 * @return int[]
	 */
	public static int[] getContextAsIntArray(Context ctx, int m_ActivityNo, int TabNo, String context) {
		return getContextAsIntArray(ctx, m_ActivityNo+"|"+TabNo+"|"+context);
	}
	
	/**
	 * Get Context as Int Array with Activity and tab
	 * @param m_ActivityNo
	 * @param TabNo
	 * @param context
	 * @return
	 * @return int[]
	 */
	public static int[] getContextAsIntArray(int m_ActivityNo, int TabNo, String context) {
		return getContextAsIntArray(getContext(), m_ActivityNo, TabNo, context);
	}
	
	/**
	 * Set Context Array
	 * @param ctx
	 * @param context
	 * @param value
	 * @return void
	 */
	public static void setContext(Context ctx, String context, String[] value) {
		org.erpya.base.util.LogM.log(ctx, "Env", Level.INFO, "setContext(" + context+", " + value);
		Editor ep = getEditor(ctx);
		if(value == null) {
			ep.putString(context, null);
		} else {
			//	Set Array
			ep.putString(context, Arrays.toString(value));
		}
		//	Commit
		ep.commit();
	}	//	setContext
	
	/**
	 * Set Context Array
	 * @param context
	 * @param value
	 * @return void
	 */
	public static void setContext(String context, String[] value) {
		setContext(getContext(), context, value);
	}
	
	/**
	 * Set Context As Int Array
	 * @param ctx
	 * @param context
	 * @param value
	 * @return void
	 */
	public static void setContext(Context ctx, String context, int[] value) {
		if(value == null
				|| value.length == 0) {
			setContext(ctx, context, (String[])null);
			//	Return
			return;
		}
		//	Do it
		String[] strValue = new String[value.length];
		for(int i = 0; i < value.length; i++) {
			strValue[i] = String.valueOf(value[i]);
		}
		//	
		setContext(ctx, context, strValue);
	}
	
	/**
	 * Set Context As Int Array
	 * @param context
	 * @param value
	 * @return void
	 */
	public static void setContext(String context, int[] value) {
		setContext (getContext(), context, value);
	}
	

	/**
	 * Get Value of Context for Window & Tab,
	 * if not found global context if available.
	 * If TabNo is TAB_INFO only tab's context will be checked.
	 * @param ctx context
	 * @param m_ActivityNo window no
	 * @param TabNo tab no
	 * @param context context key
	 * @param onlyTab if true, no window value is searched
	 * @return value or ""
	 */
	public static String getContext(Context ctx, int m_ActivityNo, int TabNo, String context, boolean onlyTab) {
		final boolean onlyWindow = onlyTab ? true : false;
		return getContext(ctx, m_ActivityNo, TabNo, context, onlyTab, onlyWindow);
	}
	
	/**
	 * Get Value of Context for Window & Tab,
	 * if not found global context if available.
	 * If TabNo is TAB_INFO only tab's context will be checked.
	 * @param m_ActivityNo
	 * @param TabNo
	 * @param context
	 * @param onlyTab
	 * @return
	 * @return String
	 */
	public static String getContext(int m_ActivityNo, int TabNo, String context, boolean onlyTab) {
		return getContext(getContext(), m_ActivityNo, TabNo, context, onlyTab);
	}
	
	/**
	 * Get Value of Context for Window & Tab,
	 * if not found global context if available.
	 * If TabNo is TAB_INFO only tab's context will be checked.
	 * @param ctx context
	 * @param m_ActivityNo window no
	 * @param TabNo tab no
	 * @param context context key
	 * @param onlyTab if true, no window value is searched
	 * @param onlyWindow if true, no global context will be searched
	 * @return value or ""
	 */
	public static String getContext(Context ctx, int m_ActivityNo, int TabNo, String context, boolean onlyTab, boolean onlyWindow) {
		String s = getContext(ctx, m_ActivityNo+"|"+TabNo+"|"+context);
		if (TAB_INFO == TabNo)
			return s != null ? s : "";
		//
		if (s == null && ! onlyTab)
			return getContext(ctx, m_ActivityNo, context, onlyWindow);
		return s;
	}	//	getContext
	
	/**
	 * Get Value of Context for Window & Tab,
	 * if not found global context if available.
	 * If TabNo is TAB_INFO only tab's context will be checked
	 * @param m_ActivityNo
	 * @param TabNo
	 * @param context
	 * @param onlyTab
	 * @param onlyWindow
	 * @return
	 * @return String
	 */
	public static String getContext(int m_ActivityNo, int TabNo, String context, boolean onlyTab, boolean onlyWindow) {
		return getContext(getContext(), m_ActivityNo, TabNo, context, onlyTab, onlyWindow);
	}

	/**
	 *	Get Context and convert it to an integer (0 if error)
	 *  @param ctx context
	 *  @param context context key
	 *  @return value
	 */
	public static int getContextAsInt(Context ctx, String context) {
		try{
			if (ctx == null || context == null)
				throw new IllegalArgumentException ("Require Context");
			SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(ctx);
			String value = sp.getString(context, "0");
			if(value != null && value.length() > 0)
				return Integer.parseInt(value);
		} catch (Exception e) {
			
		}
		return 0;
	}	//	getContextAsInt
	
	/**
	 * Get Context for long values
	 * @param ctx
	 * @param context
	 * @return
	 * @return long
	 */
	public static long getContextAsLong(Context ctx, String context) {
		try{
			if (ctx == null || context == null)
				throw new IllegalArgumentException ("Require Context");
			SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(ctx);
			String value = sp.getString(context, "0");
			if(value != null && value.length() > 0)
				return Long.parseLong(value);
		} catch (Exception e) {
			
		}
		return 0;
	}	//	getContextAsInt
	
	/**
	 * Get Context for long values
	 * @param context
	 * @return
	 * @return long
	 */
	public static long getContextAsLong(String context) {
		return getContextAsLong(getContext(), context);
	}
	
	/**
	 * Get Context and convert it to an integer (0 if error)
	 * @param context
	 * @return
	 * @return int
	 */
	public static int getContextAsInt(String context) {
		return getContextAsInt(getContext(), context);
	}
	
	/**
	 *	Is Sales Order Trx
	 *  @param ctx context
	 *  @param WindowNo window no
	 *  @return true if SO (default)
	 */
	public static boolean isSOTrx (Context ctx, int WindowNo) {
		return getContextAsBoolean(ctx, WindowNo, "IsSOTrx");
	}	//	isSOTrx
	
	/**
	 * Is Sales Order Trx
	 * @return
	 * @return boolean
	 */
	public static boolean isSOTrx () {
		return getContextAsBoolean(getContext(), "IsSOTrx");
	}
	
	/**
	 * 	Get Login AD_Client_ID
	 *	@param ctx context
	 *	@return login AD_Client_ID
	 */
	public static int getAD_Client_ID (Context ctx) {
		return getContextAsInt(ctx, "#AD_Client_ID");
	}	//	getAD_Client_ID

	/**
	 * Get Login AD_Client_ID
	 * @return
	 * @return int
	 */
	public static int getAD_Client_ID() {
		return getAD_Client_ID(getContext());
	}
	
	/**
	 * 	Get Login AD_Org_ID
	 *	@param ctx context
	 *	@return login AD_Org_ID
	 */
	public static int getAD_Org_ID (Context ctx) {
		return getContextAsInt(ctx, "#AD_Org_ID");
	}	//	getAD_Client_ID

	/**
	 * Get Login AD_Org_ID
	 * @return
	 * @return int
	 */
	public static int getAD_Org_ID() {
		return getAD_Org_ID(getContext());
	}
	
	/**
	 * 	Get Login AD_User_ID
	 *	@param ctx context
	 *	@return login AD_User_ID
	 */
	public static int getAD_User_ID(Context ctx) {
		return getContextAsInt(ctx, "#AD_User_ID");
	}	//	getAD_User_ID
	
	/**
	 * Get Context
	 * @return
	 * @return int
	 */
	public static int getAD_User_ID () {
		return getAD_User_ID(getContext());
	}
	
	/**
	 * 	Get Login AD_Role_ID
	 *	@param ctx context
	 *	@return login AD_Role_ID
	 */
	public static int getAD_Role_ID(Context ctx) {
		return getContextAsInt(ctx, "#AD_Role_ID");
	}	//	getAD_Role_ID
	
	/**
	 * Get Login AD_Role_ID
	 * @return
	 * @return int
	 */
	public static int getAD_Role_ID() {
		return getAD_Role_ID(getContext());
	}	//	getAD_Role_ID

	/**
	 * Get Login M_Warehouse
	 * @param ctx
	 * @return
	 * @return int
	 */
	public static int getM_Warehouse_ID(Context ctx) {
		return getContextAsInt(ctx, "#M_Warehouse_ID");
	}	//	getAD_Role_ID
	
	/**
	 * Get Login M_Warehouse
	 * @return
	 * @return int
	 */
	public static int getM_Warehouse_ID() {
		return getM_Warehouse_ID(getContext());
	}
	
	/**
	 * Set User ID
	 * @param ctx
	 * @param m_AD_User_ID
	 * @return void
	 */
	public static void setAD_User_ID(Context ctx, int m_AD_User_ID) {
		setContext(ctx, "#AD_User_ID", m_AD_User_ID);
	}
	
	/**
	 * Set User ID
	 * @param m_AD_User_ID
	 * @return void
	 */
	public static void setAD_User_ID(int m_AD_User_ID) {
		setAD_User_ID(getContext(), m_AD_User_ID);
	}
	
	/**
	 * Set Client
	 * @param ctx
	 * @param m_AD_Client_ID
	 * @return void
	 */
	public static void setAD_Client_ID(Context ctx, int m_AD_Client_ID) {
		setContext(ctx, "#AD_Client_ID", m_AD_Client_ID);
	}
	
	/**
	 * Set Client
	 * @param m_AD_Client_ID
	 * @return void
	 */
	public static void setAD_Client_ID(int m_AD_Client_ID) {
		setAD_Client_ID(getContext(), m_AD_Client_ID);
	}
	
	/**
	 * Set Org
	 * @param ctx
	 * @param m_AD_Org_ID
	 * @return void
	 */
	public static void setAD_Org_ID(Context ctx, int m_AD_Org_ID) {
		setContext(ctx, "#AD_Org_ID", m_AD_Org_ID);
	}
	
	/**
	 * Set Org
	 * @param m_AD_Org_ID
	 * @return void
	 */
	public static void setAD_Org_ID(int m_AD_Org_ID) {
		setAD_Org_ID(getContext(), m_AD_Org_ID);
	}
	
	/**
	 * Set Role
	 * @param ctx
	 * @param m_AD_Role_ID
	 * @return void
	 */
	public static void setAD_Role_ID(Context ctx, int m_AD_Role_ID) {
		setContext(ctx, "#AD_Role_ID", m_AD_Role_ID);
	}
	
	/**
	 * Set Role
	 * @param m_AD_Role_ID
	 * @return void
	 */
	public static void setAD_Role_ID(int m_AD_Role_ID) {
		setAD_Role_ID(getContext(), m_AD_Role_ID);
	}
	
	/**
	 * Set Warehouse
	 * @param ctx
	 * @param m_M_Warehouse_ID
	 * @return void
	 */
	public static void setM_Warehouse_ID(Context ctx, int m_M_Warehouse_ID) {
		setContext(ctx, "#M_Warehouse_ID", m_M_Warehouse_ID);
	}
	
	/**
	 * Set Warehouse
	 * @param m_M_Warehouse_ID
	 * @return void
	 */
	public static void setM_Warehouse_ID(int m_M_Warehouse_ID) {
		setM_Warehouse_ID(getContext(), m_M_Warehouse_ID);
	}
	
	/**
	 * Set Save Pass
	 * @param ctx
	 * @param isSavePass
	 * @return void
	 */
	public static void setSavePass(Context ctx, boolean isSavePass) {
		setContext(ctx, "#SavePass", isSavePass);
	}
	
	/**
	 * Set Save Pass
	 * @param isSavePass
	 * @return void
	 */
	public static void setSavePass(boolean isSavePass) {
		setSavePass(getContext(), isSavePass);
	}
	
	/**
	 * Set Request Password
	 * @param ctx
	 * @return void
	 */
	public static void setRequestPass(Context ctx, boolean isAutoLogin) {
		setContext(ctx, "#RequestPass", isAutoLogin);
	}
	
	/**
	 * Set Login Pass Code
	 * @param passcode
	 * @return void
	 */
	public static void setLoginPasscode(int passcode) {
		setLoginPasscode(getContext(), passcode);
	}
	
	/**
	 * Set Login Pass Code
	 * @param ctx
	 * @param passcode
	 * @return void
	 */
	public static void setLoginPasscode(Context ctx, int passcode) {
		setContext(ctx, "#Login_Passcode", passcode);
	}
	
	/**
	 * Valid Login Pass Code
	 * @param ctx
	 * @param passcode
	 * @return
	 * @return boolean
	 */
	public static boolean validLoginPasscode(Context ctx, int passcode) {
		int internalPasscode = getContextAsInt(ctx, "#Login_Passcode");
		return internalPasscode == passcode;
	}
	
	/**
	 * Valid Login Pass code
	 * @param passcode
	 * @return
	 * @return boolean
	 */
	public static boolean validLoginPasscode(int passcode) {
		return validLoginPasscode(getContext(), passcode);
	}
	
	/**
	 * Get Login Pass Code
	 * @param ctx
	 * @return
	 * @return int
	 */
	public static int getLoginPasscode(Context ctx) {
		return getContextAsInt(ctx, "#Login_Passcode");
	}
	
	/**
	 * Get Login Pass Code
	 * @return
	 * @return int
	 */
	public static int getLoginPasscode() {
		return getLoginPasscode(getContext());
	}
	
	/**
	 * Set Request Password
	 * @param isAutoLogin
	 * @return void
	 */
	public static void setRequestPass(boolean isAutoLogin) {
		setRequestPass(getContext(), isAutoLogin);
	}
	
	/**
	 * Request Password on Login
	 * @param ctx
	 * @return
	 * @return boolean
	 */
	public static boolean isRequestPass(Context ctx) {
		return getContextAsBoolean(ctx, "#RequestPass");
	}
	
	/**
	 * Request Password on Login
	 * @return
	 * @return boolean
	 */
	public static boolean isRequestPass() {
		return isRequestPass(getContext());
	}
	
	/**
	 * Set database name
	 * @param ctx
	 * @param value
	 * @return void
	 */
	public static void setDB_PathName(Context ctx, String value) {
		setContext(ctx, DB_NAME_KEY, value);
	}
	
	/**
	 * Set database name
	 * @param value
	 * @return void
	 */
	public static void setDB_PathName(String value) {
		setDB_PathName(getContext(), value);
	}
	
	/**
	 * Get database Name
	 * @param ctx
	 * @return
	 * @return String
	 */
	public static String getDB_PathName(Context ctx) {
		return getContext(ctx, DB_NAME_KEY);
	}
	
	/**
	 * Get database Name
	 * @return
	 * @return String
	 */
	public static String getDB_PathName() {
		return getDB_PathName(getContext());
	}
	
	/**
	 * Set Document Path Name
	 * @param ctx
	 * @param value
	 * @return void
	 */
	public static void setDoc_DirectoryPathName(Context ctx, String value) {
		setContext(ctx, DOC_DIRECTORY_KEY, value);
	}
	
	/**
	 * Set Document Path Name
	 * @param value
	 * @return void
	 */
	public static void setDoc_DirectoryPathName(String value) {
		setDoc_DirectoryPathName(getContext(), value);
	}
	
	/**
	 * Get Document Path Name
	 * @param ctx
	 * @return
	 * @return String
	 */
	public static String getDoc_DirectoryPathName(Context ctx) {
		return getContext(ctx, DOC_DIRECTORY_KEY);
	}
	
	/**
	 * Get Document Path Name
	 * @return
	 * @return String
	 */
	public static String getDoc_DirectoryPathName() {
		return getDoc_DirectoryPathName(getContext());
	}
	
	/**
	 * Set Image Path Name
	 * @param ctx
	 * @param value
	 * @return void
	 */
	public static void setTmp_DirectoryPathName(Context ctx, String value) {
		setContext(ctx, TMP_DIRECTORY_KEY, value);
	}
	
	/**
	 * Set Image Path Name
	 * @param value
	 * @return void
	 */
	public static void setTmp_DirectoryPathName(String value) {
		setTmp_DirectoryPathName(getContext(), value);
	}
	
	/**
	 * Set Attachment Directory Name
	 * @param ctx
	 * @param value
	 * @return void
	 */
	public static void setAtt_DirectoryPathName(Context ctx, String value) {
		setContext(ctx, ATT_DIRECTORY_KEY, value);
	}
	
	/**
	 * Set Attachment Directory Name
	 * @param value
	 * @return void
	 */
	public static void setAtt_DirectoryPathName(String value) {
		setAtt_DirectoryPathName(getContext(), value);
	}
	
	/**
	 * Get Attachment Directory Name
	 * @param ctx
	 * @return
	 * @return String
	 */
	public static String getAtt_DirectoryPathName(Context ctx) {
		return getContext(ctx, ATT_DIRECTORY_KEY);
	}

	/**
	 * Get Business Chat Directory
	 * @param ctx
	 * @return
	 * @return String
	 */
	public static String getBC_DirectoryPathName(Context ctx) {
		return getContext(ctx, BC_DIRECTORY_KEY);
	}
	
	/**
	 * Get Business Chat Image Directory
	 * @param ctx
	 * @return
	 * @return String
	 */
	public static String getBC_IMG_DirectoryPathName(Context ctx) {
		return getAppBaseDirectory(ctx) + BC_IMG_DIRECTORY;
	}
	
	/**
	 * Set Business Chat Directory
	 * @param ctx
	 * @param value
	 * @return void
	 */
	public static void setBC_DirectoryPathName(Context ctx, String value) {
		setContext(ctx, BC_DIRECTORY_KEY, value);
	}
	
	/**
	 * Set Business Chat Directory
	 * @param value
	 * @return void
	 */
	public static void setBC_DirectoryPathName(String value) {
		setBC_DirectoryPathName(getContext(), value);
	}
	
	/**
	 * Get Attachment Directory Name
	 * @return
	 * @return String
	 */
	public static String getAtt_DirectoryPathName() {
		return getAtt_DirectoryPathName(getContext());
	}
	
	/**
	 * Get Image Path Name
	 * @param ctx
	 * @return
	 * @return String
	 */
	public static String getTmp_DirectoryPathName(Context ctx) {
		return getContext(ctx, TMP_DIRECTORY_KEY);
	}
	
	/**
	 * Get Image Path Name
	 * @return
	 * @return String
	 */
	public static String getTmp_DirectoryPathName() {
		return getTmp_DirectoryPathName(getContext());
	}
	
	/**
	 * Get database version
	 * @param ctx
	 * @return
	 * @return int
	 */
	public static int getDB_Version(Context ctx) {
		return Env.getContextAsInt(ctx, DB_VERSION);
	}	//	getAD_Role_ID
	
	/**
	 * Get database version
	 * @return
	 * @return int
	 */
	public static int getDB_Version() {
		return getDB_Version(getContext());
	}
	
	/**
	 * Get Tab Record Identifier
	 * @param ctx
	 * @param m_ActivityNo
	 * @param TabNo
	 * @return
	 * @return int
	 */
	public static int[] getTabRecord_ID(Context ctx, int m_ActivityNo, int TabNo) {
		//Msg.toastMsg(ctx, ID_TAB + tab + " " + getContextAsInt(ctx, ID_TAB + tab));
		return getContextAsIntArray(ctx, m_ActivityNo, TabNo, ID_TAB);
	}
	
	/**
	 * Get Tab Record Identifier
	 * @param m_ActivityNo
	 * @param TabNo
	 * @return
	 * @return int[]
	 */
	public static int[] getTabRecord_ID(int m_ActivityNo, int TabNo) {
		return getTabRecord_ID(getContext(), m_ActivityNo, TabNo);
	}
	
	/**
	 * Get Tab KeyColumns
	 * @param ctx
	 * @param m_ActivityNo
	 * @param TabNo
	 * @return
	 * @return String[]
	 */
	public static String[] getTabKeyColumns(Context ctx, int m_ActivityNo, int TabNo) {
		return getContextAsArray(ctx, m_ActivityNo, TabNo, ID_TAB_KEYS);
	}
	
	/**
	 * Get Tab KeyColumns
	 * @param m_ActivityNo
	 * @param TabNo
	 * @return
	 * @return String[]
	 */
	public static String[] getTabKeyColumns(int m_ActivityNo, int TabNo) {
		return getTabKeyColumns(getContext(), m_ActivityNo, TabNo);
	}
	

	/**
	 * Set Tab Record Identifier
	 * @param ctx
	 * @param m_ActivityNo
	 * @param TabNo
	 * @param record_ID
	 * @return void
	 */
	public static void setTabRecord_ID(Context ctx, int m_ActivityNo, int TabNo, int[] record_ID) {
		setContextArray(ctx, m_ActivityNo, TabNo, ID_TAB, record_ID);
	}
	
	/**
	 * Set Tab Record Identifier
	 * @param m_ActivityNo
	 * @param TabNo
	 * @param record_ID
	 * @return void
	 */
	public static void setTabRecord_ID(int m_ActivityNo, int TabNo, int[] record_ID) {
		setTabRecord_ID(getContext(), m_ActivityNo, TabNo, record_ID);
	}
	
	/**
	 * Set Tab Key Columns
	 * @param ctx
	 * @param m_ActivityNo
	 * @param TabNo
	 * @param keyColumns
	 * @return void
	 */
	public static void setTabKeyColumns(Context ctx, int m_ActivityNo, int TabNo, String[] keyColumns) {
		setContextArray(ctx, m_ActivityNo, TabNo, ID_TAB_KEYS, keyColumns);
	}
	
	/**
	 * Set Tab Key Columns
	 * @param m_ActivityNo
	 * @param TabNo
	 * @param keyColumns
	 * @return void
	 */
	public static void setTabKeyColumns(int m_ActivityNo, int TabNo, String[] keyColumns) {
		setTabKeyColumns(getContext(), m_ActivityNo, TabNo, keyColumns);
	}
	
	/**
	 * Set Current Tab
	 * @param ctx
	 * @param m_ActivityNo
	 * @return void
	 */
	public static void setCurrentTab(Context ctx, int m_ActivityNo, int tabNo) {
		setContext(ctx, m_ActivityNo, CURRENT_TAB, tabNo);
	}
	
	/**
	 * Set Current Tab
	 * @param m_ActivityNo
	 * @param tabNo
	 * @return void
	 */
	public static void setCurrentTab(int m_ActivityNo, int tabNo) {
		setCurrentTab(getContext(), m_ActivityNo, tabNo);
	}
	
	/**
	 * Get Current Tab
	 * @param ctx
	 * @param m_ActivityNo
	 * @return
	 * @return int
	 */
	public static int getCurrentTab(Context ctx, int m_ActivityNo) {
		return getContextAsInt(ctx, m_ActivityNo, CURRENT_TAB);
	}
	
	/**
	 * Get Current Tab
	 * @param m_ActivityNo
	 * @return
	 * @return int
	 */
	public static int getCurrentTab(int m_ActivityNo) {
		return getCurrentTab(getContext(), m_ActivityNo);
	}
	
	/**
	 * Is Current Tab No
	 * @param ctx
	 * @param m_ActivityNo
	 * @param tabNo
	 * @return
	 * @return boolean
	 */
	public static boolean isCurrentTab(Context ctx, int m_ActivityNo, int tabNo) {
		return tabNo == getContextAsInt(ctx, m_ActivityNo, CURRENT_TAB);
	}
	
	/**
	 * Is Current Tab No
	 * @param m_ActivityNo
	 * @param tabNo
	 * @return
	 * @return boolean
	 */
	public static boolean isCurrentTab(int m_ActivityNo, int tabNo) {
		return isCurrentTab(getContext(), m_ActivityNo, tabNo);
	}
	
	/**
	 * Set Database version
	 * @param ctx
	 * @param value
	 * @return void
	 */
	public static void setDB_Version(Context ctx, int value) {
		if (ctx == null)
			return;
		//
		Env.setContext(ctx, DB_VERSION, value);
	}
	
	/**
	 * Set Database version
	 * @param value
	 * @return void
	 */
	public static void setDB_Version(int value) {
		setDB_Version(getContext(), value);
	}
	
	/**
	 * Parse Context
	 * @param ctx
	 * @param whereClause
	 * @param ignoreUnparsable
	 * @return
	 * @return String
	 */
    public static String parseContext(Context ctx, String whereClause, boolean ignoreUnparsable) {
		return parseContext(ctx, 0, 0, whereClause, ignoreUnparsable, null);
	}
	
	/**
	 * Parse Context
	 * @param whereClause
	 * @param ignoreUnparsable
	 * @return
	 * @return String
	 */
    public static String parseContext(String whereClause, boolean ignoreUnparsable) {
		return parseContext(getContext(), whereClause, ignoreUnparsable);
	}
	
	
	
	/**
	 *	Parse Context replaces global or Window context @tag@ with actual value.
	 *
	 *  @tag@ are ignored otherwise "" is returned
	 *  @param ctx context
	 *	@param m_ActivityNo	Number of Window
	 *	@param whereClause Message to be parsed
	 * 	@param ignoreUnparsable if true, unsuccessful @return parsed String or "" if not successful and ignoreUnparsable
	 *	@return parsed context 
	 */
    public static String parseContext(Context ctx, int m_ActivityNo, int m_TabNo,
                                      String whereClause, boolean ignoreUnparsable, String defaultUnparseable) {
		if (whereClause == null || whereClause.length() == 0)
			return "";

		String token;
		String inStr = new String(whereClause);
		StringBuffer outStr = new StringBuffer();

		int i = inStr.indexOf('@');
		while (i != -1)
		{
			outStr.append(inStr.substring(0, i));			// up to @
			inStr = inStr.substring(i+1, inStr.length());	// from first @

			int j = inStr.indexOf('@');						// next @
			if (j < 0)
			{
				org.erpya.base.util.LogM.log(ctx, "Env", Level.INFO, "No second tag: " + inStr);
				return "";						//	no second tag
			}

			token = inStr.substring(0, j);
			//	
			String ctxInfo = getContext(ctx, m_ActivityNo, m_TabNo, token);	// get context
			if (ctxInfo != null && ctxInfo.length() == 0)
				ctxInfo = getContext(ctx, m_ActivityNo, token);	//	get from windows
			if (ctxInfo != null && ctxInfo.length() == 0 && (token.startsWith("#") || token.startsWith("$")) )
				ctxInfo = getContext(ctx, token);	// get global context
			if (ctxInfo != null && ctxInfo.length() == 0) {
				org.erpya.base.util.LogM.log(ctx, "Env", Level.INFO, "No Context for: " + token);
				if (!ignoreUnparsable && defaultUnparseable==null)
					return "";						//	token not found
				else if (!ignoreUnparsable && defaultUnparseable!=null)
					outStr.append(defaultUnparseable);
					//ctxInfo=defaultUnparseable;
			}
			else
				outStr.append(ctxInfo);				// replace context with Context

			inStr = inStr.substring(j+1, inStr.length());	// from second @
			i = inStr.indexOf('@');
		}
		outStr.append(inStr);						// add the rest of the string
		//	
		org.erpya.base.util.LogM.log(ctx, "Env", Level.FINE, "parseContext(" + inStr + ")");
		//	
		return outStr.toString();
	}	//	parseContext
	
	/**
	 * Get Array from string
	 * @param p_Value
	 * @param p_Separator
	 * @return
	 * @return String[]
	 */
	public static String[] getArrayFromString(String p_Value, String p_Separator) {
		if (p_Value == null || p_Value.length() == 0)
			return null;

		String token;
		String inStr = new String(p_Value);
		ArrayList<String> list = new ArrayList<String>();
		//	
		int i = inStr.indexOf(p_Separator);
		while (i != -1) {
			inStr = inStr.substring(i+1, inStr.length());	// from first @
			
			int j = inStr.indexOf(p_Separator);				// next @
			if (j < 0) {
				org.erpya.base.util.LogM.log(getContext(), "Env", Level.INFO, "No second tag: " + inStr);
				return null;								//	no second tag
			}
			//	
			token = inStr.substring(0, j);
			//	
			list.add(token);

			inStr = inStr.substring(j+1, inStr.length());	// from second @
			i = inStr.indexOf(p_Separator);
		}
		//	
		org.erpya.base.util.LogM.log(getContext(), "Env", Level.FINE, "getArrayFromString(" + inStr + ")");
		//	
		String array[] = new String[list.size()];
		list.toArray(array);
		return array;
	}	//	parseContext
	
	/**
	 * Get Default Language
	 * @param ctx
	 * @return
	 * @return String
	 */
	public static String getSOLanguage(Context ctx) {
		return ctx.getResources().getConfiguration().locale.getLanguage();
	}
	
	/**
	 * Get Default Language
	 * @return
	 * @return String
	 */
	public static String getSOLanguage() {
		return getSOLanguage(getContext());
	}
	
	/**
	 * Set Current Language
	 * @param ctx
	 * @param language
	 * @return void
	 */
	public static void setLanguage(Context ctx, String language) {
		setContext(ctx, LANGUAGE, language);
	}
	
	/**
	 * Set Current Language
	 * @param language
	 * @return void
	 */
	public static void setLanguage(String language) {
		setLanguage(getContext(), language);
	}
	
	/**
	 * Get System AD_Language
	 * @param ctx
	 * @return
	 * @return String
	 */
	public static String getLanguage(Context ctx) {
		return getContext(ctx, LANGUAGE);
	}
	
	/**
	 * Get System AD_Language
	 * @return
	 * @return String
	 */
	public static String getLanguage() {
		return getLanguage(getContext());
	}
	
	/**
	 * Get is base language
	 * @param ctx
	 * @return
	 * @return boolean
	 */
	public static boolean isBaseLanguage(Context ctx) {
		String language = getLanguage(ctx);
		if(language != null)
			return getLanguage(ctx).equals(BASE_LANGUAGE);
		return true;
	}
	
	/**
	 * Get is base language
	 * @return
	 * @return boolean
	 */
	public static boolean isBaseLanguage() {
		return isBaseLanguage(getContext());
	}
	
	/**
	 * Change Language
	 * @param ctx
	 * @param language
	 * @param metrics
	 * @return void
	 */
	public static void changeLanguage(Context ctx, String language, DisplayMetrics metrics) {
		Locale locale = Language.getLocale(language);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        ctx.getApplicationContext().getResources().updateConfiguration(config, metrics);
	}
	
	/**
	 * Change Language
	 * @param language
	 * @param metrics
	 * @return void
	 */
	public static void changeLanguage(String language, DisplayMetrics metrics) {
		changeLanguage(getContext(), language, metrics);
	}
	
	/**
	 * Get Locale from language
	 * @param ctx
	 * @return
	 * @return Locale
	 */
	public static Locale getLocate(Context ctx) {
		String language = getLanguage(ctx);
		if(language == null)
			language = BASE_LANGUAGE;
		return Language.getLocale(language);
	}
	
	/**
	 * Get Locale from language
	 * @return
	 * @return Locale
	 */
	public static Locale getLocate() {
		return getLocate(getContext());
	}
	
	/**
	 * Change Language
	 * @param ctx
	 * @param language
	 * @return void
	 */
	public static void changeLanguage(Context ctx, String language) {
		changeLanguage(ctx, language, null);
	}
	
	/**
	 * Change Language
	 * @param language
	 * @return void
	 */
	public static void changeLanguage(String language) {
		changeLanguage(getContext(), language);
	}
	
	/**
	 *  Set Date Pattern.
	 *  The date format is not checked for correctness
	 *  @param javaDatePattern for details see java.text.SimpleDateFormat,
	 *  format must be able to be converted to database date format by
	 *  using the upper case function.
	 *  It also must have leading zero for day and month.
	 */
	public static void setDateFormat(Context ctx, String javaDatePattern) {
		if (javaDatePattern == null)
			return;
		SimpleDateFormat m_dateFormat = (SimpleDateFormat)DateFormat.getDateInstance
				(DateFormat.SHORT, getLocate(ctx));
		try {
			m_dateFormat.applyPattern(javaDatePattern);
		} catch (Exception e) {
			org.erpya.base.util.LogM.log(ctx, "Env", Level.SEVERE, "Env.setDateFormat(Context, String)" + javaDatePattern, e);
			m_dateFormat = null;
		}
	}   //  setDateFormat
	
	/**
	 * Set Date Pattern.
	 *  The date format is not checked for correctness
	 * @param javaDatePattern
	 * @return void
	 */
	public static void setDateFormat(String javaDatePattern) {
		setDateFormat(getContext(), javaDatePattern);
	}


	/**
	 * Get Date Format from Pattern
	 * @param javaFormatPattern
	 * @return
	 */
	public static SimpleDateFormat getDateFormat(String javaFormatPattern) {
		return new SimpleDateFormat(javaFormatPattern);
	}

	/**
	 *  Get (Short) Date Format.
	 *  The date format must parseable by org.compiere.grid.ed.MDocDate
	 *  i.e. leading zero for date and month
	 *  @return date format MM/dd/yyyy - dd.MM.yyyy
	 */
	public static SimpleDateFormat getDateFormat(Context ctx) {
		SimpleDateFormat m_dateFormat = (SimpleDateFormat)DateFormat.getDateInstance(DateFormat.SHORT, getLocate(ctx));
		String sFormat = m_dateFormat.toPattern();
		//	some short formats have only one M and/or d (e.g. ths US)
		if (sFormat.indexOf("MM") == -1 || sFormat.indexOf("dd") == -1)
			{
			sFormat = sFormat.replaceFirst("d+", "dd");
			sFormat = sFormat.replaceFirst("M+", "MM");
		//	log.finer(sFormat + " => " + nFormat);
			m_dateFormat.applyPattern(sFormat);
		}
		//	Unknown short format => use JDBC
		if (m_dateFormat.toPattern().length() != 8)
			m_dateFormat.applyPattern("yyyy-MM-dd");

		//	4 digit year
		if (m_dateFormat.toPattern().indexOf("yyyy") == -1)
		{
			sFormat = m_dateFormat.toPattern();
			String nFormat = "";
			for (int i = 0; i < sFormat.length(); i++)
			{
				if (sFormat.charAt(i) == 'y')
					nFormat += "yy";
				else
					nFormat += sFormat.charAt(i);
			}
			m_dateFormat.applyPattern(nFormat);
		}
		m_dateFormat.setLenient(true);
		return m_dateFormat;
	}   //  getDateFormat
	
	/**
	 * Get (Short) Date Format.
	 * The date format must parseable by org.compiere.grid.ed.MDocDate
	 * @return
	 * @return SimpleDateFormat
	 */
	public static SimpleDateFormat getDateFormat() {
		return getDateFormat(getContext());
	}

	/**
	 * 	Get Date Time Format.
	 * 	Used for Display only
	 *  @return Date Time format MMM d, yyyy h:mm:ss a z -or- dd.MM.yyyy HH:mm:ss z
	 *  -or- j nnn aaaa, H' ?????? 'm' ????'
	 */
	public static SimpleDateFormat getDateTimeFormat(Context ctx) {
		SimpleDateFormat retValue = (SimpleDateFormat)DateFormat.getDateTimeInstance
			(DateFormat.MEDIUM, DateFormat.LONG, getLocate(ctx));
	//	log.finer("Pattern=" + retValue.toLocalizedPattern() + ", Loc=" + retValue.toLocalizedPattern());
		return retValue;
	}	//	getDateTimeFormat

	/**
	 * Get Date Time Format.
	 * Used for Display only
	 * @return
	 * @return SimpleDateFormat
	 */
	public static SimpleDateFormat getDateTimeFormat() {
		return getDateTimeFormat(getContext());
	}
	
	/**
	 * 	Get Time Format.
	 * 	Used for Display only
	 *  @return Time format h:mm:ss z or HH:mm:ss z
	 */
	public static SimpleDateFormat getTimeFormat(Context ctx) {
		return (SimpleDateFormat)DateFormat.getTimeInstance
			(DateFormat.LONG, getLocate(ctx));
	}	//	getTimeFormat
	
	/**
	 * Get Time Format.
	 * Used for Display only
	 * @return
	 * @return SimpleDateFormat
	 */
	public static SimpleDateFormat getTimeFormat() {
		return getTimeFormat(getContext());
	}
	
	/**
	 * Get Current Activity No
	 * @param ctx
	 * @return
	 * @return int
	 */
	public static int getActivityNo(Context ctx) {
		//	Get Current Activity No
		int aNo = getContextAsInt(ctx, ACTIVITY_NO);
		//Msg.toastMsg(ctx, "ActivityNo=" + aNo);
		//	Set New
		setContext(ctx, ACTIVITY_NO, ++aNo);
		//	Return Activity
		return aNo;
	}
	
	/**
	 * Get Current Activity No
	 * @return
	 * @return int
	 */
	public static int getActivityNo() {
		return getActivityNo(getContext());
	}
	
	/**
	 * Reset Activity No
	 * @param ctx
	 * @return void
	 */
	public static void resetActivityNo(Context ctx) {
		setContext(ctx, ACTIVITY_NO, 0);
	}
	
	/**
	 * Reset Activity No
	 * @return void
	 */
	public static void resetActivityNo() {
		resetActivityNo(getContext());
	}
	
	/**
	 * Set App Base Directory
	 * @param ctx
	 * @param path
	 * @return void
	 */
	private  static void setAppBaseDirectory(Context ctx, String path) {
		setContext(ctx, APP_BASE_DIRECTORY_CTX_NAME, path);
	}
	
	/**
	 * Set App Base Directory
	 * @param path
	 * @return void
	 */
	public static void setAppBaseDirectory(String path) {
		setAppBaseDirectory(getContext(), path);
	}
	
	/**
	 * Get App Base Directory
	 * @param ctx
	 * @return
	 * @return String
	 */
	public static String getAppBaseDirectory(Context ctx) {
		return getContext(ctx, APP_BASE_DIRECTORY_CTX_NAME);
	}
	
	/**
	 * Get App Base Directory
	 * @return
	 * @return String
	 */
	public static String getAppBaseDirectory() {
		return getAppBaseDirectory(getContext());
	}
	
	/**
	 * Get Resource Identifier from Attribute
	 * @param ctx
	 * @param att
	 * @return
	 * @return int
	 */
	public static int getResourceID(Context ctx, int att) {
		TypedValue typedValueAttr = new TypedValue();
		ctx.getTheme().resolveAttribute(att, typedValueAttr, true);
		//	Return
		return typedValueAttr.resourceId;
	}
	
	/**
	 * Get Resource
	 * @param ctx
	 * @param att
	 * @return
	 * @return TypeValue
	 */
	public static TypedValue getResource(Context ctx, int att) {
		TypedValue typedValueAttr = new TypedValue();
		ctx.getTheme().resolveAttribute(att, typedValueAttr, true);
		//	Return
		return typedValueAttr;
	}

	/**
	 * Get Current Supported Database
	 * @return
	 */
	public static String getCurrentSupportedDatabase() {
		return getContext(DB_CURRENT);
	}

	/**
	 * Set current supported database
	 * @param supportPath
	 */
	public static void setCurrentSupportedDatabase(String supportPath) {
		setContext(DB_CURRENT, supportPath);
	}

	/**
	 * Get Current Supported Secure Engine
	 * @return
	 */
	public static String getCurrentSupportedSecureEngine() {
		return getContext(SECURE_CURRENT);
	}

	/**
	 * Set current supported Secure Engine
	 * @param supportPath
	 */
	public static void setCurrentSupportedSecureEngine(String supportPath) {
		setContext(SECURE_CURRENT, supportPath);
	}
	
	/**	Context					*/
	public static Context context;
	/**	Share Preferences		*/
	public static SharedPreferences 	m_ShareP;
	/**	Env Instance			*/
	public static Env instance;
	
	/**************************************************************************
	 *  Application Context
	 */
	
	public static final String	SET_ENV = "#SET_ENV#";
	
	/** WindowNo for Main           */
	public static final int     WINDOW_MAIN = 0;
	/** WindowNo for Find           */
	public static final int     WINDOW_FIND = 1110;
	/** WinowNo for MLookup         */
	public static final int	   	WINDOW_MLOOKUP = 1111;
	/** WindowNo for PrintCustomize */
	public static final int     WINDOW_CUSTOMIZE = 1112;
	/** WindowNo for PrintCustomize */
	public static final int     WINDOW_INFO = 1113;

	/** Tab for Info                */
	public static final int     TAB_INFO = 1113;
	
	/**	New Line 		 */
	public static final String	NL = System.getProperty("line.separator");
	
	/**************************************************************************
	 *  Language issues
	 */

	/** Context Language identifier */
	public static final String      LANGUAGE 			= "#AD_Language";
	public static final String      BASE_LANGUAGE 		= "en_US";
	public static final String      BASE_COUNTRY_CODE 	= "US";
	
	/************************************Security******************************
	 * Security Access
	 */
	
	private static final String		S_PROCESS_ACCESS 	= "#PROCESS_ACCESS";
	private static final String		S_WINDOW_ACCESS 	= "#WINDOW_ACCESS";
	private static final String		S_DOCUMENT_ACCESS 	= "#DOCUMENT_ACCESS";
	private static final String		S_IS_ACCESS_LOADED 	= "#IS_ACCESS_LOADED";
	
	/************************************Env***************************************
	 * Database Context
	 */
	private static final String		DB_VERSION 			= "#DB_Version";
	private static final String		DB_NAME_KEY 		= "#DB_Name";
	private static final String		DB_CURRENT			= "#CurrentDB";
	private static final String		SECURE_CURRENT		= "#CurrentSecureEngine";
	private static final String		DOC_DIRECTORY_KEY 	= "#DOC_Name";
	private static final String		TMP_DIRECTORY_KEY 	= "#TMP_Name";
	private static final String		ATT_DIRECTORY_KEY 	= "#ATT_Name";
	private static final String		BC_DIRECTORY_KEY	= "#BC_Name";
	/******************************************************************************
	 * App Context
	 */
	public static final String 		APP_DIRECTORY 		= "ERP";
	public static final String 		DB_DIRECTORY 		= "data";
	public static final String 		DOC_DIRECTORY 		= APP_DIRECTORY + File.separator + "Documents";
	public static final String 		ATT_DIRECTORY 		= APP_DIRECTORY + File.separator + "Attachment";
	public static final String 		TMP_DIRECTORY 		= APP_DIRECTORY + File.separator + "Tmp";
	public static final String 		BC_DIRECTORY 		= APP_DIRECTORY + File.separator + "BChat";
	public static final String 		BC_IMG_DIRECTORY 	= BC_DIRECTORY + File.separator + "Images";
	//	Database
	public static final String 		DB_PATH_DIRECTORY 	= APP_DIRECTORY + File.separator + DB_DIRECTORY;
	public static final String		DB_PATH_NAME 		= DB_PATH_DIRECTORY + File.separator;// + DB.DB_NAME;
	//	Key Directory
	public static final String 		APP_BASE_DIRECTORY_CTX_NAME = "#APP_BASE_DIRECTORY_CTX_NAME";
	/***************************************************************************
	 * Prefix
	 */
	public static final String		ACTIVITY_NO 		= "|AN|";
	public static final String		CURRENT_ACTIVITY_NO = "|CAN|";
	public static final String		ID_TAB 				= "T_Record_ID";
	public static final String		ID_TAB_KEYS 		= "T_KeyColumn";
	public static final String		ID_PARENT_TAB 		= "T_P_Record_ID";
	public static final String		CURRENT_TAB 		= "|CT|";
	public static final String		SUMMARY_RECORD_ID 	= "#SummRID";
	
	/**	Big Decimal 0	 */
	public static final BigDecimal 	ZERO = new BigDecimal(0.0);
	/**	Big Decimal 1	 */
	public static final BigDecimal 	ONE = new BigDecimal(1.0);
	/**	Big Decimal 100	 */
	public static final BigDecimal 	ONEHUNDRED = new BigDecimal(100.0);
	
}
