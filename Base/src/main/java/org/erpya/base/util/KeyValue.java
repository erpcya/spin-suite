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

/**
 * Key and value for display
 */
public class KeyValue {

    /**
     * Defalt constructor with ID, UUID and Display Value
     * @param id
     * @param uuid
     * @param value
     */
    public KeyValue(int id, String uuid, Object value) {
        this.id = id;
        this.uuid = uuid;
        this.value = value;
    }

    /**
     * Default constructor fot a id and display value
     * @param id
     * @param displayValue
     */
    public KeyValue(int id, String displayValue) {
        this(id, null, displayValue);
    }

    /**
     * Constructor from UUID and Display Value
     * @param uuid
     * @param displayValue
     */
    public KeyValue(String uuid, String displayValue) {
        this(0, uuid, displayValue);
    }

    private final int id;
    private final String uuid;
    private final Object value;

    public int getId() {
        return id;
    }

    public String getUuid() {
        return uuid;
    }

    public String getDisplaValue() {
        String displaValue = "";
        if(value != null) {
            displaValue = String.valueOf(value);
        }
        return displaValue;
    }

    public Object getValue() {
        return value;
    }

    @Override
    public String toString() {
        return getDisplaValue();
    }
}
