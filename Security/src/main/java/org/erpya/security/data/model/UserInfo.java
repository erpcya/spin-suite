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

/**
 * User information like name, username and comments
 */
public class UserInfo {

    private String userName;
    private String displayName;
    private String eMail;
    private String description;
    private String comments;

    /**
     * default constructor set context
     * @param userName
     * @param displayName
     * @param eMail
     * @param description
     * @param comments
     */
    public UserInfo(String userName, String displayName, String eMail, String description, String comments) {
        this.userName = userName;
        this.displayName = displayName;
        this.eMail = eMail;
        this.description = description;
        this.comments = comments;
        Env.setContext("#User_UserName", SecureHandler.getInstance(Env.getContext()).getSecureEngine().encrypt(userName));
        Env.setContext("#User_DisplayName", SecureHandler.getInstance(Env.getContext()).getSecureEngine().encrypt(displayName));
        Env.setContext("#User_UserEMail", SecureHandler.getInstance(Env.getContext()).getSecureEngine().encrypt(eMail));
        Env.setContext("#User_Description", SecureHandler.getInstance(Env.getContext()).getSecureEngine().encrypt(description));
        Env.setContext("#User_Comments", SecureHandler.getInstance(Env.getContext()).getSecureEngine().encrypt(comments));
    }

    /**
     * Load all from context
     */
    public UserInfo() {
        loadFromContext();
    }

    private void loadFromContext() {
        userName = SecureHandler.getInstance(Env.getContext()).getSecureEngine().decrypt(Env.getContext("#User_UserName"));
        displayName = SecureHandler.getInstance(Env.getContext()).getSecureEngine().decrypt(Env.getContext("#User_DisplayName"));
        eMail = SecureHandler.getInstance(Env.getContext()).getSecureEngine().decrypt(Env.getContext("#User_UserEMail"));
        description = SecureHandler.getInstance(Env.getContext()).getSecureEngine().decrypt(Env.getContext("#User_Description"));
        comments = SecureHandler.getInstance(Env.getContext()).getSecureEngine().decrypt(Env.getContext("#User_Comments"));
    }

    public String getEMail() {
        return eMail;
    }

    public String getUserName() {
        return userName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getDescription() {
        return description;
    }

    public String getComments() {
        return comments;
    }
}
