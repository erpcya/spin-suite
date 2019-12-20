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
package org.erpya.component.factory;

import android.content.Context;

import org.erpya.component.field.Field;
import org.erpya.component.field.FieldAutoComplete;
import org.erpya.component.field.FieldDateTime;
import org.erpya.component.field.FieldNumber;
import org.erpya.component.field.FieldText;
import org.erpya.base.exceptions.SpinSuiteException;
import org.erpya.base.model.InfoField;

/**
 * @author Yamel Senih, ysenih@erpya.com , http://www.erpya.com
 * Factory for InfoField class, static class for generate InfoField
 */
public class FieldFactory {

    /**
     * Standard Constructor for it,
     * @param context is mandatory
     */
    private FieldFactory(Context context) {
        fieldDefinition = new InfoField(context);
    }

    /**
     * Provate constructor from Info Field
     * @param fieldDefinition
     */
    private FieldFactory(InfoField fieldDefinition) {
        this.fieldDefinition = fieldDefinition;
    }

    /**
     * Create Field
     * @param context
     * @return
     */
    public static FieldFactory createField(Context context) {
        return new FieldFactory(context);
    }

    /**
     * Create from Info Field
     * @param fieldDefinition
     * @return
     */
    public static FieldFactory createField(InfoField fieldDefinition) {
        return new FieldFactory(fieldDefinition);
    }

    /** Internal Info Field */
    private InfoField fieldDefinition;

    /**
     * Get Field Definition
     * @return
     */
    public InfoField getFieldDefinition() {
        return fieldDefinition;
    }

    /**
     * Set Display Type
     * @param displayType
     * @return
     */
    public FieldFactory withDisplayType(int displayType) {
        fieldDefinition.setAttribute(InfoField.ATTRIBUTE_DisplayType, displayType);
        return this;
    }

    /**
     * Set Table Name for search for lookup type
     * @param tableName
     * @return
     */
    public FieldFactory withTableName(String tableName) {
        fieldDefinition.setAttribute(InfoField.ATTRIBUTE_TableName, tableName);
        return this;
    }

    /**
     * Set Display Column Name for lookup type
     * @param displayColumnName
     * @return
     */
    public FieldFactory withDisplayColumnName(String displayColumnName) {
        fieldDefinition.setAttribute(InfoField.ATTRIBUTE_DisplayColumnName, displayColumnName);
        return this;
    }

    /**
     * Set column name
     * @param columnName
     * @return
     */
    public FieldFactory withColumnName(String columnName) {
        fieldDefinition.setAttribute(InfoField.ATTRIBUTE_ColumnName, columnName);
        return this;
    }

    /**
     * Set name or label
     * @param name
     * @return
     */
    public FieldFactory withName(String name) {
        fieldDefinition.setAttribute(InfoField.ATTRIBUTE_Name, name);
        return this;
    }

    /**
     * Set Desctiption
     * @param description
     * @return
     */
    public FieldFactory withDescription(String description) {
        fieldDefinition.setAttribute(InfoField.ATTRIBUTE_Description, description);
        return this;
    }

    /**
     * Set Help
     * @param help
     * @return
     */
    public FieldFactory withHelp(String help) {
        fieldDefinition.setAttribute(InfoField.ATTRIBUTE_Help, help);
        return this;
    }

    /**
     * Set read only
     * @param isReadOnly
     * @return
     */
    public FieldFactory withReadOnly(boolean isReadOnly) {
        fieldDefinition.setAttribute(InfoField.ATTRIBUTE_IsReadOnly, isReadOnly);
        return this;
    }

    /**
     * Set Is Updateable
     * @param isUpdateable
     * @return
     */
    public FieldFactory withUpdateable(boolean isUpdateable) {
        fieldDefinition.setAttribute(InfoField.ATTRIBUTE_IsUpdateable, isUpdateable);
        return this;
    }

    /**
     * Set always updateable
     * @param isAlwaysUpdateable
     * @return
     */
    public FieldFactory withAlwaysUpdateable(boolean isAlwaysUpdateable) {
        fieldDefinition.setAttribute(InfoField.ATTRIBUTE_IsAlwaysUpdateable, isAlwaysUpdateable);
        return this;
    }

    /**
     * Set mandatory
     * @param isMandatory
     * @return
     */
    public FieldFactory withMandatory(boolean isMandatory) {
        fieldDefinition.setAttribute(InfoField.ATTRIBUTE_IsMandatory, isMandatory);
        return this;
    }

    /**
     * Set Encrypted
     * @param isEncrypted
     * @return
     */
    public FieldFactory withEncrypted(String isEncrypted) {
        fieldDefinition.setAttribute(InfoField.ATTRIBUTE_IsEncrypted, isEncrypted);
        return this;
    }

    /**
     * get Field component from factory
     * @return
     */
    public Field getFieldComponent() {
        Field fieldLoaded = null;
        //  For Text
        if (fieldDefinition.isText()) {
            fieldLoaded = new FieldText(fieldDefinition);
        } else if (fieldDefinition.isNumeric()) {
            fieldLoaded = new FieldNumber(fieldDefinition);
        } else if (fieldDefinition.isDate()) {
            fieldLoaded = new FieldDateTime(fieldDefinition);
        } else if (fieldDefinition.isLookup()) {
            fieldLoaded = new FieldAutoComplete(fieldDefinition);
        } else {
            throw new SpinSuiteException("Unsupported Type" + fieldDefinition.getDisplayType());
        }
        //
        return fieldLoaded;
    }
}
