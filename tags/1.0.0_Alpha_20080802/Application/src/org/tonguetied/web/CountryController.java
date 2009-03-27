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
import org.tonguetied.keywordmanagement.Country;
import org.tonguetied.keywordmanagement.KeywordService;
import org.tonguetied.keywordmanagement.Country.CountryCode;


/**
 * @author bsion
 *
 */
public class CountryController extends CancellableFormController {
    
    private KeywordService keywordService;
    
    private static final Logger logger = 
        Logger.getLogger(CountryController.class);

    /**
     * Create new instance of CountryController 
     */
    public CountryController() {
        setCommandClass(Country.class);
    }

    @Override
    protected Object formBackingObject(HttpServletRequest request) 
            throws Exception {
        String stringId = request.getParameter("countryId");
        Long id = null;
        if (stringId != null)
            id = Long.parseLong(stringId);
        Country  country = keywordService.getCountry (id);
        if (country == null)
            country = new Country();
        
        return country;
    }

    @Override
    protected ModelAndView onSubmit(HttpServletRequest request, 
                                    HttpServletResponse response,
                                    Object command,
                                    BindException errors) throws Exception {
        if (logger.isDebugEnabled()) logger.debug("saving country");
        Country country = (Country) command;
        
        keywordService.saveOrUpdate(country);
        
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
        binder.registerCustomEditor(CountryCode.class, 
                new CountryCodeSupport());
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
    }

    @Override
    protected Map<String, Object> referenceData(HttpServletRequest request) 
            throws Exception 
    {
        Map<String, Object> model = new HashMap<String, Object>();
        model.put("countryCodes", CountryCode.values());
        
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
}