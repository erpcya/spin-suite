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

import android.content.Context;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.logging.Level;

/**
 * static class for distinct type of column and field definition
 * Copied from DisplayType ADempiere
 */
public class DisplayType {

    /** Display Type 10	String	*/
    public static final int STRING     	= 10;
    /** Display Type 11	Integer	*/
    public static final int INTEGER    	= 11;
    /** Display Type 12	Amount	*/
    public static final int AMOUNT     	= 12;
    /** Display Type 13	ID	*/
    public static final int ID         	= 13;
    /** Display Type 14	Text	*/
    public static final int TEXT       	= 14;
    /** Display Type 15	Date	*/
    public static final int DATE       	= 15;
    /** Display Type 16	DateTime	*/
    public static final int DATE_TIME   = 16;
    /** Display Type 17	List	*/
    public static final int LIST       	= 17;
    /** Display Type 18	Table	*/
    public static final int TABLE      	= 18;
    /** Display Type 19	TableDir	*/
    public static final int TABLE_DIR   = 19;
    /** Display Type 20	YN	*/
    public static final int YES_NO      = 20;
    /** Display Type 21	Location	*/
    public static final int LOCATION   	= 21;
    /** Display Type 22	Number	*/
    public static final int NUMBER     	= 22;
    /** Display Type 23	BLOB	*/
    public static final int BINARY     	= 23;
    /** Display Type 24	Time	*/
    public static final int TIME       	= 24;
    /** Display Type 25	Account	*/
    public static final int ACCOUNT    	= 25;
    /** Display Type 26	RowID	*/
    public static final int ROW_ID      = 26;
    /** Display Type 27	Color   */
    public static final int COLOR      	= 27;
    /** Display Type 28	Button	*/
    public static final int BUTTON	   	= 28;
    /** Display Type 29	Quantity	*/
    public static final int QUANTITY   	= 29;
    /** Display Type 30	Search	*/
    public static final int SEARCH     	= 30;
    /** Display Type 31	Locator	*/
    public static final int LOCATOR    	= 31;
    /** Display Type 32 Image	*/
    public static final int IMAGE      	= 32;
    /** Display Type 33 Assignment	*/
    public static final int ASSIGNMENT 	= 33;
    /** Display Type 34	Memo	*/
    public static final int MEMO       	= 34;
    /** Display Type 35	PAttribute	*/
    public static final int PATTRIBUTE 	= 35;
    /** Display Type 36	CLOB	*/
    public static final int TEXT_LONG   = 36;
    /** Display Type 37	CostPrice	*/
    public static final int COST_PRICE  = 37;
    /** Display Type 38	File Path	*/
    public static final int FILE_PATH  	= 38;
    /** Display Type 39 File Name	*/
    public static final int FILE_NAME	= 39;
    /** Display Type 40	URL	*/
    public static final int URL			= 40;
    /** Display Type 42	PrinterName	*/
    public static final int PRINTER_NAME= 42;


    //  See DBA_DisplayType.sql ----------------------------------------------

    /** Maximum number of digits    */
    private static final int    MAX_DIGITS 			= 28;        //  Oracle Standard Limitation 38 digits
    /** Digits of an Integer        */
    private static final int    INTEGER_DIGITS 		= 10;
    /** Maximum number of fractions */
    private static final int    MAX_FRACTION 		= 12;
    /** Default Amount Precision    */
    private static final int    AMOUNT_FRACTION 	= 2;


    /**
     *	Returns true if (numeric) ID (Table, Search, Account, ..).
     *  (stored as Integer)
     *  @param displayType Display Type
     *  @return true if ID
     */
    public static boolean isId (int displayType) {
        if (displayType == ID || displayType == TABLE || displayType == TABLE_DIR
                || displayType == SEARCH || displayType == LOCATION || displayType == LOCATOR
                || displayType == ACCOUNT || displayType == ASSIGNMENT || displayType == PATTRIBUTE
                || displayType == IMAGE || displayType == COLOR)
            return true;
        return false;
    }	//	isID

    /**
     * Verify if is a Boolean
     * @param displayType
     * @return
     * @return boolean
     */
    public static boolean isBoolean(int displayType) {
        if(displayType == YES_NO)
            return true;
        return false;
    }

    /**
     *	Returns true, if DisplayType is numeric (Amount, Number, Quantity, Integer).
     *  (stored as BigDecimal)
     *  @param displayType Display Type
     *  @return true if numeric
     */
    public static boolean isNumeric(int displayType) {
        if (displayType == AMOUNT || displayType == NUMBER || displayType == COST_PRICE
                || displayType == INTEGER || displayType == QUANTITY)
            return true;
        return false;
    }	//	isNumeric

    /**
     * Is Integer
     * @param displayType
     * @return
     */
    public static boolean isInteger(int displayType) {
        if (displayType == INTEGER)
            return true;
        return false;
    }	//	isInteger

    /**
     * Is a BigDecimal
     * @param displayType
     * @return
     * @return boolean
     */
    public static boolean isBigDecimal(int displayType) {
        if (displayType == AMOUNT || displayType == NUMBER || displayType == COST_PRICE
                || displayType == QUANTITY)
            return true;
        return false;
    }	//	isBigDecimal

    /**
     * 	Get Default Precision.
     * 	Used for databases who cannot handle dynamic number precision.
     *	@param displayType display type
     *	@return scale (decimal precision)
     */
    public static int getDefaultPrecision(int displayType) {
        if (displayType == AMOUNT)
            return 2;
        if (displayType == NUMBER)
            return 6;
        if (displayType == COST_PRICE
                || displayType == QUANTITY)
            return 4;
        return 0;
    }	//	getDefaultPrecision


    /**
     *	Returns true, if DisplayType is text (String, Text, TextLong, Memo).
     *  @param displayType Display Type
     *  @return true if text
     */
    public static boolean isText(int displayType) {
        if (displayType == STRING || displayType == TEXT
                || displayType == TEXT_LONG || displayType == MEMO
                || displayType == FILE_PATH || displayType == FILE_NAME
                || displayType == URL || displayType == PRINTER_NAME)
            return true;
        return false;
    }	//	isText

    /**
     *	Returns true if DisplayType is a Date.
     *  (stored as Timestamp)
     *  @param displayType Display Type
     *  @return true if date
     */
    public static boolean isDate (int displayType) {
        if (displayType == DATE || displayType == DATE_TIME || displayType == TIME)
            return true;
        return false;
    }	//	isDate

    /**
     *	Returns true if DisplayType is a VLookup (List, Table, TableDir, Search).
     *  (stored as Integer)
     *  @param displayType Display Type
     *  @return true if Lookup
     */
    public static boolean isLookup(int displayType) {
        if (displayType == LIST || displayType == TABLE
                || displayType == TABLE_DIR || displayType == SEARCH
                //	Added
                || displayType == LOCATION || displayType == LOCATOR)
            return true;
        return false;
    }	//	isLookup

    /**
     * 	Returns true if DisplayType is a Large Object
     *	@param displayType Display Type
     *	@return true if Binary
     */
    public static boolean isBinary (int displayType) {
        if (displayType == BINARY)
            return true;
        return false;
    }	//	isBinary

    /**************************************************************************
     *	Return Format for numeric DisplayType
     *	@param context
     *  @param displayType Display Type (default Number)
     *  @param pattern Java Number Format pattern e.g. "#,##0.00"
     *  @return number format
     */
    public static DecimalFormat getNumberFormat(Context context, int displayType, String pattern) {
        String language = org.erpya.base.util.Env.BASE_LANGUAGE;
        if(context != null)
            language = Env.getContext(Env.LANGUAGE);
        //
        Locale locale = org.erpya.base.util.Language.getLocale(language);
        DecimalFormat format = null;
        format = (DecimalFormat) NumberFormat.getNumberInstance(locale);
        //
        if (pattern != null && pattern.length() > 0) {
            try {
                format.applyPattern(pattern);
                return format;
            }
            catch (IllegalArgumentException e) {}
        } else if (displayType == INTEGER) {
            format.setParseIntegerOnly(true);
            format.setMaximumIntegerDigits(INTEGER_DIGITS);
            format.setMaximumFractionDigits(0);
        } else if (displayType == QUANTITY) {
            format.setMaximumIntegerDigits(MAX_DIGITS);
            format.setMaximumFractionDigits(MAX_FRACTION);
        } else if (displayType == AMOUNT) {
            format.setMaximumIntegerDigits(MAX_DIGITS);
            format.setMaximumFractionDigits(MAX_FRACTION);
            format.setMinimumFractionDigits(AMOUNT_FRACTION);
        } else if (displayType == COST_PRICE) {
            format.setMaximumIntegerDigits(MAX_DIGITS);
            format.setMaximumFractionDigits(MAX_FRACTION);
            format.setMinimumFractionDigits(AMOUNT_FRACTION);
        } else {
            format.setMaximumIntegerDigits(MAX_DIGITS);
            format.setMaximumFractionDigits(MAX_FRACTION);
            format.setMinimumFractionDigits(1);
        }
        return format;
    }	//	getDecimalFormat

    /**
     * Get value as Big Decimal from a String formatted like text view
     * @param value
     * @return
     */
    public static BigDecimal getValueAsBigDecimal(String value) {
        try {
            if(value != null
                    && value.length() > 0) {
                DecimalFormat decimalFormat = getNumberFormat(org.erpya.base.util.Env.getContext(), AMOUNT);
                //
                decimalFormat.setParseBigDecimal(true);
                BigDecimal parsed = (BigDecimal)decimalFormat.parse(value, new ParsePosition(0));
                return parsed;
            }
        } catch (Exception e) {
            if(org.erpya.base.util.Env.getContext() != null) {
                org.erpya.base.util.LogM.log(org.erpya.base.util.Env.getContext(), DisplayType.class, Level.SEVERE, "Not Parsed Value = " + value, e);
            }
        }
        //
        return org.erpya.base.util.Env.ZERO;
    }

    /**
     * Get BigDecimal format
     * @param value
     * @return
     * @return BigDecimal
     */
    public static BigDecimal getNumber(String value, int displayType) {
        try {
            if(value != null
                    && value.length() > 0) {
                DecimalFormat decimalFormat = getNumberFormat(org.erpya.base.util.Env.getContext(), displayType);
                //
                decimalFormat.setParseBigDecimal(true);
                BigDecimal parsed = (BigDecimal)decimalFormat.parse(value, new ParsePosition(0));
                return parsed;
            }
        } catch (Exception e) {
            if(org.erpya.base.util.Env.getContext() != null) {
                org.erpya.base.util.LogM.log(org.erpya.base.util.Env.getContext(), DisplayType.class, Level.SEVERE, "Not Parsed Value = " + value, e);
            }
        }
        //
        return org.erpya.base.util.Env.ZERO;
    }

    /**
     *	Return Format for numeric DisplayType
     *	@param context
     *  @param displayType Display Type
     *  @return number format
     */
    public static DecimalFormat getNumberFormat(Context context, int displayType) {
        return getNumberFormat (context, displayType, null);
    }   //  getNumberFormat


    /*************************************************************************
     *	Return Date Format
     *  @return date format
     */
    public static SimpleDateFormat getDateFormat(Context context) {
        return getDateFormat (context, DisplayType.DATE, null);
    }   //  getDateFormat

    /**
     *	Return Date Format
     *  @param language Language
     *  @return date format
     */
    public static SimpleDateFormat getDateFormat (Context context, String language) {
        return getDateFormat (context, DisplayType.DATE, language);
    }	//	getDateFormat

    /**
     *	Return format for date displayType
     *  @param displayType Display Type
     *  @return date format
     */
    public static SimpleDateFormat getDateFormat (Context context, int displayType) {
        return getDateFormat(context, displayType, null);
    }   //  getDateFormat

    /**
     *	Return format for date displayType
     *  @param displayType Display Type (default Date)
     *  @param pattern Java Simple Date Format pattern e.g. "dd/MM/yy"
     *  @return date format
     */
    public static SimpleDateFormat getDateFormat (Context context, int displayType, String pattern) {
        //
        if ( pattern != null && pattern.length() > 0) {
            SimpleDateFormat format = (SimpleDateFormat) DateFormat.getInstance();
            try {
                format.applyPattern(pattern);
                return format;
            } catch (IllegalArgumentException e) {
                org.erpya.base.util.LogM.log(context, "DisplayType", Level.FINE, "Invalid date pattern: " + pattern);
            }
        }

        if (displayType == DATE_TIME)
            return org.erpya.base.util.Env.getDateTimeFormat();
        else if (displayType == TIME)
            return org.erpya.base.util.Env.getTimeFormat();
        //
        return org.erpya.base.util.Env.getDateFormat();		//	default
    }	//	getDateFormat
}
