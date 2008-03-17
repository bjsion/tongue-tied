package org.tonguetied.web.servlet;

import static org.tonguetied.web.Constants.SHOW_ALL;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * Listener that initializes all the user session settings, when the user first 
 * starts a {@link HttpSession} and tidies up any resources when the 
 * {@link HttpSession} is lost.
 * 
 * @author bsion
 *
 */
public class UserSessionInitializer implements HttpSessionListener {

    /**
     * Initialize user preferences when a user starts a new session. This method
     * will set up the tabbing flag. 
     * 
     * @param event the notification event
     */
    public void sessionCreated(HttpSessionEvent event) {
        HttpSession session = event.getSession();
        session.setAttribute(SHOW_ALL, true);
    }

    /**
     * Currently does nothing.
     * 
     * the notification event
     */
    public void sessionDestroyed(HttpSessionEvent event) {
    }
}
