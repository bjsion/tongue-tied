package org.tonguetied.service;

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
     * @param arg0
     */
    public ImportException(String arg0) {
        super(arg0);
    }

    /**
     * Create a new instance of ExportException.
     * 
     * @param arg0
     */
    public ImportException(Throwable arg0) {
        super(arg0);
    }

    /**
     * Create a new instance of ExportException.
     * 
     * @param arg0
     * @param arg1
     */
    public ImportException(String arg0, Throwable arg1) {
        super(arg0, arg1);
    }
}
