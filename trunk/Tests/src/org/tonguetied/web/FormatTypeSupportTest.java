package org.tonguetied.web;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;
import org.tonguetied.domain.FormatType;


/**
 * @author bsion
 *
 */
public class FormatTypeSupportTest {

    /**
     * Test method for {@link org.tonguetied.web.FormatTypeSupport#getAsText()}.
     */
    @Test
    public final void testGetAsText() {
        FormatTypeSupport support = new FormatTypeSupport();
        support.setValue(FormatType.csv);
        
        assertEquals("csv", support.getAsText());
    }

    /**
     * Test method for {@link org.tonguetied.web.FormatTypeSupport#getAsText()}.
     */
    @Test
    public final void testGetAsTextWithNull() {
        FormatTypeSupport support = new FormatTypeSupport();
        support.setValue(null);
        
        assertEquals("", support.getAsText());
    }

    /**
     * Test method for {@link org.tonguetied.web.FormatTypeSupport#setAsText(java.lang.String)}.
     */
    @Test
    public final void testSetAsText() {
        FormatTypeSupport support = new FormatTypeSupport();
        support.setAsText("xls");
        
        assertEquals(FormatType.xls, support.getValue());
    }

    /**
     * Test method for {@link org.tonguetied.web.FormatTypeSupport#setAsText(java.lang.String)}.
     */
    @Test
    public final void testSetAsTextWithNull() {
        FormatTypeSupport support = new FormatTypeSupport();
        support.setAsText(null);
        
        assertNull(support.getValue());
    }

    /**
     * Test method for {@link org.tonguetied.web.FormatTypeSupport#setAsText(java.lang.String)}.
     */
    @Test
    public final void testSetAsTextWithEmptyString() {
        FormatTypeSupport support = new FormatTypeSupport();
        support.setAsText("");
        
        assertNull(support.getValue());
    }

    /**
     * Test method for {@link org.tonguetied.web.FormatTypeSupport#setAsText(java.lang.String)}.
     */
    @Test(expected=IllegalArgumentException.class)
    public final void testSetAsTextWithInvalidString() {
        FormatTypeSupport support = new FormatTypeSupport();
        support.setAsText("INVALID");
    }
}
