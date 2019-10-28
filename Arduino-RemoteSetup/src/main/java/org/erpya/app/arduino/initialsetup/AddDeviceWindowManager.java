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
package org.erpya.app.arduino.initialsetup;

import org.erpya.app.arduino.remotesetup.R;
import org.erpya.base.util.DisplayType;
import org.erpya.component.base.ITab;
import org.erpya.component.factory.FieldFactory;
import org.erpya.component.factory.TabFactory;
import org.erpya.component.window.Wizard;

/**
 * Custom activity_wizard for add new device
 */
public class AddDeviceWindowManager extends Wizard {

    @Override
    public void setupTabs() {
        //  Custom Device Acknowledgment
        ITab customStep = TabFactory.createTab(getApplicationContext())
                .withCustomClass(DeviceAcknowledgment.class)
                .withTitle("Device Acknowledgment")
                .getTab();
        addTab(customStep);
        //  Device Definition step
        ITab deviceDefinitionStep = TabFactory.createTab(getApplicationContext())
                .withTitle(getString(R.string.DeviceDefinitionTitle))
                .withHelp(getString(R.string.DeviceDefinitionHelp))
                .withMandatory(true)
                .withTableName("DeviceDefinition")
                .withAdditionalField(
                        FieldFactory.createField(getApplicationContext())
                        .withColumnName("DeviceName")
                        .withName(getString(R.string.Name))
                        .withReadOnly(false)
                        .withUpdateable(true)
                        .withMandatory(true)
                        .withDisplayType(DisplayType.STRING)
                        .getFieldDefinition())
                .withAdditionalField(FieldFactory.createField(getApplicationContext())
                        .withColumnName("DeviceDescription")
                        .withName(getString(R.string.Description))
                        .withReadOnly(false)
                        .withUpdateable(true)
                        .withMandatory(false)
                        .withDisplayType(DisplayType.TEXT_LONG)
                        .getFieldDefinition())
                .withAdditionalField(FieldFactory.createField(AddDeviceWindowManager.this)
                        .withColumnName("DeviceStartDate")
                        .withName("Start Date")
                        .withMandatory(true)
                        .withUpdateable(true)
                        .withDisplayType(DisplayType.DATE)
                        .getFieldDefinition())
                .withAdditionalField(FieldFactory.createField(AddDeviceWindowManager.this)
                        .withColumnName("DeviceStartTime")
                        .withName("Start Time")
                        .withMandatory(true)
                        .withUpdateable(true)
                        .withDisplayType(DisplayType.TIME)
                        .getFieldDefinition())
                .getTab();
        addTab(deviceDefinitionStep);
        //  WIFI Info Step
        ITab wifiInfoStep = TabFactory.createTab(getApplicationContext())
                .withTitle(getString(R.string.WIFIInfoTitle))
                .withHelp(getString(R.string.WIFIInfoHelp))
                .withTableName("WifiInfo")
                .withAdditionalField(
                        FieldFactory.createField(getApplicationContext())
                                .withColumnName("SSID")
                                .withName(getString(R.string.SSID))
                                .withReadOnly(false)
                                .withUpdateable(true)
                                .withMandatory(true)
                                .withDisplayType(DisplayType.STRING)
                                .getFieldDefinition())
                .withAdditionalField(FieldFactory.createField(getApplicationContext())
                        .withColumnName("PSK")
                        .withName(getString(R.string.Pass))
                        .withReadOnly(false)
                        .withUpdateable(true)
                        .withMandatory(true)
                        .withDisplayType(DisplayType.STRING)
                        .withEncrypted(true)
                        .getFieldDefinition())
                .withAdditionalField(FieldFactory.createField(getApplicationContext())
                        .withColumnName("DeviceNameReference")
                        .withTableName("DeviceDefinition")
                        .withDisplayColumnName("DeviceName")
                        .withName("reference to Device Name")
                        .withReadOnly(false)
                        .withUpdateable(true)
                        .withMandatory(true)
                        .withDisplayType(DisplayType.TABLE_DIR)
                        .getFieldDefinition())
                .getTab();
        addTab(wifiInfoStep);
    }
}
