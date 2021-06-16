/*************************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution                              *
 * This program is free software; you can redistribute it and/or modify it    		 *
 * under the terms version 2 or later of the GNU General Public License as published *
 * by the Free Software Foundation. This program is distributed in the hope   		 *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied 		 *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           		 *
 * See the GNU General Public License for more details.                       		 *
 * You should have received a copy of the GNU General Public License along    		 *
 * with this program; if not, write to the Free Software Foundation, Inc.,    		 *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     		 *
 * For the text or an alternative of this public license, you may reach us    		 *
 * Copyright (C) 2012-2018 E.R.P. Consultores y Asociados, S.A. All Rights Reserved. *
 * Contributor(s): Yamel Senih www.erpya.com				  		                 *
 *************************************************************************************/
package org.erpya.pos.util;

import org.erpya.base.util.Util;
import org.spin.grpc.util.Decimal;
import org.spin.grpc.util.KeyValue;
import org.spin.grpc.util.Value;
import org.spin.grpc.util.Value.ValueType;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Class for handle Values from and to client
 * @author Yamel Senih, ysenih@erpya.com , http://www.erpya.com
 */
public class ServerValueUtil {
	
	/**	Date format	*/
	private static final String TIME_FORMAT = "yyyy-MM-dd hh:mm:ss";
	private static final String DATE_FORMAT = "yyyy-MM-dd";
	
	/**
	 * Get Value 
	 * @param value
	 * @return
	 */
	public static Value.Builder getValueFromObject(Object value) {
		Value.Builder builderValue = Value.newBuilder();
		if(value == null) {
			return builderValue;
		}
		//	Validate value
		if(value instanceof BigDecimal) {
			return getValueFromDecimal((BigDecimal) value);
		} else if (value instanceof Integer) {
			return getValueFromInteger((Integer)value);
		} else if (value instanceof String) {
			return getValueFromString((String) value);
		} else if (value instanceof Boolean) {
			return getValueFromBoolean((Boolean) value);
		} else if(value instanceof Timestamp) {
			return getValueFromDate((Timestamp) value);
		}
		//	
		return builderValue;
	}
	
	/**
	 * Get value from Integer
	 * @param value
	 * @return
	 */
	public static Value.Builder getValueFromInteger(Integer value) {
		Value.Builder convertedValue = Value.newBuilder().setValueType(ValueType.INTEGER);
		if(value != null) {
			convertedValue.setIntValue((Integer)value);
		}
		//	default
		return convertedValue;
	}
	
	/**
	 * Get value from a string
	 * @param value
	 * @return
	 */
	public static Value.Builder getValueFromString(String value) {
		return Value.newBuilder().setStringValue(validateNull(value)).setValueType(ValueType.STRING);
	}
	
	/**
	 * Get value from a boolean value
	 * @param value
	 * @return
	 */
	public static Value.Builder getValueFromBoolean(boolean value) {
		return Value.newBuilder().setBooleanValue(value).setValueType(ValueType.BOOLEAN);
	}
	
	/**
	 * Get value from a date
	 * @param value
	 * @return
	 */
	public static Value.Builder getValueFromDate(Timestamp value) {
		return Value.newBuilder().setLongValue(value.getTime()).setValueType(ValueType.DATE);
	}
	
	/**
	 * Get value from big decimal
	 * @param value
	 * @return
	 */
	public static Value.Builder getValueFromDecimal(BigDecimal value) {
		return Value.newBuilder().setDecimalValue(Decimal.newBuilder().setDecimalValue(value.toPlainString()).setScale(value.scale())).setValueType(ValueType.DECIMAL);
	}
	
	/**
	 * Get decimal from big decimal
	 * @param value
	 * @return
	 */
	public static Decimal.Builder getDecimalFromBigDecimal(BigDecimal value) {
		if(value == null) {
			return Decimal.newBuilder();
		}
		return Decimal.newBuilder().setDecimalValue(value.toPlainString()).setScale(value.scale());
	}
	
	/**
	 * Get Decimal from Value
	 * @param value
	 * @return
	 */
	public static BigDecimal getDecimalFromValue(Value value) {
		if(Util.isEmpty(value.getDecimalValue().getDecimalValue())) {
			return null;
		}
		return new BigDecimal(value.getDecimalValue().getDecimalValue());
	}
	
	/**
	 * Get BigDecimal object from decimal
	 * @param decimalValue
	 * @return
	 */
	public static BigDecimal getBigDecimalFromDecimal(Decimal decimalValue) {
		if(decimalValue == null 
				|| Util.isEmpty(decimalValue.getDecimalValue())) {
			return null;
		}
		return new BigDecimal(decimalValue.getDecimalValue());
	}
	
	/**
	 * Get Date from a value
	 * @param value
	 * @return
	 */
	public static Timestamp getDateFromValue(Value value) {
		if(value.getLongValue() > 0) {
			return new Timestamp(value.getLongValue());
		}
		return null;
	}
	
	/**
	 * Get String from a value
	 * @param value
	 * @param uppercase
	 * @return
	 */
	public static String getStringFromValue(Value value, boolean uppercase) {
		String stringValue = value.getStringValue();
		if(Util.isEmpty(stringValue)) {
			stringValue = null;
		}
		//	To Upper case
		if(uppercase) {
			stringValue = stringValue.toUpperCase();
		}
		return stringValue;
	}
	
	/**
	 * Get String from a value
	 * @param value
	 * @return
	 */
	public static String getStringFromValue(Value value) {
		return getStringFromValue(value, false);
	}
	
	/**
	 * Get integer from a value
	 * @param value
	 * @return
	 */
	public static int getIntegerFromValue(Value value) {
		return value.getIntValue();
	}
	
	/**
	 * Get Boolean from a value
	 * @param value
	 * @return
	 */
	public static boolean getBooleanFromValue(Value value) {
		return value.getBooleanValue();
	}
	
	/**
	 * Convert Selection values from gRPC to ADempiere values
	 * @param values
	 * @return
	 */
	public static Map<String, Object> convertValuesToObjects(List<KeyValue> values) {
		Map<String, Object> convertedValues = new HashMap<>();
		for(KeyValue value : values) {
			convertedValues.put(value.getKey(), getObjectFromValue(value.getValue()));
		}
		//	
		return convertedValues;
	}
	
	/**
	 * Default get value from type
	 * @param valueToConvert
	 * @return
	 */
	public static Object getObjectFromValue(Value valueToConvert) {
		return getObjectFromValue(valueToConvert, false);
	}
	
	/**
	 * Get value from parameter type
	 * @param value
	 * @return
	 */
	public static Object getObjectFromValue(Value value, boolean uppercase) {
		if (value.getValueType().equals(ValueType.BOOLEAN)) {
			return value.getBooleanValue();
		} else if (value.getValueType().equals(ValueType.DECIMAL)) {
			return getDecimalFromValue(value);
		} else if (value.getValueType().equals(ValueType.INTEGER)) {
			return value.getIntValue();
		} else if (value.getValueType().equals(ValueType.STRING)) {
			return getStringFromValue(value, uppercase);
		} else if (value.getValueType().equals(ValueType.DATE)) {
			return getDateFromValue(value);
		}
		return null;
	}
	
	/**
	 * Convert null on ""
	 * @param value
	 * @return
	 */
	public static String validateNull(String value) {
		if(value == null) {
			value = "";
		}
		//	
		return value;
	}
	
	/**
	 * Validate if is numeric
	 * @param value
	 * @return
	 */
	public static boolean isNumeric(String value) {
		if(Util.isEmpty(value)) {
			return false;
		}
		//	
		return value.matches("[+-]?\\d*(\\.\\d+)?");
	}
	
	/**
	 * Get Int value from String
	 * @param value
	 * @return
	 */
	public static int getIntegerFromString(String value) {
		Integer integerValue = null;
		try {
			integerValue = Integer.parseInt(value);
		} catch (Exception e) {
			
		}
		if(integerValue == null) {
			return 0;
		}
		return integerValue;
	}
	
	
	/**
	 * Validate if is boolean
	 * @param value
	 * @return
	 */
	public static boolean isBoolean(String value) {
		if(Util.isEmpty(value)) {
			return false;
		}
		//	
		return value.equals("Y") 
				|| value.equals("N") 
				|| value.equals("true") 
				|| value.equals("false");
	}
	
	/**
	 * Is BigDecimal
	 * @param value
	 * @return
	 */
	public static boolean isBigDecimal(String value) {
		return getBigDecimalFromString(value) != null;
	}
	
	/**
	 * 
	 * @param value
	 * @return
	 */
	public static BigDecimal getBigDecimalFromString(String value) {
		BigDecimal numberValue = null;
		if(Util.isEmpty(value)) {
			return null;
		}
		//	
		try {
			numberValue = new BigDecimal(value);
		} catch (Exception e) {
			
		}
		return numberValue;
	}
	
	/**
	 * Set Parameter for Statement from value
	 * @param pstmt
	 * @param value
	 * @param index
	 * @throws SQLException
	 */
	public static void setParameterFromValue(PreparedStatement pstmt, Value value, int index) throws SQLException {
		if(value.getValueType().equals(ValueType.INTEGER)) {
			pstmt.setInt(index, ServerValueUtil.getIntegerFromValue(value));
		} else if(value.getValueType().equals(ValueType.DECIMAL)) {
			pstmt.setBigDecimal(index, ServerValueUtil.getDecimalFromValue(value));
		} else if(value.getValueType().equals(ValueType.STRING)) {
			pstmt.setString(index, ServerValueUtil.getStringFromValue(value));
		} else if(value.getValueType().equals(ValueType.DATE)) {
			pstmt.setTimestamp(index, ServerValueUtil.getDateFromValue(value));
		}
	}
	
	/**
	 * Convert Timestamp to String
	 * @param date
	 * @return
	 */
	public static String convertDateToString(Timestamp date) {
		if(date == null) {
			return null;
		}
		return new SimpleDateFormat(TIME_FORMAT).format(date);
	}

}
