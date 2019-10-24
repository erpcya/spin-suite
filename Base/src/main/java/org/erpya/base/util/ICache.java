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
package org.erpya.base.util;

import java.util.Collection;
import java.util.Set;

/**
 * Cache interface used as contract of a implemented cache
 * @author Yamel Senih, ysenih@erpya.com , http://www.erpya.com
 * <li> FR [  ]
 * @see https://github.com/adempiere/spin-suite/issues/2
 */
public interface ICache <K, V> {

    /**
     * Put a value from a key, it can replace a existing value if has the same key
     * @param key
     * @param value
     */
    public void put(K key, V value);

    /**
     * Used for get a cached value from key
     * @param key
     * @return
     */
    public V get(K key);


    /**
     * Remove a cache entry
     * @param key
     */
    public void remove(K key);

    /**
     * Get all values
     * @return
     */
    public Collection<?> values();

    /**
     * Clear cache
     */
    public void clear();

    /**
     * Get Cache Size
     * @return
     */
    public int size();

    /**
     * Get Cache Name
     * @return
     */
    public String getName();

    /**
     * Copy cache from other
     * @param fromCache
     */
    public void copyFrom(ICache fromCache);

    /**
     * Get Key Set
     * @return
     */
    public Set<K> keySet();

    /**
     * Verify if is empty cache
     * @return
     */
    public boolean isEmpty();
}
