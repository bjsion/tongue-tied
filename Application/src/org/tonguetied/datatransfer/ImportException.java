package org.tonguetied.datatransfer;

import java.util.ArrayList;
import java.util.List;

/**
 * Exception thrown when an error occurs during a data import. This exception
 * acts as wrapper to the underlying cause, which could be from when imput data
 * is parsed from the source file.
 * 
 * @author bsion
 *
 */
public class ImportException extends RuntimeException {
    private List<ImportErrorCode> errorCodes;

    private static final long serialVersionUID = -8338610645953970655L;

    /**
     * Create a new instance of ExportException.
     * 
     * @param errorCodes the list of codes for each error that occurred during 
     * the import process
     */
    public ImportException(List<ImportErrorCode> errorCodes) {
        this.errorCodes = errorCodes;
    }

    /**
     * Create a new instance of ExportException.
     * 
     * @param cause the root cause
     */
    public ImportException(Throwable cause) {
        this(null, cause);
    }

    /**
     * Create a new instance of ExportException.
     * 
     * @param message the message detail
     * @param cause the root cause
     */
    public ImportException(String message, Throwable cause) {
        super(message, cause);
        this.errorCodes = new ArrayList<ImportErrorCode>();
    }

    /**
     * @return the errorCodes
     */
    public List<ImportErrorCode> getErrorCodes() {
        return errorCodes;
    }

    /**
     * The type of errors that can occur during the import process. Each value
     * describes the type of error that occurred making it easier to identify 
     * problem files during import. 
     *  
     * @author bsion
     *
     */
    protected enum ImportErrorCode {
        emptyData, illegalCountry, illegalLanguage, invalidNameFormat, 
        unknownBundle, unknownCountry, unknownLanguage
    }
}
