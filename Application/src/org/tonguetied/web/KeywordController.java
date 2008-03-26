package org.tonguetied.web;

import static org.tonguetied.web.Constants.BUNDLES;
import static org.tonguetied.web.Constants.COUNTRIES;
import static org.tonguetied.web.Constants.COUNTRY;
import static org.tonguetied.web.Constants.LANGUAGE;
import static org.tonguetied.web.Constants.LANGUAGES;
import static org.tonguetied.web.Constants.STATES;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.validation.BindException;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.CancellableFormController;
import org.tonguetied.domain.Bundle;
import org.tonguetied.domain.Country;
import org.tonguetied.domain.Keyword;
import org.tonguetied.domain.KeywordFactory;
import org.tonguetied.domain.Language;
import org.tonguetied.domain.Translation;
import org.tonguetied.domain.Country.CountryCode;
import org.tonguetied.domain.Language.LanguageCode;
import org.tonguetied.domain.Translation.TranslationState;
import org.tonguetied.service.ApplicationService;


/**
 * Manage the submission of {@link Keyword}s. This controller is responsible 
 * for either creating a new {@link Keyword} or saving an existing one. 
 * {@link Keyword}s can be created either by {@link Language} or by 
 * {@link Country}
 *  
 * @author bsion
 * @see KeywordFactory#createKeyword(java.util.List, Country)
 * @see KeywordFactory#createKeyword(java.util.List, Language)
 */
public class KeywordController extends CancellableFormController {
    
    private ApplicationService appService;
    
    private static final Logger logger = 
        Logger.getLogger(KeywordController.class);

    /**
     * Create new instance of KeywordController 
     */
    public KeywordController() {
        setCommandClass(Keyword.class);
    }

    @Override
    protected Object formBackingObject(HttpServletRequest request) 
            throws Exception {
        String stringId = request.getParameter("keywordId");
        Long id = null;
        if (stringId != null)
            id = Long.parseLong(stringId);
        Keyword keyword = appService.getKeyword(id);
        if (keyword == null) {
            String creationType = request.getParameter("creationType");
            if (LANGUAGE.equals(creationType)) {
                keyword = 
                    KeywordFactory.createKeyword(appService.getLanguages(), 
                        appService.getCountry(CountryCode.DEFAULT));
            }
            else if (COUNTRY.equals(creationType)) {
                keyword = 
                    KeywordFactory.createKeyword(appService.getCountries(), 
                        appService.getLanguage(LanguageCode.DEFAULT));
            } 
        }
        
        return keyword;
    }

    @Override
    protected ModelAndView onSubmit(HttpServletRequest request, 
                                    HttpServletResponse response,
                                    Object command,
                                    BindException errors) throws Exception {
        if (logger.isDebugEnabled()) logger.debug("saving keyword");
        Keyword keyword = (Keyword) command;
        
        ModelAndView modelAndView;
        if (request.getParameter("add") != null) {
            modelAndView = addTranslation(request, response, errors, keyword);
        }
        else if (request.getParameter("delete") != null) {
            modelAndView = deleteKeyword(keyword);
        }
        else if (request.getParameter("deleteTranslation") != null) {
            modelAndView = 
                deleteTranslation(request, response, errors, keyword);
        }
        else {
            modelAndView = saveKeyword(keyword);
        }
        
        return modelAndView;
    }

    @Override
    protected ModelAndView onCancel(HttpServletRequest request, 
                                    HttpServletResponse response,
                                    Object command) throws Exception {
        return new ModelAndView(getCancelView());
    }
    
    @Override
    protected Map<String, Object> referenceData(HttpServletRequest request) 
            throws Exception 
    {
        Map<String, Object> model = new HashMap<String, Object>();
        model.put(LANGUAGES, appService.getLanguages());
        model.put(COUNTRIES, appService.getCountries());
        model.put(BUNDLES, appService.getBundles());
        model.put(STATES, TranslationState.values());
        
        return model;
    }

    @Override
    protected void initBinder(HttpServletRequest request,
                              ServletRequestDataBinder binder) 
            throws Exception {
        binder.registerCustomEditor(Language.class,  
                new LanguageSupport(appService.getLanguages()));
        binder.registerCustomEditor(Country.class, 
                new CountrySupport(appService.getCountries()));
        binder.registerCustomEditor(Bundle.class, 
                new BundleSupport(appService.getBundles()));
        binder.registerCustomEditor(TranslationState.class, 
                new TranslationStateSupport());
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
    }
    
    private ModelAndView saveKeyword(Keyword keyword) {
        appService.saveOrUpdate(keyword);
        
        return new ModelAndView(getSuccessView());
    }
    
    private ModelAndView deleteKeyword(Keyword keyword) {
        appService.delete(keyword);
        
        return new ModelAndView(getSuccessView());
    }

    private ModelAndView addTranslation(HttpServletRequest request,
                                        HttpServletResponse response,
                                        BindException errors,
                                        Keyword keyword)
            throws Exception 
    {
        keyword.addTranslation(new Translation());
        
        return showForm(request, response, errors);
    }
    
    private ModelAndView deleteTranslation(HttpServletRequest request,
                                           HttpServletResponse response,
                                           BindException errors,
                                           Keyword keyword)
            throws Exception
    {
        Long translationId = 
            Long.valueOf(request.getParameter("deleteTranslation"));
        keyword.removeTranslation(translationId);
        return showForm(request, response, errors);
    }

    /**
     * Assign the {@link ApplicationService}.
     * 
     * @param appService the {@link ApplicationService} to set.
     */
    public void setAppService(ApplicationService appService) {
        this.appService = appService;
    }
}
