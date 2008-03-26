package org.tonguetied.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.validation.BindException;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.CancellableFormController;
import org.tonguetied.domain.Bundle;
import org.tonguetied.service.ApplicationService;


/**
 * @author bsion
 *
 */
public class BundleController extends CancellableFormController {
    
    private ApplicationService appService;
    
    private static final Logger logger = 
        Logger.getLogger(BundleController.class);

    /**
     * Create new instance of BundleController 
     */
    public BundleController() {
        setCommandClass(Bundle.class);
    }

    /* (non-Javadoc)
     * @see org.springframework.web.servlet.mvc.BaseCommandController#initBinder(javax.servlet.http.HttpServletRequest, org.springframework.web.bind.ServletRequestDataBinder)
     */
    @Override
    protected void initBinder(HttpServletRequest request, 
                              ServletRequestDataBinder binder) throws Exception
    {
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
    }

    @Override
    protected Object formBackingObject(HttpServletRequest request) 
            throws Exception {
        String stringId = request.getParameter("bundleId");
        Long id = null;
        if (stringId != null)
            id = Long.parseLong(stringId);
        Bundle bundle = appService.getBundle(id);
        if (bundle == null)
            bundle = new Bundle();
        
        return bundle;
    }

    @Override
    protected ModelAndView onSubmit(HttpServletRequest request, 
                                    HttpServletResponse response,
                                    Object command,
                                    BindException errors) throws Exception {
        if (logger.isDebugEnabled()) logger.debug("saving bundle");
        Bundle bundle = (Bundle) command;
        
        appService.saveOrUpdate(bundle);
        
        return new ModelAndView(getSuccessView());
    }

    @Override
    protected ModelAndView onCancel(HttpServletRequest request,
                                    HttpServletResponse response,
                                    Object command) throws Exception {
        return new ModelAndView(getCancelView());
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
