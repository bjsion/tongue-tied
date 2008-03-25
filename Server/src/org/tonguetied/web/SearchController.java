package org.tonguetied.web;

import static org.tonguetied.web.Constants.BUNDLES;
import static org.tonguetied.web.Constants.COUNTRIES;
import static org.tonguetied.web.Constants.LANGUAGES;
import static org.tonguetied.web.Constants.SHOW_ALL;
import static org.tonguetied.web.Constants.STATES;
import static org.tonguetied.web.Constants.VIEW_PREFERENCES;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.validation.BindException;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;
import org.tonguetied.domain.Bundle;
import org.tonguetied.domain.Country;
import org.tonguetied.domain.Language;
import org.tonguetied.domain.Translation.TranslationState;
import org.tonguetied.service.ApplicationService;


/**
 * Controller for processing keyword / translation search requests.
 * 
 * @author bsion
 *
 */
public class SearchController extends SimpleFormController {

    private ApplicationService appService;
    private PreferenceForm viewPreferences;
    private SearchForm searchParameters;
    
    private static final Logger logger = 
        Logger.getLogger(SearchController.class);

    /**
     * Create new instance of SearchController 
     */
    public SearchController() {
        setCommandClass(SearchForm.class);
    }
    
    @Override
    protected Object formBackingObject(HttpServletRequest request) 
            throws Exception {
        return searchParameters;
    }

    @Override
    protected ModelAndView onSubmit(HttpServletRequest request, 
                                    HttpServletResponse response,
                                    Object command,
                                    BindException errors) throws Exception {
        if (logger.isDebugEnabled()) 
            logger.debug("searching for keywords");
        
        request.getSession().setAttribute(SHOW_ALL, false);

        return new ModelAndView(getSuccessView());
    }

    @Override
    protected Map<String, Object> referenceData(HttpServletRequest request)
            throws Exception 
    {
        Map<String, Object> model = new HashMap<String, Object>();
        model.put(LANGUAGES, appService.getLanguages());
        model.put(BUNDLES, appService.getBundles());
        model.put(COUNTRIES, appService.getCountries());
        model.put(STATES, TranslationState.values());
        model.put(VIEW_PREFERENCES, viewPreferences);
        
        return model;
    }

    @Override
    protected void initBinder(HttpServletRequest request,
                              ServletRequestDataBinder binder) 
            throws Exception {
        binder.registerCustomEditor(Language.class,  
                new LanguageSupport(appService.getLanguages()));
        binder.registerCustomEditor(Bundle.class, 
                new BundleSupport(appService.getBundles()));
        binder.registerCustomEditor(Country.class, 
                new CountrySupport(appService.getCountries()));
        binder.registerCustomEditor(TranslationState.class, 
                new TranslationStateSupport());
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
    }
    
    /**
     * Assign the {@link ApplicationService}.
     * 
     * @param appService the {@link ApplicationService} to set.
     */
    public void setAppService(ApplicationService appService) {
        this.appService = appService;
    }
    
    /**
     * Assign the {@link PreferenceForm}.
     * 
     * @param viewPreferences the viewPreferences to set
     */
    public void setViewPreferences(PreferenceForm viewPreferences) {
        this.viewPreferences = viewPreferences;
    }
    
    /**
     * Assign the {@link SearchForm}.
     * 
     * @param searchParameters the searchParameters to set
     */
    public void setSearchParameters(SearchForm searchParameters) {
        this.searchParameters = searchParameters;
    }
}
