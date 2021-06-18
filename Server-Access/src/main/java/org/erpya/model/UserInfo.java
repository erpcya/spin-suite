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
package org.erpya.model;

import org.erpya.base.util.Env;

/**
 * User information like name, username and comments
 */
public class UserInfo {

    private String userName;
    private String displayName;
    private String eMail;
    private String description;
    private String comments;
    private String userUuid;

    /**
     * default constructor set context
     * @param userName
     * @param displayName
     * @param eMail
     * @param description
     * @param comments
     */
    public UserInfo(String userUuid, String userName, String displayName, String eMail, String description, String comments) {
        this.userUuid = userUuid;
        this.userName = userName;
        this.displayName = displayName;
        this.eMail = eMail;
        this.description = description;
        this.comments = comments;
        Env.setContext("#User_UUID", userUuid);
        Env.setContext("#User_UserName", userName);
        Env.setContext("#User_DisplayName", displayName);
        Env.setContext("#User_UserEMail", eMail);
        Env.setContext("#User_Description", description);
        Env.setContext("#User_Comments", comments);
    }

    /**
     * Load all from context
     */
    public UserInfo() {
        loadFromContext();
    }

    private void loadFromContext() {
        userUuid = Env.getContext("#User_UUID");
        userName = Env.getContext("#User_UserName");
        displayName = Env.getContext("#User_DisplayName");
        eMail = Env.getContext("#User_UserEMail");
        description = Env.getContext("#User_Description");
        comments = Env.getContext("#User_Comments");
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

    public String getUserUuid() {
        return userUuid;
    }
}
