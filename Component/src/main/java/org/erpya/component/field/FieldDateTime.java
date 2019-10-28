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

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.text.format.DateFormat;
import android.util.AttributeSet;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TimePicker;

import org.erpya.base.model.InfoField;
import org.erpya.base.util.Env;
import org.erpya.base.util.Util;
import org.erpya.base.util.ValueUtil;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Edit Text component for Date
 */
public class FieldDateTime extends FieldText implements View.OnClickListener, DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    /**
     * Standard constructor
     * @param context
     */
    public FieldDateTime(Context context) {
        super(context);
    }

    /**
     * From field definition
     * @param fieldDefinition
     */
    public FieldDateTime(InfoField fieldDefinition) {
        super(fieldDefinition);
    }

    /**
     * Init from parent
     * @param context
     * @param attrs
     */
    public FieldDateTime(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FieldDateTime(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    /** calendar for date   */
    private Calendar calendar;
    /** Default Date Format */
    private SimpleDateFormat dateFormat;
    /** Is 24 Hour View */
    private boolean is24HourView;
    /** Is enabled  */
    private boolean isEnabled;

    /**
     * Refresh Display
     * @return void
     */
    private void updateDisplay() {
        if(calendar != null) {
            getEditText().setText(dateFormat.format(calendar.getTime()));
        } else {
            getEditText().setText("");
        }
    }

    @Override
    protected void initFromInfoField() {
        super.initFromInfoField();
        getEditText().setFocusable(false);
        getEditText().setOnClickListener(this);
        getEditText().setHint("");
        //
        if(getFieldDefinition() != null
                && !Util.isEmpty(getFieldDefinition().getFormatPattern())) {
            dateFormat = Env.getDateFormat(getFieldDefinition().getFormatPattern());
        } else if(showTime()) {
            dateFormat = Env.getTimeFormat();
            is24HourView = DateFormat.is24HourFormat(getContext());
        } else {
            dateFormat = Env.getDateFormat();
        }
    }

    /**
     * Create Dialog
     * @return
     * @return Dialog
     */
    private Dialog createDialog() {
        if(calendar == null) {
            calendar = Calendar.getInstance();
        }
        //  For Time
        if(showTime()) {
            return new TimePickerDialog(getContext(),
                    this,
                    calendar.get(Calendar.HOUR_OF_DAY),
                    calendar.get(Calendar.MINUTE),
                    is24HourView);
        }
        //  For Date
        return new DatePickerDialog(getContext(),
                    this,
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH));
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        if(calendar == null) {
            calendar = Calendar.getInstance();
        }
        //  Set date from Picker
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        updateDisplay();
    }

    @Override
    public void onClick(View v) {
        if(isEnabled) {
            createDialog().show();
        }
    }

    /**
     * Set Date to field
     * @param date
     */
    public void setDate(Date date) {
        try{
            if(date == null){
                calendar = null;
            } else {
                if(calendar == null) {
                    calendar = Calendar.getInstance();
                }
                //
                calendar.setTime(date);
            }
            updateDisplay();
        } catch(Exception e) {
            log.error("setDate():" + e.getLocalizedMessage());
            getEditText().setText("");
        }
    }

    /**
     * Get date saved
     * @return
     */
    public Date getDate(){
        if(calendar == null) {
            return null;
        }
        //  Default
        return calendar.getTime();
    }

    @Override
    protected void setFieldValue(Object value) {
        setDate(ValueUtil.getValueAsDate(value));
    }

    @Override
    protected Object getFieldValue() {
        return getDate();
    }

    /**
     * Show Time
     * @return
     */
    private boolean showTime() {
        if(getFieldDefinition() != null) {
            return getFieldDefinition().isTimeOnly();
        }
        //  Default
        return false;
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        if(calendar == null) {
            calendar = Calendar.getInstance();
        }
        //  Set date from Picker
        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
        calendar.set(Calendar.MINUTE, minute);
        updateDisplay();
    }

    @Override
    public void setEnabled(boolean isEnabled) {
        this.isEnabled = isEnabled;
    }
}
