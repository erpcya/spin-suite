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
package org.erpya.component.field;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.erpya.base.dictionary.FieldInfo;
import org.erpya.base.util.Util;
import org.erpya.base.util.ValueUtil;
import org.erpya.component.R;
import org.erpya.base.util.LogM;

/**
 * Main view for lookup in/out
 * It can be implemented for:
 * Text View
 * Image View
 * Numeric
 * Spinner
 * Search View
 * Check box
 */
public abstract class Field extends LinearLayout {

    /**
     * Default constructor
     * @param context
     */
    public Field(Context context) {
        super(context);
        initLayout(null, 0);
    }

    /**
     * Constructor From field
     * @param fieldDefinition
     */
    public Field(FieldInfo fieldDefinition) {
        super(fieldDefinition.getContext());
        setInfoField(fieldDefinition);
        initLayout(null, 0);
    }

    public Field(Context context, AttributeSet attrs) {
        super(context, attrs);
        initLayout(attrs, 0);
    }

    public Field(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initLayout(attrs, defStyle);
    }

    /**
     * Init Layout
     * @param attrs
     * @param defStyle
     */
    private void initLayout(AttributeSet attrs, int defStyle) {
        //	Set Parameter
        setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
                LayoutParams.MATCH_PARENT));
        //	Set Orientation
        setOrientation(VERTICAL);
        //
        label = new TextView(getContext());
        label.setText(getResources().getString(R.string.None));
        //  Add to main
        addView(label);
        //  Call of parent for init child view
        init();
        initFromInfoField();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    /** Field Definition for it */
    private FieldInfo fieldDefinition;
    /** Label for Component */
    private TextView label;
    /** Value   */
    private Object value = null;
    /** Old Value   */
    private Object oldValue = null;
    /**	Logger							*/
    protected transient LogM log = new LogM(getContext(), this.getClass());

    /**
     * Get label object from view
     * @return
     */
    public TextView getLabel() {
        return label;
    }

    /**
     * Get Main Layout
     * @return
     */
    public LinearLayout getLayout() {
        return this;
    }
    /**
     * Get Field Definition
     * @return
     */
    public FieldInfo getFieldDefinition() {
        return fieldDefinition;
    }

    /**
     * Verify if it not has label
     * @return
     */
    public boolean isFieldOnly() {
        if(getFieldDefinition() != null) {
            return getFieldDefinition().isFieldOnly();
        }
        //
        return false;
    }

    /**
     * Verify if is displayed
     * @return
     */
    public boolean isDisplayed() {
        return false;
    }

    /**
     * Get Sequence for display
     * @return
     */
    public int getSeqNo() {
        if(getFieldDefinition() != null) {
            return getFieldDefinition().getSeqNo();
        }
        return 0;
    }

    /**
     * Get Sorting number
     * @return
     */
    public int getSortNo() {
        if(getFieldDefinition() != null) {
            return getFieldDefinition().getSortNo();
        }
        return 0;
    }

    /**
     * Get Text of Label
     * @return
     */
    public String getFieldLabel() {
        if(getFieldDefinition() != null) {
            return getFieldDefinition().getColumnLabel();
        }
        //  Default
        return null;
    }

    /**
     * Get value from component
     * @return
     */
    public Object getValue() {
        return getFieldValue();
    }

    /**
     * Get Old Value
     * @return
     */
    public Object getOldValue() {
        return oldValue;
    }

    /**
     * Set value for component
     * @param value
     */
    public void setValue(Object value) {
        oldValue = this.value;
        this.value = value;
        setFieldValue(value);
    }

    /**
     * Get display value
     * @return
     */
    public String getDisplayValue() {
        return getFieldDisplayValue();
    }

    /**
     * Set Field Value for child
     * @param value
     */
    protected abstract void setFieldValue(Object value);

    /**
     * set Field Display Value
     * @param value
     */
    public void setFieldDisplauValue(Object value) {
        //  Optional to be implemented
    }

    /**
     * Get Value from child
     * @return
     */
    protected abstract Object getFieldValue();

    /**
     * Get Display value from child
     * @return
     */
    protected abstract String getFieldDisplayValue();

    /**
     * Init child
     */
    protected abstract void init();

    /**
     * Init component from info field
     * It method can be called from parent after init
     */
    protected void initFromInfoField() {
        if(getFieldDefinition() != null) {
            //  Hide Label when is only field
            if(getFieldDefinition().isFieldOnly()) {
                getLabel().setVisibility(GONE);
            } else {
                getLabel().setVisibility(VISIBLE);
            }
            //  Enabled it
            setEnabled(getFieldDefinition().isUpdateable() && !getFieldDefinition().isReadOnly());
        }
    }

    /**
     * Set Info Field
     * @param fieldDefinition
     */
    public void setInfoField(FieldInfo fieldDefinition) {
        this.fieldDefinition = fieldDefinition;
    }

    /**
     * Set enabled attribute
     * @param isEnabled
     */
    public void setEnabled(boolean isEnabled) {
        //  TODO Implement it for all
    }

    /**
     * Get Field component
     * @return
     */
    public abstract View getField();

    /**
     * Validate field
     * @return
     */
    public boolean validateValue() {
        return validateValue(null);
    }

    /**
     * Validate field with a custom error for man datory fields
     * @return false when field is empty and is mandatory
     */
    public boolean validateValue(String customError) {
        View field = getField();
        if(field == null) {
            return false;
        }
        //  Validate value
        boolean isValid = false;
        if(getValue() != null) {
            if(getFieldDefinition().isText()) {
                isValid = !Util.isEmpty(ValueUtil.getValueAsString(getValue()));
            } else {
                isValid = true;
            }
        }
        if(getFieldDefinition().isMandatory()
                && !isValid) {
            //  For fields
            if(field.getClass().isAssignableFrom(EditText.class)){
                EditText castedField = (EditText) field;
                if(Util.isEmpty(customError)) {
                    customError = getContext().getString(R.string.msg_FieldRequired);
                }
                castedField.setError(customError);
            }
        }
        return isValid;
    }
}
