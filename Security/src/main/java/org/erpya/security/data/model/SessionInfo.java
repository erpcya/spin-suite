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
package org.erpya.security.data.model;

import org.erpya.base.util.Env;
import org.erpya.security.util.SecureHandler;

import java.util.HashMap;
import java.util.Map;

/**
 * Singleton class for provide SessionInfo values and login values
 * This class can be instaced after Env class instance
 */
public class SessionInfo {

    private static final SessionInfo instance = new SessionInfo();
    private String sessionUuid;
    private String sessionName;
    private boolean isLogged;
    private int sessionId;
    private RoleInfo role;
    private UserInfo user;
    private Map<String, Object> defaultContext;

    public static SessionInfo getInstance() {
        return instance;
    }

    private SessionInfo() {
        sessionUuid = SecureHandler.getInstance(Env.getContext()).getSecureEngine().decrypt(Env.getContext("#Session_UUID"));
        sessionName = SecureHandler.getInstance(Env.getContext()).getSecureEngine().decrypt(Env.getContext("#Session_Name"));
        isLogged = Env.getContextAsBoolean("#IsLogged");
        sessionId = Env.getContextAsInt("#Session_ID");
        defaultContext = new HashMap<>();
        user = new UserInfo();
        role = new RoleInfo();
    }

    public SessionInfo setIsLogged(boolean isLogged) {
        this.isLogged = isLogged;
        Env.setContext("#IsLogged", isLogged);
        return this;
    }

    public boolean isLogged() {
        return isLogged;
    }

    public SessionInfo setSessionName(String sessionName) {
        this.sessionName = sessionName;
        Env.setContext("#Session_Name", sessionName);
        return this;
    }

    public String getSessionName() {
        return sessionName;
    }

    public SessionInfo setSessionUuid(String sessionUuid) {
        this.sessionUuid = sessionUuid;
        Env.setContext("#Session_UUID", sessionUuid);
        return this;
    }

    public SessionInfo setSessionId(int sessionId) {
        this.sessionId = sessionId;
        Env.setContext("#Session_ID", sessionId);
        return this;
    }

    public int getSessionId() {
        return sessionId;
    }

    public String getSessionUuid() {
        return sessionUuid;
    }

    public Map<String, Object> getDefaultContext() {
        return defaultContext;
    }

    public SessionInfo setDefaultContext(Map<String, Object> defaultContext) {
        this.defaultContext = defaultContext;
        if(defaultContext != null) {
            defaultContext.entrySet().forEach(keyValue -> Env.setContextObject(keyValue.getKey(), keyValue.getValue()));
        }
        return this;
    }

    public SessionInfo setRoleInfo(RoleInfo role) {
        this.role = role;
        return this;
    }

    public SessionInfo setUserInfo(UserInfo user) {
        this.user = user;
        return this;
    }

    public UserInfo getUserInfo() {
        return user;
    }

    public RoleInfo getRoleInfo() {
        return role;
    }
}
