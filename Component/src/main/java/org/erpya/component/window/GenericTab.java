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

import org.erpya.base.model.InfoField;
import org.erpya.component.base.ITab;
import org.erpya.component.base.IWizardStepPage;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Yamel Senih, ysenih@erpya.com, http://www.erpya.com
 * Generic WindowManager Step
 */
public class GenericTab implements ITab {

    public GenericTab() {
        nextStep = -1;
        fields = new ArrayList<InfoField>();
    }

    /** Mandatory property  */
    private boolean isMandatory;
    /** Next Step   */
    private int nextStep;
    /** Title of Step   */
    private String title;
    /** Help to show for WindowManager Step    */
    private String help;
    /** Fields  */
    private List<InfoField> fields;
    /** Step    */
    private IWizardStepPage tab;
    /** Table Name  */
    private String tableName;
    /** Custom Class    */
    private Class<?> customClass;

    @Override
    public Fragment newInstance(Bundle savedInstanceState) {
        tab = getInstance();
        if(tab instanceof Tab) {
            Tab genericStep = (Tab) tab;
            genericStep.setName(title);
            genericStep.setHelp(help);
            genericStep.setTableName(tableName);
            genericStep.setFields(fields);
        }

        if(savedInstanceState != null) {
            ((Fragment) tab).setArguments(savedInstanceState);
        }
        return (Fragment) tab;
    }

    @Override
    public int getNextItem() {
        return nextStep;
    }

    @Override
    public boolean validateIt() {
        if(tab == null) {
            return true;
        }
        return tab.validate();
    }

    @Override
    public boolean saveIt() {
        if(tab == null) {
            return true;
        }
        return tab.save();
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public boolean isMandatory() {
        return isMandatory;
    }

    /**
     * Set Table Name
     * @param tableName
     */
    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    /**
     * Set title for Step
     * @param title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Set help for tab
     * @param help
     */
    public void setHelp(String help) {
        this.help = help;
    }

    /**
     * Set mandatory property
     * @param isMandatory
     */
    public void setMandatory(boolean isMandatory) {
        this.isMandatory = isMandatory;
    }

    /**
     * Add field to Step
     * @param field
     */
    public void addField(InfoField field) {
        fields.add(field);
    }

    /**
     * Get Step fragment
     * @return
     */
    public IWizardStepPage getTab() {
        return tab;
    }

    /**
     * Set custom class for it
     * @param customClass
     */
    public void setCustomClass(Class<?> customClass) {
        this.customClass = customClass;
    }

    /**
     * Get WindowManager Step
     * @return
     */
    private IWizardStepPage getInstance() {
        //	Not yet implemented
        if (customClass == null
                || !IWizardStepPage.class.isAssignableFrom(customClass)) {
            return new Tab();
        } else {
            //
            Constructor<?> constructor = null;
            try {
                constructor = customClass.getDeclaredConstructor();
                //	new instance
                return (IWizardStepPage)constructor.newInstance();
            } catch (Exception e) {

            }
        }
        //
        return null;
    }
}
