package org.tonguetied.datatransfer.exporting;

/**
 * Exception thrown when an error occurs during a data export. This exception
 * acts as wrapper to the underlying cause, which could be from when data is
 * extracted from persistence or during the formatting of that data into the 
 * appropriate format.
 * 
 * @author bsion
 *
 */
public class ExportException extends RuntimeException {

    private static final long serialVersionUID = 9062827618835803948L;

    /**
     * Create a new instance of ExportException.
     */
    public ExportException() {
    }

    /**
     * Create a new instance of ExportException.
     * 
     * @param message the message detail
     */
    public ExportException(String message) {
        super(message);
    }

    /**
     * Create a new instance of ExportException.
     * 
     * @param cause the root cause
     */
    public ExportException(Throwable cause) {
        super(cause);
    }

    /**
     * Create a new instance of ExportException.
     * 
     * @param message the message detail
     * @param cause the root cause
     */
    public ExportException(String message, Throwable cause) {
        super(message, cause);
    }
}
