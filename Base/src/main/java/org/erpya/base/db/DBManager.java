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
package org.erpya.base.db;

import android.content.Context;
import android.util.Log;

import org.erpya.base.model.PO;

import org.erpya.base.util.Criteria;
import org.erpya.base.util.Env;
import org.erpya.base.util.Util;

import java.lang.reflect.Constructor;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Database Manager (Handle support for distinct database)
 *
 * @author yamel, ysenih@erpya.com , http://www.erpya.com
 */
public final class DBManager {

    /**
     * Private constructor for evelope
     * @param context
     */
    private DBManager(Context context) {
        this.context = context;
    }

    /** Private context */
    private Context context = null;
    /** Instance of this    */
    private static DBManager instance = null;
    /** Database reference  */
    private DBSupport database = null;
    /**
     * Get instance for it (Create new instance if it does not exist)
     * @param context
     * @return
     */
    public static DBManager getInstance(Context context) {
        if(instance == null) {
            instance = new DBManager(context);
        }
        //  Default return
        return instance;
    }

    /**
     * Get Database
     * @return
     * @throws Exception
     */
    private DBSupport getDatabase() throws Exception {
        if(database == null) {
            loadClass();
        }
        //  Default return
        return database;
    }

    /**
     * Get context
     * @return
     */
    public Context getContext() {
        return context;
    }

    /**
     * Get class name for instance
     * @return
     */
    private String getClassname() {
        return Env.getCurrentSupportedDatabase();
    }

    /**
     * Get Class from device type, used for handler
     * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
     * @return
     * @return Class<?>
     */
    private Class<?> getHandlerClass() {
        String className = getClassname();
        //	Validate null values
        if(Util.isEmpty(className)) {
            return null;
        }
        try {
            Class<?> clazz = Class.forName(className);
            if(DBSupport.class.isAssignableFrom(clazz)) {
                return clazz;
            }
            //	Make sure that it is a PO class
            Class<?> superClazz = clazz.getSuperclass();
            //	Validate super class
            while (superClazz != null) {
                if (superClazz == DBSupport.class) {
                    Log.e("Error loading class", "Use: " + className);
                    return clazz;
                }
                //	Get Super Class
                superClazz = superClazz.getSuperclass();
            }
        } catch (Exception e) {
            Log.e("Loading class Error", e.getMessage());
        }
        //
        Log.e("Not found", "Class: " + className);
        return null;
    }	//	getHandlerClass

    /**
     * Load class for export
     * @throws Exception
     */
    private void loadClass() throws Exception {
        if(database != null) {
            return;
        }
        //	Load it
        //	Get class from parent
        Class<?> clazz = getHandlerClass();
        //	Not yet implemented
        if (clazz == null) {
            Log.d("Class not found", "Using Standard Class");
            database = null;
            throw new Exception("Class for connection not found");
        }
        //
        Constructor<?> constructor = clazz.getDeclaredConstructor(new Class[]{Context.class});
        //	new instance
        database = (DBSupport) constructor.newInstance(new Object[] {getContext()});
    }

    /**
     * Helper Methods
     */
    public void save(PO entity) throws Exception {
        DBSupport database = getDatabase();
        //  Is Open
        boolean isOpen = database.isOpen();
        if(!isOpen) {
            database.open();
        }
        //  Save
        String id = database.saveMap(entity.getMap());
        entity.setId(id);
    }

    /**
     * Get Attributes from criteria, it can resurn null
     * @param criteria
     * @return
     * @throws Exception
     */
    public Map<String, Object> getMap(Criteria criteria) throws Exception {
        //  Validate metadata for query
        if (criteria == null) {
            return null;
        }
        //  Else
        DBSupport database = getDatabase();
        //  Is Open
        boolean isOpen = database.isOpen();
        if(!isOpen) {
            database.open();
        }
        //  Get Map
        Map<String, Object> attributes = database.getMap(criteria);
        //  Default return
        return attributes;
    }

    /**
     * Get List woith map for values
     * @param criteria
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getListMap(Criteria criteria) throws Exception {
        //  Validate metadata for query
        if (criteria == null) {
            return null;
        }
        //  Else
        DBSupport database = getDatabase();
        //  Is Open
        boolean isOpen = database.isOpen();
        if(!isOpen) {
            database.open();
        }
        //  Get Map
        return database.getListMap(criteria);
    }

    /**
     * Convert a DB value to date
     * @param value
     * @return
     * @throws Exception
     */
    public Date convetToDate(String value) throws Exception {
        return getDatabase().convetToDate(value);
    }
}
