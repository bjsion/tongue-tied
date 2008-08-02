package org.tonguetied.web;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;
import org.tonguetied.keywordmanagement.Country.CountryCode;


/**
 * @author bsion
 *
 */
public class CountryCodeSupportTest {

    /**
     * Test method for {@link org.tonguetied.web.CountryCodeSupport#getAsText()}.
     */
    @Test
    public final void testGetAsText() {
        CountryCodeSupport support = new CountryCodeSupport();
        support.setValue(CountryCode.JP);
        
        assertEquals("JP", support.getAsText());
    }

    /**
     * Test method for {@link org.tonguetied.web.CountryCodeSupport#getAsText()}.
     */
    @Test
    public final void testGetAsTextWithNull() {
        CountryCodeSupport support = new CountryCodeSupport();
        support.setValue(null);
        
        assertEquals("", support.getAsText());
    }

    /**
     * Test method for {@link org.tonguetied.web.CountryCodeSupport#setAsText(java.lang.String)}.
     */
    @Test
    public final void testSetAsText() {
        CountryCodeSupport support = new CountryCodeSupport();
        support.setAsText("HK");
        
        assertEquals(CountryCode.HK, support.getValue());
    }

    /**
     * Test method for {@link org.tonguetied.web.CountryCodeSupport#setAsText(java.lang.String)}.
     */
    @Test
    public final void testSetAsTextWithNull() {
        CountryCodeSupport support = new CountryCodeSupport();
        support.setAsText(null);
        
        assertNull(support.getValue());
    }

    /**
     * Test method for {@link org.tonguetied.web.CountryCodeSupport#setAsText(java.lang.String)}.
     */
    @Test
    public final void testSetAsTextWithEmptyString() {
        CountryCodeSupport support = new CountryCodeSupport();
        support.setAsText("");
        
        assertNull(support.getValue());
    }

    /**
     * Test method for {@link org.tonguetied.web.CountryCodeSupport#setAsText(java.lang.String)}.
     */
    @Test(expected=IllegalArgumentException.class)
    public final void testSetAsTextWithInvalidString() {
        CountryCodeSupport support = new CountryCodeSupport();
        support.setAsText("INVALID");
    }
}
