package org.tonguetied.web;

import static org.tonguetied.web.Constants.USER;
import static org.tonguetied.web.Constants.USERS;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.validation.BindException;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;
import org.tonguetied.usermanagement.User;
import org.tonguetied.usermanagement.UserService;

/**
 * Controller for processing user search requests.
 * 
 * @author bsion
 * 
 */
public class UserSearchController extends SimpleFormController
{
    private UserService userService;

    private static final Logger logger = Logger
            .getLogger(UserSearchController.class);

    /**
     * Create new instance of KeywordSearchController
     */
    public UserSearchController()
    {
        setCommandClass(User.class);
    }

    @Override
    protected Object formBackingObject(HttpServletRequest request)
            throws Exception
    {
        return new User();
    }

    @Override
    protected ModelAndView onSubmit(HttpServletRequest request,
            HttpServletResponse response, Object command, BindException errors)
            throws Exception
    {
        if (logger.isDebugEnabled()) logger.debug("searching for users");
        User user = (User) command;
        List<User> users = userService.findUsers(user);
        Map<String, Object> model = new HashMap<String, Object>();
        model.put(USERS, users);
        model.put(USER, user);

        return new ModelAndView(getSuccessView(), model);
    }

    @Override
    protected void initBinder(HttpServletRequest request,
            ServletRequestDataBinder binder) throws Exception
    {
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(
                        true));
    }

    /**
     * Assign the {@link UserService}.
     * 
     * @param userService the {@link UserService} to set.
     */
    public void setUserService(UserService userService)
    {
        this.userService = userService;
    }
}
