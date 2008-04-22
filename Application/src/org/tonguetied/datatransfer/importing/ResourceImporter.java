package org.tonguetied.datatransfer.importing;

import org.tonguetied.keywordmanagement.KeywordService;
import org.tonguetied.keywordmanagement.Translation.TranslationState;

/**
 * @author bsion
 *
 */
public class ResourceImporter extends Importer {

    /**
     * Create a new instance of ResourceImporter.
     * 
     * @param keywordService
     */
    public ResourceImporter(KeywordService keywordService) {
        super(keywordService);
    }

    /* (non-Javadoc)
     * @see org.tonguetied.datatransfer.importing.Importer#doImport(byte[], org.tonguetied.keywordmanagement.Translation.TranslationState)
     */
    @Override
    protected void doImport(byte[] input, TranslationState state)
            throws ImportException 
    {
        throw new ImportException("not yet implemented", null);
    }

}
