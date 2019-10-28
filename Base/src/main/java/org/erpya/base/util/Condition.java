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

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Arrays;

/**
 * Condition class is used as component for Criteria class
 * @author yamel, ysenih@erpya.com , http://www.erpya.com
 */
public class Condition implements Parcelable {

    /**
     * For from and to with between
     * @param keyAttribute
     * @param comparator
     * @param value
     * @param valueTo
     */
    public Condition(String keyAttribute, String comparator, Object value, Object valueTo) {
        this.keyAttribute = keyAttribute;
        this.comparator = comparator;
        this.value = value;
        this.valueTo = valueTo;
    }

    /**
     * Default constructor with mandatory values
     * @param keyAttribute
     * @param comparator
     * @param value
     */
    public Condition(String keyAttribute, String comparator, Object value) {
        this(keyAttribute, comparator, value, null);
    }

    /**
     * For IN(,) clause
     * @param keyAttribute
     * @param comparator
     * @param values
     */
    public Condition(String keyAttribute, String comparator, Object... values) {
        this(keyAttribute, comparator, null, null);
        this.values = values;
    }

    /**
     * Default constructor
     */
    public Condition() {
        this(null, null, (Object[])null);
    }

    /** Key Attribute   */
    private String keyAttribute;
    /** Comparator  */
    private String comparator;
    /** Value for condition */
    private Object value;
    /** Value for condition */
    private Object valueTo;
    /** Array for values    */
    private Object[] values;

    /** Equal 			*/
    public static final String	EQUAL = "=";
    /** Not Equal		*/
    public static final String	NOT_EQUAL = "!=";
    /** Like			*/
    public static final String	LIKE = " LIKE ";
    /** Not Like		*/
    public static final String	NOT_LIKE = " NOT LIKE ";
    /** Greater			*/
    public static final String	GREATER = ">";
    /** Greater Equal	*/
    public static final String	GREATER_EQUAL = ">=";
    /** Less			*/
    public static final String	LESS = "<";
    /** Less Equal		*/
    public static final String	LESS_EQUAL = "<=";
    /** Between			*/
    public static final String	BETWEEN = " BETWEEN ";
    /** For 	*/
    public static final String 	NOT_NULL = " IS NOT NULL ";
    /** For 	*/
    public static final String 	NULL = " IS NULL ";
    /** For IN	*/
    public static final String 	IN = " IN ";
    /** For NOT IN	*/
    public static final String 	NOT_IN = " NOT IN ";

    public String getKeyAttribute() {
        return keyAttribute;
    }

    public String getComparator() {
        return comparator;
    }

    public Object getValue() {
        return value;
    }

    public Object getValueTo() {
        return valueTo;
    }

    public Object[] getValues() {
        return values;
    }

    @Override
    public String toString() {
        return "Condition{" +
                "keyAttribute='" + keyAttribute + '\'' +
                ", comparator='" + comparator + '\'' +
                ", value=" + value +
                ", valueTo=" + valueTo +
                ", values=" + Arrays.toString(values) +
                '}';
    }

    @Override
    public int describeContents() {
        return hashCode();
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Condition createFromParcel(Parcel parcel) {
            return new Condition(parcel);
        }
        public Condition[] newArray(int size) {
            return new Condition[size];
        }
    };

    public Condition(Parcel parcel){
        this();
        readToParcel(parcel);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(keyAttribute);
        dest.writeString(comparator);
        dest.writeValue(value);
        dest.writeValue(valueTo);
        dest.writeArray(values);
    }

    public void readToParcel(Parcel parcel){
        keyAttribute = parcel.readString();
        comparator = parcel.readString();
        value = parcel.readValue(getClass().getClassLoader());
        valueTo = parcel.readValue(getClass().getClassLoader());
        values = parcel.readArray(getClass().getClassLoader());
    }
}
