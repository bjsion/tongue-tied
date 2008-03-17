package org.tonguetied.web;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Before;
import org.junit.Test;
import org.tonguetied.domain.Translation.TranslationState;


/**
 * @author bsion
 *
 */
public class TranslationStateSupportTest {
    TranslationStateSupport support;

    @Before
    public void setUp() {
        support = new TranslationStateSupport();
    }
    /**
     * Test method for {@link org.tonguetied.web.TranslationStateSupport#getAsText()}.
     */
    @Test
    public final void testGetAsText() {
        support.setValue(TranslationState.QUERIED);
        
        assertEquals("QUERIED", support.getAsText());
    }

    /**
     * Test method for {@link org.tonguetied.web.TranslationStateSupport#getAsText()}.
     */
    @Test
    public final void testGetAsTextWithNull() {
        support.setValue(null);
        
        assertEquals("", support.getAsText());
    }

    /**
     * Test method for {@link org.tonguetied.web.TranslationStateSupport#setAsText(java.lang.String)}.
     */
    @Test
    public final void testSetAsText() {
        support.setAsText("VERIFIED");
        
        assertEquals(TranslationState.VERIFIED, support.getValue());
    }

    /**
     * Test method for {@link org.tonguetied.web.TranslationStateSupport#setAsText(java.lang.String)}.
     */
    @Test
    public final void testSetAsTextWithNull() {
        support.setAsText(null);
        
        assertNull(support.getValue());
    }

    /**
     * Test method for {@link org.tonguetied.web.TranslationStateSupport#setAsText(java.lang.String)}.
     */
    @Test
    public final void testSetAsTextWithEmptyString() {
        support.setAsText("");
        
        assertNull(support.getValue());
    }

    /**
     * Test method for {@link org.tonguetied.web.TranslationStateSupport#setAsText(java.lang.String)}.
     */
    @Test(expected=IllegalArgumentException.class)
    public final void testSetAsTextWithInvalidString() {
        support.setAsText("INVALID");
    }
}
