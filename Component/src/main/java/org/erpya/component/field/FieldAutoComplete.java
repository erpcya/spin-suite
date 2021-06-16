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
import android.text.InputType;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;

import org.erpya.base.dictionary.FieldInfo;
import org.erpya.base.util.DisplayType;
import org.erpya.base.util.KeyValue;
import org.erpya.base.util.Util;
import org.erpya.base.util.ValueUtil;
import org.erpya.component.base.PersistenceListAdapter;

/**
 * Edit Text component for in / out
 */
public class FieldAutoComplete extends Field {

    /**
     * Standard constructor
     *
     * @param context
     */
    public FieldAutoComplete(Context context) {
        super(context);
    }

    /**
     * From field definition
     *
     * @param fieldDefinition
     */
    public FieldAutoComplete(FieldInfo fieldDefinition) {
        super(fieldDefinition);
    }

    /**
     * Init from parent
     *
     * @param context
     * @param attrs
     */
    public FieldAutoComplete(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FieldAutoComplete(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void setFieldValue(Object value) {
        uuid = ValueUtil.getValueAsString(value);
        if(Util.isEmpty(editText.getText().toString())) {
            editText.setText(uuid);
        }
    }

    @Override
    public void setFieldDisplauValue(Object value) {
        editText.setText(ValueUtil.getValueAsString(value));
    }

    @Override
    protected Object getFieldValue() {
        return uuid;
    }

    @Override
    protected String getFieldDisplayValue() {
        return editText.getText().toString();
    }

    /**
     * Edit text
     */
    private AutoCompleteTextView editText;
    private String uuid;

    /**
     * Get Edit text used
     *
     * @return
     */
    protected EditText getEditText() {
        return editText;
    }

    /**
     * Init it from parent
     */
    protected void init() {
        PersistenceListAdapter adapter = new PersistenceListAdapter(getFieldDefinition());
        editText = new AutoCompleteTextView(getContext());
        editText.setAdapter(adapter);
        editText.setOnItemClickListener((adapterView, view, i, l) -> {
            KeyValue value = (KeyValue) adapterView.getItemAtPosition(i);
            if(value != null) {
                uuid = value.getUuid();
            }
        });
        //  Add to parent
        addView(editText);
    }

    @Override
    public void setEnabled(boolean isEnabled) {
        super.setEnabled(isEnabled);
        //  For it
        editText.setEnabled(isEnabled);
    }

    @Override
    public View getField() {
        return getEditText();
    }

    @Override
    protected void initFromInfoField() {
        super.initFromInfoField();
        if (getFieldDefinition() != null) {
            //  For Input Type
            if (getInputType() > 0) {
                editText.setInputType(getInputType());
            }
            //	Set Hint
            editText.setHint(getFieldDefinition().getColumnLabel());
            setEnabled(!getFieldDefinition().isReadOnly());
            //	Selected All on Focus
            editText.setSelectAllOnFocus(true);
            //	Set Multi-line
            if ((getFieldDefinition().getDisplayType() == DisplayType.TEXT
                    || getFieldDefinition().getDisplayType() == DisplayType.TEXT_LONG
                    || getFieldDefinition().getDisplayType() == DisplayType.MEMO)
                    && !getFieldDefinition().isEncrypted()) {
                editText.setSingleLine(false);
            }
        }
    }

    /**
     * Get input Type with is Encrypted
     *
     * @return int
     */
    protected int getInputType() {
        int inputType = 0;
        if (getFieldDefinition() == null) {
            return inputType;
        }
        int displayType = getFieldDefinition().getDisplayType();

        if (getFieldDefinition().isText()) {
            //	Encrypted
            if (getFieldDefinition().isEncrypted()) {
                inputType = InputType.TYPE_CLASS_TEXT |
                        InputType.TYPE_TEXT_VARIATION_PASSWORD;
            } else if (displayType == DisplayType.TEXT
                    || displayType == DisplayType.TEXT_LONG
                    || displayType == DisplayType.MEMO) {
                inputType = InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_MULTI_LINE
                        | InputType.TYPE_TEXT_FLAG_CAP_SENTENCES;
            } else if (displayType == DisplayType.URL) {
                inputType = InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_URI;
            } else {
                inputType = InputType.TYPE_CLASS_TEXT
                        | InputType.TYPE_TEXT_FLAG_CAP_SENTENCES;
            }
        } else if (displayType == DisplayType.INTEGER) {
            inputType = InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_SIGNED;
        } else if (getFieldDefinition().isNumeric()) {
            inputType = InputType.TYPE_CLASS_NUMBER
                    | InputType.TYPE_NUMBER_FLAG_DECIMAL
                    | InputType.TYPE_NUMBER_FLAG_SIGNED
                    | InputType.TYPE_CLASS_PHONE;
        } else if (getFieldDefinition().isDate()) {
            inputType = InputType.TYPE_CLASS_DATETIME;
        }
        //	Default
        return inputType;
    }
}
