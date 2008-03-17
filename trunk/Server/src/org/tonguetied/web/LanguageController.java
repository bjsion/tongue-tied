package org.tonguetied.web;

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
import org.tonguetied.domain.Language;
import org.tonguetied.domain.Language.LanguageCode;
import org.tonguetied.service.ApplicationService;


/**
 * Manage the submission of {@link Language}s. This controller is responsible 
 * for either creating a new {@link Language} or saving an existing one. 
 * 
 * @author bsion
 *
 */
public class LanguageController extends CancellableFormController {
    
    private ApplicationService appService;
    
    private static final Logger logger = 
        Logger.getLogger(LanguageController.class);

    /**
     * Create new instance of LanguageController 
     */
    public LanguageController() {
        setCommandClass(Language.class);
    }

    @Override
    protected Object formBackingObject(HttpServletRequest request) 
            throws Exception {
        String stringId = request.getParameter("languageId");
        Long id = null;
        if (stringId != null)
            id = Long.parseLong(stringId);
        Language language = appService.getLanguage(id);
        if (language == null)
            language = new Language();
        
        return language;
    }

    /**
     * 
     */
    @Override
    protected ModelAndView onSubmit(HttpServletRequest request, 
                                    HttpServletResponse response,
                                    Object command,
                                    BindException errors) throws Exception {
        if (logger.isDebugEnabled()) logger.debug("saving language");
        Language language = (Language) command;
        
        appService.saveOrUpdate(language);
        
        return new ModelAndView(getSuccessView());
    }

    @Override
    protected ModelAndView onCancel(HttpServletRequest request,
                                    HttpServletResponse response,
                                    Object command) throws Exception {
        return new ModelAndView(getCancelView());
    }

    @Override
    protected void initBinder(HttpServletRequest request,
                              ServletRequestDataBinder binder) 
            throws Exception {
        binder.registerCustomEditor(LanguageCode.class, 
                new LanguageCodeSupport());
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
    }

    @Override
    protected Map<String, Object> referenceData(HttpServletRequest request) 
            throws Exception 
    {
        Map<String, Object> model = new HashMap<String, Object>();
        model.put("languageCodes", LanguageCode.values());
        
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
}
