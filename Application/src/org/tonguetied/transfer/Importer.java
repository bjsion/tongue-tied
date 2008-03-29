package org.tonguetied.datatransfer;

import org.tonguetied.keywordmanagement.KeywordService;


/**
 * This factory acts as abstraction on top the underlying import method. The 
 * purpose is to hide away the implementation details such that multiple import
 * types can be handled. 
 * 
 * @author bsion
 *
 */
public abstract class Importer {
    private KeywordService keywordService;
    
    /**
     * Create a new instance of Importer.
     * 
     * @param keywordService
     */
    protected Importer(KeywordService keywordService) {
        this.keywordService = keywordService;
    }

    /**
     * Factory method to create the appropriate <code>Importer</code>
     * @param keywordService interface to persistent storage
     * @param formatType the format the import data is in. This disambiguates
     * which type of <code>Importer</code> to use
     * 
     * @return The newly created <code>Importer</code>
     */
    public static final Importer createInstance(KeywordService keywordService,
            FormatType formatType) 
    {
        Importer importer = null;
        switch (formatType) {
            case xls:
            case xlsLanguage:
                importer = new ExcelImporter(keywordService);
                break;
            default:
                break;
        }
        
        return importer;
    }

    /**
     * 
     * @param input raw data to import. This data can be in text or binary 
     * format. This will be specific to the importer, as data can be imported 
     * from different sources.
     */
    abstract void importData(byte[] input);

    /**
     * @return the keywordService
     */
    protected KeywordService getKeywordService() {
        return keywordService;
    }
}
