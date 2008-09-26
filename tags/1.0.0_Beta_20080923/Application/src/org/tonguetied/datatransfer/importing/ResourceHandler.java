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

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.apache.log4j.Logger;
import org.tonguetied.keywordmanagement.Bundle;
import org.tonguetied.keywordmanagement.Country;
import org.tonguetied.keywordmanagement.Keyword;
import org.tonguetied.keywordmanagement.KeywordService;
import org.tonguetied.keywordmanagement.Language;
import org.tonguetied.keywordmanagement.Translation;
import org.tonguetied.keywordmanagement.Translation.TranslationState;
import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

/**
 * This class is used to parse a .Net resource file (.resx) when importing data
 * into the system.
 * 
 * @author bsion
 *
 */
public class ResourceHandler extends DefaultHandler
{
    private static final String ELEMENT_VALUE = "value";
    private static final String ELEMENT_RESHEADER = "resheader";
    private static final String ELEMENT_COMMENT = "comment";
    private static final String ELEMENT_DATA = "data";
    private static final Logger logger = Logger.getLogger(ResourceHandler.class);
    
    private final Bundle bundle;
    private final Country country;
    private final Language language;
    private final KeywordService keywordService;
    private final TranslationState state;
    private Keyword keyword;
    private Translation translation;
    private String parent;
    private Predicate predicate;
    private StringBuilder chars = new StringBuilder();
    private int keywordCount;

    
    /**
     * Create a new instance of ResourceHandler.
     *
     * @param bundle the {@link Bundle} the imported translations belong to
     * @param country the {@link Country} the imported translations are set to
     * @param language the {@link Language} the imported translations are set 
     * to
     * @param state the {@link TranslationState} to assign to the imported 
     * translations
     * @param keywordService the interface to the keyword service layer
     */
    public ResourceHandler(final Bundle bundle, final Country country, 
            final Language language, final TranslationState state, 
            final KeywordService keywordService)
    {
        this.bundle = bundle;
        this.country = country;
        this.language = language;
        this.state = state;
        this.keywordService = keywordService;
        predicate = new TranslationPredicate(bundle, country, language);
        this.keywordCount = 0;
    }

    /* (non-Javadoc)
     * @see org.xml.sax.helpers.DefaultHandler#startElement(java.lang.String, java.lang.String, java.lang.String, org.xml.sax.Attributes)
     */
    @Override
    public void startElement(String uri, String localName, String name,
            Attributes attributes)
    {
        // reset the character array
        chars.setLength(0);
        if (ELEMENT_RESHEADER.equalsIgnoreCase(name))
        {
            parent = name;
        }
        else if (ELEMENT_DATA.equalsIgnoreCase(name))
        {
            final String keywordStr = attributes.getValue("name");
            keyword = keywordService.getKeyword(keywordStr);
            if (keyword == null) {
                keyword = new Keyword();
                keyword.setKeyword(keywordStr);
                translation = 
                    new Translation(bundle, country, language, null, state);
                keyword.addTranslation(translation);
            }
            else {
                translation = (Translation) CollectionUtils.find(
                        keyword.getTranslations(), predicate);
            }
            parent = name;
        }
    }

    /* (non-Javadoc)
     * @see org.xml.sax.helpers.DefaultHandler#endElement(java.lang.String, java.lang.String, java.lang.String)
     */
    @Override
    public void endElement(String uri, String localName, String name)
    {
        if (ELEMENT_RESHEADER.equalsIgnoreCase(name))
        {
            parent = null;
        }
        else if (ELEMENT_DATA.equalsIgnoreCase(name))
        {
            keywordService.saveOrUpdate(keyword);
            translation = null;
            keyword = null;
            parent = null;
        }
        else if (ELEMENT_COMMENT.equalsIgnoreCase(name))
        {
            final String comment = getCharacters();
            if (keyword.getContext() == null && comment != null)
                keyword.setContext(comment);
        }
        else if (ELEMENT_VALUE.equalsIgnoreCase(name) && ELEMENT_DATA.equalsIgnoreCase(parent))
        {
            if (translation == null)
            {
                translation = 
                    new Translation(bundle, country, language, getCharacters(), state);
                keyword.addTranslation(translation);
            }
            else {
                translation.setState(state);
                translation.setValue(getCharacters());
            }
        }
    }

    /* (non-Javadoc)
     * @see org.xml.sax.helpers.DefaultHandler#endDocument()
     */
    @Override
    public void endDocument()
    {
        if (logger.isInfoEnabled())
            logger.info("processed " + keywordCount + " translations");
    }

    /* (non-Javadoc)
     * @see org.xml.sax.helpers.DefaultHandler#characters(char[], int, int)
     */
    @Override
    public void characters(char[] ch, int start, int length)
    {
        chars.append(ch, start, length);
    }

    /**
     * Return the current buffer of characters.
     * 
     * @return the string value of the char buffer
     */
    private String getCharacters()
    {
        String value = chars.toString();
        chars.setLength(0);
        
        return "".equals(value)? null: value;
    }
}
