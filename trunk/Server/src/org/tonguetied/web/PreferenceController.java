package org.tonguetied.web;

import static org.tonguetied.web.Constants.LANGUAGES;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.propertyeditors.CustomCollectionEditor;
import org.springframework.validation.BindException;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;
import org.tonguetied.domain.Bundle;
import org.tonguetied.domain.Country;
import org.tonguetied.domain.Language;
import org.tonguetied.service.ApplicationService;


/**
 * @author bsion
 *
 */
public class PreferenceController extends SimpleFormController {

    private ApplicationService appService;
    private PreferenceForm viewPreferences;
    
    private static final Logger logger = 
        Logger.getLogger(PreferenceController.class);

    public PreferenceController() {
        setCommandClass(PreferenceForm.class);
    }
    
    @Override
    protected Object formBackingObject(HttpServletRequest request) 
            throws Exception {
        return viewPreferences;
    }

    @Override
    protected ModelAndView onSubmit(HttpServletRequest request, 
                                    HttpServletResponse response,
                                    Object command,
                                    BindException errors) throws Exception {
        if (logger.isDebugEnabled()) 
            logger.debug("setting view preferences");

        return new ModelAndView(getSuccessView());
    }

    @Override
    protected Map<String, Object> referenceData(HttpServletRequest request) 
            throws Exception 
    {
        Map<String, Object> model = new HashMap<String, Object>();
        model.put(LANGUAGES, appService.getLanguages());
        
        return model;
    }

    @Override
    protected void initBinder(HttpServletRequest request,
                              ServletRequestDataBinder binder) 
            throws Exception {
        binder.registerCustomEditor(List.class, "selectedLanguages", new CustomCollectionEditor(List.class){
            @Override
            protected Object convertElement(Object element) {
                Language language = null;
                if (element != null) {
                    Long id = new Long((String)element);
                    language = appService.getLanguage(id);
                }
                return language;
            }
        });
        binder.registerCustomEditor(List.class, "selectedCountries", new CustomCollectionEditor(List.class){
            @Override
            protected Object convertElement(Object element) {
                Country country = null;
                if (element != null) {
                    Long id = new Long((String)element);
                    country = appService.getCountry(id);
                }
                return country;
            }
        });
        binder.registerCustomEditor(List.class, "selectedBundles", new CustomCollectionEditor(List.class){
            @Override
            protected Object convertElement(Object element) {
                Bundle bundle = null;
                if (element != null) {
                    Long id = new Long((String)element);
                    bundle = appService.getBundle(id);
                }
                return bundle;
            }
        });
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
}
