package org.tonguetied.datatransfer;

/**
 * Exception thrown when an error occurs during a data import. This exception
 * acts as wrapper to the underlying cause, which could be from when imput data
 * is parsed from the source file.
 * 
 * @author bsion
 *
 */
public class ImportException extends RuntimeException {

    private static final long serialVersionUID = -8338610645953970655L;

    /**
     * Create a new instance of ExportException.
     */
    public ImportException() {
    }

    /**
     * Create a new instance of ExportException.
     * 
     * @param message the message detail
     */
    public ImportException(String message) {
        super(message);
    }

    /**
     * Create a new instance of ExportException.
     * 
     * @param cause the root cause
     */
    public ImportException(Throwable cause) {
        super(cause);
    }

    /**
     * Create a new instance of ExportException.
     * 
     * @param message the message detail
     * @param cause the root cause
     */
    public ImportException(String message, Throwable cause) {
        super(message, cause);
    }
}
