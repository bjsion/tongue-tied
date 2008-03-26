package org.tonguetied.web;

import static org.tonguetied.web.Constants.BUNDLES;
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
import org.tonguetied.domain.Bundle;
import org.tonguetied.domain.Country;
import org.tonguetied.domain.ExportParameters;
import org.tonguetied.domain.FormatType;
import org.tonguetied.domain.Language;
import org.tonguetied.service.ExportService;
import org.tonguetied.service.ApplicationService;


/**
 * Manage the submission of export requests. This controller is responsible 
 * for either creating a new data export to a specified format. 
 * 
 * @author bsion
 *
 */
public class ExportController extends CancellableFormController {
    
    private ApplicationService appService;
    private ExportService exportService;
    
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
        
        exportService.exportData(parameters);
        
        return new ModelAndView(getSuccessView());
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
        binder.registerCustomEditor(FormatType.class, new FormatTypeSupport()); 
    }

    @Override
    protected Map<String, Object> referenceData(HttpServletRequest request)
            throws Exception 
    {
        Map<String, Object> model = new HashMap<String, Object>();
        model.put(LANGUAGES, appService.getLanguages());
        model.put(COUNTRIES, appService.getCountries());
        model.put(BUNDLES, appService.getBundles());
        model.put(FORMAT_TYPES, FormatType.values());
        
        return model;
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
     * Assign the {@link ExportService}.
     * 
     * @param exportService the {@link ExportService} to set.
     */
    public void setExportService(ExportService exportService) {
        this.exportService = exportService;
    }
}
