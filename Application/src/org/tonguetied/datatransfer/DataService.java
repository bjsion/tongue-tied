package org.tonguetied.datatransfer;




/**
 * This interface defines the events for processing data import and export.
 * 
 * @author bsion
 *
 */
public interface DataService {

    /**
     * Create a snapshot of data from persistence and transform it into the 
     * desired format. 
     * 
     * @param parameters the parameters used to filter and format the data
     * @throws ExportException if an error occurs during the export data 
     * process
     */
    void exportData(final ExportParameters parameters) throws ExportException;
    
    /**
     * Returns the absolute pathname of the the directory where exported files 
     * from the most recently executed export are saved. This method pass in a
     * value of false to {@link #getExportPath(boolean)} so as not to reset the
     * output path.
     * 
     * @return the absolute path of the output directory
     * @see #getExportPath(boolean) 
     */
    String getExportPath();
    
    /**
     * Perform a bulk update/insert of data from an external source.
     * 
     * @param parameters the parameters used to import data into the system
     * @throws ImportException if an error occurs during the import data 
     * process
     */
    void importData(final ImportParameters parameters);
}