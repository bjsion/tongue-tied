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
package org.tonguetied.keywordmanagement;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.tonguetied.keywordmanagement.Translation.TranslationIdComparator;

/**
 * Test class for the {@link TranslationIdComparator} class.
 * 
 * @author bsion
 *
 */
@RunWith(value=Parameterized.class)
public class TranslationIdComparatorTest
{
    private Translation translation1;
    private Translation translation2;
    private int expected;

    @Parameters
    public static final Collection<Object[]> data()
    {
        return Arrays.asList(new Object[][] {
                {401L, 504L, -1},
                {698L, 504L, 1},
                {200L, 200L, 0},
                {null,257L, 1},
                {369L, null, -1},
                {null, null, 0}
        });
    }
    
    /**
     * Create a new instance of TranslationIdComparatorTest.
     *
     * @param translation1Id the id of the first translation
     * @param translation2Id the id of the first translation
     * @param expected the expected comparison result
     */
    public TranslationIdComparatorTest(final Long translation1Id,
            final Long translation2Id, final int expected)
    {
        this.translation1 = new Translation();
        this.translation2 = new Translation();
        this.translation1.setId(translation1Id);
        this.translation2.setId(translation2Id);
        this.expected = expected;
    }

    /**
     * Test method for {@link org.tonguetied.keywordmanagement.Translation.TranslationIdComparator#compare(org.tonguetied.keywordmanagement.Translation, org.tonguetied.keywordmanagement.Translation)}.
     */
    @Test
    public final void testCompare()
    {
        Comparator<Translation> comparator = new TranslationIdComparator();
        int result = comparator.compare(translation1, translation2);
        assertEquals(expected, result);
    }
}
