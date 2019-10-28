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

import org.erpya.base.model.InfoField;
import org.erpya.component.base.ITab;
import org.erpya.component.window.GenericTab;


/**
 * @author Yamel Senih, ysenih@erpya.com , http://www.erpya.com
 * Factory for WindowManager Step
 */
public class TabFactory {
    /**
     * Standard Constructor for it,
     * @param context is mandatory
     */
    private TabFactory(Context context) {
        tab = new GenericTab();
    }

    /** Dynamic Step for load   */
    private GenericTab tab;

    /**
     * Create Field
     * @param context
     * @return
     */
    public static TabFactory createTab(Context context) {
        return new TabFactory(context);
    }

    /**
     * Set Title
     * @param title
     * @return
     */
    public TabFactory withTitle(String title) {
        tab.setTitle(title);
        return this;
    }

    /**
     * Set Table
     * @param tableName
     * @return
     */
    public TabFactory withTableName(String tableName) {
        tab.setTableName(tableName);
        return this;
    }

    /**
     * Set Help
     * @param help
     * @return
     */
    public TabFactory withHelp(String help) {
        tab.setHelp(help);
        return this;
    }

    /**
     * Set mandatory property
     * @param isMandatory
     * @return
     */
    public TabFactory withMandatory(boolean isMandatory) {
        tab.setMandatory(isMandatory);
        return this;
    }

    /**
     * Add field to Step
     * @param field
     * @return
     */
    public TabFactory withAdditionalField(InfoField field) {
        tab.addField(field);
        return this;
    }

    /**
     * Wit custom class name
     * @param customClass
     * @return
     */
    public TabFactory withCustomClass(Class<?> customClass) {
        tab.setCustomClass(customClass);
        return this;
    }

    /**
     * Get Step definition
     * @return
     */
    public ITab getTab() {
        return tab;
    }
}
