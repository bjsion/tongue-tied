package org.tonguetied.datatransfer.exporting;

import org.tonguetied.datatransfer.common.FormatType;


/**
 * Factory to create an {@link ExportDataPostProcessor}.
 * 
 * @author bsion
 *
 */
public class ExportDataPostProcessorFactory {
    
    /**
     * Factory method to create the appropriate <code>ExportDataPostProcessor</code>.
     * 
     * @param formatType the input format of the data to process
     * @return The newly created <code>ExportDataPostProcessor</code>
     */
    public static final ExportDataPostProcessor getPostProcessor(FormatType formatType) 
    {
        ExportDataPostProcessor postProcessor = null;
        
        switch (formatType) {
            case xlsLanguage:
                postProcessor = new LanguageCentricProcessor(); 
                break;
            default:
                postProcessor = null;
                break;
        }
        
        return postProcessor;
    }

}
