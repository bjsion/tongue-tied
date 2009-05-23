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
package org.tonguetied.web;

import static org.displaytag.tags.TableTagParameters.PARAMETER_PAGE;
import static org.tonguetied.web.Constants.BUNDLES;
import static org.tonguetied.web.Constants.COUNTRIES;
import static org.tonguetied.web.Constants.COUNTRY;
import static org.tonguetied.web.Constants.KEYWORD_ID;
import static org.tonguetied.web.Constants.LANGUAGE;
import static org.tonguetied.web.Constants.LANGUAGES;
import static org.tonguetied.web.Constants.STATES;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.displaytag.util.ParamEncoder;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.validation.BindException;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.CancellableFormController;
import org.tonguetied.keywordmanagement.Bundle;
import org.tonguetied.keywordmanagement.Country;
import org.tonguetied.keywordmanagement.Keyword;
import org.tonguetied.keywordmanagement.KeywordFactory;
import org.tonguetied.keywordmanagement.KeywordService;
import org.tonguetied.keywordmanagement.Language;
import org.tonguetied.keywordmanagement.Translation;
import org.tonguetied.keywordmanagement.TranslationPredicate;
import org.tonguetied.keywordmanagement.Country.CountryCode;
import org.tonguetied.keywordmanagement.Language.LanguageCode;
import org.tonguetied.keywordmanagement.Translation.TranslationState;


/**
 * Manage the submission of {@link Keyword}s. This controller is responsible 
 * for either creating a new {@link Keyword} or saving an existing one. 
 * {@link Keyword}s can be created either by {@link Language} or by 
 * {@link Country}
 *  
 * @author bsion
 * @see KeywordFactory#createKeyword(java.util.List, Country, Bundle)
 * @see KeywordFactory#createKeyword(java.util.List, Language, Bundle)
 */
public class KeywordController extends CancellableFormController
{
    
private KeywordService keywordService;
    
    private static final Logger logger = 
        Logger.getLogger(KeywordController.class);

    /**
     * Create new instance of KeywordController 
     */
    public KeywordController()
    {
        setCommandClass(Keyword.class);
    }

    @Override
    protected Object formBackingObject(HttpServletRequest request) 
            throws Exception
    {
        final Long id = RequestUtils.getLongParameter(request, KEYWORD_ID);
        Keyword keyword = keywordService.getKeyword(id);
        if (keyword == null)
        {
            final String creationType = request.getParameter("creationType");
            final Bundle bundle = keywordService.getDefaultBundle();
            if (LANGUAGE.equals(creationType))
            {
                keyword = 
                    KeywordFactory.createKeyword(keywordService.getLanguages(), 
                        keywordService.getCountry(CountryCode.DEFAULT), bundle);
            }
            else if (COUNTRY.equals(creationType))
            {
                keyword = 
                    KeywordFactory.createKeyword(keywordService.getCountries(), 
                        keywordService.getLanguage(LanguageCode.DEFAULT), bundle);
            } 
            else
            {
                // catch all if neither is specified. This is a rare occurence
                // and generally will not occur. This case is only to prevent
                // NPE when rendering the form
                keyword = new Keyword();
            }
        }
        
        return keyword;
    }

    @Override
    protected ModelAndView onSubmit(HttpServletRequest request, 
                                    HttpServletResponse response,
                                    Object command,
                                    BindException errors) throws Exception
    {
        if (logger.isDebugEnabled()) logger.debug("saving keyword");
        Keyword keyword = (Keyword) command;
        
        ModelAndView modelAndView;
        if (request.getParameter("add") != null)
        {
            modelAndView = addTranslation(request, response, errors, keyword);
        }
        else if (request.getParameter("delete") != null)
        {
            modelAndView = deleteKeyword(keyword);
        }
        else if (request.getParameter("deleteTranslation") != null)
        {
            modelAndView = 
                deleteTranslation(request, response, errors, keyword);
        }
        else
        {
            modelAndView = saveKeyword(keyword);
        }
        
        return modelAndView;
    }

    @Override
    protected ModelAndView onCancel(HttpServletRequest request, 
                                    HttpServletResponse response,
                                    Object command) throws Exception
    {
        Map<String, Object> model = new HashMap<String, Object>();
        final String pageParam = 
            new ParamEncoder("keyword").encodeParameterName(PARAMETER_PAGE);
        model.put(pageParam, request.getParameter(pageParam));
        return new ModelAndView(getCancelView(), model);
    }
    
    @Override
    protected Map<String, Object> referenceData(HttpServletRequest request) 
            throws Exception 
    {
        Map<String, Object> model = new HashMap<String, Object>();
        model.put(LANGUAGES, keywordService.getLanguages());
        model.put(COUNTRIES, keywordService.getCountries());
        model.put(BUNDLES, keywordService.getBundles());
        model.put(STATES, TranslationState.values());
        
        return model;
    }

    @Override
    protected void initBinder(HttpServletRequest request,
                              ServletRequestDataBinder binder) 
            throws Exception
    {
        binder.registerCustomEditor(Language.class,  
                new LanguageSupport(keywordService.getLanguages()));
        binder.registerCustomEditor(Country.class, 
                new CountrySupport(keywordService.getCountries()));
        binder.registerCustomEditor(Bundle.class, 
                new BundleSupport(keywordService.getBundles()));
        binder.registerCustomEditor(TranslationState.class, 
                new TranslationStateSupport());
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
    }
    
    private ModelAndView saveKeyword(Keyword keyword)
    {
        keywordService.saveOrUpdate(keyword);
        
        return new ModelAndView(getSuccessView());
    }
    
    private ModelAndView deleteKeyword(Keyword keyword)
    {
        keywordService.delete(keyword);
        
        return new ModelAndView(getSuccessView());
    }

    private ModelAndView addTranslation(HttpServletRequest request,
                                        HttpServletResponse response,
                                        BindException errors,
                                        Keyword keyword)
            throws Exception 
    {
        KeywordValidator validator = (KeywordValidator) getValidator();
        validator.validateDuplicates(keyword.getTranslations(), new TranslationPredicate(null, null, null), errors);
        if (!errors.hasErrors())
        {
            keyword.addTranslation(new Translation());
            keywordService.saveOrUpdate(keyword);
        }
        
        return new ModelAndView("redirect:/keyword.htm?keywordId="+keyword.getId());
    }
    
    /**
     * Perform steps to remove a translation from the current keyword.
     */
    private ModelAndView deleteTranslation(HttpServletRequest request,
                                           HttpServletResponse response,
                                           BindException errors,
                                           Keyword keyword)
            throws Exception
    {
        final Long translationId = 
            RequestUtils.getLongParameter(request, "deleteTranslation");
        keyword.removeTranslation(translationId);
        keywordService.saveOrUpdate(keyword);

        return new ModelAndView("redirect:/keyword.htm?keywordId="+keyword.getId());
    }

    /**
     * Assign the {@link KeywordService}.
     * 
     * @param keywordService the {@link KeywordService} to set.
     */
    public void setKeywordService(final KeywordService keywordService)
    {
        this.keywordService = keywordService;
    }
}
