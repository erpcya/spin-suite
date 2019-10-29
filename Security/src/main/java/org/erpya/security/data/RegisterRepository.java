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
package org.erpya.security.data;

import org.erpya.security.data.model.RegisteredUser;

/**
 * Class that requests authentication and user information from the remote data source and
 * maintains an in-memory cache of login status and user credentials information.
 */
public class RegisterRepository {

    private static volatile RegisterRepository instance;

    private SecurityDataSource dataSource;
    private RegisteredUser user = null;

    // private constructor : singleton access
    private RegisterRepository(SecurityDataSource dataSource) {
        this.dataSource = dataSource;
    }

    public static RegisterRepository getInstance(SecurityDataSource dataSource) {
        if (instance == null) {
            instance = new RegisterRepository(dataSource);
        }
        return instance;
    }

    private void setRegisteredUser(RegisteredUser user) {
        this.user = user;
    }

    public Result<RegisteredUser> enroll(String name, String username, String email) {
        // handle login
        Result<RegisteredUser> result = dataSource.enroll(name, username, email);
        if (result instanceof Result.Success) {
            setRegisteredUser(((Result.Success<RegisteredUser>) result).getData());
        }
        return result;
    }
}
