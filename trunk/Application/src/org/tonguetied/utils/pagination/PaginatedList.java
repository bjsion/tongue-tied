/*
 * Copyright 2008 The Tongue-Tied Authors
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not 
 * use this file except in compliance with the License. You may obtain a copy 
 * of the License at
 *  
 * http://www.apache.org/licenses/LICENSE-2.0 
 * 
 * Unless required by applicable law or agreed to in writing, software 
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT 
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the 
 * License for the specific language governing permissions and limitations 
 * under the License. 
 */
package org.tonguetied.utils.pagination;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * Immutable object that wraps a collection of objects that is used for 
 * paginated support. This class contains all the meta information used for 
 * pagination.
 * 
 * @author bsion
 */
public class PaginatedList<T> extends ArrayList<T>
{
    private static final long serialVersionUID = 7656155425446828050L;
    private int maxListSize;

    /**
     * Create a new instance of the PaginatedList object.
     * 
     * @param results the page of results
     * @param maxListSize the max number of records that would be return by an 
     * unpaginated result set
     */
    public PaginatedList(final List<T> results, final int maxListSize)
    {
        super(results);
        this.maxListSize = maxListSize;
    }

    /**
     * Get the size of the items for which the results are a subset.
     * 
     * @return the total number of items in store
     */
    public int getMaxListSize()
    {
        return maxListSize;
    }

    /**
     * This method is a wrapper method around the {@linkplain List#get(int)}.
     * Its primary use is for display when using the list a bean.
     * 
     * @param index the index of the list to return
     * @return the object in the list matching the index
     */
    public T getResult(final int index)
    {
        return super.get(index);
    }

    @Override
    public String toString()
    {
        return new ReflectionToStringBuilder(this,
                ToStringStyle.SHORT_PREFIX_STYLE).toString();
    }
}
