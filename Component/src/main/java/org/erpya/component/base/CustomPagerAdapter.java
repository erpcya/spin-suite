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
package org.erpya.component.base;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Custom Adapter for fragments
 */
public class CustomPagerAdapter extends FragmentPagerAdapter {

    public CustomPagerAdapter(FragmentManager fm) {
        super(fm);
        fragmentList = new ArrayList<ITab>();
    }

    @Override
    public Fragment getItem(int position) {
        if(!isValidPosition(position)) {
            return null;
        }
        return fragmentList.get(position).newInstance(savedInstanceState);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        if(!isValidPosition(position)) {
            return super.getPageTitle(position);
        }
        return fragmentList.get(position).getTitle();
    }

    /**
     * Verify if is a valid position
     * @param position
     * @return
     */
    private boolean isValidPosition(int position) {
        return fragmentList.size() > position;
    }

    /**
     * Add Step to list
     * @param step
     */
    public void addStep(ITab step) {
        fragmentList.add(step);
    }

    /** Fragment list   */
    private List<ITab> fragmentList;
    /** Parameters  */
    private Bundle savedInstanceState;

    /**
     * Set arguments for Step
     * @param savedInstanceState
     */
    public void setArguments(Bundle savedInstanceState) {
        this.savedInstanceState = savedInstanceState;
    }

    /**
     * Get Step definition
     * @param position
     * @return
     */
    public ITab getStepDefinition(int position) {
        if(!isValidPosition(position)) {
            return null;
        }
        return fragmentList.get(position);
    }
}
