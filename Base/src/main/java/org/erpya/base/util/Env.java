/*************************************************************************************
 * Product: Spin-Suite (Mobile Suite)                       		                 *
 * Copyright (C) 2012-2018 E.R.P. Consultores y Asociados, C.A.                      *
 * Contributor(s): Yamel Senih ysenih@erpya.com				  		                 *
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

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.stream.Collectors;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.Configuration;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.util.TypedValue;

/**
 * @author Yamel Senih
 * Enviroment singleton class
 */
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
	 * @param date
	 * @return
	 * @return boolean
	 */
	public static boolean loginDate(Date date) {
		Calendar currentDate = Calendar.getInstance();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		//	Format Date yyyy-MM-dd hh:mm:ss
		Env.setContext("#Date", simpleDateFormat.format(date.getTime()));
		
		simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		
		String curDate = simpleDateFormat.format(currentDate.getTime());
		String ctxDate = simpleDateFormat.format(date.getTime());
		
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
	 * @return
	 * @return boolean
	 */
	public static boolean isEnvLoad() {
        return getContextAsBoolean(SET_ENV);
	}
	
	/**
	 * Get is load activity
	 * @return
	 * @return boolean
	 */
	public static boolean isLoadedActivity() {
        return getContextAsBoolean("#IsLoadedActivity");
	}

	
	/**
	 * Set is initial load
	 * @param value
	 * @return void
	 */
	public static void setIsEnvLoad(boolean value) {
        setContext(SET_ENV, value);
	}
	
	/**
	 * Set if loaded activity
	 * @param value
	 * @return void
	 */
	public static void setIsLoadedActivity(boolean value) {
        setContext("#IsLoadedActivity", value);
	}

	/**
	 * Get Keys from context
	 * @param prefix
	 * @return
	 */
	public static List<String> getKeys(String prefix) {
		final String searchPrefix = (Util.isEmpty(prefix)? "": prefix.trim());
		//	Get Preferences
		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
		//	Get All Entries
		return preferences.getAll().keySet().stream()
				.filter(key -> key != null)
				.filter(key -> key.startsWith(searchPrefix)).collect(Collectors.toList());
	}

	/**
	 * Cache Reset
	 * @return
	 * @return int
	 */
	public static int cacheReset() {
		return cacheReset("#", true);
	}
	
	/**
	 * Cache Reset
	 * @param prefix
	 * @param ignorePrefix
	 * @return
	 * @return int
	 */
	public static int cacheReset(String prefix, boolean ignorePrefix) {
		//	Get Preferences
		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
		//	Get All Entries
		Map<String, ?> allEntries = preferences.getAll();
		Editor editor = getEditor();
		//	Delete
		AtomicInteger deletedEntries = new AtomicInteger();
		getKeys(prefix).forEach(key -> {
			editor.remove(key);
			deletedEntries.addAndGet(1);
			LogM.log(getContext(), "ENV", Level.FINE, "Entry [" + key + "] Deleted");
		});

		//	Commit
		if(deletedEntries.get() > 0) {
			editor.commit();
		}
		//	Return
		return deletedEntries.get();
	}
	
	/**
	 * Get share preference editor
	 * @return
	 * @return Editor
	 */
	public static Editor getEditor() {
		sharePreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
		return sharePreferences.edit();
	}
	
	/**
	 * Get Share Preferences
	 * @return
	 * @return SharedPreferences
	 */
	public static SharedPreferences getSharePreferences() {
		if(sharePreferences == null) {
			sharePreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
		}
		return sharePreferences;
	}
	
	/**
	 * Remove a context value
	 * @param context
	 * @return void
	 */
	public static void removeContext(String context) {
		if (getContext() == null || context == null)
			return;
		//	Log
		LogM.log(getContext(), "Env", Level.FINE, "removeContext("  + context + ")");
		//	
		Editor ep = getEditor();
		ep.remove(context);
		ep.commit();
	}
	
	/**
	 *	Set Global Context to Value
	 *  @param context context key
	 *  @param value context value
	 */
	public static void setContext (String context, String value) {
		if (getContext() == null || context == null)
			return;
		//	Log
		LogM.log(getContext(), "Env", Level.FINE, "setContext("  + context + ", " + value + ")");
		//
		if (value == null)
			value = "";
		Editor editor = getEditor();
		editor.putString(context, value);
		editor.commit();
	}	//	setContext
	
	/**
	 * Set Context Object
	 * @param context
	 * @param value
	 * @return void
	 */
	public static void setContextObject(String context, Object value) {
		if(value instanceof Integer) {
			setContext(context, (Integer) value);
		} if(value instanceof Boolean) {
			setContext(context, (Boolean) value);
		} else {
			setContext(context, String.valueOf(value));
		}
		/*Editor prefsEditor = getEditor();
        Gson gson = new Gson();
        String json = gson.toJson(value);
        prefsEditor.putString(context, json);
        prefsEditor.commit();*/
	}
	
	/**
	 * Set Context Object with Activity No and Tab No
	 * @param activityNo
	 * @param tabNo
	 * @param context
	 * @param value
	 * @return void
	 */
	public static void setContextObject(int activityNo, int tabNo, String context, Object value) {
		if (getContext() == null || context == null)
			return;
		if (activityNo != WINDOW_FIND && activityNo != WINDOW_MLOOKUP)
			LogM.log(getContext(), "Env", Level.FINE, "Context("+activityNo+","+tabNo+") " + context + "=" + value);
		//	
		setContextObject(activityNo+"|"+tabNo+"|"+context, value);
	}	//	setContext
	
	/**
	 * Set Context Object
	 * @param context
	 * @param clazz
	 * @return
	 * @return Object
	 */
	//  TODO Implement it
	public static Object getContextObject(String context, Class<?> clazz) {
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
	 * Get Context Object with Activity No and Tab No
	 * @param activityNo
	 * @param tabNo
	 * @param context
	 * @param clazz
	 * @return
	 * @return Object
	 */
	public static Object getContextObject(int activityNo, int tabNo, String context, Class<?> clazz) {
		if (getContext() == null || context == null)
			throw new IllegalArgumentException ("Require Context");
		LogM.log(getContext(), "Env", Level.INFO, "getContextObject=" + activityNo+"|"+tabNo+"|"+context);
		//	
		return getContextObject(activityNo+"|"+tabNo+"|"+context, clazz);
	}	//	getContext
	
	/**
	 * Set Context
	 * @param context
	 * @param value
	 * @return void
	 */
	public static void setContext (String context, int value) {
		if (getContext() == null || context == null)
			return;
		LogM.log(getContext(), "Env", Level.INFO, "setContext(" + context+", " + value);
		setContext(context, String.valueOf(value));
	}	//	setContext
	
	
	/**
	 * Set Context for long
	 * @param context
	 * @param value
	 * @return void
	 */
	public static void setContext(String context, long value) {
		if (getContext() == null || context == null)
			return;
		LogM.log(getContext(), "Env", Level.INFO, "setContext(" + context+", " + value);
		setContext(context, String.valueOf(value));
	}
	
	/**
	 *	Set Context for Window & Tab to Value
	 *  @param activityNo window no
	 *  @param tabNo tab no
	 *  @param context context key
	 *  @param value context value
	 *   */
	public static void setContext (int activityNo, int tabNo, String context, String value) {
		if (activityNo != WINDOW_FIND && activityNo != WINDOW_MLOOKUP)
			LogM.log(getContext(), "Env", Level.FINE, "Context("+activityNo+","+tabNo+") " + context + "==" + value);
		//
		if (value == null)
			if (context.endsWith("_ID"))
				// TODO: Research potential problems with tables with Record_ID=0
				value = new String("0");
			else
				value = new String("");
		setContext(activityNo+"|"+tabNo+"|"+context, value);
	}	//	setContext
	
	/**
	 * Set Context Array Int
	 * @param activityNo
	 * @param tabNo
	 * @param context
	 * @param value
	 * @return void
	 */
	public static void setContextArray (int activityNo, int tabNo, String context, int[] value) {
		if (getContext() == null || context == null)
			return;
		if (activityNo != WINDOW_FIND && activityNo != WINDOW_MLOOKUP)
			LogM.log(getContext(), "Env", Level.FINE, "Context("+activityNo+","+tabNo+") " + context + "==" + value);
		//
		setContext(activityNo+"|"+tabNo+"|"+context, value);
	}	//	setContext

	/**
	 * Set Context Array
	 * @param activityNo
	 * @param tabNo
	 * @param context
	 * @param value
	 * @return void
	 */
	public static void setContextArray (int activityNo, int tabNo, String context, String[] value) {
		if (getContext() == null || context == null)
			return;
		if (activityNo != WINDOW_FIND && activityNo != WINDOW_MLOOKUP)
			LogM.log(getContext(), "Env", Level.FINE, "Context("+activityNo+","+tabNo+") " + context + "==" + value);
		//
		setContext(activityNo+"|"+tabNo+"|"+context, value);
	}	//	setContext

	/**	
	 * Set Context as boolean
	 * @param activityNo
	 * @param tabNo
	 * @param context
	 * @param value
	 * @return void
	 */
	public static void setContext(int activityNo, int tabNo, String context, boolean value) {
		if (getContext() == null || context == null)
			return;
		if (activityNo != WINDOW_FIND && activityNo != WINDOW_MLOOKUP)
			LogM.log(getContext(), "Env", Level.FINE, "Context("+activityNo+","+tabNo+") " + context + "==" + value);
		//	
		setContext(activityNo+"|"+tabNo+"|"+context, value);
	}	//	Set Context

	/**
	 * Set Context as Boolean
	 * @param activityNo
	 * @param context
	 * @param value
	 * @return void
	 */
	public static void setContext (int activityNo, String context, boolean value) {
		if (activityNo != WINDOW_FIND && activityNo != WINDOW_MLOOKUP)
			LogM.log(getContext(), "Env", Level.FINE, "Context("+activityNo+") " + context + "==" + value);
		//
		setContext(activityNo+"|"+context, value);
	}	//	Set Context
	
	/**
	 * Set Context with Activity No and Tab No
	 * @param activityNo
	 * @param tabNo
	 * @param context
	 * @param value
	 * @return void
	 */
	public static void setContext (int activityNo, int tabNo, String context, int value) {
		if (activityNo != WINDOW_FIND && activityNo != WINDOW_MLOOKUP)
			LogM.log(getContext(), "Env", Level.FINE, "Context("+activityNo+","+tabNo+") " + context + "=" + value);
		//	
		setContext(activityNo+"|"+tabNo+"|"+context, value);
	}	//	setContext
	
	/**
	 * Set Context with Activity No
	 * @param activityNo
	 * @param context
	 * @param value
	 * @return void
	 */
	public static void setContext(int activityNo, String context, int value) {
		if (activityNo != WINDOW_FIND && activityNo != WINDOW_MLOOKUP)
			LogM.log(getContext(), "Env", Level.FINE, "Context("+activityNo+") " + context + "=" + value);
		//	
		setContext(activityNo+"|"+context, value);
	}	//	setContext
	
	/**
	 *	Get Context and convert it to an integer (0 if error)
	 *  @param activityNo window no
	 *  @param context context key
	 *  @return value or 0
	 */
	public static int getContextAsInt(int activityNo, String context) {
		String s = getContext(activityNo, context, false);
		if (s == null || s.length() == 0)
			return 0;
		//
		try {
			return Integer.parseInt(s);
		} catch (NumberFormatException e) {
			LogM.log(getContext(), "Env", Level.SEVERE, "(" + context + ") = " + s, e);
		}
		//	Default Return
		return 0;
	}	//	getContextAsInt

	/**
	 *	Get Context and convert it to an integer (0 if error)
	 *  @param activityNo window no
	 *  @param context context key
	 *  @param onlyWindow  if true, no defaults are used unless explicitly asked for
	 *  @return value or 0
	 */
	public static int getContextAsInt(int activityNo, String context, boolean onlyWindow) {
		String s = getContext(activityNo, context, onlyWindow);
		if (s == null || s.length() == 0)
			return 0;
		//
		try {
			return Integer.parseInt(s);
		} catch (NumberFormatException e) {
			LogM.log(getContext(), "Env", Level.SEVERE, "(" + context + ") = " + s, e);
		}
		//	Default Return
		return 0;
	}	//	getContextAsInt

	/**
	 *	Get Context and convert it to an integer (0 if error)
	 *  @param windowNo window no
	 *  @param tabNo tab no
	 * 	@param context context key
	 *  @return value or 0
	 */
	public static int getContextAsInt(int windowNo, int tabNo, String context) {
		String s = getContext(windowNo, tabNo, context);
		if (s == null || s.length() == 0)
			return 0;
		//
		try {
			return Integer.parseInt(s);
		} catch (NumberFormatException e) {
			LogM.log(getContext(), "Env", Level.SEVERE, "(" + context + ") = " + s, e);
		}
		return 0;
	}	//	getContextAsInt

	/**
	 *	Set SO Trx
	 *  @param isSOTrx SO Context
	 */
	public static void setISOTrx (boolean isSOTrx) {
		setContext("IsSOTrx", isSOTrx? "Y": "N");
	}	//	setSOTrx

	/**
	 *	Get global Value of Context
	 *  @param context context key
	 *  @return value or ""
	 */
	public static String getContext(String context) {
		SharedPreferences pf = getSharePreferences();
		String value = pf.getString(context, null);
		LogM.log(getContext(), "Env", Level.FINE, "getContext(" + context + ") = " + value);
		return value;
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
		String dateS = getContext(context);
		return getDateFormatString(dateS, fromFormat, toFormat);
	}	//	getContext

	/**
	 * Get Date with format
	 * @param stringDate
	 * @param fromFormat
	 * @param toFormat
	 * @return
	 * @return String
	 */
	public static String getDateFormatString(String stringDate, String fromFormat, String toFormat) {
		Date date;
		try {
			SimpleDateFormat fmtFront=new SimpleDateFormat(fromFormat);
	        SimpleDateFormat fmtBack=new SimpleDateFormat(toFormat);
			date = fmtFront.parse(stringDate);
			return fmtBack.format(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		//	
        return null;
	}	//	getContext
	
	/**
	 * Get Date Format
	 * @param stringDate
	 * @param fromFormat
	 * @param toFormat
	 * @return
	 * @return Date
	 */
	public static Date getDateFormat(String stringDate, String fromFormat, String toFormat) {
		Date date;
		try {
			SimpleDateFormat fmtFront = new SimpleDateFormat(fromFormat);
			date = fmtFront.parse(stringDate);
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
	 * @param context
	 * @return
	 * @return boolean
	 */
	public static boolean getContextAsBoolean (String context) {
		String s = getContext(context);
		//	
		boolean valid = (s != null && s.equals("Y"));
		//	Log
		LogM.log(getContext(), "Env", Level.FINE, "getContextAsBoolean(" + context + ") = " + valid);
		return valid;
	}	//	getContext

	/**
	 * Get Context as boolean
	 * @param activityNo
	 * @param tabNo
	 * @param context
	 * @return
	 * @return boolean
	 */
	public static boolean getContextAsBoolean(int activityNo, int tabNo, String context) {
		String contextValue = getContext(activityNo+"|"+tabNo+"|"+context);
		LogM.log(getContext(), "Env", Level.INFO, "getContext=" + activityNo+"|"+tabNo+"|"+context);
		// If TAB_INFO, don't check Window and Global context - teo_sarca BF [ 2017987 ]
		if (TAB_INFO == tabNo)
			return contextValue != null ? contextValue.equals("Y") : false;
		//
		if (contextValue == null)
			contextValue = getContext(activityNo, context);
		return contextValue != null ? contextValue.equals("Y") : false;
	}	//	getContext

	/**
	 * Get Context as Boolean with Activity No
	 * @param activityNo
	 * @param context
	 * @return
	 * @return boolean
	 */
	public static boolean getContextAsBoolean(int activityNo, String context) {
		String contextValue = getContext(activityNo+"|"+context);
		LogM.log(getContext(), "Env", Level.INFO, "getContext=" + activityNo+"|"+context);
		// If TAB_INFO, don't check Window and Global context - teo_sarca BF [ 2017987 ]
		//
		if (contextValue == null)
			contextValue = getContext(activityNo, context);
		return contextValue != null ? contextValue.equals("Y") : false;
	}	//	getContext

	/**
	 *	Set Global Context to Y/N Value
	 *  @param context context key
	 *  @param value context value
	 */
	public static void setContext(String context, boolean value) {
		setContext(context, value? "Y": "N");
	}	//	setContext

	/**
	 *	Set Context for Window to Value
	 *  @param activityNo window no
	 *  @param context context key
	 *  @param value context value
	 */
	public static void setContext(int activityNo, String context, String value) {
		if (value == null)
			value = "";
		setContext(activityNo+"|"+context, value);
	}	//	setContext
	
	/**
	 *	Get Value of Context for Window.
	 *	if not found global context if available and enabled
	 *  @param activityNo window
	 *  @param context context key
	 *  @param  onlyWindow  if true, no defaults are used unless explicitly asked for
	 *  @return value or ""
	 */
	public static String getContext(int activityNo, String context, boolean onlyWindow) {
		String contextValue = getContext(activityNo+"|"+context);
		if (contextValue == null) {
			//	Explicit Base Values
			if (context.startsWith("#") || context.startsWith("$"))
				return getContext(context);
			if (onlyWindow)			//	no Default values
				return "";
			return getContext("#" + context);
		} else {
			LogM.log(getContext(), "Env", Level.FINE, "getContext(" + activityNo+"|"+context + ") = " + contextValue);
		}
		return contextValue;
	}	//	getContext

	/**
	 *	Get Value of Context for Window.
	 *	if not found global context if available
	 *  @param activityNo window
	 *  @param context context key
	 *  @return value or ""
	 */
	public static String getContext(int activityNo, String context) {
		return getContext(activityNo, context, false);
	}	//	getContext

	/**
	 * Get Value of Context for Window & Tab,
	 * if not found global context if available.
	 * If TabNo is TAB_INFO only tab's context will be checked.
	 * @param activityNo window no
	 * @param tabNo tab no
	 * @param context context key
	 * @return value or ""
	 */
	public static String getContext(int activityNo, int tabNo, String context) {
		String contextValue = getContext(activityNo+"|"+tabNo+"|"+context);
		LogM.log(getContext(), "Env", Level.INFO, "getContext=" + activityNo+"|"+tabNo+"|"+context);
		// If TAB_INFO, don't check Window and Global context - teo_sarca BF [ 2017987 ]
		if (TAB_INFO == tabNo)
			return contextValue != null ? contextValue : "";
		//
		if (contextValue == null || contextValue.length() == 0)
			return getContext(activityNo, context, false);
		return contextValue;
	}	//	getContext
	
	/**
	 * Get Context As Array
	 * @param context
	 * @return
	 * @return String[]
	 */
	@Nullable
	public static String[] getContextAsArray(String context) {
		LogM.log(getContext(), "Env", Level.INFO, "getContext=" + context);
		SharedPreferences pf = PreferenceManager.getDefaultSharedPreferences(getContext());
		String set = pf.getString(context, null);
		//	Default
		if(set == null)
			return null;
		//	Default
		String [] array = set.split("");
		return array;
	}	//	getContext

	/**
	 * Get Context As Int Array
	 * @param context
	 * @return
	 * @return int[]
	 */
	public static int[] getContextAsIntArray(String context) {
		String [] array = getContextAsArray(context);
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
	 * Get Context as Array with Activity and tab
	 * @param activityNo
	 * @param tabNo
	 * @param context
	 * @return
	 * @return String[]
	 */
	public static String[] getContextAsArray(int activityNo, int tabNo, String context) {
		return getContextAsArray(activityNo+"|"+tabNo+"|"+context);
	}

	/**
	 * Get Context as Int Array with Activity and tab
	 * @param activityNo
	 * @param tabNo
	 * @param context
	 * @return
	 * @return int[]
	 */
	public static int[] getContextAsIntArray(int activityNo, int tabNo, String context) {
		return getContextAsIntArray(activityNo+"|"+tabNo+"|"+context);
	}
	
	/**
	 * Set Context Array
	 * @param context
	 * @param value
	 * @return void
	 */
	public static void setContext(String context, String[] value) {
		LogM.log(getContext(), "Env", Level.INFO, "setContext(" + context+", " + value);
		Editor ep = getEditor();
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
	 * Set Context As Int Array
	 * @param context
	 * @param value
	 * @return void
	 */
	public static void setContext(String context, int[] value) {
        if (value == null
                || value.length == 0) {
            setContext(context, (String[]) null);
            //	Return
            return;
        }
        //	Do it
        String[] strValue = new String[value.length];
        for (int i = 0; i < value.length; i++) {
            strValue[i] = String.valueOf(value[i]);
        }
        //
        setContext(context, strValue);
    }

	/**
	 * Get Value of Context for Window & Tab,
	 * if not found global context if available.
	 * If TabNo is TAB_INFO only tab's context will be checked.
	 * @param activityNo window no
	 * @param tabNo tab no
	 * @param context context key
	 * @param onlyTab if true, no window value is searched
	 * @return value or ""
	 */
	public static String getContext(int activityNo, int tabNo, String context, boolean onlyTab) {
		final boolean onlyWindow = onlyTab ? true : false;
		return getContext(activityNo, tabNo, context, onlyTab, onlyWindow);
	}

	/**
	 * Get Value of Context for Window & Tab,
	 * if not found global context if available.
	 * If TabNo is TAB_INFO only tab's context will be checked.
	 * @param activityNo window no
	 * @param tabNo tab no
	 * @param context context key
	 * @param onlyTab if true, no window value is searched
	 * @param onlyWindow if true, no global context will be searched
	 * @return value or ""
	 */
	public static String getContext(int activityNo, int tabNo, String context, boolean onlyTab, boolean onlyWindow) {
		String contextValue = getContext(activityNo+"|"+tabNo+"|"+context);
		if (TAB_INFO == tabNo)
			return contextValue != null ? contextValue : "";
		//
		if (contextValue == null && ! onlyTab)
			return getContext(activityNo, context, onlyWindow);
		return contextValue;
	}	//	getContext

	/**
	 *	Get Context and convert it to an integer (0 if error)
	 *  @param context context key
	 *  @return value
	 */
	public static int getContextAsInt(String context) {
		try{
			if (getContext() == null || context == null)
				throw new IllegalArgumentException ("Require Context");
			SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getContext());
			String value = sp.getString(context, "0");
			if(value != null && value.length() > 0)
				return Integer.parseInt(value);
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
		try{
			if (getContext() == null || context == null)
				throw new IllegalArgumentException ("Require Context");
			SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getContext());
			String value = sp.getString(context, "0");
			if(value != null && value.length() > 0)
				return Long.parseLong(value);
		} catch (Exception e) {
			
		}
		return 0;
	}	//	getContextAsInt
	
	/**
	 *	Is Sales Order Trx
	 *  @param windowNo window no
	 *  @return true if SO (default)
	 */
	public static boolean isSOTrx (int windowNo) {
		return getContextAsBoolean(windowNo, "IsSOTrx");
	}	//	isSOTrx

	/**
	 *	Is Sales Order Trx
	 *  @return true if SO (default)
	 */
	public static boolean isSOTrx () {
		return getContextAsBoolean( "IsSOTrx");
	}	//	isSOTrx

	/**
	 * 	Get Login AD_Client_ID
	 *	@return login AD_Client_ID
	 */
	public static int getAD_Client_ID () {
		return getContextAsInt("#AD_Client_ID");
	}	//	getAD_Client_ID
	
	/**
	 * 	Get Login AD_Org_ID
	 *	@return login AD_Org_ID
	 */
	public static int getAD_Org_ID () {
		return getContextAsInt("#AD_Org_ID");
	}	//	getAD_Client_ID
	
	/**
	 * 	Get Login AD_User_ID
	 *	@return login AD_User_ID
	 */
	public static int getAD_User_ID() {
		return getContextAsInt("#AD_User_ID");
	}	//	getAD_User_ID
	
	/**
	 * 	Get Login AD_Role_ID
	 *	@return login AD_Role_ID
	 */
	public static int getAD_Role_ID() {
		return getContextAsInt("#AD_Role_ID");
	}	//	getAD_Role_ID

	/**
	 * Get Login M_Warehouse
	 * @return
	 * @return int
	 */
	public static int getM_Warehouse_ID() {
		return getContextAsInt("#M_Warehouse_ID");
	}	//	getAD_Role_ID
	
	/**
	 * Set User ID
	 * @param userId
	 * @return void
	 */
	public static void setAD_User_ID(int userId) {
		setContext("#AD_User_ID", userId);
	}
	
	/**
	 * Set Client
	 * @param clientId
	 * @return void
	 */
	public static void setAD_Client_ID(int clientId) {
		setContext("#AD_Client_ID", clientId);
	}
	
	/**
	 * Set Org
	 * @param orgId
	 * @return void
	 */
	public static void setAD_Org_ID(int orgId) {
		setContext("#AD_Org_ID", orgId);
	}
	
	/**
	 * Set Role
	 * @param roleId
	 * @return void
	 */
	public static void setAD_Role_ID(int roleId) {
		setContext("#AD_Role_ID", roleId);
	}
	
	/**
	 * Set Warehouse
	 * @param warehouseId
	 * @return void
	 */
	public static void setM_Warehouse_ID(int warehouseId) {
		setContext("#M_Warehouse_ID", warehouseId);
	}

	/**
	 * Get database version
	 * @return
	 * @return int
	 */
	public static int getDBVersion() {
		return Env.getContextAsInt(DB_VERSION);
	}	//	getAD_Role_ID

	
	/**
	 * Get Tab Record Identifier
	 * @param activityNo
	 * @param tabNo
	 * @return
	 * @return int
	 */
	public static int[] getTabRecord_ID(int activityNo, int tabNo) {
		return getContextAsIntArray(activityNo, tabNo, ID_TAB);
	}

	/**
	 * Get Tab KeyColumns
	 * @param activityNo
	 * @param tabNo
	 * @return
	 * @return String[]
	 */
	public static String[] getTabKeyColumns(int activityNo, int tabNo) {
		return getContextAsArray(activityNo, tabNo, ID_TAB_KEYS);
	}

	/**
	 * Set Tab Record Identifier
	 * @param activityNo
	 * @param tabNo
	 * @param recordId
	 * @return void
	 */
	public static void setTabRecord_ID(int activityNo, int tabNo, int[] recordId) {
		setContextArray(activityNo, tabNo, ID_TAB, recordId);
	}

	/**
	 * Set Tab Key Columns
	 * @param activityNo
	 * @param tabNo
	 * @param keyColumns
	 * @return void
	 */
	public static void setTabKeyColumns(int activityNo, int tabNo, String[] keyColumns) {
		setContextArray(activityNo, tabNo, ID_TAB_KEYS, keyColumns);
	}
	
	/**
	 * Set Current Tab
	 * @param activityNo
	 * @return void
	 */
	public static void setCurrentTab(int activityNo, int tabNo) {
		setContext(activityNo, CURRENT_TAB, tabNo);
	}
	
	/**
	 * Get Current Tab
	 * @param activityNo
	 * @return
	 * @return int
	 */
	public static int getCurrentTab(int activityNo) {
		return getContextAsInt(activityNo, CURRENT_TAB);
	}
	
	/**
	 * Is Current Tab No
	 * @param activityNo
	 * @param tabNo
	 * @return
	 * @return boolean
	 */
	public static boolean isCurrentTab(int activityNo, int tabNo) {
		return tabNo == getContextAsInt(activityNo, CURRENT_TAB);
	}
	
	/**
	 * Set Database version
	 * @param value
	 * @return void
	 */
	public static void setDBVersion(int value) {
		Env.setContext(DB_VERSION, value);
	}
	
	/**
	 * Parse Context
	 * @param whereClause
	 * @param ignoreUnparsable
	 * @return
	 * @return String
	 */
    @NonNull
	public static String parseContext(String whereClause, boolean ignoreUnparsable) {
		return parseContext(0, 0, whereClause, ignoreUnparsable, null);
	}

	/**
	 *	Parse Context replaces global or Window context @tag@ with actual value.
	 *
	 *  @tag@ are ignored otherwise "" is returned
	 *	@param activityNo	Number of Window
	 *	@param whereClause Message to be parsed
	 * 	@param ignoreUnparsable if true, unsuccessful @return parsed String or "" if not successful and ignoreUnparsable
	 *	@return parsed context 
	 */
    @NonNull
	public static String parseContext(int activityNo, int tabNo, String whereClause, boolean ignoreUnparsable, String defaultUnparseable) {
		if (Util.isEmpty(whereClause))
			return "";

		String token;
		String inStr = new String(whereClause);
		StringBuffer outStr = new StringBuffer();

		int i = inStr.indexOf('@');
		while (i != -1) {
			outStr.append(inStr.substring(0, i));			// up to @
			inStr = inStr.substring(i+1, inStr.length());	// from first @

			int j = inStr.indexOf('@');						// next @
			if (j < 0) {
				LogM.log(getContext(), "Env", Level.INFO, "No second tag: " + inStr);
				return "";						//	no second tag
			}

			token = inStr.substring(0, j);
			//	
			String ctxInfo = getContext(activityNo, tabNo, token);	// get context
			if (ctxInfo != null && ctxInfo.length() == 0)
				ctxInfo = getContext(activityNo, token);	//	get from windows
			if (ctxInfo != null && ctxInfo.length() == 0 && (token.startsWith("#") || token.startsWith("$")) )
				ctxInfo = getContext(token);	// get global context
			if (ctxInfo != null && ctxInfo.length() == 0) {
				LogM.log(getContext(), "Env", Level.INFO, "No Context for: " + token);
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
		LogM.log(getContext(), "Env", Level.FINE, "parseContext(" + inStr + ")");
		//	
		return outStr.toString();
	}	//	parseContext

	/**
	 * Get Default Language
	 * @return
	 * @return String
	 */
	public static String getSOLanguage() {
		return Locale.getDefault().toString();
	}
	
	/**
	 * Set Current Language
	 * @param language
	 * @return void
	 */
	public static void setLanguage(String language) {
		setContext(LANGUAGE, language);
	}
	
	/**
	 * Get System AD_Language
	 * @return
	 * @return String
	 */
	public static String getLanguage() {
		return getContext(LANGUAGE);
	}

	/**
	 * Get is base language
	 * @return
	 * @return boolean
	 */
	public static boolean isBaseLanguage() {
		String language = getLanguage();
		if(language != null)
			return getLanguage().equals(BASE_LANGUAGE);
		return true;
	}

	/**
	 * Change Language
	 * @param language
	 * @param metrics
	 * @return void
	 */
	public static void changeLanguage(String language, DisplayMetrics metrics) {
		Locale locale = Language.getLocale(language);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.setLocale(locale);
        getContext().getApplicationContext().getResources().updateConfiguration(config, metrics);
	}

	/**
	 * Get Locale from language
	 * @return
	 * @return Locale
	 */
	public static Locale getLocate() {
		String language = getLanguage();
		if(language == null)
			language = BASE_LANGUAGE;
		return Language.getLocale(language);
	}

	/**
	 * Change Language
	 * @param language
	 * @return void
	 */
	public static void changeLanguage(String language) {
		changeLanguage(language, null);
	}
	
	/**
	 *  Set Date Pattern.
	 *  The date format is not checked for correctness
	 *  @param javaDatePattern for details see java.text.SimpleDateFormat,
	 *  format must be able to be converted to database date format by
	 *  using the upper case function.
	 *  It also must have leading zero for day and month.
	 */
	public static void setDateFormat(String javaDatePattern) {
		if (javaDatePattern == null)
			return;
		SimpleDateFormat m_dateFormat = (SimpleDateFormat)DateFormat.getDateInstance
				(DateFormat.SHORT, getLocate());
		try {
			m_dateFormat.applyPattern(javaDatePattern);
		} catch (Exception e) {
			LogM.log(getContext(), "Env", Level.SEVERE, "Env.setDateFormat(Context, String)" + javaDatePattern, e);
			m_dateFormat = null;
		}
	}   //  setDateFormat

	/**
	 * Get Date Format from Pattern
	 * @param javaFormatPattern
	 * @return
	 */
	public static SimpleDateFormat getDateFormat(String javaFormatPattern) {
		return new SimpleDateFormat(javaFormatPattern, getLocate());
	}

	/**
	 *  Get (Short) Date Format.
	 *  The date format must parseable by org.compiere.grid.ed.MDocDate
	 *  i.e. leading zero for date and month
	 *  @return date format MM/dd/yyyy - dd.MM.yyyy
	 */
	public static SimpleDateFormat getDateFormat() {
		SimpleDateFormat dateFormat = (SimpleDateFormat)DateFormat.getDateInstance(DateFormat.SHORT, getLocate());
		String sFormat = dateFormat.toPattern();
		//	some short formats have only one M and/or d (e.g. ths US)
		if (sFormat.indexOf("MM") == -1 || sFormat.indexOf("dd") == -1) {
			sFormat = sFormat.replaceFirst("d+", "dd");
			sFormat = sFormat.replaceFirst("M+", "MM");
		//	log.finer(sFormat + " => " + nFormat);
			dateFormat.applyPattern(sFormat);
		}
		//	Unknown short format => use JDBC
		if (dateFormat.toPattern().length() != 8)
			dateFormat.applyPattern("yyyy-MM-dd");

		//	4 digit year
		if (dateFormat.toPattern().indexOf("yyyy") == -1) {
			sFormat = dateFormat.toPattern();
			String nFormat = "";
			for (int i = 0; i < sFormat.length(); i++) {
				if (sFormat.charAt(i) == 'y')
					nFormat += "yy";
				else
					nFormat += sFormat.charAt(i);
			}
			dateFormat.applyPattern(nFormat);
		}
		dateFormat.setLenient(true);
		return dateFormat;
	}   //  getDateFormat

	/**
	 * 	Get Date Time Format.
	 * 	Used for Display only
	 *  @return Date Time format MMM d, yyyy h:mm:ss a z -or- dd.MM.yyyy HH:mm:ss z
	 *  -or- j nnn aaaa, H' ?????? 'm' ????'
	 */
	public static SimpleDateFormat getDateTimeFormat() {
		SimpleDateFormat retValue = (SimpleDateFormat)DateFormat.getDateTimeInstance
			(DateFormat.MEDIUM, DateFormat.LONG, getLocate());
	//	log.finer("Pattern=" + retValue.toLocalizedPattern() + ", Loc=" + retValue.toLocalizedPattern());
		return retValue;
	}	//	getDateTimeFormat
	
	/**
	 * 	Get Time Format.
	 * 	Used for Display only
	 *  @return Time format h:mm:ss z or HH:mm:ss z
	 */
	public static SimpleDateFormat getTimeFormat() {
		return (SimpleDateFormat) DateFormat.getTimeInstance(DateFormat.LONG, getLocate());
	}	//	getTimeFormat

	/**
	 * Get Current Activity No
	 * @return
	 * @return int
	 */
	public static int getActivityNo() {
		//	Get Current Activity No
		int activityNo = getContextAsInt(ACTIVITY_NO);
		//Msg.toastMsg(ctx, "ActivityNo=" + aNo);
		//	Set New
		setContext(ACTIVITY_NO, ++activityNo);
		//	Return Activity
		return activityNo;
	}
	
	/**
	 * Reset Activity No
	 * @return void
	 */
	public static void resetActivityNo() {
		setContext(ACTIVITY_NO, 0);
	}

	/**
	 * Get Resource Identifier from Attribute
	 * @param ctx
	 * @param att
	 * @return
	 * @return int
	 */
	public static int getResourceId(Context ctx, int att) {
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
	public static SharedPreferences sharePreferences;
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
	/******************************************************************************
	 * App Context
	 */
	public static final String 		APP_DIRECTORY 		= "ERP";
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
