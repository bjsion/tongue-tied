package org.tonguetied.datatransfer;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.apache.poi.hssf.eventusermodel.HSSFEventFactory;
import org.apache.poi.hssf.eventusermodel.HSSFListener;
import org.apache.poi.hssf.eventusermodel.HSSFRequest;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.tonguetied.keywordmanagement.Keyword;
import org.tonguetied.keywordmanagement.KeywordService;
import org.tonguetied.keywordmanagement.Translation;
import org.tonguetied.keywordmanagement.Translation.TranslationState;


/**
 * Data importer that handles input in excel format. The excel file is read and
 * rows are transformed into {@link Translation}s  
 * 
 * @author bsion
 *
 */
public class ExcelImporter extends Importer {

    /**
     * Create a new instance of ExcelImporter.
     * 
     * @param keywordService the interface to keyword functions
     */
    protected ExcelImporter(KeywordService keywordService) {
        super(keywordService);
    }

    @Override
    public void doImport(byte[] input, TranslationState state) throws ImportException {
        ExcelDataParser parser = new ExcelDataParser(getKeywordService());
        loadData(parser, input);
        doImport(parser.getKeywords());
    }

    /**
     * This method initializes the parser enabling the parser to handle the
     * excel document.
     * 
     * @param parser the type of excel parser to use
     * @param input the byte code representation of the excel document 
     * @throws ImportException if the input data fails to be parsed
     */
    private void loadData(HSSFListener parser, byte[] input) throws ImportException {
        ByteArrayInputStream bais = null;
        InputStream dis = null;
        try {
            bais = new ByteArrayInputStream(input);
            // create a new org.apache.poi.poifs.filesystem.Filesystem
            POIFSFileSystem poifs = new POIFSFileSystem(bais);
            // get the Workbook (excel part) stream in a InputStream
            dis = poifs.createDocumentInputStream("Workbook");
            // construct out HSSFRequest object
            HSSFRequest req = new HSSFRequest();
            // lazy listen for ALL records with the listener shown above
            req.addListenerForAllRecords(parser);
            // create our event factory
            HSSFEventFactory factory = new HSSFEventFactory();
            // process our events based on the document input stream
            factory.processEvents(req, dis);
        } catch (IOException ioe) {
            throw new ImportException(ioe);
        } finally {
            // and our document input stream (don't want to leak these!)
            close(dis);
            // once all the events are processed close our file input stream
            close(bais);
        }
    }

    /**
     * Perform the actual import of data into persistent storage.
     * 
     * @param keywords the map of {@link Keyword}s to import.
     */
    private void doImport(Map<String, Keyword> keywords) {
        Keyword keyword;
        for (Map.Entry<String, Keyword> entry : keywords.entrySet()) {
            keyword = getKeywordService().getKeyword(entry.getKey());
            if (keyword == null) {
                keyword = entry.getValue();
            }
            else {
                Keyword reference = keywords.get(entry.getKey());
                // find translation by business key
                Predicate predicate; 
                Translation translation; 
                for (Translation refTranslation : reference.getTranslations()) {
                    predicate = new TranslationPredicate(
                            refTranslation.getBundle(), 
                            refTranslation.getCountry(), 
                            refTranslation.getLanguage());
                    translation = 
                        (Translation) CollectionUtils.find(
                                keyword.getTranslations(), predicate);
                    if (translation == null) {
                        keyword.addTranslation(refTranslation);
                    }
                    else {
                        translation.setValue(refTranslation.getValue());
                    }
                }
            }
            
            getKeywordService().saveOrUpdate(keyword);
        }
    }

    /**
     * Helper method to safely close an {@link InputStream}.
     * 
     * @param is the {@link InputStream} to close
     * @throws ImportException if the stream fails to close
     */
    private void close(InputStream is) {
        try {
            if (is != null)
                is.close();
        }
        catch (IOException ioe) {
            throw new ImportException(ioe);
        }
    }
}
