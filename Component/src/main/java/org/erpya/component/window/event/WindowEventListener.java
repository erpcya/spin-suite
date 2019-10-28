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
package org.erpya.component.window.event;

/**
 * @author Yamel Senih, ysenih@erpya.com, ERPCyA http://www.erpya.com
 *	Add event listener for activity_wizard
 */
public interface WindowEventListener {

    /**
     * Fire event when activity_wizard is started
     * @param ev
     * @return void
     */
    void onStart(WindowEvent ev);

	/**
	 * Fire event when validate
	 * @param ev
	 * @return void
	 */
	void onValidate(WindowEvent ev);

    /**
     * Fire event when finish is done
     * @param ev
     * @return void
     */
    void onFinish(WindowEvent ev);

	/**
	 * Fire event for change view
	 * @param ev
	 */
	void onChange(WindowEvent ev);
}
