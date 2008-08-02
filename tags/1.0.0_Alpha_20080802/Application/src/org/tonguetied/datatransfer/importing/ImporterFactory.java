package org.tonguetied.datatransfer.importing;

import org.tonguetied.datatransfer.common.FormatType;
import org.tonguetied.keywordmanagement.KeywordService;

/**
 * Factory to create an {@link Importer}.
 * 
 * @author bsion
 *
 */
public class ImporterFactory {
    
    /**
     * Factory method to create the appropriate <code>Importer</code>.
     * 
     * @param formatType the input format of the data to process
     * @param keywordService interface to persistent storage
     * @return The newly created <code>Importer</code>
     */
    public static final Importer getImporter(
            FormatType formatType, KeywordService keywordService) 
    {
        Importer importer = null;
        
        switch (formatType) {
            case csv:
                importer = new CsvImporter(keywordService);
                break;
            case properties:
                importer = new PropertiesImporter(keywordService);
                break;
            case resx:
                importer = new ResourceImporter(keywordService);
                break;
            case xls:
            case xlsLanguage:
                importer = new ExcelImporter(keywordService);
                break;
        }
        
        return importer;
    }
}
