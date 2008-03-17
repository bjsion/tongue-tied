package org.tonguetied.web;

import static org.tonguetied.web.Constants.BUNDLES;
import static org.tonguetied.web.Constants.COUNTRIES;
import static org.tonguetied.web.Constants.LANGUAGES;
import static org.tonguetied.web.Constants.SEARCH_FORM;
import static org.tonguetied.web.Constants.SHOW_ALL;
import static org.tonguetied.web.Constants.STATES;
import static org.tonguetied.web.Constants.TRANSLATIONS;
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
import org.tonguetied.domain.AuditLogRecord;
import org.tonguetied.domain.Bundle;
import org.tonguetied.domain.Country;
import org.tonguetied.domain.Keyword;
import org.tonguetied.domain.Language;
import org.tonguetied.domain.Translation;
import org.tonguetied.domain.User;
import org.tonguetied.domain.Translation.TranslationState;
import org.tonguetied.service.AuditService;
import org.tonguetied.service.ApplicationService;
import org.tonguetied.service.UserService;


/**
 * Controller implementation that processes various HTTP requests for the main
 * page.
 * 
 * @author bsion
 *
 */
public class MainController extends MultiActionController {

    private ApplicationService appService;
    private UserService userService;
    private AuditService auditService;
    private PreferenceForm viewPreferences;
    private SearchForm searchForm;
    
    /**
     * Handler method that acts as an HTTP interface to the 
     * {@linkplain ApplicationService#getKeywords()} method.
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
        boolean showAll = (Boolean) request.getSession().getAttribute(SHOW_ALL);
        if (showAll) {
            keywords = appService.getKeywords(0, viewPreferences.getMaxResults());
            searchForm = new SearchForm();
        }
        else {
            Keyword keyword = searchForm.getKeyword().clone();
            if (new Translation().equals(keyword.getTranslations().first())) {
                keyword.setTranslations(SetUtils.EMPTY_SORTED_SET);
            }
            keywords = 
                appService.findKeywords(keyword,
                        searchForm.getIgnoreCase(),
                                        0,
                                        viewPreferences.getMaxResults());
        }
        
        List<Translation> translations = 
            TranslationTransformer.transform(keywords);
        PreferenceFilter filter = new PreferenceFilter(viewPreferences);
        CollectionUtils.filter(translations, filter);

        Map<String, Object> model = new HashMap<String, Object>();
        model.put(TRANSLATIONS, translations);
        model.put(LANGUAGES, appService.getLanguages());
        model.put(BUNDLES, appService.getBundles());
        model.put(COUNTRIES, appService.getCountries());
        model.put(STATES, TranslationState.values());
        model.put(SEARCH_FORM, searchForm);
        model.put(VIEW_PREFERENCES, viewPreferences);
        return new ModelAndView("keyword/keywords", model);
    }
    
    /**
     * Handler method that acts as an HTTP interface to the 
     * {@linkplain ApplicationService#getBundles()} method.
     * 
     * @param request the current HTTP request.
     * @param response the current HTTP response.
     * @return a ModelAndView to render.
     * @throws Exception in case of errors.
     */
    public ModelAndView bundles(HttpServletRequest request,
            HttpServletResponse response) throws Exception
    {
        List<Bundle> bundles = appService.getBundles();
        
        return new ModelAndView("bundle/bundles", "bundles", bundles);
    }
    
    /**
     * Handler method that acts as an HTTP interface to the 
     * {@linkplain ApplicationService#getCountries()} method.
     * 
     * @param request the current HTTP request.
     * @param response the current HTTP response.
     * @return a ModelAndView to render.
     * @throws Exception in case of errors.
     */
    public ModelAndView countries(HttpServletRequest request,
            HttpServletResponse response) throws Exception
    {
        List<Country> countries = appService.getCountries();
        
        return new ModelAndView("country/countries", "countries", countries);
    }
    
    /**
     * Handler method that acts as an HTTP interface to the 
     * {@linkplain ApplicationService#getLanguages()} method.
     * 
     * @param request the current HTTP request.
     * @param response the current HTTP response.
     * @return a ModelAndView to render.
     * @throws Exception in case of errors.
     */
    public ModelAndView languages(HttpServletRequest request,
            HttpServletResponse response) throws Exception
    {
        List<Language> languages = appService.getLanguages();
        
        return new ModelAndView("language/languages", "languages", languages);
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
        
        return new ModelAndView("user/users", "users", users);
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
        
        return new ModelAndView("audit/auditLog", "auditLog", auditLog);
    }
    
    /**
     * Handler method that acts as an HTTP interface to the 
     * {@linkplain ApplicationService#delete(Object)} method.
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
        appService.deleteKeyword(Long.parseLong(keywordId));
        
        return new ModelAndView("redirect:/keywords.htm");
    }
    
    /**
     * Assign the {@link ApplicationService}.
     * 
     * @param appService the {@link ApplicationService} to set
     */
    public void setAppService(ApplicationService appService) {
        this.appService = appService;
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
     * @param searchForm the searchForm to set
     */
    public void setSearchForm(SearchForm searchForm) {
        this.searchForm = searchForm;
    }
}
