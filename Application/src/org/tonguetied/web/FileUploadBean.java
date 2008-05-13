package org.tonguetied.web;

import org.springframework.web.multipart.MultipartFile;


/**
 * Value object used to hold the contents of an uploaded file received in a 
 * multipart request.
 * 
 * @author bsion
 *
 */
public class FileUploadBean {

    private MultipartFile file;

    /**
     * Create a new instance of FileUploadBean.
     */
    public FileUploadBean() {
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
}
