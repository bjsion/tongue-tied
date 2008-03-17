package org.tonguetied.service;

import java.util.List;

import org.tonguetied.dao.DaoRepository;
import org.tonguetied.domain.AuditLogRecord;
import org.tonguetied.domain.Bundle;
import org.tonguetied.domain.Country;
import org.tonguetied.domain.Keyword;
import org.tonguetied.domain.Language;
import org.tonguetied.domain.Country.CountryCode;
import org.tonguetied.domain.Language.LanguageCode;



/**
 * 
 * @author mforslund
 */
public class ApplicationServiceImpl implements ApplicationService
{
    private DaoRepository daoRepository;
    
    public void setDaoRepository(DaoRepository daoRepository) {
    	this.daoRepository = daoRepository;
    }
    
    public void delete(Object object) 
    {
    	daoRepository.delete(object);
    }
    
    public Bundle getBundle(final Long id) 
    {
        return daoRepository.getBundle(id);
    }
    
    public Bundle getBundle(String name) {
        return daoRepository.getBundle(name);
    }

    public List<Bundle> getBundles() 
    {
    	return daoRepository.getBundles();
    }
    
    public Country getCountry(final Long id) 
    {
        return daoRepository.getCountry(id);
    }
    
    public Country getCountry(final CountryCode code) 
    {
        return daoRepository.getCountry(code);
    }
    
    public List<Country> getCountries() 
    {
    	return daoRepository.getCountries();
    }
    
    public Keyword getKeyword(final Long id) 
    {
        return daoRepository.getKeyword(id);
    }
    
    public Keyword getKeyword(final String keywordString) 
    {
        return daoRepository.getKeyword(keywordString);
    }
    
    public List<Keyword> getKeywords() 
    {
        return getKeywords(0, null);
    }
    
    public List<Keyword> getKeywords(Integer firstResult, Integer maxResults) 
    {
    	return daoRepository.getKeywords(firstResult, maxResults);
    }
    
    public List<Keyword> findKeywords(Keyword keyword, 
                                      final boolean ignoreCase,
                                      final Integer firstResult,
                                      final Integer maxResults) {
        return daoRepository.findKeywords(
                keyword, ignoreCase, firstResult, maxResults);
    }
    
    public Language getLanguage(final Long id) 
    {
        return daoRepository.getLanguage(id);
    }
    
    public Language getLanguage(final LanguageCode code) 
    {
        return daoRepository.getLanguage(code);
    }
    
    public List<Language> getLanguages() 
    {
    	return daoRepository.getLanguages();
    }

    public List<AuditLogRecord> getAuditLog() {
        return daoRepository.getAuditLog();
    }

    public void deleteKeyword(final Long id) {
        Keyword keyword = getKeyword(id);
        delete(keyword);
    }
    
    public void saveOrUpdate(Object object) 
    {
    	daoRepository.saveOrUpdate(object);
    }
}
