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
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import android.view.View;

import org.erpya.component.R;
import org.erpya.component.window.event.WindowEvent;
import org.erpya.component.window.event.WindowEventListener;

/**
 * Standard Wizard implementation
 */
public abstract class Wizard extends WindowManager implements WindowEventListener {

    /** Previous Action */
    private FloatingActionButton previousAction;
    /** Next Action */
    private FloatingActionButton nextAction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_wizard;
    }

    @Override
    protected void setupAdapter() {
        setEnableScroll(false);
    }

    @Override
    public void setupActions() {
        //  Get Previous Action
        previousAction = findViewById(R.id.previous_action);
        previousAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                previousAction();
            }
        });
        //  Get next Action
        nextAction = findViewById(R.id.next_action);
        nextAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextAction();
            }
        });
        addWindowListener(this);
    }

    @Override
    public void onStart(WindowEvent ev) {

    }

    @Override
    public void onValidate(WindowEvent ev) {

    }

    @Override
    public void onFinish(WindowEvent ev) {

    }

    @Override
    public void onChange(WindowEvent ev) {
        int position = getCurrentItem();
        if(position > 0) {
            previousAction.setVisibility(View.VISIBLE);
        } else {
            previousAction.setVisibility(View.GONE);
        }
    }
}