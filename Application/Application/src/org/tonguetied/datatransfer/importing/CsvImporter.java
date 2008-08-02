package org.tonguetied.datatransfer.importing;

import org.tonguetied.keywordmanagement.KeywordService;
import org.tonguetied.keywordmanagement.Translation.TranslationState;

/**
 * @author bsion
 *
 */
public class CsvImporter extends Importer {

    /**
     * Create a new instance of CsvImporter.
     * 
     * @param keywordService
     */
    public CsvImporter(KeywordService keywordService) {
        super(keywordService);
        // TODO Auto-generated constructor stub
    }

    /* (non-Javadoc)
     * @see org.tonguetied.datatransfer.Importer#importData(byte[])
     */
    @Override
    protected void doImport(byte[] input, TranslationState state) throws ImportException 
    {
        // TODO Auto-generated method stub
        throw new ImportException("not yet implemented", null);

    }

}
