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

import org.erpya.base.util.Env;
import org.erpya.base.util.Util;
import org.erpya.security.data.model.LoggedInUser;
import org.erpya.security.util.SecureHandler;

/**
 * Class that requests authentication and user information from the remote data source and
 * maintains an in-memory cache of login status and user credentials information.
 */
public class LoginRepository {

    private static volatile LoginRepository instance;

    private LoginDataSource dataSource;

    // If user credentials will be cached in local storage, it is recommended it be encrypted
    // @see https://developer.android.com/training/articles/keystore
    private LoggedInUser user = null;

    // private constructor : singleton access
    private LoginRepository(LoginDataSource dataSource) {
        this.dataSource = dataSource;
        String userId = Env.getContext("#User_ID");
        String token = Env.getContext("#SessionToken");
        if(!Util.isEmpty(token)) {
            String userDisplayName = Env.getContext("#User_DisplayName");
            user = new LoggedInUser(SecureHandler.getInstance(Env.getContext()).getSecureEngine().decrypt(userId),
                    userDisplayName, SecureHandler.getInstance(Env.getContext()).getSecureEngine().decrypt(userId));
        }
    }

    public static LoginRepository getInstance(LoginDataSource dataSource) {
        if (instance == null) {
            instance = new LoginRepository(dataSource);
        }
        return instance;
    }

    public boolean isLoggedIn() {
        return user != null;
    }

    public void logout() {
        user = null;
        dataSource.logout();
    }

    private void setLoggedInUser(LoggedInUser user) {
        this.user = user;
        String userId = SecureHandler.getInstance(Env.getContext()).getSecureEngine().encrypt(user.getUserId());
        String token = SecureHandler.getInstance(Env.getContext()).getSecureEngine().encrypt(user.getToken());
        Env.setContext("#SessionToken", token);
        Env.setContext("#User_ID", userId);
        Env.setContext("#User_DisplayName", user.getDisplayName());
    }

    public Result<LoggedInUser> login(String username, String password) {
        // handle login
        Result<LoggedInUser> result = dataSource.login(username, password);
        if (result instanceof Result.Success) {
            setLoggedInUser(((Result.Success<LoggedInUser>) result).getData());
        }
        return result;
    }
}
