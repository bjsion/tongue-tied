package org.tonguetied.web;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.security.context.SecurityContextHolder;
import org.springframework.security.userdetails.UserDetails;
import org.springframework.validation.BindException;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.CancellableFormController;
import org.tonguetied.usermanagement.User;
import org.tonguetied.usermanagement.UserRight;
import org.tonguetied.usermanagement.UserService;
import org.tonguetied.usermanagement.UserRight.Permission;


/**
 * Controller responsible for action logic around the creating or updating of a
 * {@link User}.
 * 
 * @author bsion
 *
 */
public class UserController extends CancellableFormController 
{
    
    private UserService userService;
    
    private static final Logger logger = 
        Logger.getLogger(UserController.class);

    /**
     * Create new instance of UserController 
     */
    public UserController()
    {
        setCommandClass(User.class);
    }

    /* (non-Javadoc)
     * @see org.springframework.web.servlet.mvc.BaseCommandController#initBinder(javax.servlet.http.HttpServletRequest, org.springframework.web.bind.ServletRequestDataBinder)
     */
    @Override
    protected void initBinder(HttpServletRequest request, 
                              ServletRequestDataBinder binder) throws Exception
    {
        binder.registerCustomEditor(Permission.class, new PermissionSupport());
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
    }

    @Override
    protected Map<String, Object> referenceData(HttpServletRequest request) 
            throws Exception 
    {
        Map<String, Object> model = new HashMap<String, Object>();
        model.put("permissions", Permission.values());
        
        return model;
    }

    @Override
    protected Object formBackingObject(HttpServletRequest request) 
            throws Exception 
    {
        final String stringId = request.getParameter("id");
        User user = null;
        if (StringUtils.isNotBlank(stringId)) {
            Long id = Long.parseLong(stringId);
            user = userService.getUser(id);
        }
        else
        {
            final String myAccountFlag = request.getParameter("myAccount");
            if (myAccountFlag != null && Boolean.parseBoolean(myAccountFlag))
            {
                UserDetails userDetails = 
                    (UserDetails) SecurityContextHolder.getContext().
                    getAuthentication().getPrincipal();
                user = userService.getUser(userDetails.getUsername());
            }
        }
        
        if (user == null) {
            user = new User();
            user.setAccountNonExpired(true);
            user.setAccountNonLocked(true);
            user.setCredentialsNonExpired(true);
            user.setEnabled(true);
            UserRight userRight = new UserRight(Permission.ROLE_USER, null, null, null);
            userRight.setUser(user);
            user.addUserRight(userRight);
        }
        
        return user;
    }

    @Override
    protected ModelAndView onSubmit(HttpServletRequest request, 
                                    HttpServletResponse response,
                                    Object command,
                                    BindException errors) throws Exception
    {
        if (logger.isDebugEnabled()) logger.debug("saving user");
        User user = (User) command;
        
        userService.saveOrUpdate(user);
        
        return new ModelAndView(getSuccessView());
    }

    @Override
    protected ModelAndView onCancel(HttpServletRequest request,
                                    HttpServletResponse response,
                                    Object command) throws Exception
    {
        return new ModelAndView(getCancelView());
    }

    /**
     * Assign the {@link UserService}.
     * 
     * @param userService the {@link UserService} to set.
     */
    public void setUserService(UserService userService) {
        this.userService = userService;
    }
}
