package org.erpya.app.arduino.initialsetup;

import org.erpya.app.arduino.remotesetup.R;
import org.erpya.base.util.DisplayType;
import org.erpya.component.base.ITab;
import org.erpya.component.factory.FieldFactory;
import org.erpya.component.factory.TabFactory;
import org.erpya.component.window.Wizard;

public class BusinessPartnerWizard extends Wizard {


    @Override
    protected void setupTabs() {
        //  Custom Device Business Partner
        ITab customStep = TabFactory.createTab(getApplicationContext())
                .withCustomClass(BusinessPartnerWizard.class)
                .withTitle("Business Partner Wizard")
                .getTab();
        addTab(customStep);
        //  Business Partner Definition step
        ITab BusinessPartnerWizardStep = TabFactory.createTab(getApplicationContext())
                .withTitle(getString(R.string.BusinessPartnerWizard))
                .withMandatory(true)
                .withTableName("BusinessPartner")
                .withAdditionalField(
                        FieldFactory.createField(getApplicationContext())
                                .withColumnName("BusinessPartnerCode")
                                .withName(getString(R.string.code))
                                .withReadOnly(false)
                                .withUpdateable(true)
                                .withMandatory(true)
                                .withDisplayType(DisplayType.STRING)
                                .getFieldDefinition())
                .withAdditionalField(FieldFactory.createField(getApplicationContext())
                        .withColumnName("BusinessPartnerName")
                        .withName(getString(R.string.Name))
                        .withReadOnly(false)
                        .withUpdateable(true)
                        .withMandatory(true)
                        .withDisplayType(DisplayType.STRING)
                        .getFieldDefinition())
                .withAdditionalField(FieldFactory.createField(getApplicationContext())
                        .withColumnName("BusinessPartnerName2")
                        .withName(getString(R.string.name2))
                        .withReadOnly(false)
                        .withUpdateable(true)
                        .withMandatory(false)
                        .withDisplayType(DisplayType.STRING)
                        .getFieldDefinition())
                .withAdditionalField(FieldFactory.createField(getApplicationContext())
                        .withColumnName("BusinessPartnerGroup")
                        .withName(getString(R.string.C_BP_Group_ID))
                        .withReadOnly(false)
                        .withUpdateable(true)
                        .withMandatory(false)
                        .withDisplayType(DisplayType.STRING)
                        .getFieldDefinition())
                .getTab();
        addTab(BusinessPartnerWizardStep);
        //  Business Partner Location Info Step
        ITab BusinessPartnerLocation = TabFactory.createTab(getApplicationContext())
                .withTitle(getString(R.string.BusinessPartnerLocation))
                .withTableName("BusinessPartnerLocation")
                .withAdditionalField(
                        FieldFactory.createField(getApplicationContext())
                                .withColumnName("Address")
                                .withName(getString(R.string.address))
                                .withReadOnly(false)
                                .withUpdateable(true)
                                .withMandatory(false)
                                .withDisplayType(DisplayType.STRING)
                                .getFieldDefinition())
                .withAdditionalField(FieldFactory.createField(getApplicationContext())
                        .withColumnName("Address1")
                        .withName(getString(R.string.address1))
                        .withReadOnly(false)
                        .withUpdateable(true)
                        .withMandatory(false)
                        .withDisplayType(DisplayType.STRING)
                        .getFieldDefinition())
                .withAdditionalField(FieldFactory.createField(getApplicationContext())
                        .withColumnName("Address2")
                        .withName(getString(R.string.address2))
                        .withReadOnly(false)
                        .withMandatory(false)
                        .withDisplayType(DisplayType.STRING)
                        .getFieldDefinition())
                .withAdditionalField(FieldFactory.createField(getApplicationContext())
                        .withColumnName("Address3")
                        .withName(getString(R.string.address3))
                        .withReadOnly(false)
                        .withUpdateable(true)
                        .withMandatory(false)
                        .withDisplayType(DisplayType.STRING)
                        .getFieldDefinition())
                .withAdditionalField(FieldFactory.createField(getApplicationContext())
                        .withColumnName("Name")
                        .withName(getString(R.string.Name))
                        .withReadOnly(false)
                        .withUpdateable(true)
                        .withMandatory(true)
                        .withDisplayType(DisplayType.STRING)
                        .getFieldDefinition())
                .getTab();
        addTab(BusinessPartnerLocation);
        //  Business Partner Address Info Step
        ITab BusinessPartnerAddress = TabFactory.createTab(getApplicationContext())
                .withTitle(getString(R.string.BusinessPArtnerAddress))
                .withTableName("BusinessPartnerAddress")
                .withAdditionalField(
                        FieldFactory.createField(getApplicationContext())
                                .withColumnName("BusinessPartnerAddressRegion")
                                .withName(getString(R.string.Region))
                                .withReadOnly(false)
                                .withUpdateable(true)
                                .withMandatory(false)
                                .withDisplayType(DisplayType.STRING)
                                .withEncrypted("@array/Location")
                                .getFieldDefinition())
                .withAdditionalField(FieldFactory.createField(getApplicationContext())
                        .withColumnName("BusinessPartnerAddressCountry")
                        .withName(getString(R.string.Country))
                        .withReadOnly(false)
                        .withUpdateable(true)
                        .withMandatory(true)
                        .withDisplayType(DisplayType.STRING)
                        .getFieldDefinition())
                .withAdditionalField(FieldFactory.createField(getApplicationContext())
                        .withColumnName("BusinessPartnerAddressCity")
                        .withName(getString(R.string.City))
                        .withReadOnly(false)
                        .withUpdateable(true)
                        .withMandatory(true)
                        .withDisplayType(DisplayType.STRING)
                        .getFieldDefinition())
                .withAdditionalField(FieldFactory.createField(getApplicationContext())
                        .withColumnName("BusinessPartnerAddressState")
                        .withName(getString(R.string.State))
                        .withReadOnly(false)
                        .withUpdateable(true)
                        .withMandatory(true)
                        .withDisplayType(DisplayType.STRING)
                        .getFieldDefinition())
                .getTab();
        addTab(BusinessPartnerAddress);


    }

}