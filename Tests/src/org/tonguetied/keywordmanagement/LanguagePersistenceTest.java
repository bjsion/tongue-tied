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
import static org.junit.Assert.assertTrue;
import static org.tonguetied.test.common.Constants.TABLE_LANGUAGE;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.Test;
import org.tonguetied.keywordmanagement.Language.LanguageCode;
import org.tonguetied.test.common.PersistenceTestBase;



/**
 * @author bsion
 *
 */
public class LanguagePersistenceTest extends PersistenceTestBase {

    @Test
    public final void simplePersistence() {
        Session session;
        Transaction tx;
        
        session = getSession();
        tx = session.beginTransaction();
        assertTrue(tx.isActive());

        Language language = new Language();
        language.setCode(LanguageCode.en);
        language.setName("English");
        session.saveOrUpdate(language);
        session.close();
        
        session = getSession();
        tx = session.beginTransaction();
        Language reloaded = 
            (Language) session.get(Language.class, language.getId());
        assertEquals(language, reloaded);
        tx.rollback();
        session.close();
    }
    
    @Override
    protected String[] getTableNames()
    {
        return new String[] {TABLE_LANGUAGE};
    }
}
