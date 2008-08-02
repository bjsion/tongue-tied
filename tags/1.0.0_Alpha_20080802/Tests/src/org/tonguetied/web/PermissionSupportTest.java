package org.tonguetied.web;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;
import org.tonguetied.usermanagement.UserRight.Permission;


/**
 * @author bsion
 *
 */
public class PermissionSupportTest {

    /**
     * Test method for {@link org.tonguetied.web.CountryCodeSupport#getAsText()}.
     */
    @Test
    public final void testGetAsText() {
        PermissionSupport support = new PermissionSupport();
        support.setValue(Permission.ROLE_ADMIN);
        
        assertEquals("ROLE_ADMIN", support.getAsText());
    }

    /**
     * Test method for {@link org.tonguetied.web.CountryCodeSupport#getAsText()}.
     */
    @Test
    public final void testGetAsTextWithNull() {
        PermissionSupport support = new PermissionSupport();
        support.setValue(null);
        
        assertEquals("", support.getAsText());
    }

    /**
     * Test method for {@link org.tonguetied.web.CountryCodeSupport#setAsText(java.lang.String)}.
     */
    @Test
    public final void testSetAsText() {
        PermissionSupport support = new PermissionSupport();
        support.setAsText("ROLE_ADMIN");
        
        assertEquals(Permission.ROLE_ADMIN, support.getValue());
    }

    /**
     * Test method for {@link org.tonguetied.web.CountryCodeSupport#setAsText(java.lang.String)}.
     */
    @Test
    public final void testSetAsTextWithNull() {
        PermissionSupport support = new PermissionSupport();
        support.setAsText(null);
        
        assertNull(support.getValue());
    }

    /**
     * Test method for {@link org.tonguetied.web.CountryCodeSupport#setAsText(java.lang.String)}.
     */
    @Test
    public final void testSetAsTextWithEmptyString() {
        PermissionSupport support = new PermissionSupport();
        support.setAsText("");
        
        assertNull(support.getValue());
    }

    /**
     * Test method for {@link org.tonguetied.web.CountryCodeSupport#setAsText(java.lang.String)}.
     */
    @Test(expected=IllegalArgumentException.class)
    public final void testSetAsTextWithInvalidString() {
        PermissionSupport support = new PermissionSupport();
        support.setAsText("INVALID");
    }
}
