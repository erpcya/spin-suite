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
package org.erpya.base.model;

/**
 * Interface for determine methods and constants of InfoField
 * @author yamel, ysenih@erpya.com , http://www.erpya.com
 * <li> FR [ 2 ]
 * @see https://github.com/adempiere/spin-suite/issues/2
 */
public interface IInfoField {

    /** Supported Attributes    */
    String ATTRIBUTE_IsFieldOnly = "IsFieldOnly";
    String ATTRIBUTE_DisplayLogic = "DisplayLogic";
    String ATTRIBUTE_DisplayLength = "DisplayLength";
    String ATTRIBUTE_SeqNo = "SeqNo";
    String ATTRIBUTE_SortNo = "SortNo";
    String ATTRIBUTE_InfoFactoryClass = "InfoFactoryClass";
    String ATTRIBUTE_IsReadOnly = "IsReadOnly";

    /**
     * Verify if it is without label
     * @return
     */
    public boolean isFieldOnly();

    /**
     * Get a display logic for new event listener
     * @return
     */
    public String getDisplayLogic();

    /**
     * Get field length
     * @return
     */
    public int getDisplayLength();

    /**
     * Get Sequence of Field
     * @return
     */
    public int getSeqNo();


    /**
     * Get Sorting No of Field
     * @return
     */
    public int getSortNo();

    /**
     * Get a Class for load lookup
     * @return
     */
    public String getInfoFactoryClass();

    /**
     * Verify if is read only
     * @return
     */
    public boolean isReadOnly();
}
