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
package org.erpya.base.dictionary;

import android.content.Context;

import org.erpya.base.model.IInfoField;
import org.erpya.base.model.PO;
import org.erpya.base.model.POInfoColumn;
import org.erpya.base.util.DisplayType;
import org.erpya.base.util.Util;
import org.erpya.base.util.ValueUtil;

import java.util.Map;

/**
 * Metadata info for Table and column
 * @author yamel, ysenih@erpya.com , http://www.erpya.com
 * <li> FR [ 2 ]
 * @see https://github.com/adempiere/spin-suite/issues/2
 */
public final class FieldInfo extends PO implements IInfoField {

    /**
     * Simple constructor
     * @param context
     */
    public FieldInfo(Context context) {
        super(context);
        columnInfo = new POInfoColumn(context);
    }

    @Override
    public String get_TableName() {
        return "Field";
    }

    private POInfoColumn columnInfo;

    /**
     * Default constructor for info field
     * @param context
     * @param attributes
     */
    public FieldInfo(Context context, Map<String, Object> attributes) {
        this(context);
        setMap(attributes);
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
        super.setValue(attributeKey, value);
        columnInfo.setAttribute(attributeKey, value);
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

    public int getColumnId() {
        return columnInfo.getColumnId();
    }

    public String getColumnName() {
        return columnInfo.getColumnName();
    }

    public String getColumnSQL() {
        return columnInfo.getColumnSQL();
    }

    public int getDisplayType() {
        return columnInfo.getDisplayType();
    }

    public Class<?> getColumnClass() {
        return null;
    }

    public boolean isMandatory() {
        return columnInfo.isMandatory();
    }

    public String getDefaultLogic() {
        return columnInfo.getDefaultLogic();
    }

    public boolean isUpdateable() {
        return columnInfo.isUpdateable();
    }

    public boolean isAlwaysUpdateable() {
        return columnInfo.isAlwaysUpdateable();
    }

    public String getColumnLabel() {
        return columnInfo.getColumnLabel();
    }

    public String getColumnDescription() {
        return columnInfo.getColumnDescription();
    }

    public String getColumnHelp() {
        return columnInfo.getColumnHelp();
    }

    public boolean isKey() {
        return columnInfo.isKey();
    }

    public boolean isParent() {
        return columnInfo.isParent();
    }

    public boolean isTranslated() {
        return columnInfo.isTranslated();
    }

    public boolean isEncrypted() {
        return columnInfo.isEncrypted();
    }

    public boolean isAllowLogging() {
        return columnInfo.isAllowLogging();
    }

    public String getValidationCode() {
        return columnInfo.getValidationCode();
    }

    public int getFieldLength() {
        return columnInfo.getFieldLength();
    }

    public String getValueMin() {
        return columnInfo.getValueMin();
    }

    public String getValueMax() {
        return columnInfo.getValueMax();
    }

    public boolean isAllowCopy() {
        return columnInfo.isAllowCopy();
    }

    public String getFormatPattern() {
        return columnInfo.getFormatPattern();
    }

    public String getContextInfoScript() {
        return columnInfo.getContextInfoScript();
    }

    public String getContextInfoFormatter() {
        return columnInfo.getContextInfoFormatter();
    }

    public String getDisplayColumnName() {
        return columnInfo.getDisplayColumnName();
    }
    public boolean isId (int displayType) {
        return DisplayType.isId(getDisplayType());
    }	//	isID

    /**
     * Verify if is a Boolean
     * @return boolean
     */
    public boolean isBoolean() {
        return DisplayType.isBoolean(getDisplayType());
    }

    /**
     * Is Integer
     * @return
     */
    public boolean isInteger() {
        return DisplayType.isInteger(getDisplayType());
    }	//	isInteger

    /**
     *	Returns true, if DisplayType is numeric (Amount, Number, Quantity, Integer).
     *  (stored as BigDecimal)
     *  @return true if numeric
     */
    public boolean isNumeric() {
        return DisplayType.isNumeric(getDisplayType());
    }	//	isNumeric

    /**
     * Is a BigDecimal
     * @return
     * @return boolean
     */
    public boolean isBigDecimal() {
        return DisplayType.isBigDecimal(getDisplayType());
    }	//	isBigDecimal

    /**
     *	Returns true, if DisplayType is text (String, Text, TextLong, Memo).
     *  @return true if text
     */
    public boolean isText() {
        return DisplayType.isText(getDisplayType());
    }	//	isText

    /**
     *	Returns true if DisplayType is a Date.
     *  (stored as Timestamp)
     *  @return true if date
     */
    public boolean isDate () {
        return DisplayType.isDate(getDisplayType());
    }	//	isDate

    /**
     * Verify if is only date supported
     * @return
     */
    public boolean isDateOnly() {
        return DisplayType.DATE == getDisplayType();
    }

    /**
     * Verify if is only time supported
     * @return
     */
    public boolean isTimeOnly() {
        return DisplayType.TIME == getDisplayType();
    }

    /**
     *	Returns true if DisplayType is a VLookup (List, Table, TableDir, Search).
     *  (stored as Integer)
     *  @return true if Lookup
     */
    public boolean isLookup() {
        return DisplayType.isLookup(getDisplayType());
    }	//	isLookup

    /**
     * 	Returns true if DisplayType is a Large Object
     *	@return true if Binary
     */
    public boolean isBinary () {
        return DisplayType.isBinary(getDisplayType());
    }	//	isBinary
}
