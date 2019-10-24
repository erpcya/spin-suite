/*************************************************************************************
 * Product: Spin-Suite (Mobile Suite)                       		                 *
 * Copyright (C) 2012-2018 E.R.P. Consultores y Asociados, C.A.                      *
 * Contributor(s): Yamel Senih ysenih@erpya.com				  		                 *
 * Contributor(s): Carlos Parada cparada@erpya.com				  		             *
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
package org.erpya.base.exceptions;

import android.util.Log;

/**
 * Exception handle for Spin-Suite
 * @author yamel, ysenih@erpya.com , http://www.erpya.com
 * <li> FR [  ]
 * @see https://github.com/adempiere/spin-suite/issues/2
 */
public class SpinSuiteException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2340179640558569534L;


	/**
	 * Default Constructor (saved logger error will be used as message)
	 */
	public SpinSuiteException() {
		this((String) null);
	}
	

	/**
	 * @param message
	 */
	public SpinSuiteException(String message) {
		super(message);
		Log.e("SpinSuiteException", "Error: " + message);
	}

	/**
	 * @param cause
	 */
	public SpinSuiteException(Throwable cause) {
		super(cause);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public SpinSuiteException(String message, Throwable cause) {
		super(message, cause);
	}

	@Override
	public String getLocalizedMessage() {
		return super.getLocalizedMessage();
	}
}
