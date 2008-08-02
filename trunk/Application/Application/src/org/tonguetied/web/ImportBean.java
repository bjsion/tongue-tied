package org.tonguetied.web;

import org.tonguetied.datatransfer.common.ImportParameters;

/**
 * Value object used as the model for import data features in the web interface.
 * This object acts as a composite of other objects needed for import 
 * functionality. 
 * 
 * @author bsion
 *
 */
public class ImportBean {
    private FileUploadBean fileUploadBean;
    private ImportParameters parameters;

    /**
     * Create a new instance of ImportBean.
     */
    public ImportBean() {
        this.fileUploadBean = new FileUploadBean();
        this.parameters = new ImportParameters();
    }

    /**
     * @return the fileUploadBean
     */
    public FileUploadBean getFileUploadBean() {
        return fileUploadBean;
    }

    /**
     * @param fileUploadBean the fileUploadBean to set
     */
    public void setFileUploadBean(FileUploadBean fileUploadBean) {
        this.fileUploadBean = fileUploadBean;
    }

    /**
     * @return the parameters of the import
     */
    public ImportParameters getParameters() {
        return parameters;
    }

    /**
     * @param parameters the parameters to set
     */
    public void setParameters(ImportParameters parameters) {
        this.parameters = parameters;
    }
}
