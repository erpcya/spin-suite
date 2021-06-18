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
package org.erpya.access.util;

import org.erpya.base.access.AccessService;
import org.erpya.base.exceptions.SpinSuiteException;
import org.erpya.model.RoleInfo;
import org.erpya.model.SessionInfo;
import org.erpya.model.UserInfo;
import org.spin.grpc.util.Session;


/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
public class SecurityHelper {

    private static SecurityHelper instance = new SecurityHelper();
    private String host;
    private int port;

    public static SecurityHelper getInstance() {
        return instance;
    }

    /**
     * Set Backend parameters
     * @param host
     * @param port
     * @return
     */
    public SecurityHelper withConnectionValues(String host, int port) {
        this.host = host;
        this.port = port;
        return this;
    }

    /**
     * Login user
     * @param username
     * @param password
     * @return
     */
    public SessionInfo loginWithUserName(String username, String password) {
        try {
            Session session = AccessService.getInstance().withConnectionValues(host, port).loginWithUser(username, password, null);
            if(session != null) {
                SessionInfo sessionInfo = SessionInfo.getInstance()
                        .setSessionUuid(session.getUuid())
                        .setSessionName(session.getName())
                        .setSessionId(session.getId());
                org.spin.grpc.util.UserInfo user = session.getUserInfo();
                if(user != null) {
                    sessionInfo.setUserInfo(new UserInfo(user.getUuid(), username, session.getUserInfo().getName(), null, session.getUserInfo().getDescription(), session.getUserInfo().getComments()));
                }
                org.spin.grpc.util.Role role = session.getRole();
                if(role != null) {
                    sessionInfo.setRoleInfo(new RoleInfo(role.getId(), role.getUuid(), role.getName(), role.getDescription(), role.getClientName(), role.getClientId()));
                }
                AccessService.getInstance().closeServiceProvider();
                return sessionInfo.setIsLogged(true);
            }
            throw new Exception("User / Password");
        } catch (Exception e) {
            throw new SpinSuiteException(e);
        }
    }

    /**
     * Login user
     * @param token
     * @param language
     * @return
     */
    public SessionInfo loginWithToken(String token, String language) {
        try {
            Session session = AccessService.getInstance().withConnectionValues(host, port).loginWithToken(token, language);
            if(session != null) {
                SessionInfo sessionInfo = SessionInfo.getInstance()
                        .setSessionUuid(session.getUuid())
                        .setSessionName(session.getName())
                        .setSessionId(session.getId());
                org.spin.grpc.util.UserInfo user = session.getUserInfo();
                if(user != null) {
                    sessionInfo.setUserInfo(new UserInfo(user.getUuid(), user.getName(), session.getUserInfo().getName(), null, session.getUserInfo().getDescription(), session.getUserInfo().getComments()));
                }
                org.spin.grpc.util.Role role = session.getRole();
                if(role != null) {
                    sessionInfo.setRoleInfo(new RoleInfo(role.getId(), role.getUuid(), role.getName(), role.getDescription(), role.getClientName(), role.getClientId()));
                }
                AccessService.getInstance().closeServiceProvider();
                return sessionInfo.setIsLogged(true);
            }
            throw new Exception("User / Password");
        } catch (Exception e) {
            throw new SpinSuiteException(e);
        }
    }
}
