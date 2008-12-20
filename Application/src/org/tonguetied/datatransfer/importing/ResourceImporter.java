/*
 * Copyright 2008 The Tongue-Tied Authors
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not 
 * use this file except in compliance with the License. You may obtain a copy 
 * of the License at
 *  
 * http://www.apache.org/licenses/LICENSE-2.0 
 * 
 * Unless required by applicable law or agreed to in writing, software 
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT 
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the 
 * License for the specific language governing permissions and limitations 
 * under the License. 
 */
package org.tonguetied.datatransfer.importing;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.apache.commons.io.IOUtils;
import org.tonguetied.datatransfer.importing.ImportException.ImportErrorCode;
import org.tonguetied.keywordmanagement.Bundle;
import org.tonguetied.keywordmanagement.Country;
import org.tonguetied.keywordmanagement.KeywordService;
import org.tonguetied.keywordmanagement.Language;
import org.tonguetied.keywordmanagement.Translation;
import org.tonguetied.keywordmanagement.Country.CountryCode;
import org.tonguetied.keywordmanagement.Language.LanguageCode;
import org.tonguetied.keywordmanagement.Translation.TranslationState;
import org.xml.sax.SAXException;

/**
 * Data importer that handles input in the .Net resource file format. The 
 * resource file is read and entries are transformed into {@link Translation}s
 * and added to the system.
 * 
 * @author bsion
 *
 */
public class ResourceImporter extends AbstractSingleResourceImporter
{
    private static final String LANG_CODE_CHINESE = "zh";

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
    protected void doImport(final byte[] input, final TranslationState state)
            throws ImportException 
    {
        if (logger.isDebugEnabled())
            logger.debug("attempting import");
        ByteArrayInputStream bais = null;
        SAXParserFactory factory = SAXParserFactory.newInstance();
        factory.setValidating(true);
        factory.setNamespaceAware(true);
        try
        {
//            validate(bais);
//            XMLReader reader = XMLReaderFactory.createXMLReader();
//            reader.setContentHandler(new ResourceHandler(
//                    getBundle(), getCountry(), getLanguage(), state, getKeywordService()));
//            // Request validation
//            reader.setFeature("http://xml.org/sax/features/validation", true);
//            InputSource is = new InputSource(bais);
//            reader.parse(is);
                    
            SAXParser parser = factory.newSAXParser();
            
            bais = new ByteArrayInputStream(input);
            parser.parse(bais, new ResourceHandler(
                    getBundle(), getCountry(), getLanguage(), state, getKeywordService()));
        }
        catch (SAXException saxe)
        {
            throw new ImportException(saxe);
        }
        catch (ParserConfigurationException pce)
        {
            throw new ImportException(pce);
        }
        catch (IOException ioe)
        {
            throw new ImportException(ioe);
        }
        finally
        {
            IOUtils.closeQuietly(bais);
        }
    }

//    /**
//     * Validate the xml document.
//     * 
//     * THis doesn;t work at the moment as I have not figured out a way to 
//     * handle an embedded xsd.
//     * 
//     * @param bais
//     * @throws SAXException
//     * @throws IOException
//     */
//    private synchronized void validate(ByteArrayInputStream bais) 
//            throws SAXException,IOException
//    {
//        SchemaFactory schemaFactory = 
//            SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
//        Schema schema = schemaFactory.newSchema();
//        Validator validator = schema.newValidator();
//        InputSource is = new InputSource(bais);
//        Source source = new SAXSource(is);
////            ContentHandler handler = new ValidatorHandler();
////            Result result = new SAXResult(handler);
//        Result result = new SAXResult();
//        validator.validate(source, result);
//    }

    /**
     * Validates the <code>fileName</code> to ensure that the fileName 
     * corresponds to an existing {@link Bundle}, {@link Country} and 
     * {@link Language}.
     */
    @Override
    protected void validate(final String fileName, List<ImportErrorCode> errorCodes)
            throws ImportException
    {
        String[] tokens = fileName.split("\\.|-");
        
        CountryCode countryCode = null;
        LanguageCode languageCode = null;
        switch (tokens.length)
        {
            case 1:
                // this is the default bundle, so no country or language
                countryCode = CountryCode.DEFAULT;
                languageCode = LanguageCode.DEFAULT;
                break;
            case 2:
                if (isCountryCode(tokens[1]))
                {
                    countryCode = ImporterUtils.evaluateCountryCode(tokens[1], errorCodes);
                    languageCode = LanguageCode.DEFAULT;
                }
                else
                {
                    countryCode = CountryCode.DEFAULT;
                    languageCode = ImporterUtils.evaluateLanguageCode(tokens[1], errorCodes);
                }
                break;
            case 3:
                if (LANG_CODE_CHINESE.equals(tokens[1]))
                {
                    if ("CHS".equals(tokens[2]))
                    {
                        countryCode = CountryCode.DEFAULT;
                        languageCode = ImporterUtils.evaluateLanguageCode(LANG_CODE_CHINESE, errorCodes);
                    }
                    else if ("CHT".equals(tokens[2]))
                    {
                        countryCode = CountryCode.DEFAULT;
                        languageCode = ImporterUtils.evaluateLanguageCode("zht", errorCodes);
                    }
                    else
                    {
                        countryCode = ImporterUtils.evaluateCountryCode(tokens[2], errorCodes);
                        languageCode = ImporterUtils.evaluateLanguageCode(tokens[1], errorCodes);
                    }
                }
                else
                {
                    countryCode = ImporterUtils.evaluateCountryCode(tokens[2], errorCodes);
                    languageCode = ImporterUtils.evaluateLanguageCode(tokens[1], errorCodes);
                }
                break;
            default:
                errorCodes.add(ImportErrorCode.invalidNameFormat);
                break;
        }
        
        if (logger.isDebugEnabled())
        {
            logger.debug("bundle name = " + tokens[0]);
        }


        setBundle(getKeywordService().getBundleByResourceName(tokens[0]));
        if (getBundle() == null)
            errorCodes.add(ImportErrorCode.unknownBundle);
        
        this.setCountry(getKeywordService().getCountry(countryCode));
        if (getCountry() == null)
            errorCodes.add(ImportErrorCode.unknownCountry);
        
        this.setLanguage(getKeywordService().getLanguage(languageCode));
        if (getLanguage() == null)
            errorCodes.add(ImportErrorCode.unknownLanguage);
        
        if (!errorCodes.isEmpty())
            logger.warn("Cannot process " + fileName + ". It contains " + 
                    errorCodes.size() + " errors");
    }

    /**
     * Determines if the string component is a country code or not.
     * 
     * @param code the code to evaluate
     * @return <code>true</code> if the string corresponds to a potential 
     * country code, <code>false</code> otherwise
     */
    protected boolean isCountryCode(String code)
    {
        boolean isCountryCode = false;
        if (code != null && !"".equals(code))
            isCountryCode = Character.isUpperCase(code.charAt(0));
        
        return isCountryCode;
    }
}
