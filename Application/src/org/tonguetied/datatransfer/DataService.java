package org.tonguetied.datatransfer;

import java.io.File;

import org.tonguetied.datatransfer.common.ExportParameters;
import org.tonguetied.datatransfer.common.ImportParameters;
import org.tonguetied.datatransfer.exporting.ExportException;
import org.tonguetied.datatransfer.importing.ImportException;




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
     * Returns the the directory where exported files from the most recently 
     * executed export are saved. 
     * 
     * @return the output directory
     */
    File getExportPath();

    /**
     * Create an archive file with the contents of the directory supplied.
     *   
     * @param directory the directory containing the files to archive
     * @throws IllegalArgumentException if <code>directory</code> is not a 
     * directory
     * @throws ExportException if an error occurs during the export data 
     * process or if the directory is not a directory
     */
    void createArchive(File directory) throws ExportException, IllegalArgumentException;
    
    /**
     * Perform a bulk update/insert of data from an external source.
     * 
     * @param parameters the parameters used to import data into the system
     * @throws ImportException if an error occurs during the import data 
     * process
     */
    void importData(final ImportParameters parameters);
}
