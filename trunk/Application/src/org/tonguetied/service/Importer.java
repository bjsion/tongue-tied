package org.tonguetied.service;

import org.tonguetied.dao.DaoRepository;
import org.tonguetied.domain.FormatType;


/**
 * This factory acts as abstraction on top the underlying import method. The 
 * purpose is to hide away the implementation details such that multiple import
 * types can be handled. 
 * 
 * @author bsion
 *
 */
public abstract class Importer {
    private DaoRepository daoRepository;
    
    /**
     * Create a new instance of Importer.
     * 
     * @param daoRepository
     */
    protected Importer(DaoRepository daoRepository) {
        this.daoRepository = daoRepository;
    }

    /**
     * Factory method to create the appropriate <code>Importer</code>
     * @param daoRepository interface to persistent storage
     * @param formatType the format the import data is in. This disambiguates
     * which type of <code>Importer</code> to use
     * 
     * @return The newly created <code>Importer</code>
     */
    public static final Importer createInstance(DaoRepository daoRepository,
            FormatType formatType) 
    {
        Importer importer = null;
        switch (formatType) {
            case xls:
            case xlsLanguage:
                importer = new ExcelImporter(daoRepository);
                break;
            default:
                break;
        }
        
        return importer;
    }

    /**
     * 
     * @param input raw data to import. This data can be in text or binary 
     * format. This will be specific to the importer, as data can be imported 
     * from different sources.
     */
    abstract void importData(byte[] input);

    /**
     * @return the daoRepository
     */
    protected DaoRepository getDaoRepository() {
        return daoRepository;
    }
}
