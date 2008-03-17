package org.tonguetied.web;

import static org.tonguetied.web.Constants.FORMAT_TYPES;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.validation.BindException;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.CancellableFormController;
import org.tonguetied.domain.FormatType;
import org.tonguetied.service.ExportService;


/**
 * Manage the submission of export requests. This controller is responsible 
 * for either creating a new data export to a specified format. 
 * 
 * @author bsion
 *
 */
public class ImportController extends CancellableFormController {
    
    private ExportService exportService;
    
    private static final Logger logger = 
        Logger.getLogger(ImportController.class);

    /**
     * Create new instance of CountryController 
     */
    public ImportController() {
        setCommandClass(FileUploadBean.class);
    }

    @Override
    protected Object formBackingObject(HttpServletRequest request) 
            throws Exception {
        return new FileUploadBean();
    }

    @Override
    protected ModelAndView onSubmit(HttpServletRequest request, 
                                    HttpServletResponse response,
                                    Object command,
                                    BindException errors) throws Exception {
        if (logger.isDebugEnabled()) logger.debug("beginning import");
        // cast the bean
        FileUploadBean bean = (FileUploadBean) command;

        // let's see if there's content there
        MultipartFile file = bean.getFile();
        if (file != null) {
            exportService.importData(file.getBytes(), bean.getFormatType());
        }
        else {
            // hmm, that's strange, the user did not upload anything
        }

        return new ModelAndView(getSuccessView());
    }

    @Override
    protected ModelAndView onCancel(HttpServletRequest request,
                                    HttpServletResponse response,
                                    Object command) throws Exception 
    {
        return new ModelAndView(getCancelView());
    }
    
    @Override
    protected void initBinder(HttpServletRequest request,
                              ServletRequestDataBinder binder) 
            throws Exception {
        binder.registerCustomEditor(FormatType.class, new FormatTypeSupport()); 
    }

    @Override
    protected Map<String, Object> referenceData(HttpServletRequest request) 
            throws Exception 
    {
        Map<String, Object> model = new HashMap<String, Object>();
        model.put(FORMAT_TYPES, FormatType.values());
        
        return model;
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
