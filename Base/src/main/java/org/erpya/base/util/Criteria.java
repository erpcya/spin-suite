/*************************************************************************************
 * Product: Spin-Suite (Mobile Suite)                                                *
 * Copyright (C) 2012-2018 E.R.P. Consultores y Asociados, C.A.                      *
 * Contributor(s): Yamel Senih ysenih@erpya.com                                      *
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

import java.util.ArrayList;
import java.util.List;

/**
 * Used for handle Criteria Query
 * @author yamel, ysenih@erpya.com , http://www.erpya.com
 */
public class Criteria implements Parcelable {

    /**
     * Instance of Criteria with table name or alias
     */
    public Criteria() {

    }

    /** Map for old key and values  */
    private List<Condition> conditionList = new ArrayList<Condition>();
    /** Name for parceable parameter    */
    public static final String PARCEABLE_NAME = "DefaultCondition";
    /**
     * Add criteria value
     * @param attributeKey
     * @param comparator
     * @param value
     * @return
     */
    public Criteria addCriteria(String attributeKey, String comparator, Object value) {
        conditionList.add(new Condition(attributeKey, comparator, value));
        return this;
    }

    /**
     * Copy from criteria
     * @param sourceCriteria
     * @return
     */
    public Criteria copyFromCriteria(Criteria sourceCriteria) {
        sourceCriteria.getCriteriaList().stream().forEach(sourceCondition -> {
            addCriteria(sourceCondition);
        });
        return this;
    }

    /**
     * Add condition from source
     * @param sourceCondition
     * @return
     */
    public Criteria addCriteria(Condition sourceCondition) {
        conditionList.add(sourceCondition);
        return this;
    }

    /**
     * Add criteria for IN
     * @param attributeKey
     * @param values
     * @return
     */
    public Criteria addCriteriaIN(String attributeKey, Object... values) {
        conditionList.add(new Condition(attributeKey, Condition.IN, values));
        return this;
    }

    /**
     * Add criteria for NOT IN
     * @param attributeKey
     * @param values
     * @return
     */
    public Criteria addCriteriaNOTIN(String attributeKey, Object... values) {
        conditionList.add(new Condition(attributeKey, Condition.NOT_IN, values));
        return this;
    }

    /**
     * Get Criteria List
     * @return
     */
    public List<Condition> getCriteriaList() {
        return conditionList;
    }

    @Override
    public int describeContents() {
        return hashCode();
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Criteria createFromParcel(Parcel parcel) {
            return new Criteria(parcel);
        }
        public Criteria[] newArray(int size) {
            return new Criteria[size];
        }
    };

    public Criteria(Parcel parcel){
        this();
        readToParcel(parcel);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(conditionList);
    }

    public void readToParcel(Parcel parcel){
        conditionList = new ArrayList<Condition>();
        parcel.readList(conditionList, getClass().getClassLoader());
    }
}
