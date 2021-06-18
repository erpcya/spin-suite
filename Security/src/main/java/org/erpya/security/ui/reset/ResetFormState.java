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
package org.erpya.security.ui.reset;

import androidx.annotation.Nullable;

/**
 * Data validation state of the reset password form.
 */
class ResetFormState {
    @Nullable
    private Integer rePasswordError;
    @Nullable
    private Integer passwordError;
    private boolean isDataValid;

    ResetFormState(@Nullable Integer passwordError, @Nullable Integer rePasswordError) {
        this.rePasswordError = rePasswordError;
        this.passwordError = passwordError;
        this.isDataValid = false;
    }

    ResetFormState(boolean isDataValid) {
        this.rePasswordError = null;
        this.passwordError = null;
        this.isDataValid = isDataValid;
    }

    @Nullable
    Integer getRePasswordError() {
        return rePasswordError;
    }

    @Nullable
    Integer getPasswordError() {
        return passwordError;
    }

    boolean isDataValid() {
        return isDataValid;
    }
}
