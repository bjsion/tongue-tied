package org.tonguetied.web;

import static org.tonguetied.web.Constants.*;
import static org.tonguetied.web.Constants.COUNTRIES;
import static org.tonguetied.web.Constants.FORMAT_TYPES;
import static org.tonguetied.web.Constants.LANGUAGES;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.validation.BindException;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.CancellableFormController;
import org.tonguetied.datatransfer.DataService;
import org.tonguetied.datatransfer.common.ExportParameters;
import org.tonguetied.datatransfer.common.FormatType;
import org.tonguetied.keywordmanagement.Bundle;
import org.tonguetied.keywordmanagement.Country;
import org.tonguetied.keywordmanagement.KeywordService;
import org.tonguetied.keywordmanagement.Language;
import org.tonguetied.keywordmanagement.Translation.TranslationState;


/**
 * Manage the submission of export requests. This controller is responsible 
 * for either creating a new data export to a specified format. 
 * 
 * @author bsion
 *
 */
public class ExportController extends CancellableFormController {
    
    private KeywordService keywordService;
    private DataService dataService;
    
    private static final Logger logger = 
        Logger.getLogger(ExportController.class);

    /**
     * Create new instance of CountryController 
     */
    public ExportController() {
        setCommandClass(ExportParameters.class);
    }

    @Override
    protected Object formBackingObject(HttpServletRequest request) 
            throws Exception {
        ExportParameters exportParameters = new ExportParameters();
        
        return exportParameters;
    }

    @Override
    protected ModelAndView onSubmit(HttpServletRequest request, 
                                    HttpServletResponse response,
                                    Object command,
                                    BindException errors) throws Exception {
        if (logger.isDebugEnabled()) logger.debug("beginning export");
        ExportParameters parameters = (ExportParameters) command;
        
        dataService.exportData(parameters);
        
        return new ModelAndView(getSuccessView());
    }

    @Override
    protected void initBinder(HttpServletRequest request,
                              ServletRequestDataBinder binder) 
            throws Exception {
        binder.registerCustomEditor(Language.class,  
                new LanguageSupport(keywordService.getLanguages()));
        binder.registerCustomEditor(Country.class, 
                new CountrySupport(keywordService.getCountries()));
        binder.registerCustomEditor(Bundle.class, 
                new BundleSupport(keywordService.getBundles()));
        binder.registerCustomEditor(FormatType.class, new FormatTypeSupport()); 
        binder.registerCustomEditor(TranslationState.class, new TranslationStateSupport()); 
    }

    @Override
    protected Map<String, Object> referenceData(HttpServletRequest request)
            throws Exception 
    {
        Map<String, Object> model = new HashMap<String, Object>();
        model.put(LANGUAGES, keywordService.getLanguages());
        model.put(COUNTRIES, keywordService.getCountries());
        model.put(BUNDLES, keywordService.getBundles());
        model.put(FORMAT_TYPES, FormatType.values());
        model.put(STATES, TranslationState.values());
        
        return model;
    }

    /**
     * Assign the {@link KeywordService}.
     * 
     * @param keywordService the {@link KeywordService} to set.
     */
    public void setKeywordService(KeywordService keywordService) {
        this.keywordService = keywordService;
    }
    
    /**
     * Assign the {@link DataService}.
     * 
     * @param dataService the {@link DataService} to set.
     */
    public void setDataService(DataService dataService) {
        this.dataService = dataService;
    }
}
