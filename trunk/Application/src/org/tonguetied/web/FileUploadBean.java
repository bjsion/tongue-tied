package org.tonguetied.web;

import org.springframework.web.multipart.MultipartFile;
import org.tonguetied.datatransfer.FormatType;


/**
 * Value object used to hold the contents of an uploaded file received in a 
 * multipart request.
 * 
 * @author bsion
 *
 */
public class FileUploadBean {

    private MultipartFile file;
    private FormatType formatType;

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
     * @return the format of the uploaded file
     */
    public FormatType getFormatType() {
        return formatType;
    }

    /**
     * @param formatType the format of the uploaded file
     */
    public void setFormatType(FormatType formatType) {
        this.formatType = formatType;
    }
}