package org.tonguetied.service;

import java.io.File;

import org.tonguetied.domain.ExportParameters;
import org.tonguetied.domain.FormatType;


/**
 * This interface defines the events for processing data import and export.
 * 
 * @author bsion
 *
 */
public interface ExportService {

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
     * Get the location on the file system where export files are saved to.
     * 
     * @return the export directory
     */
    File getExportDirectory();
    
    /**
     * Perform a bulk update/insert of data from an external source.
     * 
     * @param data the data to process and then add to persistence
     * @param formatType the format the input file is in
     * @throws ImportException if an error occurs during the import data 
     * process
     */
    void importData(byte[] data, FormatType formatType);
}
