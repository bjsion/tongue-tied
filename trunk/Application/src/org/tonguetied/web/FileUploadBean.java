package org.tonguetied.web;

import org.springframework.web.multipart.MultipartFile;
import org.tonguetied.datatransfer.ImportParameters;


/**
 * Value object used to hold the contents of an uploaded file received in a 
 * multipart request.
 * 
 * @author bsion
 *
 */
public class FileUploadBean {

    private MultipartFile file;
    private ImportParameters parameters;

    /**
     * Create a new instance of FileUploadBean.
     */
    public FileUploadBean() {
        this.parameters = new ImportParameters();
    }

    /**
     * @return a representation of the uploaded file 
     */
    public MultipartFile getFile() {
        return file;
    }

    /**
     * @param file the uploaded file representation
     */
    public void setFile(MultipartFile file) {
        this.file = file;
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
