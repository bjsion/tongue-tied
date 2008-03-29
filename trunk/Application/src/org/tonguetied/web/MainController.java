package org.tonguetied.web;

import static org.tonguetied.web.Constants.AUDIT_LOG;
import static org.tonguetied.web.Constants.BUNDLES;
import static org.tonguetied.web.Constants.COUNTRIES;
import static org.tonguetied.web.Constants.LANGUAGES;
import static org.tonguetied.web.Constants.SEARCH_PARAMETERS;
import static org.tonguetied.web.Constants.SHOW_ALL;
import static org.tonguetied.web.Constants.STATES;
import static org.tonguetied.web.Constants.TRANSLATIONS;
import static org.tonguetied.web.Constants.USERS;
import static org.tonguetied.web.Constants.VIEW_PREFERENCES;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.SetUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;
import org.tonguetied.audit.AuditLogRecord;
import org.tonguetied.audit.AuditService;
import org.tonguetied.keywordmanagement.Bundle;
import org.tonguetied.keywordmanagement.Country;
import org.tonguetied.keywordmanagement.Keyword;
import org.tonguetied.keywordmanagement.KeywordService;
import org.tonguetied.keywordmanagement.Language;
import org.tonguetied.keywordmanagement.Translation;
import org.tonguetied.keywordmanagement.Translation.TranslationState;
import org.tonguetied.usermanagement.User;
import org.tonguetied.usermanagement.UserService;


/**
 * Controller implementation that processes various HTTP requests for the main
 * page.
 * 
 * @author bsion
 *
 */
public class MainController extends MultiActionController {

    private KeywordService keywordService;
    private UserService userService;
    private AuditService auditService;
    private PreferenceForm viewPreferences;
    private SearchForm searchParameters;
    
    /**
     * Handler method that acts as an HTTP interface to the 
     * {@linkplain KeywordService#getKeywords()} method.
     * 
     * @param request the current HTTP request.
     * @param response the current HTTP response.
     * @return a ModelAndView to render.
     * @throws Exception in case of errors.
     */
    public ModelAndView keywords(HttpServletRequest request,
            HttpServletResponse response) throws Exception
    {
        List<Keyword> keywords;
        String parameter = request.getParameter(SHOW_ALL);
        boolean showAll;
        if (parameter != null) {
            showAll = Boolean.parseBoolean(parameter);
        }
        else {
            showAll = (Boolean) request.getSession().getAttribute(SHOW_ALL);
        }
        
        if (showAll) {
            keywords = keywordService.getKeywords(0, viewPreferences.getMaxResults());
            searchParameters.initialize();
        }
        else {
            Keyword keyword = searchParameters.getKeyword();
            if (new Translation().equals(keyword.getTranslations().first())) {
                keyword.setTranslations(SetUtils.EMPTY_SORTED_SET);
            }
            keywords = 
                keywordService.findKeywords(keyword,
                        searchParameters.getIgnoreCase(),
                                        0,
                                        viewPreferences.getMaxResults());
        }
        
        List<Translation> translations = 
            TranslationTransformer.transform(keywords);
        PreferenceFilter filter = new PreferenceFilter(viewPreferences);
        CollectionUtils.filter(translations, filter);
        searchParameters.getKeyword();

        Map<String, Object> model = new HashMap<String, Object>();
        model.put(TRANSLATIONS, translations);
        model.put(LANGUAGES, keywordService.getLanguages());
        model.put(BUNDLES, keywordService.getBundles());
        model.put(COUNTRIES, keywordService.getCountries());
        model.put(STATES, TranslationState.values());
        model.put(SEARCH_PARAMETERS, searchParameters);
        model.put(VIEW_PREFERENCES, viewPreferences);
        return new ModelAndView("keyword/keywords", model);
    }
    
    /**
     * Handler method that acts as an HTTP interface to the 
     * {@linkplain KeywordService#getBundles()} method.
     * 
     * @param request the current HTTP request.
     * @param response the current HTTP response.
     * @return a ModelAndView to render.
     * @throws Exception in case of errors.
     */
    public ModelAndView bundles(HttpServletRequest request,
            HttpServletResponse response) throws Exception
    {
        List<Bundle> bundles = keywordService.getBundles();
        
        return new ModelAndView("bundle/bundles", BUNDLES, bundles);
    }
    
    /**
     * Handler method that acts as an HTTP interface to the 
     * {@linkplain KeywordService#getCountries()} method.
     * 
     * @param request the current HTTP request.
     * @param response the current HTTP response.
     * @return a ModelAndView to render.
     * @throws Exception in case of errors.
     */
    public ModelAndView countries(HttpServletRequest request,
            HttpServletResponse response) throws Exception
    {
        List<Country> countries = keywordService.getCountries();
        
        return new ModelAndView("country/countries", COUNTRIES, countries);
    }
    
    /**
     * Handler method that acts as an HTTP interface to the 
     * {@linkplain KeywordService#getLanguages()} method.
     * 
     * @param request the current HTTP request.
     * @param response the current HTTP response.
     * @return a ModelAndView to render.
     * @throws Exception in case of errors.
     */
    public ModelAndView languages(HttpServletRequest request,
            HttpServletResponse response) throws Exception
    {
        List<Language> languages = keywordService.getLanguages();
        
        return new ModelAndView("language/languages", LANGUAGES, languages);
    }
    
    /**
     * Handler method that acts as an HTTP interface to the 
     * {@linkplain UserService#getUsers()} method.
     * 
     * @param request the current HTTP request.
     * @param response the current HTTP response.
     * @return a ModelAndView to render.
     * @throws Exception in case of errors.
     */
    public ModelAndView users(HttpServletRequest request,
            HttpServletResponse response) throws Exception
    {
        List<User> users = userService.getUsers();
        
        return new ModelAndView("user/users", USERS, users);
    }
    
    /**
     * Handler method that acts as an HTTP interface to the 
     * {@linkplain AuditService#getAuditLog()} method.
     * 
     * @param request the current HTTP request.
     * @param response the current HTTP response.
     * @return a ModelAndView to render.
     * @throws Exception in case of errors.
     */
    public ModelAndView auditLog(HttpServletRequest request,
            HttpServletResponse response) throws Exception
    {
        List<AuditLogRecord> auditLog = auditService.getAuditLog();
        
        return new ModelAndView("audit/auditLog", AUDIT_LOG, auditLog);
    }
    
    /**
     * Handler method that acts as an HTTP interface to the 
     * {@linkplain KeywordService#delete(Object)} method.
     * 
     * @param request the current HTTP request.
     * @param response the current HTTP response.
     * @return a ModelAndView to render.
     * @throws Exception in case of errors.
     */
    public ModelAndView deleteKeyword(HttpServletRequest request,
            HttpServletResponse response) throws Exception
    {
        String keywordId = request.getParameter("keywordId");
        keywordService.deleteKeyword(Long.parseLong(keywordId));
        
        return new ModelAndView("forward:/keywords.htm");
    }
    
    /**
     * Assign the {@link KeywordService}.
     * 
     * @param keywordService the {@link KeywordService} to set
     */
    public void setKeywordService(KeywordService keywordService) {
        this.keywordService = keywordService;
    }
    
    /**
     * Assign the {@link UserService}.
     * 
     * @param userService the {@link UserService} to set
     */
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    /**
     * Assign the {@link AuditService}.
     * 
     * @param auditService the {@link AuditService} to set
     */
    public void setAuditService(AuditService auditService) {
        this.auditService = auditService;
    }
    
    /**
     * Assign the {@link PreferenceForm}.
     * 
     * @param viewPreferences the {@link PreferenceForm} to set
     */
    public void setViewPreferences(PreferenceForm viewPreferences) {
        this.viewPreferences = viewPreferences;
    }

    /**
     * Assign the {@link SearchForm}.
     * 
     * @param searchParameters the searchParameters to set
     */
    public void setSearchParameters(SearchForm searchParameters) {
        this.searchParameters = searchParameters;
    }
}
