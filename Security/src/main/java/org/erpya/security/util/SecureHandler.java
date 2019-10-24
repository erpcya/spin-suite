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
package org.erpya.security.util;

import android.content.Context;
import android.util.Log;

import org.erpya.base.exceptions.SpinSuiteException;
import org.erpya.base.util.Env;
import org.erpya.base.util.Util;

import java.lang.reflect.Constructor;

/**
 * Secure handler, if you want use it just must implement class org.erpya.security.util.SecureInterface
 */
public class SecureHandler {
    private static SecureHandler instance = null;

    public static SecureHandler getInstance(Context context) {
        if(instance == null) {
            instance = new SecureHandler(context);
        }
        return instance;
    }

    private SecureHandler(Context context) {
        this.context = context;
    }

    /** Context for it  */
    private Context context;
    /** Secure Interface    */
    private SecureInterface secureImplementation;

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
        return Env.getCurrentSupportedSecureEngine();
    }

    /**
     * Get Secure Engine
     * @return
     * @throws Exception
     */
    public SecureInterface getSecureEngine() {
        if(secureImplementation == null) {
            try {
                loadClass();
            } catch (Exception e) {
                new SpinSuiteException(e);
            }
        }
        //  Default return
        return secureImplementation;
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
            if(SecureInterface.class.isAssignableFrom(clazz)) {
                return clazz;
            }
            //	Make sure that it is a PO class
            Class<?> superClazz = clazz.getSuperclass();
            //	Validate super class
            while (superClazz != null) {
                if (superClazz == SecureInterface.class) {
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
        if(secureImplementation != null) {
            return;
        }
        //	Load it
        //	Get class from parent
        Class<?> clazz = getHandlerClass();
        //	Not yet implemented
        if (clazz == null) {
            Log.d("Class not found", "Using Standard Class");
            secureImplementation = null;
            throw new Exception("Class for connection not found");
        }
        //
        Constructor<?> constructor = clazz.getDeclaredConstructor(new Class[]{Context.class});
        //	new instance
        secureImplementation = (SecureInterface) constructor.newInstance(new Object[] {getContext()});
    }
}
