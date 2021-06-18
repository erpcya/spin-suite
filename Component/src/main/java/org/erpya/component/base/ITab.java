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
import androidx.fragment.app.Fragment;

/**
 * Contract for determine standard methods to be implemented for Fragments used on WindowManager
 */
public interface ITab {

    /**
     * New instance for load
     * @param savedInstanceState
     * @return
     */
    Fragment newInstance(Bundle savedInstanceState);

    /**
     * Get next fragment item position
     * @return
     */
    int getNextItem();

    /**
     * This method is called from next action buttom
     * @return true if is ok
     */
    boolean validateIt();

    /**
     * Save current tab
     * @return
     */
    boolean saveIt();

    /**
     * Get Title for it
     * @return
     */
    String getTitle();

    /**
     * Verify if is mandatory
     * @return
     */
    boolean isMandatory();
}
