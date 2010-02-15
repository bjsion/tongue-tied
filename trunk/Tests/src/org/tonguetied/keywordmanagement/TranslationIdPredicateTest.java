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

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

/**
 * Test class for the {@link TranslationIdPredicate} class.
 * 
 * @author bsion
 *
 */
public class TranslationIdPredicateTest
{
    private Translation translation;

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception
    {
        translation = new Translation();
        translation.setId(3L);
    }

    /**
     * Test method for {@link org.tonguetied.keywordmanagement.TranslationIdPredicate#evaluate(java.lang.Object)}.
     */
    @Test
    public final void testEvaluateWithUnknownId()
    {
        final TranslationIdPredicate predicate = new TranslationIdPredicate(5L);
        assertFalse(predicate.evaluate(translation));
    }

    /**
     * Test method for {@link org.tonguetied.keywordmanagement.TranslationIdPredicate#evaluate(java.lang.Object)}.
     */
    @Test(expected=IllegalArgumentException.class)
    public final void testEvaluateWithNullTranslationId()
    {
        Long id = null;
        new TranslationIdPredicate(id);
    }

    /**
     * Test method for {@link org.tonguetied.keywordmanagement.TranslationIdPredicate#evaluate(java.lang.Object)}.
     */
    @Test(expected=IllegalArgumentException.class)
    public final void testEvaluateWithNullTranslationIds()
    {
        Set<Long> ids = null;
        new TranslationIdPredicate(ids);
    }

    /**
     * Test method for {@link org.tonguetied.keywordmanagement.TranslationIdPredicate#evaluate(java.lang.Object)}.
     */
    @Test
    public final void testEvaluateWithNullTranslation()
    {
        final TranslationIdPredicate predicate = new TranslationIdPredicate(3L);
        assertFalse(predicate.evaluate(null));
    }

    /**
     * Test method for {@link org.tonguetied.keywordmanagement.TranslationIdPredicate#evaluate(java.lang.Object)}.
     */
    @Test
    public final void testEvaluate()
    {
        final TranslationIdPredicate predicate = new TranslationIdPredicate(3L);
        assertTrue(predicate.evaluate(translation));
    }

    /**
     * Test method for {@link org.tonguetied.keywordmanagement.TranslationIdPredicate#evaluate(java.lang.Object)}.
     */
    @Test
    public final void testEvaluateWithSingleMatch()
    {
        Set<Long> ids = new HashSet<Long>();
        ids.add(3L);
        ids.add(4L);
        final TranslationIdPredicate predicate = new TranslationIdPredicate(ids);
        assertTrue(predicate.evaluate(translation));
    }

    /**
     * Test method for {@link org.tonguetied.keywordmanagement.TranslationIdPredicate#evaluate(java.lang.Object)}.
     */
    @Test
    public final void testEvaluateWithNoMatch()
    {
        Set<Long> ids = new HashSet<Long>();
        ids.add(2L);
        ids.add(4L);
        final TranslationIdPredicate predicate = new TranslationIdPredicate(ids);
        assertFalse(predicate.evaluate(translation));
    }

}
