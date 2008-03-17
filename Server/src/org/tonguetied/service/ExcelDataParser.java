package org.tonguetied.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.eventusermodel.HSSFListener;
import org.apache.poi.hssf.record.BOFRecord;
import org.apache.poi.hssf.record.BoundSheetRecord;
import org.apache.poi.hssf.record.LabelSSTRecord;
import org.apache.poi.hssf.record.NumberRecord;
import org.apache.poi.hssf.record.Record;
import org.apache.poi.hssf.record.RowRecord;
import org.apache.poi.hssf.record.SSTRecord;
import org.tonguetied.dao.DaoRepository;
import org.tonguetied.domain.Bundle;
import org.tonguetied.domain.Country;
import org.tonguetied.domain.Keyword;
import org.tonguetied.domain.Language;
import org.tonguetied.domain.Translation;
import org.tonguetied.domain.Country.CountryCode;
import org.tonguetied.domain.Language.LanguageCode;
import org.tonguetied.domain.Translation.TranslationState;


/**
 * This class basically acts as an excel spreadsheet parser for generating a 
 * {@link Keyword} map. All cells are parsed and a map of {@link Keyword}s with
 * {@link Translation}s are built. This {@link Map} can then be processed 
 * accordingly.
 * 
 * @author bsion
 *
 */
public class ExcelDataParser implements HSSFListener {
    private SSTRecord sstrec;
    private List<Language> languages;
    private Map<String, Keyword> keywords;
    private Keyword keyword;
    private Translation baseTranslation;
    private short lastColOfRow;
    private DaoRepository daoRepository;
    
    private static final Logger logger = Logger.getLogger(ExcelDataParser.class);

    /**
     * Create a new instance of ExcelDataParser.
     */
    public ExcelDataParser(DaoRepository daoRepository) {
        this.languages = new ArrayList<Language>();
        this.keywords = new HashMap<String, Keyword>();
        this.daoRepository = daoRepository;
    }

    /**
     * This method listens for incoming records and handles them as required.
     * 
     * @param record    The record that was found while reading.
     */
    public void processRecord(Record record) {
        if (record == null) {
            if (logger.isInfoEnabled()) logger.info("no record to process");
        }
        else {
            switch (record.getSid())
            {
                // the BOFRecord can represent either the beginning of a sheet 
                // or the workbook
                case BOFRecord.sid:
                    BOFRecord bof = (BOFRecord) record;
                    if (bof.getType() == BOFRecord.TYPE_WORKBOOK) {
                        if (logger.isInfoEnabled())
                            logger.info("Processing excel workbook");
                        // assigned to the class level member
                    } 
                    else if (bof.getType() == BOFRecord.TYPE_WORKSHEET) {
                        if (logger.isInfoEnabled())
                            logger.info("recordsize = " + bof.getRecordSize() + 
                                    ", required version = " +
                                    bof.getRequiredVersion());
                    }
                    break;
                case BoundSheetRecord.sid:
                    BoundSheetRecord bsr = (BoundSheetRecord) record;
                    // sheets named have no impact on generating query
                    if (logger.isDebugEnabled()) 
                        logger.debug("processing sheet: "+ bsr.getSheetname());
                    break;
                case RowRecord.sid:
                    RowRecord rowrec = (RowRecord) record;
                    lastColOfRow = rowrec.getLastCol();
//                    if (rowrec.getRowNumber() > 0) {
//                        if (logger.isDebugEnabled())
//                            logger.debug("creating new keyword instance");
//                        keyword = new Keyword();
//                    }
                    break;
                case NumberRecord.sid:
                    NumberRecord numrec = (NumberRecord) record;
                    logger.warn("Cell [" + numrec.getRow() + "," + 
                            numrec.getColumn() + 
                            "] expecting a string value not numeric: " + 
                            numrec.getValue() + ". Ignoring value");
                    break;
                case SSTRecord.sid:
                    // SSTRecords store a array of unique strings used in Excel.
                    sstrec = (SSTRecord) record;
                    if (logger.isDebugEnabled()) {
                        logger.debug("file contains " + 
                            sstrec.getNumUniqueStrings() + " unique strings");
                    }
                    break;
                case LabelSSTRecord.sid:
                    LabelSSTRecord lrec = (LabelSSTRecord) record;
                    if (lrec.getRow() == 0) { 
                        processHeader(lrec);
                    }
                    else {
                        if (lrec.getColumn() == 0) {
                            String keywordStr = 
                                sstrec.getString(lrec.getSSTIndex());
                            loadKeyword(keywordStr);
                        }
                        else if (lrec.getColumn() == 1) {
                            keyword.setContext(
                                    sstrec.getString(lrec.getSSTIndex()));
                        }
                        else if (lrec.getColumn() == 2) {
                            baseTranslation = new Translation();
                            baseTranslation.setKeyword(keyword);
                            String name = sstrec.getString(lrec.getSSTIndex());
                            Bundle bundle = daoRepository.getBundle(name);
                            baseTranslation.setBundle(bundle);
                        }
                        else if (lrec.getColumn() == 3) {
                            String colHeader = 
                                sstrec.getString(lrec.getSSTIndex());
                            String[] headers = colHeader.split(":");
                            CountryCode code = CountryCode.valueOf(headers[0]);
                            Country country = daoRepository.getCountry(code);
                            baseTranslation.setCountry(country);
                        }
                        else {
                            Language language = 
                                languages.get(lrec.getColumn()-4);
                            String value = sstrec.getString(lrec.getSSTIndex());
                            try {
                                Translation translation = 
                                    baseTranslation.clone();
                                if (language.getCode() == LanguageCode.zht) {
                                    language = 
                                        daoRepository.getLanguage(LanguageCode.zh);
                                    Country country = daoRepository.getCountry(CountryCode.TW);
                                    translation.setCountry(country);
                                }
                                translation.setLanguage(language);
                                translation.setState(TranslationState.UNVERIFIED);
                                translation.setValue(value);
                                keyword.addTranslation(translation);
                            }
                            catch (CloneNotSupportedException cnse) {
                                throw new ImportException(cnse);
                            }
//                        System.out.println("String cell found with value "
//                                + sstrec.getString(lrec.getSSTIndex()));
                        }
                        
                        if (isLastColumn(lrec.getColumn())) {
                            keywords.put(keyword.getKeyword(), keyword);
                        }
                    }
                    break;
            }
        }       
    }

    /**
     * @param keywordStr
     */
    private void loadKeyword(String keywordStr) {
        keyword = keywords.get(keywordStr);
        if (keyword == null) {
            if (logger.isDebugEnabled())
                logger.debug("creating new keyword instance");
            keyword = new Keyword();
            keyword.setKeyword(keywordStr);
        }
    }

    /**
     * Column 0 keyword
     * Column 1 context
     * Column 2 Bundle
     * Column 3 Country
     * Column 4..n Languages
     * @param lrec
     */
    private void processHeader(LabelSSTRecord lrec) {
        if (lrec.getColumn() > 3) {
            String colHeader = sstrec.getString(lrec.getSSTIndex());
            String[] headers = colHeader.split(":");
            LanguageCode code = LanguageCode.valueOf(headers[0]);
            Language language;
            if (LanguageCode.zht == code) {
                language = new Language();
                language.setCode(code);
                language.setName("Traditional Chinese");
            }
            else {
                language = daoRepository.getLanguage(code);
            }
            languages.add(language);
        }
    }

    private boolean isLastColumn(short columnNum) {
        return lastColOfRow-1 == columnNum;
    }

    /**
     * @return the list of {@link Language}s used in this file, or an empty 
     * list if no {@link Language}s were specified
     */
    protected List<Language> getLanguages() {
        return this.languages;
    }

    /**
     * @return map of {@link Keyword}s generated from the parsing, using the 
     * keyword as the map key. An empty map is returned if no 
     * {@linkplain Keyword}s were generated
     */
    public Map<String, Keyword> getKeywords() {
        return keywords;
    }
}
