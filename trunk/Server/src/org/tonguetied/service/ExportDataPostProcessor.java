package org.tonguetied.service;

import java.util.List;
import java.util.Map;

import org.tonguetied.dao.DaoRepository;
import org.tonguetied.domain.ExportParameters;
import org.tonguetied.domain.Translation;


/**
 * Data post processor that performs transformations / formatting of data 
 * retrieved from persistence.
 * 
 * @author bsion
 *
 */
public interface ExportDataPostProcessor {
    
    /**
     * Using an existing list of {@link Translation}s, transform this list into
     * another list containing a different view of the same data in 
     * <code>translations</code>.
     * 
     * @param translations the list of {@link Translation}s to transform
     * @param daoRepository interface to persistant storage
     * @return List of objects that may or may not be of type 
     * {@link Translation} that is a different representation of the 
     * <code>translations</code>
     */
    List<?> transformData(List<Translation> translations, DaoRepository daoRepository);
    
    /**
     * Add additional data used by the template to format the export data
     * 
     * @param root the root level object passed to the templating library
     * @param parameters the {@link ExportParameters} used to generate this 
     * export
     */
    void addData(Map<String, Object> root, ExportParameters parameters);
}
