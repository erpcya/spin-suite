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

import android.util.Log;

import org.erpya.base.db.DBManager;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;

/**
 * Value Util allows cast class
 * @author yamel, ysenih@erpya.com , http://www.erpya.com
 * <li> FR [ 2 ]
 * @see https://github.com/adempiere/spin-suite/issues/2
 */
public class ValueUtil {

    /**************************************************************
     * Helper Methods
     *************************************************************/

    /**
     *  Get Value as int
     *  @param Value for cast
     *  @return int value or 0
     */
    public static int getValueAsInt(Object value) {
        //
        if (value instanceof Integer)
            return ((Integer)value).intValue();
        try {
            return Integer.parseInt(value.toString());
        } catch (NumberFormatException ex) {
            Log.w("getValueAsInt", value + " - " + ex.getMessage());
            return 0;
        }
    }   //  getValueAsInt

    /**
     * 	Get Column Value
     *	@param key name
     *	@return value or ""
     */
    public static String getValueAsString (Object value) {
        if (value == null)
            return "";
        return value.toString();
    }	//	get_ValueAsString

    /**
     * Get value as Boolean
     * @param Value for cast
     * @return boolean value
     */
    public static boolean getValueAsBoolean(Object value) {
        if (value != null) {
            if (value instanceof Boolean)
                return ((Boolean)value).booleanValue();
            return "Y".equals(value);
        }
        return false;
    }

    /**
     * Get value as big decimal
     * @param value
     * @return
     */
    public static BigDecimal getValueAsBigDecimal(Object value) {
        BigDecimal bigDecimalValue = Env.ZERO;
        if (value != null) {
            if (value instanceof BigDecimal) {
                bigDecimalValue = (BigDecimal) value;
                if (bigDecimalValue == null) {
                    bigDecimalValue = Env.ZERO;
                }
                return bigDecimalValue;
            } else if(value instanceof Double) {
                Double doubleValue = (Double) value;
                if (doubleValue == null) {
                    doubleValue = new Double(0);
                }
                //
                bigDecimalValue = new BigDecimal(doubleValue);
            } else if(value instanceof Float) {
                Float floatValue = (Float) value;
                if (floatValue == null) {
                    floatValue = new Float(0);
                }
                //
                bigDecimalValue = new BigDecimal(floatValue);
            } else if(value instanceof Integer) {
                Integer integerValue = (Integer) value;
                if (integerValue == null) {
                    integerValue = new Integer(0);
                }
                //
                bigDecimalValue = new BigDecimal(integerValue);
            }
        }
        //  Default
        return bigDecimalValue;
    }

    /**
     * Get value as Timestamp
     * @param Value for cast
     * @return boolean value
     */
    public static Date getValueAsDate(Object value) {
        if (value != null) {
            if (value instanceof Date) {
                return (Date) value;
            } else if(value instanceof String) {
                try {
                    return DBManager.getInstance(Env.getContext()).convetToDate((String) value);
                } catch (Exception e) {
                    LogM.log(Env.getContext(), ValueUtil.class.getName(), Level.FINE, e.getMessage());
                }
            }
        }
        return null;
    }

    /**
     * Get Database value as list
     * @param Value for cast
     * @return
     */
    public static List<?> getValueAsList(Object value) {
        if (value != null) {
            if (value instanceof List)
                return (List<?>) value;
        }
        return null;
    }
}
