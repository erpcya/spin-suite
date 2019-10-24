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

import org.erpya.base.util.Criteria;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Interface of distint DB support
 * @author yamel, ysenih@erpya.com , http://www.erpya.com
 * <li> FR [  ]
 * @see https://github.com/erpcya/Spin-Suite/issues/
 */
public interface DBSupport {

    /**
     * Open Database for use
     */
    void open() throws Exception;

    /**
     * Close Database
     * @throws Exception
     */
    void close() throws Exception;

    /**
     * Save a Map from entity parameter
     * @param entity
     * @return Id
     */
    String saveMap(Map<String, Object> entity) throws Exception;

    /**
    * get Map with attributes and values from criteria
    */
    Map<String, Object> getMap(Criteria condition) throws Exception;

    /**
     * Get a list with all attributes
     * @param condition
     * @return
     * @throws Exception
     */
    List<Map<String, Object>> getListMap(Criteria condition) throws Exception;
    /**
     * Verify if it is open
     * @return
     */
    boolean isOpen();

    /**
     * Get Date from DB value
     * @param value
     * @return
     */
    Date convetToDate(String value);
}
