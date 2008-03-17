/**
 * 
 */
package org.tonguetied.service;

import java.util.Locale;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.springframework.core.io.Resource;
import org.springframework.test.AbstractSingleSpringContextTests;

/**
 * @author bsion
 *
 */
public class ResourceTest extends AbstractSingleSpringContextTests {
    
    private static final Logger logger = Logger.getLogger(ResourceTest.class);

    @Test
    public final void testResourceLoader() {
        //applicationContext.getResources(arg0)
        Resource resource = super.applicationContext.getResource("classpath:/tonguetied.properties");
        assertTrue(resource.exists());
        assertEquals("tonguetied.properties", resource.getFilename());
        
        resource = super.applicationContext.getResource("classpath:/tonguetied_he.properties");
        assertTrue(resource.exists());
        assertEquals("tonguetied_he.properties", resource.getFilename());
    }
    
    @Test
    public final void testMessageWithDefaultLocale() {
        Locale locale = Locale.getDefault();
        String message = applicationContext.getMessage("save", null, locale);
        assertEquals("Save", message);
    }

    @Test
    public final void testMessageWithHebrewLocale() {
        Locale locale = new Locale("iw");
        String message = applicationContext.getMessage("save", null, locale);
        assertEquals("HEBREW Save", message);
        message = applicationContext.getMessage("language", null, locale);
        assertEquals("\u05e9\u05e3\u05d4", message);
    }

    @Test
    public final void testMessageWithChineseLocale() {
        Locale locale = Locale.CHINESE;
        String message = applicationContext.getMessage("save", null, locale);
        assertEquals("CHINESE Save", message);
    }
    
    @Test
    public final void testLocale() {
        for (String country: Locale.getISOCountries()) {
            logger.info("country: |" + country + "|");
        }
        
        for (String lang : Locale.getISOLanguages()) {
            logger.info("lang: |" + lang + "|");
        }
        
        for (Locale locale: Locale.getAvailableLocales()) {
            logger.info("locale = |" + locale + "|");
        }
    }

    /* (non-Javadoc)
     * @see org.springframework.test.AbstractSingleSpringContextTests#getConfigLocations()
     */
    @Override
    protected String[] getConfigLocations() {
        return new String[] {"classpath:/application-context.xml",
                "classpath:/web-servlet.xml"};
    }
}
