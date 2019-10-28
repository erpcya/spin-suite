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
package org.erpya.component.window;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.erpya.base.model.GenericPO;
import org.erpya.base.model.InfoField;
import org.erpya.base.model.PO;
import org.erpya.base.model.POInfo;
import org.erpya.base.util.Condition;
import org.erpya.base.util.Criteria;
import org.erpya.base.util.Util;
import org.erpya.component.R;
import org.erpya.component.base.IWizardStepPage;
import org.erpya.component.factory.FieldFactory;
import org.erpya.component.field.Field;

import java.util.List;

/**
 * This Fragment is for add dynamically a step for activity_wizard
 */
public class Tab extends Fragment implements IWizardStepPage {

    /**
     * The dummy deviceName this fragment is presenting.
     */
    private LinearLayout parent;
    private TextView stepName;
    private TextView stepHelp;
    /** Table Name  */
    private String tableName;
    /** Model   */
    private PO stepModel;
    /** Condition for load  */
    private Criteria criteria;
    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public Tab() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tab, container, false);
        parent = rootView.findViewById(R.id.parent);
        //  Set Name and Help
        if(!Util.isEmpty(name)) {
            stepName = rootView.findViewById(R.id.StepName);
            stepName.setText(name);
        }
        if(!Util.isEmpty(help)) {
            stepHelp = rootView.findViewById(R.id.StepHelp);
            stepHelp.setText(help);
        }
        ViewGroup.LayoutParams layoutParams = parent.getLayoutParams();
        layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        Bundle arguments = getArguments();
        if(arguments != null) {
            criteria = arguments.getParcelable(Criteria.PARCEABLE_NAME);
            if(!Util.isEmpty(tableName)) {
                stepModel = new GenericPO(getContext(), tableName);
                if(criteria != null) {
                    //  Add table support
                    Criteria criteriaForLoad = new Criteria();
                    criteriaForLoad.copyFromCriteria(criteria);
                    criteriaForLoad.addCriteria(POInfo.METADATA_TABLE_NAME, Condition.EQUAL, tableName);
                    if(!stepModel.reload(criteriaForLoad)) {
                        for(Condition condition : criteriaForLoad.getCriteriaList()) {
                            stepModel.setValue(condition.getKeyAttribute(), condition.getValue());
                        }
                    }
                }
            }
        }
        //  Device Name
        if(fields != null) {
            for(InfoField field: fields) {
                Field child = FieldFactory.createField(field).getFieldComponent();
                //  Set value
                if(stepModel != null) {
                    Object value = stepModel.getValueAsObject(child.getFieldDefinition().getColumnName());
                    if(value != null) {
                        child.setValue(value);
                        if(child.getFieldDefinition().isLookup()) {
                            String displayValue = stepModel.getValueAsString(child.getFieldDefinition().getDisplayColumnName());
                            if(!Util.isEmpty(displayValue)) {
                                child.setFieldDisplauValue(displayValue);
                            }
                        }
                    }
                }
                parent.addView(child, layoutParams);
            }
        }
        //
        return rootView;
    }

    @Override
    public boolean validate() {
        if(parent == null) {
            return false;
        }
        if(Util.isEmpty(tableName)) { {
           return true;
        }}
        int count = parent.getChildCount();
        for(int i = 0; i < count; i++) {
            View view = parent.getChildAt(i);
            if(view instanceof Field) {
                Field field = (Field) view;
                boolean isValidField = field.validateValue();
                if(field.getFieldDefinition().isMandatory()
                        && !isValidField) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public boolean save() {
        if(parent == null) {
            return false;
        }
        if(Util.isEmpty(tableName)) { {
            return true;
        }}
        int count = parent.getChildCount();
        boolean isValid = true;
        for(int i = 0; i < count; i++) {
            View view = parent.getChildAt(i);
            if(view instanceof Field) {
                Field field = (Field) view;
                boolean isValidField = field.validateValue();
                if(field.getFieldDefinition().isMandatory()
                        && !isValidField) {
                    isValid = false;
                } else if(isValidField
                        && !Util.isEmpty(tableName)) {
                    stepModel.setValue(field.getFieldDefinition().getColumnName(), field.getValue());
                    if(field.getFieldDefinition().isLookup()) {
                        stepModel.setValue(field.getFieldDefinition().getDisplayColumnName(), field.getDisplayValue());
                    }
                }
            }
        }
        //  Save
        if(isValid) {
            stepModel.saveEx();
        }
        return isValid;
    }

    /**
     * Set field to show
     * @param fields
     */
    public void setFields(List<InfoField> fields) {
        this.fields = fields;
    }

    /**
     * Set Table Name
     * @param tableName
     */
    public void setTableName(String tableName) {
        this.tableName = tableName;
    }


    /**
     * Set name
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Set help to show
     * @param help
     */
    public void setHelp(String help) {
        this.help = help;
    }
    /** Name    */
    private String name;
    /** Help    */
    private String help;
    /** Fields  */
    private List<InfoField> fields;
}
