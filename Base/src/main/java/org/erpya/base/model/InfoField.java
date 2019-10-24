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
package org.erpya.base.model;

import android.content.Context;

import org.erpya.base.util.Util;
import org.erpya.base.util.ValueUtil;

import java.util.Map;

/**
 * Metadata info for Table and column
 * @author yamel, ysenih@erpya.com , http://www.erpya.com
 * <li> FR [ 2 ]
 * @see https://github.com/adempiere/spin-suite/issues/2
 */
public final class InfoField extends org.erpya.base.model.POInfoColumn implements IInfoField {

    /**
     * Simple constructor
     * @param context
     */
    public InfoField(Context context) {
        super(context);
    }

    /**
     * Default constructor for info field
     * @param context
     * @param attributes
     */
    public InfoField(Context context, Map<String, Object> attributes) {
        super(context, attributes);
        if(attributes != null
                && !attributes.isEmpty()) {
            isFieldOnly = ValueUtil.getValueAsBoolean(attributes.get(ATTRIBUTE_IsFieldOnly));
            displayLogic = ValueUtil.getValueAsString(attributes.get(ATTRIBUTE_DisplayLogic));
            displayLength = ValueUtil.getValueAsInt(attributes.get(ATTRIBUTE_DisplayLength));
            seqNo = ValueUtil.getValueAsInt(attributes.get(ATTRIBUTE_SeqNo));
            sortNo = ValueUtil.getValueAsInt(attributes.get(ATTRIBUTE_SortNo));
            infoFactoryClass = ValueUtil.getValueAsString(attributes.get(ATTRIBUTE_InfoFactoryClass));
            isReadOnly = ValueUtil.getValueAsBoolean(attributes.get(ATTRIBUTE_IsReadOnly));
        }
    }

    /**
     * Set Attribute
     * @param attributeKey
     * @param value
     */
    public void setAttribute(String attributeKey, Object value) {
        super.setAttribute(attributeKey, value);
        if (Util.isEmpty(attributeKey)) {
            return;
        }
        //  Add
        switch (attributeKey) {
            case ATTRIBUTE_IsFieldOnly:
                isFieldOnly = ValueUtil.getValueAsBoolean(value);
                break;
            case ATTRIBUTE_DisplayLogic:
                displayLogic = ValueUtil.getValueAsString(value);
                break;
            case ATTRIBUTE_DisplayLength:
                displayLength = ValueUtil.getValueAsInt(value);
                break;
            case ATTRIBUTE_SeqNo:
                seqNo = ValueUtil.getValueAsInt(value);
                break;
            case ATTRIBUTE_SortNo:
                sortNo = ValueUtil.getValueAsInt(value);
                break;
            case ATTRIBUTE_InfoFactoryClass:
                infoFactoryClass = ValueUtil.getValueAsString(value);
                break;
            case ATTRIBUTE_IsReadOnly:
                isReadOnly = ValueUtil.getValueAsBoolean(value);
                break;
        }
    }

    /** Field Only  */
    private boolean isFieldOnly = false;
    /** Display Logic   */
    private String displayLogic = null;
    /** Display Length  */
    private int displayLength = 0;
    /** Sequence    */
    private int seqNo = 0;
    /** Sorting No  */
    private int sortNo = 0;
    /** Factory Class   */
    private String infoFactoryClass = null;
    /** Is Read Only    */
    private boolean isReadOnly;

    @Override
    public boolean isFieldOnly() {
        return isFieldOnly;
    }

    @Override
    public String getDisplayLogic() {
        return displayLogic;
    }

    @Override
    public int getDisplayLength() {
        return displayLength;
    }

    @Override
    public int getSeqNo() {
        return seqNo;
    }

    @Override
    public int getSortNo() {
        return sortNo;
    }

    @Override
    public String getInfoFactoryClass() {
        return infoFactoryClass;
    }

    @Override
    public boolean isReadOnly() {
        return isReadOnly;
    }
}
