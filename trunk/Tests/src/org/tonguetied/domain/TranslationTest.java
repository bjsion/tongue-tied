package org.tonguetied.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.tonguetied.domain.Country.CountryCode;
import org.tonguetied.domain.Language.LanguageCode;
import org.tonguetied.domain.Translation.TranslationState;


/**
 * @author bsion
 *
 */
public class TranslationTest {
    
    private Language portugese; 
    private Country brazil;
    private Bundle bundle;
    
    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
        portugese = new Language();
        portugese.setCode(LanguageCode.pt);
        portugese.setName("Portugese");
        
        brazil = new Country();
        brazil.setCode(CountryCode.BR);
        brazil.setName("Brasil");
        
        this.bundle = new Bundle();
        this.bundle.setName("bundle name");
        this.bundle.setResourceName("resourceName");
    }

    /**
     * Test method for {@link org.tonguetied.domain.Keyword#clone()}.
     */
    @Test
    public final void testCloneWithEmptyValues() throws CloneNotSupportedException {
        Translation translation = new Translation();
        translation.setLanguage(null);
        translation.setCountry(null);
        translation.setBundle(null);
        translation.setValue(null);
        translation.setState(null);
        
        Translation clone = translation.clone();
        
        assertFalse(translation == clone);
        assertTrue(translation.getClass() == clone.getClass());
        assertEquals(translation, clone);
    }
    
    /**
     * Test method for {@link org.tonguetied.domain.Translation#clone()}.
     */
    @Test
    public void testClone() throws CloneNotSupportedException {
        Translation translation = new Translation();
        translation.setLanguage(portugese);
        translation.setCountry(brazil);
        translation.setBundle(bundle);
        translation.setValue("joga");
        translation.setState(TranslationState.QUERIED);

        Translation clone = translation.clone();
        
        assertFalse(translation == clone);
        assertTrue(translation.getClass() == clone.getClass());
        assertEquals(translation, clone);
    }
}
