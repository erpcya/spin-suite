/*************************************************************************************
 * Product: Spin-Suite (Mobile Suite)                       		                 *
 * Copyright (C) 2012-2018 E.R.P. Consultores y Asociados, C.A.                      *
 * Contributor(s): Yamel Senih ysenih@erpya.com				  		                 *
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
package org.erpya.app.sales;

import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import org.erpya.base.database.support.CouchDBLite_2_0_Support;
import org.erpya.component.factory.FieldFactory;
import org.erpya.base.util.DisplayType;
import org.erpya.base.util.Env;
import org.erpya.security.ui.login.Login;
import org.erpya.security.util.RSACrypt;


public class SalesMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Env.getInstance(getApplicationContext());
        Env.setCurrentSupportedDatabase(CouchDBLite_2_0_Support.class.getName());
        Env.setCurrentSupportedSecureEngine(RSACrypt.class.getName());
        //  Add dynamic
        final ConstraintLayout parent = findViewById(R.id.parent);
        if(parent != null) {
            parent.addView(FieldFactory
                    .createField(this)
                    .withName("Test Label")
                    .withReadOnly(false)
                    .withUpdateable(true)
                    .withMandatory(true)
                    .withDisplayType(DisplayType.DATE)
                    .getFieldComponent(), 0, parent.getLayoutParams());
        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SalesMainActivity.this, Login.class);
                startActivity(intent);
            }
        });
    }
}
