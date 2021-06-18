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
import com.google.android.material.tabs.TabLayout;

import org.erpya.component.R;

public class Window extends WindowManager {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    protected int getContentView() {
        return R.layout.activity_window;
    }

    @Override
    protected void setupTabs() {

    }

    @Override
    protected void setupAdapter() {
        TabLayout tabs = super.findViewById(R.id.tabs);
        tabs.setupWithViewPager(getViewPagerController());
        setEnableScroll(true);
    }

    @Override
    protected void setupActions() {

    }
}