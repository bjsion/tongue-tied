package org.tonguetied.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.context.SecurityContextHolder;
import org.springframework.security.userdetails.UserDetails;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.CancellableFormController;
import org.tonguetied.usermanagement.AuthenticationException;
import org.tonguetied.usermanagement.User;
import org.tonguetied.usermanagement.UserService;

/**
 * Controller object responsible for the action logic of a {@link User} 
 * changing their password.
 * 
 * @author bsion
 *
 */
public class ChangePasswordController extends CancellableFormController
{
    private UserService userService;

    /**
     * Create a new instance of ChangePasswordController.
     */
    public ChangePasswordController()
    {
        this.setCommandClass(ChangePasswordForm.class);
    }

    @Override
    protected ModelAndView onSubmit(HttpServletRequest request,
            HttpServletResponse response, Object command, BindException errors)
            throws Exception
    {
        ModelAndView mav;
        ChangePasswordForm form = (ChangePasswordForm) command;
        try 
        {
            this.userService.changePassword(
                getCurrentUser(), form.getOldPassword(), form.getNewPassword());
            mav = new ModelAndView(getSuccessView());
        }
        catch (AuthenticationException ae)
        {
            errors.rejectValue("oldPassword", "error.invalid.password");
            mav = showForm(request, response, errors);
        }
        
        return mav;
    }

    @Override
    protected ModelAndView onCancel(HttpServletRequest request,
                                    HttpServletResponse response,
                                    Object command) throws Exception {
        return new ModelAndView(getCancelView());
    }

    /**
     * Find the currently logged in {@link User}.
     * 
     * @return the currently logged in user.
     */
    private User getCurrentUser()
    {
        UserDetails userDetails = 
            (UserDetails) SecurityContextHolder.getContext().
            getAuthentication().getPrincipal();
        User user = userService.getUser(userDetails.getUsername());
        return user;
    }

    @Override
    protected Object formBackingObject(HttpServletRequest request)
            throws Exception
    {
        ChangePasswordForm form = new ChangePasswordForm();
        return form;
    }

    /**
     * @param userService the userService to set
     */
    public void setUserService(UserService userService)
    {
        this.userService = userService;
    }
}
