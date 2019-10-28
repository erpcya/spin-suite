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
import java.util.List;
import java.util.Map;

/**
 * This class have all related to role and access from it
 */
public class RoleInfo {

    private String name;
    private String uuid;
    private String description;
    private String clientName;
    private int clientId;
    private int roleId;
    private Map<String, Boolean> accessInfo;
    /** Access Main key */
    final String ACCESS_KEY = "#ACCESS|";
    /** Window Key for access */
    final String WINDOW_KEY = ACCESS_KEY + "WINDOW";
    /** Form Key for access */
    final String FORM_KEY = ACCESS_KEY + "FORM";
    /** Process Key for access */
    final String PROCESS_KEY = ACCESS_KEY + "PROCESS";
    /** Organization Key for access */
    final String ORGANIZATION_KEY = ACCESS_KEY + "ORGANIZATION";
    /** Document Action Key for access */
    final String DOCUMENT_ACTION_KEY = ACCESS_KEY + "DOCUMENT_ACTION";

    /**
     * Default constructor
     * @param roleId
     * @param uuid
     * @param name
     * @param description
     * @param clientName
     * @param clientId
     */
    public RoleInfo(int roleId, String uuid, String name, String description, String clientName, int clientId) {
        this.name = name;
        this.uuid = uuid;
        this.description = description;
        this.clientName = clientName;
        this.clientId = clientId;
        this.roleId = roleId;
        accessInfo = new HashMap<>();
        Env.setContext("#Role_Name", SecureHandler.getInstance(Env.getContext()).getSecureEngine().encrypt(name));
        Env.setContext("#Role_UUID", SecureHandler.getInstance(Env.getContext()).getSecureEngine().encrypt(uuid));
        Env.setContext("#Role_ClientName", SecureHandler.getInstance(Env.getContext()).getSecureEngine().encrypt(clientName));
        Env.setContext("#Role_Description", SecureHandler.getInstance(Env.getContext()).getSecureEngine().encrypt(description));
        Env.setContext("#Role_AD_Client_ID", clientId);
        Env.setContext("#Role_AD_Role_ID", roleId);
    }

    /**
     * Load values from context
     */
    public RoleInfo() {
        loadFromContext();
    }

    private void loadFromContext() {
        name = SecureHandler.getInstance(Env.getContext()).getSecureEngine().decrypt(Env.getContext("#Role_Name"));
        uuid = SecureHandler.getInstance(Env.getContext()).getSecureEngine().decrypt(Env.getContext("#Role_UUID"));
        clientName = SecureHandler.getInstance(Env.getContext()).getSecureEngine().decrypt(Env.getContext("#Role_ClientName"));
        description = SecureHandler.getInstance(Env.getContext()).getSecureEngine().decrypt(Env.getContext("#User_Description"));
        clientId = Env.getContextAsInt("#Role_AD_Client_ID");
        roleId = Env.getContextAsInt("#Role_AD_Role_ID");
        List<String> access = Env.getKeys(ACCESS_KEY);
        if(access != null) {
            access.stream().forEach(key -> accessInfo.put(key, Env.getContextAsBoolean(key)));
        }
    }

    public String getName() {
        return name;
    }

    public String getUuid() {
        return uuid;
    }

    public String getDescription() {
        return description;
    }

    public String getClientName() {
        return clientName;
    }

    public int getClientId() {
        return clientId;
    }

    public int getRoleId() {
        return roleId;
    }

    public void addWindowAccess(String uuid, boolean isReadOnly) {
        accessInfo.put(WINDOW_KEY + "|" + uuid, isReadOnly);
        Env.setContext(WINDOW_KEY + "|" + uuid, isReadOnly);
    }

    public void addProcessAccess(String uuid, boolean isReadOnly) {
        accessInfo.put(PROCESS_KEY + "|" + uuid, isReadOnly);
        Env.setContext(PROCESS_KEY + "|" + uuid, isReadOnly);
    }

    public void addFormAccess(String uuid, boolean isReadOnly) {
        accessInfo.put(FORM_KEY + "|" + uuid, isReadOnly);
        Env.setContext(FORM_KEY + "|" + uuid, isReadOnly);
    }

    public void addOrganizationAccess(String uuid, boolean isReadOnly) {
        accessInfo.put(ORGANIZATION_KEY + "|" + uuid, isReadOnly);
        Env.setContext(ORGANIZATION_KEY + "|" + uuid, isReadOnly);
    }

    public void addDocumentActionAccess(String uuid, String documentAction) {
        accessInfo.put(DOCUMENT_ACTION_KEY + "|" + uuid + "|" + documentAction, true);
        Env.setContext(DOCUMENT_ACTION_KEY + "|" + uuid + "|" + documentAction, true);
    }

    /**
     * Get Window Acccess
     * @param uuid
     * @return null in no access, TRUE if r/w and FALSE if r/o
     */
    public boolean getWindowAccess(String uuid) {
        return accessInfo.get(WINDOW_KEY + "|" + uuid);
    }

    /**
     * Get Process Acccess
     * @param uuid
     * @return null in no access, TRUE if r/w and FALSE if r/o
     */
    public boolean getProcessAccess(String uuid) {
        return accessInfo.get(PROCESS_KEY + "|" + uuid);
    }

    /**
     * Get Form Acccess
     * @param uuid
     * @return null in no access, TRUE if r/w and FALSE if r/o
     */
    public boolean getFormAccess(String uuid) {
        return accessInfo.get(FORM_KEY + "|" + uuid);
    }

    /**
     * Get Organization Acccess
     * @param uuid
     * @return null in no access, TRUE if r/w and FALSE if r/o
     */
    public boolean getOrganizationAccess(String uuid) {
        return accessInfo.get(ORGANIZATION_KEY + "|" + uuid);
    }

    /**
     * Get Document Action Acccess
     * @param uuid
     * @return null in no access, TRUE if have access
     */
    public boolean getDocumentActionAccess(String uuid, String documentAction) {
        return accessInfo.get(DOCUMENT_ACTION_KEY + "|" + uuid + "|" + documentAction);
    }
}
